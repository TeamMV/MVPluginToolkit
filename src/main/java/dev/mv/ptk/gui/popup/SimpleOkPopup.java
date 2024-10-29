package dev.mv.ptk.gui.popup;

import dev.mv.ptk.gui.DisplayBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SimpleOkPopup extends Popup {
    private Runnable callback;
    private boolean canClose;

    public SimpleOkPopup(String title, Runnable callback) {
        this.callback = callback;
        createInventory(title);
    }

    @Override
    protected void createInventory(String title) {
        inventory = Bukkit.createInventory(null, 27, title);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, DisplayBuilder.build(Material.GRAY_STAINED_GLASS_PANE).withTitle("&7").build());
        }
        inventory.setItem(13, DisplayBuilder
                .build(Material.GREEN_STAINED_GLASS_PANE)
                .withTitle("&2&lOk")
                .build()
        );
    }

    @Override
    public void clickEvent(int slot, Player player) {
        if (slot == 13) {
            canClose = true;
            player.closeInventory();
            callback.run();
        }
    }

    @Override
    public boolean canClose() {
        return canClose;
    }
}
