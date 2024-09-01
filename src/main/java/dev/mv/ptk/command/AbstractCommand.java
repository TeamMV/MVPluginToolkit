package dev.mv.ptk.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    private CommandRoutes routes;

    protected AbstractCommand(CommandRoutes routes) {
        this.routes = routes;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        CommandRoute route = routes.findRoute(args);
        if (route != null) {
            var signature = route.generateSignature();
            try {
                getClass().getMethod(signature.a, signature.b.toArray()).invoke(this, route.toParams((Player) commandSender, args));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
