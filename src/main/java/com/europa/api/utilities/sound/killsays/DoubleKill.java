/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.sound.killsays;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class DoubleKill {
    public static ISound sound;
    public static String song;
    public static ResourceLocation loc;

    public static ResourceLocation access$000() {
        return loc;
    }

    static {
        song = "double_kill";
        loc = new ResourceLocation("europa/sounds/double_kill.ogg");
        sound = new ISound(){
            public Minecraft mc = Minecraft.getMinecraft();
            public int pitch = 1;
            public int volume = 1000000;

            public ResourceLocation getSoundLocation() {
                return DoubleKill.access$000();
            }

            @Nullable
            public SoundEventAccessor createAccessor(SoundHandler soundHandler) {
                return new SoundEventAccessor(DoubleKill.access$000(), "double_kill");
            }

            public Sound getSound() {
                return new Sound("double_kill", Float.intBitsToFloat(Float.floatToIntBits(2.0945367E-6f) ^ 0x7F78ABDE), Float.intBitsToFloat(Float.floatToIntBits(22.594954f) ^ 0x7E34C277), 1, Sound.Type.SOUND_EVENT, false);
            }

            public SoundCategory getCategory() {
                return SoundCategory.VOICE;
            }

            public boolean canRepeat() {
                return true;
            }

            public int getRepeatDelay() {
                return 2;
            }

            public float getVolume() {
                return Float.intBitsToFloat(Float.floatToIntBits(3.5557036E-6f) ^ 0x7F1ABA86);
            }

            public float getPitch() {
                return Float.intBitsToFloat(Float.floatToIntBits(39.82736f) ^ 0x7D9F4F37);
            }

            public float getXPosF() {
                return this.mc.player != null ? (float)this.mc.player.posX : Float.intBitsToFloat(Float.floatToIntBits(3.0058639E38f) ^ 0x7F6222D5);
            }

            public float getYPosF() {
                return this.mc.player != null ? (float)this.mc.player.posY : Float.intBitsToFloat(Float.floatToIntBits(1.0765174E38f) ^ 0x7EA1F9F1);
            }

            public float getZPosF() {
                return this.mc.player != null ? (float)this.mc.player.posZ : Float.intBitsToFloat(Float.floatToIntBits(1.0588689E38f) ^ 0x7E9F5225);
            }

            public ISound.AttenuationType getAttenuationType() {
                return ISound.AttenuationType.LINEAR;
            }
        };
    }
}

