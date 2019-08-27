package me.williamsaada.MorePicks.commands;

import me.williamsaada.MorePicks.MorePicks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PickCommands implements CommandExecutor {

    private MorePicks plugin = JavaPlugin.getPlugin(MorePicks.class);
    private HashMap<String, SubCommand> subCommands;
    private final String main = "mp";

    public void setup(){

        plugin.getCommand(main).setExecutor(this);
        subCommands = new HashMap<String, SubCommand>();
        subCommands.put("give", new GiveCommand());
        subCommands.put("reload", new ReloadCommand());
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){return false;}
        Player player = (Player)sender;
        if(args.length < 1){
            player.sendMessage("Please enter another command /" + main + " [give, reload, help]");
            return false;
        }
        String sub = args[0];

        if(subCommands.containsKey(sub)){
            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);
            subCommands.get(sub).onCommand(player, args);
        } else {
            player.sendMessage("The subcommand " + sub + " does not exist!");
        }

        return false;
    }
}
