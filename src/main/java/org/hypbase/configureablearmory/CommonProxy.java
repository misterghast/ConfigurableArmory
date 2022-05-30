package org.hypbase.configureablearmory;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.hypbase.configureablearmory.client.CustomAssetsPack;
import org.hypbase.configureablearmory.io.CustomItems;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {
    protected final CustomItems items;
    public CommonProxy() {
        items = new CustomItems(ConfigurableArmory.ROOT_DIRECTORY);
    }

    public void initItems() {
        items.loadMaterials("materials");
        items.loadItems("items");
    }

    @SubscribeEvent
    public void itemRegistryEvent(RegistryEvent.Register<Item> event) {
        for(Item item : items.getItems().values()) {

            event.getRegistry().register(item);
        }
    }
}
