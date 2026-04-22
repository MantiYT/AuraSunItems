package ru.mantiyt.rideeggkeeper.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.mantiyt.rideeggkeeper.RideEggKeeper;

public class Event implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        if (isEgg(mainHand)) {
            event.setCancelled(true);
            return;
        }

        ItemStack offHand = player.getInventory().getItemInOffHand();
        if (isEgg(offHand)) {
            event.setCancelled(true);
        }
    }

    private boolean isEgg(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        return meta.getPersistentDataContainer().has(RideEggKeeper.getOskolEggKey(), PersistentDataType.INTEGER) ||
                meta.getPersistentDataContainer().has(RideEggKeeper.getEnderEggKey(), PersistentDataType.INTEGER);
    }
}