package dev.mv.ptk.style.styles;

import dev.mv.ptk.gui.DisplayBuilder;
import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.ptk.style.UiStyle;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class MysticStyle extends UiStyle {

    @Override
    public ItemStack getStyleDisplayItem() {
        return DisplayBuilder
                .build(Material.DIAMOND)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&b&lMystic &3&lStyle")
                .withLore("&7Author: &aChatGPT-4o\n&aAn ethereal style for your UI\n&7Enchanted with mystical energies.")
                .build();
    }

    @Override
    public String getStyleName() {
        return "Mystic";
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
        return Material.FEATHER;
    }

    @Override
    public Material getBorder() {
        return Material.PURPLE_STAINED_GLASS_PANE;
    }

    @Override
    public ItemStack getBackButton() {
        return DisplayBuilder
                .build(Material.BARRIER)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&c&lBack")
                .withLore("&7Click to return to the previous menu")
                .build();
    }

    @Override
    public Pair<BorderToolbar.Border, Integer> getBackButtonPosition() {
        return new Pair<>(BorderToolbar.Border.BOTTOM, 1);
    }

    @Override
    public Option<ItemStack> getCloseButton() {
        return Option.some(
                DisplayBuilder
                        .build(Material.REDSTONE)
                        .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .withTitle("&4&lClose")
                        .withLore("&7Exit the menu")
                        .build()
        );
    }

    @Override
    public Option<Pair<BorderToolbar.Border, Integer>> getCloseButtonPosition() {
        return Option.some(new Pair<>(BorderToolbar.Border.BOTTOM, 7));
    }

    @Override
    public ItemStack getEnabled() {
        return DisplayBuilder
                .build(Material.LIME_DYE)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&a&lEnabled")
                .withLore("&7This feature is currently enabled")
                .build();
    }

    @Override
    public ItemStack getDisabled() {
        return DisplayBuilder
                .build(Material.GRAY_DYE)
                .withFlag(ItemFlag.HIDE_ATTRIBUTES)
                .withTitle("&7&lDisabled")
                .withLore("&7This feature is currently disabled")
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
        return "&e";
    }

    @Override
    public String getPrimarySystemFormat() {
        return "&b";
    }

    @Override
    public String getSecondarySystemFormat() {
        return "&7";
    }

    @Override
    public String getNumericHighlightFormat() {
        return "&e&l";
    }

    @Override
    public String getAlphabeticHighlightFormat() {
        return "&b&l";
    }

    @Override
    public String getSeparatorFormat() {
        return "&7";
    }

    @Override
    public String getPositiveFormat() {
        return "&a&l";
    }

    @Override
    public String getNegativeFormat() {
        return "&c&l";
    }
}