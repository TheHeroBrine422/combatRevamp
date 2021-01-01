package com.HeroAtlasted.combatrevamp;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class testGolemRenderFactory implements IRenderFactory<MobEntity> {
    public static final testGolemRenderFactory INSTANCE = new testGolemRenderFactory();

    @Override
    public EntityRenderer<? super MobEntity> createRenderFor(EntityRendererManager manager) {
        // TODO Auto-generated method stub
        return new testGolemRenderer(manager).getRenderManager().getRenderer(manager.pointedEntity);
    }
}
