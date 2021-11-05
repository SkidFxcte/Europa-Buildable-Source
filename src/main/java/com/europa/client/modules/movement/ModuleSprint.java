/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.utilities.entity.EntityUtils;
import com.europa.api.utilities.math.MathUtils;

public class ModuleSprint
extends Module {
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "Mode", modes.Rage);

    public ModuleSprint() {
        super("Sprint", "Sprint", "Automatically sprints for you.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals((Object)modes.Legit)) {
            if (ModuleSprint.mc.player.moveForward > Float.intBitsToFloat(Float.floatToIntBits(2.7336784E38f) ^ 0x7F4DA8BB) && !ModuleSprint.mc.player.collidedHorizontally) {
                ModuleSprint.mc.player.setSprinting(true);
            }
        } else if (EntityUtils.isMoving()) {
            ModuleSprint.mc.player.setSprinting(true);
            if (ModuleSprint.mc.gameSettings.keyBindLeft.isKeyDown() || ModuleSprint.mc.gameSettings.keyBindRight.isKeyDown() && !ModuleSprint.mc.gameSettings.keyBindForward.isKeyDown() && !ModuleSprint.mc.gameSettings.keyBindBack.isKeyDown()) {
                double[] dir = MathUtils.directionSpeed(Double.longBitsToDouble(Double.doubleToLongBits(12.936028215725923) ^ 0x7FEED50267BE8D2CL));
                ModuleSprint.mc.player.motionX = dir[0];
                ModuleSprint.mc.player.motionZ = dir[1];
            }
        }
    }

    public static enum modes {
        Rage,
        Legit;

    }
}

