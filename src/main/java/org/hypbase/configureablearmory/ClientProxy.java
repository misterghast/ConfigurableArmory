package org.hypbase.configureablearmory;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public void modelRegistration(ModelRegistryEvent event) {
        Set<Map.Entry<String, Item>> itemSet = super.items.getItems().entrySet();
        Iterator<Map.Entry<String, Item>> itemIt = itemSet.iterator();
        while(itemIt.hasNext()) {
            Map.Entry<String, Item> entry = itemIt.next();
            ModelLoader.setCustomModelResourceLocation(entry.getValue(), 0, new ModelResourceLocation(new ResourceLocation("configurablearmory", entry.getKey()), "inventory"));
        }
    }
}
