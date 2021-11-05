//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.EnumCreatureType;
import com.europa.Europa;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import com.europa.api.utilities.IMinecraft;

public class EntityUtils implements IMinecraft
{
    public static Vec3d getLastTickPos(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return getInterpolatedPos(entity, ticks).subtract(Minecraft.getMinecraft().getRenderManager().viewerPosX, Minecraft.getMinecraft().getRenderManager().viewerPosY, Minecraft.getMinecraft().getRenderManager().viewerPosZ);
    }

    public static boolean isProjectile(final Entity entity) {
        return entity instanceof EntityShulkerBullet || entity instanceof EntityFireball;
    }

    public static boolean isMobAggressive(final Entity entity) {
        if (entity instanceof EntityPigZombie) {
            if (!((EntityPigZombie)entity).isArmsRaised()) {
                if (!((EntityPigZombie)entity).isAngry()) {
                    return isHostileMob(entity);
                }
            }
            return true;
        }
        if (entity instanceof EntityWolf) {
            return ((EntityWolf)entity).isAngry() && !EntityUtils.mc.player.equals((Object)((EntityWolf)entity).getOwner());
        }
        if (entity instanceof EntityEnderman) {
            return ((EntityEnderman)entity).isScreaming();
        }
        return isHostileMob(entity);
    }

    public static boolean isVehicle(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }

    public static Vec3d getCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + Double.longBitsToDouble(Double.doubleToLongBits(22.5321180609703) ^ 0x7FD68838E3A57A77L);
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + Double.longBitsToDouble(Double.doubleToLongBits(3.0619626332512278) ^ 0x7FE87EE643DB1AC2L);
        return new Vec3d(x, y, z);
    }

    public static float getHealth(final Entity entity) {
        if (isLiving(entity)) {
            final EntityLivingBase livingBase = (EntityLivingBase)entity;
            return livingBase.getHealth() + livingBase.getAbsorptionAmount();
        }
        return Float.intBitsToFloat(Float.floatToIntBits(3.0545621E38f) ^ 0x7F65CCBA);
    }

    public static Vec3d getInterpolatedPos(final Entity entity, final double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getLastTickPos(entity, ticks, ticks, ticks));
    }

    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }

    public static boolean isntValid(final Entity entity) {
        return entity == null || isDead(entity) || entity.equals((Object)EntityUtils.mc.player) || (entity instanceof EntityPlayer && Europa.FRIEND_MANAGER.isFriend(entity.getName()));
    }

    public static boolean isDead(final Entity entity) {
        return !isAlive(entity);
    }

    public static boolean isAlive(final Entity entity) {
        if (isLiving(entity)) {
            if (!entity.isDead && ((EntityLivingBase)entity).getHealth() > Float.intBitsToFloat(Float.floatToIntBits(8.671327E37f) ^ 0x7E8278BD)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }

    public static boolean isHostileMob(final Entity entity) {
        return entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity);
    }

    public static boolean isNeutralMob(final Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }

    public static boolean isPassive(final Entity e) {
        return (!(e instanceof EntityWolf) || !((EntityWolf)e).isAngry()) && (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid || (e instanceof EntityIronGolem && ((EntityIronGolem)e).getRevengeTarget() == null));
    }

    public static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plusY) {
        final List<BlockPos> circleBlocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - Float.intBitsToFloat(Float.floatToIntBits(4.3435817f) ^ 0x7F0AFE9F)) * (r - Float.intBitsToFloat(Float.floatToIntBits(7.0435686f) ^ 0x7F6164EA)))) {
                        final BlockPos l = new BlockPos(x, y + plusY, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }

    public static boolean isMoving() {
        return EntityUtils.mc.player.motionX > Double.longBitsToDouble(Double.doubleToLongBits(1.9208899197057635E307) ^ 0x7FBB5AB8AD5DB287L) || EntityUtils.mc.player.motionX < Double.longBitsToDouble(Double.doubleToLongBits(-4.710141003534489E307) ^ 0x7FD0C4C6CF24C3EFL) || EntityUtils.mc.player.motionZ > Double.longBitsToDouble(Double.doubleToLongBits(1.3182290674040571E308) ^ 0x7FE7771B096D7F3FL) || EntityUtils.mc.player.motionZ < Double.longBitsToDouble(Double.doubleToLongBits(-7.645656144229753E307) ^ 0x7FDB382D2E04048FL);
    }
}
