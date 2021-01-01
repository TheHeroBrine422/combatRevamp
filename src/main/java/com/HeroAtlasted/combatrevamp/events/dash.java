package com.HeroAtlasted.combatrevamp.events;

import com.HeroAtlasted.combatrevamp.combatrevamp;
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
        ArrayList<KeyBinding> keyBindings = combatrevamp.keyBindings;

        if (keyBindings.get(0).isKeyDown()) {
            boolean canDash = true;
            if (!combatrevamp.canDashAir) {
                canDash = false;
            }
            if ((combatrevamp.lastDash+ combatrevamp.timeBetweenDashes) > (System.currentTimeMillis() / 100L)) {
                canDash = false;
            }
            if (canDash) {
                PlayerEntity player = Minecraft.getInstance().player;
                if (combatrevamp.currentStamina >= combatrevamp.dashStaminaUsage) {
                    if (combatrevamp.dashType == 0) {
                        Vector3d motion = player.getMotion();
                        player.setVelocity(motion.x * combatrevamp.dashMultiplier, motion.y, motion.z * combatrevamp.dashMultiplier);
                    } else if (combatrevamp.dashType == 1 || combatrevamp.dashType == 2) {
                        Vector2f inputMotion = combatrevamp.movementInputObject.getMoveVector();
                        Vector3d playerLookVec = player.getLookVec();
                        double angle = combatrevamp.cameraAngle();
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
                        if (combatrevamp.dashType == 1) {
                            player.setVelocity(Math.sin(angle) * combatrevamp.dashAbsolute, player.getMotion().y, Math.cos(angle) * combatrevamp.dashAbsolute);
                        } else if (combatrevamp.dashType == 2) {
                            player.setVelocity(Math.sin(angle) * combatrevamp.dashAbsolute, combatrevamp.dashYMomentum, Math.cos(angle) * combatrevamp.dashAbsolute);
                        }
                    }
                    combatrevamp.lastDash = (System.currentTimeMillis() / 100L);
                    combatrevamp.canDashAir = false;
                    combatrevamp.currentStamina -= combatrevamp.dashStaminaUsage;
                    combatrevamp.lastStaminaUsage = System.currentTimeMillis();
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
        if (!combatrevamp.canDashAir) { // deal with onGround stuff for dash/double jump
            try {
                if (player.isOnGround()) {
                    combatrevamp.canDashAir = true;
                }
            } catch (NullPointerException e) {}
        }
    }
}
