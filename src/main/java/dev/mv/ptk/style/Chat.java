package dev.mv.ptk.style;

import dev.mv.ptk.Utils;
import dev.mv.ptk.utils.Null;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {
    public static void send(Player player, String message, Object... args) {
        player.sendMessage(format(player, message, args));
    }

    public static String format(Player player, String message, Object... args) {
        String fmt = message.formatted(args);


        StringBuilder builder = new StringBuilder();
        boolean inColCode = false;
        boolean inFancyColCode = false;
        for (char c : fmt.toCharArray()) {
            if (inFancyColCode) {
                builder.append(Utils.chat(getFormatting(player, c)));
                inFancyColCode = false;
                inColCode = false;
                continue;
            }
            if (inColCode) {
                if (c == '+') {
                    inFancyColCode = true;
                    continue;
                } else {
                    builder.append(Null.or(ChatColor.getByChar(c).toString(), "&" + c));
                }
                inColCode = false;
                continue;
            }
            if (c == '&') {
                inColCode = true;
                continue;
            }
            builder.append(c);
        }

        return builder.toString();
    }

    private static String getFormatting(Player player, char code) {
        UiStyle style = Style.getInstance().getStyle(player.getUniqueId());

        return switch (code) {
            case '+' -> style.getPositiveFormat();
            case '-' -> style.getNegativeFormat();
            case 'e' -> style.getErrorFormat();
            case 'd' -> style.getDefaultFormat();
            case 'D' -> style.getDebugFormat();
            case 'p' -> style.getPrimarySystemFormat();
            case 's' -> style.getSecondarySystemFormat();
            case 'n' -> style.getNumericHighlightFormat();
            case 'a' -> style.getAlphabeticHighlightFormat();
            case 'S' -> style.getSeparatorFormat();
            default -> "&+" + code;
        };
    }
}
