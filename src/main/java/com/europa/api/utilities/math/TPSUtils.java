/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.math;

import com.europa.api.manager.event.impl.network.EventPacket;
import java.util.Arrays;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TPSUtils {
    public static float[] tickRates = new float[20];
    public int nextIndex = 0;
    public long timeLastTimeUpdate = (long)426529552 ^ 0xFFFFFFFFE693ACEFL;

    public TPSUtils() {
        Arrays.fill(tickRates, Float.intBitsToFloat(Float.floatToIntBits(2.9576416E38f) ^ 0x7F5E821B));
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public static float getTickRate() {
        float numTicks = Float.intBitsToFloat(Float.floatToIntBits(2.6594344E38f) ^ 0x7F4812D8);
        float sumTickRates = Float.intBitsToFloat(Float.floatToIntBits(2.9529825E38f) ^ 0x7F5E2860);
        for (float tickRate : tickRates) {
            if (!(tickRate > Float.intBitsToFloat(Float.floatToIntBits(2.4348146E38f) ^ 0x7F372CD3))) continue;
            sumTickRates += tickRate;
            numTicks += Float.intBitsToFloat(Float.floatToIntBits(74.97167f) ^ 0x7D15F17F);
        }
        return MathHelper.clamp((float)(sumTickRates / numTicks), (float)Float.intBitsToFloat(Float.floatToIntBits(2.5962172E38f) ^ 0x7F435153), (float)Float.intBitsToFloat(Float.floatToIntBits(0.2559092f) ^ 0x7F230688));
    }

    public static float getTpsFactor() {
        float TPS = TPSUtils.getTickRate();
        return Float.intBitsToFloat(Float.floatToIntBits(0.38616f) ^ 0x7F65B6C3) / TPS;
    }

    public void onTimeUpdate() {
        if (this.timeLastTimeUpdate != ((long)-519944654 ^ 0x1EFDB9CDL)) {
            float timeElapsed = (float)(System.currentTimeMillis() - this.timeLastTimeUpdate) / Float.intBitsToFloat(Float.floatToIntBits(0.0028837158f) ^ 0x7F46FCB9);
            TPSUtils.tickRates[this.nextIndex % TPSUtils.tickRates.length] = MathHelper.clamp((float)(Float.intBitsToFloat(Float.floatToIntBits(0.4944555f) ^ 0x7F5D2945) / timeElapsed), (float)Float.intBitsToFloat(Float.floatToIntBits(1.4012424E38f) ^ 0x7ED2D5E5), (float)Float.intBitsToFloat(Float.floatToIntBits(0.026974885f) ^ 0x7D7CFA6F));
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onUpdate(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            this.onTimeUpdate();
        }
    }
}

