package dev.mv.ptk.gui;

import dev.mv.ptk.PluginListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryInterface extends CompoundComponent {
    private Inventory inventory;
    private int height;

    public InventoryInterface(String title) {
        inventory = Bukkit.createInventory(null, 54, title);
        this.height = 6;
        inventoryInterface = this;
    }

    public InventoryInterface(String title, int height) {
        inventory = Bukkit.createInventory(null, 9 * height, title);
        this.height = height;
        inventoryInterface = this;
    }

    public void open(Player player) {
        PluginListener.INTERFACES.push(this);
        positionChildren();

        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.open(inventory);
        }

        player.openInventory(inventory);
    }

    public void update(Player player) {
        inventory.clear();
        positionChildren();
        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.open(inventory);
        }
        player.updateInventory();
    }

    @Override
    public void positionChildren() {
        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.slot = 0;
            if (child instanceof CompoundComponent cc) cc.positionChildren();
        }
    }

    public void setTitle(String name) {
        inventory = Bukkit.createInventory(null, height * 9, name);
    }

    @Override
    public int getWidth() {
        return 9;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
