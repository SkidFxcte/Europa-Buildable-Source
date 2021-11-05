/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleAutoFish
extends Module {
    public int delay = 0;

    public ModuleAutoFish() {
        super("AutoFish", "Auto Fish", "Automatically fishes for you.", ModuleCategory.MISC);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        block0: {
            SPacketSoundEffect packet;
            if (!(receive.getPacket() instanceof SPacketSoundEffect) || !(packet = (SPacketSoundEffect)receive.getPacket()).getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH)) break block0;
            new Thread(this){
                public ModuleAutoFish this$0;
                {
                    this.this$0 = this$0;
                }

                @Override
                public void run() {
                    Minecraft minecraft = ModuleAutoFish.access$000();
                    minecraft.rightClickMouse();
                    long l = (long)536403029 ^ 0x1FF8DD79L;
                    try {
                        Thread.sleep(l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Minecraft minecraft2 = ModuleAutoFish.access$100();
                    try {
                        minecraft2.rightClickMouse();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }.start();
        }
    }

    public static Minecraft access$000() {
        return mc;
    }

    public static Minecraft access$100() {
        return mc;
    }
}

