package com.HeroAtlasted.combatrevamp;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.fixes.EntityHealth;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("EntityConstructor")
public class testGolemEntity extends MonsterEntity {
    private static final Logger LOGGER = LogManager.getLogger();

    public testGolemEntity(EntityType<? extends testGolemEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {

        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(ForgeMod.SWIM_SPEED.get(), 0.25D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23F)
                .createMutableAttribute(Attributes.MAX_HEALTH, 20D);
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
