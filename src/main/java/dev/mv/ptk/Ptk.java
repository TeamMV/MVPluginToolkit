package dev.mv.ptk;

import dev.mv.ptk.module.SingletonModule;
import dev.mv.ptk.sudo.PwListener;
import dev.mv.ptk.sudo.SudoCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class Ptk extends PluginToolkit {
    private String sudoPassword;
    private static Ptk INSTANCE;
    private boolean canUnload = false;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();
        FileConfiguration config = getConfig();
        config.addDefault("sudo.pass", "<your sudo password>");

        sudoPassword = config.getString("sudo.pass");

        Utils.loadClasses(this);
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getPluginManager().registerEvents(new PwListener(), this);

        getCommand("sudo").setExecutor(new SudoCommand());
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

    public String getSudoPass() {
        return sudoPassword;
    }
}
