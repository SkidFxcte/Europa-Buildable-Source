/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.entity.EventRenderEntityName;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderPlayer.class})
public class MixinRenderPlayer {
    @Inject(method={"renderEntityName"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        EventRenderEntityName l_Event = new EventRenderEntityName(entityIn, x, y, z, name, distanceSq);
        MinecraftForge.EVENT_BUS.post((Event)l_Event);
        if (l_Event.isCancelled()) {
            info.cancel();
        }
    }
}

