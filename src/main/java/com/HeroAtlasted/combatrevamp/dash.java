package com.HeroAtlasted.combatrevamp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber()
public class dash {
    private static final Logger LOGGER = LogManager.getLogger();
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        ArrayList<KeyBinding> keyBindings = combatRevamp.keyBindings;

        if (keyBindings.get(0).isKeyDown()) {
            boolean canDash = true;
            if (!combatRevamp.canDashAir) {
                canDash = false;
            }
            if ((combatRevamp.lastDash+ combatRevamp.timeBetweenDashes) > (System.currentTimeMillis() / 100L)) {
                canDash = false;
            }
            if (canDash) {
                PlayerEntity player = Minecraft.getInstance().player;
                if (combatRevamp.currentStamina >= combatRevamp.dashStaminaUsage) {
                    if (combatRevamp.dashType == 0) {
                        Vector3d motion = player.getMotion();
                        player.setVelocity(motion.x * combatRevamp.dashMultiplier, motion.y, motion.z * combatRevamp.dashMultiplier);
                    } else if (combatRevamp.dashType == 1 || combatRevamp.dashType == 2) {
                        Vector2f inputMotion = combatRevamp.movementInputObject.getMoveVector();
                        Vector3d playerLookVec = player.getLookVec();
                        double angle = combatRevamp.cameraAngle();
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
                        if (combatRevamp.dashType == 1) {
                            player.setVelocity(Math.sin(angle) * combatRevamp.dashAbsolute, player.getMotion().y, Math.cos(angle) * combatRevamp.dashAbsolute);
                        } else if (combatRevamp.dashType == 2) {
                            player.setVelocity(Math.sin(angle) * combatRevamp.dashAbsolute, combatRevamp.dashYMomentum, Math.cos(angle) * combatRevamp.dashAbsolute);
                        }
                    }
                    combatRevamp.lastDash = (System.currentTimeMillis() / 100L);
                    combatRevamp.canDashAir = false;
                    combatRevamp.currentStamina -= combatRevamp.dashStaminaUsage;
                    combatRevamp.lastStaminaUsage = System.currentTimeMillis();
                } else {
                    // some kind of notification that you dont have enough stamina. sound effect?
                    LOGGER.info("dash not done cause not enough stamina");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent event) { // DO NOT ASSUME EACH TICK IS 50MS
        PlayerEntity player = Minecraft.getInstance().player;
        if (!combatRevamp.canDashAir) { // deal with onGround stuff for dash/double jump
            try {
                if (player.isOnGround()) {
                    combatRevamp.canDashAir = true;
                }
            } catch (NullPointerException e) {}
        }
    }
}
