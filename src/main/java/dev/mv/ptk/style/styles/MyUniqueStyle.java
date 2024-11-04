package dev.mv.ptk.style.styles;

import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.ptk.style.UiStyle;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class MyUniqueStyle extends UiStyle {
    @Override
    public ItemStack getStyleDisplayItem() {
        return DisplayBuilder
                .build(Material.DIAMOND)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&b&lEpic Style")
                .withLore("&7Author: &aChatGPT o1-preview\n&7The &6epic &7style for your plugin")
                .build();
    }

    @Override
    public String getStyleName() {
        return "EpicStyle";
    }

    @Override
    public Material getAddButton() {
        return Material.EMERALD;
    }

    @Override
    public Material getMenuButton() {
        return Material.NETHER_STAR;
    }

    @Override
    public Material getArrowButton() {
        return Material.SPECTRAL_ARROW;
    }

    @Override
    public Material getBorder() {
        return Material.PURPLE_STAINED_GLASS_PANE;
    }

    @Override
    public ItemStack getBackButton() {
        return DisplayBuilder
                .build(Material.REDSTONE_TORCH)
                .withTitle("&c&lBack")
                .build();
    }

    @Override
    public Pair<BorderToolbar.Border, Integer> getBackButtonPosition() {
        return new Pair<>(BorderToolbar.Border.BOTTOM, 0); // Bottom left corner
    }

    @Override
    public Option<ItemStack> getCloseButton() {
        ItemStack closeButton = DisplayBuilder
                .build(Material.BARRIER)
                .withTitle("&4&lClose")
                .build();
        return Option.some(closeButton);
    }

    @Override
    public Option<Pair<BorderToolbar.Border, Integer>> getCloseButtonPosition() {
        return Option.some(new Pair<>(BorderToolbar.Border.BOTTOM, 8)); // Bottom right corner
    }

    @Override
    public ItemStack getEnabled() {
        return DisplayBuilder
                .build(Material.LIME_DYE)
                .withTitle("&a&lEnabled")
                .build();
    }

    @Override
    public ItemStack getDisabled() {
        return DisplayBuilder
                .build(Material.GRAY_DYE)
                .withTitle("&7&lDisabled")
                .build();
    }

    @Override
    public String getErrorFormat() {
        return "&7";
    }

    @Override
    public String getDefaultFormat() {
        return "&f";
    }

    @Override
    public String getDebugFormat() {
        return "&e";
    }

    @Override
    public String getPrimarySystemFormat() {
        return "&6";
    }

    @Override
    public String getSecondarySystemFormat() {
        return "&7";
    }

    @Override
    public String getNumericHighlightFormat() {
        return "&b";
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
