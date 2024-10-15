package dev.mv.ptk.display;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;
import dev.mv.ptk.module.SingletonModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public final class Display extends SingletonModule {
    private static Display INSTANCE;

    private HashMap<Player, Sidebar> sidebars = new HashMap<>();
    private HashMap<Player, Actionbar> actionbars = new HashMap<>();
    private HashMap<Player, Bossbar> bossbars = new HashMap<>();

    private Display(PluginToolkit toolkit) {
        super(toolkit);
    }

    @Override
    public String getId() {
        return "display";
    }

    public static Display getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Display(Ptk.getInstance());
        }
        return INSTANCE;
    }

    public Actionbar getActionbar(Player player) {
        return actionbars.get(player);
    }

    public Bossbar getBossbar(Player player) {
        return bossbars.get(player);
    }

    public Sidebar getSidebar(Player player) {
        return sidebars.get(player);
    }

    public void addActionbar(Player player, Actionbar actionbar) {
        actionbars.put(player, actionbar);
        actionbar.addPlayer(player);
    }

    public void addBossbar(Player player, Bossbar bossbar) {
        bossbars.put(player, bossbar);
        bossbar.addPlayer(player);
    }

    public void addSidebar(Player player, Sidebar sidebar) {
        sidebars.put(player, sidebar);
        sidebar.addPlayer(player);
    }

    public void removeActionbar(Player player) {
        actionbars.remove(player).removePlayer(player);
    }

    public void removeBossbar(Player player) {
        bossbars.remove(player).removePlayer(player);
    }

    public void removeSidebar(Player player) {
        sidebars.remove(player);
    }

    @Override
    protected void clean() {
        bossbars.values().forEach(Bossbar::removeAllPlayers);
    }
}
