/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.Event;
import com.europa.api.manager.event.impl.network.EventPacket;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={NetworkManager.class})
public class MixinNetworkManager {
    @Inject(method={"sendPacket(Lnet/minecraft/network/Packet;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        EventPacket.Send event = new EventPacket.Send(Event.Stage.PRE, packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"channelRead0"}, at={@At(value="HEAD")}, cancellable=true)
    private void onReceivePacket(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callbackInfo) {
        EventPacket.Receive event = new EventPacket.Receive(Event.Stage.POST, packet);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}

