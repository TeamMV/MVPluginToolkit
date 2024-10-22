package dev.mv.ptk.hotbar;

import dev.mv.utilsx.UtilsX;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerHotbar {
    private Player player;
    private HotbarItem[] items;

    public PlayerHotbar(Player player) {
        this.player = player;
        this.items = new HotbarItem[9];
    }

    public HotbarItem getItemAtSlot(int slot) {
        return items[UtilsX.clamp(slot, 0, 8)];
    }

    public void callItemAtSlot(int slot) {
        HotbarItem item = getItemAtSlot(slot);
        if (item != null) {
            item.getCallback().run();
        }
    }

    public void createNewItem(int slot, ItemStack stack, Runnable onInteract) {
        items[slot] = new HotbarItem(stack, onInteract);
        player.getInventory().setItem(slot, items[slot].getStack());
    }

    public void clear() {
        for (int i = 0; i < items.length; i++) {
            player.getInventory().setItem(i, new ItemStack(Material.AIR));
            items[i] = null;
        }
    }

    public Player getPlayer() {
        return player;
    }
}
