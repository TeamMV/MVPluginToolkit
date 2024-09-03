package dev.mv.ptk.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemButton extends Component{
    private ItemStack display;
    private List<Listener> listeners;

    public ItemButton(ItemStack display) {
        this.display = display;
        listeners = new ArrayList<>();
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

    public interface Listener {
        void click(int slot, boolean isShift, boolean isRight);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void click(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            listeners.forEach(l -> l.click(slot, e.isShiftClick(), e.isRightClick()));
        }
    }

    public ItemStack getDisplay() {
        return display;
    }

    public int getSlot() {
        return slot;
    }
}
