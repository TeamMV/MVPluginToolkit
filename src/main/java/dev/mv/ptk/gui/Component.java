package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class Component {
    public int slot;
    protected Component parent;
    protected InventoryInterface inventoryInterface;

    public Component(Component parent) {
        this.parent = parent;
    }

    public abstract int getWidth();
    public abstract int getHeight();

    public int getContentWidth() {
        return getWidth();
    }

    public int getContentHeight() {
        return getHeight();
    }

    public InventoryInterface getInterface() {
        return inventoryInterface;
    }

    public void setInterface(InventoryInterface ii) {
        inventoryInterface = ii;
    }

    public abstract void open(Inventory inventory, UiStyle style);

    public abstract boolean clickEvent(InventoryClickEvent e);

    public Component getParent() {
        return parent;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }
}
