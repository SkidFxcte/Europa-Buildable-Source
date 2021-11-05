/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.player.EventKey;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={KeyBinding.class})
public class MixinKeyBinding {
    @Shadow
    private boolean pressed;

    @Inject(method={"isKeyDown"}, at={@At(value="RETURN")}, cancellable=true)
    private void isKeyDown(CallbackInfoReturnable<Boolean> info) {
        EventKey event = new EventKey(info.getReturnValue(), this.pressed);
        MinecraftForge.EVENT_BUS.post((Event)event);
        info.setReturnValue(event.info);
    }
}

