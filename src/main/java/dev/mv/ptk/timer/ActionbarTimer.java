package dev.mv.ptk.timer;

import dev.mv.ptk.display.Actionbar;
import dev.mv.ptk.display.Display;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ActionbarTimer extends BaseTimer {
    private Vec<Player> subscribed;
    private Actionbar actionbar;
    private Formatter formatter;

    public ActionbarTimer(JavaPlugin plugin) {
        this(plugin, Formatter.ofColorCode("&6"));
    }

    public ActionbarTimer(JavaPlugin plugin, Formatter formatter) {
        super(plugin);
        this.formatter = formatter;
        subscribed = new Vec<>();
        actionbar = new Actionbar(plugin, formatter.idle());
    }

    public void subscribePlayer(Player player) {
        subscribed.push(player);
        Display.getInstance().addActionbar(player, actionbar);
    }

    public void unsubscribePlayer(Player player) {
        subscribed.remove(player);
        Display.getInstance().removeActionbar(player);
    }

    @Override
    public void timeChanged(long remainingTotalSecs, int hours, int minutes, int seconds) {
        String fmt = formatter.format(hours, minutes, seconds);
        actionbar.setTextContents(fmt);
    }
}
