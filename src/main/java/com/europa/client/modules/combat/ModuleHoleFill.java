/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.combat;

import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.crystal.CrystalUtils;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.world.BlockUtils;
import com.europa.api.utilities.world.HoleUtils;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class ModuleHoleFill
extends Module {
    public int placements = 0;
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "The mode for the HoleFill.", Modes.Normal);
    public static ValueEnum item = new ValueEnum("Item", "Item", "The item for block placing.", InventoryUtils.Items.Obsidian);
    public static ValueEnum switchMode = new ValueEnum("Switch", "Switch", "The mode for switching.", InventoryUtils.SwitchModes.Normal);
    public ValueNumber blocks = new ValueNumber("Blocks", "Blocks", "The amount of blocks that can be placed per tick.", 8, 1, 40);
    public static ValueNumber range = new ValueNumber("Range", "Range", "The maximum range that the block can be away.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.3448474f) ^ 0x7E308FD7)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(9.149796E37f) ^ 0x7E89ABBB)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.1356838f) ^ 0x7F0AF0B2)));
    public static ValueNumber targetRange = new ValueNumber("TargetRange", "TargetRange", "The range for the targeting.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.454627f) ^ 0x7F7A3138)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(3.0595859E38f) ^ 0x7F662D7B)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.51753014f) ^ 0x7E047CDB)));
    public static ValueBoolean doubles = new ValueBoolean("Doubles", "Doubles", "Fills in double holes too.", true);
    public static ValueBoolean targetDisable = new ValueBoolean("TargetDisable", "TargetDisable", "Automatically disables when there is no target.", true);
    public static ValueBoolean selfDisable = new ValueBoolean("SelfDisable", "SelfDisable", "Automatically disables when there are no more holes.", true);

    public ModuleHoleFill() {
        super("HoleFill", "Hole Fill", "Automatically fills holes with selected blocks.", ModuleCategory.COMBAT);
    }

    @Override
    public void onMotionUpdate() {
        block7: {
            super.onMotionUpdate();
            EntityPlayer target = CrystalUtils.getTarget(targetRange.getValue().floatValue());
            if (mode.getValue().equals((Object)Modes.Smart)) {
                if (target == null) {
                    if (targetDisable.getValue()) {
                        this.disable();
                    }
                    return;
                }
            }
            this.placements = 0;
            int slot = InventoryUtils.getCombatBlock(item.getValue().toString());
            int lastSlot = ModuleHoleFill.mc.player.inventory.currentItem;
            if (slot == -1) {
                ChatManager.sendClientMessage("No blocks could be found.", 256);
                this.disable();
                return;
            }
            ArrayList<BlockPos> holes = this.getHoles();
            if (!holes.isEmpty()) {
                InventoryUtils.switchSlot(slot, switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
                for (BlockPos position : holes) {
                    this.placeBlock(position);
                }
                if (!switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Strict)) {
                    InventoryUtils.switchSlot(lastSlot, switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
                }
            }
            if (!selfDisable.getValue() || !holes.isEmpty()) break block7;
            this.disable();
        }
    }

    public ArrayList<BlockPos> getHoles() {
        ArrayList<BlockPos> holes = new ArrayList<BlockPos>();
        for (BlockPos position : CrystalUtils.getSphere(range.getValue().floatValue(), true, false)) {
            if (HoleUtils.isHole(position)) {
                if (!BlockUtils.isPositionPlaceable(position, true, true, false)) continue;
                holes.add(position);
                continue;
            }
            if (!HoleUtils.isDoubleHole(position) || !doubles.getValue() || ModuleHoleFill.mc.world.getBlockState(position).getBlock() != Blocks.AIR || ModuleHoleFill.mc.world.getBlockState(position.up()).getBlock() != Blocks.AIR || ModuleHoleFill.mc.world.getBlockState(position.up().up()).getBlock() != Blocks.AIR || !BlockUtils.isPositionPlaceable(position, true, true, false)) continue;
            holes.add(position);
        }
        return holes;
    }

    public void placeBlock(final BlockPos position) {
        if (BlockUtils.isPositionPlaceable(position, true, true, false) && this.placements < this.blocks.getValue().intValue()) {
            BlockUtils.placeBlock(position, EnumHand.MAIN_HAND, true);
            ++this.placements;
        }
    }

    public static enum Modes {
        Normal,
        Smart;

    }
}

