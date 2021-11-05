/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueString;

import java.util.Iterator;
import java.util.ListIterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleStreamerMode
extends Module {
    public static ValueBoolean hideYou = new ValueBoolean("HideIGN", "HideIGN", "", false);
    public static ValueString yourName = new ValueString("YourName", "YourName", "", "You");
    public static ValueBoolean hideName = new ValueBoolean("HideOthers", "HideOthers", "", false);
    public static ValueString otherName = new ValueString("OthersName", "OthersName", "", "Enemy");
    public static ValueBoolean hideF3 = new ValueBoolean("HideF3", "HideF3", "", false);

    public ModuleStreamerMode() {
        super("StreamerMode", "Streamer Mode", "", ModuleCategory.CLIENT);
    }

    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.getType() != ChatType.GAME_INFO) {
                if (this.getChatNames(packet.getChatComponent().getFormattedText(), packet.getChatComponent().getUnformattedText())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public boolean getChatNames(final String message, final String unformatted) {
        String out = message;
        if (ModuleStreamerMode.hideName.getValue()) {
            if (ModuleStreamerMode.mc.player == null) {
                return false;
            }
            for (final Object o : ModuleStreamerMode.mc.world.playerEntities) {
                if (o instanceof EntityPlayer) {
                    if (o == ModuleStreamerMode.mc.player) {
                        continue;
                    }
                    final EntityPlayer ent = (EntityPlayer)o;
                    if (!Europa.FRIEND_MANAGER.isFriend(ent.getName())) {
                        if (!out.contains(ent.getName())) {
                            continue;
                        }
                        out = out.replaceAll(ent.getName(), ModuleStreamerMode.otherName.getValue());
                    }
                    else {
                        if (!Europa.FRIEND_MANAGER.isFriend(ent.getName()) || !out.contains(ent.getName())) {
                            continue;
                        }
                        out = out.replaceAll(ent.getName(), "Friend");
                    }
                }
            }
        }
        if (ModuleStreamerMode.hideYou.getValue()) {
            if (ModuleStreamerMode.mc.player == null) {
                return false;
            }
            out = out.replace(ModuleStreamerMode.mc.player.getName(), ModuleStreamerMode.yourName.getValue());
        }
        ChatManager.sendRawMessage(out);
        return true;
    }

    @SubscribeEvent
    public void renderOverlayEvent(final RenderGameOverlayEvent.Text event) {
        if (FMLClientHandler.instance().getClient().player.capabilities.isCreativeMode) {
            return;
        }
        if (!ModuleStreamerMode.hideF3.getValue()) {
            return;
        }
        final Iterator<String> it = (Iterator<String>)event.getLeft().listIterator();
        while (it.hasNext()) {
            final String value = it.next();
            if ((value == null || !value.startsWith("XYZ:")) && !value.startsWith("Looking at:")) {
                if (!value.startsWith("Block:")) {
                    if (!value.startsWith("Facing:")) {
                        continue;
                    }
                }
            }
            it.remove();
        }
    }

    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        dname = dname.replace(ModuleStreamerMode.mc.player.getName(), ModuleStreamerMode.yourName.getValue());
        return dname;
    }
}

