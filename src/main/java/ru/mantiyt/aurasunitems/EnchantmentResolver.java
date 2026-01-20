package ru.mantiyt.aurasunitems;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentResolver {

    private static final Map<String, Enchantment> LEGACY_ENCHANTMENTS = new HashMap<>();

    static {
        initializeLegacyEnchantments();
    }

    public static Enchantment getEnchantmentByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        name = name.toLowerCase().trim();

        Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(name));
        if (enchant != null) {
            return enchant;
        }

        for (Enchantment e : Enchantment.values()) {
            if (e.getKey().getKey().equalsIgnoreCase(name) ||
                    e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }

        return LEGACY_ENCHANTMENTS.get(name);
    }

    private static void initializeLegacyEnchantments() {
        addLegacy("protection_environmental", Enchantment.PROTECTION_ENVIRONMENTAL);
        addLegacy("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        addLegacy("fire_protection", Enchantment.PROTECTION_FIRE);
        addLegacy("feather_falling", Enchantment.PROTECTION_FALL);
        addLegacy("blast_protection", Enchantment.PROTECTION_EXPLOSIONS);
        addLegacy("projectile_protection", Enchantment.PROTECTION_PROJECTILE);
        addLegacy("respiration", Enchantment.OXYGEN);
        addLegacy("aqua_affinity", Enchantment.WATER_WORKER);
        addLegacy("thorns", Enchantment.THORNS);
        addLegacy("depth_strider", Enchantment.DEPTH_STRIDER);
        addLegacy("frost_walker", Enchantment.FROST_WALKER);
        addLegacy("mending", Enchantment.MENDING);
        addLegacy("vanishing_curse", Enchantment.VANISHING_CURSE);
        addLegacy("binding_curse", Enchantment.BINDING_CURSE);
    }

    private static void addLegacy(String name, Enchantment enchantment) {
        LEGACY_ENCHANTMENTS.put(name.toLowerCase(), enchantment);
    }
}