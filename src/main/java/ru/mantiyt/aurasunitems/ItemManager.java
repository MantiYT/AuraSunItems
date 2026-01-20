package ru.mantiyt.aurasunitems;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.stream.Collectors;

public class ItemManager {

    private final AuraSunItems plugin;
    private final NamespacedKey helmetKey;
    private final NamespacedKey bootsKey;

    private ItemStack sunHelmet;
    private ItemStack sunBoots;

    public ItemManager(AuraSunItems plugin) {
        this.plugin = plugin;
        this.helmetKey = new NamespacedKey(plugin, "sun_helmet");
        this.bootsKey = new NamespacedKey(plugin, "sun_boots");
        reload();
    }

    public void reload() {
        FileConfiguration config = plugin.getConfig();
        loadHelmet(config);
        loadBoots(config);
    }

    private void loadHelmet(FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("helmet");
        if (section == null) {
            return;
        }

        sunHelmet = createItem(section, EquipmentSlot.HEAD, helmetKey);
    }

    public ItemStack getSunHelmet() {
        return sunHelmet != null ? sunHelmet.clone() : null;
    }

    public boolean isSunHelmet(ItemStack item) {
        return hasCustomKey(item, helmetKey);
    }

    private void loadBoots(FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("boots");
        if (section == null) {
            return;
        }

        sunBoots = createItem(section, EquipmentSlot.FEET, bootsKey);
    }

    public ItemStack getSunBoots() {
        return sunBoots != null ? sunBoots.clone() : null;
    }

    public boolean isSunBoots(ItemStack item) {
        return hasCustomKey(item, bootsKey);
    }

    private ItemStack createItem(ConfigurationSection section, EquipmentSlot slot, NamespacedKey key) {
        Material material = parseMaterial(section);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        applyDisplayName(meta, section);
        applyLore(meta, section);
        applyCustomModelData(meta, section);
        applyAttributes(meta, section, slot);
        applyEnchantments(meta, section);
        applyUnbreakable(meta, section);
        applyPersistentData(meta, key);
        applyItemFlags(meta);

        item.setItemMeta(meta);
        return item;
    }

    private Material parseMaterial(ConfigurationSection section) {
        String materialName = section.getString("material");
        try {
            return Material.valueOf(materialName);
        } catch (IllegalArgumentException e) {
            return Material.NETHERITE_HELMET;
        }
    }

    private void applyDisplayName(ItemMeta meta, ConfigurationSection section) {
        String displayName = section.getString("display-name");
        meta.setDisplayName(ColorUtil.translate(displayName));
    }

    private void applyLore(ItemMeta meta, ConfigurationSection section) {
        List<String> lore = section.getStringList("lore").stream()
                .map(ColorUtil::translate)
                .collect(Collectors.toList());
        meta.setLore(lore);
    }

    private void applyCustomModelData(ItemMeta meta, ConfigurationSection section) {
        if (section.contains("custom-model-data")) {
            meta.setCustomModelData(section.getInt("custom-model-data"));
        }
    }

    private void applyAttributes(ItemMeta meta, ConfigurationSection section, EquipmentSlot slot) {
        ConfigurationSection attributes = section.getConfigurationSection("attributes");
        if (attributes == null) return;

        addAttribute(meta, Attribute.GENERIC_ARMOR, attributes.getDouble("armor"), slot);
        addAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, attributes.getDouble("armor_toughness"), slot);
        addAttribute(meta, Attribute.GENERIC_KNOCKBACK_RESISTANCE, attributes.getDouble("knockback_resistance"), slot);
    }

    private void addAttribute(ItemMeta meta, Attribute attribute, double value, EquipmentSlot slot) {
        AttributeModifier modifier = new AttributeModifier(
                UUID.randomUUID(),
                attribute.name().toLowerCase(),
                value,
                Operation.ADD_NUMBER,
                slot
        );
        meta.addAttributeModifier(attribute, modifier);
    }

    private void applyEnchantments(ItemMeta meta, ConfigurationSection section) {
        List<String> enchantments = section.getStringList("enchantments");

        for (String enchantStr : enchantments) {
            String[] parts = enchantStr.split(":");
            if (parts.length != 2) continue;

            try {
                Enchantment enchantment = EnchantmentResolver.getEnchantmentByName(parts[0]);
                if (enchantment != null) {
                    int level = Integer.parseInt(parts[1]);
                    meta.addEnchant(enchantment, level, true);
                }
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private void applyUnbreakable(ItemMeta meta, ConfigurationSection section) {
        meta.setUnbreakable(section.getBoolean("unbreakable"));
    }

    private void applyPersistentData(ItemMeta meta, NamespacedKey key) {
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
    }

    private void applyItemFlags(ItemMeta meta) {
        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_UNBREAKABLE
        );
    }

    private boolean hasCustomKey(ItemStack item, NamespacedKey key) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }
}