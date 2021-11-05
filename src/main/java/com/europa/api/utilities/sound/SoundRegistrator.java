//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRegistrator
{
    public static SoundEvent DOUBLE_KILL;

    public static SoundEvent addSoundsToRegistry(final String soundId) {
        final ResourceLocation shotSoundLocation = new ResourceLocation(soundId);
        final SoundEvent soundEvent = new SoundEvent(shotSoundLocation);
        soundEvent.setRegistryName(shotSoundLocation);
        return soundEvent;
    }

    static {
        SoundRegistrator.DOUBLE_KILL = addSoundsToRegistry("double_kill");
    }
}
