package dev.mv.ptk.gui.border;

import dev.mv.ptk.gui.Component;
import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.InsetFrame;
import dev.mv.ptk.gui.InventoryInterface;
import dev.mv.ptk.style.UiStyle;
import dev.mv.ptk.utils.Null;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BorderFrame extends InsetFrame {
    protected ItemStack display;
    private BorderToolbar toolbar;
    private boolean overwriteBorder = false;

    public BorderFrame() {
        super(1, 1, 1, 1);
    }

    public BorderFrame(Component parent) {
        super(1, 1, 1, 1, parent);
    }

    public BorderFrame(BorderToolbar toolbar) {
        super(1, 1, 1, 1);
        this.toolbar = toolbar;
    }

    public BorderFrame(Component parent, BorderToolbar toolbar) {
        super(1, 1, 1, 1, parent);
        this.toolbar = toolbar;
    }

    public BorderFrame(ItemStack display) {
        super(1, 1, 1, 1);
        this.display = display;
    }

    public BorderFrame(Component parent, ItemStack display) {
        super(1, 1, 1, 1, parent);
        this.display = display;
        this.overwriteBorder = true;
    }

    public BorderFrame(ItemStack display, BorderToolbar toolbar) {
        super(1, 1, 1, 1);
        this.display = display;
        this.toolbar = toolbar;
        this.overwriteBorder = true;
    }

    public BorderFrame(Component parent, ItemStack display, BorderToolbar toolbar) {
        super(1, 1, 1, 1, parent);
        this.display = display;
        this.toolbar = toolbar;
        this.overwriteBorder = true;
    }

    public BorderFrame withDefaultToolbar() {
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

        if (!overwriteBorder) this.display = DisplayBuilder.build(style.getBorder()).withTitle("&7").build();

        int width = getContentWidth() + 2;
        int height = getContentHeight() + 2;
        int size = width * height;

        for (int i = 0; i < width; i++) {
            inventory.setItem(map(i), display);
            inventory.setItem(map(size - width + i), display);
        }

        for (int i = 1; i < size / width - 1; i++) {
            inventory.setItem(map(i * width), display);
            inventory.setItem(map(i * width + width - 1), display);
        }

        if (toolbar != null) toolbar.open(inventory, style);
    }

    @Override
    public void positionChildren(UiStyle style) {
        super.positionChildren(style);
        if (toolbar != null) toolbar.positionChildren(this, style);
    }

    public int map(int slot) {
        int width = getContentWidth() + 2;
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
}
