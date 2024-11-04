package dev.mv.ptk.module;

import dev.mv.ptk.PluginToolkit;

public abstract class Module {

    protected PluginToolkit toolkit;

    public Module(PluginToolkit toolkit) {
        this.toolkit = toolkit;
    }
    
    public abstract String getId();
    public abstract void enable();
    public abstract void disable();
}
