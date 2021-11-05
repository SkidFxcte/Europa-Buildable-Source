/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.api.manager.misc.DiscordPresence;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;

public class ModuleDiscordPresence
extends Module {
    public ModuleDiscordPresence() {
        super("Discord Presence", "Discord Presence", "Makes your Discord profile have a Rich Presence.", ModuleCategory.CLIENT);
    }

    @Override
    public void onEnable() {
        DiscordPresence.startRPC();
    }

    @Override
    public void onDisable() {
        DiscordPresence.stopRPC();
    }
}

