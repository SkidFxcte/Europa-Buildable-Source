/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.crystal;

import com.europa.Europa;
import com.europa.api.utilities.IMinecraft;
import com.europa.api.utilities.math.MathUtils;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class CrystalUtils
implements IMinecraft {
    public static EntityPlayer getTarget(final float range) {
        EntityPlayer targetPlayer = null;
        for (final EntityPlayer player : new ArrayList<EntityPlayer>(CrystalUtils.mc.world.playerEntities)) {
            if (CrystalUtils.mc.player.getDistanceSq((Entity)player) > MathUtils.square(range)) {
                continue;
            }
            if (player == CrystalUtils.mc.player) {
                continue;
            }
            if (Europa.FRIEND_MANAGER.isFriend(player.getName())) {
                continue;
            }
            if (player.isDead) {
                continue;
            }
            if (player.getHealth() <= Float.intBitsToFloat(Float.floatToIntBits(1.2784752E38f) ^ 0x7EC05D13)) {
                continue;
            }
            if (targetPlayer == null) {
                targetPlayer = player;
            }
            else {
                if (CrystalUtils.mc.player.getDistanceSq((Entity)player) >= CrystalUtils.mc.player.getDistanceSq((Entity)targetPlayer)) {
                    continue;
                }
                targetPlayer = player;
            }
        }
        return targetPlayer;
    }

    public static List<BlockPos> getSphere(final float range, final boolean sphere, final boolean hollow) {
        final List<BlockPos> blocks = new ArrayList<BlockPos>();
        for (int x = CrystalUtils.mc.player.getPosition().getX() - (int)range; x <= CrystalUtils.mc.player.getPosition().getX() + range; ++x) {
            for (int z = CrystalUtils.mc.player.getPosition().getZ() - (int)range; z <= CrystalUtils.mc.player.getPosition().getZ() + range; ++z) {
                for (int y = sphere ? (CrystalUtils.mc.player.getPosition().getY() - (int)range) : CrystalUtils.mc.player.getPosition().getY(); y < CrystalUtils.mc.player.getPosition().getY() + range; ++y) {
                    final double distance = (CrystalUtils.mc.player.getPosition().getX() - x) * (CrystalUtils.mc.player.getPosition().getX() - x) + (CrystalUtils.mc.player.getPosition().getZ() - z) * (CrystalUtils.mc.player.getPosition().getZ() - z) + (sphere ? ((CrystalUtils.mc.player.getPosition().getY() - y) * (CrystalUtils.mc.player.getPosition().getY() - y)) : 0);
                    if (distance < range * range && (!hollow || distance >= (range - Double.longBitsToDouble(Double.doubleToLongBits(638.4060856917202) ^ 0x7F73F33FA9DAEA7FL)) * (range - Double.longBitsToDouble(Double.doubleToLongBits(13.015128470890444) ^ 0x7FDA07BEEB3F6D07L)))) {
                        blocks.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }


    public static boolean canPlaceCrystal(final BlockPos position, final boolean placeUnderBlock, final boolean multiPlace, final boolean holePlace) {
        if (CrystalUtils.mc.world.getBlockState(position).getBlock() != Blocks.BEDROCK && CrystalUtils.mc.world.getBlockState(position).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        if (CrystalUtils.mc.world.getBlockState(position.add(0, 1, 0)).getBlock() != Blocks.AIR || (!placeUnderBlock && CrystalUtils.mc.world.getBlockState(position.add(0, 2, 0)).getBlock() != Blocks.AIR)) {
            return false;
        }
        if (multiPlace) {
            return CrystalUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position.add(0, 1, 0))).isEmpty() && !placeUnderBlock && CrystalUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position.add(0, 2, 0))).isEmpty();
        }
        for (final Object entity : CrystalUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position.add(0, 1, 0)))) {
            if (entity instanceof EntityEnderCrystal) {
                continue;
            }
            return false;
        }
        if (!placeUnderBlock) {
            for (final Object entity : CrystalUtils.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position.add(0, 2, 0)))) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    if (holePlace && entity instanceof EntityPlayer) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void drawText(final BlockPos pos, final String text) {
        GlStateManager.pushMatrix();
        RenderUtils.glBillboardDistanceScaled(pos.getX() + Float.intBitsToFloat(Float.floatToIntBits(56.765804f) ^ 0x7D63102F), pos.getY() + Float.intBitsToFloat(Float.floatToIntBits(22.30964f) ^ 0x7EB27A25), pos.getZ() + Float.intBitsToFloat(Float.floatToIntBits(15.876006f) ^ 0x7E7E041F), (EntityPlayer)CrystalUtils.mc.player, Float.intBitsToFloat(Float.floatToIntBits(33.090816f) ^ 0x7D845CFF));
        GlStateManager.disableDepth();
        GlStateManager.translate(-(Europa.FONT_MANAGER.getStringWidth(text) / Double.longBitsToDouble(Double.doubleToLongBits(0.2579471275262499) ^ 0x7FD08234AB34A1F3L)), Double.longBitsToDouble(Double.doubleToLongBits(8.964569445934237E307) ^ 0x7FDFEA38A13E706BL), Double.longBitsToDouble(Double.doubleToLongBits(1.3014105849625204E308) ^ 0x7FE72A76F089AAE7L));
        Europa.FONT_MANAGER.drawString(text, Float.intBitsToFloat(Float.floatToIntBits(2.2813044E36f) ^ 0x7BDBAE7F), Float.intBitsToFloat(Float.floatToIntBits(1.2346908E38f) ^ 0x7EB9C68F), Color.WHITE);
        GlStateManager.popMatrix();
    }
    public static boolean isEntityMoving(final EntityLivingBase entity) {
        return entity.motionX > Double.longBitsToDouble(Double.doubleToLongBits(0.5327718501168097) ^ 0x7FE10C778D0F6544L) || entity.motionY > Double.longBitsToDouble(Double.doubleToLongBits(0.07461435496686485) ^ 0x7FB319ED266512E7L) || entity.motionZ > Double.longBitsToDouble(Double.doubleToLongBits(0.9006325807477794) ^ 0x7FECD1FB6B00C2E7L);
    }

    public static boolean canSeePos(final BlockPos pos) {
        return CrystalUtils.mc.world.rayTraceBlocks(new Vec3d(CrystalUtils.mc.player.posX, CrystalUtils.mc.player.posY + CrystalUtils.mc.player.getEyeHeight(), CrystalUtils.mc.player.posZ), new Vec3d((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), false, true, false) == null;
    }

}

