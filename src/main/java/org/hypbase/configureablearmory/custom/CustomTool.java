package org.hypbase.configureablearmory.custom;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import org.hypbase.configureablearmory.io.CustomType;
import org.xml.sax.helpers.AttributesImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CustomTool extends ItemTool {
    private final Set<String> toolClasses;

    private final Item.ToolMaterial material;

    public CustomTool(String translationKey, float attackSpeed, float toolAttackDamage, ResourceLocation registryName, ToolMaterial material, CreativeTabs tab, String[] toolClasses, Set<Block> effective) {
        super(toolAttackDamage, attackSpeed, material, effective);
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage((material.getMaxUses()));
        this.setCreativeTab(tab);
        this.setRegistryName(registryName);
        this.setTranslationKey(translationKey);
        this.toolClasses = new HashSet<String>();
        for(String tool : toolClasses) {
            this.toolClasses.add(tool);
        }
    }
    //@Override
    //public boolean canHarvestBlock(IBlockState blockIn) {
        //Block block = blockIn.getBlock();
        //if()
    //}

    /*@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot) {
        Multimap<String, AttributeModifier> map = (Multimap<String, AttributeModifier>) new HashMap<String, AttributeModifier>();

        if (slot == EntityEquipmentSlot.MAINHAND) {
            map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.att))
        }
    }*/

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        if(toolClasses != null) {
            return toolClasses;
        } else {
            return super.getToolClasses(stack);
        }
    }
}
