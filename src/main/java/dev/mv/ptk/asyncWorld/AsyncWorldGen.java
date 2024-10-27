package dev.mv.ptk.asyncWorld;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;
import dev.mv.ptk.module.SingletonModule;
import net.querz.nbt.io.NBTUtil;
import net.querz.nbt.tag.CompoundTag;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;

public class AsyncWorldGen extends SingletonModule {
    private static AsyncWorldGen INSTANCE;

    private static String NORMAL_TEMPLATE_NAME = "template_world_normal";
    private static String FLAT_TEMPLATE_NAME = "template_world_flat";
    private static String AMPLIFIED_TEMPLATE_NAME = "template_world_amplified";
    private static String LARGE_BIOMES_TEMPLATE_NAME = "template_world_large_biomes";

    private final File normalTemplate;
    private final File flatTemplate;
    private final File amplifiedTemplate;
    private final File largeBiomesTemplate;

    public AsyncWorldGen(PluginToolkit toolkit) {
        super(toolkit);

        Path worldDir = toolkit.getDataFolder().toPath().toAbsolutePath().getParent().getParent();

        normalTemplate = worldDir.resolve(NORMAL_TEMPLATE_NAME).toFile();
        if (!normalTemplate.exists()) createTemplateWorld(NORMAL_TEMPLATE_NAME, WorldType.NORMAL, normalTemplate);

        flatTemplate = worldDir.resolve(FLAT_TEMPLATE_NAME).toFile();
        if (!flatTemplate.exists()) createTemplateWorld(FLAT_TEMPLATE_NAME, WorldType.FLAT, flatTemplate);

        amplifiedTemplate = worldDir.resolve(AMPLIFIED_TEMPLATE_NAME).toFile();
        if (!amplifiedTemplate.exists()) createTemplateWorld(AMPLIFIED_TEMPLATE_NAME, WorldType.AMPLIFIED, amplifiedTemplate);

        largeBiomesTemplate = worldDir.resolve(LARGE_BIOMES_TEMPLATE_NAME).toFile();
        if (!largeBiomesTemplate.exists()) createTemplateWorld(LARGE_BIOMES_TEMPLATE_NAME, WorldType.LARGE_BIOMES, largeBiomesTemplate);
    }
    
    public static AsyncWorldGen getInstance() {
        if (INSTANCE == null) INSTANCE = new AsyncWorldGen(Ptk.getInstance());
        return INSTANCE;
    }

    private void createTemplateWorld(String name, WorldType type, File worldDir) {
        WorldCreator creator = new WorldCreator(name);
        creator.type(type);
        World world = Bukkit.createWorld(creator);
        assert world != null;
        Bukkit.unloadWorld(world, true);

        try {
            FileUtils.cleanDirectory(worldDir.toPath().resolve("region").toFile());
        } catch (IOException ignore) {}
        try {
            FileUtils.cleanDirectory(worldDir.toPath().resolve("entities").toFile());
        } catch (IOException ignore) {}
        try {
            FileUtils.cleanDirectory(worldDir.toPath().resolve("poi").toFile());
        } catch (IOException ignore) {}
        try {
            FileUtils.delete(worldDir.toPath().resolve("uid.dat").toFile());
        } catch (IOException ignore) {}
    }

    public void generateWorld(WorldCreator creator, Consumer<World> output) {

        new Thread(() -> {
            WorldType type = creator.type();
            String name = creator.name();
            long seed = creator.seed();

            File template = switch (type) {
                case NORMAL -> normalTemplate;
                case FLAT -> flatTemplate;
                case LARGE_BIOMES -> largeBiomesTemplate;
                case AMPLIFIED -> amplifiedTemplate;
            };

            Path newWorldPath = template.toPath().getParent().resolve(name);

            try {
                FileUtils.copyDirectory(template, newWorldPath.toFile());

                CompoundTag levelDat = (CompoundTag) NBTUtil.read(newWorldPath.resolve("level.dat").toFile()).getTag();
                levelDat.getCompoundTag("Data").getCompoundTag("WorldGenSettings").putLong("seed", seed);
                NBTUtil.write(levelDat, newWorldPath.resolve("level.dat").toFile());

                Bukkit.getScheduler().runTaskLater(toolkit, () -> {
                    output.accept(Bukkit.createWorld(new WorldCreator(name)));
                }, 0);
            } catch (IOException e) {
                e.printStackTrace();
                output.accept(null);
            }
        }).start();
    }

    public void deleteWorld(String name) {
        new Thread(() -> {
            Path path = normalTemplate.toPath().getParent().resolve(name);
            try {
                FileUtils.delete(path.toFile());
            } catch (IOException ignore) {}
        }).start();
    }

    @Override
    protected void clean() {}

    @Override
    public String getId() {
        return "async-world";
    }
}
