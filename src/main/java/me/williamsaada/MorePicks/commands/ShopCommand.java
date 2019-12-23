package me.williamsaada.MorePicks.commands;

import me.williamsaada.MorePicks.AwesomeTools;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopCommand extends SubCommand {

    public void onCommand(Player player, String[] args) {

        if(!player.hasPermission("awesometools.shop.use")) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }

        JavaPlugin.getPlugin(AwesomeTools.class).shopGui.openInventory(player);
    }

    public String name() {
        return null;
    }

    public String info() {
        return null;
    }

    public String[] aliases() {
        return new String[0];
    }
}
