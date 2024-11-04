package dev.mv.ptk.data;

public class StorageNode {

    protected Database database;

    public StorageNode(Database database) {
        this.database = database;
        database.connect();
    }

    public final void disconnect() {
        database.disconnect();
    }
}
