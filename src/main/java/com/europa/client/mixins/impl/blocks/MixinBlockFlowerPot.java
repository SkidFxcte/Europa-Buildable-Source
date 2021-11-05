/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl.blocks;

import com.europa.api.manager.event.impl.render.EventBlockGetRenderLayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockFlowerPot.class})
public class MixinBlockFlowerPot {
    @Inject(method={"getRenderLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void getRenderLayer(CallbackInfoReturnable<BlockRenderLayer> callback) {
        EventBlockGetRenderLayer event = new EventBlockGetRenderLayer(this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue(event.getBlockRenderLayer());
        }
    }
}

