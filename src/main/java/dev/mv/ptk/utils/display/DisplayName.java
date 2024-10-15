package dev.mv.ptk.utils.display;

import dev.mv.ptk.utils.State;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DisplayName {
    private List<Object> components;
    private List<Entity> holders;
    private String last;
    private Consumer<String> onChange;

    public void applyTo(Entity entity) {
        holders.add(entity);
        rebuild();
    }

    public void rebuild() {
        StringBuilder builder = new StringBuilder();
        for (Object component : components) {
            if (component instanceof Builder.FormattedValue<?> fv) {
                builder.append(ChatColor.translateAlternateColorCodes('&', fv.colorCode));
                builder.append(fv.state.read().toString());
            } else {
                builder.append(component);
            }
        }

        last = builder.toString();

        holders.forEach(h -> {
            h.setCustomNameVisible(true);
            h.setCustomName(last);
        });

        if (onChange != null) {
            onChange.accept(last);
        }
    }

    public void setOnChange(Consumer<String> onChange) {
        this.onChange = onChange;
    }

    public String getAsString() {
        return last;
    }

    public static class Builder<B> {
        private Consumer<DisplayName> output;
        private List<Object> components;
        private Supplier<DisplayName> supplier;
        private B res;

        public Builder(B res, Consumer<DisplayName> output) {
            this.res = res;
            this.output = output;
            components = new ArrayList<>();
            supplier = () -> null;
        }

        public Builder<B> string(String colorString) {
            components.add(colorString);
            return this;
        }

        public <T> Builder<B> value(State<T> value, String colorCode) {
            State<T>.Observer observer = value.new Observer(_0 -> supplier.get().rebuild());
            value.observe(observer);

            components.add(new FormattedValue<>(value, colorCode));
            return this;
        }

        public B build() {
            DisplayName name = new DisplayName();
            name.components = components;
            name.holders = new ArrayList<>();

            supplier = () -> name;

            if (output != null) {
                output.accept(name);
            }

            name.rebuild();

            return res;
        }

        public record FormattedValue<T>(State<T> state, String colorCode) {}
    }
}
