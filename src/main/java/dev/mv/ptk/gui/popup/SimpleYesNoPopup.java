package dev.mv.ptk.gui.popup;

import dev.mv.ptk.gui.DisplayBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class SimpleYesNoPopup extends Popup {
    private Consumer<Boolean> callback;
    private boolean canClose;

    public SimpleYesNoPopup(String title, Consumer<Boolean> callback) {
        this.callback = callback;
        createInventory(title);
    }

    @Override
    protected void createInventory(String title) {
        inventory = Bukkit.createInventory(null, 27, title);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, DisplayBuilder.build(Material.GRAY_STAINED_GLASS_PANE).withTitle("&7").build());
        }
        inventory.setItem(11, DisplayBuilder
                .build(Material.GREEN_STAINED_GLASS_PANE)
                .withTitle("&2&lYes")
                .build()
        );

        inventory.setItem(15, DisplayBuilder
                .build(Material.RED_STAINED_GLASS_PANE)
                .withTitle("&4&lNo")
                .build()
        );
    }

    @Override
    public void clickEvent(int slot, Player player) {
        if (slot == 11) {
            canClose = true;
            player.closeInventory();
            callback.accept(true);
        }
        if (slot == 15) {
            canClose = true;
            player.closeInventory();
            callback.accept(false);
        }
    }

    @Override
    public boolean canClose() {
        return canClose;
    }
}
