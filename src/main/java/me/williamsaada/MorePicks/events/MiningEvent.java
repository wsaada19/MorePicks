package me.williamsaada.MorePicks.events;

import me.williamsaada.MorePicks.AwesomeTools;
import me.williamsaada.MorePicks.MorePicksUtility;
import me.williamsaada.MorePicks.PickAxeInformation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

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
    private static final int DIRT_DESTROYER = 8;
    private static final String BLOCK_KEY = "IS_PLACED";

    private ArrayList<PickAxeInformation> allTools = PickAxeInformation.getListOfPicks();

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e)
    {
        if(MorePicksUtility.isMaterialOre(e.getBlockPlaced().toString())){
            MetadataValue meta = new FixedMetadataValue(AwesomeTools.getInstance(), true);
            e.getBlock().setMetadata(BLOCK_KEY, meta);
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){

        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        String itemName = item.getItemMeta().getDisplayName();

        if(e.getBlock().hasMetadata(BLOCK_KEY)){return;}

        if(!PickAxeInformation.IsThisAMorePicksTool(itemName)){return;}

        // Check permission to use a custom tool
        if(!e.getPlayer().hasPermission("morepicks.use")){
            e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to use this tool");
        }

        // Runs when player uses a smelting pickaxe
        if(itemName.equals(allTools.get(SMELTING_PICKAXE).getName()) &&
                allTools.get(SMELTING_PICKAXE).getEnabled()){
            handleSmeltingPick(e);
            return;
        }
        // Experience pickaxe
        if(itemName.equals(allTools.get(EXP_PICKAXE).getName()) &&
                allTools.get(EXP_PICKAXE).getEnabled()){
            handleExperiencePick(e);
            return;
        }
        //Magnetic Pickaxe
        if(itemName.equals(allTools.get(MAGNETIC_PICKAXE).getName()) &&
                allTools.get(MAGNETIC_PICKAXE).getEnabled()){
            handleMagneticPick(e);
            return;
        }

        if(itemName.equals(allTools.get(DIRT_DESTROYER).getName()) &&
                allTools.get(DIRT_DESTROYER).getEnabled()){
            handleDirtDestroyer(e);
            return;
        }
        Block b = e.getBlock();

        /*
            Bountiful Pickaxe
         */
        if(itemName.equals(allTools.get(BOUNTIFUL_PICKAXE).getName()) &&
                allTools.get(BOUNTIFUL_PICKAXE).getEnabled()){

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
        if(itemName.equals(allTools.get(EXPLOSIVE_PICKAXE).getName()) &&
                allTools.get(EXPLOSIVE_PICKAXE).getEnabled()){
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
           currentBlock = getRelativeBlock(dir, currentBlock);
            currentBlock.breakNaturally();
            num = random.nextDouble();
        }
    }

    private void handleDirtDestroyer(BlockBreakEvent e){
        int maxDistance = 10;

        if(e.getBlock().getType() != Material.DIRT && e.getBlock().getType() != Material.GRASS_BLOCK){
            return;
        }

        String direction = MorePicksUtility.getCardinalDirection(e.getPlayer());
        Block currentBlock = getRelativeBlock(direction, e.getBlock());
        for(int i = 0; i < maxDistance; i++){
            if(currentBlock.getType() != Material.DIRT && e.getBlock().getType() != Material.GRASS_BLOCK){
                continue;
            }
            currentBlock.breakNaturally();

            currentBlock = getRelativeBlock(direction, currentBlock);

        }
    }

    private static Block getRelativeBlock(String direction, Block block){
        if(direction.equals("North")){
            return block.getRelative(-1, 0, 0);
        } else if(direction.equals("South")){
            return block.getRelative(1, 0, 0);
        } else if(direction.equals("East")){
            return block.getRelative(0, 0, -1);
        } else {
            return block.getRelative(0, 0, 1);
        }
    }
}
