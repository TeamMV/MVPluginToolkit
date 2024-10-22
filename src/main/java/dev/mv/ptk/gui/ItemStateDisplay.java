package dev.mv.ptk.gui;

import dev.mv.ptk.utils.State;
import dev.mv.utilsx.UtilsX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStateDisplay extends Component {
    private ItemStack display;
    private State<?> state;
    private Inventory inv;

    public <T> ItemStateDisplay(ItemStack display, State<T> state) {
        this.display = display;
        this.state = state;
        state.observe(state.new Observer(this::setContent));
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
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
    public void open(Inventory inventory) {
        inventory.setItem(slot, display);
        this.inv = inventory;
        setContent(state.read());
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            inventoryInterface.open((Player) e.getWhoClicked());
        }
    }
}
