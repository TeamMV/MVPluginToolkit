package dev.mv.ptk.data;

public interface Table {
    Database getDatabase();

    String getTableName();

    String getIdField();

    String get(String id, String key);

    ResultData get(String id);
}
