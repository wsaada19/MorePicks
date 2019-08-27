package me.williamsaada.MorePicks.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {

    public void onCommand(Player player, String[] args){

        player.sendMessage(ChatColor.BLUE + "More Picks Commands \n" +
                            ChatColor.YELLOW + "/mp give <player name> <tool name>: " + ChatColor.WHITE + "Gives the named player the named tool \n" +
                            ChatColor.YELLOW + "/mp reload: " + ChatColor.WHITE + "Reloads the plugin configuration file \n" +
                            ChatColor.YELLOW + "/mp help: " + ChatColor.WHITE + "Shows a list of all commands");
    }

    public String name() {
        return "help";
    }

    public String info() {
        return "The command" + ChatColor.YELLOW + " /mp [help} will give information on the More Picks plugin";
    }

    public String[] aliases() {
        return new String[0];
    }
}
