package org.hypbase.configureablearmory;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.hypbase.configureablearmory.client.CustomAssetsPack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid="configurablearmory")
public class ConfigurableArmory {
    public static final String MODID = "configurablearmory";
    public static final String ROOT_DIRECTORY = "configurablearmory";
    @SidedProxy(modId="configurablearmory", clientSide="org.hypbase.configureablearmory.ClientProxy", serverSide="org.hypbase.configureablearmory.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(proxy);
        File directory = new File(ROOT_DIRECTORY);
        if(!directory.exists()) {
            directory.mkdir();
        }



        proxy.initItems();
    }

}
