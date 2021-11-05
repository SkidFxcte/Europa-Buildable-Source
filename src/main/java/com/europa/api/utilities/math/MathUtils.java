//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.math;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class MathUtils
{
    public static double roundToPlaces(final double number, final int places) {
        BigDecimal decimal = new BigDecimal(number);
        decimal = decimal.setScale(places, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }

    public static Vec3d roundVector(final Vec3d vec3d, final int places) {
        return new Vec3d(roundToPlaces(vec3d.x, places), roundToPlaces(vec3d.y, places), roundToPlaces(vec3d.z, places));
    }

    public static double square(final double input) {
        return input * input;
    }

    public static float square(final float input) {
        return input * input;
    }

    public static double distance(final float x, final float y, final float x1, final float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return interpolateEntity(entity, ticks).subtract(Minecraft.getMinecraft().getRenderManager().viewerPosX, Minecraft.getMinecraft().getRenderManager().viewerPosY, Minecraft.getMinecraft().getRenderManager().viewerPosZ);
    }

    public static Vec3d interpolateEntity(final Entity entity, final float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }

    public static double[] directionSpeed(final double speed) {
        final Minecraft mc = Minecraft.getMinecraft();
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != Float.intBitsToFloat(Float.floatToIntBits(1.9741632E38f) ^ 0x7F148500)) {
            if (side > Float.intBitsToFloat(Float.floatToIntBits(3.3727889E38f) ^ 0x7F7DBD8D)) {
                yaw += ((forward > Float.intBitsToFloat(Float.floatToIntBits(2.556533E38f) ^ 0x7F405509)) ? -45 : 45);
            }
            else if (side < Float.intBitsToFloat(Float.floatToIntBits(3.3104196E38f) ^ 0x7F790C5D)) {
                yaw += ((forward > Float.intBitsToFloat(Float.floatToIntBits(1.8714153E38f) ^ 0x7F0CCA26)) ? 45 : -45);
            }
            side = Float.intBitsToFloat(Float.floatToIntBits(1.6154958E37f) ^ 0x7D42754F);
            if (forward > Float.intBitsToFloat(Float.floatToIntBits(2.6036586E38f) ^ 0x7F43E0A4)) {
                forward = Float.intBitsToFloat(Float.floatToIntBits(6.4496064f) ^ 0x7F4E632D);
            }
            else if (forward < Float.intBitsToFloat(Float.floatToIntBits(3.2306641E38f) ^ 0x7F730C54)) {
                forward = Float.intBitsToFloat(Float.floatToIntBits(-192.56981f) ^ 0x7CC091DF);
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + Float.intBitsToFloat(Float.floatToIntBits(0.077464305f) ^ 0x7F2AA59B)));
        final double cos = Math.cos(Math.toRadians(yaw + Float.intBitsToFloat(Float.floatToIntBits(0.026198639f) ^ 0x7E629E87)));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
}
