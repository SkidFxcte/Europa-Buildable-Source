/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.Europa;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import net.minecraft.client.gui.GuiScreen;

public class ModuleGUI
extends Module {
    public static ModuleGUI INSTANCE;
    public static ValueBoolean customMenu;
    public ValueNumber scrollSpeed = new ValueNumber("ScrollSpeed", "ScrollSpeed", "The speed for scrolling through the GUI.", 10, 1, 20);

    public ModuleGUI() {
        super("GUI", "GUI", "The client's Click GUI.", ModuleCategory.CLIENT);
        this.setBind(54);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen((GuiScreen)Europa.CLICK_GUI);
    }

    static {
        customMenu = new ValueBoolean("CustomMainMenu", "CustomMainMenu", "", true);
    }
}

