package com.HeroAtlasted.combatrevamp.entity;

import com.HeroAtlasted.combatrevamp.testGolemEntity;
import com.HeroAtlasted.combatrevamp.testGolemModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class platillaRenderer extends MobRenderer<platillaEntity, platillaModel<platillaEntity>> {

    public platillaRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new platillaModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(platillaEntity entity) {
        return new ResourceLocation("combatrevamp:platillaTexture.png");
    }
}
