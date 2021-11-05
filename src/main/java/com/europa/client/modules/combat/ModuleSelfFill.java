/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.combat;

import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.world.BlockUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class ModuleSelfFill
extends Module {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "The mode for the filling.", Modes.Burrow);
    public static ValueEnum item = new ValueEnum("Item", "Item", "The item for the block placing.", InventoryUtils.Items.Obsidian);
    public static ValueEnum switchMode = new ValueEnum("Switch", "Switch", "The mode for switching to the target block.", InventoryUtils.SwitchModes.Normal);
    public static ValueBoolean rotate = new ValueBoolean("Rotate", "Rotate", "Rotates to place the block.", false);

    public ModuleSelfFill() {
        super("SelfFill", "Self Fill", "Places a block at your feet and either rubberbands you into it or jumps on it.", ModuleCategory.COMBAT);
    }

    @Override
    public void onEnable() {
        block1: {
            block0: {
                super.onEnable();
                if (ModuleSelfFill.mc.player == null) break block0;
                if (ModuleSelfFill.mc.world != null) break block1;
            }
            this.disable();
        }
    }

    @Override
    public void onMotionUpdate() {
        super.onMotionUpdate();
        BlockPos lastPos = new BlockPos(ModuleSelfFill.mc.player.posX, Math.ceil(ModuleSelfFill.mc.player.posY), ModuleSelfFill.mc.player.posZ);
        int slot = InventoryUtils.getCombatBlock(item.getValue().toString());
        int lastSlot = ModuleSelfFill.mc.player.inventory.currentItem;
        if (slot == -1) {
            ChatManager.sendClientMessage("No blocks could be found.", 256);
            this.disable();
            return;
        }
        InventoryUtils.switchSlot(slot, switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
        ModuleSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleSelfFill.mc.player.posX, ModuleSelfFill.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(242.8422863277949) ^ 0x7FB48A1162357EDFL), ModuleSelfFill.mc.player.posZ, true));
        ModuleSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleSelfFill.mc.player.posX, ModuleSelfFill.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(25.296825724702348) ^ 0x7FD151CA1D626197L), ModuleSelfFill.mc.player.posZ, true));
        ModuleSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleSelfFill.mc.player.posX, ModuleSelfFill.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(5.0945393701201835) ^ 0x7FE460407E90277DL), ModuleSelfFill.mc.player.posZ, true));
        ModuleSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleSelfFill.mc.player.posX, ModuleSelfFill.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(24.104125548382463) ^ 0x7FCAB2C5D624CF57L), ModuleSelfFill.mc.player.posZ, true));
        BlockUtils.placeBlock(lastPos, EnumHand.MAIN_HAND, true);
        if (mode.getValue().equals((Object)Modes.Burrow)) {
            ModuleSelfFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleSelfFill.mc.player.posX, ModuleSelfFill.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(14.157087389087282) ^ 0x7FDC506DC21D7CE1L), ModuleSelfFill.mc.player.posZ, false));
        } else {
            ModuleSelfFill.mc.player.jump();
        }
        if (!switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Strict)) {
            InventoryUtils.switchSlot(lastSlot, switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
        }
        this.disable();
    }

    public static enum Modes {
        Block,
        Burrow;

    }
}

