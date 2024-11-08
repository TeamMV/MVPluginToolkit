package dev.mv.ptk.gui;

import dev.mv.ptk.Ptk;
import dev.mv.ptk.keyboard.SignKeyboard;
import dev.mv.ptk.keyboard.SoftKeyboard;
import dev.mv.ptk.style.UiStyle;
import dev.mv.ptk.utils.input.TextProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemInput extends Component1x1 {
    private ItemStack stack;
    private String text = "";
    private String prompt;
    private TextProvider provider;

    private List<Listener> listeners;

    public ItemInput(ItemStack stack) {
        super(null);
        this.stack = stack;
        listeners = new ArrayList<>();
        setProvider(new SignKeyboard());
        prompt = "Enter text";
    }

    public ItemInput(ItemStack stack, TextProvider provider) {
        super(null);
        this.stack = stack;
        listeners = new ArrayList<>();
        prompt = "Enter text";
        setProvider(provider);
    }

    public ItemInput(ItemStack stack, String prompt) {
        super(null);
        this.stack = stack;
        this.prompt = prompt;
        listeners = new ArrayList<>();
        setProvider(new SignKeyboard());
    }

    public ItemInput(ItemStack stack, TextProvider provider, String prompt) {
        super(null);
        this.stack = stack;
        setProvider(provider);
        this.prompt = prompt;
        listeners = new ArrayList<>();
    }

    public ItemInput(Component parent, ItemStack stack) {
        super(parent);
        this.stack = stack;
        listeners = new ArrayList<>();
        setProvider(new SignKeyboard());
        prompt = "Enter text";
    }

    public ItemInput(Component parent, ItemStack stack, TextProvider provider) {
        super(parent);
        this.stack = stack;
        listeners = new ArrayList<>();
        prompt = "Enter text";
        setProvider(provider);
    }

    public ItemInput(Component parent, ItemStack stack, String prompt) {
        super(parent);
        this.stack = stack;
        this.prompt = prompt;
        listeners = new ArrayList<>();
        setProvider(new SignKeyboard());
    }

    public ItemInput(Component parent, ItemStack stack, TextProvider provider, String prompt) {
        super(parent);
        this.stack = stack;
        setProvider(provider);
        this.prompt = prompt;
        listeners = new ArrayList<>();
    }

    @Override
    public void open(Inventory inventory, UiStyle style) {
        inventory.setItem(slot, stack);
        provider.setCloseCallback((s, pl) -> {
            pl.closeInventory();
            inventoryInterface.open(pl);
        });
    }

    public void setProvider(TextProvider provider) {
        this.provider = provider;
        provider.setCallback(this::setText);
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            provider.open((Player) e.getWhoClicked(), prompt);
        }
        return false;
    }

    private void setText(String text, Player player) {
        this.text = text;
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(text);

        listeners.forEach(l -> l.textChange(text, player));
    }

    public String getText() {
        return text;
    }

    public ItemInput withListener(Listener listener) {
        listeners.add(listener);
        return this;
    }

    public interface Listener {
        void textChange(String newText, Player changer);
    }
}
