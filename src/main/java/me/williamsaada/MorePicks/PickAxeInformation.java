package me.williamsaada.MorePicks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


public class PickAxeInformation {

    public static ArrayList<PickAxeInformation> listOfPicks = new ArrayList<PickAxeInformation>();
    private String name;
    private ArrayList<String> itemLore;
    private boolean unbreakable;
    private boolean enabled;
    private Material material;
    private int cost;

    public PickAxeInformation(String name, ArrayList<String> itemLore, boolean ub, boolean eb, Material mat,
                              int cost){
        this.name = name;
        this.itemLore = itemLore;
        itemLore.add("");
        unbreakable = ub;
        enabled = eb;
        material = mat;
        this.cost = cost;
    }

    public static boolean IsThisAMorePicksTool(String itemName){
        for(PickAxeInformation pick : listOfPicks)
        {
            if(itemName.equalsIgnoreCase((pick.getName())))
            {
                return true;
            }
        }
        return false;
    }

    public static PickAxeInformation getPick(int i){
        return listOfPicks.get(i);
    }

    public static ArrayList<PickAxeInformation> getListOfPicks(){
        return listOfPicks;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getItemLore(){
        return itemLore;
    }

    public boolean getEnabled(){
        return enabled;
    }

    public ItemStack getPick(){

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(itemLore);
        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;

    }


}
