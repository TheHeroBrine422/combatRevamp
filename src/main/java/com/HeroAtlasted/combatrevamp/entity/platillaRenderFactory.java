package com.HeroAtlasted.combatrevamp.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class platillaRenderFactory implements IRenderFactory<MobEntity> {
    public static final platillaRenderFactory INSTANCE = new platillaRenderFactory();

    @Override
    public EntityRenderer<? super MobEntity> createRenderFor(EntityRendererManager manager) {
        return new platillaRenderer(manager).getRenderManager().getRenderer(manager.pointedEntity);
    }
}
