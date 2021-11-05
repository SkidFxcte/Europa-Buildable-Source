//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.mixins.impl;

import org.spongepowered.asm.mixin.Overwrite;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import com.europa.api.utilities.render.OutlineUtils;
import com.europa.client.modules.render.ModulePlayerChams;
import org.lwjgl.opengl.GL11;
import com.europa.client.modules.render.ModuleKillEffects;
import com.europa.client.modules.render.ModulePopChams;
import com.europa.Europa;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;

@Mixin(value = { RenderLivingBase.class }, priority = 999)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    protected ModelBase mainModel;
    private static final ResourceLocation RES_ITEM_GLINT;

    protected MixinRendererLivingEntity() {
        super((RenderManager)null);
    }

    @Overwrite
    protected void renderModel(final T entitylivingbaseIn, final float p_77036_2_, final float p_77036_3_, final float p_77036_4_, final float p_77036_5_, final float p_77036_6_, final float scaleFactor) {
        final boolean isPlayer = entitylivingbaseIn instanceof EntityPlayer;
        if (!this.bindEntityTexture((T) entitylivingbaseIn)) {
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.fancyGraphics = false;
        if (Europa.getModuleManager().isModuleEnabled("PlayerChams") && isPlayer && entitylivingbaseIn != ModulePopChams.player && entitylivingbaseIn != ModuleKillEffects.player) {
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(10754);
            if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName()) && !ModulePlayerChams.syncFriend.getValue()) {
                if (ModulePlayerChams.fhiddenSync.getValue()) {
                    GL11.glColor4f(ModulePlayerChams.fcolor.getRed() / 255.0f, ModulePlayerChams.fcolor.getGreen() / 255.0f, ModulePlayerChams.fcolor.getBlue() / 255.0f, ModulePlayerChams.fdaColor.getValue().getAlpha() / 255.0f);
                }
                else {
                    GL11.glColor4f(ModulePlayerChams.fhideColor.getRed() / 255.0f, ModulePlayerChams.fhideColor.getGreen() / 255.0f, ModulePlayerChams.fhideColor.getBlue() / 255.0f, ModulePlayerChams.fhiddenColor.getValue().getAlpha() / 255.0f);
                }
            }
            else if (ModulePlayerChams.hiddenSync.getValue()) {
                GL11.glColor4f(ModulePlayerChams.color.getRed() / 255.0f, ModulePlayerChams.color.getGreen() / 255.0f, ModulePlayerChams.color.getBlue() / 255.0f, ModulePlayerChams.daColor.getValue().getAlpha() / 255.0f);
            }
            else {
                GL11.glColor4f(ModulePlayerChams.hideColor.getRed() / 255.0f, ModulePlayerChams.hideColor.getGreen() / 255.0f, ModulePlayerChams.hideColor.getBlue() / 255.0f, ModulePlayerChams.hiddenColor.getValue().getAlpha() / 255.0f);
            }
            if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName())) {
                if (ModulePlayerChams.fhidden.getValue()) {
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                }
            }
            else if (ModulePlayerChams.hidden.getValue()) {
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            }
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName()) && !ModulePlayerChams.syncFriend.getValue()) {
                GL11.glColor4f(ModulePlayerChams.fcolor.getRed() / 255.0f, ModulePlayerChams.fcolor.getGreen() / 255.0f, ModulePlayerChams.fcolor.getBlue() / 255.0f, ModulePlayerChams.fdaColor.getValue().getAlpha() / 255.0f);
            }
            else {
                GL11.glColor4f(ModulePlayerChams.color.getRed() / 255.0f, ModulePlayerChams.color.getGreen() / 255.0f, ModulePlayerChams.color.getBlue() / 255.0f, ModulePlayerChams.daColor.getValue().getAlpha() / 255.0f);
            }
            if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName())) {
                if (ModulePlayerChams.fvisible.getValue()) {
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                }
            }
            else if (ModulePlayerChams.visible.getValue()) {
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            }
            if (ModulePlayerChams.enchanted.getValue()) {
                mc.getTextureManager().bindTexture(MixinRendererLivingEntity.RES_ITEM_GLINT);
                GL11.glTexCoord3d(1.0, 1.0, 1.0);
                GL11.glEnable(3553);
                GL11.glBlendFunc(768, 771);
                GL11.glColor4f(ModulePlayerChams.enchantedColor.getValue().getRed() / 255.0f, ModulePlayerChams.enchantedColor.getValue().getGreen() / 255.0f, ModulePlayerChams.enchantedColor.getValue().getBlue() / 255.0f, ModulePlayerChams.enchantedColor.getValue().getAlpha() / 255.0f);
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            if (ModulePlayerChams.outline.getValue()) {
                if (ModulePlayerChams.outlineMode.getValue().equals(ModulePlayerChams.outlineModes.Wire)) {
                    GL11.glPushAttrib(1048575);
                    GL11.glPolygonMode(1032, 6913);
                    GL11.glDisable(3008);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth(ModulePlayerChams.width.getValue().floatValue());
                    GL11.glEnable(2960);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName()) && !ModulePlayerChams.syncFriend.getValue()) {
                        GL11.glColor4f(ModulePlayerChams.foutColor.getRed() / 255.0f, ModulePlayerChams.foutColor.getGreen() / 255.0f, ModulePlayerChams.foutColor.getBlue() / 255.0f, 1.0f);
                    }
                    else {
                        GL11.glColor4f(ModulePlayerChams.outColor.getRed() / 255.0f, ModulePlayerChams.outColor.getGreen() / 255.0f, ModulePlayerChams.outColor.getBlue() / 255.0f, 1.0f);
                    }
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    GL11.glEnable(3042);
                    GL11.glEnable(2896);
                    GL11.glEnable(3553);
                    GL11.glEnable(3008);
                    GL11.glPopAttrib();
                }
                else {
                    if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName()) && !ModulePlayerChams.syncFriend.getValue()) {
                        OutlineUtils.setColor(ModulePlayerChams.foutColor);
                    }
                    else {
                        OutlineUtils.setColor(ModulePlayerChams.outColor);
                    }
                    OutlineUtils.renderOne(ModulePlayerChams.width.getValue().floatValue());
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    OutlineUtils.renderTwo();
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    OutlineUtils.renderThree();
                    OutlineUtils.renderFour();
                    if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName()) && !ModulePlayerChams.syncFriend.getValue()) {
                        OutlineUtils.setColor(ModulePlayerChams.foutColor);
                    }
                    else {
                        OutlineUtils.setColor(ModulePlayerChams.outColor);
                    }
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                    OutlineUtils.renderFive();
                    OutlineUtils.setColor(Color.WHITE);
                }
            }
            if (Europa.FRIEND_MANAGER.isFriend(entitylivingbaseIn.getName())) {
                if (!ModulePlayerChams.fvisible.getValue()) {
                    this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                }
            }
            else if (!ModulePlayerChams.visible.getValue()) {
                this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            }
        }
        if (entitylivingbaseIn == ModulePopChams.player && ModulePopChams.player != null) {
            GL11.glPushAttrib(1048575);
            RenderUtils.prepareGL();
            GL11.glEnable(2881);
            GL11.glEnable(2848);
            GL11.glColor4f(ModulePopChams.color.getRed() / 255.0f, ModulePopChams.color.getGreen() / 255.0f, ModulePopChams.color.getBlue() / 255.0f, ModulePopChams.opacity);
            GL11.glPolygonMode(1032, 6914);
            this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            GL11.glColor4f(ModulePopChams.outlineColor.getRed() / 255.0f, ModulePopChams.outlineColor.getGreen() / 255.0f, ModulePopChams.outlineColor.getBlue() / 255.0f, ModulePopChams.opacity);
            GL11.glPolygonMode(1032, 6913);
            this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            GL11.glPolygonMode(1032, 6914);
            GL11.glPopAttrib();
            RenderUtils.releaseGL();
        }
        if (entitylivingbaseIn == ModuleKillEffects.player && ModuleKillEffects.player != null) {
            GL11.glPushAttrib(1048575);
            RenderUtils.prepareGL();
            GL11.glEnable(2881);
            GL11.glEnable(2848);
            GL11.glColor4f(ModuleKillEffects.chamColor.getValue().getRed() / 255.0f, ModuleKillEffects.chamColor.getValue().getGreen() / 255.0f, ModuleKillEffects.chamColor.getValue().getBlue() / 255.0f, ModuleKillEffects.opacity);
            GL11.glPolygonMode(1032, 6914);
            this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            GL11.glColor4f(ModuleKillEffects.chamColor.getValue().getRed() / 255.0f, ModuleKillEffects.chamColor.getValue().getGreen() / 255.0f, ModuleKillEffects.chamColor.getValue().getBlue() / 255.0f, ModuleKillEffects.opacity);
            GL11.glPolygonMode(1032, 6913);
            this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            GL11.glPolygonMode(1032, 6914);
            GL11.glPopAttrib();
            RenderUtils.releaseGL();
        }
        if ((!Europa.getModuleManager().isModuleEnabled("PlayerChams") || !isPlayer) && entitylivingbaseIn != ModulePopChams.player) {
            this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
        }
    }

    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
