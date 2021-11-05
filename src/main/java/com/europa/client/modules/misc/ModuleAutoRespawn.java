/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraft.client.gui.GuiGameOver;

public class ModuleAutoRespawn
extends Module {
    public static ValueBoolean showCoords = new ValueBoolean("ShowDeathCoords", "showdeathcoords", "shows the coords at which you died", false);

    public ModuleAutoRespawn() {
        super("AutoRespawn", "Auto Respawn", "Automatically respawns you when you die.", ModuleCategory.MISC);
    }

    @Override
    public void onUpdate() {
        block1: {
            if (ModuleAutoRespawn.mc.currentScreen instanceof GuiGameOver) {
                ModuleAutoRespawn.mc.player.respawnPlayer();
                mc.displayGuiScreen(null);
            }
            if (!showCoords.getValue() || !(ModuleAutoRespawn.mc.currentScreen instanceof GuiGameOver)) break block1;
            ChatManager.printChatNotifyClient(String.format("You died at x %d y %d z %d", (int)ModuleAutoRespawn.mc.player.posX, (int)ModuleAutoRespawn.mc.player.posY, (int)ModuleAutoRespawn.mc.player.posZ));
        }
    }
}

