package dev.mv.ptk.gui;

import dev.mv.ptk.style.UiStyle;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.function.Function;

public abstract class CompoundComponent extends Component {
    protected Vec<Component> children = new Vec<>();
    protected int maxChildWidth, maxChildHeight;
    private boolean locked;
    private Vec<Integer> buffer = new Vec<>();

    public CompoundComponent(Component parent) {
        super(parent);
    }

    public void addComponent(Component component) {
        children.push(component);
        applyComponent(component);
    }

    private void applyComponent(Component component) {
        component.parent = this;
        maxChildWidth = Math.max(maxChildWidth, component.getWidth());
        maxChildHeight = Math.max(maxChildHeight, component.getHeight());
        component.setInterface(getInterface());
    }

    public void removeComponent(int idx) {
        if (locked) {
            buffer.push(idx);
            return;
        }
        Component c = children.remove(idx);
        c.setInterface(null);

        maxChildWidth = 0;
        maxChildHeight = 0;
        for (Component child : children) {
            if (child.getWidth() > maxChildHeight) maxChildWidth = child.getWidth();
            if (child.getHeight() > maxChildHeight) maxChildHeight = child.getHeight();
        }
    }

    public Vec<Component> getChildren() {
        return children;
    }

    public void insertComponent(int idx, Component component) {
        children.insert(idx, component);
        applyComponent(component);
    }

    public void setComponent(int idx, Component component) {
        children.replace(idx, component);
        applyComponent(component);
    }

    public Component getComponent(int idx) {
        return children.get(idx);
    }

    @Override
    public void setInterface(InventoryInterface ii) {
        super.setInterface(ii);

        locked = true;
        children.forEach(c -> c.setInterface(ii));
        locked = false;
        buffer.drain().forEach(this::removeComponent);
    }

    public abstract void positionChildren(UiStyle style);

    @Override
    public void open(Inventory inventory, UiStyle style) {
        locked = true;
        children.forEach(c -> c.open(inventory, style));
        locked = false;
        buffer.drain().forEach(this::removeComponent);
    }

    public CompoundComponent with(Component component) {
        addComponent(component);
        return this;
    }

    public CompoundComponent with(Function<CompoundComponent, Component> onReady) {
        addComponent(onReady.apply(this));
        return this;
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        boolean[] shouldUpdate = {false};
        locked = true;
        children.iterCopied().forEach(c -> {
            shouldUpdate[0] |= c.clickEvent(e);
        });
        locked = false;
        buffer.drain().forEach(this::removeComponent);

        if (shouldUpdate[0] && parent instanceof InventoryInterface ii) {
            ii.update((Player) e.getWhoClicked());
        }
        return shouldUpdate[0];
    }
}
