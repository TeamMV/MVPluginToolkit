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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftSign;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class SignKeyboard extends TextProvider {
    @Override
    public void open(Player player) {
        String prompt = "Hello sign\nHello prompt";
        String[] split = prompt.split("\n");

        String[] lines = new String[] { "", "^^^^^^^^^^^^^^^", split[0], split[1]};

        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        final var blockPosition = new BlockPos(player.getLocation().getBlockX(), 1, player.getLocation().getBlockZ());

        ClientboundBlockUpdatePacket packet = new ClientboundBlockUpdatePacket(blockPosition, CraftMagicNumbers.getBlock(Material.OAK_SIGN, (byte) 0));
        serverPlayer.connection.send(packet);

        Component[] components = CraftSign.sanitizeLines(lines);
        SignText text = new SignText();
        text.setHasGlowingText(false);
        text.setColor(DyeColor.BLACK);
        for (int i = 0; i < components.length; i++) {
            text.setMessage(i, components[i]);
        }

        SignBlockEntity sign = new SignBlockEntity(blockPosition, Blocks.OAK_SIGN.defaultBlockState());
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
                    String output = packet.getLines()[0];
                    Bukkit.broadcastMessage(output);
                    pipeline.remove(this);
                }
                super.channelRead(ctx, msg);
            }
        };

        pipeline.addBefore("sign_handler", player.getName(), handler);
    }

    @Override
    public void textChange(String newText, Player player) {}
}
