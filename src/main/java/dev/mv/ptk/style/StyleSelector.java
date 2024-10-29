package dev.mv.ptk.style;

import dev.mv.ptk.gui.*;
import dev.mv.ptk.gui.border.AdvancedBorderFrame;
import dev.mv.ptk.gui.border.BorderToolbar;
import dev.mv.ptk.utils.State;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StyleSelector {

    private InventoryInterface ui;
    private InventoryInterface demo;
    private Player player;
    private int offset = 0;

    private Style style = Style.getInstance();

    public StyleSelector(Player player) {
        this.player = player;
        ui = new InventoryInterface("Select UI style", 6);
        demo = new InventoryInterface("Hello Inventory");
        demo.addComponent(new ItemButton(DisplayBuilder.build(Material.AIR).build()));
        ui.addComponent(createSelector(style.getStyle(player.getUniqueId())));
    }

    private Component createSelector(UiStyle playerStyle) {

        FlowFrame demoFrame = new FlowFrame();

        State<Integer> state = new State<>(0);

        demoFrame.addComponent(new ItemToggle(demoFrame, DisplayBuilder.build(Material.LEVER).withTitle("&fHello Toggle").build()));
        demoFrame.addComponent(new ItemStateDisplay(demoFrame, DisplayBuilder.build(Material.PAPER).withLore("&fHello State Display").build(), state));
        demoFrame.addComponent(new ItemButton(DisplayBuilder.build(playerStyle.getAddButton()).withTitle("&aHello Button").build()).withListener((_0, _1, c) -> {
            state.write((Player) c, i -> i + 1);
            return true;
        }));
        demoFrame.addComponent(new ItemInput(demoFrame, DisplayBuilder.build(Material.OAK_SIGN).withTitle("&fHello Input").build(), "Hello Sign"));
        demoFrame.addComponent(new ItemButton(DisplayBuilder.build(playerStyle.getMenuButton()).withTitle("&bHello Menu").build()));

        for (int i = 1; i < 80; i++) {
            demoFrame.addComponent(new ItemButton(DisplayBuilder.build(Material.values()[i]).withTitle("Hello Random Item").build()));
        }

        demo.setComponent(0, new AdvancedBorderFrame(demo, true, true, true, false, new BorderToolbar().setBackButton(ui))
                .with(p -> new HScrollFrame(p).with(demoFrame))
        );

        FlowFrame frame = new FlowFrame();
        Component contents = new AdvancedBorderFrame(ui, true, true, true, false, new BorderToolbar()
                .withComponent(new ItemButton(DisplayBuilder.build(playerStyle.getMenuButton()).withTitle("&fPreview").build()).withListener((_0, _1, c) -> {
                    demo.open((Player) c);
                    return false;
                }), BorderToolbar.Border.TOP, 4)
                )
                .with(p -> new HScrollFrame(p, offset).with(frame));

        for (UiStyle s : Style.PROVIDED.values()) {
            ItemStack item = s.getStyleDisplayItem();
            if (s == playerStyle) {
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                item.setItemMeta(meta);
            }
            frame.addComponent(new ItemButton(item).withListener((_0, _1, clicker) -> updateStyle((Player) clicker, s)));
        }

        return contents;
    }

    private boolean updateStyle(Player player, UiStyle playerStyle) {
        style.setStyle(player.getUniqueId(), playerStyle);

        offset = ((HScrollFrame) ((CompoundComponent) ui.getComponent(0)).getComponent(0)).getOffset();
        ui.setComponent(0, createSelector(playerStyle));

        return true;
    }

    public void open() {
        ui.open(player);
    }
}
