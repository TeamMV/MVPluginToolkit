package dev.mv.ptk.gui;

import org.bukkit.inventory.Inventory;

public abstract class Component {
    protected int slot;
    protected Component parent;

    public abstract int getWidth();
    public abstract int getHeight();

    public abstract void open(Inventory inventory);
}
