package dev.mv.ptk.hotbar;

import dev.mv.ptk.utils.Null;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class HotbarListener implements Listener {

    @EventHandler
    public void onChangeHotbarSlot(PlayerItemHeldEvent e) {
        Null.not(Hotbar.getInstance().get(e.getPlayer()), p -> p.onChangeHotbarSlot(e));
    }

    @EventHandler
    public void onSwapItemWithOffhand(PlayerSwapHandItemsEvent e) {
        Null.not(Hotbar.getInstance().get(e.getPlayer()), p -> p.onSwapItemWithOffhand(e));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Null.not(Hotbar.getInstance().get(e.getPlayer()), p -> p.onInteract(e));
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Null.not(Hotbar.getInstance().get((Player) e.getWhoClicked()), p -> p.onClick(e));
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Null.not(Hotbar.getInstance().get(e.getPlayer()), p -> p.onDrop(e));
    }
}
