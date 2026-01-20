package ru.mantiyt.aurasunitems;

import org.bukkit.plugin.java.JavaPlugin;

public final class AuraSunItems extends JavaPlugin {
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        itemManager = new ItemManager(this);
        getCommand("aurasunitems").setExecutor(new ItemCommand(this));
        getServer().getPluginManager().registerEvents(new ItemListener(this), this);
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}