/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordPresence {
    public static String discordID = "831944327491223663";
    public static DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    public static DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    public static String clientVersion = "Europa " + "1.1.5";

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = DiscordPresence::lambda$startRPC$0;
        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);
        DiscordPresence.discordRichPresence.startTimestamp = System.currentTimeMillis() / ((long)-2121370231 ^ 0xFFFFFFFF818E7661L);
        DiscordPresence.discordRichPresence.details = "Looking up at Jupiter";
        DiscordPresence.discordRichPresence.largeImageKey = "europawaifu";
        DiscordPresence.discordRichPresence.largeImageText = clientVersion;
        DiscordPresence.discordRichPresence.state = null;
        discordRPC.Discord_UpdatePresence(discordRichPresence);
    }

    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
        discordRPC.Discord_ClearPresence();
    }

    public static void lambda$startRPC$0(final int var1, final String var2) {
        System.out.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2);
    }
}

