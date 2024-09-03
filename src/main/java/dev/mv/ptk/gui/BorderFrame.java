package dev.mv.ptk.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BorderFrame extends InsetFrame {
    protected ItemStack display;

    public BorderFrame(ItemStack display) {
        super(1, 1, 1, 1);
        this.display = display;
    }

    @Override
    public void open(Inventory inventory) {
        super.open(inventory);

        int size = inventory.getSize();
        int rows = size / 9;

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, display);
            inventory.setItem(size - 9 + i, display);
        }

        for (int i = 1; i < rows - 1; i++) {
            inventory.setItem(i * 9, display);
            inventory.setItem(i * 9 + 8, display);
        }
    }
}
