package org.hypbase.configureablearmory.client;

import com.google.gson.JsonObject;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import org.hypbase.configureablearmory.ConfigurableArmory;

import java.io.File;

public class CustomAssetsPack extends FolderResourcePack {

    public static final CustomAssetsPack INST_RESOURCES = new CustomAssetsPack();

    public CustomAssetsPack() {
        super(new File(ConfigurableArmory.ROOT_DIRECTORY, "cosmetic"));
        resourcePackFile.mkdir();
        File temp = new File(resourcePackFile, "assets");
        temp.mkdir();

        new File(temp,"configurablearmory").mkdir();

    }

    @Override
    public String getPackName() {
        return "ConfigurableExternalResources";
    }

    @Override
    public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) {
        JsonObject metadata = new JsonObject();
        JsonObject packobject = new JsonObject();
        metadata.add("pack", packobject);
        packobject.addProperty("description", "Textures and models for custom items implemented by ConfigurableArmory");
        packobject.addProperty("pack_format", 3);
        return metadataSerializer.parseMetadataSection(metadataSectionName, metadata);
    }
}
