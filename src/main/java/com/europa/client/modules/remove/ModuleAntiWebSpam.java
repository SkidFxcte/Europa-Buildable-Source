/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.utilities.entity.InventoryUtils;
import java.util.ArrayList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;

public class ModuleAntiWebSpam
extends Module {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.WebNear);
    public static ArrayList<BlockPos> blocks = new ArrayList();
    public boolean ateChorus = false;

    public ModuleAntiWebSpam() {
        super("AntiWebSpam", "Anti Web Spam", "Automatically eats chorus when in a lot of webs.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX, ModuleAntiWebSpam.mc.player.posY, ModuleAntiWebSpam.mc.player.posZ));
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX + Double.longBitsToDouble(Double.doubleToLongBits(7.168147308960913) ^ 0x7FECAC2ECEE39712L), ModuleAntiWebSpam.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(4.6374955140078296) ^ 0x7FE28CCB9FC00A96L), ModuleAntiWebSpam.mc.player.posZ + Double.longBitsToDouble(Double.doubleToLongBits(15.228092690665859) ^ 0x7FDE74C890ADBBA7L)));
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX + Double.longBitsToDouble(Double.doubleToLongBits(0.9058104394653387) ^ 0x7FECFC662CBC20E9L), ModuleAntiWebSpam.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(0.5542992727263832) ^ 0x7FE1BCD1D411CE09L), ModuleAntiWebSpam.mc.player.posZ + Double.longBitsToDouble(Double.doubleToLongBits(0.9710440662346002) ^ 0x7FEF12CB016E7A99L)));
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX + Double.longBitsToDouble(Double.doubleToLongBits(0.328566872617643) ^ 0x7FDD073D591C4609L), ModuleAntiWebSpam.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(0.5604798552220878) ^ 0x7FE9EF737307DCA0L), ModuleAntiWebSpam.mc.player.posZ + Double.longBitsToDouble(Double.doubleToLongBits(0.10253457188005487) ^ 0x7FB23FB4A8EF2787L)));
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX - Double.longBitsToDouble(Double.doubleToLongBits(28.23279080673421) ^ 0x7FCC3B982DA5BB9FL), ModuleAntiWebSpam.mc.player.posY - Double.longBitsToDouble(Double.doubleToLongBits(4.555898618432704) ^ 0x7FE2393D7CC83D7DL), ModuleAntiWebSpam.mc.player.posZ - Double.longBitsToDouble(Double.doubleToLongBits(14.859794270949648) ^ 0x7FDDB836F46608A7L)));
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX - Double.longBitsToDouble(Double.doubleToLongBits(0.8348272479432102) ^ 0x7FEAB6E7A1F73984L), ModuleAntiWebSpam.mc.player.posY - Double.longBitsToDouble(Double.doubleToLongBits(0.1853614912148329) ^ 0x7FC7B9ECE35A4DC7L), ModuleAntiWebSpam.mc.player.posZ - Double.longBitsToDouble(Double.doubleToLongBits(0.9111122447841747) ^ 0x7FED27D4DDCAA963L)));
        blocks.add(new BlockPos(ModuleAntiWebSpam.mc.player.posX - Double.longBitsToDouble(Double.doubleToLongBits(0.8939487638004415) ^ 0x7FE49B3A701A5220L), ModuleAntiWebSpam.mc.player.posY - Double.longBitsToDouble(Double.doubleToLongBits(0.895885631232032) ^ 0x7FE4AB1857E321F2L), ModuleAntiWebSpam.mc.player.posZ - Double.longBitsToDouble(Double.doubleToLongBits(0.5062933376290092) ^ 0x7FE8338E15E993B5L)));
        if (mode.getValue().equals("All")) {
            if (ModuleAntiWebSpam.mc.player.isInWeb) {
                InventoryUtils.switchToSlot(Items.CHORUS_FRUIT);
                KeyBinding.setKeyBindState((int)ModuleAntiWebSpam.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
                this.ateChorus = true;
            }
        } else if (mode.getValue().equals("WebNear")) {
            if (ModuleAntiWebSpam.isSpam()) {
                InventoryUtils.switchToSlot(Items.CHORUS_FRUIT);
                KeyBinding.setKeyBindState((int)ModuleAntiWebSpam.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)true);
                this.ateChorus = true;
            }
        }
        if (this.ateChorus) {
            InventoryUtils.switchToSlot(Items.CHORUS_FRUIT);
            KeyBinding.setKeyBindState((int)ModuleAntiWebSpam.mc.gameSettings.keyBindUseItem.getKeyCode(), (boolean)false);
            this.ateChorus = false;
        }
    }

    public static boolean isSpam() {
        return blocks.equals(Blocks.WEB);
    }

    public static enum modes {
        All,
        WebNear;

    }
}

