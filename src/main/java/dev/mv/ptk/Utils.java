package dev.mv.ptk;

import dev.mv.utilsx.UtilsX;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Utils {
    public static final Vec<Class<?>> CLASSES = new Vec<>();
    public static final HashMap<String, Vec<Class<?>>> PLUGIN_CLASSES = new HashMap<>();

    public static void loadClasses(PluginToolkit ptk) {
        try {
            File plugin = ptk.getJarFile();
            ClassLoader loader = PluginToolkit.class.getClassLoader();
            JarFile jar = new JarFile(plugin);
            Enumeration<JarEntry> entries = jar.entries();
            Vec<Class<?>> classes = new Vec<>();
            while (entries.hasMoreElements()) {
                try {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory()) continue;
                    if (UtilsX.containsAny(entry.getName(), "META-INF", "module-info", "package-info")) continue;
                    if (entry.getName().equals(JarFile.MANIFEST_NAME)) continue;
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().substring(0, entry.getName().length() - 6).replace('/', '.');
                        Class<?> clazz = loader.loadClass(className);
                        classes.push(clazz);
                    }
                } catch (Exception ignore) {}
            }
            PLUGIN_CLASSES.put(ptk.getName(), classes);
            CLASSES.append(classes);
        } catch (Exception ignore) {}
    }

    public static String chat(String msg, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(msg, args));
    }

    public static <T> void enumerateList(List<T> list, BiConsumer<Integer, T> indexed) {
        for (int i = 0; i < list.size(); i++) {
            indexed.accept(i, list.get(i));
        }
    }
}
