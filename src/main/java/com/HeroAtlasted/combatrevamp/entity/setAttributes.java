package com.HeroAtlasted.combatrevamp.entity;

import com.HeroAtlasted.combatrevamp.combatrevamp;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;

public class setAttributes {
    public setAttributes() {

    }

    public static void setup() {
        GlobalEntityTypeAttributes.put(combatrevamp.PLATILLA_ENTITY, platillaEntity.registerAttributes().create());
    }
}
