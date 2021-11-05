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
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.math.MathUtils;
import com.europa.api.utilities.world.BlockUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;

public class ModuleSurround
extends Module {
    public int placements;
    public BlockPos startPosition;
    public int tries;
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "The mode for the Surround.", Modes.Normal);
    public ValueEnum item = new ValueEnum("Item", "Item", "The item for block placing.", InventoryUtils.Items.Obsidian);
    public ValueEnum switchMode = new ValueEnum("Switch", "Switch", "The mode for switching.", InventoryUtils.SwitchModes.Normal);
    public ValueNumber blocks = new ValueNumber("Blocks", "Blocks", "The amount of blocks that can be placed per tick.", 8, 1, 40);
    public ValueEnum supportBlocks = new ValueEnum("SupportBlocks", "SupportBlocks", "The support blocks for placing.", Supports.Dynamic);
    public ValueNumber retries = new ValueNumber("Retries", "Retries", "The amount of retries that can happen before stopping the crystal ignore.", 5, 0, 20);
    public ValueBoolean dynamic = new ValueBoolean("Dynamic", "Dynamic", "Makes the surround place dynamically.", true);
    public ValueBoolean center = new ValueBoolean("Center", "Center", "Positions the player to the center.", false);
    public ValueBoolean rotate = new ValueBoolean("Rotate", "Rotate", "Rotates to the block when placing.", false);
    public ValueBoolean floor = new ValueBoolean("Floor", "Floor", "Places blocks at the floor.", false);

    public ModuleSurround() {
        super("Surround", "Surround", "Places blocks around your feet to protect you from crystals.", ModuleCategory.COMBAT);
    }

    @Override
    public void onMotionUpdate() {
        block8: {
            if ((double)this.startPosition.getY() != MathUtils.roundToPlaces(ModuleSurround.mc.player.posY, 0) && this.mode.getValue().equals((Object)Modes.Normal)) {
                this.disable();
                return;
            }
            int slot = InventoryUtils.getCombatBlock(this.item.getValue().toString());
            int lastSlot = ModuleSurround.mc.player.inventory.currentItem;
            if (slot == -1) {
                ChatManager.sendClientMessage("No blocks could be found.", 256);
                this.disable();
                return;
            }
            if (!this.getUnsafeBlocks().isEmpty()) {
                InventoryUtils.switchSlot(slot, this.switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
                for (BlockPos position : this.getUnsafeBlocks()) {
                    if (!this.supportBlocks.getValue().equals((Object)Supports.None)) {
                        if ((BlockUtils.getPlaceableSide(position) == null || this.supportBlocks.getValue().equals((Object)Supports.Static)) && BlockUtils.isPositionPlaceable(position.down(), true, true)) {
                            this.placeBlock(position.down());
                        }
                    }
                    if (!BlockUtils.isPositionPlaceable(position, true, true, this.tries <= this.retries.getValue().intValue())) continue;
                    this.placeBlock(position);
                    ++this.tries;
                }
                if (!this.switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Strict)) {
                    InventoryUtils.switchSlot(lastSlot, this.switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
                }
            }
            this.placements = 0;
            if (!this.getUnsafeBlocks().isEmpty()) break block8;
            this.tries = 0;
            if (this.mode.getValue().equals((Object)Modes.Toggle)) {
                this.disable();
            }
        }
    }

    public List<BlockPos> getOffsets() {
        ArrayList<BlockPos> offsets = new ArrayList<BlockPos>();
        if (this.dynamic.getValue()) {
            int z;
            int x;
            double decimalX = Math.abs(ModuleSurround.mc.player.posX) - Math.floor(Math.abs(ModuleSurround.mc.player.posX));
            double decimalZ = Math.abs(ModuleSurround.mc.player.posZ) - Math.floor(Math.abs(ModuleSurround.mc.player.posZ));
            int lengthX = this.calculateLength(decimalX, false);
            int negativeLengthX = this.calculateLength(decimalX, true);
            int lengthZ = this.calculateLength(decimalZ, false);
            int negativeLengthZ = this.calculateLength(decimalZ, true);
            ArrayList<BlockPos> tempOffsets = new ArrayList<BlockPos>();
            offsets.addAll(this.getOverlapPositions());
            for (x = 1; x < lengthX + 1; ++x) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), x, 1 + lengthZ));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), x, -(1 + negativeLengthZ)));
            }
            for (x = 0; x <= negativeLengthX; ++x) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -x, 1 + lengthZ));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -x, -(1 + negativeLengthZ)));
            }
            for (z = 1; z < lengthZ + 1; ++z) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), 1 + lengthX, z));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -(1 + negativeLengthX), z));
            }
            for (z = 0; z <= negativeLengthZ; ++z) {
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), 1 + lengthX, -z));
                tempOffsets.add(this.addToPosition(this.getPlayerPosition(), -(1 + negativeLengthX), -z));
            }
            offsets.addAll(tempOffsets);
        } else {
            for (EnumFacing side : EnumFacing.HORIZONTALS) {
            }
        }
        return offsets;
    }

    public BlockPos getPlayerPosition() {
        return new BlockPos(ModuleSurround.mc.player.posX, ModuleSurround.mc.player.posY - Math.floor(ModuleSurround.mc.player.posY) > Double.longBitsToDouble(Double.doubleToLongBits(19.39343307331816) ^ 0x7FDAFD219E3E896DL) ? Math.floor(ModuleSurround.mc.player.posY) + Double.longBitsToDouble(Double.doubleToLongBits(4.907271931218261) ^ 0x7FE3A10BE4A4A510L) : Math.floor(ModuleSurround.mc.player.posY), ModuleSurround.mc.player.posZ);
    }

    public List<BlockPos> getOverlapPositions() {
        ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        int offsetX = this.calculateOffset(ModuleSurround.mc.player.posX - Math.floor(ModuleSurround.mc.player.posX));
        int offsetZ = this.calculateOffset(ModuleSurround.mc.player.posZ - Math.floor(ModuleSurround.mc.player.posZ));
        positions.add(this.getPlayerPosition());
        for (int x = 0; x <= Math.abs(offsetX); ++x) {
            for (int z = 0; z <= Math.abs(offsetZ); ++z) {
                int properX = x * offsetX;
                int properZ = z * offsetZ;
                positions.add(this.getPlayerPosition().add(properX, -1, properZ));
            }
        }
        return positions;
    }

    public BlockPos addToPosition(final BlockPos position, double x, double z) {
        if (position.getX() < 0) {
            x = -x;
        }
        if (position.getZ() < 0) {
            z = -z;
        }
        return position.add(x, Double.longBitsToDouble(Double.doubleToLongBits(1.4868164896774578E308) ^ 0x7FEA7759ABE7F7C1L), z);
    }


    /*
     * WARNING - void declaration
     */
    public int calculateOffset(double d) {
        double dec = 0;
        return dec >= Double.longBitsToDouble(Double.doubleToLongBits(22.19607388697261) ^ 0x7FD05457839243F9L) ? 1 : (dec <= Double.longBitsToDouble(Double.doubleToLongBits(7.035587642812949) ^ 0x7FCF1742257B24DBL) ? -1 : 0);
    }

    /*
     * WARNING - void declaration
     */
    public int calculateLength(double d, boolean bl) {
        double decimal = 0;
        double negative = 0;
        if (negative != 1) {
            return decimal <= Double.longBitsToDouble(Double.doubleToLongBits(30.561776836994962) ^ 0x7FEDBCE3A865B81CL) ? 1 : 0;
        }
        return decimal >= Double.longBitsToDouble(Double.doubleToLongBits(22.350511399288944) ^ 0x7FD03FDD7B12B45DL) ? 1 : 0;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (ModuleSurround.mc.player == null || ModuleSurround.mc.world == null) {
            this.disable();
            return;
        }
        this.startPosition = new BlockPos(MathUtils.roundVector(ModuleSurround.mc.player.getPositionVector(), 0));
    }

    /*
     * WARNING - void declaration
     */
    public void placeBlock(BlockPos blockPos) {
        if (this.placements < this.blocks.getValue().intValue()) {
            BlockUtils.placeBlock((BlockPos) blockPos, EnumHand.MAIN_HAND, true);
            ++this.placements;
        }
    }

    public List<BlockPos> getUnsafeBlocks() {
        ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
        for (BlockPos position : this.getOffsets()) {
            if (this.isSafe(position)) continue;
            positions.add(position);
        }
        return positions;
    }

    /*
     * WARNING - void declaration
     */
    public boolean isSafe(BlockPos blockPos) {
        return !ModuleSurround.mc.world.getBlockState((BlockPos)blockPos).getBlock().isReplaceable((IBlockAccess)ModuleSurround.mc.world, (BlockPos)blockPos);
    }

    @Override
    public String getHudInfo() {
        return " " + this.tries + "/" + this.retries.getValue().intValue();
    }

    public static enum Supports {
        None,
        Dynamic,
        Static;

    }

    public static enum Modes {
        Normal,
        Persistent,
        Toggle,
        Shift;

    }
}

