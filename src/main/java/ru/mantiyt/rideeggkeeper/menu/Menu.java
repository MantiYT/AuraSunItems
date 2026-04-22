package ru.mantiyt.rideeggkeeper.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    public void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(MenuHolder.INSTANCE, 27, ChatColor.translateAlternateColorCodes('&', "&5&lХранитель яиц"));

        inventory.setItem(10, createMenuItem(Material.ENDERMAN_SPAWN_EGG,
                "&5 &x&F&B&0&F&D&4▶&f Обменять &x&F&B&0&F&D&41&f Энд Осколочных Яиц",
                "&5  &f  на шанс получить &x&F&B&0&F&D&4Яйцо дракона",
                "&5  &7  (шанс выпадения: 0,1%).",
                "&5&o "));

        inventory.setItem(12, createMenuItem(Material.PUFFERFISH_SPAWN_EGG,
                "&5 &x&C&8&A&E&2&2▶&f Обменять &x&C&8&A&E&2&216&f Осколочных яиц",
                "&5  &f  на &x&C&8&A&E&2&21&f Случайное яйца призыва",
                "&5&o ",
                "&x&C&8&A&E&2&2Может выпасть:",
                "&x&C&8&A&E&2&2&l&n▎&f Пчела, Векс, &x&5&2&B&3&0&0Слайм&f, Лама",
                "&x&C&8&A&E&2&2&l&n▎&f Корова, Свинья, Паук, Зомби",
                "&x&C&8&A&E&2&2&l&n▎&f Хоглин, Панда, Пиглин, Скелет",
                "&x&C&8&A&E&2&2&l&n▎&f &x&A&E&8&3&E&EФантом&f, Лошадь, Черепаха, Житель",
                "&x&C&8&A&E&2&2&l&n▎&f Эндермен, Утопленник, Пещерный паук",
                "&x&C&8&A&E&2&2&l▎&F &x&C&8&A&E&2&2Брутальный пиглин"));

        inventory.setItem(14, createMenuItem(Material.PUFFERFISH_SPAWN_EGG,
                "&5 &x&C&8&A&E&2&2▶&f Обменять &x&C&8&A&E&2&248&f Осколочных яиц",
                "&5  &f  на &x&C&8&A&E&2&21&f Случайное яйца призыва",
                "&5&o ",
                "&x&C&8&A&E&2&2Может выпасть:",
                "&x&C&8&A&E&2&2&l&n▎&f Свинья, Паук, Зомби, &x&5&2&B&3&0&0Слайм",
                "&x&C&8&A&E&2&2&l&n▎&f Хоглин, Зоглин, Скелет, Пиглин",
                "&x&C&8&A&E&2&2&l&n▎&f &x&A&E&8&3&E&EФантом&f, Житель, Разбойник, Утопленник",
                "&x&C&8&A&E&2&2&l&n▎&f Поборник, Эндермен, Магма-куб",
                "&x&C&8&A&E&2&2&l&n▎&f Визер-скелет, Зомби-пиглин",
                "&x&C&8&A&E&2&2&l▎&F &x&C&8&A&E&2&2Брутальный пиглин"));

        inventory.setItem(16, createMenuItem(Material.PUFFERFISH_SPAWN_EGG,
                "&5 &x&C&8&A&E&2&2▶&f Обменять &x&C&8&A&E&2&264&f Осколочных яиц",
                "&5  &f  на &x&C&8&A&E&2&21&f Случайное яйца призыва",
                "&5&o ",
                "&x&C&8&A&E&2&2Может выпасть:",
                "&x&C&8&A&E&2&2&l&n&l▎&f Ифрит, Гаст, &x&5&2&B&3&0&0Слайм&f, Пиглин",
                "&x&C&8&A&E&2&2&l&n&l▎&f Свинья, Зомби, Скелет, Шалкер",
                "&x&C&8&A&E&2&2&l&n&l▎&f Житель, Разбойник, Поборник",
                "&x&C&8&A&E&2&2&l&n&l▎&f Эндермен, Утопленник, Магма-куб",
                "&x&C&8&A&E&2&2&l&n&l▎&f Визер-скелет, Зомби-пиглин",
                "&x&C&8&A&E&2&2&l▎&F &x&C&8&A&E&2&2Брутальный пиглин"));

        player.openInventory(inventory);
    }

    private ItemStack createMenuItem(Material material, String... lines) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', " "));

        List<String> lore = new ArrayList<>();
        for (String line : lines) {
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        meta.setLore(lore);

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return item;
    }
}