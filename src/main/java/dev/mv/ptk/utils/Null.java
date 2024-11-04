package dev.mv.ptk.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class Null {
    public static <T> void not(T nullable, Consumer<T> func) {
        if (nullable != null) {
            func.accept(nullable);
        }
    }

    public static <T> T or(T nullable, @NotNull T def) {
        return nullable == null ? def : nullable;
    }

    public static <T, R> R notRet(T nullable, Function<T, R> func) {
        if (nullable != null) {
            return func.apply(nullable);
        }
        return null;
    }

    public static <T, R> R notRet(T nullable, Function<T, R> func, R def) {
        if (nullable != null) {
            return func.apply(nullable);
        }
        return def;
    }
}
