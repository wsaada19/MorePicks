package me.williamsaada.MorePicks;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MorePicksUtility {

    private static final HashMap<String, String> smeltingMap = new HashMap<String, String>();
    private static final String[] itemCodes = {"laserPickaxe", "expPickaxe", "smeltingPickaxe", "magneticPickaxe",
            "bountifulPickaxe", "explosivePickaxe", "megaAxe", "piercingPickaxe", "dirtDestroyer", "treasurePickaxe",
            "treasureAxe", "treasureFishingRod"};
    private static final Material[] oreRank = {Material.DIAMOND_ORE, Material.GOLD_ORE, Material.EMERALD, Material.IRON_ORE,
                                Material.REDSTONE_ORE, Material.NETHER_QUARTZ_ORE, Material.COAL, Material.LAPIS_ORE};

    private static final String[] itemCodesThatNeedManualDurability = {"laserPickaxe", "explosivePickaxe", "megaAxe"};

    public static void initialize(){
        // Creates a hashmap of Ores/Materials and the material they are smelted into
        smeltingMap.put("IRON_ORE", "IRON_INGOT");
        smeltingMap.put("GOLD_ORE", "GOLD_INGOT");
        smeltingMap.put("STONE", "STONE");
        smeltingMap.put("SAND", "GLASS");
        smeltingMap.put("SANDSTONE", "SMOOTH_SANDSTONE");
        smeltingMap.put("NETHERRACK", "NETHER_BRICK");
    }

    // Returns the smelted material
    public static Material getSmeltingMaterial(String materialName){
        return(Material.getMaterial(smeltingMap.get(materialName)));
    }

    public static boolean isMaterialOre(String oreName){
        for(Material ore : oreRank){
            if(oreName.equalsIgnoreCase(ore.toString())){
                return true;
            }
        }
        return false;
    }

    // Returns true/false based on if a material can be smelted
    public static boolean canSmeltMaterial(String materialName){
        return (smeltingMap.containsKey(materialName));
    }

    // Returns array of string codes used in generating Picks from config file
    public static String[] getItemCodes(){
        return itemCodes;
    }

    public static Material returnHighestRankedOre(Material mat1, Material mat2){
        for(int i = 0; i < oreRank.length; i++){
            if(mat1 == oreRank[i]){
                return mat1;
            } else if(mat2 == oreRank[i]){
                return mat2;
            }
        }
        Bukkit.getLogger().info("ERROR: neither material is an ore, " + mat1.toString() + " or " + mat2.toString());
        return Material.BOWL;
    }

    public static String getCardinalDirection(Player player) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return getDirection(rot);
    }

    private static String getDirection(double rot) {
        if (0 <= rot && rot < 45) {
            return "North";
        } else if (45 <= rot && rot < 135) {
            return "East";
        } else if (135 <= rot && rot < 225) {
            return "South";
        } else if (225 <= rot && rot < 315) {
            return "West";
        } else if (315 <= rot && rot < 360.0) {
            return "North";
        } else {
            return null;
        }
    }


}
