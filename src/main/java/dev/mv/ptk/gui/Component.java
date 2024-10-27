package dev.mv.ptk.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class Component {
    protected int slot;
    protected Component parent;
    protected InventoryInterface inventoryInterface;

    public abstract int getWidth();
    public abstract int getHeight();

    public InventoryInterface getInterface() {
        return inventoryInterface;
    }

    public void setInterface(InventoryInterface ii) {
        inventoryInterface = ii;
    }

    public abstract void open(Inventory inventory);

    public abstract boolean clickEvent(InventoryClickEvent e);
}
