package dev.mv.ptk.timer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class BaseTimer {
    private JavaPlugin plugin;

    private long seconds;
    private boolean isRunning;
    private boolean isDone;
    private BukkitTask task;

    public BaseTimer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void timeChanged(long remainingTotalSecs, int hours, int minutes, int seconds);

    private void setupTask() {
        if (task == null) {
            task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                seconds--;
                timeChanged(seconds, (int) (seconds / 3600), (int) ((seconds % 3600) / 60), (int) (seconds % 60));
            }, 0, 20);
        }
    }

    public void setTime(long seconds) {
        this.seconds = seconds;
        if (task == null) setupTask();
    }

    public void setTime(int hours, int minutes, int seconds) {
        this.seconds = seconds + minutes * 60L + hours * 3600L;
        if (task == null) setupTask();
    }

    public void pause() {
        this.isRunning = false;
        this.task.cancel();
        this.task = null;
    }

    public void resume() {
        this.isRunning = true;
        if (!isDone) {
            setupTask();
        }
    }

    public void stop() {
        this.isRunning = false;
        this.isDone = true;
        this.task.cancel();
        this.task = null;
    }

    public long getRemainingSeconds() {
        return seconds;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isDone() {
        return isDone;
    }

    public void clean() {
        task.cancel();
        task = null;
    }
}
