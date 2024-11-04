package dev.mv.ptk.data;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.module.Module;

import java.util.HashMap;

public class Data extends Module {
    private HashMap<String, StorageNode> storageNodes = new HashMap<>();

    public Data(PluginToolkit toolkit) {
        super(toolkit);
    }

    @Override
    public String getId() {
        return "data";
    }

    public void registerStorageNode(String id, Database database) {
        storageNodes.put(id, new StorageNode(database));
    }

    public StorageNode getStorageNode(String id) {
        return storageNodes.get(id);
    }

    @Override
    public void enable() {}

    @Override
    public void disable() {
        storageNodes.values().forEach(StorageNode::disconnect);
    }
}
