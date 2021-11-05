/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.player.EventMotionUpdate;
import com.europa.api.manager.event.impl.player.EventPlayerMove;
import com.europa.api.manager.event.impl.player.EventPush;
import com.europa.client.mixins.impl.MixinEntity;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPlayerSP.class})
public class MixinEntityPlayerSP
extends MixinEntity {
    @Inject(method={"move"}, at={@At(value="HEAD")}, cancellable=true)
    private void move(MoverType type, double x, double y, double z, CallbackInfo info) {
        EventPlayerMove event = new EventPlayerMove(type, x, y, z);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            super.move(type, event.getX(), event.getY(), event.getZ());
            info.cancel();
        }
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void OnPreUpdateWalkingPlayer(CallbackInfo info) {
        EventMotionUpdate event = new EventMotionUpdate(0);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"onUpdateWalkingPlayer"}, at={@At(value="RETURN")}, cancellable=true)
    public void OnPostUpdateWalkingPlayer(CallbackInfo info) {
        EventMotionUpdate event = new EventMotionUpdate(1);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"pushOutOfBlocks"}, at={@At(value="HEAD")}, cancellable=true)
    private void pushOutOfBlocksHook(double x, double y, double z, CallbackInfoReturnable<Boolean> info) {
        EventPush event = new EventPush();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.setReturnValue(false);
        }
    }
}

