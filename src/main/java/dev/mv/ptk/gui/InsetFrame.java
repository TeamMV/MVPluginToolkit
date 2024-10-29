package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;

public class InsetFrame extends CompoundComponent {
    protected int top, bottom, left, right;

    public InsetFrame(int top, int left, int bottom, int right) {
        super(null);
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public InsetFrame(int top, int left, int bottom, int right, Component parent) {
        super(parent);
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    @Override
    public void positionChildren(UiStyle style) {
        if (!children.isEmpty()) {
            Component component = children.get(0);
            component.slot = slot + left + 9 * top;
            if (component instanceof CompoundComponent cc) cc.positionChildren(style);
        }
    }

    @Override
    public int getContentWidth() {
        if (parent == null) return 0;
        return parent.getContentWidth() - left - right;
    }

    @Override
    public int getContentHeight() {
        if (parent == null) return 0;
        return parent.getContentHeight() - top - bottom;
    }

    @Override
    public int getWidth() {
        return parent.getContentWidth();
    }

    @Override
    public int getHeight() {
        return parent.getContentHeight();
    }
}
