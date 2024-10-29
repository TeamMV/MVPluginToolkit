package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;
import dev.mv.ptk.utils.State;
import dev.mv.utilsx.UtilsX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStateDisplay extends Component1x1 {
    private ItemStack display;
    private State<?> state;
    private Inventory inv;

    public <T> ItemStateDisplay(ItemStack display, State<T> state) {
        super(null);
        this.display = display;
        this.state = state;
        state.observe(state.new Observer((v, pl) -> setContent(v)));
    }

    public <T> ItemStateDisplay(Component parent, ItemStack display, State<T> state) {
        super(parent);
        this.display = display;
        this.state = state;
        state.observe(state.new Observer((v, pl) -> setContent(v)));
    }

    public void setContent(Object obj) {
        ItemMeta meta = display.getItemMeta();
        meta.setDisplayName(obj.toString());
        if (obj instanceof Number n) {
            int amt = n.intValue();
            display.setAmount(UtilsX.clamp(amt, 1, 64));
        }
        display.setItemMeta(meta);
        inv.setItem(slot, display);
    }

    @Override
    public void open(Inventory inventory, UiStyle style) {
        inventory.setItem(slot, display);
        this.inv = inventory;
        setContent(state.read());
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        return false;
    }
}
