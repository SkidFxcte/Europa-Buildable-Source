//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.mixins.impl;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;
import com.europa.api.manager.event.impl.network.EventDeath;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.EntityPlayer;
import com.europa.api.utilities.IMinecraft;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetHandlerPlayClient.class })
public class MixinNetHandlerPlayClient
{
    @Inject(method = { "handleEntityMetadata" }, at = { @At("RETURN") }, cancellable = true)
    private void handleEntityMetadataHook(final SPacketEntityMetadata packetIn, final CallbackInfo info) {
        final Entity entity;
        final EntityPlayer player;
        if (IMinecraft.mc.world != null && (entity = IMinecraft.mc.world.getEntityByID(packetIn.getEntityId())) instanceof EntityPlayer && (player = (EntityPlayer)entity).getHealth() <= 0.0f) {
            MinecraftForge.EVENT_BUS.post((Event)new EventDeath(player));
        }
    }
}
