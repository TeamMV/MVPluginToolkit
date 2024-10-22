package dev.mv.ptk;

import dev.mv.ptk.hotbar.Hotbar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ItemListener implements Listener {
    private int lastSlot = 0;

    @EventHandler
    public void onItemHold(PlayerItemHeldEvent e) {
        lastSlot = e.getNewSlot();
    }


    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        int slot = lastSlot;
        Hotbar.getInstance().get(player).callItemAtSlot(slot);
    }
}
