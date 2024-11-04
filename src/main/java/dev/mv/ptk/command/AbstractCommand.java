package dev.mv.ptk.command;

import dev.mv.ptk.Utils;
import dev.mv.ptk.style.Chat;
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

    public AbstractCommand(CommandRoutes routes) {
        this.routes = routes;
        this.enabled = true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }
        if (!enabled) {
            Chat.send(player, "&+eThis command has been disabled!");
            return false;
        }
        CommandRoute route = routes.findRoute(args);
        if (route != null) {
            var signature = route.generateSignature();
            try {
                getClass().getMethod(signature.a, signature.b.toArray()).invoke(this, route.toParams(player, args));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Chat.send(player, "&+eInvalid usage!");
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
