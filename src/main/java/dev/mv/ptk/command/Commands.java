package dev.mv.ptk.command;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Utils;
import dev.mv.ptk.module.Module;

import java.util.HashMap;

public final class Commands extends Module {
    private HashMap<String, AbstractCommand> commands = new HashMap<>();

    public Commands(PluginToolkit toolkit) {
        super(toolkit);
    }

    @Override
    public void enable() {
        Utils.PLUGIN_CLASSES.get(toolkit.getName()).iterCopied()
            .filter(clazz -> AbstractCommand.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Command.class))
            .forEach(clazz -> {
                Command command = clazz.getAnnotation(Command.class);
                try {
                    var executor = (AbstractCommand) clazz.getConstructor().newInstance();
                    toolkit.getCommand(command.value()).setExecutor(executor);
                    toolkit.getCommand(command.value()).setTabCompleter(executor);
                    commands.put(command.value(), executor);
                } catch (NullPointerException e) {
                    System.err.println("Command " + command.value() + " is not registered in plugin.yml");
                } catch (Exception e) {
                    System.err.println("Failed to load command " + command.value());
                }
            });
    }

    @Override
    public String getId() {
        return "commands";
    }

    @Override
    public void disable() {
        for (String command : commands.keySet()) {
            toolkit.getCommand(command).setExecutor(null);
            toolkit.getCommand(command).setTabCompleter(null);
        }
    }

    public AbstractCommand getCommand(String command) {
        return commands.get(command);
    }
}
