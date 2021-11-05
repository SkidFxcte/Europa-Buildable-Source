/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.render.EventBossBar;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiBossOverlay.class})
public class MixinGuiBossOverlay {
    @Inject(method={"renderBossHealth"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderBossHealth(CallbackInfo info) {
        EventBossBar event = new EventBossBar();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}

