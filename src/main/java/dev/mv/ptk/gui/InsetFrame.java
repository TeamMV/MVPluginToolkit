package dev.mv.ptk.gui;

public class InsetFrame extends CompoundComponent {
    protected int top, bottom, left, right;

    public InsetFrame(int top, int left, int bottom, int right) {
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
    public void positionChildren() {
        if (!children.isEmpty()) {
            Component component = children.get(0);
            component.slot = slot + left + 9 * top;
            if (component instanceof CompoundComponent cc) cc.positionChildren();
        }
    }

    @Override
    public int getWidth() {
        if (parent == null) return 0;
        return parent.getWidth() - left - right;
    }

    @Override
    public int getHeight() {
        if (parent == null) return 0;
        return parent.getHeight() - top - bottom;
    }
}
