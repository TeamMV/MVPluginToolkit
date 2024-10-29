package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;
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

    public HScrollFrame(Component parent, int startingOffset) {
        super(parent);
        this.scrollOffset = startingOffset;
    }

    public int getOffset() {
        return scrollOffset;
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
        if (scrollOffset >= overlap) scrollOffset = overlap;
        onScrolled(player);
    }

    private int mapSlot(int slot) {
        return slot - 9 * scrollOffset;
    }

    @Override
    public void positionChildren(UiStyle style) {
        int currentSlot = slot;

        for (Component c : children) {
            int mapped = mapSlot(currentSlot);
            if (mapped > slot + getContentHeight() * 9) break;
            c.slot = mapped;
            currentSlot += 9 * c.getHeight();
            if (c instanceof CompoundComponent cc) cc.positionChildren(style);
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
    public void open(Inventory inventory, UiStyle style) {
        overlap = 0;
        for (Component c : children) {
            overlap += c.getHeight();
        }
        overlap -= (getContentHeight() - 1);
        if (overlap < 0) overlap = 0;

        children.forEach(c -> {
            c.open(inventory, style);
        });

        for (int i = 0; i < getContentHeight(); i++) {
            int s = slot + i * 9 + getContentWidth();
            inventory.setItem(s, DisplayBuilder.build(style.getBorder()).withTitle("&7").build());
        }
        if (scrollOffset > 0) inventory.setItem(slot + getContentWidth(), DisplayBuilder.build(style.getArrowButton()).withTitle("&fScroll up").build());
        if (scrollOffset < overlap) inventory.setItem(slot + getContentWidth() + 9 * getContentHeight() - 9, DisplayBuilder.build(style.getArrowButton()).withTitle("&fScroll down").build());
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
