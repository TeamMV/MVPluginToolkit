package dev.mv.ptk.gui;

import org.bukkit.inventory.Inventory;

public class HFrame extends CompoundComponent {
    @Override
    public void positionChildren() {
        int currentSlot = slot;

        for (Component c : children) {
            c.slot = currentSlot;
            currentSlot += 9 * c.getHeight();
            if (c instanceof CompoundComponent cc) cc.positionChildren();
        }
    }

    @Override
    public int getWidth() {
        return maxChildWidth;
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (Component c : children) {
            h += c.getHeight();
        }
        return h;
    }
}
