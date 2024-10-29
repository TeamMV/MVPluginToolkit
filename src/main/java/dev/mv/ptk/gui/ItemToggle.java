package dev.mv.ptk.gui;

import dev.mv.ptk.Utils;
import dev.mv.ptk.style.Style;
import dev.mv.ptk.style.UiStyle;
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
    private Inventory inv;

    public ItemToggle(ItemStack stack) {
        super(null);
        this.stack = stack;
        listeners = new Vec<>();
    }

    public ItemToggle(Component parent, ItemStack stack) {
        super(parent);
        this.stack = stack;
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
    public void open(Inventory inventory, UiStyle style) {
        inventory.setItem(slot, getActualStack());
        inventory.setItem(slot + 9, enabled ?
                style.getEnabled() :
                style.getDisabled()
        );
        inv = inventory;
    }

    private ItemStack getActualStack() {
        return stack;
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot || e.getSlot() == slot + 9) {
            enabled = !enabled;
            open(inv, Style.getInstance().getStyle(e.getWhoClicked().getUniqueId()));
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
