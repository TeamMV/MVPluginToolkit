package dev.mv.ptk.gui;

import dev.mv.ptk.Utils;
import dev.mv.ptk.utils.State;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemToggle extends Component {
    private boolean enabled;
    private State<Boolean> state;
    private Vec<Listener> listeners;
    private ItemStack stack;
    private ItemStack displayStackOff;
    private ItemStack displayStackOn;
    private Inventory inv;

    public ItemToggle(ItemStack stack) {
        this.stack = stack;
        this.displayStackOff = DisplayBuilder.build(Material.RED_CANDLE).withTitle("&4&lDisabled").build();
        this.displayStackOn = DisplayBuilder.build(Material.LIME_CANDLE).withTitle("&2&lEnabled").build();
        listeners = new Vec<>();
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 2;
    }

    @Override
    public void open(Inventory inventory) {
        inventory.setItem(slot, getActualStack());
        inventory.setItem(slot + 9, enabled ? displayStackOn : displayStackOff);
        inv = inventory;
    }

    private ItemStack getActualStack() {
        ItemMeta meta = stack.getItemMeta();
        if (enabled) {
            meta.setLore(List.of(Utils.chat("&2&lEnabled")));
        } else {
            meta.setLore(List.of(Utils.chat("&4&lDisabled")));
        }
        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot || e.getSlot() == slot + 9) {
            enabled = !enabled;
            open(inv);
            if (state != null) {
                state.write((Player) e.getWhoClicked(), enabled);
            }
            if (inv != null) {
                inv.setItem(slot, getActualStack());
            }
            return listeners.iter().forAny(l -> l.toggle(slot, enabled, e.getWhoClicked()));
        }
        return false;
    }

    public void addListener(Listener listener) {
        listeners.push(listener);
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
        boolean toggle(int slot, boolean now, HumanEntity toggler);
    }
}
