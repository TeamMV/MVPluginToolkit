package dev.mv.ptk.module;

public abstract class Module {
    public abstract String getId();
    public abstract void onEnable();
    public abstract void onDisable();
}
