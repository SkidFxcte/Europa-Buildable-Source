/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueEnum;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ModuleFullBright
extends Module {
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.NightVision);

    public ModuleFullBright() {
        super("FullBright", "Full Bright", "Makes it so that the world is always at maximum brightness.", ModuleCategory.RENDER);
    }

    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals((Object)modes.Gamma)) {
            ModuleFullBright.mc.gameSettings.gammaSetting = Float.intBitsToFloat(Float.floatToIntBits(0.010559204f) ^ 0x7EE50083);
            ModuleFullBright.mc.player.removeActivePotionEffect(Potion.getPotionById((int)16));
        } else {
            ModuleFullBright.mc.gameSettings.gammaSetting = Float.intBitsToFloat(Float.floatToIntBits(8.517325E37f) ^ 0x7E80278B);
            ModuleFullBright.mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById((int)16), 1000, 1));
        }
    }

    @Override
    public void onDisable() {
        ModuleFullBright.mc.gameSettings.gammaSetting = Float.intBitsToFloat(Float.floatToIntBits(2.9946769E38f) ^ 0x7F614B61);
    }

    public static enum modes {
        Gamma,
        NightVision;

    }
}

