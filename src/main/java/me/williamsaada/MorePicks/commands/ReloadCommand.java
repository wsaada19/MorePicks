package me.williamsaada.MorePicks.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class ReloadCommand extends SubCommand {

    public void onCommand(Player player, String[] args){

        if(!player.isOp()){
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return;
        }
    }

    public String name() {
        return "reload";
    }

    public String info() {
        return "The command" + ChatColor.YELLOW + " /mp [reload} will reload the configuration file";
    }

    public String[] aliases() {
        return new String[0];
    }
}
