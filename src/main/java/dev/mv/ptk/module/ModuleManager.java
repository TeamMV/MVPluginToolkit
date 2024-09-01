package dev.mv.ptk.module;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {
    private Map<String, Module> modules;

    public ModuleManager() {
        modules = new HashMap<>();
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
        } except (ClassCastException e) {
            return null;
        }
    }
}
