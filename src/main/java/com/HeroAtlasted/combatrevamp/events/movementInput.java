package com.HeroAtlasted.combatrevamp.events;

import com.HeroAtlasted.combatrevamp.combatrevamp;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class movementInput {
    @SubscribeEvent
    public static void onMovementInput(InputUpdateEvent event) { // give mod access to MovementInput
        combatrevamp.movementInputObject = event.getMovementInput();
    }
}
