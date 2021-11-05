/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueNumber;

public class ModuleTimer
extends Module {
    public static ValueNumber timer = new ValueNumber("Timer", "timer", "timer", Double.longBitsToDouble(Double.doubleToLongBits(0.0521842415135843) ^ 0x7FBAB7E49555B6C7L), Double.longBitsToDouble(Double.doubleToLongBits(24.559715707147593) ^ 0x7F8116D01EC8D83FL), Double.longBitsToDouble(Double.doubleToLongBits(0.1300621593587471) ^ 0x7FE4A5E078724C7CL));

    public ModuleTimer() {
        super("Timer", "Timer", "Let's you change how fast the Minecraft timer is going.", ModuleCategory.MISC);
    }

    @Override
    public void onEnable() {
        ModuleTimer.mc.timer.tickLength = Float.intBitsToFloat(Float.floatToIntBits(0.04392911f) ^ 0x7F7BEF03) / timer.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        ModuleTimer.mc.timer.tickLength = Float.intBitsToFloat(Float.floatToIntBits(0.015568974f) ^ 0x7E371503);
    }
}

