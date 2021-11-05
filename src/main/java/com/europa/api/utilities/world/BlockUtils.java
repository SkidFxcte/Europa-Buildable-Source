//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.world;

import java.util.Arrays;
import net.minecraft.world.World;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.init.Blocks;
import com.europa.api.utilities.math.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import com.europa.api.utilities.IMinecraft;

public class BlockUtils implements IMinecraft
{
    public static List<Block> blackList;
    public static List<Block> shulkerList;
    public static BlockPos[] SURROUND;

    public static boolean canBeClicked(final BlockPos var0) {
        return BlockUtils.mc.world.getBlockState(var0).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(var0), false);
    }

    public static float[] getLegitRotations(final Vec3d var0) {
        final Vec3d var = new Vec3d(BlockUtils.mc.player.posX, BlockUtils.mc.player.posY + BlockUtils.mc.player.getEyeHeight(), BlockUtils.mc.player.posZ);
        final double var2 = var0.x - var.x;
        final double var3 = var0.y - var.y;
        final double var4 = var0.z - var.z;
        final double var5 = Math.sqrt(var2 * var2 + var4 * var4);
        final float var6 = (float)Math.toDegrees(Math.atan2(var4, var2)) - Float.intBitsToFloat(Float.floatToIntBits(0.0919861f) ^ 0x7F086335);
        final float var7 = (float)(-Math.toDegrees(Math.atan2(var3, var5)));
        return new float[] { BlockUtils.mc.player.rotationYaw + MathHelper.wrapDegrees(var6 - BlockUtils.mc.player.rotationYaw), BlockUtils.mc.player.rotationPitch + MathHelper.wrapDegrees(var7 - BlockUtils.mc.player.rotationPitch) };
    }

    public static void faceVectorPacketInstant(final Vec3d var0) {
        final float[] var = getLegitRotations(var0);
        BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(var[0], var[1], BlockUtils.mc.player.onGround));
    }

    public static boolean placeBlockOnHole(final BlockPos pos, final boolean swing) {
        final EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        final Vec3d hitVec = null;
        if (!BlockUtils.mc.player.isSneaking()) {
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtils.mc.player.setSneaking(true);
        }
        BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        if (swing) {
            BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockUtils.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        return true;
    }

    public static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }

    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtils.mc.world.getBlockState(neighbour) == null) {
                return facings;
            }
            if (BlockUtils.mc.world.getBlockState(neighbour).getBlock() == null) {
                return facings;
            }
            if (BlockUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BlockUtils.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }

    public static List<BlockPos> getNearbyBlocks(final EntityPlayer player, final double blockRange, final boolean motion) {
        final List<BlockPos> nearbyBlocks = new ArrayList<BlockPos>();
        final int range = (int)MathUtils.roundToPlaces(blockRange, 0);
        if (motion) {
            player.getPosition().add(new Vec3i(player.motionX, player.motionY, player.motionZ));
        }
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range - range / 2; ++y) {
                for (int z = -range; z <= range; ++z) {
                    nearbyBlocks.add(player.getPosition().add(x, y, z));
                }
            }
        }
        return nearbyBlocks;
    }

    public static void placeBlock(final BlockPos blockPos, final boolean swing, final boolean packet) {
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            if (!BlockUtils.mc.world.getBlockState(blockPos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(blockPos)) {
                if (packet) {
                    BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(blockPos.offset(enumFacing), enumFacing.getOpposite(), EnumHand.MAIN_HAND, Float.intBitsToFloat(Float.floatToIntBits(2.7271183f) ^ 0x7F2E891B), Float.intBitsToFloat(Float.floatToIntBits(3.827434f) ^ 0x7F74F4AE), Float.intBitsToFloat(Float.floatToIntBits(30.020975f) ^ 0x7EF02AF5)));
                }
                else {
                    BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, blockPos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)blockPos), EnumHand.MAIN_HAND);
                }
                if (swing) {
                    BlockUtils.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                return;
            }
        }
    }

    public static void placeBlock(final BlockPos position, final EnumHand hand, final boolean packet) {
        if (!BlockUtils.mc.world.getBlockState(position).getBlock().isReplaceable((IBlockAccess)BlockUtils.mc.world, position)) {
            return;
        }
        if (getPlaceableSide(position) == null) {
            return;
        }
        clickBlock(position, getPlaceableSide(position), hand, packet);
        BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
    }

    public static void clickBlock(final BlockPos position, final EnumFacing side, final EnumHand hand, final boolean packet) {
        if (packet) {
            BlockUtils.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(position.offset(side), side.getOpposite(), hand, Float.intBitsToFloat(Float.floatToIntBits(17.735476f) ^ 0x7E8DE241), Float.intBitsToFloat(Float.floatToIntBits(26.882437f) ^ 0x7ED70F3B), Float.intBitsToFloat(Float.floatToIntBits(3.0780227f) ^ 0x7F44FE53)));
        }
        else {
            BlockUtils.mc.playerController.processRightClickBlock(BlockUtils.mc.player, BlockUtils.mc.world, position.offset(side), side.getOpposite(), new Vec3d((Vec3i)position), hand);
        }
    }

    public static boolean isIntercepted(final BlockPos blockPos) {
        for (final Entity entity : BlockUtils.mc.world.loadedEntityList) {
            if (entity instanceof EntityItem) {
                continue;
            }
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            if (new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPositionPlaceable(final BlockPos position, final boolean entityCheck, final boolean sideCheck) {
        if (!BlockUtils.mc.world.getBlockState(position).getBlock().isReplaceable((IBlockAccess)BlockUtils.mc.world, position)) {
            return false;
        }
        if (entityCheck) {
            for (final Object entity : BlockUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityItem)) {
                    if (entity instanceof EntityXPOrb) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return !sideCheck || getPlaceableSide(position) != null;
    }

    public static boolean isPositionPlaceable(final BlockPos position, final boolean entityCheck, final boolean sideCheck, final boolean ignoreCrystals) {
        if (!BlockUtils.mc.world.getBlockState(position).getBlock().isReplaceable((IBlockAccess)BlockUtils.mc.world, position)) {
            return false;
        }
        if (entityCheck) {
            for (final Object entity : BlockUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position))) {
                if (!(entity instanceof EntityItem)) {
                    if (entity instanceof EntityXPOrb) {
                        continue;
                    }
                    if (entity instanceof EntityEnderCrystal && ignoreCrystals) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return !sideCheck || getPlaceableSide(position) != null;
    }

    public static boolean isPositionPlaceable(final BlockPos pos, final boolean entityCheck, final double distance) {
        final Block block = BlockUtils.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir)) {
            if (!(block instanceof BlockLiquid)) {
                if (!(block instanceof BlockTallGrass) && !(block instanceof BlockFire)) {
                    if (!(block instanceof BlockDeadBush)) {
                        if (!(block instanceof BlockSnow)) {
                            return false;
                        }
                    }
                }
            }
        }
        if (entityCheck) {
            for (final Object entity : BlockUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (BlockUtils.mc.player.getDistance((Entity) entity) > distance) {
                    continue;
                }
                if (entity instanceof EntityItem) {
                    continue;
                }
                if (entity instanceof EntityXPOrb) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static EnumFacing getPlaceableSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (BlockUtils.mc.world.getBlockState(neighbour).getBlock().canCollideCheck(BlockUtils.mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = BlockUtils.mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    return side;
                }
            }
        }
        return null;
    }

    public static BlockResistance getBlockResistance(final BlockPos block) {
        if (BlockUtils.mc.world.isAirBlock(block)) {
            return BlockResistance.Blank;
        }
        if (BlockUtils.mc.world.getBlockState(block).getBlock().getBlockHardness(BlockUtils.mc.world.getBlockState(block), (World)BlockUtils.mc.world, block) != Float.intBitsToFloat(Float.floatToIntBits(-6.673269f) ^ 0x7F558B6B)) {
            if (!BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
                return BlockResistance.Breakable;
            }
        }
        if (!BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.OBSIDIAN) && !BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ANVIL)) {
            if (!BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENCHANTING_TABLE)) {
                if (!BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.ENDER_CHEST)) {
                    if (BlockUtils.mc.world.getBlockState(block).getBlock().equals(Blocks.BEDROCK)) {
                        return BlockResistance.Unbreakable;
                    }
                    return null;
                }
            }
        }
        return BlockResistance.Resistant;
    }

    static {
        BlockUtils.blackList = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
        BlockUtils.shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
        BlockUtils.SURROUND = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
    }

    public enum BlockResistance
    {
        Blank,
        Breakable,
        Resistant,
        Unbreakable;

        public static BlockResistance[] $VALUES;

        static {
            BlockResistance.$VALUES = new BlockResistance[] { BlockResistance.Blank, BlockResistance.Breakable, BlockResistance.Resistant, BlockResistance.Unbreakable };
        }
    }
}
