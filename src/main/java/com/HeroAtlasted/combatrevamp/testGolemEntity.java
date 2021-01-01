package com.HeroAtlasted.combatrevamp;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class testGolemEntity extends MonsterEntity {

    public testGolemEntity(EntityType<? extends testGolemEntity> type, World world) {

        super(type, world);

    }

    @Override
    protected SoundEvent getAmbientSound() {

        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {

        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {

        return null;
    }

}
