/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.client.modules.player.ModuleNoEntityTrace;
import com.europa.client.modules.render.ModuleNoRender;
import com.europa.client.modules.render.ModuleViewClip;
import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityRenderer.class})
public class MixinEntityRenderer {
    @Shadow
    public ItemStack itemActivationItem;

    @Inject(method={"renderItemActivation"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderItemActivationHook(CallbackInfo info) {
        if (this.itemActivationItem != null && Europa.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.totemPop.getValue() && this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            info.cancel();
        }
    }

    @Redirect(method={"getMouseOver"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
        if (ModuleNoEntityTrace.INSTANCE.noTrace()) {
            return new ArrayList<Entity>();
        }
        return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }

    @Inject(method={"hurtCameraEffect"}, at={@At(value="HEAD")}, cancellable=true)
    public void hurtCameraEffectHook(float ticks, CallbackInfo info) {
        if (Europa.getModuleManager().isModuleEnabled("NoRender") && ModuleNoRender.hurtCam.getValue()) {
            info.cancel();
        }
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=3, at=@At(value="STORE", ordinal=0), require=1)
    public double changeCameraDistanceHook(double range) {
        return Europa.getModuleManager().isModuleEnabled("ViewClip") && ModuleViewClip.extend.getValue() ? ModuleViewClip.distance.getValue().doubleValue() : range;
    }

    @ModifyVariable(method={"orientCamera"}, ordinal=7, at=@At(value="STORE", ordinal=0), require=1)
    public double orientCameraHook(double range) {
        return Europa.getModuleManager().isModuleEnabled("ViewClip") && ModuleViewClip.extend.getValue() ? Double.valueOf(ModuleViewClip.distance.getValue().doubleValue()) : (Europa.getModuleManager().isModuleEnabled("ViewClip") && !ModuleViewClip.extend.getValue() ? 4.0 : range);
    }
}

