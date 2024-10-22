package dev.mv.ptk.utils.input;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public interface InvClickReceiver {
    void acceptEvent(InventoryClickEvent e);
    Inventory getInventory();
    void close(InventoryCloseEvent e);

}
