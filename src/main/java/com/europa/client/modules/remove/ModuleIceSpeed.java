/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueNumber;
import net.minecraft.init.Blocks;

public class ModuleIceSpeed
extends Module {
    public static ValueNumber speed = new ValueNumber("Speed", "Speed", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(26.747997f) ^ 0x7F19372B)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(7.3491765E37f) ^ 0x7E5D27FF)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(14.678151f) ^ 0x7EEAD9B5)));

    public ModuleIceSpeed() {
        super("IceSpeed", "Ice Speed", "", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        Blocks.ICE.slipperiness = speed.getValue().floatValue();
        Blocks.PACKED_ICE.slipperiness = speed.getValue().floatValue();
        Blocks.FROSTED_ICE.slipperiness = speed.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        Blocks.ICE.slipperiness = Float.intBitsToFloat(Float.floatToIntBits(13.466815f) ^ 0x7E2D995B);
        Blocks.PACKED_ICE.slipperiness = Float.intBitsToFloat(Float.floatToIntBits(3.5869308f) ^ 0x7F1F710E);
        Blocks.FROSTED_ICE.slipperiness = Float.intBitsToFloat(Float.floatToIntBits(29.825071f) ^ 0x7E9478F7);
    }
}

