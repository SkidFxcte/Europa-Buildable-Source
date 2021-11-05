/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.client.modules.render.ModuleNoRender;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LayerArmorBase.class})
public class MixinLayerArmorBase {
    @Inject(method={"doRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo info) {
        if (Europa.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.armor.getValue()) {
            info.cancel();
        }
    }
}

