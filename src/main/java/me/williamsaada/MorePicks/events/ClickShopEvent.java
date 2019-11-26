package me.williamsaada.MorePicks.events;

import me.williamsaada.MorePicks.ShopGUI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ClickShopEvent implements Listener {

    @EventHandler
    public void onClickShop(PlayerInteractEntityEvent event){

        if(event.getRightClicked().getType() == EntityType.VILLAGER){

            Villager villager = (Villager)event.getRightClicked();

            if(villager.getName().equals("Awesome Tools Shop")){
                event.setCancelled(true);
                ShopGUI shopGUI = new ShopGUI();
                shopGUI.initializeItems();
                shopGUI.openInventory(event.getPlayer());
            }
        }

    }

}
