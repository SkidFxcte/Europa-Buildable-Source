/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraft.item.ItemPickaxe;

public class ModuleNoEntityTrace
extends Module {
    public static ModuleNoEntityTrace INSTANCE;
    public static ValueBoolean pickaxeOnly;
    public boolean isHoldingPickaxe = false;

    public ModuleNoEntityTrace() {
        super("NoEntityTrace", "No Entity Trace", "Let's you mine through entities.", ModuleCategory.PLAYER);
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        this.isHoldingPickaxe = ModuleNoEntityTrace.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean noTrace() {
        if (!pickaxeOnly.getValue()) return this.isToggled();
        if (!this.isToggled()) return false;
        if (!this.isHoldingPickaxe) return false;
        return true;
    }

    static {
        pickaxeOnly = new ValueBoolean("Pickaxe Only", "Pickaxe Only", "", true);
    }
}

