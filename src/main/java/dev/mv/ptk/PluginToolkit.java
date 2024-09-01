package dev.mv.ptk;

import dev.mv.ptk.command.CommandFinder;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginToolkit extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandFinder.findAndInitialize(this);
    }

    @Override
    public void onDisable() {

    }
}
