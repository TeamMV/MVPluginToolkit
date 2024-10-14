package dev.mv.ptk.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class CompoundComponent extends Component {
    protected List<Component> children = new ArrayList<>();
    protected int maxChildWidth, maxChildHeight;

    public void addComponent(Component component) {
        children.add(component);
        component.parent = this;
        maxChildWidth = Math.max(maxChildWidth, component.getWidth());
        maxChildHeight = Math.max(maxChildHeight, component.getHeight());
    }

    public abstract void positionChildren();

    @Override
    public void open(Inventory inventory) {
        children.forEach(c -> c.open(inventory));
    }

    public CompoundComponent with(Component component) {
        addComponent(component);
        return this;
    }

    @Override
    public void clickEvent(InventoryClickEvent e) {
        children.forEach(c -> c.clickEvent(e));
    }
}
