/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.combat;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.crystal.CrystalUtils;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.api.utilities.world.BlockUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class ModuleSurroundBreaker
extends Module {
    public ValueBoolean autoBreak = new ValueBoolean("AutoBreak", "AutoBreak", "", false);
    public ValueNumber range = new ValueNumber("Range", "Range", "", Double.longBitsToDouble(Double.doubleToLongBits(1.0688511741957978) ^ 0x7FE91A03B05764F8L), Double.longBitsToDouble(Double.doubleToLongBits(1.0427139870360291E307) ^ 0x7FADB28E0EA555FFL), Double.longBitsToDouble(Double.doubleToLongBits(0.14670650424349568) ^ 0x7FE2C7475AEB0BDAL));
    public List<BlockPos> offsets = new ArrayList<BlockPos>(Arrays.asList(new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1)));
    public EntityPlayer target;
    public TimerUtils timer = new TimerUtils();
    public BlockPos doPos = null;
    public EnumFacing doFace = null;

    public ModuleSurroundBreaker() {
        super("SurroundBreaker", "SurroundBreaker", "", ModuleCategory.COMBAT);
    }

    @Override
    public void onUpdate() {
        block14: {
            BlockPos placePos;
            block13: {
                block12: {
                    if (ModuleSurroundBreaker.mc.player == null) break block12;
                    if (ModuleSurroundBreaker.mc.world != null) break block13;
                }
                return;
            }
            this.target = CrystalUtils.getTarget(this.range.getValue().floatValue());
            if (this.target != null && this.isCitied(this.target) && (placePos = this.findCity(this.target)) != null) {
                int oldSlot = ModuleSurroundBreaker.mc.player.inventory.currentItem;
                int anvilSlot = InventoryUtils.getHotbarBlockSlot(Blocks.ANVIL);
                ModuleSurroundBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(anvilSlot));
                if (BlockUtils.isPositionPlaceable(placePos, true, true)) {
                    BlockUtils.placeBlock(placePos, EnumHand.MAIN_HAND, true);
                }
                ModuleSurroundBreaker.mc.player.swingArm(EnumHand.MAIN_HAND);
                ModuleSurroundBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
                EnumFacing side = BlockUtils.getFirstFacing(placePos);
                if (side != null) {
                    if (this.autoBreak.getValue()) {
                        this.doPos = placePos;
                        this.doFace = side;
                        ModuleSurroundBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, placePos, side));
                        ModuleSurroundBreaker.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, placePos, side));
                        this.timer.reset();
                    }
                }
            }
            int oldSlot = ModuleSurroundBreaker.mc.player.inventory.currentItem;
            int pickSlot = InventoryUtils.getHotbarItemSlot(Items.DIAMOND_PICKAXE);
            if (!this.autoBreak.getValue()) break block14;
            if (this.doPos != null) {
                if (this.doFace != null) {
                    if (this.timer.passed(5 * Float.intBitsToFloat(Float.floatToIntBits(0.39142433f) ^ 0x7F6868C5) * Float.intBitsToFloat(Float.floatToIntBits(0.96488684f) ^ 0x7F7702D3)) && InventoryUtils.getHotbarItemSlot(Items.DIAMOND_PICKAXE) != -1) {
                        ModuleSurroundBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(pickSlot));
                    }
                    mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.doPos, this.doFace));
                    if (oldSlot != -1) {
                        ModuleSurroundBreaker.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(oldSlot));
                    }
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public boolean isCitied(EntityPlayer entityPlayer) {
        BlockPos playerPos = new BlockPos(Math.floor(entityPlayer.posX), entityPlayer.posY, Math.floor(entityPlayer.posZ));
        for (BlockPos blockPos : this.offsets) {
            BlockPos pos = playerPos.add((Vec3i)blockPos);
            if (ModuleSurroundBreaker.mc.world.getBlockState(pos).getBlock() != Blocks.AIR) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public BlockPos findCity(EntityPlayer entityPlayer) {
        BlockPos cityPos = null;
        BlockPos playerPos = new BlockPos(Math.floor(entityPlayer.posX), entityPlayer.posY, Math.floor(entityPlayer.posZ));
        for (BlockPos blockPos : this.offsets) {
            BlockPos pos = playerPos.add((Vec3i)blockPos);
            if (!(ModuleSurroundBreaker.mc.player.getDistanceSq(pos) < Math.sqrt(Double.longBitsToDouble(Double.doubleToLongBits(0.345442610131067) ^ 0x7FCE1BBB524A1993L))) || ModuleSurroundBreaker.mc.world.getBlockState(pos).getBlock() != Blocks.AIR) continue;
            if (cityPos == null) {
                cityPos = pos;
            }
            if (!(ModuleSurroundBreaker.mc.player.getDistanceSq(cityPos) > ModuleSurroundBreaker.mc.player.getDistanceSq(pos))) continue;
            cityPos = pos;
        }
        return cityPos;
    }
}

