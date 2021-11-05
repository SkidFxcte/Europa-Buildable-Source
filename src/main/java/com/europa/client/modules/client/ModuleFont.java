/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueString;

public class ModuleFont
extends Module {
    public static ValueString name = new ValueString("Name", "Name", "The name for the Font.", "Arial");
    public static ValueEnum style = new ValueEnum("Style", "Style", "The style for the font.", Styles.Plain);
    public static ValueNumber size = new ValueNumber("Size", "Size", "The size for the Font.", 18, 10, 50);
    public static ValueBoolean antiAlias = new ValueBoolean("AntiAlias", "AntiAlias", "Makes the font smoother.", true);
    public static ValueBoolean metrics = new ValueBoolean("Metrics", "Metrics", "Makes the font more clumped up and better looking.", true);
    public static ValueEnum shadow = new ValueEnum("Shadow", "Shadow", "The shadow for the Font.", Shadows.Normal);

    public ModuleFont() {
        super("Font", "Font", "Allows you to customize the client's font.", ModuleCategory.CLIENT);
    }

    public static enum Shadows {
        None,
        Small,
        Normal;

    }

    public static enum Styles {
        Plain,
        Italic,
        Bold,
        Both;

    }
}

