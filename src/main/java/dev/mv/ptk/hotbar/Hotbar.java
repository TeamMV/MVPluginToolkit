package dev.mv.ptk.hotbar;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;
import dev.mv.ptk.module.SingletonModule;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Hotbar extends SingletonModule {
    private static Hotbar INSTANCE;
    private HashMap<Player, PlayerHotbar> hotbars;

    protected Hotbar(PluginToolkit toolkit) {
        super(toolkit);
        hotbars = new HashMap<>();
    }

    public PlayerHotbar get(Player player) {
        if (hotbars.containsKey(player)) {{
            hotbars.put(player, new PlayerHotbar(player));
        }}
        return hotbars.get(player);
    }

    @Override
    protected void clean() {
        hotbars.values().forEach(PlayerHotbar::clear);
        hotbars.clear();
    }

    @Override
    public String getId() {
        return "hotbar";
    }

    public static Hotbar getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Hotbar(Ptk.getInstance());
        }
        return INSTANCE;
    }
}
