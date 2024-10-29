package dev.mv.ptk.style.styles;

import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.ptk.style.UiStyle;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class CarbonStyle extends UiStyle {
    @Override
    public ItemStack getStyleDisplayItem() {
        return DisplayBuilder
                .build(Material.COAL)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&8&lCarbon")
                .withLore("&7Author: &av22minecraft").build();
    }

    @Override
    public String getStyleName() {
        return "carbon";
    }

    @Override
    public Material getAddButton() {
        return Material.SUNFLOWER;
    }

    @Override
    public Material getMenuButton() {
        return Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE;
    }

    @Override
    public Material getArrowButton() {
        return Material.BLACK_CANDLE;
    }

    @Override
    public Material getBorder() {
        return Material.BLACK_STAINED_GLASS_PANE;
    }

    @Override
    public ItemStack getBackButton() {
        return DisplayBuilder
                .build(Material.RED_STAINED_GLASS_PANE)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&4&lBack")
                .build();
    }

    @Override
    public Pair<BorderToolbar.Border, Integer> getBackButtonPosition() {
        return new Pair<>(BorderToolbar.Border.TOP, 0);
    }

    @Override
    public Option<ItemStack> getCloseButton() {
        return Option.some(DisplayBuilder
                .build(Material.RED_STAINED_GLASS_PANE)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&4&lClose")
                .build());
    }

    @Override
    public Option<Pair<BorderToolbar.Border, Integer>> getCloseButtonPosition() {
        return Option.some(new Pair<>(BorderToolbar.Border.TOP, 8));
    }

    @Override
    public ItemStack getEnabled() {
        return DisplayBuilder
                .build(Material.GREEN_TERRACOTTA)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&2&lEnabled")
                .build();
    }

    @Override
    public ItemStack getDisabled() {
        return DisplayBuilder
                .build(Material.RED_TERRACOTTA)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&2&lDisabled")
                .build();
    }
}
