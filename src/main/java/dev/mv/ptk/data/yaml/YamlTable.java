package dev.mv.ptk.data.yaml;

import dev.mv.ptk.data.Database;
import dev.mv.ptk.data.ResultData;
import dev.mv.ptk.data.Table;

public class YamlTable implements Table {

    private YamlDatabase parent;
    private String name;

    YamlTable(YamlDatabase parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public Database getDatabase() {
        return parent;
    }

    @Override
    public String getTableName() {
        return name;
    }

    @Override
    public String getIdField() {
        return "";
    }

    @Override
    public String get(String id, String key) {
        return "";
    }

    @Override
    public ResultData get(String id) {
        return null;
    }
}
