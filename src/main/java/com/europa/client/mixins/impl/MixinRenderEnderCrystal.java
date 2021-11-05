/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.api.utilities.render.OutlineUtils;
import com.europa.client.gui.click.components.impl.PreviewComponent;
import com.europa.client.modules.render.ModuleCrystalChams;
import com.europa.client.modules.render.ModuleESP;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderEnderCrystal.class})
public abstract class MixinRenderEnderCrystal {
    @Shadow
    public ModelBase modelEnderCrystal;
    @Shadow
    public ModelBase modelEnderCrystalNoBase;
    @Final
    @Shadow
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    private static final ResourceLocation RES_ITEM_GLINT;

    @Shadow
    public abstract void doRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9);

    @Redirect(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void render1(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        if (!Europa.getModuleManager().isModuleEnabled("CrystalChams")) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }

    @Redirect(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal=1))
    private void render2(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        if (!Europa.getModuleManager().isModuleEnabled("CrystalChams")) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }

    @Inject(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at={@At(value="RETURN")}, cancellable=true)
    public void IdoRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.fancyGraphics = false;
        if (Europa.getModuleManager().isModuleEnabled("ESP") && ModuleESP.others.getValue()) {
            float var13 = (float)var1.innerRotation + var9;
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)var2, (double)var4, (double)var6);
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            float var14 = MathHelper.sin((float)(var13 * 0.2f)) / 2.0f + 0.5f;
            var14 += var14 * var14;
            GL11.glLineWidth((float)5.0f);
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            OutlineUtils.renderOne((float)ModuleESP.width.getValue().doubleValue());
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            OutlineUtils.renderTwo();
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            Color rainbowColor1 = new Color(ModuleESP.color.getRed(), ModuleESP.color.getGreen(), ModuleESP.color.getBlue());
            Color rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
            Color n = new Color(rainbowColor2.getRed(), rainbowColor2.getGreen(), rainbowColor2.getBlue());
            OutlineUtils.renderThree();
            OutlineUtils.renderFour();
            OutlineUtils.setColor(n);
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var13 * 3.0f, var14 * 0.2f, 0.0f, 0.0f, 0.0625f);
            }
            OutlineUtils.renderFive();
            GlStateManager.popMatrix();
        }
        if (Europa.getModuleManager().isModuleEnabled("CrystalChams")) {
            GL11.glPushMatrix();
            float var14 = (float)var1.innerRotation + var9;
            GlStateManager.translate((double)var2, (double)var4, (double)var6);
            if (var1 == PreviewComponent.entityEnderCrystal) {
                GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
            } else {
                GlStateManager.scale((float)ModuleCrystalChams.size.getValue().floatValue(), (float)ModuleCrystalChams.size.getValue().floatValue(), (float)ModuleCrystalChams.size.getValue().floatValue());
            }
            GlStateManager.scale((float)ModuleCrystalChams.size.getValue().floatValue(), (float)ModuleCrystalChams.size.getValue().floatValue(), (float)ModuleCrystalChams.size.getValue().floatValue());
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(ENDER_CRYSTAL_TEXTURES);
            float var15 = MathHelper.sin((float)(var14 * 0.2f)) / 2.0f + 0.5f;
            var15 += var15 * var15;
            float spinSpeed = ModuleCrystalChams.crystalSpeed.getValue().floatValue();
            float bounceSpeed = ModuleCrystalChams.crystalBounce.getValue().floatValue();
            if (ModuleCrystalChams.texture.getValue()) {
                if (var1.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                } else {
                    this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
            }
            GL11.glPushAttrib((int)1048575);
            if (ModuleCrystalChams.mode.getValue().equals((Object)ModuleCrystalChams.modes.Wireframe)) {
                GL11.glPolygonMode((int)1032, (int)6913);
            }
            GL11.glDisable((int)3008);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.5f);
            GL11.glEnable((int)2960);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)10754);
            if (ModuleCrystalChams.lifetimeColor.getValue()) {
                if (ModuleCrystalChams.thingers.containsKey(var1.getUniqueID())) {
                    GL11.glColor4f((float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getRed() / 255.0f), (float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getGreen() / 255.0f), (float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getBlue() / 255.0f), (float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getAlpha() / 255.0f));
                } else {
                    GL11.glColor4f((float)((float)ModuleCrystalChams.lifeStart.getValue().getRed() / 255.0f), (float)((float)ModuleCrystalChams.lifeStart.getValue().getGreen() / 255.0f), (float)((float)ModuleCrystalChams.lifeStart.getValue().getBlue() / 255.0f), (float)((float)ModuleCrystalChams.lifeStart.getValue().getAlpha() / 255.0f));
                }
            } else if (ModuleCrystalChams.hiddenSync.getValue()) {
                GL11.glColor4f((float)((float)ModuleCrystalChams.color.getRed() / 255.0f), (float)((float)ModuleCrystalChams.color.getGreen() / 255.0f), (float)((float)ModuleCrystalChams.color.getBlue() / 255.0f), (float)((float)ModuleCrystalChams.daColor.getValue().getAlpha() / 255.0f));
            } else {
                GL11.glColor4f((float)((float)ModuleCrystalChams.hideColor.getRed() / 255.0f), (float)((float)ModuleCrystalChams.hideColor.getGreen() / 255.0f), (float)((float)ModuleCrystalChams.hideColor.getBlue() / 255.0f), (float)((float)ModuleCrystalChams.hiddenColor.getValue().getAlpha() / 255.0f));
            }
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            if (ModuleCrystalChams.lifetimeColor.getValue()) {
                if (ModuleCrystalChams.thingers.containsKey(var1.getUniqueID())) {
                    GL11.glColor4f((float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getRed() / 255.0f), (float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getGreen() / 255.0f), (float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getBlue() / 255.0f), (float)((float)ModuleCrystalChams.thingers.get((Object)var1.getUniqueID()).color.getAlpha() / 255.0f));
                } else {
                    GL11.glColor4f((float)((float)ModuleCrystalChams.lifeStart.getValue().getRed() / 255.0f), (float)((float)ModuleCrystalChams.lifeStart.getValue().getGreen() / 255.0f), (float)((float)ModuleCrystalChams.lifeStart.getValue().getBlue() / 255.0f), (float)((float)ModuleCrystalChams.lifeStart.getValue().getAlpha() / 255.0f));
                }
            } else {
                GL11.glColor4f((float)((float)ModuleCrystalChams.color.getRed() / 255.0f), (float)((float)ModuleCrystalChams.color.getGreen() / 255.0f), (float)((float)ModuleCrystalChams.color.getBlue() / 255.0f), (float)((float)ModuleCrystalChams.daColor.getValue().getAlpha() / 255.0f));
            }
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            if (ModuleCrystalChams.enchanted.getValue()) {
                mc.getTextureManager().bindTexture(RES_ITEM_GLINT);
                GL11.glTexCoord3d((double)1.0, (double)1.0, (double)1.0);
                GL11.glEnable((int)3553);
                GL11.glBlendFunc((int)768, (int)771);
                GL11.glColor4f((float)((float)ModuleCrystalChams.enchantedColor.getValue().getRed() / 255.0f), (float)((float)ModuleCrystalChams.enchantedColor.getValue().getGreen() / 255.0f), (float)((float)ModuleCrystalChams.enchantedColor.getValue().getBlue() / 255.0f), (float)((float)ModuleCrystalChams.enchantedColor.getValue().getAlpha() / 255.0f));
                if (var1.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                } else {
                    this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            }
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)3008);
            GL11.glPopAttrib();
            if (ModuleCrystalChams.outline.getValue()) {
                if (ModuleCrystalChams.outlineMode.getValue().equals((Object)ModuleCrystalChams.outlineModes.Wire)) {
                    GL11.glPushAttrib((int)1048575);
                    GL11.glPolygonMode((int)1032, (int)6913);
                    GL11.glDisable((int)3008);
                    GL11.glDisable((int)3553);
                    GL11.glDisable((int)2896);
                    GL11.glEnable((int)3042);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glLineWidth((float)ModuleCrystalChams.width.getValue().floatValue());
                    GL11.glEnable((int)2960);
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                    GL11.glEnable((int)10754);
                    GL11.glColor4f((float)((float)ModuleCrystalChams.outColor.getRed() / 255.0f), (float)((float)ModuleCrystalChams.outColor.getGreen() / 255.0f), (float)((float)ModuleCrystalChams.outColor.getBlue() / 255.0f), (float)1.0f);
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    GL11.glEnable((int)3042);
                    GL11.glEnable((int)2896);
                    GL11.glEnable((int)3553);
                    GL11.glEnable((int)3008);
                    GL11.glPopAttrib();
                } else {
                    OutlineUtils.setColor(ModuleCrystalChams.outColor);
                    OutlineUtils.renderOne(ModuleCrystalChams.width.getValue().floatValue());
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    OutlineUtils.renderTwo();
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    OutlineUtils.renderThree();
                    OutlineUtils.renderFour();
                    OutlineUtils.setColor(ModuleCrystalChams.outColor);
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var14 * spinSpeed, var15 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    OutlineUtils.renderFive();
                    OutlineUtils.setColor(Color.WHITE);
                }
            }
            GL11.glPopMatrix();
        }
    }

    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}

