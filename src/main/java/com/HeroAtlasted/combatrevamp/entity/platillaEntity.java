package com.HeroAtlasted.combatrevamp.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

@SuppressWarnings("EntityConstructor")
public class platillaEntity extends MonsterEntity {
    public platillaEntity(EntityType<? extends platillaEntity> type, World world) {
        super(type, world);
        this.experienceValue = 10;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20D)
                .createMutableAttribute(ForgeMod.SWIM_SPEED.get(), 0);
    }
}
