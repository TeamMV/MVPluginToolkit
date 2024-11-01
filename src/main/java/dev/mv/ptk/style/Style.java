package dev.mv.ptk.style;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;
import dev.mv.ptk.Utils;
import dev.mv.ptk.module.SingletonModule;
import dev.mv.ptk.style.styles.CarbonStyle;
import dev.mv.ptk.utils.Null;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.UUID;

public class Style extends SingletonModule {
    private static Style INSTANCE;

    public static HashMap<String, UiStyle> PROVIDED = new HashMap<>();

    private HashMap<UUID, UiStyle> styles = new HashMap<>();

    private Style(PluginToolkit toolkit) {
        super(toolkit);
        toolkit.saveDefaultConfig();
        FileConfiguration config = toolkit.getConfig();

        Utils.CLASSES.iterCopied()
            .filter(UiStyle.class::isAssignableFrom)
            .forEach(clazz -> {
                if (clazz.equals(UiStyle.class)) return;
                try {
                    UiStyle style = (UiStyle) clazz.getConstructor().newInstance();
                    PROVIDED.put(style.getStyleName(), style);
                } catch (Exception e) {
                    System.err.println("Failed to load style " + clazz.getSimpleName());
                }
            });

        ConfigurationSection section = config.getConfigurationSection("uiStyle");
        if (section == null) {
            section = config.createSection("uiStyle");
        }
        for (String key : section.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                String style = config.getString("uiStyle." + key);
                Null.not(PROVIDED.get(style), s -> styles.put(uuid, s));
            } catch (Exception ignore) {}
        }
    }

    public UiStyle getStyle(UUID uuid) {
        if (!styles.containsKey(uuid)) {
            setStyle(uuid, PROVIDED.get("carbon"));
        }
        return styles.get(uuid);
    }

    public void setStyle(UUID uuid, UiStyle style) {
        styles.put(uuid, style);
        toolkit.getConfig().set("uiStyle." + uuid.toString(), style.getStyleName());
    }

    @Override
    protected void clean() {
        toolkit.saveConfig();
    }

    @Override
    public String getId() {
        return "style";
    }

    public static Style getInstance() {
        if (INSTANCE == null) INSTANCE = new Style(Ptk.getInstance());
        return INSTANCE;
    }
}
