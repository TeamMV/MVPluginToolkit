package dev.mv.ptk.command;

import dev.mv.ptk.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    private CommandRoutes routes;
    private boolean enabled;

    protected AbstractCommand(CommandRoutes routes) {
        this.routes = routes;
        this.enabled = true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!enabled) {
            sender.sendMessage(Utils.chat("&cThis command has been disabled!"));
            return false;
        }
        CommandRoute route = routes.findRoute(args);
        if (route != null) {
            var signature = route.generateSignature();
            try {
                getClass().getMethod(signature.a, signature.b.toArray()).invoke(this, route.toParams((Player) sender, args));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(Utils.chat("&cInvalid usage!"));
        }
        return false;
    }

    public final void enable() {
        enabled = true;
    }

    public final void disable() {
        enabled = false;
    }
}
