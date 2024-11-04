package dev.mv.ptk.sudo;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;
import dev.mv.ptk.Utils;
import dev.mv.ptk.module.SingletonModule;
import dev.mv.ptk.style.Chat;
import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class Sudo extends SingletonModule {
    private static Sudo INSTANCE;

    private HashSet<Player> sudoers;

    private Sudo(PluginToolkit toolkit) {
        super(toolkit);
        sudoers = new HashSet<>();
    }

    public static Sudo getInstance() {
        if (INSTANCE == null) INSTANCE = new Sudo(Ptk.getInstance());
        return INSTANCE;
    }

    public void requestSudoAccess(Player player) {
        if (sudoers.contains(player)) {
            Chat.send(player, "&+pAccess granted!");
        } else {
            Chat.send(player, "&+pPlease type passwort:");
            PwListener.REQUESTS.put(player, new Pair<>(Ptk.getInstance().getSudoPass(), new PwListener.Callback() {
                @Override
                public void onCorrect() {
                    sudoers.add(player);
                    Chat.send(player, "&+pAccess granted!");
                }

                @Override
                public void onIncorrect() {
                    Chat.send(player, "&+eIncorrect Password");
                }
            }));
        }
    }

    public boolean isSudoer(Player player) {
        return sudoers.contains(player);
    }

    @Override
    protected void clean() {
        sudoers.clear();
    }

    @Override
    public String getId() {
        return "";
    }
}
