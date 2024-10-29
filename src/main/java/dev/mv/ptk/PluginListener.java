package dev.mv.ptk;

import dev.mv.ptk.gui.InventoryInterface;
import dev.mv.ptk.gui.popup.Popup;
import dev.mv.ptk.utils.input.InvClickReceiver;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class PluginListener implements Listener {
    public static Vec<InventoryInterface> INTERFACES = new Vec<>();
    public static List<InvClickReceiver> receivers = new ArrayList<>();
    public static Vec<Popup> POPUPS = new Vec<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        receivers.forEach(r -> {
            if (r.getInventory().equals(e.getClickedInventory())) {
                r.acceptEvent(e);
            }
        });
        try {
            INTERFACES.forEach(i -> {
                if (i.getInventory() == e.getClickedInventory()) {
                    e.setCancelled(true);
                    i.clickEvent(e);
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

        POPUPS.iterCopied().forEach(currentPopup -> {
            if (e.getInventory().equals(currentPopup.getInventory())) {
                e.setCancelled(true);
                currentPopup.clickEvent(e.getSlot(), (Player) e.getWhoClicked());
            }
        });
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Vec<Integer> toRemoveInvRec = new Vec<>();
        int icrIdx = 0;
        for (InvClickReceiver icr : receivers) {
            if (icr.getInventory() != null && icr.getInventory().equals(e.getInventory())) {
                toRemoveInvRec.push(icrIdx);
            }
            icrIdx++;
        }
        toRemoveInvRec.forEach(idx -> {
            receivers.remove(idx.intValue()).close(e);
        });

        INTERFACES = INTERFACES.iter().filter(inv -> inv.getInventory() != e.getInventory()).collect();

        POPUPS.iterCopied().enumerate().forEach((idx) -> {
            int i = idx.i;
            Popup currentPopup = idx.value;
            if (e.getInventory().equals(currentPopup.getInventory())) {
                POPUPS.remove(i);
                if (!currentPopup.canClose()) {
                    Bukkit.getScheduler().runTaskLater(Ptk.getInstance(), () -> {
                        currentPopup.open((Player) e.getPlayer());
                    }, 1);
                }
            }
        });
    }
}
