package dev.mv.ptk.gui;

import dev.mv.utilsx.collection.Vec;
import jdk.dynalink.linker.support.Lookup;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemButton extends Component{
    private ItemStack display;
    private Vec<Listener> listeners;

    public ItemButton(ItemStack display) {
        this.display = display;
        listeners = new Vec<>();
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public void open(Inventory inventory) {
        inventory.setItem(slot, display);
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            return listeners.iter().forAny(l -> l.click(slot, e.getClick(), e.getWhoClicked()));
        }
        return false;
    }

    public interface Listener {
        boolean click(int slot, ClickType type, HumanEntity clicker);
    }

    public void addListener(Listener listener) {
        listeners.push(listener);
    }

    public ItemButton withListener(Listener listener) {
        listeners.push(listener);
        return this;
    }

    public ItemStack getDisplay() {
        return display;
    }

    public int getSlot() {
        return slot;
    }
}
