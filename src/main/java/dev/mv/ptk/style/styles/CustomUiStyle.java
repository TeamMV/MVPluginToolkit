package dev.mv.ptk.style.styles;

import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.ptk.style.UiStyle;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class CustomUiStyle extends UiStyle {

    @Override
    public ItemStack getStyleDisplayItem() {
        return DisplayBuilder
                .build(Material.DIAMOND)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&b&lCustom Style")
                .withLore("&7A custom style for the UI\n&7Enhanced visuals and icons\n&7Author: &aChatGPT 3.5")
                .build();
    }

    @Override
    public String getStyleName() {
        return "&bCustom UI Style";
    }

    @Override
    public Material getAddButton() {
        return Material.GREEN_CONCRETE;
    }

    @Override
    public Material getMenuButton() {
        return Material.BOOK;
    }

    @Override
    public Material getArrowButton() {
        return Material.ARROW;
    }

    @Override
    public Material getBorder() {
        return Material.BLUE_STAINED_GLASS_PANE;
    }

    @Override
    public ItemStack getBackButton() {
        return DisplayBuilder
                .build(Material.REDSTONE_TORCH)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&cBack")
                .withLore("&7Return to the previous menu")
                .build();
    }

    @Override
    public Pair<BorderToolbar.Border, Integer> getBackButtonPosition() {
        return new Pair<>(BorderToolbar.Border.BOTTOM, 0); // Position at the bottom-left corner
    }

    @Override
    public Option<ItemStack> getCloseButton() {
        return Option.some(DisplayBuilder
                .build(Material.BARRIER)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&cClose")
                .withLore("&7Close this menu")
                .build());
    }

    @Override
    public Option<Pair<BorderToolbar.Border, Integer>> getCloseButtonPosition() {
        return Option.some(new Pair<>(BorderToolbar.Border.BOTTOM, 8)); // Position at the bottom-right corner
    }

    @Override
    public ItemStack getEnabled() {
        return DisplayBuilder
                .build(Material.LIME_DYE)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&aEnabled")
                .withLore("&7This option is enabled")
                .build();
    }

    @Override
    public ItemStack getDisabled() {
        return DisplayBuilder
                .build(Material.GRAY_DYE)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&7Disabled")
                .withLore("&7This option is disabled")
                .build();
    }

    @Override
    public String getErrorFormat() {
        return "&c";
    }

    @Override
    public String getDefaultFormat() {
        return "&7";
    }

    @Override
    public String getDebugFormat() {
        return "&b";
    }

    @Override
    public String getPrimarySystemFormat() {
        return "&e";
    }

    @Override
    public String getSecondarySystemFormat() {
        return "&f";
    }

    @Override
    public String getNumericHighlightFormat() {
        return "&6";
    }

    @Override
    public String getAlphabeticHighlightFormat() {
        return "&d";
    }

    @Override
    public String getSeparatorFormat() {
        return "&8";
    }

    @Override
    public String getPositiveFormat() {
        return "&a";
    }

    @Override
    public String getNegativeFormat() {
        return "&c";
    }
}

