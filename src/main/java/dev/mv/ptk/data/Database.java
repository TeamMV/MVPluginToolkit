package dev.mv.ptk.data;

public interface Database {
    Table getTable(String name);

    void connect();
    void disconnect();
}
