package me.williamsaada.MorePicks.events;

import me.williamsaada.MorePicks.PickAxeInformation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Iterator;

public class LaserEvent implements Listener {

    public final int LASER_PICKAXE = 0;
    @EventHandler
    public void onClickEvent(PlayerInteractEvent e){

        Player player = e.getPlayer();

        if( e.getAction().equals(Action.LEFT_CLICK_AIR) &&  player.getInventory().getItemInMainHand().getType() != Material.AIR && player.getInventory().getItemInMainHand() != null &&
                        player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(PickAxeInformation.getPick(LASER_PICKAXE).getName()) &&
                PickAxeInformation.getPick(LASER_PICKAXE).getEnabled()){
            Snowball snowball = player.launchProjectile(Snowball.class);
            snowball.setCustomName(player.getName());
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){
        Entity entity = e.getEntity();
        if (!(entity instanceof Snowball)) { return; }
        Player p = Bukkit.getPlayer(entity.getCustomName());

        if(p == null){return;}
            Location loc = entity.getLocation();
            Vector vec = entity.getVelocity();
            Location loc2 = new Location(loc.getWorld(), loc.getX()+vec.getX(), loc.getY()+vec.getY(), loc.getZ()+vec.getZ());
            Block b = loc2.getBlock();

            Iterator<ItemStack> itemIterator = b.getDrops().iterator();
            while(itemIterator.hasNext()){
                p.getInventory().addItem(itemIterator.next());
            }
            b.breakNaturally();


    }

}
