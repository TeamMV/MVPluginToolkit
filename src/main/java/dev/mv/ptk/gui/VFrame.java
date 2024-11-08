package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;

public class VFrame extends CompoundComponent {
    public VFrame() {
        super(null);
    }

    public VFrame(Component parent) {
        super(parent);
    }

    @Override
    public void positionChildren(UiStyle style) {
        int currentSlot = slot;

        for (Component c : children) {
            c.slot = currentSlot;
            currentSlot += c.getWidth();
            if (c instanceof CompoundComponent cc) cc.positionChildren(style);
        }
    }

    @Override
    public int getContentWidth() {
        int w = 0;
        for (Component c : children) {
            w += c.getWidth();
        }
        return w;
    }

    @Override
    public int getContentHeight() {
        return maxChildHeight;
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (Component c : children) {
            w += c.getWidth();
        }
        return w;
    }

    @Override
    public int getHeight() {
        return maxChildHeight;
    }
}
