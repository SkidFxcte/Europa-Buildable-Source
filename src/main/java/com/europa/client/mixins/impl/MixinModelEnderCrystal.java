/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.client.modules.render.ModuleCrystalChams;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={ModelEnderCrystal.class})
public class MixinModelEnderCrystal {
    @Shadow
    private ModelRenderer cube;
    @Shadow
    private ModelRenderer glass;
    @Shadow
    private ModelRenderer base;

    @Overwrite
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.translate((float)0.0f, (float)-0.5f, (float)0.0f);
        if (this.base != null) {
            this.base.render(scale);
        }
        GlStateManager.rotate((float)limbSwingAmount, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.translate((float)0.0f, (float)(0.8f + ageInTicks), (float)0.0f);
        GlStateManager.rotate((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        if (Europa.getModuleManager().isModuleEnabled("CrystalChams")) {
            if (ModuleCrystalChams.outsideCube.getValue()) {
                this.glass.render(scale);
            }
        } else {
            this.glass.render(scale);
        }
        float f = 0.875f;
        GlStateManager.scale((float)0.875f, (float)0.875f, (float)0.875f);
        GlStateManager.rotate((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        GlStateManager.rotate((float)limbSwingAmount, (float)0.0f, (float)1.0f, (float)0.0f);
        if (Europa.getModuleManager().isModuleEnabled("CrystalChams")) {
            if (ModuleCrystalChams.outsideCube2.getValue()) {
                this.glass.render(scale);
            }
        } else {
            this.glass.render(scale);
        }
        GlStateManager.scale((float)0.875f, (float)0.875f, (float)0.875f);
        GlStateManager.rotate((float)60.0f, (float)0.7071f, (float)0.0f, (float)0.7071f);
        GlStateManager.rotate((float)limbSwingAmount, (float)0.0f, (float)1.0f, (float)0.0f);
        if (Europa.getModuleManager().isModuleEnabled("CrystalChams")) {
            if (ModuleCrystalChams.insideCube.getValue()) {
                this.cube.render(scale);
            }
        } else {
            this.cube.render(scale);
        }
        GlStateManager.popMatrix();
    }
}

