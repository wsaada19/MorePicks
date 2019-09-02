package me.williamsaada.MorePicks.treasurefind;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class LootTable {

    private ArrayList<LootItem> itemList;
    private int totalWeight;
    private double dropChance;

    public LootTable(double chance){
        itemList = new ArrayList<LootItem>();
        totalWeight = 0;
        dropChance = chance;
    }

    public void addItem(LootItem item){
        itemList.add(item);
        totalWeight += item.getWeight();
    }
    public boolean willDropItem()
    {
        Random random = new Random();
        double rand = random.nextDouble();
        return (rand >= dropChance);
    }
    public ItemStack pullFromLootTable(){

        Random random = new Random();
        int finalWeight = random.nextInt(totalWeight + 1);
        ItemStack itemToReturn = null;
        int current = 0;
        for ( LootItem item : itemList) {
            if(item.getWeight() + current >= finalWeight){
                itemToReturn = item.getDrop();
                break;
            } else {
                current += item.getWeight();
            }
        }
        if(itemToReturn == null){
            return itemList.get(itemList.size() - 1).getDrop();
        } else {
            return itemToReturn;
        }
    }
}
