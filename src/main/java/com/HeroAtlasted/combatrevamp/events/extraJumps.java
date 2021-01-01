package com.HeroAtlasted.combatrevamp.events;

import com.HeroAtlasted.combatrevamp.combatrevamp;
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
                if (combatrevamp.NumberOfJumps - 1 <= combatrevamp.extraJumpsUsed) {
                    canExtraJump = false;
                }
                if (combatrevamp.waitingForKeyDownJump) {
                    canExtraJump = false;
                }
                if (canExtraJump) {
                    Vector3d motion = player.getMotion();
                    player.setVelocity(motion.x, combatrevamp.extraJumpAbsolute, motion.z);
                    combatrevamp.extraJumpsUsed++;

                }
            }
            combatrevamp.waitingForKeyDownJump = true;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent event) { // DO NOT ASSUME EACH TICK IS 50MS
        if (combatrevamp.waitingForKeyDownJump) {
            if (!Minecraft.getInstance().gameSettings.keyBindJump.isKeyDown()) {
                combatrevamp.waitingForKeyDownJump = false;
            }
        }

        if (combatrevamp.NumberOfJumps-1 <= combatrevamp.extraJumpsUsed) {
            PlayerEntity player = Minecraft.getInstance().player;
            try {
                if (player.isOnGround()) {
                    combatrevamp.extraJumpsUsed = 0;
                }
            } catch (NullPointerException e) {}
        }
    }
}
