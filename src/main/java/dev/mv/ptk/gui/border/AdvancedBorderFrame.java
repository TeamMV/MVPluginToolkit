package dev.mv.ptk.gui.border;

import dev.mv.ptk.gui.*;
import dev.mv.ptk.style.UiStyle;
import dev.mv.ptk.utils.Null;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdvancedBorderFrame extends InsetFrame {
    private boolean topBorder, bottomBorder, leftBorder, rightBorder;
    private Material borderMaterial;
    private BorderToolbar toolbar;
    private boolean overwriteBorder = false;

    public AdvancedBorderFrame(boolean topBorder, boolean bottomBorder, boolean leftBorder, boolean rightBorder, BorderToolbar toolbar) {
        super(topBorder ? 1 : 0, leftBorder ? 1 : 0, bottomBorder ? 1 : 0, rightBorder ? 1 : 0);
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.toolbar = toolbar;
    }

    public AdvancedBorderFrame(boolean allBorders, BorderToolbar toolbar) {
        super(allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0);
        this.topBorder = allBorders;
        this.bottomBorder = allBorders;
        this.leftBorder = allBorders;
        this.rightBorder = allBorders;
        this.toolbar = toolbar;
    }

    public AdvancedBorderFrame(BorderToolbar toolbar) {
        super(1, 1, 1, 1);
        this.topBorder = true;
        this.bottomBorder = true;
        this.leftBorder = true;
        this.rightBorder = true;
        this.toolbar = toolbar;
    }

    public AdvancedBorderFrame(boolean topBorder, boolean bottomBorder, boolean leftBorder, boolean rightBorder) {
        super(topBorder ? 1 : 0, leftBorder ? 1 : 0, bottomBorder ? 1 : 0, rightBorder ? 1 : 0);
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    public AdvancedBorderFrame(boolean allBorders) {
        super(allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0);
        this.topBorder = allBorders;
        this.bottomBorder = allBorders;
        this.leftBorder = allBorders;
        this.rightBorder = allBorders;
    }

    public AdvancedBorderFrame() {
        super(1, 1, 1, 1);
        this.topBorder = true;
        this.bottomBorder = true;
        this.leftBorder = true;
        this.rightBorder = true;
    }

    public AdvancedBorderFrame(Component parent, boolean topBorder, boolean bottomBorder, boolean leftBorder, boolean rightBorder, BorderToolbar toolbar) {
        super(topBorder ? 1 : 0, leftBorder ? 1 : 0, bottomBorder ? 1 : 0, rightBorder ? 1 : 0, parent);
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.toolbar = toolbar;
    }

    public AdvancedBorderFrame(Component parent, boolean allBorders, BorderToolbar toolbar) {
        super(allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0, parent);
        this.topBorder = allBorders;
        this.bottomBorder = allBorders;
        this.leftBorder = allBorders;
        this.rightBorder = allBorders;
        this.toolbar = toolbar;
    }

    public AdvancedBorderFrame(Component parent, BorderToolbar toolbar) {
        super(1, 1, 1, 1, parent);
        this.topBorder = true;
        this.bottomBorder = true;
        this.leftBorder = true;
        this.rightBorder = true;
        this.toolbar = toolbar;
    }

    public AdvancedBorderFrame(Component parent, boolean topBorder, boolean bottomBorder, boolean leftBorder, boolean rightBorder) {
        super(topBorder ? 1 : 0, leftBorder ? 1 : 0, bottomBorder ? 1 : 0, rightBorder ? 1 : 0, parent);
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    public AdvancedBorderFrame(Component parent, boolean allBorders) {
        super(allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0, allBorders ? 1 : 0, parent);
        this.topBorder = allBorders;
        this.bottomBorder = allBorders;
        this.leftBorder = allBorders;
        this.rightBorder = allBorders;
    }

    public AdvancedBorderFrame(Component parent) {
        super(1, 1, 1, 1, parent);
        this.topBorder = true;
        this.bottomBorder = true;
        this.leftBorder = true;
        this.rightBorder = true;
    }

    public AdvancedBorderFrame withBorderMaterial(Material material) {
        this.borderMaterial = material;
        overwriteBorder = true;
        return this;
    }

    public AdvancedBorderFrame withDefaultToolbar() {
        this.toolbar = new BorderToolbar();
        return this;
    }

    public BorderToolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(BorderToolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void open(Inventory inventory, UiStyle style) {
        super.open(inventory, style);

        int width = getWidth();
        int height = getHeight();
        int size = width * height;

        if (!overwriteBorder) borderMaterial = style.getBorder();
        ItemStack display = DisplayBuilder.build(borderMaterial).withTitle("&7").build();

        // Top border
        if (topBorder) {
            for (int i = 0; i < width; i++) {
                inventory.setItem(map(i), display);
            }
        }

        // Bottom border
        if (bottomBorder) {
            for (int i = 0; i < width; i++) {
                inventory.setItem(map(size - i - 1), display);
            }
        }

        // Left and Right borders
        for (int i = 0; i < height; i++) {
            if (leftBorder) {
                inventory.setItem(map(i * width), display);
            }
            if (rightBorder) {
                try {
                    inventory.setItem(map(i * width + width - 1), display);
                } catch (Throwable ignore) {}
            }
        }

        if (toolbar != null) toolbar.open(inventory, style);
    }

    public int map(int slot) {
        int width = getWidth();
        int row = slot / width;
        int col = slot % width;
        int top = this.slot / 9;
        int left = this.slot % 9;

        return (row + top) * 9 + col + left;
    }

    @Override
    public void setInterface(InventoryInterface ii) {
        super.setInterface(ii);
        Null.not(toolbar, t -> t.setInterface(ii));
    }

    @Override
    public boolean clickEvent(InventoryClickEvent e) {
        boolean update = Null.notRet(toolbar, t -> t.clickEvent(e), false);
        return update | super.clickEvent(e); // use bitwise so call to super isnt discarded when update == true
    }

    @Override
    public void positionChildren(UiStyle style) {
        super.positionChildren(style);
        if (toolbar != null) toolbar.positionChildren(this, style);
    }
}
