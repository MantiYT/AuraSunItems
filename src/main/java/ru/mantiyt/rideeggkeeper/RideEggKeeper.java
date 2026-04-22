package ru.mantiyt.rideeggkeeper;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mantiyt.rideeggkeeper.commands.EggCommand;
import ru.mantiyt.rideeggkeeper.item.ItemEgg;
import ru.mantiyt.rideeggkeeper.menu.Events;
import ru.mantiyt.rideeggkeeper.menu.Menu;
import ru.mantiyt.rideeggkeeper.utils.Event;

public final class RideEggKeeper extends JavaPlugin {
    private static RideEggKeeper instance;
    private static NamespacedKey oskolEggKey;
    private static NamespacedKey enderEggKey;
    private ItemEgg itemEgg;
    private Menu menu;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        oskolEggKey = new NamespacedKey(this, "oskol");
        enderEggKey = new NamespacedKey(this, "ender");
        itemEgg = new ItemEgg();
        menu = new Menu();

        Bukkit.getPluginManager().registerEvents(new Event(), this);
        Bukkit.getPluginManager().registerEvents(new Events(), this);

        EggCommand executor = new EggCommand();
        getCommand("rideeggkeeper").setExecutor(executor);
        getCommand("rideeggkeeper").setTabCompleter(executor);
        getCommand("eggkeeper").setExecutor(executor);
    }

    public static NamespacedKey getOskolEggKey() { return oskolEggKey; }
    public static NamespacedKey getEnderEggKey() { return enderEggKey; }
    public static RideEggKeeper getInstance() { return instance; }
    public ItemEgg getItemEgg() { return itemEgg; }
    public Menu getMenu() { return menu; }
}