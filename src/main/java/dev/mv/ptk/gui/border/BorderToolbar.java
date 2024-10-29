package dev.mv.ptk.gui.border;

import dev.mv.ptk.gui.*;
import dev.mv.ptk.style.UiStyle;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BorderToolbar {
    private HashMap<Integer, Component1x1> componentsTop = new HashMap<>();
    private HashMap<Integer, Component1x1> componentsBottom = new HashMap<>();
    private HashMap<Integer, Component1x1> componentsLeft = new HashMap<>();
    private HashMap<Integer, Component1x1> componentsRight = new HashMap<>();

    private ItemButton backBtn;
    private ItemButton closeBtn;
    private boolean closeEnabled = true;

    public BorderToolbar() {
        closeBtn = new ItemButton(null).withListener((slot, type, clicker) -> {
            clicker.closeInventory();
            return false;
        });
    }

    void setInterface(InventoryInterface ii) {
        componentsTop.values().forEach(c -> c.setInterface(ii));
        componentsBottom.values().forEach(c -> c.setInterface(ii));
        componentsLeft.values().forEach(c -> c.setInterface(ii));
        componentsRight.values().forEach(c -> c.setInterface(ii));
        if (backBtn != null) backBtn.setInterface(ii);
        if (closeBtn != null && closeEnabled) closeBtn.setInterface(ii);
    }

    boolean clickEvent(InventoryClickEvent e) {
        final boolean[] update = {false};
        componentsTop.values().forEach(c -> update[0] |= c.clickEvent(e));
        componentsBottom.values().forEach(c -> update[0] |= c.clickEvent(e));
        componentsLeft.values().forEach(c -> update[0] |= c.clickEvent(e));
        componentsRight.values().forEach(c -> update[0] |= c.clickEvent(e));
        if (backBtn != null) update[0] |= backBtn.clickEvent(e);
        if (closeBtn != null && closeEnabled) update[0] |= closeBtn.clickEvent(e);
        return update[0];
    }

    public enum Border {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT;

        public int resolvePos(int offset, int width, int height) {
            return switch (this) {
                case TOP -> offset;
                case BOTTOM -> offset + 9 * height - 9;
                case LEFT -> offset * 9;
                case RIGHT -> offset * 9 + width - 1;
            };
        }
    }

    public BorderToolbar withComponent(Component1x1 smallComponent, Border border, int offsetFromTopLeft) {
        switch (border) {
            case TOP -> componentsTop.put(offsetFromTopLeft, smallComponent);
            case BOTTOM -> componentsBottom.put(offsetFromTopLeft, smallComponent);
            case LEFT -> componentsLeft.put(offsetFromTopLeft, smallComponent);
            case RIGHT -> componentsRight.put(offsetFromTopLeft, smallComponent);
        }
        return this;
    }

    public BorderToolbar withLinkButton(Material material, Border border, int offsetFromTopLeft, InventoryInterface target) {
        return withLinkButton(DisplayBuilder.build(material).withTitle("&4Back").build(), border, offsetFromTopLeft, target);
    }

    public BorderToolbar withLinkButton(ItemStack button, Border border, int offsetFromTopLeft, InventoryInterface target) {
        ItemButton itemButton = new ItemButton(button);
        itemButton.addListener((slot, type, clicker) -> {
            if (target == null) {
                clicker.closeInventory();
            } else {
                target.open((Player) clicker);
            }
            return false;
        });
        return withComponent(itemButton, border, offsetFromTopLeft);
    }

    public BorderToolbar setBackButton(InventoryInterface target) {
        backBtn = new ItemButton(null).withListener((slot, type, clicker) -> {
            if (target == null) {
                clicker.closeInventory();
            } else {
                target.open((Player) clicker);
            }
            return false;
        });
        return this;
    }

    public BorderToolbar disableCloseButton() {
        closeBtn = null;
        return this;
    }

    public void open(Inventory inventory, UiStyle style) {
        componentsTop.values().forEach(c -> {
            c.open(inventory, style);
        });
        componentsBottom.values().forEach(c -> {
            c.open(inventory, style);
        });
        componentsLeft.values().forEach(c -> {
            c.open(inventory, style);
        });
        componentsRight.values().forEach(c -> {
            c.open(inventory, style);
        });

        if (backBtn != null) {
            backBtn.setDisplay(style.getBackButton());
            backBtn.open(inventory, style);
        }
        if (closeBtn != null && style.getCloseButton().isSome()) {
            closeBtn.setDisplay(style.getCloseButton().get());
            closeBtn.open(inventory, style);
        }

        closeEnabled = style.getCloseButton().isSome();
    }

    public void positionChildren(Component frame, UiStyle style) {
        componentsTop.forEach((s, c) -> {
            c.slot = frame.slot + s;
        });
        componentsBottom.forEach((s, c) -> {
            c.slot = frame.slot + s + 9 * frame.getHeight() - 9;
        });
        componentsLeft.forEach((s, c) -> {
            c.slot = frame.slot + s * 9;
        });
        componentsRight.forEach((s, c) -> {
            c.slot = frame.slot + s * 9 + frame.getWidth() - 1;
        });

        if (backBtn != null) {
            backBtn.slot = style.getBackButtonPosition().a.resolvePos(style.getBackButtonPosition().b, frame.getWidth(), frame.getHeight());
        }
        if (closeBtn != null && style.getCloseButton().isSome()) {
            closeBtn.slot = style.getCloseButtonPosition().get().a.resolvePos(style.getCloseButtonPosition().get().b, frame.getWidth(), frame.getHeight());
        }
    }
}
