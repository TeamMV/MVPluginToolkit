package dev.mv.ptk.gui;

import dev.mv.ptk.PluginListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryInterface extends CompoundComponent {
    private Inventory inventory;

    public InventoryInterface(String title) {
        inventory = Bukkit.createInventory(null, 54, title);
        inventoryInterface = this;
    }

    public void open(Player player) {
        PluginListener.INTERFACES.add(this);
        positionChildren();

        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.open(inventory);
        }

        player.openInventory(inventory);
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

    public Inventory getInventory() {
        return inventory;
    }
}
