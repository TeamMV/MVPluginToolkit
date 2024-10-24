package dev.mv.ptk.sudo;

import dev.mv.utilsx.collection.Vec;
import dev.mv.utilsx.generic.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class PwListener implements Listener {
    public static HashMap<Player, Pair<String, Callback>> REQUESTS = new HashMap<>();

    @EventHandler
    public void onChatSend(AsyncPlayerChatEvent e) {
        Vec<Player> toRemove = new Vec<>();

        REQUESTS.forEach((player, check) -> {
            if (player.equals(e.getPlayer())) {
                e.setCancelled(true);
                toRemove.push(player);

                if (check.a.equals(e.getMessage())) {
                    check.b.onCorrect();
                } else {
                    check.b.onIncorrect();
                }
            }
        });

        toRemove.forEach(REQUESTS::remove);
    }

    public interface Callback {
        void onCorrect();
        void onIncorrect();
    }
}
