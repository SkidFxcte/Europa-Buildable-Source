/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueColor;
import java.awt.Color;

public class ModuleGlintModify
extends Module {
    public static ValueColor color = new ValueColor("Color", "Color", "", new Color(255, 0, 0));

    public ModuleGlintModify() {
        super("GlintModify", "Glint Modify", "Modifies the Glint effect on enchanted items.", ModuleCategory.RENDER);
    }

    public static Color getColor() {
        return new Color(color.getValue().getRed(), color.getValue().getGreen(), color.getValue().getBlue());
    }
}

