package com.HeroAtlasted.combatrevamp.entity;


import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class platillaRenderer extends MobRenderer<platillaEntity, platillaModel<platillaEntity>> {

    public platillaRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new platillaModel<>(), 1F);
    }

    @Override
    public ResourceLocation getEntityTexture(platillaEntity entity) {
        ResourceLocation locator = new ResourceLocation("combatrevamp:textures/entity/platillatexture.png");

        return locator;
    }
}
