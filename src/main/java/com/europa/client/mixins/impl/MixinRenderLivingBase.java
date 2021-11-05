//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.mixins.impl;

import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityMob;
import com.europa.api.utilities.render.OutlineUtils;
import net.minecraft.client.Minecraft;
import com.europa.client.modules.render.ModulePopChams;
import com.europa.Europa;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import net.minecraftforge.client.event.RenderLivingEvent;
import java.awt.Color;
import com.europa.client.modules.render.ModuleESP;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.europa.api.manager.event.impl.render.EventRenderEntityLayer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.europa.client.modules.render.ModuleShaderChams;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import java.util.List;
import java.nio.FloatBuffer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Shadow;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    @Shadow
    private static final Logger LOGGER;
    @Shadow
    private static final DynamicTexture TEXTURE_BRIGHTNESS;
    @Shadow
    protected ModelBase mainModel;
    @Shadow
    protected FloatBuffer brightnessBuffer;
    @Shadow
    protected List<LayerRenderer<T>> layerRenderers;
    @Shadow
    protected boolean renderMarker;
    @Shadow
    public static float NAME_TAG_RANGE;
    @Shadow
    public static float NAME_TAG_RANGE_SNEAK;

    public MixinRenderLivingBase() {
        super((RenderManager)null);
        this.brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
        this.layerRenderers = Lists.newArrayList();
    }
    @Shadow
    protected float getSwingProgress(final T livingBase, final float partialTickTime) {
        return livingBase.getSwingProgress(partialTickTime);
    }

    @Shadow
    protected float interpolateRotation(final float prevYawOffset, final float yawOffset, final float partialTicks) {
        float f;
        for (f = yawOffset - prevYawOffset; f < -180.0f; f += 360.0f) {}
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return prevYawOffset + partialTicks * f;
    }

    @Shadow
    protected void renderLivingAt(final T entityLivingBaseIn, final double x, final double y, final double z) {
        GlStateManager.translate((float)x, (float)y, (float)z);
    }

    @Shadow
    protected float handleRotationFloat(final T livingBase, final float partialTicks) {
        return livingBase.ticksExisted + partialTicks;
    }

    @Shadow
    protected void applyRotations(final T entityLiving, final float ageInTicks, final float rotationYaw, final float partialTicks) {
        GlStateManager.rotate(180.0f - rotationYaw, 0.0f, 1.0f, 0.0f);
        if (entityLiving.deathTime > 0) {
            float f = (entityLiving.deathTime + partialTicks - 1.0f) / 20.0f * 1.6f;
            if ((f = MathHelper.sqrt(f)) > 1.0f) {
                f = 1.0f;
            }
        }
        else {
            final String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
            if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer)entityLiving).isWearing(EnumPlayerModelParts.CAPE))) {
                GlStateManager.translate(0.0f, entityLiving.height + 0.1f, 0.0f);
                GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            }
        }
    }

    @Shadow
    public float prepareScale(final T entitylivingbaseIn, final float partialTicks) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        final float f = 0.0625f;
        GlStateManager.translate(0.0f, -1.501f, 0.0f);
        return 0.0625f;
    }

    @Shadow
    protected boolean setScoreTeamColor(final T entityLivingBaseIn) {
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    @Shadow
    protected void renderModel(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor) {
    }

    @Shadow
    protected void renderLayers(final T entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn) {
    }

    @Inject(method = { "renderName" }, at = { @At("HEAD") }, cancellable = true)
    public void obRenderNamePre(final T entity, final double x, final double y, final double z, final CallbackInfo ci) {
        if (!ModuleShaderChams.renderNameTags) {
            ci.cancel();
        }
    }

    @Inject(method = { "renderLayers" }, at = { @At("HEAD") }, cancellable = true)
    public void onInjectRenderLayers(final CallbackInfo ci) {
        if (!ModuleShaderChams.renderNameTags) {
            ci.cancel();
        }
    }

    @Redirect(method = { "renderLayers" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;doRenderLayer(Lnet/minecraft/entity/EntityLivingBase;FFFFFFF)V"))
    public void onRenderLayersDoLayers(final LayerRenderer<EntityLivingBase> layer, final EntityLivingBase entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn) {
        final EventRenderEntityLayer event = new EventRenderEntityLayer(entity, layer);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            layer.doRenderLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
        }
    }

    @Shadow
    protected void unsetScoreTeamColor() {
        GlStateManager.enableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Shadow
    protected boolean setDoRenderBrightness(final T entityLivingBaseIn, final float partialTicks) {
        return false;
    }

    @Shadow
    protected void unsetBrightness() {
    }

    @Overwrite
    public void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        final Color color = new Color(ModuleESP.color.getRed(), ModuleESP.color.getGreen(), ModuleESP.color.getBlue());
        final float lineWidth = (float)ModuleESP.width.getValue().doubleValue();
        if (MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)entity, (RenderLivingBase)RenderLivingBase.class.cast(this), partialTicks, x, y, z))) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GL11.glEnable(2848);
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        final boolean shouldSit = this.mainModel.isRiding = (entity.isRiding() && entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        this.mainModel.isChild = entity.isChild();
        try {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            final float f2 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f3 = f2 - f;
            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
                final EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f3 = f2 - f;
                float f4 = MathHelper.wrapDegrees(f3);
                if (f4 < -85.0f) {
                    f4 = -85.0f;
                }
                if (f4 >= 85.0f) {
                    f4 = 85.0f;
                }
                f = f2 - f4;
                if (f4 * f4 > 2500.0f) {
                    f += f4 * 0.2f;
                }
                f3 = f2 - f;
            }
            final float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            final float f6 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f6, f, partialTicks);
            final float f7 = this.prepareScale(entity, partialTicks);
            float f8 = 0.0f;
            float f9 = 0.0f;
            if (!entity.isRiding()) {
                f8 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f9 = entity.limbSwing - entity.limbSwingAmount * (1.0f - partialTicks);
                if (entity.isChild()) {
                    f9 *= 3.0f;
                }
                if (f8 > 1.0f) {
                    f8 = 1.0f;
                }
                f3 = f2 - f;
            }
            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations((EntityLivingBase)entity, f9, f8, partialTicks);
            this.mainModel.setRotationAngles(f9, f8, f6, f3, f5, f7, (Entity)entity);
            if (Europa.getModuleManager().isModuleEnabled("ESP")) {
                GlStateManager.depthMask(true);
                if (ModuleESP.players.getValue()) {
                    if (entity instanceof EntityPlayer && entity != ModulePopChams.player) {
                        if (entity != Minecraft.getMinecraft().player) {
                            OutlineUtils.setColor(color);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderOne(lineWidth);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderTwo();
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderThree();
                            OutlineUtils.renderFour();
                            OutlineUtils.setColor(color);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                        }
                    }
                    else {
                        this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                    }
                }
                if (ModuleESP.mobs.getValue()) {
                    if (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityMagmaCube || entity instanceof EntityGhast) {
                        if (entity != Minecraft.getMinecraft().player && !(entity instanceof EntityPigZombie)) {
                            OutlineUtils.setColor(Color.RED);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderOne(lineWidth);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderTwo();
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderThree();
                            OutlineUtils.renderFour();
                            OutlineUtils.setColor(Color.RED);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                        }
                    }
                    else {
                        this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                    }
                }
                if (ModuleESP.animals.getValue()) {
                    if (entity instanceof EntityAnimal || entity instanceof EntityIronGolem || entity instanceof EntityGolem || entity instanceof EntitySquid || entity instanceof EntityPigZombie) {
                        if (entity != Minecraft.getMinecraft().player) {
                            OutlineUtils.setColor(Color.GREEN);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderOne(lineWidth);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderTwo();
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderThree();
                            OutlineUtils.renderFour();
                            OutlineUtils.setColor(Color.GREEN);
                            this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                            OutlineUtils.renderFive();
                            OutlineUtils.setColor(Color.WHITE);
                        }
                    }
                    else {
                        this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                    }
                }
            }
            else {
                this.renderModel(entity, f9, f8, f6, f3, f5, f7);
            }
            if (this.renderOutlines) {
                final boolean flag1 = this.setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(ModuleESP.color.getRGB());
                if (!this.renderMarker) {
                    this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                }
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                    this.renderLayers(entity, f9, f8, partialTicks, f6, f3, f5, f7);
                }
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
                if (flag1) {
                    this.unsetScoreTeamColor();
                }
            }
            else {
                final boolean flag2 = this.setDoRenderBrightness(entity, partialTicks);
                this.renderModel(entity, f9, f8, f6, f3, f5, f7);
                if (flag2) {
                    this.unsetBrightness();
                }
                GlStateManager.depthMask(true);
                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
                    this.renderLayers(entity, f9, f8, partialTicks, f6, f3, f5, f7);
                }
            }
            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception) {
            MixinRenderLivingBase.LOGGER.error("Couldn't render entity", (Throwable)exception);
        }
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GL11.glDisable(2848);
        GlStateManager.popMatrix();
        super.doRender((T) entity, x, y, z, entityYaw, partialTicks);
        MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)entity, (RenderLivingBase)RenderLivingBase.class.cast(this), partialTicks, x, y, z));
    }

    static {
        LOGGER = LogManager.getLogger();
        TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);
        MixinRenderLivingBase.NAME_TAG_RANGE = 64.0f;
        MixinRenderLivingBase.NAME_TAG_RANGE_SNEAK = 32.0f;
    }
}
