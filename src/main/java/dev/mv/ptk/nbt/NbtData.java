package dev.mv.ptk.nbt;

import dev.mv.ptk.PluginToolkit;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.ListPersistentDataTypeProvider;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class NbtData {

    private PluginToolkit toolkit;
    private PersistentDataContainer container;

    NbtData(PluginToolkit toolkit, PersistentDataContainer container) {
        this.toolkit = toolkit;
        this.container = container;
    }

    public boolean hasChild(String key) {
        return container.has(new NamespacedKey(toolkit, key), PersistentDataType.TAG_CONTAINER);
    }

    public NbtData getChild(String key) {
        return new NbtData(toolkit, container.get(new NamespacedKey(toolkit, key), PersistentDataType.TAG_CONTAINER));
    }

    public void setChild(String key, NbtData child) {
        container.set(new NamespacedKey(toolkit, key), PersistentDataType.TAG_CONTAINER, child.container);
    }

    public NbtData createChild(String key) {
        container.set(new NamespacedKey(toolkit, key), PersistentDataType.TAG_CONTAINER, container.getAdapterContext().newPersistentDataContainer());
        return getChild(key);
    }

    public NbtArray<NbtData> getChildArray(String key) {
        container.get(new NamespacedKey(toolkit, key), PersistentDataType.LIST.booleans());
        return null;
    }

    public void removeValue(String key) {
        container.remove(new NamespacedKey(toolkit, key));
    }

    public void clear() {
        container.getKeys().forEach(container::remove);
    }

    public boolean has(String key) {
        return container.has(new NamespacedKey(toolkit, key));
    }

    public boolean isEmpty() {
        return container.isEmpty();
    }

    public void copyTo(NbtData other, boolean replace) {
        container.copyTo(other.container, replace);
    }

}
