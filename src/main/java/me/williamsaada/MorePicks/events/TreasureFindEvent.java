package me.williamsaada.MorePicks.events;

import me.williamsaada.MorePicks.PickAxeInformation;
import me.williamsaada.MorePicks.treasurefind.LootTable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class TreasureFindEvent implements Listener {

    private static final int TREASURE_PICKAXE = 9;
    private static final int TREASURE_AXE = 10;
    private static final int TREASURE_FISHINGROD = 11;

    private LootTable lootTable;
    public TreasureFindEvent(LootTable table){
        lootTable = table;
    }

    @EventHandler
    public void onUseTreasureTool(BlockBreakEvent event){

        if(!event.getBlock().getType().isSolid()){
            return;
        }
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(item.getType() == Material.AIR){return;}

        if(!PickAxeInformation.IsThisAMorePicksTool(item.getItemMeta().getDisplayName()))
        {
            return;
        }

        if((item.getItemMeta().getDisplayName().equals(PickAxeInformation.getPick(TREASURE_PICKAXE).getName()) &&
        PickAxeInformation.getPick(TREASURE_PICKAXE).getEnabled()) ||
                (item.getItemMeta().getDisplayName().equals(PickAxeInformation.getPick(TREASURE_AXE).getName()) &&
                        PickAxeInformation.getPick(TREASURE_AXE).getEnabled()))
        {

            // Check permission to use a custom tool
            if(!event.getPlayer().hasPermission("awesometools.use")){
                event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to use this tool");
                return;
            }

            callLootTable(event.getPlayer());
        }
    }
    @EventHandler
    public void onCatchFish(PlayerFishEvent event){

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH &&
        item.getItemMeta().getDisplayName().equals(PickAxeInformation.getPick(TREASURE_FISHINGROD).getName()) &&
        PickAxeInformation.getPick(TREASURE_FISHINGROD).getEnabled()){

            if(!event.getPlayer().hasPermission("awesometools.use")){
                event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to use this tool");
                return;
            }

            callLootTable(event.getPlayer());
        }
    }

    private void callLootTable(Player player){
        if(lootTable.willDropItem())
        {
            ItemStack item = lootTable.pullFromLootTable();
            player.getInventory().addItem(item);
            player.sendMessage(ChatColor.GREEN + "Your " + player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() +
                    " has awarded you with " + item.getAmount() + " " + item.getType().toString());
        }
    }
}
