/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.utilities.sound.killsays.DoubleKill;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;

public class ModuleTest
extends Module {
    public ISound sound = DoubleKill.sound;

    public ModuleTest() {
        super("Test", "Test", "", ModuleCategory.MISC);
    }

    @Override
    public void onEnable() {
        if (ModuleTest.mc.world == null) {
            return;
        }
        /* SoundHandler.playSound(this.sound); what ? */
    }
}

