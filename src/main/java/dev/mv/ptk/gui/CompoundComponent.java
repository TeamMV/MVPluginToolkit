package dev.mv.ptk.gui;

import org.bukkit.inventory.Inventory;

import java.util.List;

public abstract class CompoundComponent extends Component {
    protected List<Component> children;
    protected int maxChildWidth, maxChildHeight;

    public void addComponent(Component component) {
        children.add(component);
        maxChildWidth = Math.max(maxChildWidth, component.getWidth());
        maxChildHeight = Math.max(maxChildHeight, component.getHeight());
    }

    public abstract void positionChildren();

    @Override
    public void open(Inventory inventory) {
        children.forEach(c -> c.open(inventory));
    }
}
