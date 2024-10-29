package dev.mv.ptk.gui.popup;

import dev.mv.ptk.PluginListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class Popup {
    protected Inventory inventory;

    protected void createInventory(String title) {
        inventory = Bukkit.createInventory(null, 54, title);
    }

    public abstract void clickEvent(int slot, Player player);

    public void open(Player player) {
        player.openInventory(inventory);
        PluginListener.POPUPS.push(this);
    }

    public abstract boolean canClose();

    public Inventory getInventory() {
        return inventory;
    }
}
