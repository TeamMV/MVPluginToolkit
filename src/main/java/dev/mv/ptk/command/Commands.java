package dev.mv.ptk.command;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.module.Module;
import dev.mv.utilsx.UtilsX;
import org.bukkit.command.CommandExecutor;

public class Commands extends Module {
    public Commands(PluginToolkit toolkit) {
        super(toolkit);
    }

    @Override
    public void onEnable() {
        var classes = UtilsX.getAllClasses(i -> !UtilsX.containsAny(i, "com.google.j2objc", "com.google.errorprone",
                "com.google.code", "com.google.guava", "net.md_5.bungee", "org.spigotmc", "org.bukkit", "org.joml",
                "org.yaml", "org.checkerframework", "dev.mv.ptk", "javax.annotation", "org.jetbrains.annotations"));

        classes.iter()
            .filter(clazz -> AbstractCommand.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Command.class))
            .forEach(clazz -> {
                Command command = clazz.getAnnotation(Command.class);
                try {
                    toolkit.getCommand(command.value()).setExecutor((CommandExecutor) clazz.getConstructor().newInstance());
                } catch (Exception e) {
                    System.err.println("Failed to load command " + command.value());
                }
            });
    }

    @Override
    public String getId() {
        return "commands";
    }

    @Override
    public void onDisable() {}
}
