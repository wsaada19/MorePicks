package me.williamsaada.MorePicks.treasurefind;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LootItem implements ConfigurationSerializable {

    private String material;
    private int min;
    private int max;
    private int weight;

    public LootItem(String mat, int min, int max, int weight ){

        material = mat;
        this.min = min;
        this.max = max;
        this.weight = weight;
    }

    public LootItem(Map<String, Object> map){
        material = (String)map.get("material");
        min = (Integer)map.get("min");
        max = (Integer)map.get("max");
        weight = (Integer)map.get("weight");

    }

    public ItemStack getDrop(){
        Random random = new Random();
        int count = 0;
        if(max == min){
            count = 1;
        } else {
            count = random.nextInt(max - min) + min;
        }
        return new ItemStack(Material.getMaterial(material), count);

    }

    public int getWeight(){
        return weight;
    }

    public Map<String, Object> serialize() {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("material", material);
        map.put("min", min);
        map.put("max", max);
        map.put("weight", weight);
        return map;

    }
}
