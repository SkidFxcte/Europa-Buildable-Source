/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.event.impl.world.EventBlock;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.math.MathUtils;
import com.europa.api.utilities.math.TPSUtils;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.api.utilities.render.RenderUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModulePacketMine
extends Module {
    public static ValueBoolean autoSwitch = new ValueBoolean("AutoSwitch", "AutoSwitch", "", true);
    public static ValueNumber resetRange = new ValueNumber("ResetRange", "ResetRange", "", Double.longBitsToDouble(Double.doubleToLongBits(0.17561330259400176) ^ 0x7FE27A7F27B12004L), Double.longBitsToDouble(Double.doubleToLongBits(1.8612638262609325) ^ 0x7FE9C7BC93F04BE7L), Double.longBitsToDouble(Double.doubleToLongBits(1.7505843828671883) ^ 0x7FB50264C514D8BFL));
    public static ValueEnum renderMode = new ValueEnum("RenderMode", "RenderMode", "", renderModes.Grow);
    public static ValueBoolean statusColor = new ValueBoolean("StatusColor", "StatusColor", "", false);
    public static ValueEnum statusMode = new ValueEnum("StatusMode", "StatusMode", "", statusModes.Static);
    public static ValueBoolean render = new ValueBoolean("Render", "Render", "", false);
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public Color fagColor;
    public BlockPos renderPos = null;
    public BlockPos breakPos = null;
    public EnumFacing breakFace = null;
    public TimerUtils timer = new TimerUtils();
    public boolean readyToMine = false;
    public int oldSlot = -1;

    public ModulePacketMine() {
        super("PacketMine", "Packet Mine", "Makes mining easier on less stricter servers.", ModuleCategory.PLAYER);
    }

    @Override
    public void onUpdate() {
        block11: {
            block10: {
                this.fagColor = syncColor.getValue() ? ModulePacketMine.globalColor(255) : daColor.getValue();
                if (ModulePacketMine.mc.player == null) break block10;
                if (ModulePacketMine.mc.world != null) break block11;
            }
            return;
        }
        if (this.breakPos != null) {
            float breakTime;
            this.oldSlot = ModulePacketMine.mc.player.inventory.currentItem;
            if (ModulePacketMine.mc.player.getDistanceSq(this.breakPos) > MathUtils.square(resetRange.getValue().doubleValue()) || ModulePacketMine.mc.world.getBlockState(this.breakPos).getBlock() == Blocks.AIR) {
                this.breakPos = null;
                this.breakFace = null;
                this.readyToMine = false;
                return;
            }
            if (render.getValue()) {
                this.renderPos = this.breakPos;
            }
            if (this.timer.hasReached((long)(breakTime = ModulePacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)ModulePacketMine.mc.world, this.breakPos) * Float.intBitsToFloat(Float.floatToIntBits(0.26427442f) ^ 0x7F274EFA) * Float.intBitsToFloat(Float.floatToIntBits(0.9389823f) ^ 0x7F706125)))) {
                this.readyToMine = true;
            }
            if (autoSwitch.getValue()) {
                if (this.timer.hasReached((long)breakTime) && InventoryUtils.getHotbarItemSlot(Items.DIAMOND_PICKAXE) != -1) {
                    ModulePacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.pickaxeInHotbar()));
                }
                mc.getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
                if (this.oldSlot != -1) {
                    ModulePacketMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                }
                return;
            }
        }
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        block3: {
            float shrinkFactor;
            Color color;
            Color defColor;
            block7: {
                Color alphaFade;
                float fadeA;
                AxisAlignedBB bb;
                block6: {
                    block5: {
                        AxisAlignedBB axisAlignedBB1;
                        double oldMaxY;
                        block4: {
                            if (ModulePacketMine.mc.player == null || ModulePacketMine.mc.world == null) {
                                return;
                            }
                            if (!render.getValue()) break block3;
                            if (this.breakPos == null || ModulePacketMine.mc.world.getBlockState(this.breakPos).getBlock() == Blocks.AIR) break block3;
                            defColor = new Color(this.fagColor.getRed(), this.fagColor.getGreen(), this.fagColor.getBlue(), daColor.getValue().getAlpha());
                            bb = new AxisAlignedBB((double)this.breakPos.getX() - ModulePacketMine.mc.getRenderManager().viewerPosX, (double)this.breakPos.getY() - ModulePacketMine.mc.getRenderManager().viewerPosY, (double)this.breakPos.getZ() - ModulePacketMine.mc.getRenderManager().viewerPosZ, (double)(this.breakPos.getX() + 1) - ModulePacketMine.mc.getRenderManager().viewerPosX, (double)(this.breakPos.getY() + 1) - ModulePacketMine.mc.getRenderManager().viewerPosY, (double)(this.breakPos.getZ() + 1) - ModulePacketMine.mc.getRenderManager().viewerPosZ);
                            float breakTime = ModulePacketMine.mc.world.getBlockState(this.breakPos).getBlockHardness((World)ModulePacketMine.mc.world, this.breakPos) * Float.intBitsToFloat(Float.floatToIntBits(0.87076825f) ^ 0x7EFEEAAB) * Float.intBitsToFloat(Float.floatToIntBits(0.5921152f) ^ 0x7F1794DD);
                            double progression = (float)this.timer.getTimePassed() * TPSUtils.getTpsFactor() / Float.intBitsToFloat(Float.floatToIntBits(0.03283384f) ^ 0x7E4E7CC7);
                            oldMaxY = bb.maxY;
                            double centerX = bb.minX + (bb.maxX - bb.minX) / Double.longBitsToDouble(Double.doubleToLongBits(0.4677600299948414) ^ 0x7FDDEFC7C3CD0B6FL);
                            double centerY = bb.minY + (bb.maxY - bb.minY) / Double.longBitsToDouble(Double.doubleToLongBits(0.2265017733140163) ^ 0x7FCCFE02966F5283L);
                            double centerZ = bb.minZ + (bb.maxZ - bb.minZ) / Double.longBitsToDouble(Double.doubleToLongBits(0.7187776739735182) ^ 0x7FE7003A0959F571L);
                            double increaseX = progression * ((bb.maxX - centerX) / Double.longBitsToDouble(Double.doubleToLongBits(0.13526251945472825) ^ 0x7FE5504840B76025L));
                            double increaseY = progression * ((bb.maxY - centerY) / Double.longBitsToDouble(Double.doubleToLongBits(0.1390228780814261) ^ 0x7FE5CB806D60B4E4L));
                            double increaseZ = progression * ((bb.maxZ - centerZ) / Double.longBitsToDouble(Double.doubleToLongBits(0.2497058873358051) ^ 0x7FEBF65CCDDCEBB7L));
                            double upY = progression * ((bb.maxY - bb.minY) / Double.longBitsToDouble(Double.doubleToLongBits(1.8254804590156823) ^ 0x7FD9352AFF6F59CDL));
                            axisAlignedBB1 = new AxisAlignedBB(centerX - increaseX, centerY - increaseY, centerZ - increaseZ, centerX + increaseX, centerY + increaseY, centerZ + increaseZ);
                            AxisAlignedBB riseBB = new AxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY + upY, bb.maxZ);
                            float sGreen = MathHelper.clamp((float)((float)upY), (float)Float.intBitsToFloat(Float.floatToIntBits(2.0187074E38f) ^ 0x7F17DEE4), (float)Float.intBitsToFloat(Float.floatToIntBits(19.36041f) ^ 0x7E1AE21F));
                            color = statusMode.getValue().equals((Object)statusModes.Static) ? new Color(this.timer.hasReached((long)breakTime) ? 0 : 255, this.timer.hasReached((long)breakTime) ? 255 : 0, 0, daColor.getValue().getAlpha()) : new Color(255 - (int)(sGreen * Float.intBitsToFloat(Float.floatToIntBits(0.068660654f) ^ 0x7EF39DF5)), (int)(sGreen * Float.intBitsToFloat(Float.floatToIntBits(0.012068316f) ^ 0x7F3ABA30)), 0, daColor.getValue().getAlpha());
                            fadeA = MathHelper.clamp((float)((float)upY), (float)Float.intBitsToFloat(Float.floatToIntBits(7.600084E37f) ^ 0x7E64B4EB), (float)Float.intBitsToFloat(Float.floatToIntBits(16.607412f) ^ 0x7E04DBFB));
                            shrinkFactor = MathHelper.clamp((float)((float)increaseX), (float)Float.intBitsToFloat(Float.floatToIntBits(-14.816234f) ^ 0x7EED0F4B), (float)Float.intBitsToFloat(Float.floatToIntBits(9.43287f) ^ 0x7E96ED09));
                            alphaFade = new Color(defColor.getRed(), defColor.getGreen(), defColor.getBlue(), (int)(fadeA * Float.intBitsToFloat(Float.floatToIntBits(0.008138063f) ^ 0x7F7A5583)));
                            if (!renderMode.getValue().equals((Object)renderModes.Rise)) break block4;
                            if (!(riseBB.maxY > oldMaxY)) {
                                RenderUtils.drawFilledBox(riseBB, statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                                RenderUtils.drawBlockOutline(riseBB, statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(22.925123f) ^ 0x7E3766A7));
                            }
                            if (!(riseBB.maxY >= oldMaxY)) break block3;
                            RenderUtils.drawFilledBox(bb, statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                            RenderUtils.drawBlockOutline(bb, statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(5.6391788f) ^ 0x7F347427));
                            break block3;
                        }
                        if (!renderMode.getValue().equals((Object)renderModes.Grow)) break block5;
                        if (!(axisAlignedBB1.maxY > oldMaxY)) {
                            RenderUtils.drawFilledBox(axisAlignedBB1, statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                            RenderUtils.drawBlockOutline(axisAlignedBB1, statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(7.8420115f) ^ 0x7F7AF1C2));
                        }
                        if (!(axisAlignedBB1.maxY >= oldMaxY)) break block3;
                        RenderUtils.drawFilledBox(bb, statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                        RenderUtils.drawBlockOutline(bb, statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(117.64367f) ^ 0x7D6B498F));
                        break block3;
                    }
                    if (!renderMode.getValue().equals((Object)renderModes.Static)) break block6;
                    RenderUtils.drawFilledBox(bb, statusColor.getValue() ? color.getRGB() : defColor.getRGB());
                    RenderUtils.drawBlockOutline(bb, statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(5.7362022f) ^ 0x7F378EF8));
                    break block3;
                }
                if (!renderMode.getValue().equals((Object)renderModes.Fade)) break block7;
                RenderUtils.drawFilledBox(bb, statusColor.getValue() ? new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(fadeA * Float.intBitsToFloat(Float.floatToIntBits(0.012192621f) ^ 0x7F38C38F))).getRGB() : alphaFade.getRGB());
                RenderUtils.drawBlockOutline(bb, statusColor.getValue() ? new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(fadeA * Float.intBitsToFloat(Float.floatToIntBits(0.07938013f) ^ 0x7EDD920D))) : alphaFade, Float.intBitsToFloat(Float.floatToIntBits(5.6802325f) ^ 0x7F35C477));
                break block3;
            }
            if (!renderMode.getValue().equals((Object)renderModes.Animate)) break block3;
            RenderUtils.drawFilledBox(RenderUtils.fixBB(new AxisAlignedBB(this.breakPos)).shrink((double)shrinkFactor), statusColor.getValue() ? color.getRGB() : defColor.getRGB());
            RenderUtils.drawBlockOutline(RenderUtils.fixBB(new AxisAlignedBB(this.breakPos)).shrink((double)shrinkFactor), statusColor.getValue() ? color : defColor, Float.intBitsToFloat(Float.floatToIntBits(119.3883f) ^ 0x7D6EC6CF));
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onBlock(EventBlock eventBlock) {
        block2: {
            if (ModulePacketMine.mc.player == null || ModulePacketMine.mc.world == null) {
                return;
            }
            if (!ModulePacketMine.canBlockBeBroken(eventBlock.pos)) break block2;
            if (this.breakPos == null) {
                this.breakPos = eventBlock.pos;
                this.breakFace = eventBlock.facing;
                this.readyToMine = false;
                this.timer.reset();
            }
            ModulePacketMine.mc.player.swingArm(EnumHand.MAIN_HAND);
            ModulePacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.breakPos, this.breakFace));
            ModulePacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.breakPos, this.breakFace));
            eventBlock.setCancelled(true);
        }
    }

    public static boolean canBlockBeBroken(BlockPos blockPos) {
        BlockPos pos;
        IBlockState blockState = ModulePacketMine.mc.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)ModulePacketMine.mc.world, blockPos) != Float.intBitsToFloat(Float.floatToIntBits(-6.0870466f) ^ 0x7F42C916);
    }

    public int pickaxeInHotbar() {
        for (int i = 0; i < 9; ++i) {
            if (ModulePacketMine.mc.player.inventory.getStackInSlot(i).getItem() != Items.DIAMOND_PICKAXE) continue;
            return i;
        }
        return -1;
    }

    @Override
    public void onEnable() {
        this.breakPos = null;
    }

    @Override
    public void onDisable() {
        this.breakPos = null;
    }

    @Override
    public String getHudInfo() {
        String t = "";
        if (this.breakPos == null) {
            t = " [" + ChatFormatting.WHITE + "No Block" + ChatFormatting.GRAY + "]";
        } else if (this.breakPos != null && !this.readyToMine) {
            t = " [" + ChatFormatting.RED + "Not Ready" + ChatFormatting.GRAY + "]";
        } else if (this.breakPos != null) {
            if (this.readyToMine) {
                t = " [" + ChatFormatting.GREEN + "Ready" + ChatFormatting.GRAY + "]";
            }
        }
        return t;
    }

    public static enum statusModes {
        Static,
        Smooth;

    }

    public static enum renderModes {
        Grow,
        Rise,
        Fade,
        Animate,
        Static;

    }
}

