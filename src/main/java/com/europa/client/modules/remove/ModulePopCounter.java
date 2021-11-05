/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.client.modules.client.ModuleNotifications;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModulePopCounter
extends Module {
    public static HashMap<String, Integer> totem_pop_counter = new HashMap();
    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;

    public ModulePopCounter() {
        super("PopCounter", "Pop Counter", "", ModuleCategory.COMBAT);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        block6: {
            SPacketEntityStatus packet;
            if (ModulePopCounter.mc.player == null || ModulePopCounter.mc.world == null) {
                return;
            }
            if (!(receive.getPacket() instanceof SPacketEntityStatus) || (packet = (SPacketEntityStatus)receive.getPacket()).getOpCode() != 35) break block6;
            Entity entity = packet.getEntity((World)ModulePopCounter.mc.world);
            int count = 1;
            if (totem_pop_counter.containsKey(entity.getName())) {
                count = totem_pop_counter.get(entity.getName());
                totem_pop_counter.put(entity.getName(), ++count);
            } else {
                totem_pop_counter.put(entity.getName(), count);
            }
            if (entity == ModulePopCounter.mc.player) {
                return;
            }
            if (Europa.FRIEND_MANAGER.isFriend(entity.getName())) {
                ChatManager.sendClientMessage(ChatFormatting.GOLD + entity.getName() + ChatFormatting.WHITE + " popped " + red + count + ChatFormatting.WHITE + " totems! you should go help them", -6969420);
                this.sendNotif(ChatFormatting.GOLD + entity.getName() + ChatFormatting.WHITE + " popped " + red + count + ChatFormatting.WHITE + " totems! you should go help them");
            } else {
                ChatManager.sendClientMessage(ChatFormatting.GOLD + entity.getName() + ChatFormatting.WHITE + " popped " + red + count + ChatFormatting.WHITE + " totems!", -6969420);
                this.sendNotif(ChatFormatting.GOLD + entity.getName() + ChatFormatting.WHITE + " popped " + red + count + ChatFormatting.WHITE + " totems!");
            }
        }
    }

    @Override
    public void onMotionUpdate() {
        if (ModulePopCounter.mc.player == null || ModulePopCounter.mc.world == null) {
            return;
        }
        for (EntityPlayer player : ModulePopCounter.mc.world.playerEntities) {
            if (!totem_pop_counter.containsKey(player.getName()) || !player.isDead && !(player.getHealth() <= Float.intBitsToFloat(Float.floatToIntBits(1.0948523E38f) ^ 0x7EA4BC2D))) continue;
            int count = totem_pop_counter.get(player.getName());
            totem_pop_counter.remove(player.getName());
            if (player == ModulePopCounter.mc.player) continue;
            ChatManager.sendClientMessage(ChatFormatting.GOLD + player.getName() + ChatFormatting.WHITE + " died after popping " + green + count + ChatFormatting.WHITE + " totems!", -6969420);
            this.sendNotif(ChatFormatting.GOLD + player.getName() + ChatFormatting.WHITE + " died after popping " + green + count + ChatFormatting.WHITE + " totems!");
        }
    }

    /*
     * WARNING - void declaration
     */
    public void sendNotif(String string) {
        block0: {
            if (!Europa.getModuleManager().isModuleEnabled("Notifications") || !ModuleNotifications.pops.getValue()) break block0;
            Europa.NOTIFICATION_PROCESSOR.addNotification((String)string, ModuleNotifications.lifetime.getValue().intValue(), ModuleNotifications.inOutTime.getValue().intValue());
        }
    }
}

