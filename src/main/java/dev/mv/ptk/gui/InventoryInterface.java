package dev.mv.ptk.gui;

import dev.mv.ptk.PluginListener;
import dev.mv.ptk.style.Style;
import dev.mv.ptk.style.UiStyle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryInterface extends CompoundComponent {
    private Inventory inventory;
    private int height;

    public InventoryInterface(String title) {
        super(null);
        inventory = Bukkit.createInventory(null, 54, title);
        this.height = 6;
        inventoryInterface = this;
    }

    public InventoryInterface(String title, int height) {
        super(null);
        inventory = Bukkit.createInventory(null, 9 * height, title);
        this.height = height;
        inventoryInterface = this;
    }

    public void open(Player player) {
        UiStyle style = Style.getInstance().getStyle(player.getUniqueId());
        PluginListener.INTERFACES.push(this);
        positionChildren(style);

        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.open(inventory, style);
        }

        player.openInventory(inventory);
    }

    public void update(Player player) {
        UiStyle style = Style.getInstance().getStyle(player.getUniqueId());
        inventory.clear();
        positionChildren(style);
        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.open(inventory, style);
        }
        player.updateInventory();
    }

    @Override
    public void positionChildren(UiStyle style) {
        if (!children.isEmpty()) {
            Component child = children.get(0);
            child.slot = 0;
            if (child instanceof CompoundComponent cc) cc.positionChildren(style);
        }
    }

    public void setTitle(String name) {
        inventory = Bukkit.createInventory(null, height * 9, name);
    }

    @Override
    public int getContentWidth() {
        return 9;
    }

    @Override
    public int getContentHeight() {
        return height;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public int getWidth() {
        return getContentWidth();
    }

    @Override
    public int getHeight() {
        return getContentHeight();
    }
}
