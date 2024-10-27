package dev.mv.ptk;

import dev.mv.ptk.utils.input.TextProvider;
import dev.mv.utilsx.UtilsX;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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

    public static String colorName(int i) {
        int idx = UtilsX.clamp(i, 0, 15);

        return switch (idx) {
            case 0 -> "black";
            case 1 -> "dark blue";
            case 2 -> "dark green";
            case 3 -> "dark aqua";
            case 4 -> "dark red";
            case 5 -> "dark purple";
            case 6 -> "gold";
            case 7 -> "gray";
            case 8 -> "dark gray";
            case 9 -> "blue";
            case 10 -> "green";
            case 11 -> "aqua";
            case 12 -> "red";
            case 13 -> "light purple";
            case 14 -> "yellow";
            case 15 -> "white";
            default -> throw new IllegalStateException("Cannot happen lmao");
        };
    }

    public static char colorChar(int i) {
        return "0123456789abcdef".charAt(UtilsX.clamp(i, 0, 15));
    }

    public static ChatColor chatColor(int i) {
        return ChatColor.values()[UtilsX.clamp(i, 0, 15)];
    }

    public static void removeAdvancements(Player player) {
        Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
        while (iterator.hasNext()) {
            AdvancementProgress progress = player.getAdvancementProgress(iterator.next());
            for (String criteria : progress.getAwardedCriteria())
                progress.revokeCriteria(criteria);
        }
    }

    public static void requestInput(TextProvider textProvider, Player player, String prompt, Consumer<String> output) {
        textProvider.setCloseCallback((s, _0) -> output.accept(s));
        textProvider.open(player, prompt);
    }
}
