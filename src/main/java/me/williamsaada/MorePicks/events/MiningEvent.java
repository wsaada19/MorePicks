package me.williamsaada.MorePicks.events;

import me.williamsaada.MorePicks.MorePicksUtility;
import me.williamsaada.MorePicks.PickAxeInformation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class MiningEvent implements Listener {

    private static final int EXP_PICKAXE = 1;
    private static final int SMELTING_PICKAXE = 2;
    private static final int MAGNETIC_PICKAXE = 3;
    private static final int BOUNTIFUL_PICKAXE = 4;
    private static final int EXPLOSIVE_PICKAXE = 5;
    private static final int PIERCING_PICKAXE = 7;

    private ArrayList<PickAxeInformation> allTools = PickAxeInformation.getListOfPicks();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){

        // Check permission to use a custom tool
        if(!e.getPlayer().hasPermission("morepicks.use")){
            e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to use this tool");
        }

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        String itemName = item.getItemMeta().getDisplayName();

        // Runs when player uses a smelting pickaxe
        if(itemName.equals(allTools.get(SMELTING_PICKAXE).getName())){
            handleSmeltingPick(e);
            return;
        }
        // Experience pickaxe
        if(itemName.equals(allTools.get(EXP_PICKAXE).getName())){
            handleExperiencePick(e);
            return;
        }
        //Magnetic Pickaxe
        if(itemName.equals(allTools.get(MAGNETIC_PICKAXE).getName())){
            handleMagneticPick(e);
            return;
        }
        Block b = e.getBlock();

        /*
            Bountiful Pickaxe
         */
        if(itemName.equals(allTools.get(BOUNTIFUL_PICKAXE).getName())){

            if(b.getType().toString().contains("ORE")){return;}
            Material highestRank = null;
            Collection<ItemStack> drops = null;

            // Loop through each block in the 3x3 area around a block (make radius custom in the future
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    for(int k = 0; k < 3; k++){
                        Block currentBlock = e.getBlock().getRelative(1 - i, 1 - j ,1 - k );
                        if(currentBlock.getType().toString().contains("ORE")) if (highestRank == null) {
                            highestRank = currentBlock.getType();
                            drops = currentBlock.getDrops();
                        } else {
                            if(MorePicksUtility.returnHighestRankedOre(highestRank, currentBlock.getType()) != highestRank){
                                highestRank = currentBlock.getType();
                                drops = currentBlock.getDrops();
                            }
                        }
                    }
                }

            }
            if(highestRank != null && drops != null){
                b.getWorld().dropItemNaturally(b.getLocation(), drops.iterator().next());
                e.setDropItems(false);
            }
            return;
        }

        /*
            Explosive Pickaxe
         */
        if(itemName.equals(allTools.get(EXPLOSIVE_PICKAXE).getName())){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    for(int k = 0; k < 3; k++){
                        Block currentBlock = e.getBlock().getRelative(1 - i, 1 - j ,1 - k );
                        currentBlock.breakNaturally();
                        }
                    }
                }
        }
        /*
            Piercing Pickaxe
         */
        if(itemName.equals(allTools.get(PIERCING_PICKAXE).getName())){
            handlePiercingPickaxe(e);
        }
    }

    private void handleExperiencePick(BlockBreakEvent e){
        int newExp = e.getExpToDrop() * 3; // MAKE CUSTOM
        e.setExpToDrop(newExp);
    }

    private void handleSmeltingPick(BlockBreakEvent e){
        if(MorePicksUtility.canSmeltMaterial(e.getBlock().getType().toString())){
            e.setDropItems(false);
            ItemStack newDrop = new ItemStack(MorePicksUtility.getSmeltingMaterial(e.getBlock().getType().toString()));
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), newDrop);
        }
    }

    private void handleMagneticPick(BlockBreakEvent e){
        Collection<ItemStack> drops = e.getBlock().getDrops();
        e.setDropItems(false);
        Iterator<ItemStack> iterator = drops.iterator();
        Inventory pInventory = e.getPlayer().getInventory();

        while(iterator.hasNext()){
            pInventory.addItem(iterator.next());
        }
    }

    private void handlePiercingPickaxe(BlockBreakEvent e){
        Random random = new Random();
        double num = random.nextDouble();
        Block currentBlock = e.getBlock();
        String dir = MorePicksUtility.getCardinalDirection(e.getPlayer());

        while(num < .5){
            if(dir.equals("North")){
                currentBlock = currentBlock.getRelative(-1, 0, 0);
            } else if(dir.equals("South")){
                currentBlock = currentBlock.getRelative(1, 0, 0);
            } else if(dir.equals("East")){
                currentBlock = currentBlock.getRelative(0, 0, -1);
            } else {
                currentBlock = currentBlock.getRelative(0, 0, 1);
            }
            currentBlock.breakNaturally();
            num = random.nextDouble();
        }
    }
}