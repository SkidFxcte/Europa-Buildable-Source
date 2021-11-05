/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.client.modules.remove.ModuleViewModel;
import com.europa.client.modules.render.ModuleGlintModify;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={RenderItem.class})
public class MixinRenderItem {
    @Shadow
    private void renderModel(IBakedModel model, int color, ItemStack stack) {
    }

    @ModifyArg(method={"renderEffect"}, at=@At(value="INVOKE", target="net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index=1)
    private int renderEffect(int oldValue) {
        return Europa.getModuleManager().isModuleEnabled("GlintModify") ? ModuleGlintModify.getColor().getRGB() : oldValue;
    }

    @Redirect(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void POOOOOP(RenderItem renderItem, IBakedModel model, ItemStack stack) {
        if (Europa.getModuleManager().isModuleEnabled("ViewModel")) {
            this.renderModel(model, new Color(1.0f, 1.0f, 1.0f, (float)ModuleViewModel.viewAlpha.getValue().intValue() / 255.0f).getRGB(), stack);
        } else {
            this.renderModel(model, -1, stack);
        }
    }

    @Redirect(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private void renderItem(float colorRed, float colorGreen, float colorBlue, float alpha) {
        if (Europa.getModuleManager().isModuleEnabled("ViewModel")) {
            GlStateManager.color((float)colorRed, (float)colorGreen, (float)colorBlue, (float)((float)ModuleViewModel.viewAlpha.getValue().intValue() / 255.0f));
        } else {
            GlStateManager.color((float)colorRed, (float)colorGreen, (float)colorBlue, (float)alpha);
        }
    }
}

