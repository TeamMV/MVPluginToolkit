package dev.mv.test;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.gui.*;
import org.bukkit.Material;

public final class Test extends PluginToolkit {

    public void start() {
        require("commands").enable();

        InventoryInterface ii = new InventoryInterface();
        BorderFrame border = new BorderFrame(DisplayBuilder.build(Material.NETHERITE_BLOCK).build());

        ItemButton button = new ItemButton(DisplayBuilder.build(Material.NETHER_STAR).withGlint().withTitle("Main Menu").build());

        button.addListener((_, _, isRight) -> {
            if (!isRight) {
                System.out.println("hello button");
            }
        });

        VFrame frame = new VFrame();
        frame.addComponent(button);
        border.addComponent(frame);
        ii.addComponent(border);
    }

}
