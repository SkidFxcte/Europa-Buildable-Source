/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.render.EventRenderPutColorMultiplier;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BufferBuilder.class})
public abstract class MixinBufferBuilder {
    @Shadow
    private boolean noColor;
    @Shadow
    private IntBuffer rawIntBuffer;

    @Shadow
    public abstract int getColorIndex(int var1);

    @Inject(method={"putColorMultiplier"}, at={@At(value="HEAD")}, cancellable=true)
    public void putColorMultiplier(float red, float green, float blue, int vertexIndex, CallbackInfo info) {
        EventRenderPutColorMultiplier event = new EventRenderPutColorMultiplier();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
            int i = this.getColorIndex(vertexIndex);
            int j = -1;
            float newAlpha = event.getOpacity();
            if (!this.noColor) {
                j = this.rawIntBuffer.get(i);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    int k = (int)((float)(j & 0xFF) * red);
                    int l = (int)((float)(j >> 8 & 0xFF) * green);
                    int i1 = (int)((float)(j >> 16 & 0xFF) * blue);
                    int alpha = (int)((float)(j >> 24 & 0xFF) * newAlpha);
                    j = alpha << 24 | i1 << 16 | l << 8 | k;
                } else {
                    int j1 = (int)((float)(j >> 24 & 0xFF) * red);
                    int k1 = (int)((float)(j >> 16 & 0xFF) * green);
                    int l1 = (int)((float)(j >> 8 & 0xFF) * blue);
                    int alpha = (int)((float)(j & 0xFF) * newAlpha);
                    j = j1 << 24 | k1 << 16 | l1 << 8 | alpha;
                }
            }
            this.rawIntBuffer.put(i, j);
        }
    }
}

