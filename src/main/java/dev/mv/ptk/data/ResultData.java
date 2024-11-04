package dev.mv.ptk.data;

public interface ResultData {
    String[] getAll();

    String get(String key);

    String[] get(Filter filter); //(field_name, value)

    interface Filter {
        boolean valid(String key, String value);
    }
}
