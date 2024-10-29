package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;

public class HFrame extends CompoundComponent {
    public HFrame() {
        super(null);
    }

    public HFrame(Component parent) {
        super(parent);
    }

    @Override
    public void positionChildren(UiStyle style) {
        int currentSlot = slot;

        for (Component c : children) {
            c.slot = currentSlot;
            currentSlot += 9 * c.getHeight();
            if (c instanceof CompoundComponent cc) cc.positionChildren(style);
        }
    }

    @Override
    public int getContentWidth() {
        return maxChildWidth;
    }

    @Override
    public int getContentHeight() {
        int h = 0;
        for (Component c : children) {
            h += c.getHeight();
        }
        return h;
    }

    @Override
    public int getWidth() {
        return maxChildWidth;
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (Component c : children) {
            h += c.getHeight();
        }
        return h;
    }
}
