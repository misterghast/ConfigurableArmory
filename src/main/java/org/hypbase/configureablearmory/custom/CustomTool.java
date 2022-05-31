package org.hypbase.configureablearmory.custom;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
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
import java.util.UUID;

public class CustomTool extends ItemTool {
    private final Set<String> toolClasses;
    private final HashMap<String, Double> extraAttributes;

    private static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("84c741d8-df76-48fc-a8c1-dd51fc277109");
    private static final UUID HEALTH_MODIFIER = UUID.fromString(("18f4d83b-523b-4aa2-968d-97bf3d077679"));
    private static final UUID REACH_MODIFIER = UUID.fromString(("95264497-95f2-4302-845a-e008c3586b1c"));
    private static final UUID SWIM_SPEED_MODIFIER = UUID.fromString("949df3ab-8592-49a2-8a5d-dfee45308b7d");

    private final Item.ToolMaterial material;

    public CustomTool(String translationKey, float attackSpeed, float toolAttackDamage, ResourceLocation registryName, ToolMaterial material, CreativeTabs tab, String[] toolClasses, HashMap<String, Double> extraAttributes, Set<Block> effective) {
        super(toolAttackDamage, attackSpeed, material, effective);
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage((material.getMaxUses()));
        this.setCreativeTab(tab);
        this.setRegistryName(registryName);
        this.setTranslationKey(translationKey);
        this.toolClasses = new HashSet<String>();
        this.extraAttributes = extraAttributes;
        for(String tool : toolClasses) {
            this.toolClasses.add(tool);
        }
    }
    //@Override
    //public boolean canHarvestBlock(IBlockState blockIn) {
        //Block block = blockIn.getBlock();
        //if()
    //}

    private static IAttribute getAttributeByString(String name) {
        switch (name) {
            case "speed":
                return SharedMonsterAttributes.MOVEMENT_SPEED;
            case "health":
                return SharedMonsterAttributes.MAX_HEALTH;
            case "reach":
                return EntityPlayer.REACH_DISTANCE;
            case "swimSpeed":
                return EntityPlayer.SWIM_SPEED;
        }

        return null;
    }

    private static UUID getAttributeUUIDByString(String name) {
        switch (name) {
            case "speed":
                return MOVEMENT_SPEED_MODIFIER;
            case "health":
                return HEALTH_MODIFIER;
            case "reach":
                return REACH_MODIFIER;
            case "swimSpeed":
                return SWIM_SPEED_MODIFIER;
        }
        return null;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        if(extraAttributes.size() > 0) {
            Multimap<String, AttributeModifier> map = super.getItemAttributeModifiers(slot);

            if (slot == EntityEquipmentSlot.MAINHAND) {
                for(String key : extraAttributes.keySet()) {
                    if(key != null && extraAttributes.get(key) != null) {
                        map.put(getAttributeByString(key).getName(), new AttributeModifier(getAttributeUUIDByString(key), "Tool modifier", extraAttributes.get(key), 0));
                    }
                }
                return map;
            }
        }

        return super.getItemAttributeModifiers(slot);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        if(toolClasses != null) {
            return toolClasses;
        } else {
            return super.getToolClasses(stack);
        }
    }
}
