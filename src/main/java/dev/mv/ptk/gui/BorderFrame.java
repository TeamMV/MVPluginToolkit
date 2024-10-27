package dev.mv.ptk.gui;

import org.bukkit.Bukkit;
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

        int width = getWidth() + 2;
        int height = getHeight() + 2;
        int size = width * height;

        for (int i = 0; i < width; i++) {
            inventory.setItem(map(i), display);
            inventory.setItem(map(size - width + i), display);
        }

        for (int i = 1; i < size / width - 1; i++) {
            inventory.setItem(map(i * width), display);
            inventory.setItem(map(i * width + width - 1), display);
        }
    }

    public int map(int slot) {
        int width = getWidth() + 2;
        int row = slot / width;
        int col = slot % width;
        int top = this.slot / 9;
        int left = this.slot % 9;

        return (row + top) * 9 + col + left;
    }
}
