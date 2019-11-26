package me.williamsaada.MorePicks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ShopGUI implements InventoryHolder, Listener {

    private final Inventory inv;
    public ShopGUI(){
        inv = Bukkit.createInventory(this, 18, "Awesome Tools Shop");
    }

    public Inventory getInventory() {
        return inv;
    }

    public void initializeItems() {
        for(PickAxeInformation picks : PickAxeInformation.getListOfPicks()){
            inv.addItem(picks.getPick());
        }
    }

    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(inv.getName().equals(e.getClickedInventory().getName()))) {
            return;
        }
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        // Using slots click is a best option for your inventory click's
        if (e.getRawSlot() < PickAxeInformation.getListOfPicks().size()){
            e.getWhoClicked().getInventory().addItem(PickAxeInformation.getListOfPicks().get(e.getRawSlot()).getPick());
            p.sendMessage(ChatColor.GREEN + "You've taken a " +
                    PickAxeInformation.getListOfPicks().get(e.getRawSlot()).getName() + " from the shop!" );
            return;

        };
    }
}

