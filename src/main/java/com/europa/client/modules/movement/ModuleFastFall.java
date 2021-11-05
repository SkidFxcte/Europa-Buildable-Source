/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueNumber;

public class ModuleFastFall
extends Module {
    public static ValueNumber speed = new ValueNumber("Speed", "Speed", "speed", Double.longBitsToDouble(Double.doubleToLongBits(26.612171291158898) ^ 0x7FCA9CB741FB13DBL), Double.longBitsToDouble(Double.doubleToLongBits(114.75805920442717) ^ 0x7FE5291D93594509L), Double.longBitsToDouble(Double.doubleToLongBits(1.0093241234297021) ^ 0x7FE426310D531C6BL));

    public ModuleFastFall() {
        super("FastFall", "Fast Fall", "falls fast", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        block5: {
            block4: {
                if (ModuleFastFall.mc.world == null || ModuleFastFall.mc.player == null || ModuleFastFall.mc.player.isInWater() || ModuleFastFall.mc.player.isInLava()) break block4;
                if (!ModuleFastFall.mc.player.isOnLadder()) break block5;
            }
            return;
        }
        if (ModuleFastFall.mc.player.onGround) {
            ModuleFastFall.mc.player.motionY -= (double)speed.getValue().floatValue();
        }
    }
}

