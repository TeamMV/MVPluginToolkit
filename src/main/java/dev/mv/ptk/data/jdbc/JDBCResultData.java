package dev.mv.ptk.data.jdbc;

import dev.mv.ptk.data.ResultData;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class JDBCResultData implements ResultData {
    private HashMap<String, String> values;

    JDBCResultData(ResultSet resultSet) {
        values = new HashMap<>();
        try {
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                values.put(resultSet.getMetaData().getColumnLabel(i), resultSet.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] getAll() {
        return values.values().toArray(String[]::new);
    }

    @Override
    public String get(String key) {
        return values.get(key);
    }

    @Override
    public String[] get(Filter filter) {
        return values
                .entrySet()
                .stream()
                .filter((e) -> filter.valid(e.getKey(), e.getValue()))
                .map(Map.Entry::getValue)
                .toArray(String[]::new);
    }
}
