package dev.mv.ptk.hotbar;

import org.bukkit.inventory.ItemStack;

public class HotbarItem {
    private ItemStack stack;
    private Runnable callback;

    public HotbarItem(ItemStack stack, Runnable callback) {
        this.stack = stack;
        this.callback = callback;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Runnable getCallback() {
        return callback;
    }
}
