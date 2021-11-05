package com.europa.client.minecraft;

import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;


public abstract class CPacketUseEntity implements Packet<INetHandlerPlayServer>
{
    public static net.minecraft.network.play.client.CPacketUseEntity.Action action;
    public static enum Action
    {
        INTERACT,
        ATTACK,
        INTERACT_AT;
    }
}