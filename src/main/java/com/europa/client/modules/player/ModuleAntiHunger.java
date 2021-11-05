/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleAntiHunger
extends Module {
    public static ValueBoolean cancelSprint = new ValueBoolean("CancelSprint", "CancelSprint", "", true);

    public ModuleAntiHunger() {
        super("AntiHunger", "Anti Hunger", "Prevents the player frorm getting hungry.", ModuleCategory.PLAYER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onSend(EventPacket.Send send) {
        block1: {
            block2: {
                CPacketPlayer packet;
                if (send.getPacket() instanceof CPacketPlayer) {
                    packet = (CPacketPlayer)send.getPacket();
                    packet.isOnGround() = ModuleAntiHunger.mc.player.fallDistance >= Float.intBitsToFloat(Float.floatToIntBits(1.0790363E38f) ^ 0x7EA25AF7) || ModuleAntiHunger.mc.playerController.isHittingBlock;
                    boolean bl = packet.isOnGround();
                }
                if (!(send.getPacket() instanceof CPacketEntityAction) || !cancelSprint.getValue()) break block1;
                packet = (CPacketPlayer) send.getPacket();
                if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING) break block2;
                if (packet.getAction() != CPacketEntityAction.Action.STOP_SPRINTING) break block1;
            }
            send.setCancelled(true);
        }
    }
}

