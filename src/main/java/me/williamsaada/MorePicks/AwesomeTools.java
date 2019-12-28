package me.williamsaada.MorePicks;

import me.williamsaada.MorePicks.commands.PickCommands;
import me.williamsaada.MorePicks.events.*;
import me.williamsaada.MorePicks.treasurefind.LootItem;
import me.williamsaada.MorePicks.treasurefind.LootTable;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class AwesomeTools extends JavaPlugin {

    public static AwesomeTools awesomeTools;
    private PickCommands commands;
    public ShopGUI shopGui;
    public Economy economy;

    @Override
    public void onEnable() {

        commands = new PickCommands();
        commands.setup();

        loadConfig();

        loadPicks();
        MorePicksUtility.initialize();

        if(!setupEconomy()){
            getServer().getConsoleSender().sendMessage("All Economy Features are disabled! If you would like to use them " +
                    "you must have an economy plugin and vault on your server.");
        }

        registerEvents();

        awesomeTools = this;
        loadLootTable();


    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    public static AwesomeTools getInstance(){
        return awesomeTools;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerEvents()
    {
        getServer().getPluginManager().registerEvents(new MiningEvent(), this);
        getServer().getPluginManager().registerEvents(new WoodEvent(), this);
        getServer().getPluginManager().registerEvents(new LaserEvent(), this);
        //getServer().getPluginManager().registerEvents(new ClickShopEvent(), this);

        shopGui = new ShopGUI(economy);
        shopGui.initializeItems();
        getServer().getPluginManager().registerEvents(shopGui, this);
    }

    private void loadPicks(){
        ConfigurationSection tools = getConfig().getConfigurationSection("tools");
        String[] itemKeys = MorePicksUtility.getItemCodes();

        for(int i = 0; i < itemKeys.length; i++){
            ConfigurationSection sel = tools.getConfigurationSection(itemKeys[i]);
            createPick(sel, i);
        }
    }

    private void createPick(ConfigurationSection sel, int id){
        try {
            String name = sel.getString("name");
            String itemLore = sel.getString("itemLore");
            ArrayList<String> loreList = new ArrayList<String>();
            loreList.add(ChatColor.WHITE + itemLore);
            boolean enabled = sel.getBoolean("enabled");
            boolean unbreakable = sel.getBoolean("unbreakable");
            String materialName = sel.getString("material");
            Material material = Material.getMaterial(materialName);
            if(material == null){
                getServer().getConsoleSender().sendMessage(materialName + " doesn't exist");

                getServer().getConsoleSender().sendMessage("Disabling Awesome Tools due to an invalid material type configuration");
                this.onDisable();
            }
            int cost = sel.getInt("cost");

            PickAxeInformation.listOfPicks.add(new PickAxeInformation(name, loreList, unbreakable, enabled, material, cost));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadLootTable(){
        List<?> lootTable = getConfig().getList("drops");
        LootTable table = new LootTable(getConfig().getDouble("dropChance"));
        for(Object item : lootTable){
            LinkedHashMap<?, ?> map = (LinkedHashMap)item;
            LootItem lootItem = new LootItem((String)map.get("material"),
                    (Integer) map.get("min"), (Integer) map.get("max"), (Integer) map.get("weight"));
            table.addItem(lootItem);
        }
        getServer().getPluginManager().registerEvents(new TreasureFindEvent(table), this);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
            Bukkit.getServer().getConsoleSender().sendMessage(economy.currencyNameSingular());
        }
        return (economy != null);
    }

}
