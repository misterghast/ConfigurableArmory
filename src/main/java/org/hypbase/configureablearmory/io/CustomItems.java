package org.hypbase.configureablearmory.io;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import org.hjson.HjsonOptions;
import org.hjson.JsonArray;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import org.hypbase.configureablearmory.ConfigurableArmory;
import org.hypbase.configureablearmory.custom.CustomTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomItems {

    private Map<String, Item.ToolMaterial> materials;
    private Map<String, ItemArmor.ArmorMaterial> armorMaterials;
    public Map<String, Item> items;
    private String rootDirectory;

    private int itemId;

    public Map<String, Item> getItems() {
        return this.items;
    }


    private CreativeTabs intTOTab(int integer) {
        switch(integer) {
            case 0:
                return CreativeTabs.BUILDING_BLOCKS;
            case 1:
                return CreativeTabs.DECORATIONS;
            case 2:
                return CreativeTabs.REDSTONE;
            case 3:
                return CreativeTabs.TRANSPORTATION;
            case 4:
                return CreativeTabs.MISC;
            case 5:
                return CreativeTabs.FOOD;
            case 6:
                return CreativeTabs.TOOLS;
            case 7:
                return CreativeTabs.COMBAT;
            case 8:
                return CreativeTabs.BREWING;
            default:
                return CreativeTabs.COMBAT;

        }
    }

    private float[] getToolDefaultVals(String template) {
        float[] vals = new float[2];
        vals[0] = 0.0f;
        vals[1] = 0.0f;
        if(template.equals("axe")) {
            vals[0] = -3.0f;
            vals[1] = 6.0f;
        } else if (template.equals("pickaxe")) {
            vals[0] = -2.8f;
            vals[1] = 1.0f;
        } else if (template.equals("shovel")) {
            vals[0] = -3.0f;
            vals[1] = 1.5f;
        } else {
            vals[0] = -2.4f;
            vals[1] = 3.0f;
        }

        return vals;
    }

    public CustomItems(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.items = new HashMap<String, Item>();
        this.materials = new HashMap<String, Item.ToolMaterial>();
        this.armorMaterials = new HashMap<String, ItemArmor.ArmorMaterial>();
        itemId = 0;
    }

    public void loadMaterials(String directory) {
        try {
            File subDirectory = new File(rootDirectory + "/" + directory);
            System.out.println(subDirectory.getAbsolutePath());
            if(!subDirectory.exists()) {
                subDirectory.mkdir();
            }
            for(String child : subDirectory.list()) {
                if(new File(child).isDirectory()) {
                    loadMaterials(subDirectory + "/" + child);
                } else {
                    JsonObject json = loadHJson(subDirectory + "/" + child);
                    if(json.get("type").asString().equals("tool")) {
                        String name = json.get("name").asString();
                        int harvestLevel = json.get("harvestLevel").asInt();
                        int maxUses = json.get("maxDurability").asInt();
                        float efficiency = json.get("miningSpeed").asFloat();
                        float damageVsEntity = json.get("attackDamage").asFloat();
                        int enchantability = json.get("enchantability").asInt();
                        materials.put(name, EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damageVsEntity, enchantability));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadItems(String directory) {
        try {
            File subDirectory = new File(rootDirectory + "/" + directory);
            if(!subDirectory.exists()) {
                subDirectory.mkdir();
            }
            for(String child : subDirectory.list() ) {
                if(new File(child).isDirectory()) {
                    loadItems(subDirectory + "/" + child);
                } else {
                    JsonObject json = loadHJson(subDirectory + "/" + child);
                    if(json.get("type").asString().equals("tool")) {

                        String id = json.get("id").asString();
                        String translationKey = "custom." + id;
                        ResourceLocation registryName = new ResourceLocation("configurablearmory",  id);
                        Item.ToolMaterial material = materials.get(json.get("material").asString());
                        CreativeTabs tab = this.intTOTab(json.get("tab").asInt());

                        JsonValue template = json.get("template");
                        String[] toolClasses;
                        if(template != null) {
                            toolClasses = new String[1];
                            toolClasses[0] = template.asString();
                        } else {
                            JsonArray toolArray = json.get("toolTypes").asArray();
                            toolClasses = new String[toolArray.size()];
                            for(int i = 0; i < toolArray.size(); i++) {
                                toolClasses[i] = toolArray.get(i).asString();
                            }
                        }

                        HashMap<String, Double> extraAttributes = new HashMap<String, Double>();
                        JsonValue extraAttributesJson = json.get("extraAttributes");
                        if(extraAttributesJson != null) {
                            JsonArray extraAttributesArray = extraAttributesJson.asArray();
                            for(int i = 0; i < extraAttributesArray.size(); i++) {
                                JsonValue entry = extraAttributesArray.get(i);
                                if(entry != null) {
                                    JsonArray entryArray = entry.asArray();
                                    JsonValue val1 = entryArray.get(0);
                                    JsonValue val2 = entryArray.get(1);

                                    try {
                                        String key = null;
                                        if(val1 != null){
                                            key = val1.asString();
                                        }

                                        Double val = null;
                                        if(val2 != null) {
                                            val = val2.asDouble();
                                        }

                                        extraAttributes.put(key, val);
                                    } catch(UnsupportedOperationException e) {

                                    }
                                }
                            }
                        } else {
                            extraAttributes = null;
                        }

                        float attackSpeed = 0;
                        float toolAttackDamage = 0;
                        if(template == null || template.equals("none")) {
                           attackSpeed = json.get("attackSpeed").asFloat();
                           toolAttackDamage = json.get("attackDamage").asFloat();
                        } else if(template != null) {
                            float[] vals = getToolDefaultVals(template.asString());
                            JsonValue attackSpeedJson = json.get("attackSpeed");
                            JsonValue toolAttackDamageJson = json.get("attackDamage");
                            if(attackSpeedJson != null) {
                                attackSpeed = attackSpeedJson.asFloat();
                            } else {
                                attackSpeed = vals[0];
                            }

                            if(toolAttackDamageJson != null) {
                                toolAttackDamage = toolAttackDamageJson.asFloat();
                            } else {
                                toolAttackDamage = vals[1];
                            }
                        }
                        Set<Block> effective = new HashSet<Block>();
                        items.put(id, new CustomTool(translationKey, attackSpeed, toolAttackDamage, registryName, material, tab, toolClasses, extraAttributes, effective));
                    } else if(json.get("type").asString().equals("armor")) {

                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }


    public void registerItems() {

    }

    public JsonObject loadHJson(String fileLocation) throws IOException {
        FileReader reader = new FileReader(fileLocation);
        JsonObject json = JsonValue.readHjson(reader).asObject();
        return json;
    }


}

