/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.world.BlockUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleSurround
extends Module {
    public static ValueNumber bpts = new ValueNumber("BPT", "BPT", "BPT", 15, 1, 50);
    public static ValueBoolean yStop = new ValueBoolean("YStop", "YStop", "", true);
    public static ValueBoolean stepDisable = new ValueBoolean("OffStep", "OffStep", "", false);
    public static ValueBoolean dynamic = new ValueBoolean("Dynamic", "Dynamic", "", false);
    public static ValueBoolean swing = new ValueBoolean("Swing", "Swing", "", false);
    public static ValueBoolean sneak = new ValueBoolean("Sneak", "Sneak", "", false);
    public static ValueBoolean assist = new ValueBoolean("Assist", "Assost", "", true);
    public static ValueBoolean echestBP = new ValueBoolean("EChestBackup", "EchestBackup", "", false);
    public static ValueBoolean antiPhase = new ValueBoolean("AntiPhase", "AntiPhase", "", false);
    public static ValueBoolean killCrystal = new ValueBoolean("KillCrystals", "KillCrystals", "", false);
    public static ValueNumber crystalRange = new ValueNumber("CrystalRange", "CrystalRange", "", Double.longBitsToDouble(Double.doubleToLongBits(0.33672415955993934) ^ 0x7FD58CE37D455049L), Double.longBitsToDouble(Double.doubleToLongBits(4.620456411263302) ^ 0x7FE27B58ECEBE0ADL), Double.longBitsToDouble(Double.doubleToLongBits(0.059866212709466675) ^ 0x7FA2A6C8C371F09FL));
    public List<BlockPos> places = new ArrayList<BlockPos>(Arrays.asList(new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)));
    public int oldSlot = -1;

    public ModuleSurround() {
        super("Surround", "Surround", "", ModuleCategory.COMBAT);
    }

    @Override
    public void onMotionUpdate() {
        if (ModuleSurround.mc.player == null || ModuleSurround.mc.world == null) {
            return;
        }
        this.oldSlot = ModuleSurround.mc.player.inventory.currentItem;
        int obiSlot = InventoryUtils.getHotbarItemSlot(Item.getItemFromBlock((Block)Blocks.OBSIDIAN));
        int echestSlot = InventoryUtils.getHotbarItemSlot(Item.getItemFromBlock((Block)Blocks.ENDER_CHEST));
        if (echestBP.getValue()) {
            if (obiSlot == -1 && echestSlot == -1) {
                this.toggle();
                return;
            }
        } else if (obiSlot == -1) {
            this.toggle();
            return;
        }
        if (ModuleSurround.mc.player.motionY > Double.longBitsToDouble(Double.doubleToLongBits(4.275775052025153E307) ^ 0x7FCE71CCA602BED3L) && yStop.getValue()) {
            this.toggle();
            return;
        }
        if (stepDisable.getValue()) {
            if (Europa.getModuleManager().isModuleEnabled("Step")) {
                this.toggle();
                return;
            }
        }
        if (!ModuleSurround.mc.player.isSneaking() && sneak.getValue()) {
            return;
        }
        this.switchSlot(obiSlot);
        if (echestBP.getValue() && obiSlot == -1) {
            this.switchSlot(echestSlot);
        }
        int ticks = 0;
        for (BlockPos blockPos : this.places) {
            if (ticks > bpts.getValue().intValue()) break;
            BlockPos pos = this.playerBlockPos().add((Vec3i)blockPos);
            this.placeBlock(pos, assist.getValue(), false);
            if (dynamic.getValue()) {
                this.placeDynamic(this.playerBlockPos());
            }
            if (antiPhase.getValue()) {
                this.placeAntiPhase(this.playerBlockPos());
            }
            ++ticks;
        }
        this.switchSlot(this.oldSlot);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        block1: {
            if (!killCrystal.getValue() || !(receive.getPacket() instanceof SPacketSpawnObject) || ((SPacketSpawnObject)receive.getPacket()).getType() != 51) break block1;
            if (ModuleSurround.mc.player.getDistance(((SPacketSpawnObject)receive.getPacket()).getX(), ((SPacketSpawnObject)receive.getPacket()).getY(), ((SPacketSpawnObject)receive.getPacket()).getZ()) > crystalRange.getValue().doubleValue()) {
                return;
            }
            CPacketUseEntity useEntity = new CPacketUseEntity();
            useEntity.entityId = ((SPacketSpawnObject)receive.getPacket()).getEntityID();
            useEntity.action = CPacketUseEntity.Action.ATTACK;
            ModuleSurround.mc.player.connection.sendPacket((Packet)useEntity);
        }
    }

    public BlockPos playerBlockPos() {
        double decimal = ModuleSurround.mc.player.posY - Math.floor(ModuleSurround.mc.player.posY);
        double posY = decimal > Double.longBitsToDouble(Double.doubleToLongBits(2.7180246944036117) ^ 0x7FD68DB088124B97L) ? Math.ceil(ModuleSurround.mc.player.posY) : Math.floor(ModuleSurround.mc.player.posY);
        return new BlockPos(Math.floor(ModuleSurround.mc.player.posX) + Double.longBitsToDouble(Double.doubleToLongBits(11.841300934390032) ^ 0x7FC7AEBEFEFE994FL), posY, Math.floor(ModuleSurround.mc.player.posZ) + Double.longBitsToDouble(Double.doubleToLongBits(30.420592022236402) ^ 0x7FDE6BABEB3476BFL));
    }

    /*
     * WARNING - void declaration
     */
    public void placeBlock(BlockPos blockPos, boolean bl, boolean bl2) {
        block1: {
            if (this.canPlace((BlockPos)blockPos)) {
                ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ModuleSurround.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                BlockUtils.placeBlock((BlockPos)blockPos, swing.getValue(), (boolean)bl);
                ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ModuleSurround.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (!this.canPlace(blockPos.down()) || bl2 == false) break block1;
            ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ModuleSurround.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            BlockUtils.placeBlock(blockPos.down(), swing.getValue(), (boolean)bl);
            ModuleSurround.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ModuleSurround.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    /*
     * WARNING - void declaration
     */
    public void placeAntiPhase(BlockPos blockPos) {
        block4: {
            if (ModuleSurround.phaseIntercept(blockPos.north()) && this.canPlace(blockPos.north(2).up())) {
                BlockUtils.placeBlock(blockPos.north(2).up(), swing.getValue(), false);
            }
            if (ModuleSurround.phaseIntercept(blockPos.east())) {
                if (this.canPlace(blockPos.east(2).up())) {
                    BlockUtils.placeBlock(blockPos.east(2).up(), swing.getValue(), false);
                }
            }
            if (ModuleSurround.phaseIntercept(blockPos.south()) && this.canPlace(blockPos.south(2).up())) {
                BlockUtils.placeBlock(blockPos.south(2).up(), swing.getValue(), false);
            }
            if (!ModuleSurround.phaseIntercept(blockPos.west()) || !this.canPlace(blockPos.west(2).up())) break block4;
            BlockUtils.placeBlock(blockPos.west(2).up(), swing.getValue(), false);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void switchSlot(int n) {
        ModuleSurround.mc.player.inventory.currentItem = n;
    }

    /*
     * WARNING - void declaration
     */
    public boolean canPlace(BlockPos blockPos) {
        Block block = BlockUtils.mc.world.getBlockState((BlockPos)blockPos).getBlock();
        return block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow;
    }

    public static boolean phaseIntercept(BlockPos blockPos) {
        for (Entity entity : ModuleSurround.mc.world.loadedEntityList) {
            BlockPos blockPos2;
            if (!(entity instanceof EntityPlayer) || entity == ModuleSurround.mc.player) continue;
            if (!new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public void placeDynamic(BlockPos blockPos) {
        block15: {
            if (BlockUtils.isIntercepted(blockPos.north())) {
                if (this.canPlace(blockPos.north(2))) {
                    BlockUtils.placeBlock(blockPos.north(2), swing.getValue(), false);
                }
                if (this.canPlace(blockPos.north().south())) {
                    BlockUtils.placeBlock(blockPos.north().south(), swing.getValue(), false);
                }
                if (this.canPlace(blockPos.north().east())) {
                    BlockUtils.placeBlock(blockPos.north().east(), swing.getValue(), false);
                }
            }
            if (BlockUtils.isIntercepted(blockPos.east())) {
                if (this.canPlace(blockPos.east(2))) {
                    BlockUtils.placeBlock(blockPos.east(2), swing.getValue(), false);
                }
                if (this.canPlace(blockPos.east().north())) {
                    BlockUtils.placeBlock(blockPos.east().north(), swing.getValue(), false);
                }
                if (this.canPlace(blockPos.east().south())) {
                    BlockUtils.placeBlock(blockPos.east().south(), swing.getValue(), false);
                }
            }
            if (BlockUtils.isIntercepted(blockPos.south())) {
                if (this.canPlace(blockPos.south(2))) {
                    BlockUtils.placeBlock(blockPos.south(2), swing.getValue(), false);
                }
                if (this.canPlace(blockPos.south().east())) {
                    BlockUtils.placeBlock(blockPos.south().east(), swing.getValue(), false);
                }
                if (this.canPlace(blockPos.south().west())) {
                    BlockUtils.placeBlock(blockPos.south().west(), swing.getValue(), false);
                }
            }
            if (!BlockUtils.isIntercepted(blockPos.west())) break block15;
            if (this.canPlace(blockPos.west(2))) {
                BlockUtils.placeBlock(blockPos.west(2), swing.getValue(), false);
            }
            if (this.canPlace(blockPos.west().north())) {
                BlockUtils.placeBlock(blockPos.west().north(), swing.getValue(), false);
            }
            if (this.canPlace(blockPos.west().south())) {
                BlockUtils.placeBlock(blockPos.west().south(), swing.getValue(), false);
            }
        }
    }
}

