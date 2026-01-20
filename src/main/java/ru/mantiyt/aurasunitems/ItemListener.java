package ru.mantiyt.aurasunitems;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListener implements Listener {
    private final AuraSunItems plugin;

    public ItemListener(AuraSunItems plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    }
}