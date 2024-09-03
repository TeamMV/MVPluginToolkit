package dev.mv.ptk;

import dev.mv.utilsx.UtilsX;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.ChatColor;

public class Utils {
    public static final Vec<Class<?>> FILTERED_CLASSES = UtilsX.getAllClasses(i -> !UtilsX.containsAny(i, "com.google.j2objc", "com.google.errorprone",
            "com.google.code", "com.google.guava", "net.md_5.bungee", "org.spigotmc", "org.bukkit", "org.joml",
            "org.yaml", "org.checkerframework", "dev.mv.ptk", "javax.annotation", "org.jetbrains.annotations"));

    public static String chat(String msg, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(msg, args));
    }
}
