package dev.mv.ptk.module;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Utils;

import java.util.HashMap;

public class ModuleManager {
    private HashMap<String, Module> modules;
    private PluginToolkit toolkit;

    public ModuleManager(PluginToolkit toolkit) {
        this.toolkit = toolkit;
        modules = new HashMap<>();
    }

    public void registerModules() {
        Utils.CLASSES.iterCopied()
            .filter(Module.class::isAssignableFrom)
            .forEach(clazz -> {
                if (clazz.equals(Module.class)) return;
                try {
                    registerModule((Module) clazz.getConstructor(PluginToolkit.class).newInstance(toolkit));
                } catch (Exception e) {
                    System.err.println("Failed to load module " + clazz.getSimpleName());
                }
            });
    }

    private void registerModule(Module module) {
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

    public <T extends Module> T require(String module) {
        return (T) modules.get(module);
    }
}
