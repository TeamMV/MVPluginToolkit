package dev.mv.ptk.utils.input;

import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public abstract class TextProvider {
    private BiConsumer<String, Player> callback;
    private String currentStr = "";

    public void setCallback(BiConsumer<String, Player> callback) {
        this.callback = callback;
    }

    protected void sendText(String text, Player player) {
        currentStr += text;
        if (callback != null) {
            callback.accept(currentStr, player);
        }
        textChange(currentStr, player);
    }

    protected void removeText(int amt, Player player) {
        currentStr = currentStr.substring(0, currentStr.length() - amt);
        if (callback != null) {
            callback.accept(currentStr, player);
        }
        textChange(currentStr, player);
    }

    public abstract void open(Player player);
    public abstract void textChange(String newText, Player player);
}
