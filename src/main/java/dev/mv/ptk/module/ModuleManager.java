package dev.mv.ptk.module;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.command.AbstractCommand;
import dev.mv.ptk.command.Command;
import dev.mv.utilsx.UtilsX;
import org.bukkit.command.CommandExecutor;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {
    private Map<String, Module> modules;
    private PluginToolkit toolkit;

    public ModuleManager(PluginToolkit toolkit) {
        this.toolkit = toolkit;
        modules = new HashMap<>();
    }

    public void registerModules() {
        var classes = UtilsX.getAllClasses(i -> !UtilsX.containsAny(i, "com.google.j2objc", "com.google.errorprone",
                "com.google.code", "com.google.guava", "net.md_5.bungee", "org.spigotmc", "org.bukkit", "org.joml",
                "org.yaml", "org.checkerframework", "dev.mv.ptk", "javax.annotation", "org.jetbrains.annotations"));

        classes.iter()
            .filter(Module.class::isAssignableFrom)
            .forEach(clazz -> {
                try {
                    registerModule((Module) clazz.getConstructor(PluginToolkit.class).newInstance(toolkit));
                } catch (Exception e) {
                    System.err.println("Failed to load module " + clazz.getSimpleName());
                }
            });
    }

    public void registerModule(Module module) {
        modules.put(module.getId(), module);
    }

    public Module findModuleById(String id) {
        return modules.get(id);
    }

    public <T extends Module> T findModuleByIdAsT(String id) {
        try {
            return (T) modules.get(id);
        } catch (ClassCastException e) {
            return null;
        }
    }
}
