package dev.mv.ptk.nbt;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;
import dev.mv.ptk.module.SingletonModule;
import org.bukkit.persistence.PersistentDataHolder;

public class Nbt extends SingletonModule {
    private static Nbt INSTANCE;

    private Nbt(PluginToolkit toolkit) {
        super(toolkit);
    }

    public NbtData getNbt(PersistentDataHolder item) {
        return new NbtData(toolkit, item.getPersistentDataContainer());
    }

    @Override
    protected void clean() {}

    @Override
    public String getId() {
        return "nbt";
    }

    public static Nbt getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Nbt(Ptk.getInstance());
        }
        return INSTANCE;
    }
}
