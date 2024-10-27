package dev.mv.ptk.gui;

import dev.mv.ptk.keyboard.SignKeyboard;
import dev.mv.ptk.keyboard.SoftKeyboard;
import dev.mv.ptk.utils.input.TextProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemInput extends Component {
    private ItemStack stack;
    private String text = "text";
    private Inventory inv;
    private TextProvider provider;

    private List<Listener> listeners;

    public ItemInput(ItemStack stack) {
        this.stack = stack;
        listeners = new ArrayList<>();
        provider = new SignKeyboard();
    }

    public ItemInput(ItemStack stack, TextProvider provider) {
        this.stack = stack;
        this.provider = provider;
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
        inventory.setItem(slot, stack);
        this.inv = inventory;
        ((SoftKeyboard) provider).setBackInv(getInterface());
    }

    public void setProvider(TextProvider provider) {
        this.provider = provider;
        provider.setCallback(this::setText);
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            provider.open((Player) e.getWhoClicked());
        }
    }

    private void setText(String text, Player player) {
        this.text = text;
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(text);

        listeners.forEach(l -> l.textChange(text, player));
    }

    public ItemInput withListener(Listener listener) {
        listeners.add(listener);
        return this;
    }

    public interface Listener {
        void textChange(String newText, Player changer);
    }
}
