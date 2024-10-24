package dev.mv.ptk.sudo;

import dev.mv.ptk.command.AbstractCommand;
import dev.mv.ptk.command.CommandRoutes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SudoCommand implements CommandExecutor {
    public SudoCommand() {}

    public void call(Player player) {
        Sudo.getInstance().requestSudoAccess(player);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            Sudo.getInstance().requestSudoAccess(player);
        }
        return false;
    }
}
