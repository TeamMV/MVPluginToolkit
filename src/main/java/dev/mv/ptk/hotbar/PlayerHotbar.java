package dev.mv.ptk.hotbar;

import dev.mv.utilsx.UtilsX;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlayerHotbar {
    private Player player;
    private HotbarItem[] items;
    private boolean[] locked;
    private boolean enabled;
    private int forceSlot = -1;

    public PlayerHotbar(Player player) {
        this.player = player;
        this.locked = new boolean[9];
        this.items = new HotbarItem[9];
    }

    public void createNewItem(int slot, ItemStack stack) {
        items[slot] = new HotbarItem(stack, () -> {});
        player.getInventory().setItem(slot, items[slot].getStack());
    }

    public void createNewItem(int slot, ItemStack stack, Runnable onInteract) {
        items[slot] = new HotbarItem(stack, onInteract);
        player.getInventory().setItem(slot, items[slot].getStack());
    }

    public void createNewItem(int slot, ItemStack stack, boolean locked) {
        items[slot] = new HotbarItem(stack, () -> {});
        player.getInventory().setItem(slot, items[slot].getStack());
        this.locked[slot] = locked;
    }

    public void createNewItem(int slot, ItemStack stack, Runnable onInteract, boolean locked) {
        items[slot] = new HotbarItem(stack, onInteract);
        player.getInventory().setItem(slot, items[slot].getStack());
        this.locked[slot] = locked;
    }

    public void lock(int slot) {
        this.locked[slot] = true;
    }

    public void unlock(int slot) {
        this.locked[slot] = false;
    }

    public void setLocked(int slot, boolean locked) {
        this.locked[slot] = locked;
    }

    public void lockAll() {
        Arrays.fill(locked, true);
    }

    public void unlockAll() {
        Arrays.fill(locked, false);
    }

    public boolean isLocked(int slot) { return locked[UtilsX.clamp(slot, 0, 8)]; }

    public void forceSlot(int slot) {
        forceSlot = slot;
        player.getInventory().setHeldItemSlot(slot);
    }

    public void stopForceSlot() {
        forceSlot = -1;
    }

    public void clear() {
        for (int i = 0; i < items.length; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
            items[i] = null;
            locked[i] = false;
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void onChangeHotbarSlot(PlayerItemHeldEvent e) {
        if (!enabled) return;
        if (forceSlot >= 0) {
            e.setCancelled(true);
            e.getPlayer().getInventory().setHeldItemSlot(forceSlot);
        }
    }

    public void onSwapItemWithOffhand(PlayerSwapHandItemsEvent e) {
        if (!enabled) return;
        if (locked[e.getPlayer().getInventory().getHeldItemSlot()]) {
            e.setCancelled(true);
        }
    }

    public void onInteract(PlayerInteractEvent e) {
        if (!enabled) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            HotbarItem item = items[e.getPlayer().getInventory().getHeldItemSlot()];
            if (item != null) {
                item.getCallback().run();
            }
        }
        if (e.getAction() != Action.PHYSICAL) {
            if (locked[e.getPlayer().getInventory().getHeldItemSlot()]) {
                e.setCancelled(true);
            }
        }
    }

    public void onClick(InventoryClickEvent e) {
        if (!enabled) return;
        if (e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
            if (e.getHotbarButton() < 0 || e.getHotbarButton() > 8) return;
            if (locked[e.getHotbarButton()]) {
                e.setCancelled(true);
            }
            return;
        }
        if (e.getSlot() > 8) return;
        if (locked[e.getSlot()]) {
            e.setCancelled(true);
        }
    }

    public void onDrop(PlayerDropItemEvent e) {
        if (!enabled) return;
        if (locked[e.getPlayer().getInventory().getHeldItemSlot()]) {
            e.setCancelled(true);
        }
    }
}
