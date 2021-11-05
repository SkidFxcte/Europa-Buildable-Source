/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import java.util.Objects;
import net.minecraft.potion.Potion;

public class ModuleAntiLevitation
extends Module {
    public ModuleAntiLevitation() {
        super("AntiLevitation", "Anti Levitation", "Prevents the effect of Levitation from affecting the player.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        if (ModuleAntiLevitation.mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation((String)"levitation")))) {
            ModuleAntiLevitation.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation((String)"levitation"));
        }
    }
}

