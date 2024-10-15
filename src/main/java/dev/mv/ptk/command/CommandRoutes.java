package dev.mv.ptk.command;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Either;


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
            private Vec<Either<CommandRoute.ArgumentType, String>> signature;

            private RouteBuilder(Builder builder) {
                this.builder = builder;
                this.signature = new Vec<>();
            }

            public RouteBuilder withType(CommandRoute.ArgumentType type) {
                this.signature.push(new Either<>(CommandRoute.ArgumentType.class, String.class).assign(type));
                return this;
            }

            public RouteBuilder withNamed(String named) {
                this.signature.push(new Either<>(CommandRoute.ArgumentType.class, String.class).assign(named));
                return this;
            }

            public Builder then() {
                builder.routes.push(new CommandRoute(signature));
                return builder;
            }
        }

        public CommandRoutes build() {
            return new CommandRoutes(routes);
        }
    }
}
