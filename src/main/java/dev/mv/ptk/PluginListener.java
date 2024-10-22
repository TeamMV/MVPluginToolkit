package dev.mv.ptk;

import dev.mv.ptk.gui.InventoryInterface;
import dev.mv.ptk.utils.input.InvClickReceiver;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class PluginListener implements Listener {
    public static List<InventoryInterface> INTERFACES = new ArrayList<>();
    public static List<Triplet<Inventory, InventoryInterface, Consumer<String>>> ANVILS = new ArrayList<>();
    public static List<InvClickReceiver> receivers = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        receivers.forEach(r -> {
            if (r.getInventory().equals(e.getClickedInventory())) {
                r.acceptEvent(e);
            }
        });
        try {
            ANVILS.forEach(a -> {
                if (a.getA() == e.getClickedInventory()) {
                    if (e.getSlot() == 0) {
                        e.setCancelled(true);
                        a.getB().open((Player) e.getWhoClicked());
                    } else if (e.getSlot() != 2) {
                        e.setCancelled(true);
                    } else {
                        Inventory anvil = e.getClickedInventory();
                        //e.setCancelled(true);
                        //Bukkit.getScheduler().runTaskLater(Ptk.getInstance(), () -> {
                        //    Bukkit.broadcastMessage("adasdasdasd");
                        //    a.getC().accept(anvil.getItem(2).getItemMeta().getDisplayName());
                        //    if (a.getB() != null) {
                        //        if (e.getInventory().getHolder() instanceof Player p) {
                        //            a.getB().open(p);
                        //        }
                        //    }
                        //}, 20);
                    }
                }
            });
            INTERFACES.forEach(i -> {
                if (i.getInventory() == e.getClickedInventory()) {
                    e.setCancelled(true);
                    i.clickEvent(e);
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @EventHandler
    public void onAnvilRename(PrepareAnvilEvent e) {
        Bukkit.broadcastMessage("anvil rename");
        ANVILS.forEach(a -> {
            if (a.getA() == e.getInventory()) {

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

        Vec<Integer> toRemoveII = new Vec<>();

        Utils.enumerateList(INTERFACES, (idx, i) -> {
            if (i.getInventory() == e.getInventory()) {
                toRemoveII.push(idx);
            }
        });

        toRemoveII.forEach(i -> INTERFACES.remove(i.intValue()));

        Vec<Integer> toRemoveA = new Vec<>();

        Utils.enumerateList(ANVILS, (idx, i) -> {
            if (i.getA() == e.getInventory()) {
                toRemoveA.push(idx);
            }
        });

        toRemoveA.forEach(i -> ANVILS.remove(i.intValue()));
    }
}
