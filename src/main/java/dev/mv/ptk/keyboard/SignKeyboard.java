package dev.mv.ptk.keyboard;

import dev.mv.ptk.utils.input.TextProvider;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftSign;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class SignKeyboard extends TextProvider {
    @Override
    public void open(Player player, String prompt) {
        String[] split = prompt.split("\n");
        String[] lines = new String[] { "", "^^^^^^^^^^^^^^^", split[0], split[1]};

        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        Location direction = player.getLocation();
        direction.setPitch(0);
        Location location = player.getLocation().subtract(direction.getDirection().normalize());

        final BlockPos blockPosition = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        Block block = player.getWorld().getBlockAt(location);

        ClientboundBlockUpdatePacket packet = new ClientboundBlockUpdatePacket(blockPosition, CraftMagicNumbers.getBlock(Material.OAK_SIGN, (byte) 0));
        serverPlayer.connection.send(packet);

        Component[] components = CraftSign.sanitizeLines(lines);
        SignText text = new SignText();
        text.setHasGlowingText(false);
        text.setColor(DyeColor.BLACK);
        for (int i = 0; i < components.length; i++) {
            text = text.setMessage(i, components[i]);
        }

        SignBlockEntity sign = new SignBlockEntity(blockPosition, CraftMagicNumbers.getBlock(Material.OAK_SIGN, (byte) 0));
        sign.setText(text, true);
        serverPlayer.connection.send(sign.getUpdatePacket());

        ClientboundOpenSignEditorPacket open = new ClientboundOpenSignEditorPacket(blockPosition, true);
        serverPlayer.connection.send(open);

        ChannelPipeline pipeline;
        try {
            Field field = ServerCommonPacketListenerImpl.class.getDeclaredField("c");
            field.setAccessible(true);
            Connection connection = (Connection) field.get(((CraftPlayer) player).getHandle().connection);
            pipeline = connection.channel.pipeline();
        } catch (Exception ignore) {
            System.err.println("Error injecting packet listener");
            return;
        }

        ChannelDuplexHandler handler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof ServerboundSignUpdatePacket packet) {
                    sendText(packet.getLines()[0], player);
                    onClose(player);
                    pipeline.remove(this);
                    ClientboundBlockUpdatePacket reset = new ClientboundBlockUpdatePacket(blockPosition, CraftMagicNumbers.getBlock(block.getType(), block.getData()));
                    serverPlayer.connection.send(reset);
                }
                super.channelRead(ctx, msg);
            }
        };

        pipeline.addBefore("packet_handler", "sign_keyboard_" + player.getName(), handler);
    }

    @Override
    public void textChange(String newText, Player player) {}
}
