package dev.mv.ptk.utils;

import dev.mv.ptk.PluginListener;
import dev.mv.utilsx.UtilsX;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class UseItem {
    private Material item;
    private Consumer<Player> useCallback;
    private long index;
    private Player player;

    public UseItem(Player player, Material item, Consumer<Player> useCallback) {
        this.player = player;
        this.item = item;
        this.useCallback = useCallback;
        this.index = UtilsX.nextId("MVPTK::USEITEM");
        PluginListener.USE_ITEMS.put(index, this);
    }

    public Material getItem() {
        return item;
    }

    public void onUse() {
        if (useCallback != null) useCallback.accept(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void discard() {
        PluginListener.USE_ITEMS.remove(index);
    }
}
