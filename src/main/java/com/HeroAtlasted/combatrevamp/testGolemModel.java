// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
package com.HeroAtlasted.combatrevamp;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class testGolemModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer bb_main;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;

	public testGolemModel() {
		textureWidth = 128;
		textureHeight = 128;

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(68, 24).addBox(-12.0F, -16.0F, -3.0F, 9.0F, 16.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 32).addBox(-12.0F, -34.0F, -3.0F, 24.0F, 8.0F, 10.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 0).addBox(-11.5F, -56.0F, -3.0F, 23.0F, 22.0F, 10.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 50).addBox(-10.0F, -60.0F, -2.0F, 20.0F, 4.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(85, 48).addBox(-19.75F, -41.0F, -1.5F, 7.0F, 16.0F, 7.0F, 0.0F, false);
		bb_main.setTextureOffset(66, 0).addBox(3.0F, -16.0F, -3.0F, 9.0F, 16.0F, 8.0F, 0.0F, false);
		bb_main.setTextureOffset(0, 83).addBox(12.75F, -41.0F, -1.5F, 7.0F, 16.0F, 7.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(11.25F, -55.0F, 1.0F);
		bb_main.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -0.0436F);
		cube_r1.setTextureOffset(38, 74).addBox(0.0F, -1.0F, -3.0F, 8.0F, 16.0F, 8.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(6.5F, 0.0F, -1.0F);
		bb_main.addChild(cube_r2);
		setRotationAngle(cube_r2, -0.0873F, 0.0F, 0.0F);
		cube_r2.setTextureOffset(47, 53).addBox(-4.25F, -27.0F, -4.0F, 10.0F, 12.0F, 9.0F, 0.0F, false);
		cube_r2.setTextureOffset(0, 62).addBox(-18.75F, -27.0F, -4.0F, 10.0F, 12.0F, 9.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-11.25F, -55.0F, 1.0F);
		bb_main.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, 0.0436F);
		cube_r3.setTextureOffset(70, 74).addBox(-8.0F, -1.0F, -3.0F, 8.0F, 16.0F, 8.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}