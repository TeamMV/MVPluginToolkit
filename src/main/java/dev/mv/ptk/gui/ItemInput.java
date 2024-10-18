package dev.mv.ptk.gui;

import dev.mv.ptk.PluginListener;
import dev.mv.ptk.Utils;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import oshi.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class ItemInput extends Component {
    private ItemStack stack;
    private String text = "text";
    private Inventory inv;

    private List<Listener> listeners;

    public ItemInput(ItemStack stack) {
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
        inventory.setItem(slot, stack);
        this.inv = inventory;
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot) {
            Inventory anvil = Bukkit.createInventory(null, InventoryType.ANVIL, stack.getItemMeta().getDisplayName());
            ItemStack closeButton = DisplayBuilder.build(Material.BARRIER).withTitle("<--").build();
            anvil.setItem(0, closeButton);
            e.getWhoClicked().openInventory(anvil);

            PluginListener.ANVILS.add(new Triplet<>(anvil, getInterface(), a -> {
                if (a != null) {
                    setText(a, (Player) e.getWhoClicked());
                }
            }));
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