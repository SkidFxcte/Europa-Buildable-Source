/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueNumber;

public class ModuleCustomFOV
extends Module {
    public static ValueNumber fov = new ValueNumber("FOV", "fov", "fov", 120, 0, 160);
    public float oldFov;

    public ModuleCustomFOV() {
        super("FOVModifier", "FOV Modifier", "Gives you more customization over the FOV.", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        this.oldFov = ModuleCustomFOV.mc.gameSettings.fovSetting;
    }

    @Override
    public void onUpdate() {
        ModuleCustomFOV.mc.gameSettings.fovSetting = fov.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        ModuleCustomFOV.mc.gameSettings.fovSetting = this.oldFov;
    }
}

