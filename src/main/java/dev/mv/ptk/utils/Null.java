package dev.mv.ptk.utils;

import java.util.function.Consumer;

public class Null {
    public static <T> void not(T nullable, Consumer<T> func) {
        if (nullable != null) {
            func.accept(nullable);
        }
    }
}
