package dev.mv.ptk.gui;

import dev.mv.ptk.Utils;
import dev.mv.ptk.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemToggle extends Component {
    private boolean enabled;
    private State<Boolean> state;
    private List<Listener> listeners;
    private ItemStack stack;
    private Inventory inv;

    public ItemToggle(ItemStack stack) {
        this.stack = stack;
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
        inventory.setItem(slot, getActualStack());
        inv = inventory;
    }

    private ItemStack getActualStack() {
        ItemMeta meta = stack.getItemMeta();
        if (enabled) {
            stack.setAmount(2);
            meta.setLore(List.of(Utils.chat("&2&lEnabled")));
        } else {
            stack.setAmount(1);
            meta.setLore(List.of(Utils.chat("&4&lDisabled")));
        }
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            enabled = !enabled;
            if (state != null) {
                state.write(enabled);
            }
            if (inv != null) {
                inv.setItem(slot, getActualStack());
            }
            listeners.forEach(l -> l.toggle(slot, enabled, e.getWhoClicked()));
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public ItemToggle withListener(Listener listener) {
        addListener(listener);
        return this;
    }

    public ItemToggle withState(State<Boolean> state) {
        setState(state);
        return this;
    }

    public void setState(State<Boolean> state) {
        this.state = state;
    }

    public interface Listener {
        void toggle(int slot, boolean now, HumanEntity toggler);
    }
}
