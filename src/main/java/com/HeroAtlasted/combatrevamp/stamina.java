package com.HeroAtlasted.combatrevamp;

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
        if (combatRevamp.currentStamina < combatRevamp.maxStamina) { // deal with upping stamina in accordance to timeDelay & sps
            if (combatRevamp.lastStaminaUsage+(combatRevamp.staminaRechangeTimeDelay*1000) < System.currentTimeMillis()) {
                if (combatRevamp.lastStaminaUpdate+ combatRevamp.staminaUpdateRate <= System.currentTimeMillis()) {
                    if ((System.currentTimeMillis() - combatRevamp.lastStaminaUpdate) > 100) {
                        combatRevamp.lastStaminaUpdate = System.currentTimeMillis();
                    }
                    combatRevamp.currentStamina = Math.min(100, combatRevamp.currentStamina + (combatRevamp.staminaPerSecond / (1000.0 / (System.currentTimeMillis() - combatRevamp.lastStaminaUpdate))));
                    combatRevamp.lastStaminaUpdate = System.currentTimeMillis();
                }
            }
        }
        try {
            player.experienceLevel = (int) Math.floor(combatRevamp.currentStamina);
            player.experience = (float) combatRevamp.currentStamina / combatRevamp.maxStamina;
        } catch (NullPointerException e) {}
    }
}
