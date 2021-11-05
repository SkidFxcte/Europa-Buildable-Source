//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.modules.player;

import com.europa.client.minecraft.PlayerControllerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.module.Module;

public class ModuleAntiHunger extends Module
{
    public static ValueBoolean cancelSprint;

    public ModuleAntiHunger() {
        super("AntiHunger", "Anti Hunger", "Prevents the player frorm getting hungry.", ModuleCategory.PLAYER);
    }

    @SubscribeEvent
    public void onSend(final EventPacket.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            com.europa.client.minecraft.CPacketPlayer.onGround = (ModuleAntiHunger.mc.player.fallDistance >= Float.intBitsToFloat(Float.floatToIntBits(1.0790363E38f) ^ 0x7EA25AF7) || PlayerControllerMP.isHittingBlock);
        }
        if (event.getPacket() instanceof CPacketEntityAction) {
            if (ModuleAntiHunger.cancelSprint.getValue()) {
                final CPacketEntityAction packet2 = (CPacketEntityAction)event.getPacket();
                if (packet2.getAction() != CPacketEntityAction.Action.START_SPRINTING) {
                    if (packet2.getAction() != CPacketEntityAction.Action.STOP_SPRINTING) {
                        return;
                    }
                }
                event.setCancelled(true);
            }
        }
    }

    static {
        ModuleAntiHunger.cancelSprint = new ValueBoolean("CancelSprint", "CancelSprint", "", true);
    }
}
