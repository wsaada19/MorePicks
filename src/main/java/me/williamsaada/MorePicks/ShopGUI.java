package me.williamsaada.MorePicks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShopGUI implements InventoryHolder, Listener {

    private final Inventory inv;
    private Economy economy;
    public ShopGUI(Economy economy){

        inv = Bukkit.createInventory(this, 18, "Awesome Tools Shop");
        this.economy = economy;
    }

    public Inventory getInventory() {
        return inv;
    }

    public void initializeItems() {
        for(PickAxeInformation pick : PickAxeInformation.getListOfPicks()){
            ItemStack pickAxe = pick.getPick();
            ItemMeta meta = pickAxe.getItemMeta();
            List<String> lore = meta.getLore();
            lore.add(pick.getCostString());
            meta.setLore(lore);
            pickAxe.setItemMeta(meta);

            inv.addItem(pickAxe);
        }
    }

    public void openInventory(Player p) {
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getClickedInventory().getHolder().equals(this))) {
            return;
        }
        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if (e.getRawSlot() < PickAxeInformation.getListOfPicks().size()){
            PickAxeInformation pickAxe = PickAxeInformation.getListOfPicks().get(e.getRawSlot());
            if(economy == null)
            {
                e.getWhoClicked().getInventory().addItem(pickAxe.getPick());
                p.sendMessage(ChatColor.GREEN + "You've taken a " +
                        PickAxeInformation.getListOfPicks().get(e.getRawSlot()).getName() + " from the shop!" );
                p.closeInventory();
                return;
            } else {

                if(economy.getBalance(p) < (double)pickAxe.getCost()){
                    p.sendMessage(ChatColor.RED + "You can not afford this tool");
                    return;
                }
                economy.withdrawPlayer(p, (double)pickAxe.getCost());
                e.getWhoClicked().getInventory().addItem(pickAxe.getPick());
                p.sendMessage(ChatColor.GREEN + "You've purchased a " +
                        PickAxeInformation.getListOfPicks().get(e.getRawSlot()).getName() + " from the shop!" );
                p.closeInventory();
            }
        };
    }
}

