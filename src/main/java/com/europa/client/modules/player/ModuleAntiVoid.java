/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.utilities.entity.InventoryUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;

public class ModuleAntiVoid
extends Module {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.Stop);
    public static ValueBoolean chorus = new ValueBoolean("Chorus", "Chorus", "", false);
    public boolean atechorus = false;

    public ModuleAntiVoid() {
        super("AntiVoid", "Anti Void", "Prevents the player from falling into the void.", ModuleCategory.PLAYER);
    }

    @Override
    public void onUpdate() {
        if (ModuleAntiVoid.mc.world == null || ModuleAntiVoid.mc.player == null) {
            return;
        }
        if (ModuleAntiVoid.mc.player.posY <= Double.longBitsToDouble(Double.doubleToLongBits(2.1474597617414252E307) ^ 0x7FBE94B2115D7E97L)) {
            ChatManager.printChatNotifyClient("Gettin you out");
            if (chorus.getValue()) {
                InventoryUtils.switchToSlot(Items.CHORUS_FRUIT);
                KeyBinding.setKeyBindState((int)ModuleAntiVoid.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
                this.atechorus = true;
            }
            switch (mode.getValue().ordinal()) {
                case 1: {
                    ModuleAntiVoid.mc.player.motionY = Double.longBitsToDouble(Double.doubleToLongBits(1.4268196432356022E308) ^ 0x7FE965F294392443L);
                    break;
                }
                case 2: {
                    ModuleAntiVoid.mc.player.motionY = Double.longBitsToDouble(Double.doubleToLongBits(-2.535331913578867) ^ 0x7FE4485C192A9C54L);
                    break;
                }
                case 3: {
                    ModuleAntiVoid.mc.player.motionY = Double.longBitsToDouble(Double.doubleToLongBits(2.70055757194542) ^ 0x7FE59ABDEDA3C416L);
                }
            }
        } else if (this.atechorus) {
            KeyBinding.setKeyBindState((int)ModuleAntiVoid.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            this.atechorus = false;
        }
    }

    public static enum modes {
        Bounce,
        Glide,
        Stop;

    }
}

