package dev.mv.ptk.gui;

import dev.mv.ptk.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DisplayBuilder {
    private ItemStack stack;
    private ItemMeta meta;

    private DisplayBuilder(Material material) {
        stack = new ItemStack(material);
        meta = stack.getItemMeta();
    }

    public static DisplayBuilder build(Material material) {
        return new DisplayBuilder(material);
    }

    public DisplayBuilder withAmount(int amt) {
        stack.setAmount(amt);
        return this;
    }

    public DisplayBuilder withGlint() {
        meta.addEnchant(Enchantment.PROTECTION_FALL, 10, false);
        return this;
    }

    public DisplayBuilder withLore(String unformattedLore, Object... args) {
        meta.setLore(Arrays.stream(Utils.chat(unformattedLore, args).split("\\n")).toList());
        return this;
    }

    public DisplayBuilder withTitle(String unformattedTitle, Object... args) {
        meta.setDisplayName(Utils.chat(unformattedTitle, args));
        return this;
    }

    public DisplayBuilder withUnbreakable() {
        meta.setUnbreakable(true);
        return this;
    }

    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}
