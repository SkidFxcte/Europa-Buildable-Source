/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.player.EventPush;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={Entity.class})
public abstract class MixinEntity {
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;

    @Shadow
    public void move(MoverType type, double x, double y, double z) {
    }

    @Redirect(method={"applyEntityCollision"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void addVelocityHook(Entity entity, double x, double y, double z) {
        EventPush event = new EventPush(entity, x, y, z, true);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCancelled()) {
            entity.motionX += event.x;
            entity.motionY += event.y;
            entity.motionZ += event.z;
            entity.isAirBorne = event.airbone;
        }
    }
}

