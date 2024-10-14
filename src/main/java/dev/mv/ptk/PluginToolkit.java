package dev.mv.ptk;

import dev.mv.ptk.module.Module;
import dev.mv.ptk.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class PluginToolkit extends JavaPlugin {

    private ModuleManager modules;

    @Override
    public void onEnable() {
        Utils.loadClasses(this);
        modules = new ModuleManager(this);
        modules.registerModules();

        start();
    }
    
    public abstract void start();

    @Override
    public void onDisable() {
        stop();
    }

    public abstract void stop();

    public ModuleManager getModules() {
        return modules;
    }

    public <T extends Module> T require(String module) {
        return modules.require(module);
    }

    public File getJarFile() {
        return getFile();
    }
}
