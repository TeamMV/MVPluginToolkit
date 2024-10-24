package dev.mv.ptk.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class State<T> {
    private T t;
    private final List<Observer> observers;

    public State(T t) {
        this.t = t;
        observers = new ArrayList<>();
    }

    public T read() {
        return t;
    }

    public void modify(Player player, Consumer<T> fn) {
        fn.accept(t);
        observers.forEach(o -> o.onChange(player));
    }

    public void write(Player player, T t) {
        this.t = t;
        observers.forEach(o -> o.onChange(player));
    }

    public void write(Player player, UnaryOperator<T> fn) {
        this.t = fn.apply(t);
        observers.forEach(o -> o.onChange(player));
    }

    public void observe(State<T>.Observer observer) {
        this.observers.add(observer);
    }

    public class Observer {
        private State<T> state;
        private BiConsumer<T, Player> callback;

        public Observer(BiConsumer<T, Player> callback) {
            this.state = State.this;
            this.callback = callback;
        }

        public void onChange(Player player) {
            callback.accept(state.read(), player);
        }
    }
}
