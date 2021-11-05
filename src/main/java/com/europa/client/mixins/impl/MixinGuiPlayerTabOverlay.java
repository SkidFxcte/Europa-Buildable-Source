/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.client.modules.client.ModuleStreamerMode;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={GuiPlayerTabOverlay.class})
public class MixinGuiPlayerTabOverlay {
    @Inject(method={"getPlayerName"}, at={@At(value="HEAD")}, cancellable=true)
    public void getPlayerName(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable returnable) {
        if (Europa.getModuleManager().isModuleEnabled("StreamerMode") && ModuleStreamerMode.hideYou.getValue()) {
            returnable.cancel();
            returnable.setReturnValue(ModuleStreamerMode.getPlayerName(networkPlayerInfoIn));
        }
    }
}

