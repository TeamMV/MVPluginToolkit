package dev.mv.ptk.utils.input;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public abstract class TextProvider {
    private BiConsumer<String, Player> callback;
    private BiConsumer<String, Player> closeCallback;
    private String currentStr = "";
    private String defaultPrompt = "";

    public void setCallback(BiConsumer<String, Player> callback) {
        this.callback = callback;
    }

    public void setDefaultPrompt(String defaultPrompt) {
        this.defaultPrompt = defaultPrompt;
    }

    protected void setText(String text, Player player) {
        this.currentStr = text;
        if (callback != null) {
            try {
                callback.accept(currentStr, player);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        textChange(currentStr, player);
    }

    protected void addText(String text, Player player) {
        setText(currentStr + text, player);
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

    public void setCloseCallback(BiConsumer<String, Player> closeCallback) {
        this.closeCallback = closeCallback;
    }

    protected void onClose(Player player) {
        if (closeCallback != null) {
            try {
                closeCallback.accept(currentStr, player);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public void open(Player player) {
        open(player, defaultPrompt);
    }

    public abstract void open(Player player, String prompt);
    public abstract void textChange(String newText, Player player);
}
