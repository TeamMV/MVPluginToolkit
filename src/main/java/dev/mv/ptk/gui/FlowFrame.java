package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class FlowFrame extends HFrame {
    private int contentHeight;

    public FlowFrame() {
        super();
    }

    public FlowFrame(Component parent) {
        super(parent);
    }

    @Override
    public int getHeight() {
        int currentSlot = slot;
        int walked = 0;
        int rowHeight = 0;
        contentHeight = 0;
        for (Component c : children) {
            rowHeight = Math.max(rowHeight, c.getHeight());

            if (currentSlot + c.getWidth() > slot + getContentWidth() + contentHeight * 9) {
                currentSlot -= walked;
                currentSlot += 9 * rowHeight;
                contentHeight += rowHeight;
                rowHeight = 0;
                walked = 0;
            }

            currentSlot += c.getWidth();
            walked += c.getWidth();
        }
        contentHeight += rowHeight;
        return contentHeight;
    }

    @Override
    public int getContentHeight() {
        return getHeight();
    }

    @Override
    public int getWidth() {
        return parent.getContentWidth();
    }

    @Override
    public int getContentWidth() {
        return getWidth();
    }

    @Override
    public void open(Inventory inventory, UiStyle style) {
        children.forEach(c -> {
            if (c.slot >= slot && c.slot >= 0 && c.slot < inventory.getSize()) {
                c.open(inventory, style);
            }
        });
    }

    @Override
    public void positionChildren(UiStyle style) {
        int currentSlot = slot;
        int walked = 0;
        int rowHeight = 0;
        contentHeight = 0;
        for (Component c : children) {
            rowHeight = Math.max(rowHeight, c.getHeight());

            if (currentSlot + c.getWidth() > slot + getContentWidth() + contentHeight * 9) {
                currentSlot -= walked;
                currentSlot += 9 * rowHeight;
                contentHeight += rowHeight;
                rowHeight = 0;
                walked = 0;
            }

            c.slot = currentSlot;

            if (c instanceof CompoundComponent cc) cc.positionChildren(style);

            currentSlot += c.getWidth();
            walked += c.getWidth();
        }
        contentHeight += rowHeight;
    }
}
