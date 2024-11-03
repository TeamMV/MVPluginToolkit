package dev.mv.ptk.style;

import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class UiStyle {
    public abstract ItemStack getStyleDisplayItem();
    public abstract String getStyleName();

    public abstract Material getAddButton();
    public abstract Material getMenuButton();

    public abstract Material getArrowButton();
    public abstract Material getBorder();
    public abstract ItemStack getBackButton();
    public abstract Pair<BorderToolbar.Border, Integer> getBackButtonPosition();
    public abstract Option<ItemStack> getCloseButton();
    public abstract Option<Pair<BorderToolbar.Border, Integer>> getCloseButtonPosition();
    public abstract ItemStack getEnabled();
    public abstract ItemStack getDisabled();

    public abstract String getErrorFormat();
    public abstract String getDefaultFormat();
    public abstract String getDebugFormat();
    public abstract String getPrimarySystemFormat();
    public abstract String getSecondarySystemFormat();
    public abstract String getNumericHighlightFormat();
    public abstract String getAlphabeticHighlightFormat();
    public abstract String getSeparatorFormat();
}
