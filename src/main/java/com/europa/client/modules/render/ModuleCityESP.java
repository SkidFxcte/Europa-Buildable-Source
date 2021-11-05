/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.api.utilities.world.HoleUtils;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ModuleCityESP
extends Module {
    public static ValueNumber range = new ValueNumber("Range", "Range", "", Double.longBitsToDouble(Double.doubleToLongBits(1.0167839805513748) ^ 0x7FD044BF477909A5L), Double.longBitsToDouble(Double.doubleToLongBits(1.305748489609004E308) ^ 0x7FE73E3B73491F6DL), Double.longBitsToDouble(Double.doubleToLongBits(0.15990896036640737) ^ 0x7FEA77E5958E37DDL));
    public static ValueBoolean outline = new ValueBoolean("Outline", "Outline", "", false);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 0, 0, 180));

    public ModuleCityESP() {
        super("CityESP", "City ESP", "Highlights blocks which can be citied.", ModuleCategory.RENDER);
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        for (EntityPlayer player : ModuleCityESP.mc.world.playerEntities) {
            if (player == null || player == ModuleCityESP.mc.player) continue;
            if (!((double)ModuleCityESP.mc.player.getDistance((Entity)player) <= range.getValue().doubleValue()) || !HoleUtils.isInHole(player)) continue;
            BlockPos pos = new BlockPos(ModuleCityESP.centerPos(player.posX, player.posY, player.posZ));
            if (ModuleCityESP.mc.world.getBlockState(pos.north().north()).getBlock() == Blocks.AIR && ModuleCityESP.mc.world.getBlockState(pos.north().north().up()).getBlock() == Blocks.AIR && ModuleCityESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.OBSIDIAN) {
                RenderUtils.drawBoxESP(pos.north(), daColor.getValue(), Float.intBitsToFloat(Float.floatToIntBits(31.96729f) ^ 0x7E7FBD03), outline.getValue(), true, daColor.getValue().getAlpha(), true, Double.longBitsToDouble(Double.doubleToLongBits(3.6851726988115354E307) ^ 0x7FCA3D42CDC92EEBL), false);
            }
            if (ModuleCityESP.mc.world.getBlockState(pos.east().east()).getBlock() == Blocks.AIR) {
                if (ModuleCityESP.mc.world.getBlockState(pos.east().east().up()).getBlock() == Blocks.AIR && ModuleCityESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.OBSIDIAN) {
                    RenderUtils.drawBoxESP(pos.east(), daColor.getValue(), Float.intBitsToFloat(Float.floatToIntBits(25.754644f) ^ 0x7E4E0983), outline.getValue(), true, daColor.getValue().getAlpha(), true, Double.longBitsToDouble(Double.doubleToLongBits(3.1668516332709612E305) ^ 0x7F5CDCC4BECBE5FFL), false);
                }
            }
            if (ModuleCityESP.mc.world.getBlockState(pos.south().south()).getBlock() == Blocks.AIR) {
                if (ModuleCityESP.mc.world.getBlockState(pos.south().south().up()).getBlock() == Blocks.AIR && ModuleCityESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.OBSIDIAN) {
                    RenderUtils.drawBoxESP(pos.south(), daColor.getValue(), Float.intBitsToFloat(Float.floatToIntBits(10.033183f) ^ 0x7EA087EB), outline.getValue(), true, daColor.getValue().getAlpha(), true, Double.longBitsToDouble(Double.doubleToLongBits(1.0173124402606892E308) ^ 0x7FE21BD7DC86D7C3L), false);
                }
            }
            if (ModuleCityESP.mc.world.getBlockState(pos.west().west()).getBlock() != Blocks.AIR || ModuleCityESP.mc.world.getBlockState(pos.west().west().up()).getBlock() != Blocks.AIR) continue;
            if (ModuleCityESP.mc.world.getBlockState(pos.west()).getBlock() != Blocks.OBSIDIAN) continue;
            RenderUtils.drawBoxESP(pos.west(), daColor.getValue(), Float.intBitsToFloat(Float.floatToIntBits(3320.9998f) ^ 0x7ACF8FFF), outline.getValue(), true, daColor.getValue().getAlpha(), true, Double.longBitsToDouble(Double.doubleToLongBits(1.706826109380788E308) ^ 0x7FEE61EC6384D502L), false);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static Vec3d centerPos(double d, double d2, double d3) {
        void posZ;
        void posY;
        double posX;
        return new Vec3d(Math.floor(posX) + Double.longBitsToDouble(Double.doubleToLongBits(21.903821322356034) ^ 0x7FD5E760D58CF257L), Math.floor((double)posY), Math.floor((double)posZ) + Double.longBitsToDouble(Double.doubleToLongBits(13.973516247435112) ^ 0x7FCBF270B8B9B787L));
    }
}

