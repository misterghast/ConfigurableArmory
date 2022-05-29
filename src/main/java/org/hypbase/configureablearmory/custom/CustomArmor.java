package org.hypbase.configureablearmory.custom;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import org.hypbase.configureablearmory.io.CustomType;

public class CustomArmor extends ItemArmor {
    public CustomArmor(String displayName, ResourceLocation registryName, ItemArmor.ArmorMaterial material, EntityEquipmentSlot slot) {

        super(material, 0, slot);
    }
}
