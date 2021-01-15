package com.HeroAtlasted.combatrevamp;

import com.HeroAtlasted.combatrevamp.entity.*;
import com.HeroAtlasted.combatrevamp.events.*;
import com.HeroAtlasted.combatrevamp.items.weapons.ShortbowItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("combatrevamp")
public class combatrevamp
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
    public static RegistryObject<EntityType<platillaEntity>> platillaRegister;
    public static RegistryObject<EntityType<testGolemEntity>> testGolemRegister;


    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "combatrevamp");

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "combatrevamp");



    //public static final RegistryObject<Block> ROCK_BLOCK = BLOCKS.register("rock", () -> new Block(Block.Properties.create(Material.ROCK)));

    public combatrevamp() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(new extraJumps());
        MinecraftForge.EVENT_BUS.register(new dash());
        MinecraftForge.EVENT_BUS.register(new stamina());
        MinecraftForge.EVENT_BUS.register(new movementInput());
        MinecraftForge.EVENT_BUS.register(new fallDamage());

        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        platillaRegister = ENTITIES.register("platilla", () -> EntityType.Builder.create(platillaEntity::new, EntityClassification.MONSTER).size(1F, 1F).build("platilla"));
        testGolemRegister = ENTITIES.register("testgolem", () -> EntityType.Builder.create(testGolemEntity::new, EntityClassification.MONSTER).size(2.5F, 3.75F).build("testgolem"));

        RegistryObject<Item> shortbowRegister = ITEMS.register("shortbow", () -> new ShortbowItem(new Item.Properties().group(ItemGroup.COMBAT).rarity(Rarity.COMMON)));
        LOGGER.info("ShortbowRegister: "+shortbowRegister.toString());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        // setup keybinding

        event.enqueueWork(setAttributes::setup);


    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

        RenderingRegistry.registerEntityRenderingHandler(platillaRegister.get(), platillaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(testGolemRegister.get(), testGolemRenderer::new);

        keyBindings.add(new KeyBinding("Dash", 98, "exampleMod"));
        for (int i = 0; i < keyBindings.size(); i++) {
            ClientRegistry.registerKeyBinding(keyBindings.get(i));
        }
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
    }
}
