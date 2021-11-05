/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.client.minecraft.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class ModuleAntiWeb
extends Module {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "mode", modes.MotionY);
    public static ValueBoolean packetMotionY = new ValueBoolean("PacketMotion", "packetmotion", "", false);

    public ModuleAntiWeb() {
        super("AntiWeb", "Anti Web", "Puts you into the hole when you are in web.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        if (Entity.isInWeb) {
            switch (mode.getValue().ordinal()) {
                case 1: {
                    if (packetMotionY.getValue()) {
                        double d = ModuleAntiWeb.mc.player.posY;
                        ModuleAntiWeb.mc.player.posY = d - Double.longBitsToDouble(Double.doubleToLongBits(15.454405508049296) ^ 0x7FDEE8A7D6B86353L);
                        ModuleAntiWeb.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleAntiWeb.mc.player.posX, d, ModuleAntiWeb.mc.player.posZ, ModuleAntiWeb.mc.player.onGround));
                        break;
                    }
                    ModuleAntiWeb.mc.player.motionY -= Double.longBitsToDouble(Double.doubleToLongBits(44.89141659768665) ^ 0x7FB67219F067167FL);
                    break;
                }
                case 2: {
                    Entity.isInWeb = false;
                }
            }
        }
    }

    public static enum modes {
        Vanilla,
        MotionY;

    }
}

