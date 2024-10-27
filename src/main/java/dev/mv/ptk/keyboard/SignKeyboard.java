package dev.mv.ptk.keyboard;

import dev.mv.ptk.utils.input.TextProvider;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SignKeyboard extends TextProvider {
    @Override
    public void open(Player player) {
        CraftPlayer p = (CraftPlayer) player;
        //PacketPlayOutOpenSignEditor
    }

    @Override
    public void textChange(String newText, Player player) {
        //PacketPlayInUpdateSign
    }
}
