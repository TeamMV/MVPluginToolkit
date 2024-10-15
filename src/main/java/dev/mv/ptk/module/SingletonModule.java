package dev.mv.ptk.module;

import dev.mv.ptk.PluginToolkit;
import dev.mv.ptk.Ptk;

public abstract class SingletonModule extends Module {
    protected SingletonModule(PluginToolkit toolkit) {
        super(toolkit);
    }

    @Override
    public void enable() {}

    @Override
    public void disable() {}

    public final void stop() {
        if (Ptk.getInstance().canUnload()) clean();
    }

    protected abstract void clean();
}
