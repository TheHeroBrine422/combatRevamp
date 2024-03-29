package com.HeroAtlasted.combatrevamp.entity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class testGolemRenderer extends MobRenderer<testGolemEntity, testGolemModel<testGolemEntity>> {
    public testGolemRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new testGolemModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(testGolemEntity entity) {
        ResourceLocation locator = new ResourceLocation("combatrevamp:textures/entity/testgolemtexture.png");
        return locator;
    }
}

