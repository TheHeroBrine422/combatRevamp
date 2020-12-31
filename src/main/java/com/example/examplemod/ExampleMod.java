package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
public class ExampleMod
{
    // Settings
    public static long timeBetweenDashes = 5; // tenths of a second
    public static double dashMultiplier = 10;
    public static double dashAbsolute = 2;
    public static double dashYMomentum = 1;
    public static int dashType = 1;
    public static int dashStaminaUsage = 20;
    /*
    Dash Types:
    0 - dash that goes in current momentum direction (multiplies x/z by dashMultiplier)
    1 - dash that cancels momentum and goes in direction of movement keys (sets x/z to dashAbsolute)
    2 - dash 1 and set y velocity to dashYMomentum
     */

    public static int NumberOfJumps = 3;
    public static double extraJumpAbsolute = 0.6; // 0.4 = 1 block.    0.6 = 2.2 blocks.   1 = 5 blocks.

    public static int maxStamina = 100;
    public static double staminaRechangeTimeDelay = 1;
    public static int staminaPerSecond = 10;
    public static int staminaUpdateRate = 10; // ms.


    // Global Vars
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static long lastDash = 0;
    public static ArrayList<KeyBinding> keyBindings = new ArrayList<>();
    public static boolean canDashAir = true;
    public static int extraJumpsUsed = 0;
    public static MovementInput movementInputObject;
    public static double currentStamina = 100;
    public static long lastStaminaUsage = 0; // unix Epoch in ms
    public static long lastStaminaUpdate = 0; // unix epoch in ms
    public static boolean waitingForKeyDownJump = false;

   // private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "examplemod");

  //  public static final RegistryObject<Block> ROCK_BLOCK = BLOCKS.register("rock", () -> new Block(Block.Properties.create(Material.ROCK)));

    public ExampleMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(new Events());

     //   BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        // setup keybinding
        keyBindings.add(new KeyBinding("Dash", 98, "exampleMod"));

        for (int i = 0; i < keyBindings.size(); i++) {
            ClientRegistry.registerKeyBinding(keyBindings.get(i));
        }
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static double cameraAngle() { // solve for camera angle from directly above player in a 2d plane     Sources - https://stackoverflow.com/questions/6139451/how-can-i-convert-3d-space-coordinates-to-2d-space-coordinates https://stackoverflow.com/questions/9614109/how-to-calculate-an-angle-from-points
        PlayerEntity player = Minecraft.getInstance().player;
        Vector3d playerLookVec = player.getLookVec();
        return Math.atan2(playerLookVec.x/Math.abs(playerLookVec.y),playerLookVec.z/Math.abs(playerLookVec.y))*(180/Math.PI);
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

    @Mod.EventBusSubscriber()
    public static class Events {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            ArrayList<KeyBinding> keyBindings = ExampleMod.keyBindings;

            if (keyBindings.get(0).isKeyDown()) {
                boolean canDash = true;
                if (!ExampleMod.canDashAir) {
                    canDash = false;
                }
                if ((ExampleMod.lastDash+ExampleMod.timeBetweenDashes) > (System.currentTimeMillis() / 100L)) {
                    canDash = false;
                }
                if (canDash) {
                    PlayerEntity player = Minecraft.getInstance().player;
                    if (ExampleMod.currentStamina >= ExampleMod.dashStaminaUsage) {
                        if (ExampleMod.dashType == 0) {
                            Vector3d motion = player.getMotion();
                            player.setVelocity(motion.x * ExampleMod.dashMultiplier, motion.y, motion.z * ExampleMod.dashMultiplier);
                        } else if (ExampleMod.dashType == 1 || ExampleMod.dashType == 2) {
                            Vector2f inputMotion = ExampleMod.movementInputObject.getMoveVector();
                            Vector3d playerLookVec = player.getLookVec();
                            double angle = ExampleMod.cameraAngle();
                            List<Integer> keyAngles = new LinkedList<Integer>();
                            if (inputMotion.x == 1) {
                                keyAngles.add(90); // a
                            } else if (inputMotion.x == -1) {
                                keyAngles.add(270); // d
                            }
                            if (inputMotion.y == 1) {
                                keyAngles.add(0); // w
                            } else if (inputMotion.y == -1) {
                                keyAngles.add(180); // s
                            }
                            int avg = 0;
                            for (int i = 0; i < keyAngles.size(); i++) {
                                avg += keyAngles.get(i);
                            }
                            if (keyAngles.size() > 0) {
                                avg /= keyAngles.size();
                            }
                            if (inputMotion.x == -1 && inputMotion.y == 1) { // wd doesnt work and i dont wanna fix it properly.
                                avg = 315;
                            }
                            //      LOGGER.info("Camera Angle: "+angle);
                            //      LOGGER.info("Key Angle: "+avg);
                            angle += avg;
                            //      LOGGER.info("Total Angle: "+angle);
                            //      LOGGER.info(playerLookVec);
                            //      LOGGER.info("("+inputMotion.x+", "+inputMotion.y+")");
                            angle = angle * (Math.PI / 180);
                            if (ExampleMod.dashType == 1) {
                                player.setVelocity(Math.sin(angle) * ExampleMod.dashAbsolute, player.getMotion().y, Math.cos(angle) * ExampleMod.dashAbsolute);
                            } else if (ExampleMod.dashType == 2) {
                                player.setVelocity(Math.sin(angle) * ExampleMod.dashAbsolute, ExampleMod.dashYMomentum, Math.cos(angle) * ExampleMod.dashAbsolute);
                            }
                        }
                        ExampleMod.lastDash = (System.currentTimeMillis() / 100L);
                        ExampleMod.canDashAir = false;
                        ExampleMod.currentStamina -= ExampleMod.dashStaminaUsage;
                        ExampleMod.lastStaminaUsage = System.currentTimeMillis();
                    } else {
                        // some kind of notification that you dont have enough stamina. sound effect?
                        LOGGER.info("dash not done cause not enough stamina");
                    }
                }
            }
            if (Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
                PlayerEntity player = Minecraft.getInstance().player;
                if (!player.isOnGround()) {
                    boolean canExtraJump = true;
                    if (ExampleMod.NumberOfJumps-1 <= ExampleMod.extraJumpsUsed) {
                        canExtraJump = false;
                    }
                    if (ExampleMod.waitingForKeyDownJump) {
                        canExtraJump = false;
                    }
                    if (canExtraJump) {
                        Vector3d motion = player.getMotion();
                        player.setVelocity(motion.x, ExampleMod.extraJumpAbsolute, motion.z);
                        ExampleMod.extraJumpsUsed++;

                    }
                }
                ExampleMod.waitingForKeyDownJump = true;
            }
        }

        @SubscribeEvent
        public static void onTick(TickEvent event) { // DO NOT ASSUME EACH TICK IS 50MS
            PlayerEntity player = Minecraft.getInstance().player;
            if (!(ExampleMod.canDashAir && !(ExampleMod.NumberOfJumps-1 <= ExampleMod.extraJumpsUsed))) { // deal with onGround stuff for dash/double jump
                try {
                    if (player.isOnGround()) {
                        ExampleMod.canDashAir = true;
                        ExampleMod.extraJumpsUsed = 0;
                    }
                } catch (NullPointerException e) {}
            }

            if (ExampleMod.waitingForKeyDownJump) {
                if (!Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
                    ExampleMod.waitingForKeyDownJump = false;
                }
            }

            if (ExampleMod.currentStamina < ExampleMod.maxStamina) { // deal with upping stamina in accordance to timeDelay & sps
                if (ExampleMod.lastStaminaUsage+(ExampleMod.staminaRechangeTimeDelay*1000) < System.currentTimeMillis()) {
                    if (ExampleMod.lastStaminaUpdate+ExampleMod.staminaUpdateRate <= System.currentTimeMillis()) {
                        if ((System.currentTimeMillis() - ExampleMod.lastStaminaUpdate) > 100) {
                            ExampleMod.lastStaminaUpdate = System.currentTimeMillis();
                        }
                        ExampleMod.currentStamina = Math.min(100, ExampleMod.currentStamina + (ExampleMod.staminaPerSecond / (1000.0 / (System.currentTimeMillis() - ExampleMod.lastStaminaUpdate))));
                        ExampleMod.lastStaminaUpdate = System.currentTimeMillis();
                    }
                }
            }
            try {
                player.experienceLevel = (int) Math.floor(ExampleMod.currentStamina);
                player.experience = (float) ExampleMod.currentStamina / ExampleMod.maxStamina;
            } catch (NullPointerException e) {}
        }

        @SubscribeEvent
        public static void onLivingFall(LivingFallEvent event) {
            if (event.getEntity() instanceof PlayerEntity) {
                event.setDistance(event.getDistance()-3);
            }
        }

        @SubscribeEvent
        public static void onMovementInput(InputUpdateEvent event) {
            ExampleMod.movementInputObject = event.getMovementInput();
        }
    }
}
