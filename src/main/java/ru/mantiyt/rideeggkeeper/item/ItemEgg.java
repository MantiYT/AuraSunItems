package ru.mantiyt.rideeggkeeper.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.mantiyt.rideeggkeeper.RideEggKeeper;
import java.util.ArrayList;
import java.util.List;

public class ItemEgg {

    public ItemStack getOskolEgg(int amount) {
        FileConfiguration config = RideEggKeeper.getInstance().getConfig();

        String materialName = config.getString("oskol-egg.material", "PUFFERFISH_SPAWN_EGG");
        Material material = Material.getMaterial(materialName);
        if (material == null) material = Material.PUFFERFISH_SPAWN_EGG;

        ItemStack item = new ItemStack(material, Math.min(amount, 64));
        ItemMeta meta = item.getItemMeta();

        String name = config.getString("oskol-egg.name");
        if (name != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }

        List<String> lore = new ArrayList<>();
        for (String line : config.getStringList("oskol-egg.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(RideEggKeeper.getOskolEggKey(), PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getEnderEgg(int amount) {
        FileConfiguration config = RideEggKeeper.getInstance().getConfig();

        String materialName = config.getString("ender-egg.material", "ENDERMAN_SPAWN_EGG");
        Material material = Material.getMaterial(materialName);
        if (material == null) material = Material.ENDERMAN_SPAWN_EGG;

        ItemStack item = new ItemStack(material, Math.min(amount, 64));
        ItemMeta meta = item.getItemMeta();

        String name = config.getString("ender-egg.name");
        if (name != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }

        List<String> lore = new ArrayList<>();
        for (String line : config.getStringList("ender-egg.lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.getPersistentDataContainer().set(RideEggKeeper.getEnderEggKey(), PersistentDataType.INTEGER, 1);

        item.setItemMeta(meta);
        return item;
    }
}