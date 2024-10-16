package dev.mv.ptk.display;

import dev.mv.ptk.Utils;
import dev.mv.ptk.utils.display.DisplayName;
import dev.mv.utilsx.collection.Vec;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class Sidebar {
    private Vec<DisplayName> lines;
    private Scoreboard scoreboard;
    private Objective objective;
    private Vec<Team> teams = new Vec<>();
    private int lastLine;

    private Sidebar(String title) {
        if (title == null) title = "";
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("MVPTK", Criteria.DUMMY, title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void rebuild() {
        int teamIdx = 0;
        for (int i = lastLine; i >= 0; i--) {
            DisplayName name = lines.get(lastLine - i);

            int finalTeamIdx = teamIdx++;
            name.setOnChange((s) -> updateLine(finalTeamIdx, s));

            Team team = scoreboard.registerNewTeam(i + "");
            team.addEntry(Utils.chatColor(i).toString());
            team.setPrefix(name.getAsString());
            objective.getScore(Utils.chatColor(i).toString()).setScore(i + 1);
            teams.push(team);
        }
    }

    public void updateLine(int i, String p) {
        teams.get(i).setPrefix(p);
    }

    public void updateLine(int i) {
        teams.get(i).setPrefix(lines.get(lastLine - i).getAsString());
    }

    public void setLine(int i, DisplayName line) {
        line.setOnChange(s -> updateLine(i, s));
        lines.replace(lastLine - i, line).setOnChange(null);
        updateLine(i);
    }

    public void setTitle(String title) {
        objective.setDisplayName(title);
    }

    public void addPlayer(Player player) {
        player.setScoreboard(scoreboard);
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private Vec<DisplayName> lines;
        private String title;
        private int currentLine = 0;
        private boolean fill = false;

        public Builder() {
            lines = new Vec<>();
        }

        public DisplayName.Builder<Builder> withLine() {
            currentLine++;
            return new DisplayName.Builder<>(this, lines::push);
        }

        public Builder withNewLine() {
            currentLine++;
            new DisplayName.Builder<>(null, lines::push)
                    .string(Utils.chatColor(currentLine - 1).toString())
                    .build();
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withFill(boolean fill) {
            this.fill = fill;
            return this;
        }

        public Sidebar build() {
            Sidebar sb = new Sidebar(title);
            sb.lines = lines;
            for (int i = currentLine; i < 15 && fill; i++) {
                new DisplayName.Builder<>(null, lines::push)
                        .string(Utils.chatColor(i).toString())
                        .build();
            }
            sb.lastLine = lines.len() - 1;
            sb.rebuild();
            return sb;
        }
    }
}
