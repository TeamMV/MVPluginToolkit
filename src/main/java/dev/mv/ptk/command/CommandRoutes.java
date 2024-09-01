package dev.mv.ptk.command;

import dev.mv.utilsx.generic.Either;

import java.util.ArrayList;
import java.util.List;

public class CommandRoutes {
    List<CommandRoute> routes;

    public CommandRoutes(List<CommandRoute> routes) {
        this.routes = routes;
    }

    public CommandRoute findRoute(String[] args) {
        for (CommandRoute route : routes) {
            if (route.isCompatible(args)) return route;
        }
        return null;
    }

    public static class Builder {
        private List<CommandRoute> routes;

        public Builder() {
            routes = new ArrayList<>();
        }

        public RouteBuilder withRoute() {
            return new RouteBuilder(this);
        }

        public static class RouteBuilder {
            private Builder builder;
            private List<Either<CommandRoute.ArgumentType, String>> signature;

            private RouteBuilder(Builder builder) {
                this.builder = builder;
                this.signature = new ArrayList<>();
            }

            public RouteBuilder withType(CommandRoute.ArgumentType type) {
                this.signature.add(new Either<>(CommandRoute.ArgumentType.class, String.class).assign(type));
                return this;
            }

            public RouteBuilder withNamed(String named) {
                this.signature.add(new Either<>(CommandRoute.ArgumentType.class, String.class).assign(named));
                return this;
            }

            public Builder then() {
                builder.routes.add(new CommandRoute(signature));
                return builder;
            }
        }

        public CommandRoutes build() {
            return new CommandRoutes(routes);
        }
    }
}
