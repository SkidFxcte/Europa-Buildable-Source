/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.api.manager.event.impl.render.EventHandSide;
import com.europa.client.modules.render.ModuleModelChanger;
import com.europa.client.modules.render.ModuleNoRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemRenderer.class})
public class MixinItemRenderer {
    @Inject(method={"renderItemSide"}, at={@At(value="HEAD")})
    public void renderItemSide(EntityLivingBase entityLivingBase, ItemStack stack, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo info) {
        if (Europa.MODULE_MANAGER != null && Europa.MODULE_MANAGER.isModuleEnabled("ModelChanger")) {
            GlStateManager.scale((float)((float)ModuleModelChanger.scaleX.getValue().intValue() / 360.0f), (float)((float)ModuleModelChanger.scaleY.getValue().intValue() / 360.0f), (float)((float)ModuleModelChanger.scaleZ.getValue().intValue() / 360.0f));
            if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
                GlStateManager.translate((float)(Minecraft.getMinecraft().player.isHandActive() && ModuleModelChanger.activeHand.getValue() ? 0.0f : (float)ModuleModelChanger.translateX.getValue().intValue() / 360.0f), (float)((float)ModuleModelChanger.translateY.getValue().intValue() / 360.0f), (float)(Minecraft.getMinecraft().player.isHandActive() && ModuleModelChanger.activeHand.getValue() ? 0.0f : (float)ModuleModelChanger.translateZ.getValue().intValue() / 360.0f));
                GlStateManager.rotate((float)ModuleModelChanger.rotateX.getValue().intValue(), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.rotate((float)ModuleModelChanger.rotateY.getValue().intValue(), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)ModuleModelChanger.rotateZ.getValue().intValue(), (float)0.0f, (float)0.0f, (float)1.0f);
            } else if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
                GlStateManager.translate((float)((float)(-ModuleModelChanger.translateX.getValue().intValue()) / 360.0f), (float)((float)ModuleModelChanger.translateY.getValue().intValue() / 360.0f), (float)((float)ModuleModelChanger.translateZ.getValue().intValue() / 360.0f));
                GlStateManager.rotate((float)(-ModuleModelChanger.rotateX.getValue().intValue()), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.rotate((float)ModuleModelChanger.rotateY.getValue().intValue(), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)ModuleModelChanger.rotateZ.getValue().intValue(), (float)0.0f, (float)0.0f, (float)1.0f);
            }
        }
    }

    @Inject(method={"transformSideFirstPerson"}, at={@At(value="HEAD")})
    public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo ci) {
        EventHandSide event = new EventHandSide(hand);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }

    @Inject(method={"transformFirstPerson"}, at={@At(value="HEAD")})
    public void transformFirstPersonHead(EnumHandSide enumHandSide, float p_187453_2_, CallbackInfo callbackInfo) {
        EventHandSide eventHandSide = new EventHandSide(enumHandSide);
        MinecraftForge.EVENT_BUS.post((Event)eventHandSide);
    }

    @Inject(method={"transformFirstPerson"}, at={@At(value="TAIL")})
    public void transformFirstPersonPost(EnumHandSide hand, float p_187453_2_, CallbackInfo ci) {
        EventHandSide event = new EventHandSide(hand);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }

    @Inject(method={"renderFireInFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    public void onRenderFireInFirstPerson(CallbackInfo ci) {
        if (ModuleNoRender.fire.getValue()) {
            ci.cancel();
        }
    }

    @Inject(method={"renderSuffocationOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderSuffocationOverlay(CallbackInfo ci) {
        if (Europa.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.suffocation.getValue()) {
            ci.cancel();
        }
    }
}

