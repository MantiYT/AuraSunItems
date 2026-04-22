package ru.mantiyt.rideeggkeeper.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.mantiyt.rideeggkeeper.RideEggKeeper;
import java.util.Random;

public class Events implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void on(InventoryClickEvent event) {
        if (event.getInventory().getHolder() != MenuHolder.INSTANCE) return;
        event.setCancelled(true);
        if (event.getClickedInventory() != event.getInventory()) return;

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 10:
                processEnderEgg(player);
                break;
            case 12:
                if (hasRequiredEggs(player, 16)) {
                    removeEggs(player, 16);
                    giveRandomEgg16(player);
                }
                break;
            case 14:
                if (hasRequiredEggs(player, 48)) {
                    removeEggs(player, 48);
                    giveRandomEgg48(player);
                }
                break;
            case 16:
                if (hasRequiredEggs(player, 64)) {
                    removeEggs(player, 64);
                    giveRandomEgg64(player);
                }
                break;
        }
    }

    private void processEnderEgg(Player player) {
        ItemStack enderEgg = findEnderEgg(player);
        if (enderEgg == null) return;

        if (enderEgg.getAmount() > 1) {
            enderEgg.setAmount(enderEgg.getAmount() - 1);
        } else {
            player.getInventory().removeItem(enderEgg);
        }

        if (random.nextDouble() <= 0.0005) {
            String command = RideEggKeeper.getInstance().getConfig().getString("ender-egg-command");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
        }
    }

    private ItemStack findEnderEgg(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.ENDERMAN_SPAWN_EGG) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getPersistentDataContainer().has(RideEggKeeper.getEnderEggKey(), PersistentDataType.INTEGER)) {
                    return item;
                }
            }
        }
        return null;
    }

    private boolean hasRequiredEggs(Player player, int requiredAmount) {
        int found = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.PUFFERFISH_SPAWN_EGG) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getPersistentDataContainer().has(RideEggKeeper.getOskolEggKey(), PersistentDataType.INTEGER)) {
                    found += item.getAmount();
                    if (found >= requiredAmount) return true;
                }
            }
        }
        return false;
    }

    private void removeEggs(Player player, int amountToRemove) {
        int remaining = amountToRemove;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.PUFFERFISH_SPAWN_EGG) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.getPersistentDataContainer().has(RideEggKeeper.getOskolEggKey(), PersistentDataType.INTEGER)) {
                    int amount = item.getAmount();
                    if (amount <= remaining) {
                        player.getInventory().removeItem(item);
                        remaining -= amount;
                    } else {
                        item.setAmount(amount - remaining);
                        return;
                    }
                    if (remaining == 0) return;
                }
            }
        }
    }

    private void giveRandomEgg16(Player player) {
        Material[] eggs = {
                Material.BAT_SPAWN_EGG, Material.CAT_SPAWN_EGG, Material.CHICKEN_SPAWN_EGG,
                Material.COD_SPAWN_EGG, Material.COW_SPAWN_EGG, Material.FOX_SPAWN_EGG,
                Material.HORSE_SPAWN_EGG, Material.MULE_SPAWN_EGG, Material.OCELOT_SPAWN_EGG,
                Material.PARROT_SPAWN_EGG, Material.PIG_SPAWN_EGG, Material.PUFFERFISH_SPAWN_EGG,
                Material.RABBIT_SPAWN_EGG, Material.SALMON_SPAWN_EGG, Material.SHEEP_SPAWN_EGG,
                Material.TROPICAL_FISH_SPAWN_EGG, Material.POLAR_BEAR_SPAWN_EGG
        };
        player.getInventory().addItem(new ItemStack(eggs[random.nextInt(eggs.length)], 1));
    }

    private void giveRandomEgg48(Player player) {
        Material[] eggs = {
                Material.BLAZE_SPAWN_EGG, Material.CAVE_SPIDER_SPAWN_EGG, Material.CREEPER_SPAWN_EGG,
                Material.ENDERMAN_SPAWN_EGG, Material.EVOKER_SPAWN_EGG, Material.MAGMA_CUBE_SPAWN_EGG,
                Material.PHANTOM_SPAWN_EGG, Material.PIGLIN_BRUTE_SPAWN_EGG, Material.SHULKER_SPAWN_EGG,
                Material.SKELETON_SPAWN_EGG, Material.SPIDER_SPAWN_EGG, Material.VINDICATOR_SPAWN_EGG,
                Material.WITCH_SPAWN_EGG, Material.WITHER_SKELETON_SPAWN_EGG, Material.ZOMBIE_SPAWN_EGG
        };
        player.getInventory().addItem(new ItemStack(eggs[random.nextInt(eggs.length)], 1));
    }

    private void giveRandomEgg64(Player player) {
        Material[] eggs = {
                Material.BAT_SPAWN_EGG, Material.BEE_SPAWN_EGG, Material.BLAZE_SPAWN_EGG,
                Material.CAT_SPAWN_EGG, Material.CAVE_SPIDER_SPAWN_EGG, Material.CHICKEN_SPAWN_EGG,
                Material.COD_SPAWN_EGG, Material.COW_SPAWN_EGG, Material.CREEPER_SPAWN_EGG,
                Material.DOLPHIN_SPAWN_EGG, Material.DONKEY_SPAWN_EGG, Material.DROWNED_SPAWN_EGG,
                Material.ELDER_GUARDIAN_SPAWN_EGG, Material.ENDERMAN_SPAWN_EGG, Material.ENDERMITE_SPAWN_EGG,
                Material.EVOKER_SPAWN_EGG, Material.FOX_SPAWN_EGG, Material.GHAST_SPAWN_EGG,
                Material.GUARDIAN_SPAWN_EGG, Material.HOGLIN_SPAWN_EGG, Material.HORSE_SPAWN_EGG,
                Material.HUSK_SPAWN_EGG, Material.LLAMA_SPAWN_EGG, Material.MAGMA_CUBE_SPAWN_EGG,
                Material.MOOSHROOM_SPAWN_EGG, Material.MULE_SPAWN_EGG, Material.OCELOT_SPAWN_EGG,
                Material.PANDA_SPAWN_EGG, Material.PARROT_SPAWN_EGG, Material.PHANTOM_SPAWN_EGG,
                Material.PIG_SPAWN_EGG, Material.PIGLIN_SPAWN_EGG, Material.PIGLIN_BRUTE_SPAWN_EGG,
                Material.PILLAGER_SPAWN_EGG, Material.POLAR_BEAR_SPAWN_EGG, Material.PUFFERFISH_SPAWN_EGG,
                Material.RABBIT_SPAWN_EGG, Material.RAVAGER_SPAWN_EGG, Material.SALMON_SPAWN_EGG,
                Material.SHEEP_SPAWN_EGG, Material.SHULKER_SPAWN_EGG, Material.SILVERFISH_SPAWN_EGG,
                Material.SKELETON_SPAWN_EGG, Material.SKELETON_HORSE_SPAWN_EGG, Material.SLIME_SPAWN_EGG,
                Material.SPIDER_SPAWN_EGG, Material.SQUID_SPAWN_EGG, Material.STRAY_SPAWN_EGG,
                Material.STRIDER_SPAWN_EGG, Material.TRADER_LLAMA_SPAWN_EGG, Material.TROPICAL_FISH_SPAWN_EGG,
                Material.TURTLE_SPAWN_EGG, Material.VEX_SPAWN_EGG, Material.VILLAGER_SPAWN_EGG,
                Material.VINDICATOR_SPAWN_EGG, Material.WANDERING_TRADER_SPAWN_EGG, Material.WITCH_SPAWN_EGG,
                Material.WITHER_SKELETON_SPAWN_EGG, Material.WOLF_SPAWN_EGG, Material.ZOGLIN_SPAWN_EGG,
                Material.ZOMBIE_SPAWN_EGG, Material.ZOMBIE_HORSE_SPAWN_EGG, Material.ZOMBIE_VILLAGER_SPAWN_EGG,
                Material.ZOMBIFIED_PIGLIN_SPAWN_EGG
        };
        player.getInventory().addItem(new ItemStack(eggs[random.nextInt(eggs.length)], 1));
    }
}