/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ModuleWelcomer
extends Module {
    public static ValueBoolean privateMsg = new ValueBoolean("Private", "Private", "", false);
    public static ArrayList<NetworkPlayerInfo> playerMap;
    public static int cachePlayerCount;
    public boolean isOnServer;
    public static ArrayList<String> greets;
    public static ArrayList<String> goodbyes;
    public static ArrayList<String> joinMessages;
    public static ArrayList<String> leaveMessages;

    public ModuleWelcomer() {
        super("Welcomer", "Welcomer", "", ModuleCategory.MISC);
        joinMessages.add("Hello, ");
        joinMessages.add("Welcome to the server, ");
        joinMessages.add("Nice to see you, ");
        joinMessages.add("Hey how are you, ");
        leaveMessages.add("Goodbye, ");
        leaveMessages.add("See you later, ");
        leaveMessages.add("Bye bye, ");
        leaveMessages.add("I hope you had a good time, ");
    }

    @Override
    public void onUpdate() {
        block0: {
            block1: {
                if (ModuleWelcomer.mc.player == null) break block0;
                if (ModuleWelcomer.mc.player.ticksExisted % 10 != 0) break block1;
                this.checkPlayers();
                break block0;
            }
            if (!mc.isSingleplayer()) break block0;
            this.toggle();
        }
    }

    @Override
    public void onEnable() {
        if (ModuleWelcomer.mc.player == null || ModuleWelcomer.mc.world == null) {
            return;
        }
        this.onJoinServer();
    }

    public void checkPlayers() {
        block3: {
            ArrayList infoMap = new ArrayList(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
            int currentPlayerCount = infoMap.size();
            if (currentPlayerCount == cachePlayerCount) break block3;
            ArrayList currentInfoMap = (ArrayList)infoMap.clone();
            currentInfoMap.removeAll(playerMap);
            if (currentInfoMap.size() > 5) {
                cachePlayerCount = playerMap.size();
                this.onJoinServer();
                return;
            }
            ArrayList playerMapClone = (ArrayList)playerMap.clone();
            playerMapClone.removeAll(infoMap);
            for (Object npi : currentInfoMap) {
                this.playerJoined((NetworkPlayerInfo) npi);
            }
            for (Object npi : playerMapClone) {
                this.playerLeft((NetworkPlayerInfo) npi);
            }
            cachePlayerCount = playerMap.size();
            this.onJoinServer();
        }
    }

    public void onJoinServer() {
        playerMap = new ArrayList(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
        cachePlayerCount = playerMap.size();
        this.isOnServer = true;
    }

    /*
     * WARNING - void declaration
     */
    public void playerJoined(NetworkPlayerInfo networkPlayerInfo) {
        block2: {
            Random random = new Random();
            if (joinMessages.isEmpty()) break block2;
            if (privateMsg.getValue()) {
                ChatManager.printChatNotifyClient(joinMessages.get(random.nextInt(joinMessages.size())) + networkPlayerInfo.getGameProfile().getName());
            } else {
                ModuleWelcomer.mc.player.sendChatMessage(joinMessages.get(random.nextInt(joinMessages.size())) + networkPlayerInfo.getGameProfile().getName());
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void playerLeft(NetworkPlayerInfo networkPlayerInfo) {
        block2: {
            Random random = new Random();
            if (leaveMessages.isEmpty()) break block2;
            if (privateMsg.getValue()) {
                ChatManager.printChatNotifyClient(leaveMessages.get(random.nextInt(leaveMessages.size())) + networkPlayerInfo.getGameProfile().getName());
            } else {
                ModuleWelcomer.mc.player.sendChatMessage(leaveMessages.get(random.nextInt(leaveMessages.size())) + networkPlayerInfo.getGameProfile().getName());
            }
        }
    }

    static {
        joinMessages = new ArrayList();
        leaveMessages = new ArrayList();
        playerMap = new ArrayList();
        greets = new ArrayList();
        goodbyes = new ArrayList();
    }
}

