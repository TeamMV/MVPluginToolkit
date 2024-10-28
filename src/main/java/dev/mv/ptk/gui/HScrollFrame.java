package dev.mv.ptk.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class HScrollFrame extends CompoundComponent {
    protected int scrollOffset;
    protected int overlap;

    public HScrollFrame(Component parent) {
        super(parent);
    }

    private void onScrolled(Player player) {
        getInterface().update(player);
    }

    public void scrollUp(int amt, Player player) {
        scrollOffset -= amt;
        if (scrollOffset < 0) scrollOffset = 0;
        onScrolled(player);
    }

    public void scrollDown(int amt, Player player) {
        scrollOffset += amt;
        if (scrollOffset >= overlap) scrollOffset -= amt;
        onScrolled(player);
    }

    private int mapSlot(int slot) {
        return slot - 9 * scrollOffset;
    }

    @Override
    public void positionChildren() {
        int currentSlot = slot;

        for (Component c : children) {
            int mapped = mapSlot(currentSlot);
            if (mapped > slot + getContentHeight() * 9) break;
            c.slot = mapped;
            currentSlot += 9 * c.getHeight();
            if (c instanceof CompoundComponent cc) cc.positionChildren();
        }
    }

    @Override
    public int getWidth() {
        return parent.getWidth();
    }

    @Override
    public int getHeight() {
        return parent.getHeight();
    }

    @Override
    public void open(Inventory inventory) {
        overlap = 0;
        for (Component c : children) {
            overlap += c.getHeight();
        }
        overlap -= (getContentHeight() - 1);
        if (overlap < 0) overlap = 0;

        children.forEach(c -> {
            c.open(inventory);
        });

        for (int i = 0; i < getContentHeight(); i++) {
            int s = slot + i * 9 + getContentWidth();
            inventory.setItem(s, DisplayBuilder.build(Material.GRAY_STAINED_GLASS_PANE).withTitle("&7").build());
        }
        inventory.setItem(slot + getContentWidth(), DisplayBuilder.build(Material.ARROW).withTitle("&6Scroll up").build());
        inventory.setItem(slot + getContentWidth() + 9 * getContentHeight() - 9, DisplayBuilder.build(Material.ARROW).withTitle("&6Scroll down").build());
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        if (e.getSlot() == slot + getContentWidth()) {
            scrollUp(1, (Player) e.getWhoClicked());
        } else if (e.getSlot() == slot + getContentWidth() + 9 * getContentHeight() - 9) {
            scrollDown(1, (Player) e.getWhoClicked());
        }
        return super.clickEvent(e);
    }

    @Override
    public int getContentWidth() {
        return parent.getContentWidth() - 1;
    }

    @Override
    public int getContentHeight() {
        return parent.getContentHeight();
    }
}
