package dev.mv.ptk.gui;

public abstract class Component1x1 extends Component {
    public Component1x1(Component parent) {
        super(parent);
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
