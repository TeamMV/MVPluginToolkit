package dev.mv.ptk;

import dev.mv.ptk.gui.InventoryInterface;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

public class PluginListener implements Listener {
    public static List<InventoryInterface> INTERFACES = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        INTERFACES.forEach(i -> {
            if (i.getInventory() == e.getClickedInventory()) {
                e.setCancelled(true);
                i.clickEvent(e);
            }
        });
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Vec<Integer> toRemove = new Vec<>();

        Utils.enumerateList(INTERFACES, (idx, i) -> {
            if (i.getInventory() == e.getInventory()) {
                toRemove.push(idx);
            }
        });

        System.out.println("ToRemove: " + toRemove);
        System.out.println("Before: " + INTERFACES);
        toRemove.forEach(i -> INTERFACES.remove(i.intValue()));
        System.out.println("After: " + INTERFACES);
    }
}
