package dev.mv.test;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.gui.*;
import dev.mv.ptk.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public final class Test extends PluginToolkit {

    public void start() {
        require("commands").enable();
        InventoryInterface ii = new InventoryInterface("hello");
        BorderFrame border = new BorderFrame(DisplayBuilder.build(Material.NETHERITE_BLOCK).build());

        ItemButton button = new ItemButton(DisplayBuilder.build(Material.NETHER_STAR).withGlint().withTitle("Main Menu").build());

        button.addListener((_0, _1, isRight, p) -> {
            if (!isRight) {
                System.out.println("hello button");
            }
        });

        VFrame frame = new VFrame();
        frame.addComponent(button);
        border.addComponent(frame);
        ii.addComponent(border);

        World world = Bukkit.getWorld("main_world"); //idk

        State<Integer> players = new State<>(Bukkit.getOnlinePlayers().size());
    }
}
