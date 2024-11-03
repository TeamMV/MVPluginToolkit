package dev.mv.ptk.command;

import dev.mv.ptk.Utils;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

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
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
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

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!enabled || !(sender instanceof Player)) {
            return List.of();
        }
        return routes.tabComplete(((Player) sender), args);
    }

    public final void enable() {
        enabled = true;
    }

    public final void disable() {
        enabled = false;
    }
}
