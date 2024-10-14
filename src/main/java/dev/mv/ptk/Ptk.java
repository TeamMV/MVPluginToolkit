package dev.mv.ptk;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Ptk extends PluginToolkit {

    @Override
    public void onEnable() {
        Utils.loadClasses(this);
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
    }

    @Override
    public void start() {}

    @Override
    public void onDisable() {}

    @Override
    public void stop() {}

}
