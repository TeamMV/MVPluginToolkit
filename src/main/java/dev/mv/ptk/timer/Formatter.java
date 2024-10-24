package dev.mv.ptk.timer;

import dev.mv.ptk.Utils;

public interface Formatter {
    String format(int hours, int minutes, int seconds);
    String idle();

    static Formatter ofColorCode(String format) {
        return new Formatter() {
            @Override
            public String format(int hours, int minutes, int seconds) {
                return Utils.chat("%s%dh %dm %ds", format, hours, minutes, seconds);
            }

            @Override
            public String idle() {
                return Utils.chat("%sTimer not running", format);
            }
        };
    }
}
