package dev.mv.ptk;

import dev.mv.ptk.module.Module;
import dev.mv.ptk.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginToolkit extends JavaPlugin {

    private ModuleManager modules;

    @Override
    public void onEnable() {
        modules = new ModuleManager(this);
        modules.registerModules();
    }

    @Override
    public void onDisable() {

    }

    public ModuleManager getModules() {
        return modules;
    }

    public <T extends Module> T require(String module) {
        return modules.require(module);
    }
}
