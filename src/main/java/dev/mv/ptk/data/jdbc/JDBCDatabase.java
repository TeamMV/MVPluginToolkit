package dev.mv.ptk.data.jdbc;

import dev.mv.ptk.data.Database;
import dev.mv.ptk.data.Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDatabase implements Database {
    private Connection connection;
    private String driver;

    public JDBCDatabase(String driver) {
        this.driver = driver;
    }

    @Override
    public Table getTable(String name) {
        return new JDBCTable(this, name, "ID"); //maybe replace ID with some param
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(driver);
        } catch (SQLException e) {
            e.printStackTrace();
            //more robust logging
        }
    }

    @Override
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //more robust logging
        }
    }

    Connection getConnection() {
        return connection;
    }
}
