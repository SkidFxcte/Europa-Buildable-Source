/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.element;

import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.Value;
import com.europa.client.gui.hud.ElementFrame;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Element
extends Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public ArrayList<Value> values = new ArrayList();
    public ElementFrame frame;

    public Element(String name, String description) {
        super(name, name, description, ModuleCategory.HUD);
    }

    public Element(String name, String tag, String description) {
        super(name, tag, description, ModuleCategory.HUD);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onMotionUpdate() {
    }

    @Override
    public void onRender2D(EventRender2D eventRender2D) {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onLogin() {
    }

    @Override
    public void onLogout() {
    }

    @Override
    public void onDeath() {
    }

    /*
     * WARNING - void declaration
     */
    public void setFrame(final ElementFrame frame) {
        this.frame = frame;
    }

    @Override
    public String getHudInfo() {
        return "";
    }

    @Override
    public void toggle() {
        if (this.isToggled()) {
            this.disable();
        } else {
            this.enable();
        }
    }

    @Override
    public void enable() {
        this.setToggled(true);
        this.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void disable() {
        this.setToggled(false);
        this.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void addValue(final Value value) {
        this.values.add(value);
    }

    @Override
    public ArrayList<Value> getValues() {
        return this.values;
    }
}

