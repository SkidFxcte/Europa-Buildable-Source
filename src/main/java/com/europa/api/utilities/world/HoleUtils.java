//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import com.europa.api.utilities.IMinecraft;

public class HoleUtils implements IMinecraft
{
    public static boolean isBedrockHole(final BlockPos pos) {
        boolean retVal = false;
        if (HoleUtils.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) && HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) && HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) && HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) && HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean isObiHole(final BlockPos pos) {
        boolean retVal = false;
        int obiCount = 0;
        if (HoleUtils.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
            if (HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                if (HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                    ++obiCount;
                }
                if (HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                    if (HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                        ++obiCount;
                    }
                    if (HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                        if (HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                            ++obiCount;
                        }
                        if (HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                            if (HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                                ++obiCount;
                            }
                            if (HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                if (HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                                    ++obiCount;
                                }
                                if (obiCount >= 1) {
                                    retVal = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return retVal;
    }

    public static boolean isDoubleHole(final BlockPos pos) {
        for (final EnumFacing f : EnumFacing.HORIZONTALS) {
            final int offX = f.getFrontOffsetX();
            final int offZ = f.getFrontOffsetZ();
            Label_1038: {
                if (HoleUtils.mc.world.getBlockState(pos.add(offX, 0, offZ)).getBlock() != Blocks.OBSIDIAN) {
                    if (HoleUtils.mc.world.getBlockState(pos.add(offX, 0, offZ)).getBlock() != Blocks.BEDROCK) {
                        break Label_1038;
                    }
                }
                if (HoleUtils.mc.world.getBlockState(pos.add(offX * -2, 0, offZ * -2)).getBlock() == Blocks.OBSIDIAN || HoleUtils.mc.world.getBlockState(pos.add(offX * -2, 0, offZ * -2)).getBlock() == Blocks.BEDROCK) {
                    if (HoleUtils.mc.world.getBlockState(pos.add(offX * -1, 0, offZ * -1)).getBlock() == Blocks.AIR) {
                        if (isSafeBlock(pos.add(0, -1, 0))) {
                            if (isSafeBlock(pos.add(offX * -1, -1, offZ * -1))) {
                                if (offZ == 0) {
                                    if (isSafeBlock(pos.add(0, 0, 1))) {
                                        if (isSafeBlock(pos.add(0, 0, -1))) {
                                            if (isSafeBlock(pos.add(offX * -1, 0, 1))) {
                                                if (isSafeBlock(pos.add(offX * -1, 0, -1))) {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (offX == 0 && isSafeBlock(pos.add(1, 0, 0)) && isSafeBlock(pos.add(-1, 0, 0))) {
                                    if (isSafeBlock(pos.add(1, 0, offZ * -1))) {
                                        if (isSafeBlock(pos.add(-1, 0, offZ * -1))) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isHole(final BlockPos pos) {
        boolean retVal = false;
        if (HoleUtils.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR)) {
            if (HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                if (!HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK)) {
                    if (!HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                        return retVal;
                    }
                }
                if ((HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) && (HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) && (HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN))) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }

    public static boolean isPlayerInHole() {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(HoleUtils.mc.player.posX, HoleUtils.mc.player.posY, HoleUtils.mc.player.posZ);
        if (HoleUtils.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && (HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN))) {
            if (!HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK)) {
                if (!HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                    return retVal;
                }
            }
            if (HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                if (!HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK)) {
                    if (!HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                        return retVal;
                    }
                }
                if (HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                    retVal = true;
                }
            }
        }
        return retVal;
    }

    public static boolean isEntityInHole(final Entity entity) {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
        if (HoleUtils.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && (HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) && (HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) && (HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) && (HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) && (HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN))) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean isPlayerTrapped() {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(HoleUtils.mc.player.posX, HoleUtils.mc.player.posY + Double.longBitsToDouble(Double.doubleToLongBits(12.367639570239492) ^ 0x7FD8BC3B40F5C9C9L), HoleUtils.mc.player.posZ);
        if (!HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean isEntityTrapped(final Entity entity) {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(entity.posX, entity.posY + Double.longBitsToDouble(Double.doubleToLongBits(8.424052216242812) ^ 0x7FD0D91D5F411E4FL), entity.posZ);
        if (!HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean isInHole(final EntityPlayer player) {
        boolean retVal = false;
        final BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
        if (HoleUtils.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR)) {
            if (!HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.BEDROCK)) {
                if (!HoleUtils.mc.world.getBlockState(pos.down()).getBlock().equals(Blocks.OBSIDIAN)) {
                    return retVal;
                }
            }
            if (HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN)) {
                if (HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.west()).getBlock().equals(Blocks.OBSIDIAN)) {
                    if (!HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.BEDROCK)) {
                        if (!HoleUtils.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN)) {
                            return retVal;
                        }
                    }
                    if (HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.BEDROCK) || HoleUtils.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN)) {
                        retVal = true;
                    }
                }
            }
        }
        return retVal;
    }

    public static boolean isSafeBlock(final BlockPos pos) {
        if (HoleUtils.mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN) {
            if (HoleUtils.mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }

    public static Vec3d centerPos(final double posX, final double posY, final double posZ) {
        return new Vec3d(Math.floor(posX) + Double.longBitsToDouble(Double.doubleToLongBits(2.483631649651979) ^ 0x7FE3DE7A453486B7L), Math.floor(posY), Math.floor(posZ) + Double.longBitsToDouble(Double.doubleToLongBits(3.847580551695734) ^ 0x7FEEC7D84FF2120EL));
    }

    public static boolean isDoubleBedrockHoleX(final BlockPos blockPos) {
        if (!HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !HoleUtils.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) || (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !HoleUtils.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (final BlockPos blockPos2 : new BlockPos[] { blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0) }) {
            final IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleZ(final BlockPos blockPos) {
        if (HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR)) {
            if (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
            }
            if (HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
                for (final BlockPos blockPos2 : new BlockPos[] { blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1) }) {
                    final IBlockState iBlockState = HoleUtils.mc.world.getBlockState(blockPos2);
                    if (iBlockState.getBlock() == Blocks.AIR || iBlockState.getBlock() != Blocks.BEDROCK) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isDoubleObsidianHoleX(final BlockPos blockPos) {
        if (HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR)) {
            if (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                if (!HoleUtils.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
            }
            if (HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || HoleUtils.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
                for (final BlockPos blockPos2 : new BlockPos[] { blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0) }) {
                    if (BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Resistant && BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Unbreakable) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isDoubleObsidianHoleZ(final BlockPos blockPos) {
        if (HoleUtils.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && HoleUtils.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) && (HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || HoleUtils.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR))) {
            if (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                if (!HoleUtils.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
                    return false;
                }
            }
            for (final BlockPos blockPos2 : new BlockPos[] { blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1) }) {
                if (BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Resistant) {
                    if (BlockUtils.getBlockResistance(blockPos2) != BlockUtils.BlockResistance.Unbreakable) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
