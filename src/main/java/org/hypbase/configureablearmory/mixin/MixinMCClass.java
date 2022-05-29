package org.hypbase.configureablearmory.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import org.hypbase.configureablearmory.ConfigurableArmory;
import org.hypbase.configureablearmory.client.CustomAssetsPack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Minecraft.class)
public class MixinMCClass {
    @Shadow
    @Final
    private List<IResourcePack> defaultResourcePacks;

    @Inject(method = "refreshResources", at = @At(value = "HEAD"))
    private void addResourcePack(CallbackInfo ci) {
        if(defaultResourcePacks.isEmpty() || !defaultResourcePacks.contains(CustomAssetsPack.INST_RESOURCES)) {
            System.out.println("test");
            defaultResourcePacks.add(CustomAssetsPack.INST_RESOURCES);
        }
    }
}
