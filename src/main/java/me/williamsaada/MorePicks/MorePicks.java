package me.williamsaada.MorePicks;

import me.williamsaada.MorePicks.commands.PickCommands;
import me.williamsaada.MorePicks.events.LaserEvent;
import me.williamsaada.MorePicks.events.MiningEvent;
import me.williamsaada.MorePicks.events.WoodEvent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class MorePicks extends JavaPlugin {

    public static MorePicks morePicks;
    private PickCommands commands;

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        getServer().getPluginManager().registerEvents(new MiningEvent(), this);
        getServer().getPluginManager().registerEvents(new WoodEvent(), this);
        getServer().getPluginManager().registerEvents(new LaserEvent(), this);
        commands = new PickCommands();
        commands.setup();
        loadConfig();
        loadPicks();
        MorePicksUtility.initialize();
        morePicks = this;

    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    public static MorePicks getInstance(){
        return morePicks;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadPicks(){
        ConfigurationSection tools = getConfig().getConfigurationSection("tools");
        String[] itemKeys = MorePicksUtility.getItemCodes();
        Material material;

        for(int i = 0; i < itemKeys.length; i++){
            ConfigurationSection sel = tools.getConfigurationSection(itemKeys[i]);
            if(itemKeys[i].contains("Pickaxe")){
                material = Material.DIAMOND_PICKAXE;
            } else {
                material = Material.DIAMOND_AXE;
            }
            createPick(sel, i, material);
        }
    }

    private void createPick(ConfigurationSection sel, int id, Material mat){
        try {
            String name = sel.getString("name");
            String itemLore = sel.getString("itemLore");
            ArrayList<String> loreList = new ArrayList<String>();
            loreList.add(itemLore);
            boolean enabled = sel.getBoolean("enabled");
            boolean unbreakable = sel.getBoolean("unbreakable");
            PickAxeInformation.listOfPicks.add(new PickAxeInformation(name, loreList, unbreakable, enabled, mat));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadLootTable(){
        List<?> lootTable = getConfig().getList("lootTable");
        for(Object item : lootTable){

        }
    }

}
