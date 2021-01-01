package com.HeroAtlasted.combatrevamp.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class fallDamage {
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) { // make it where you can fall 6 blocks instead of 3 for fall dmg
        if (event.getEntity() instanceof PlayerEntity) {
            event.setDistance(event.getDistance()-3);
        }
    }
}
