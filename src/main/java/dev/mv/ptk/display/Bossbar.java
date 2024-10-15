package dev.mv.ptk.display;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bossbar {
    private BossBar bossBar;

    public Bossbar() {
        this("");
    }

    public Bossbar(String content) {
        this(content, BarColor.GREEN, BarStyle.SOLID);
    }

    public Bossbar(String content, BarColor color) {
        this(content, color, BarStyle.SOLID);
    }

    public Bossbar(String content, BarColor color, BarStyle style) {
        this.bossBar = Bukkit.createBossBar(content, color, style);
    }

    public void setProgress(double progress) {
        bossBar.setProgress(progress);
    }

    public void setTextContents(String contents) {
        bossBar.setTitle(contents);
    }

    public void disable() {
        bossBar.setVisible(false);
    }

    public void enable() {
        bossBar.setVisible(true);
    }

    public void addPlayer(Player player) {
        bossBar.addPlayer(player);
    }

    public void removePlayer(Player player) {
        bossBar.removePlayer(player);
    }

    public void removeAllPlayers() {
        bossBar.removeAll();
    }
}
