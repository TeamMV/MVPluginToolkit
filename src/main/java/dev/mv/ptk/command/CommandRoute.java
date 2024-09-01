package dev.mv.ptk.command;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Either;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandRoute {
    List<Either<ArgumentType, String>> signature;

    public enum ArgumentType {
        STRING,
        INTEGER,
        FLOAT,
        OPTIONAL, // params after this token become optional using dev.mv.utilsx.generic.Option<>
        EXTRA, // optional LAST parameter in array, if included, String[] args is included at the end of function signature
    }

    CommandRoute(List<Either<ArgumentType, String>> signature) {
        this.signature = signature;
    }

    boolean isCompatible(String[] args) {
        for (int i = 0; i < signature.size(); i++) {
            var expected = signature.get(i);
            if (expected.is(ArgumentType.class)) {
                ArgumentType type = expected.value();
                if (type == ArgumentType.EXTRA || type == ArgumentType.OPTIONAL) return true;
                if (type != getArgumentType(args[i])) return false;
            }
            else {
                String subcommand = expected.value();
                if (!subcommand.equals(args[i])) return false;
            }
        }
        return true;
    }

    ArgumentType getArgumentType(String input) {
        if (input == null || input.isEmpty()) {
            return ArgumentType.STRING;
        }
        if (input.matches("\\d+")) {
            return ArgumentType.INTEGER;
        }
        if (input.matches("\\d+\\.\\d*")) {
            return ArgumentType.FLOAT;
        }
        return ArgumentType.STRING;
    }

    Pair<String, Vec<Class<?>>> generateSignature() {
        StringBuilder fnName = new StringBuilder("call");
        Vec<Class<?>> clazzes = new Vec<>(Player.class);

        boolean isOptNow = false;

        for (var entry : signature) {
            if (entry.is(String.class)) {
                fnName.append('_').append(entry.<String>value());
            } else {
                ArgumentType type = entry.value();
                if (type == ArgumentType.OPTIONAL) {
                    isOptNow = true;
                    continue;
                }
                if (isOptNow && type != ArgumentType.EXTRA) {
                    clazzes.push(Option.class);
                    continue;
                }
                Class<?> clazz = switch (type) {
                    case STRING -> String.class;
                    case INTEGER -> int.class;
                    case FLOAT -> float.class;
                    case EXTRA -> String[].class;
                    default -> Object.class; //should never be the case lel
                };
                clazzes.push(clazz);
            }
        }

        return new Pair<>(fnName.toString(), clazzes);
    }

    Object[] toParams(Player player, String[] args) {
        Vec<Object> params = new Vec<>();
        params.push(player);

        boolean isOptNow = false;
        int i = 0;

        loop:
        for (var entry : signature) {
            if (entry.is(String.class)) continue;
            ArgumentType type = entry.value();
            switch (type) {
                case STRING -> {
                    if (isOptNow) {
                        if (i < args.length) params.push(Option.some(args[i]));
                        else params.push(Option.none());
                    }
                    else params.push(args[i]);
                }
                case INTEGER -> {
                    if (isOptNow) {
                        if (i < args.length) params.push(Option.some(Integer.parseInt(args[i])));
                        else params.push(Option.<Integer>none());
                    }
                    else params.push(Integer.parseInt(args[i]));
                }
                case FLOAT -> {
                    if (isOptNow) {
                        if (i < args.length) params.push(Option.some(Float.parseFloat(args[i])));
                        else params.push(Option.<Float>none());
                    }
                    else params.push(Float.parseFloat(args[i]));
                }
                case OPTIONAL -> {
                    isOptNow = true;
                    continue;
                }
                case EXTRA -> {
                    if (i < args.length) params.push(Arrays.copyOfRange(args, i, args.length));
                    else params.push(new String[0]);
                    break loop;
                }
            }
            i++;
        }

        return params.toArray();
    }
}
