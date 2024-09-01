package dev.mv.ptk.command;

public class SellCommand extends AbstractCommand {
    protected SellCommand() {
        super(new CommandRoutes.Builder()
                .withRoute()
                    .withNamed("sell")
                    .withType(CommandRoute.ArgumentType.INTEGER)
                    .then()
                .withRoute()
                    .withNamed("buy")
                    .withType(CommandRoute.ArgumentType.INTEGER)
                    .then()
                .build()
        );
    }
}
