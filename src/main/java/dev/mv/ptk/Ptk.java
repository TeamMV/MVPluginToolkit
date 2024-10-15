package dev.mv.ptk;

import dev.mv.ptk.module.Module;
import dev.mv.ptk.module.SingletonModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Ptk extends PluginToolkit {

    private static Ptk INSTANCE;
    private boolean canUnload = false;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Utils.loadClasses(this);
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
    }

    @Override
    public void start() {}

    @Override
    public void onDisable() {
        canUnload = true;
        Utils.CLASSES.iter()
            .filter(SingletonModule.class::isAssignableFrom)
            .forEach(clazz -> {
                try {
                    SingletonModule module = (SingletonModule) clazz.getMethod("getInstance").invoke(null);
                    module.stop();
                } catch (Exception e) {
                    System.err.println("Failed to unload module " + clazz.getSimpleName());
                }
            });
    }

    @Override
    public void stop() {}

    public boolean canUnload() {
        return canUnload;
    }

    public static Ptk getInstance() {
        return INSTANCE;
    }

}
