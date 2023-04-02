package com.HeroAtlasted.combatrevamp.items.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ShortbowItem extends BowItem {
    public ShortbowItem(Properties builder) {
        super(builder);
    }

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) { // not sure how to make custom actions yet: https://forge.yue.moe/javadoc/1.16.4/
        LOGGER.info("onItemRightClick Shortbow");
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        LOGGER.info("onUse Shortbow");
        //super.onUse(worldIn, livingEntityIn, stack, count);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.FAIL;
    }
}
