/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.Europa;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;

public class ModuleFastSwim
extends Module {
    public int divider = 5;

    public ModuleFastSwim() {
        super("FastSwim", "Fast Swim", "Makes swimming in water and lava faster.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        if (ModuleFastSwim.mc.player == null || ModuleFastSwim.mc.world == null) {
            return;
        }
        if (Europa.getModuleManager().isModuleEnabled("Dive")) {
            int var1;
            if (ModuleFastSwim.mc.player.isInWater() && ModuleFastSwim.mc.gameSettings.keyBindSneak.isKeyDown()) {
                var1 = this.divider * -1;
                ModuleFastSwim.mc.player.motionY = Double.longBitsToDouble(Double.doubleToLongBits(0.08732661367246182) ^ 0x7FB7C290EC52B53FL) / (double)var1;
            }
            if (ModuleFastSwim.mc.player.isInLava() && ModuleFastSwim.mc.gameSettings.keyBindSneak.isKeyDown()) {
                var1 = this.divider * -1;
                ModuleFastSwim.mc.player.motionY = Double.longBitsToDouble(Double.doubleToLongBits(3.7200592088906697) ^ 0x7FE0DC1636E176CAL) / (double)var1;
            }
        }
    }
}

