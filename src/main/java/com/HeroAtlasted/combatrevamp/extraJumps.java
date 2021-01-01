package com.HeroAtlasted.combatrevamp;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber()
public class extraJumps {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (!player.isOnGround()) {
                boolean canExtraJump = true;
                if (combatRevamp.NumberOfJumps - 1 <= combatRevamp.extraJumpsUsed) {
                    canExtraJump = false;
                }
                if (combatRevamp.waitingForKeyDownJump) {
                    canExtraJump = false;
                }
                if (canExtraJump) {
                    Vector3d motion = player.getMotion();
                    player.setVelocity(motion.x, combatRevamp.extraJumpAbsolute, motion.z);
                    combatRevamp.extraJumpsUsed++;

                }
            }
            combatRevamp.waitingForKeyDownJump = true;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent event) { // DO NOT ASSUME EACH TICK IS 50MS
        if (combatRevamp.waitingForKeyDownJump) {
            if (!Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
                combatRevamp.waitingForKeyDownJump = false;
            }
        }

        if (combatRevamp.NumberOfJumps-1 <= combatRevamp.extraJumpsUsed) {
            PlayerEntity player = Minecraft.getInstance().player;
            try {
                if (player.isOnGround()) {
                    combatRevamp.extraJumpsUsed = 0;
                }
            } catch (NullPointerException e) {}
        }
    }
}
