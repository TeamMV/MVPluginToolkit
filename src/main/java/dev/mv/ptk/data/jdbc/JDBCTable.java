package dev.mv.ptk.data.jdbc;

import dev.mv.ptk.data.Database;
import dev.mv.ptk.data.ResultData;
import dev.mv.ptk.data.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTable implements Table {
    private JDBCDatabase database;
    private String tableName;
    private String idField;

    JDBCTable(JDBCDatabase database, String tableName, String idField) {
        this.database = database;
        this.tableName = tableName;
        this.idField = idField;
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getIdField() {
        return idField;
    }

    @Override
    public String get(String id, String key) {
        try {
            Statement stmt = database.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT %s FROM %s WHERE %s IS %s".formatted(key, getTableName(), getIdField(), id));
            rs.getString(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "err:1";
    }

    @Override
    public ResultData get(String id) {
        return null;
    }
}
