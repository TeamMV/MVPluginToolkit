package dev.mv.ptk.utils.input;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class TextProvider {
    private BiConsumer<String, Player> callback;
    private Consumer<Player> closeCallback;
    private String currentStr = "";

    public void setCallback(BiConsumer<String, Player> callback) {
        this.callback = callback;
    }

    protected void sendText(String text, Player player) {
        currentStr += text;
        if (callback != null) {
            try {
                callback.accept(currentStr, player);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        textChange(currentStr, player);
    }

    protected void removeText(int amt, Player player) {
        currentStr = currentStr.substring(0, currentStr.length() - amt);
        if (callback != null) {
            try {
                callback.accept(currentStr, player);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        textChange(currentStr, player);
    }

    public void setCloseCallback(Consumer<Player> closeCallback) {
        this.closeCallback = closeCallback;
    }

    protected void onClose(Player player) {
        if (closeCallback != null) {
            try {
                closeCallback.accept(player);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public void open(Player player) {
        open(player, "");
    }

    public abstract void open(Player player, String prompt);
    public abstract void textChange(String newText, Player player);
}
