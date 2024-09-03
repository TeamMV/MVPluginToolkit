package dev.mv.ptk.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class InventoryInterface extends CompoundComponent {
    private Inventory inventory;

    public void open() {
        positionChildren();

        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.open(inventory);
        }
    }

    @Override
    public void positionChildren() {
        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.slot = 0;
            if (child instanceof CompoundComponent cc) cc.positionChildren();
        }
    }

    @Override
    public int getWidth() {
        return 9;
    }

    @Override
    public int getHeight() {
        return inventory.getSize() / 9;
    }

    @Override
    public void open(Inventory inventory) {

    }
}
