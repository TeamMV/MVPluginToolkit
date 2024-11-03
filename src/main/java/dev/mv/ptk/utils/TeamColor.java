package dev.mv.ptk.utils;

import dev.mv.utilsx.UtilsX;
import org.bukkit.Material;

public class TeamColor {

    public static final String[] colorCodes = new String[] {
            "&c", "&9", "&a", "&e", "&3", "&f", "&d", "&8",
            "&4", "&b", "&2", "&6", "&5", "&7", "&d", "&0"
    };

    public static final String[] names = new String[] {
            "red", "blue", "green", "yellow", "aqua", "white", "pink", "gray",
            "brown", "light blue", "dark green", "orange", "purple", "light gray", "magenta", "black"
    };

    public static final Material[][] materials = new Material[][] {
            new Material[] { Material.RED_WOOL, Material.RED_CONCRETE, Material.RED_CONCRETE_POWDER, Material.RED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.RED_STAINED_GLASS, Material.RED_STAINED_GLASS_PANE, Material.RED_BANNER, Material.RED_BED, Material.RED_CARPET, Material.RED_DYE },
            new Material[] { Material.BLUE_WOOL, Material.BLUE_CONCRETE, Material.BLUE_CONCRETE_POWDER, Material.BLUE_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA, Material.BLUE_STAINED_GLASS, Material.BLUE_STAINED_GLASS_PANE, Material.BLUE_BANNER, Material.BLUE_BED, Material.BLUE_CARPET, Material.BLUE_DYE },
            new Material[] { Material.LIME_WOOL, Material.LIME_CONCRETE, Material.LIME_CONCRETE_POWDER, Material.LIME_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA, Material.LIME_STAINED_GLASS, Material.LIME_STAINED_GLASS_PANE, Material.LIME_BANNER, Material.LIME_BED, Material.LIME_CARPET, Material.LIME_DYE },
            new Material[] { Material.YELLOW_WOOL, Material.YELLOW_CONCRETE, Material.YELLOW_CONCRETE_POWDER, Material.YELLOW_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA, Material.YELLOW_STAINED_GLASS, Material.YELLOW_STAINED_GLASS_PANE, Material.YELLOW_BANNER, Material.YELLOW_BED, Material.YELLOW_CARPET, Material.YELLOW_DYE },
            new Material[] { Material.CYAN_WOOL, Material.CYAN_CONCRETE, Material.CYAN_CONCRETE_POWDER, Material.CYAN_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.CYAN_STAINED_GLASS, Material.CYAN_STAINED_GLASS_PANE, Material.CYAN_BANNER, Material.CYAN_BED, Material.CYAN_CARPET, Material.CYAN_DYE },
            new Material[] { Material.WHITE_WOOL, Material.WHITE_CONCRETE, Material.WHITE_CONCRETE_POWDER, Material.WHITE_TERRACOTTA, Material.WHITE_GLAZED_TERRACOTTA, Material.WHITE_STAINED_GLASS, Material.WHITE_STAINED_GLASS_PANE, Material.WHITE_BANNER, Material.WHITE_BED, Material.WHITE_CARPET, Material.WHITE_DYE },
            new Material[] { Material.PINK_WOOL, Material.PINK_CONCRETE, Material.PINK_CONCRETE_POWDER, Material.PINK_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA, Material.PINK_STAINED_GLASS, Material.PINK_STAINED_GLASS_PANE, Material.PINK_BANNER, Material.PINK_BED, Material.PINK_CARPET, Material.PINK_DYE },
            new Material[] { Material.GRAY_WOOL, Material.GRAY_CONCRETE, Material.GRAY_CONCRETE_POWDER, Material.GRAY_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA, Material.GRAY_STAINED_GLASS, Material.GRAY_STAINED_GLASS_PANE, Material.GRAY_BANNER, Material.GRAY_BED, Material.GRAY_CARPET, Material.GRAY_DYE },
            new Material[] { Material.BROWN_WOOL, Material.BROWN_CONCRETE, Material.BROWN_CONCRETE_POWDER, Material.BROWN_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA, Material.BROWN_STAINED_GLASS, Material.BROWN_STAINED_GLASS_PANE, Material.BROWN_BANNER, Material.BROWN_BED, Material.BROWN_CARPET, Material.BROWN_DYE },
            new Material[] { Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_BLUE_CONCRETE_POWDER, Material.LIGHT_BLUE_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.LIGHT_BLUE_BANNER, Material.LIGHT_BLUE_BED, Material.LIGHT_BLUE_CARPET, Material.LIGHT_BLUE_DYE },
            new Material[] { Material.GREEN_WOOL, Material.GREEN_CONCRETE, Material.GREEN_CONCRETE_POWDER, Material.GREEN_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA, Material.GREEN_STAINED_GLASS, Material.GREEN_STAINED_GLASS_PANE, Material.GREEN_BANNER, Material.GREEN_BED, Material.GREEN_CARPET, Material.GREEN_DYE },
            new Material[] { Material.ORANGE_WOOL, Material.ORANGE_CONCRETE, Material.ORANGE_CONCRETE_POWDER, Material.ORANGE_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.ORANGE_STAINED_GLASS, Material.ORANGE_STAINED_GLASS_PANE, Material.ORANGE_BANNER, Material.ORANGE_BED, Material.ORANGE_CARPET, Material.ORANGE_DYE },
            new Material[] { Material.PURPLE_WOOL, Material.PURPLE_CONCRETE, Material.PURPLE_CONCRETE_POWDER, Material.PURPLE_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.PURPLE_STAINED_GLASS, Material.PURPLE_STAINED_GLASS_PANE, Material.PURPLE_BANNER, Material.PURPLE_BED, Material.PURPLE_CARPET, Material.PURPLE_DYE },
            new Material[] { Material.LIGHT_GRAY_WOOL, Material.LIGHT_GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE_POWDER, Material.LIGHT_GRAY_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA, Material.LIGHT_GRAY_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.LIGHT_GRAY_BANNER, Material.LIGHT_GRAY_BED, Material.LIGHT_GRAY_CARPET, Material.LIGHT_GRAY_DYE },
            new Material[] { Material.MAGENTA_WOOL, Material.MAGENTA_CONCRETE, Material.MAGENTA_CONCRETE_POWDER, Material.MAGENTA_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA, Material.MAGENTA_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS_PANE, Material.MAGENTA_BANNER, Material.MAGENTA_BED, Material.MAGENTA_CARPET, Material.MAGENTA_DYE },
            new Material[] { Material.BLACK_WOOL, Material.BLACK_CONCRETE, Material.BLACK_CONCRETE_POWDER, Material.BLACK_TERRACOTTA, Material.BLACK_GLAZED_TERRACOTTA, Material.BLACK_STAINED_GLASS, Material.BLACK_STAINED_GLASS_PANE, Material.BLACK_BANNER, Material.BLACK_BED, Material.BLACK_CARPET, Material.BLACK_DYE },
    };

    public enum ColorItemType {
        WOOL,
        CONCRETE,
        CONCRETE_POWDER,
        TERRACOTTA,
        GLAZED_TERRACOTTA,
        GLASS,
        GLASS_PANE,
        BANNER,
        BED,
        CARPET,
        DYE;
    }

    public static Material getTeamMaterial(int teamId, ColorItemType colorItemType) {
        return materials[UtilsX.overlap(teamId, 0, 15)][colorItemType.ordinal()];
    }

    public static String getColorName(int teamId) {
        return names[UtilsX.overlap(teamId, 0, 15)];
    }

    public static String getColorCode(int teamId) {
        return names[UtilsX.overlap(teamId, 0, 15)];
    }

}
