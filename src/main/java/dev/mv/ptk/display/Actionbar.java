package dev.mv.ptk.display;

import dev.mv.utilsx.collection.Vec;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Actionbar {
    private BaseComponent contents;
    private BukkitTask task;
    private Vec<Player> players;
    private JavaPlugin plugin;

    public Actionbar(JavaPlugin plugin) {
        this.players = new Vec<>();
        this.plugin = plugin;
        enable();
    }

    public Actionbar(JavaPlugin plugin, BaseComponent contents) {
        this.players = new Vec<>();
        this.plugin = plugin;
        this.contents = contents;
        enable();
    }

    public Actionbar(JavaPlugin plugin, String contents) {
        this.players = new Vec<>();
        this.plugin = plugin;
        this.contents = new TextComponent(contents);
        enable();
    }

    public void setTextContents(String text) {
        contents = new TextComponent(text);
    }

    public void setContents(BaseComponent component) {
        contents = component;
    }

    public void addPlayer(Player player) {
        players.push(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void disable() {
        task.cancel();
    }

    public void enable() {
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (contents != null) {
                for (Player p : players) {
                    if (p.isOnline()) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, contents);
                    }
                }
            }
        }, 0, 1);
    }
}
