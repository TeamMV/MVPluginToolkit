package dev.mv.ptk.utils;

import java.util.ArrayList;
import java.util.List;
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

    public void modify(Consumer<T> fn) {
        fn.accept(t);
        observers.forEach(Observer::onChange);
    }

    public void write(T t) {
        this.t = t;
        observers.forEach(Observer::onChange);
    }

    public void write(UnaryOperator<T> fn) {
        this.t = fn.apply(t);
        observers.forEach(Observer::onChange);
    }

    public void observe(State<T>.Observer observer) {
        this.observers.add(observer);
    }

    public class Observer {
        private State<T> state;
        private Consumer<T> callback;

        public Observer(Consumer<T> callback) {
            this.state = State.this;
            this.callback = callback;
        }

        public void onChange() {
            callback.accept(state.read());
        }
    }
}
