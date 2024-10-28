package dev.mv.ptk.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class WindowFrame extends InsetFrame {
    private InventoryInterface previous;

    public WindowFrame() {
        super(1, 0, 0, 0);
        previous = null;
    }

    public WindowFrame(InventoryInterface previous) {
        super(1, 0, 0, 0);
        this.previous = previous;
    }

    public WindowFrame(Component parent) {
        super(1, 0, 0, 0, parent);
        previous = null;
    }

    public WindowFrame(Component parent, InventoryInterface previous) {
        super(1, 0, 0, 0, parent);
        this.previous = previous;
    }

    @Override
    public void open(Inventory inventory) {
        super.open(inventory);

        inventory.setItem(slot, DisplayBuilder.build(Material.RED_STAINED_GLASS_PANE).withTitle("&4Back").build());
        for (int i = slot + 1; i < slot + getContentWidth(); i++) {
            inventory.setItem(i, DisplayBuilder.build(Material.GRAY_STAINED_GLASS_PANE).withTitle("&7").build());
        }
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            if (previous != null) {
                previous.open((Player) e.getWhoClicked());
            } else {
                e.getWhoClicked().closeInventory();
            }
        }

        return super.clickEvent(e);
    }
}
