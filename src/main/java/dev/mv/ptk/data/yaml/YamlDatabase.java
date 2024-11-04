package dev.mv.ptk.data.yaml;

import dev.mv.ptk.data.Database;
import dev.mv.ptk.data.Table;

public class YamlDatabase implements Database {

    public YamlDatabase() {

    }

    @Override
    public Table getTable(String name) {
        return new YamlTable(this, name);
    }

    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }
}
