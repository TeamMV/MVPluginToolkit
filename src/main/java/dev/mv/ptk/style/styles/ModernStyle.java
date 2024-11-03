package dev.mv.ptk.style.styles;

import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.ptk.style.UiStyle;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ModernStyle extends UiStyle {
    @Override
    public ItemStack getStyleDisplayItem() {
        return DisplayBuilder
                .build(Material.QUARTZ_BLOCK)
                .withTitle("&b&lModern")
                .withLore("&7Author: &amqxf")
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    @Override
    public String getStyleName() {
        return "modern";
    }

    @Override
    public Material getAddButton() {
        return Material.SUNFLOWER;
    }

    @Override
    public Material getMenuButton() {
        return Material.NETHER_STAR;
    }

    @Override
    public Material getArrowButton() {
        return Material.ARROW;
    }

    @Override
    public Material getBorder() {
        return Material.GRAY_STAINED_GLASS_PANE;
    }

    @Override
    public ItemStack getBackButton() {
        return DisplayBuilder.build(Material.ARROW)
                .withTitle("&fBack")
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    @Override
    public Pair<BorderToolbar.Border, Integer> getBackButtonPosition() {
        return new Pair<>(BorderToolbar.Border.BOTTOM, 3);
    }

    @Override
    public Option<ItemStack> getCloseButton() {
        return Option.some(DisplayBuilder.build(Material.BARRIER)
                .withTitle("&cClose")
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build());
    }

    @Override
    public Option<Pair<BorderToolbar.Border, Integer>> getCloseButtonPosition() {
        return Option.some(new Pair<>(BorderToolbar.Border.BOTTOM, 4));
    }

    @Override
    public ItemStack getEnabled() {
        return DisplayBuilder.build(Material.LIME_DYE)
                .withTitle("&aEnabled")
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();
    }

    @Override
    public ItemStack getDisabled() {
        return DisplayBuilder.build(Material.GRAY_DYE)
                .withTitle("&7Disabled")
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
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
        return "&8";
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
        return "&a";
    }

    @Override
    public String getAlphabeticHighlightFormat() {
        return "&b";
    }

    @Override
    public String getSeparatorFormat() {
        return "&9&l";
    }
}
