package ru.mantiyt.rideeggkeeper.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {
    public static final MenuHolder INSTANCE = new MenuHolder();
    private Inventory inventory;

    private MenuHolder() {}

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}