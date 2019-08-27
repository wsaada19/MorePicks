package me.williamsaada.MorePicks.treasurefind;

import com.sun.istack.internal.Nullable;
import me.williamsaada.MorePicks.treasurefind.LootItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class LootTable {

    private ArrayList<LootItem> itemList;
    private int totalWeight;

    public LootTable(){
        itemList = new ArrayList<LootItem>();
        totalWeight = 0;
    }

    public void addItem(LootItem item){
        itemList.add(item);
        totalWeight += item.getWeight();
    }
    @Nullable
    public ItemStack pullFromLootTable(){

        Random random = new Random();
        int finalWeight = random.nextInt(totalWeight + 1) + 1;
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
            return null;
        } else {
            return itemToReturn;
        }
    }
}
