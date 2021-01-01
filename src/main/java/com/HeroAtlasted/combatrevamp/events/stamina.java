package com.HeroAtlasted.combatrevamp.events;

import com.HeroAtlasted.combatrevamp.combatrevamp;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber()
public class stamina {
    private static final Logger LOGGER = LogManager.getLogger();
    @SubscribeEvent
    public static void onTick(TickEvent event) { // DO NOT ASSUME EACH TICK IS 50MS
        PlayerEntity player = Minecraft.getInstance().player;
        if (combatrevamp.currentStamina < combatrevamp.maxStamina) { // deal with upping stamina in accordance to timeDelay & sps
            if (combatrevamp.lastStaminaUsage+(combatrevamp.staminaRechangeTimeDelay*1000) < System.currentTimeMillis()) {
                if (combatrevamp.lastStaminaUpdate+ combatrevamp.staminaUpdateRate <= System.currentTimeMillis()) {
                    if ((System.currentTimeMillis() - combatrevamp.lastStaminaUpdate) > 100) {
                        combatrevamp.lastStaminaUpdate = System.currentTimeMillis();
                    }
                    combatrevamp.currentStamina = Math.min(100, combatrevamp.currentStamina + (combatrevamp.staminaPerSecond / (1000.0 / (System.currentTimeMillis() - combatrevamp.lastStaminaUpdate))));
                    combatrevamp.lastStaminaUpdate = System.currentTimeMillis();
                }
            }
        }
        try {
            player.experienceLevel = (int) Math.floor(combatrevamp.currentStamina);
            player.experience = (float) combatrevamp.currentStamina / combatrevamp.maxStamina;
        } catch (NullPointerException e) {}
    }
}
