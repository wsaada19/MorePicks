package me.williamsaada.MorePicks.commands;

import me.williamsaada.MorePicks.MorePicksUtility;
import me.williamsaada.MorePicks.PickAxeInformation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand extends SubCommand {

    String[] itemCodes = MorePicksUtility.getItemCodes();

    public void onCommand(Player player, String[] args) {

        if(args.length < 3){return;}

        if(!player.hasPermission("awesometools.give")){
            player.sendMessage("§cYou do not have permission to use this command!");
            return;
        }

        Player playerToSend = Bukkit.getServer().getPlayer(args[1]);

        if(playerToSend == null || !playerToSend.isOnline()){
            player.sendMessage("§cThe player + " + args[1] + " does not exist or is offline");
        }

        String itemName = args[2];
        ItemStack item = null;
        String displayName = "";

        if(itemName.equals("all"))
        {
            for(int i = 0; i < itemCodes.length; i++){
                if(!PickAxeInformation.getPick(i).getEnabled()){break;}

                playerToSend.getInventory().addItem(PickAxeInformation.getPick(i).getPick());
            }
            playerToSend.sendMessage(ChatColor.GREEN + "You have been awarded with all of our awesome tools!");
            return;
        }

        for(int i = 0; i < itemCodes.length; i++){
            if(itemName.equalsIgnoreCase(itemCodes[i])){
                item = PickAxeInformation.getPick(i).getPick();
                displayName = PickAxeInformation.getPick(i).getName();
                break;
            }
        }

        if(item == null){
            player.sendMessage(ChatColor.RED + "Invalid pick, " + itemName + " does not exist");
            return;
        }
        playerToSend.getInventory().addItem(item);
        playerToSend.sendMessage(ChatColor.GREEN + "You have been awarded with a " + displayName);
    }

    public String name() {
        return "give";
    }

    public String info() {
        return null;
    }

    public String[] aliases() {
        return new String[0];
    }
}
