package org.hypbase.configureablearmory.custom;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import org.hypbase.configureablearmory.io.CustomType;

public class CustomTool extends ItemTool {

    private final float attackDamage;

    private final Item.ToolMaterial material;

    public CustomTool(String translationKey, ResourceLocation registryName, ToolMaterial material, CreativeTabs tab) {
        super(material, );
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage((material.getMaxUses()));
        this.setCreativeTab(tab);
        this.attackDamage = 3.0F + material.getAttackDamage();
    }

    public float getAttackDamage() {
        return this.material.getAttackDamage();
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state) {

    }
}
