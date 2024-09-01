package dev.mv.ptk.command;

@Command("sell")
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

    public void call_sell(int amount) {

    }

    public void call_buy(int amount) {

    }
}
