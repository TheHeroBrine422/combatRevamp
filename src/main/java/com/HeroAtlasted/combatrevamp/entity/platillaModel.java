// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports
package com.HeroAtlasted.combatrevamp.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class platillaModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer Legs;
	private final ModelRenderer Arms;
	private final ModelRenderer Arm1;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer Arm2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer Head;
	private final ModelRenderer Eyes;
	private final ModelRenderer Body;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;
	private final ModelRenderer bb_main;
	private final ModelRenderer Tail_r1;

	public platillaModel() {
		textureWidth = 64;
		textureHeight = 64;

		Legs = new ModelRenderer(this);
		Legs.setRotationPoint(3.0F, 24.0F, 5.0F);
		Legs.setTextureOffset(20, 0).addBox(-8.0F, -4.0F, -4.0F, 3.0F, 4.0F, 3.0F, 0.0F, true);
		Legs.setTextureOffset(20, 0).addBox(-1.0F, -4.0F, -4.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);
		Legs.setTextureOffset(12, 28).addBox(-2.0F, 0.0F, -5.0F, 5.0F, 0.0F, 4.0F, 0.0F, false);
		Legs.setTextureOffset(12, 28).addBox(-9.0F, 0.0F, -5.0F, 5.0F, 0.0F, 4.0F, 0.0F, false);

		Arms = new ModelRenderer(this);
		Arms.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		Arm1 = new ModelRenderer(this);
		Arm1.setRotationPoint(-2.5F, -9.5F, -1.75F);
		Arms.addChild(Arm1);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -1.0F, -2.0F);
		Arm1.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.6109F, 0.6981F, -0.0436F);
		cube_r1.setTextureOffset(0, 20).addBox(-3.0F, -0.75F, -1.41F, 3.0F, 6.0F, 3.0F, -0.1F, true);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-2.5F, 3.5F, -2.75F);
		Arm1.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.6981F, 0.0F);
		cube_r2.setTextureOffset(0, 20).addBox(-1.5F, -1.0F, -1.75F, 3.0F, 7.0F, 3.0F, 0.1F, false);

		Arm2 = new ModelRenderer(this);
		Arm2.setRotationPoint(2.5F, -9.5F, -1.75F);
		Arms.addChild(Arm2);
		

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(2.5F, 3.5F, -2.75F);
		Arm2.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -0.6981F, 0.0F);
		cube_r3.setTextureOffset(0, 20).addBox(-1.5F, -1.0F, -1.75F, 3.0F, 7.0F, 3.0F, 0.1F, true);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, -1.0F, -2.0F);
		Arm2.addChild(cube_r4);
		setRotationAngle(cube_r4, -0.6109F, -0.6981F, 0.0436F);
		cube_r4.setTextureOffset(0, 20).addBox(0.0F, -0.75F, -1.41F, 3.0F, 6.0F, 3.0F, -0.1F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 10.0F, -3.0F);
		Head.setTextureOffset(17, 8).addBox(-2.0F, -0.5F, -3.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
		Head.setTextureOffset(28, 7).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		Eyes = new ModelRenderer(this);
		Eyes.setRotationPoint(2.0F, 2.0F, 0.0F);
		Head.addChild(Eyes);
		Eyes.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, true);
		Eyes.setTextureOffset(0, 0).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setRotationPoint(-0.5F, 21.75F, 5.0F);
		

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, 0.0F, -2.0F);
		Body.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.7418F, 0.0F, 0.0F);
		cube_r5.setTextureOffset(0, 11).addBox(-2.0F, -6.0F, -1.0F, 5.0F, 6.0F, 3.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.5F, -0.75F, -2.0F);
		Body.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.7418F, 0.0F, 0.0F);
		cube_r6.setTextureOffset(0, 0).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 7.0F, 4.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		Tail_r1 = new ModelRenderer(this);
		Tail_r1.setRotationPoint(0.0F, -2.75F, 3.75F);
		bb_main.addChild(Tail_r1);
		setRotationAngle(Tail_r1, -0.3491F, 0.0F, 0.0F);
		Tail_r1.setTextureOffset(12, 20).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Legs.render(matrixStack, buffer, packedLight, packedOverlay);
		Arms.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}