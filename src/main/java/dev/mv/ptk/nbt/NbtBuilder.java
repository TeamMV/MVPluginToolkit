package dev.mv.ptk.nbt;

import dev.mv.ptk.Ptk;
import org.bukkit.persistence.PersistentDataHolder;

import java.util.function.Consumer;

public class NbtBuilder {
    private PersistentDataHolder holder;
    private NbtData data;

    public NbtBuilder(PersistentDataHolder holder) {
        this.holder = holder;
        this.data = new NbtData(Ptk.getInstance(), holder.getPersistentDataContainer());
    }

    public NbtBuilder api(Consumer<NbtData> cb) {
        cb.accept(data);
        return this;
    }

    public <T extends PersistentDataHolder> T finish() {
        return (T) holder;
    }
}
