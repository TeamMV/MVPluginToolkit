package dev.mv.ptk.gui;

public class VFrame extends CompoundComponent {
    @Override
    public void positionChildren() {
        int currentSlot = slot;

        for (Component c : children) {
            c.slot = currentSlot;
            currentSlot += c.getWidth();
            if (c instanceof CompoundComponent cc) cc.positionChildren();
        }
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
