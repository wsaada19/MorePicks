package me.williamsaada.MorePicks.events;

import me.williamsaada.MorePicks.PickAxeInformation;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class WoodEvent implements Listener {

    public static int MEGA_AXE = 6;

    @EventHandler
    public void onBreakWood(BlockBreakEvent e){

        Block b = e.getBlock();
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if(b.getType().toString().contains("LOG") &&
                item.getItemMeta().getDisplayName().equalsIgnoreCase(PickAxeInformation.getPick(MEGA_AXE).getName())){

            Block block = b.getRelative(0, 1, 0);

            while(block.getType().toString().contains("LOG")){
                block.breakNaturally();
                block = block.getRelative(0, 1, 0);
            }

        }

    }

    private void recursiveBreakBlock(Block block){

    }

}
