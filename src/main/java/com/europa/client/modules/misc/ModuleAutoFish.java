//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.modules.misc;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.module.Module;

public class ModuleAutoFish extends Module
{
    public int delay;

    public ModuleAutoFish() {
        super("AutoFish", "Auto Fish", "Automatically fishes for you.", ModuleCategory.MISC);
        this.delay = 0;
    }

    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            if (packet.getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH)) {
                new Thread() {
                    public ModuleAutoFish this$0;

                    @Override
                    public void run() {
                        try {
                            com.europa.client.minecraft.Minecraft.rightClickMouse();
                            Thread.sleep((long)536403029 ^ 0x1FF8DD79L);
                            com.europa.client.minecraft.Minecraft.rightClickMouse();
                        }
                        catch (Exception ex) {}
                    }
                }.start();
            }
        }
    }

    public static Minecraft access$000() {
        return ModuleAutoFish.mc;
    }

    public static Minecraft access$100() {
        return ModuleAutoFish.mc;
    }
}
