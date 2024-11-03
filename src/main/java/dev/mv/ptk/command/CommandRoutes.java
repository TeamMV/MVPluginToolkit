package dev.mv.ptk.command;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Either;
import dev.mv.utilsx.generic.Option;
import dev.mv.utilsx.sequence.Sequence;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CommandRoutes {
    Vec<CommandRoute> routes;

    public CommandRoutes(Vec<CommandRoute> routes) {
        this.routes = routes;
    }

    public CommandRoute findRoute(String[] args) {
        for (CommandRoute route : routes) {
            if (route.isCompatible(args)) return route;
        }
        return null;
    }

    public List<String> tabComplete(Player player, String[] args) {
        return routes.iterCopied().filter(r -> r.isPartial(args)).flatMap(route -> route.tabComplete(player, args)).collect();
    }

    public static class Builder {
        private Vec<CommandRoute> routes;

        public Builder() {
            routes = new Vec<>();
        }

        public RouteBuilder withRoute() {
            return new RouteBuilder(this);
        }

        public static class RouteBuilder {
            private Builder builder;
            private final Vec<Either<CommandRoute.ArgumentType, String>> signature;
            private final Vec<Option<Function<Player, Vec<String>>>> tabCompleters;

            private RouteBuilder(Builder builder) {
                this.builder = builder;
                this.signature = new Vec<>();
                this.tabCompleters = new Vec<>();
            }

            public RouteBuilder withType(CommandRoute.ArgumentType type) {
                this.signature.push(new Either<>(CommandRoute.ArgumentType.class, String.class).assign(type));
                if (type != CommandRoute.ArgumentType.OPTIONAL) this.tabCompleters.push(Option.none());
                return this;
            }

            public RouteBuilder withTabCompleter(Function<Player, Vec<String>> tabCompleter) {
                this.tabCompleters.replace(this.tabCompleters.len() - 1, Option.some(tabCompleter));
                return this;
            }

            public RouteBuilder withTabCompleter(Vec<String> completions) {
                return withTabCompleter(_0 -> completions);
            }

            public RouteBuilder withPlayerTabCompleter() {
                return withTabCompleter(_0 -> Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Vec.collector()));
            }

            public RouteBuilder withNamed(String named) {
                this.signature.push(new Either<>(CommandRoute.ArgumentType.class, String.class).assign(named));
                this.tabCompleters.push(Option.some(_0 -> new Vec<>(named)));
                return this;
            }

            public Builder then() {
                builder.routes.push(new CommandRoute(signature, tabCompleters));
                return builder;
            }
        }

        public CommandRoutes build() {
            return new CommandRoutes(routes);
        }
    }
}
