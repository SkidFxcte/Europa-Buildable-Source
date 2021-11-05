//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderGlobal;
import java.util.Objects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import com.europa.api.utilities.entity.EntityUtils;
import net.minecraft.util.EnumFacing;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import com.europa.api.utilities.IMinecraft;
import net.minecraft.client.renderer.Tessellator;

public class RenderUtils extends Tessellator implements IMinecraft
{
    public static ICamera camera;
    public static Frustum frustrum;
    public static AxisAlignedBB DEFAULT_AABB;
    public Map<Integer, Boolean> glCapMap;
    public static int deltaTime;
    public static RenderUtils INSTANCE;
    public static boolean depth;
    public static boolean texture;
    public static boolean clean;
    public static boolean bind;
    public static boolean override;

    public RenderUtils() {
        super(2097152);
        this.glCapMap = new HashMap<Integer, Boolean>();
    }

    public static void prepare(final int mode) {
        prepareGL();
        begin(mode);
    }

    public static void prepareGL() {
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(Float.intBitsToFloat(Float.floatToIntBits(5.0675106f) ^ 0x7F22290C));
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color(Float.intBitsToFloat(Float.floatToIntBits(11.925059f) ^ 0x7EBECD0B), Float.intBitsToFloat(Float.floatToIntBits(18.2283f) ^ 0x7E11D38F), Float.intBitsToFloat(Float.floatToIntBits(9.73656f) ^ 0x7E9BC8F3));
    }

    public static void begin(final int mode) {
        RenderUtils.INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
    }

    public static void release() {
        render();
        releaseGL();
    }

    public static void render() {
        RenderUtils.INSTANCE.draw();
    }

    public static void releaseGL() {
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        GlStateManager.color(Float.intBitsToFloat(Float.floatToIntBits(12.552789f) ^ 0x7EC8D839), Float.intBitsToFloat(Float.floatToIntBits(7.122752f) ^ 0x7F63ED96), Float.intBitsToFloat(Float.floatToIntBits(5.4278784f) ^ 0x7F2DB12E));
        GL11.glColor4f(Float.intBitsToFloat(Float.floatToIntBits(10.5715685f) ^ 0x7EA92525), Float.intBitsToFloat(Float.floatToIntBits(4.9474883f) ^ 0x7F1E51D3), Float.intBitsToFloat(Float.floatToIntBits(4.9044757f) ^ 0x7F1CF177), Float.intBitsToFloat(Float.floatToIntBits(9.482457f) ^ 0x7E97B825));
    }

    public static void drawOpenGradientBox(final BlockPos pos, final Color startColor, final Color endColor, final double height) {
        for (final EnumFacing face : EnumFacing.values()) {
            if (face != EnumFacing.UP) {
                drawGradientPlane(pos, face, startColor, endColor, height);
            }
        }
    }

    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - RenderUtils.mc.getRenderManager().viewerPosX, bb.minY - RenderUtils.mc.getRenderManager().viewerPosY, bb.minZ - RenderUtils.mc.getRenderManager().viewerPosZ, bb.maxX - RenderUtils.mc.getRenderManager().viewerPosX, bb.maxY - RenderUtils.mc.getRenderManager().viewerPosY, bb.maxZ - RenderUtils.mc.getRenderManager().viewerPosZ);
    }

    public static void drawGradientPlane(final BlockPos pos, final EnumFacing face, final Color startColor, final Color endColor, final boolean half, final boolean top) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();
        final IBlockState iblockstate = RenderUtils.mc.world.getBlockState(pos);
        final Vec3d interp = EntityUtils.interpolateEntity((Entity)RenderUtils.mc.player, RenderUtils.mc.getRenderPartialTicks());
        final AxisAlignedBB bb = iblockstate.getSelectedBoundingBox((World)RenderUtils.mc.world, pos).grow(Double.longBitsToDouble(Double.doubleToLongBits(7737.873906549376) ^ 0x7FDE5B925856F155L)).offset(-interp.x, -interp.y, -interp.z);
        final float red = startColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.011933815f) ^ 0x7F3C860C);
        final float green = startColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.014238123f) ^ 0x7F164704);
        final float blue = startColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.04742649f) ^ 0x7E3D4247);
        final float alpha = startColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.008362556f) ^ 0x7F76031A);
        final float red2 = endColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.10421128f) ^ 0x7EAA6CB9);
        final float green2 = endColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.08333851f) ^ 0x7ED5AD61);
        final float blue2 = endColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.0144527145f) ^ 0x7F13CB14);
        final float alpha2 = endColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.012371935f) ^ 0x7F35B3A8);
        double x1 = Double.longBitsToDouble(Double.doubleToLongBits(8.835131783660546E307) ^ 0x7FDF7440C3BC5D3DL);
        double y1 = Double.longBitsToDouble(Double.doubleToLongBits(1.118181430726592E308) ^ 0x7FE3E77F8F6B26DEL);
        double z1 = Double.longBitsToDouble(Double.doubleToLongBits(1.380140667303924E308) ^ 0x7FE8913BD76D7C97L);
        double x2 = Double.longBitsToDouble(Double.doubleToLongBits(4.752487534707832E307) ^ 0x7FD0EB5EEDAA85C9L);
        double y2 = Double.longBitsToDouble(Double.doubleToLongBits(9.887791724409538E306) ^ 0x7FAC294F41BA167FL);
        double z2 = Double.longBitsToDouble(Double.doubleToLongBits(5.35959611879942E307) ^ 0x7FD314AF0452AEC9L);
        if (face == EnumFacing.DOWN) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.minY + (top ? Double.longBitsToDouble(Double.doubleToLongBits(3.143369194728737) ^ 0x7FE9259EBF94E906L) : Double.longBitsToDouble(Double.doubleToLongBits(1.7424901971094587E308) ^ 0x7FEF04716219751FL));
            y2 = bb.minY + (top ? Double.longBitsToDouble(Double.doubleToLongBits(25.313526608979853) ^ 0x7FD9504347A3FE87L) : Double.longBitsToDouble(Double.doubleToLongBits(8.360703780749126E307) ^ 0x7FDDC3DD2A7954CDL));
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.UP) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.maxY / (half ? 2 : 1);
            y2 = bb.maxY / (half ? 2 : 1);
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.EAST) {
            x1 = bb.maxX;
            x2 = bb.maxX;
            y1 = bb.minY + (top ? Double.longBitsToDouble(Double.doubleToLongBits(18.522674392601733) ^ 0x7FD285CDFD2EAE5DL) : Double.longBitsToDouble(Double.doubleToLongBits(1.031120033212976E308) ^ 0x7FE25AC384422687L));
            y2 = bb.maxY / (half ? 2 : 1);
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.WEST) {
            x1 = bb.minX;
            x2 = bb.minX;
            y1 = bb.minY + (top ? Double.longBitsToDouble(Double.doubleToLongBits(3.6410682101818854) ^ 0x7FED20E85EA9E441L) : Double.longBitsToDouble(Double.doubleToLongBits(6.437474394910168E307) ^ 0x7FD6EB0D2975CFE9L));
            y2 = bb.maxY / (half ? 2 : 1);
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.SOUTH) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.minY + (top ? Double.longBitsToDouble(Double.doubleToLongBits(15.296728066590147) ^ 0x7FCE97ECBDBB9EB7L) : Double.longBitsToDouble(Double.doubleToLongBits(9.653964997422201E307) ^ 0x7FE12F43C9CE0278L));
            y2 = bb.maxY / (half ? 2 : 1);
            z1 = bb.maxZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.NORTH) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.minY + (top ? Double.longBitsToDouble(Double.doubleToLongBits(9.842466081175195) ^ 0x7FC3AF57B6D54603L) : Double.longBitsToDouble(Double.doubleToLongBits(1.5551384482214934E308) ^ 0x7FEBAEB0AB58F02FL));
            y2 = bb.maxY / (half ? 2 : 1);
            z1 = bb.minZ;
            z2 = bb.minZ;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
        builder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        Label_7222: {
            if (face != EnumFacing.EAST) {
                if (face != EnumFacing.WEST) {
                    if (face != EnumFacing.NORTH && face != EnumFacing.SOUTH) {
                        if (face == EnumFacing.UP) {
                            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
                            break Label_7222;
                        }
                        if (face == EnumFacing.DOWN) {
                            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
                            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
                        }
                        break Label_7222;
                    }
                }
            }
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
        }
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void drawGradientPlane(final BlockPos pos, final EnumFacing face, final Color startColor, final Color endColor, final double height) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();
        final IBlockState iblockstate = RenderUtils.mc.world.getBlockState(pos);
        final Vec3d interp = EntityUtils.interpolateEntity((Entity)RenderUtils.mc.player, RenderUtils.mc.getRenderPartialTicks());
        final AxisAlignedBB bb = iblockstate.getSelectedBoundingBox((World)RenderUtils.mc.world, pos).grow(Double.longBitsToDouble(Double.doubleToLongBits(3.0607151770573178) ^ 0x7F681E15DD1EA7FFL)).offset(-interp.x, -interp.y, -interp.z).expand(Double.longBitsToDouble(Double.doubleToLongBits(1.646911076243114E308) ^ 0x7FED50E4BCEAEB97L), height, Double.longBitsToDouble(Double.doubleToLongBits(9.969131849133298E307) ^ 0x7FE1BEE28202EE6BL));
        final float red = startColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.090864085f) ^ 0x7EC516F3);
        final float green = startColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.058850195f) ^ 0x7E0E0CE7);
        final float blue = startColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.04067875f) ^ 0x7E599EC3);
        final float alpha = startColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.06464929f) ^ 0x7EFB66D9);
        final float red2 = endColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.015392774f) ^ 0x7F0331F9);
        final float green2 = endColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.011544588f) ^ 0x7F422583);
        final float blue2 = endColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(1.1073645f) ^ 0x7CF2BE1F);
        final float alpha2 = endColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.012541848f) ^ 0x7F327C53);
        double x1 = Double.longBitsToDouble(Double.doubleToLongBits(1.3225864938154327E308) ^ 0x7FE78AF6523D93C9L);
        double y1 = Double.longBitsToDouble(Double.doubleToLongBits(1.3775115287059444E308) ^ 0x7FE88540BE04EA82L);
        double z1 = Double.longBitsToDouble(Double.doubleToLongBits(6.46021304761502E307) ^ 0x7FD6FFC67387116DL);
        double x2 = Double.longBitsToDouble(Double.doubleToLongBits(1.2678712548475257E308) ^ 0x7FE691A0A4D499AEL);
        double y2 = Double.longBitsToDouble(Double.doubleToLongBits(1.6277632430317775E308) ^ 0x7FECF9A344900BB7L);
        double z2 = Double.longBitsToDouble(Double.doubleToLongBits(8.302590426886019E307) ^ 0x7FDD8EE6656C79C7L);
        if (face == EnumFacing.DOWN) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.minY;
            y2 = bb.minY;
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.UP) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.maxY;
            y2 = bb.maxY;
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.EAST) {
            x1 = bb.maxX;
            x2 = bb.maxX;
            y1 = bb.minY;
            y2 = bb.maxY;
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.WEST) {
            x1 = bb.minX;
            x2 = bb.minX;
            y1 = bb.minY;
            y2 = bb.maxY;
            z1 = bb.minZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.SOUTH) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.minY;
            y2 = bb.maxY;
            z1 = bb.maxZ;
            z2 = bb.maxZ;
        }
        else if (face == EnumFacing.NORTH) {
            x1 = bb.minX;
            x2 = bb.maxX;
            y1 = bb.minY;
            y2 = bb.maxY;
            z1 = bb.minZ;
            z2 = bb.minZ;
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);
        builder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
        }
        else if (face == EnumFacing.UP) {
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
        }
        else if (face == EnumFacing.DOWN) {
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
        }
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = Float.intBitsToFloat(Float.floatToIntBits(43.059853f) ^ 0x7EF64945);
        GlStateManager.translate(x - Minecraft.getMinecraft().getRenderManager().viewerPosX, y - Minecraft.getMinecraft().getRenderManager().viewerPosY, z - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
        GlStateManager.glNormal3f(Float.intBitsToFloat(Float.floatToIntBits(1.8430962E38f) ^ 0x7F0AA8BE), Float.intBitsToFloat(Float.floatToIntBits(4.1650653f) ^ 0x7F054837), Float.intBitsToFloat(Float.floatToIntBits(2.7096567E38f) ^ 0x7F4BDA17));
        GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationYaw, Float.intBitsToFloat(Float.floatToIntBits(7.285461E36f) ^ 0x7CAF641F), Float.intBitsToFloat(Float.floatToIntBits(7.6383615f) ^ 0x7F746D75), Float.intBitsToFloat(Float.floatToIntBits(3.8722768E37f) ^ 0x7DE90DDF));
        GlStateManager.rotate(Minecraft.getMinecraft().player.rotationPitch, (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) ? Float.intBitsToFloat(Float.floatToIntBits(-7.747731f) ^ 0x7F77ED6A) : Float.intBitsToFloat(Float.floatToIntBits(7.322496f) ^ 0x7F6A51E3), Float.intBitsToFloat(Float.floatToIntBits(8.078122E37f) ^ 0x7E731797), Float.intBitsToFloat(Float.floatToIntBits(3.2507196E38f) ^ 0x7F748E95));
        GlStateManager.scale(-scale, -scale, scale);
    }

    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / Float.intBitsToFloat(Float.floatToIntBits(0.39821896f) ^ 0x7ECBE35B) / (Float.intBitsToFloat(Float.floatToIntBits(0.05389839f) ^ 0x7D5CC48F) + (Float.intBitsToFloat(Float.floatToIntBits(0.22974841f) ^ 0x7E6B432B) - scale));
        if (scaleDistance < Float.intBitsToFloat(Float.floatToIntBits(7.6779084f) ^ 0x7F75B16D)) {
            scaleDistance = Float.intBitsToFloat(Float.floatToIntBits(9.369836f) ^ 0x7E95EAD9);
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }

    public static void drawBox(final BlockPos blockPos, final int argb, final int sides) {
        final int a = argb >>> 24 & 0xFF;
        final int r = argb >>> 16 & 0xFF;
        final int g = argb >>> 8 & 0xFF;
        final int b = argb & 0xFF;
        drawBox(blockPos, r, g, b, a, sides);
    }

    public static void drawBox(final BlockPos blockPos, final int r, final int g, final int b, final int a, final int sides) {
        drawBox(RenderUtils.INSTANCE.getBuffer(), (float)blockPos.getX(), (float)blockPos.getY(), (float)blockPos.getZ(), Float.intBitsToFloat(Float.floatToIntBits(10.001433f) ^ 0x7EA005DF), Float.intBitsToFloat(Float.floatToIntBits(14.253013f) ^ 0x7EE40C57), Float.intBitsToFloat(Float.floatToIntBits(4.310877f) ^ 0x7F09F2B4), r, g, b, a, sides);
    }

    public static void drawBox(final BufferBuilder buffer, final float x, final float y, final float z, final float w, final float h, final float d, final int r, final int g, final int b, final int a, final int sides) {
        if ((sides & 0x1) != 0x0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x2) != 0x0) {
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x4) != 0x0) {
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x8) != 0x0) {
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x10) != 0x0) {
            buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
        }
        if ((sides & 0x20) != 0x0) {
            buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
            buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
        }
    }

    public static void drawBoundingBoxBlockPos(final BlockPos bp, final float width, final int r, final int g, final int b, final int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        final Minecraft mc = Minecraft.getMinecraft();
        final double x = bp.getX() - mc.getRenderManager().viewerPosX;
        final double y = bp.getY() - mc.getRenderManager().viewerPosY;
        final double z = bp.getZ() - mc.getRenderManager().viewerPosZ;
        final AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + Double.longBitsToDouble(Double.doubleToLongBits(5.904661419426285) ^ 0x7FE79E5F90298E2AL), y + Double.longBitsToDouble(Double.doubleToLongBits(15.500682791548558) ^ 0x7FDF00597EAEBDF9L), z + Double.longBitsToDouble(Double.doubleToLongBits(384.3294781497497) ^ 0x7F8805458AE15EBFL));
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawGradientBlockOutline(final BlockPos pos, final Color startColor, final Color endColor, final float linewidth) {
        final IBlockState iblockstate = RenderUtils.mc.world.getBlockState(pos);
        final Vec3d interp = EntityUtils.interpolateEntity((Entity)RenderUtils.mc.player, RenderUtils.mc.getRenderPartialTicks());
        drawGradientBlockOutline(iblockstate.getSelectedBoundingBox((World)RenderUtils.mc.world, pos).grow(Double.longBitsToDouble(Double.doubleToLongBits(5140.135716816253) ^ 0x7FD4766F5E565753L)).offset(-interp.x, -interp.y, -interp.z), startColor, endColor, linewidth);
    }

    public static void drawGradientBlockOutline(final AxisAlignedBB bb, final Color startColor, final Color endColor, final float linewidth) {
        final float red = startColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.009788291f) ^ 0x7F5F5F11);
        final float green = startColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.23705815f) ^ 0x7D0DBF5F);
        final float blue = startColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.29198143f) ^ 0x7DEA7E97);
        final float alpha = startColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.012616771f) ^ 0x7F31B693);
        final float red2 = endColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.012736047f) ^ 0x7F2FAADA);
        final float green2 = endColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.010022703f) ^ 0x7F5B3643);
        final float blue2 = endColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.01259957f) ^ 0x7F316E6D);
        final float alpha2 = endColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.011501952f) ^ 0x7F4372AF);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red2, green2, blue2, alpha2).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoxESP(final BlockPos pos, final Color color, final int boxAlpha) {
        drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha));
    }

    public static void drawBox(final BlockPos pos, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtils.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtils.mc.getRenderManager().viewerPosZ);
        RenderUtils.camera.setPosition(Objects.requireNonNull(RenderUtils.mc.getRenderViewEntity()).posX, RenderUtils.mc.getRenderViewEntity().posY, RenderUtils.mc.getRenderViewEntity().posZ);
        if (RenderUtils.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtils.mc.getRenderManager().viewerPosX, bb.minY + RenderUtils.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtils.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtils.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtils.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtils.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.010107363f) ^ 0x7F5A995A), color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.011656278f) ^ 0x7F41F9F9), color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.015351498f) ^ 0x7F0484D9), color.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.012585007f) ^ 0x7F313158));
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static void drawBoxESP(final BlockPos pos, final Color color, final float lineWidth, final boolean outline, final boolean box, final int boxAlpha, final boolean air, final double height, final boolean gradientBox) {
        if (box) {
            drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha), height, gradientBox, boxAlpha);
        }
        if (outline) {
            drawBlockOutline(pos, color, lineWidth, air, height);
        }
    }

    public static void drawBlockOutline(final BlockPos pos, final Color color, final float linewidth, final boolean air, final double height) {
        final IBlockState iblockstate = RenderUtils.mc.world.getBlockState(pos);
        if (!air) {
            if (iblockstate.getMaterial() == Material.AIR) {
                return;
            }
        }
        if (RenderUtils.mc.world.getWorldBorder().contains(pos)) {
            final AxisAlignedBB blockAxis = new AxisAlignedBB(pos.getX() - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtils.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtils.mc.getRenderManager().viewerPosY + height, pos.getZ() + 1 - RenderUtils.mc.getRenderManager().viewerPosZ);
            drawBlockOutline(blockAxis.grow(Double.longBitsToDouble(Double.doubleToLongBits(3177.4888695024906) ^ 0x7FC8B0B7AD1A7A6BL)), color, linewidth);
        }
    }

    public static void drawBlockOutline(final AxisAlignedBB bb, final Color color, final float linewidth) {
        final float red = color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.010800879f) ^ 0x7F4FF62C);
        final float green = color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.013595752f) ^ 0x7F21C0B8);
        final float blue = color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.014829914f) ^ 0x7F0DF92B);
        final float alpha = Float.intBitsToFloat(Float.floatToIntBits(5.635761f) ^ 0x7F345827);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawColoredFlatBox(final BlockPos pos, final float linewidth, final boolean air, final double height) {
        final IBlockState iblockstate = RenderUtils.mc.world.getBlockState(pos);
        if (!air) {
            if (iblockstate.getMaterial() == Material.AIR) {
                return;
            }
        }
        if (RenderUtils.mc.world.getWorldBorder().contains(pos)) {
            final AxisAlignedBB blockAxis = new AxisAlignedBB(pos.getX() - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtils.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtils.mc.getRenderManager().viewerPosY + height, pos.getZ() + 1 - RenderUtils.mc.getRenderManager().viewerPosZ);
            drawColoredFlatBox(blockAxis.grow(Double.longBitsToDouble(Double.doubleToLongBits(5482.148565022259) ^ 0x7FD5086BE85B77EFL)), linewidth);
        }
    }

    public static void drawColoredFlatBox(final AxisAlignedBB bb, final float linewidth) {
        final float alpha = Float.intBitsToFloat(Float.floatToIntBits(13.809779f) ^ 0x7EDCF4DB);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(linewidth);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(Float.intBitsToFloat(Float.floatToIntBits(5.1694365f) ^ 0x7F256C06), Float.intBitsToFloat(Float.floatToIntBits(2.478717E38f) ^ 0x7F3A7A5A), Float.intBitsToFloat(Float.floatToIntBits(1.9199145E37f) ^ 0x7D6719EF), alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(Float.intBitsToFloat(Float.floatToIntBits(1.5403378E38f) ^ 0x7EE7C3A7), Float.intBitsToFloat(Float.floatToIntBits(13.219632f) ^ 0x7ED3839D), Float.intBitsToFloat(Float.floatToIntBits(2.724206E38f) ^ 0x7F4CF24D), alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(Float.intBitsToFloat(Float.floatToIntBits(2.3238642E38f) ^ 0x7F2ED3FF), Float.intBitsToFloat(Float.floatToIntBits(2.9685533E38f) ^ 0x7F5F5442), Float.intBitsToFloat(Float.floatToIntBits(4.9496536f) ^ 0x7F1E6390), alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(Float.intBitsToFloat(Float.floatToIntBits(7.2860913f) ^ 0x7F6927A9), Float.intBitsToFloat(Float.floatToIntBits(78.59313f) ^ 0x7D1D2FAF), Float.intBitsToFloat(Float.floatToIntBits(4.597998f) ^ 0x7F1322CD), alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(Float.intBitsToFloat(Float.floatToIntBits(4.738372f) ^ 0x7F17A0BE), Float.intBitsToFloat(Float.floatToIntBits(2.5195168E38f) ^ 0x7F3D8C21), Float.intBitsToFloat(Float.floatToIntBits(2.272865E38f) ^ 0x7F2AFDCA), alpha).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBox(final BlockPos pos, final Color color, final double height, final boolean gradient, final int alpha) {
        if (gradient) {
            final Color endColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
            drawOpenGradientBox(pos, color, endColor, height);
            return;
        }
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtils.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtils.mc.getRenderManager().viewerPosY + height, pos.getZ() + 1 - RenderUtils.mc.getRenderManager().viewerPosZ);
        RenderUtils.camera.setPosition(Objects.requireNonNull(RenderUtils.mc.getRenderViewEntity()).posX, RenderUtils.mc.getRenderViewEntity().posY, RenderUtils.mc.getRenderViewEntity().posZ);
        if (RenderUtils.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtils.mc.getRenderManager().viewerPosX, bb.minY + RenderUtils.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtils.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtils.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtils.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtils.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.079023026f) ^ 0x7EDED6D3), color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.120759256f) ^ 0x7E8850A1), color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.091729276f) ^ 0x7EC4DC8F), color.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.013379525f) ^ 0x7F2435CC));
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static void renderCrosses(final BlockPos pos, final Color color, final float lineWidth) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtils.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtils.mc.getRenderManager().viewerPosZ);
        RenderUtils.camera.setPosition(Objects.requireNonNull(RenderUtils.mc.getRenderViewEntity()).posX, RenderUtils.mc.getRenderViewEntity().posY, RenderUtils.mc.getRenderViewEntity().posZ);
        if (RenderUtils.camera.isBoundingBoxInFrustum(new AxisAlignedBB(pos))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(lineWidth);
            renderCrosses(bb, color);
            GL11.glDisable(2848);
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static void renderCrosses(final AxisAlignedBB bb, final Color color) {
        final int hex = color.getRGB();
        final float red = (hex >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.010141646f) ^ 0x7F592925);
        final float green = (hex >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.09402426f) ^ 0x7EBF8FCB);
        final float blue = (hex & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.01439369f) ^ 0x7F14D383);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, Float.intBitsToFloat(Float.floatToIntBits(6.2025056f) ^ 0x7F467AED)).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, Float.intBitsToFloat(Float.floatToIntBits(13.170365f) ^ 0x7ED2B9D1)).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, Float.intBitsToFloat(Float.floatToIntBits(12.665132f) ^ 0x7ECAA461)).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, Float.intBitsToFloat(Float.floatToIntBits(6.132548f) ^ 0x7F443DD5)).endVertex();
        tessellator.draw();
    }

    public static void drawOutlineLine(double left, double top, double right, double bottom, final float width, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        if (left < right) {
            final double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float a = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.0137258265f) ^ 0x7F1FE24A);
        final float r = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.01309684f) ^ 0x7F299421);
        final float g = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.010762423f) ^ 0x7F4F54E0);
        final float b = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.014254631f) ^ 0x7F168C41);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(left, bottom, Double.longBitsToDouble(Double.doubleToLongBits(1.218645285212023E308) ^ 0x7FE5B14EA2BC5296L)).color(r, g, b, a).endVertex();
        bufferbuilder.pos(right, bottom, Double.longBitsToDouble(Double.doubleToLongBits(1.3868782241790337E308) ^ 0x7FE8AFEFBCC64C92L)).color(r, g, b, a).endVertex();
        bufferbuilder.pos(right, top, Double.longBitsToDouble(Double.doubleToLongBits(1.7327740273664738E308) ^ 0x7FEED82AB2D3399EL)).color(r, g, b, a).endVertex();
        bufferbuilder.pos(left, top, Double.longBitsToDouble(Double.doubleToLongBits(4.724280987430946E307) ^ 0x7FD0D1A9E46D0C4DL)).color(r, g, b, a).endVertex();
        bufferbuilder.pos(left, bottom, Double.longBitsToDouble(Double.doubleToLongBits(1.6226425550407737E308) ^ 0x7FECE24D93FC492CL)).color(r, g, b, a).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawRectrgb(final float x, final float y, final float w, final float h, final float r, final float g, final float b, final float a) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, Double.longBitsToDouble(Double.doubleToLongBits(9.873126907619453E307) ^ 0x7FE19322C499921BL)).color(r / Float.intBitsToFloat(Float.floatToIntBits(0.008758856f) ^ 0x7F70814E), g / Float.intBitsToFloat(Float.floatToIntBits(0.011129295f) ^ 0x7F4957A6), b / Float.intBitsToFloat(Float.floatToIntBits(0.08990056f) ^ 0x7EC71DC9), a / Float.intBitsToFloat(Float.floatToIntBits(0.118046276f) ^ 0x7E8EC23F)).endVertex();
        bufferbuilder.pos((double)w, (double)h, Double.longBitsToDouble(Double.doubleToLongBits(1.6387745625643133E308) ^ 0x7FED2BD0D903AB8AL)).color(r / Float.intBitsToFloat(Float.floatToIntBits(0.100699f) ^ 0x7EB13B47), g / Float.intBitsToFloat(Float.floatToIntBits(0.01207173f) ^ 0x7F3AC881), b / Float.intBitsToFloat(Float.floatToIntBits(0.080900006f) ^ 0x7EDAAEE7), a / Float.intBitsToFloat(Float.floatToIntBits(0.05701689f) ^ 0x7E168A8B)).endVertex();
        bufferbuilder.pos((double)w, (double)y, Double.longBitsToDouble(Double.doubleToLongBits(4.354100540066609E306) ^ 0x7F98CD41733C5BDFL)).color(r / Float.intBitsToFloat(Float.floatToIntBits(0.013578997f) ^ 0x7F217A71), g / Float.intBitsToFloat(Float.floatToIntBits(0.04730652f) ^ 0x7E3EC47B), b / Float.intBitsToFloat(Float.floatToIntBits(0.010899194f) ^ 0x7F4D9288), a / Float.intBitsToFloat(Float.floatToIntBits(0.0918661f) ^ 0x7EC3244B)).endVertex();
        bufferbuilder.pos((double)x, (double)y, Double.longBitsToDouble(Double.doubleToLongBits(2.8165720646742964E307) ^ 0x7FC40DFE1E51331BL)).color(r / Float.intBitsToFloat(Float.floatToIntBits(0.035969656f) ^ 0x7E6C54EB), g / Float.intBitsToFloat(Float.floatToIntBits(0.012732537f) ^ 0x7F2F9C22), b / Float.intBitsToFloat(Float.floatToIntBits(0.110038914f) ^ 0x7E9E5C15), a / Float.intBitsToFloat(Float.floatToIntBits(0.013974584f) ^ 0x7F1BF5A7)).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(final float x, final float y, final float width, final float height, final int rgb) {
        final Color color = new Color(rgb);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)x, (double)height, Double.longBitsToDouble(Double.doubleToLongBits(1.7440275154218355E307) ^ 0x7FB8D5F56E0824BFL)).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos((double)width, (double)height, Double.longBitsToDouble(Double.doubleToLongBits(8.970041210315517E307) ^ 0x7FDFEF35478E5423L)).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos((double)width, (double)y, Double.longBitsToDouble(Double.doubleToLongBits(1.1109098278862572E308) ^ 0x7FE3C65CA841DB5AL)).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferBuilder.pos((double)x, (double)y, Double.longBitsToDouble(Double.doubleToLongBits(1.6778616876832634E308) ^ 0x7FEDDDEF166601BDL)).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawOutline(final float x, final float y, final float width, final float height, final float lineWidth, final int color) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        drawRect(x + lineWidth, y, x - lineWidth, y + lineWidth, color);
        drawRect(x + lineWidth, y, width - lineWidth, y + lineWidth, color);
        drawRect(x, y, x + lineWidth, height, color);
        drawRect(width - lineWidth, y, width, height, color);
        drawRect(x + lineWidth, height - lineWidth, width - lineWidth, height, color);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawRecta(float x, float y, final float w, final float h, final int color) {
        float p_drawRect_2_ = x + w;
        float p_drawRect_3_ = y + h;
        if (x < p_drawRect_2_) {
            final float lvt_5_2_ = x;
            x = p_drawRect_2_;
            p_drawRect_2_ = lvt_5_2_;
        }
        if (y < p_drawRect_3_) {
            final float lvt_5_2_ = y;
            y = p_drawRect_3_;
            p_drawRect_3_ = lvt_5_2_;
        }
        final float lvt_5_3_ = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.057955842f) ^ 0x7E12631B);
        final float lvt_6_1_ = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.070744775f) ^ 0x7EEFE2A3);
        final float lvt_7_1_ = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.08584332f) ^ 0x7ED0CE9F);
        final float lvt_8_1_ = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.6575088f) ^ 0x7C57527F);
        final Tessellator lvt_9_1_ = Tessellator.getInstance();
        final BufferBuilder lvt_10_1_ = lvt_9_1_.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(lvt_6_1_, lvt_7_1_, lvt_8_1_, lvt_5_3_);
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION);
        lvt_10_1_.pos((double)x, (double)p_drawRect_3_, Double.longBitsToDouble(Double.doubleToLongBits(1.1570676508599334E307) ^ 0x7FB07A292487A177L)).endVertex();
        lvt_10_1_.pos((double)p_drawRect_2_, (double)p_drawRect_3_, Double.longBitsToDouble(Double.doubleToLongBits(1.36127169834824E308) ^ 0x7FE83B3FB04F2CFDL)).endVertex();
        lvt_10_1_.pos((double)p_drawRect_2_, (double)y, Double.longBitsToDouble(Double.doubleToLongBits(1.5915725433131406E308) ^ 0x7FEC54B7F0485181L)).endVertex();
        lvt_10_1_.pos((double)x, (double)y, Double.longBitsToDouble(Double.doubleToLongBits(1.3041426104764812E308) ^ 0x7FE736EA108B0F77L)).endVertex();
        lvt_9_1_.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawDoubleRect(double left, double top, double right, double bottom, final int color) {
        if (left < right) {
            final double j = left;
            left = right;
            right = j;
        }
        if (top < bottom) {
            final double j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.008837141f) ^ 0x7F6FC9A8);
        final float f4 = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.24967287f) ^ 0x7D00AA3F);
        final float f5 = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.08944767f) ^ 0x7EC83057);
        final float f6 = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.063480444f) ^ 0x7EFD0209);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, Double.longBitsToDouble(Double.doubleToLongBits(5.792164346573969E307) ^ 0x7FD49EEC10737D75L)).endVertex();
        bufferbuilder.pos(right, bottom, Double.longBitsToDouble(Double.doubleToLongBits(1.3053260674455657E308) ^ 0x7FE73C4EA94A3489L)).endVertex();
        bufferbuilder.pos(right, top, Double.longBitsToDouble(Double.doubleToLongBits(9.192985021720132E307) ^ 0x7FE05D32CEEB3EB4L)).endVertex();
        bufferbuilder.pos(left, top, Double.longBitsToDouble(Double.doubleToLongBits(1.5975435153920324E308) ^ 0x7FEC6FED8D67B87BL)).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSelectionBoundingBox(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawCircle(final float x, final float y, final float radius, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), getAlphaFromHex(color));
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)(x + MathHelper.sin(i * Float.intBitsToFloat(Float.floatToIntBits(0.30120212f) ^ 0x7ED338F1) / Float.intBitsToFloat(Float.floatToIntBits(0.07748035f) ^ 0x7EAAAE05)) * radius), (double)(y + MathHelper.cos(i * Float.intBitsToFloat(Float.floatToIntBits(0.6391426f) ^ 0x7F6A9102) / Float.intBitsToFloat(Float.floatToIntBits(0.011713186f) ^ 0x7F0BE8AA)) * radius));
        }
        GL11.glColor4d(Double.longBitsToDouble(Double.doubleToLongBits(53.67714277800314) ^ 0x7FBAD6AC9D531F7FL), Double.longBitsToDouble(Double.doubleToLongBits(501.6963415926147) ^ 0x7F8F5B243714F1FFL), Double.longBitsToDouble(Double.doubleToLongBits(6.706577014744621) ^ 0x7FEAD388ECC9BBDCL), Double.longBitsToDouble(Double.doubleToLongBits(14.139351235344405) ^ 0x7FDC47590B8CEC3FL));
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static double getAlphaFromHex(final int color) {
        return (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.008373946f) ^ 0x7F7632E0);
    }

    public static double getRedFromHex(final int color) {
        return (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.032228123f) ^ 0x7E7B01A3);
    }

    public static double getGreenFromHex(final int color) {
        return (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.0140558975f) ^ 0x7F194AB5);
    }

    public static double getBlueFromHex(final int color) {
        return (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.009096651f) ^ 0x7F6A0A1F);
    }

    public static void drawFilledBox(final AxisAlignedBB bb, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        final float alpha = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.0121679185f) ^ 0x7F385BF3);
        final float red = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.009070697f) ^ 0x7F6B9D43);
        final float green = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.013924689f) ^ 0x7F1B2461);
        final float blue = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.067761265f) ^ 0x7EF5C66B);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawSolidBox() {
        drawSolidBox(RenderUtils.DEFAULT_AABB);
    }

    public static void drawSolidBox(final AxisAlignedBB bb) {
        GL11.glBegin(7);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
    }

    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int argb) {
        final int a = argb >>> 24 & 0xFF;
        final int r = argb >>> 16 & 0xFF;
        final int g = argb >>> 8 & 0xFF;
        final int b = argb & 0xFF;
        drawBoundingBox(bb, width, r, g, b, a);
    }

    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int red, final int green, final int blue, final int alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.glLineWidth(width);
        final BufferBuilder bufferbuilder = RenderUtils.INSTANCE.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        render();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void GLPre(final float lineWidth) {
        RenderUtils.depth = GL11.glIsEnabled(2896);
        RenderUtils.texture = GL11.glIsEnabled(3042);
        RenderUtils.clean = GL11.glIsEnabled(3553);
        RenderUtils.bind = GL11.glIsEnabled(2929);
        RenderUtils.override = GL11.glIsEnabled(2848);
        GLPre(RenderUtils.depth, RenderUtils.texture, RenderUtils.clean, RenderUtils.bind, RenderUtils.override, lineWidth);
    }

    public static void GlPost() {
        GLPost(RenderUtils.depth, RenderUtils.texture, RenderUtils.clean, RenderUtils.bind, RenderUtils.override);
    }

    public static void GLPre(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override, final float lineWidth) {
        if (depth) {
            GL11.glDisable(2896);
        }
        if (!texture) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(lineWidth);
        if (clean) {
            GL11.glDisable(3553);
        }
        if (bind) {
            GL11.glDisable(2929);
        }
        if (!override) {
            GL11.glEnable(2848);
        }
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
    }

    public static void GLPost(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override) {
        GlStateManager.depthMask(true);
        if (!override) {
            GL11.glDisable(2848);
        }
        if (bind) {
            GL11.glEnable(2929);
        }
        if (clean) {
            GL11.glEnable(3553);
        }
        if (!texture) {
            GL11.glDisable(3042);
        }
        if (depth) {
            GL11.glEnable(2896);
        }
    }

    public static void rainbowOutline(final int x, final int y, final float width, final float height, final float lineThick, final int sat, final int bri) {
        final int[] counterChing = { 1 };
        for (int i = 0; i < width / Float.intBitsToFloat(Float.floatToIntBits(0.29362026f) ^ 0x7E965565); ++i) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 50, sat, bri);
            drawRecta((float)(x + i), (float)y, (float)(i + 2), lineThick, rainbowCol.getRGB());
            final int[] array = counterChing;
            final int n = 0;
            ++array[n];
        }
        for (int d = 0; d < height / Float.intBitsToFloat(Float.floatToIntBits(0.1070069f) ^ 0x7DDB266F); ++d) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 50, sat, 255);
            drawRecta(x + width, (float)(y + d), lineThick, (float)(d + 2), rainbowCol.getRGB());
            final int[] array2 = counterChing;
            final int n2 = 0;
            ++array2[n2];
        }
        for (int c = 0; c < width / Float.intBitsToFloat(Float.floatToIntBits(0.8806751f) ^ 0x7F6173EC); ++c) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 50, sat, bri);
            drawRecta((float)(x + c), y + height + lineThick / Float.intBitsToFloat(Float.floatToIntBits(0.19170086f) ^ 0x7E444D3B), (float)(c + 2), lineThick, rainbowCol.getRGB());
            final int[] array3 = counterChing;
            final int n3 = 0;
            ++array3[n3];
        }
        for (int j = 0; j < height / Float.intBitsToFloat(Float.floatToIntBits(0.5944587f) ^ 0x7F182E72); ++j) {
            final Color rainbowCol = RainbowUtils.anyRainbowColor(counterChing[0] * 100, sat, bri);
            drawRecta((float)x, (float)(y + j), lineThick, (float)(j + 2), rainbowCol.getRGB());
            final int[] array4 = counterChing;
            final int n4 = 0;
            ++array4[n4];
        }
    }

    public static void renderEntity(final EntityPlayer entity, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (RenderUtils.mc.getRenderManager() == null) {
            return;
        }
        if (modelBase instanceof ModelPlayer) {
            final ModelPlayer modelPlayer = (ModelPlayer)modelBase;
            modelPlayer.bipedHeadwear.showModel = false;
            modelPlayer.bipedBodyWear.showModel = false;
            modelPlayer.bipedLeftLegwear.showModel = false;
            modelPlayer.bipedRightLegwear.showModel = false;
            modelPlayer.bipedLeftArmwear.showModel = false;
            modelPlayer.bipedRightArmwear.showModel = false;
        }
        final float partialTicks = RenderUtils.mc.getRenderPartialTicks();
        final double x = entity.posX - RenderUtils.mc.getRenderManager().viewerPosX;
        double y = entity.posY - RenderUtils.mc.getRenderManager().viewerPosY;
        final double z = entity.posZ - RenderUtils.mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            y -= Double.longBitsToDouble(Double.doubleToLongBits(13.45461363651511) ^ 0x7FEAE8C31E5A4BCAL);
        }
        renderLivingAt(x, y, z);
        prepareRotations((EntityLivingBase)entity);
        final float f4 = prepareScale((EntityLivingBase)entity, scale);
        final float yaw = handleRotationFloat((EntityLivingBase)entity, partialTicks);
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations((EntityLivingBase)entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, Float.intBitsToFloat(Float.floatToIntBits(1.1669066E38f) ^ 0x7EAF939B), yaw, entity.rotationPitch, f4, (Entity)entity);
        modelBase.render((Entity)entity, limbSwing, limbSwingAmount, Float.intBitsToFloat(Float.floatToIntBits(9.5078E37f) ^ 0x7E8F0EB7), yaw, entity.rotationPitch, f4);
        GlStateManager.popMatrix();
    }

    public static void renderLivingAt(final double x, final double y, final double z) {
        GlStateManager.translate((float)x, (float)y, (float)z);
    }

    public static float prepareScale(final EntityLivingBase entity, final float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(Float.intBitsToFloat(Float.floatToIntBits(-10.400136f) ^ 0x7EA666F5), Float.intBitsToFloat(Float.floatToIntBits(-9.592343f) ^ 0x7E997A3D), Float.intBitsToFloat(Float.floatToIntBits(23.436003f) ^ 0x7E3B7CEF));
        final double widthX = entity.getRenderBoundingBox().maxX - entity.getRenderBoundingBox().minX;
        final double widthZ = entity.getRenderBoundingBox().maxZ - entity.getRenderBoundingBox().minZ;
        GlStateManager.scale(scale + widthX, (double)(scale * entity.height), scale + widthZ);
        final float f = Float.intBitsToFloat(Float.floatToIntBits(65.08312f) ^ 0x7F022A8F);
        GlStateManager.translate(Float.intBitsToFloat(Float.floatToIntBits(3.3419943E38f) ^ 0x7F7B6C78), Float.intBitsToFloat(Float.floatToIntBits(-21.703114f) ^ 0x7E6DBF3F), Float.intBitsToFloat(Float.floatToIntBits(1.3743181E38f) ^ 0x7ECEC8CF));
        return Float.intBitsToFloat(Float.floatToIntBits(11.578338f) ^ 0x7CB940DF);
    }

    public static void prepareRotations(final EntityLivingBase entityLivingBase) {
        GlStateManager.rotate(Float.intBitsToFloat(Float.floatToIntBits(0.008832816f) ^ 0x7F24B784) - entityLivingBase.rotationYaw, Float.intBitsToFloat(Float.floatToIntBits(1.1144003E38f) ^ 0x7EA7AD23), Float.intBitsToFloat(Float.floatToIntBits(5.227671f) ^ 0x7F274915), Float.intBitsToFloat(Float.floatToIntBits(2.6400446E38f) ^ 0x7F469D69));
    }

    public static float handleRotationFloat(final EntityLivingBase livingBase, final float partialTicks) {
        return livingBase.rotationYawHead;
    }

    public static void drawSidewaysGradient(final float x, final float y, final float width, final float height, final Color color1, final Color color2, final int alpha1, final int alpha2) {
        prepareGL();
        GL11.glShadeModel(7425);
        GL11.glEnable(2848);
        GL11.glBegin(7);
        GL11.glColor4f(color1.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.093258716f) ^ 0x7EC1FE6D), color1.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.29362985f) ^ 0x7DE956A7), color1.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.011518868f) ^ 0x7F43B9A2), alpha1 / Float.intBitsToFloat(Float.floatToIntBits(0.089654006f) ^ 0x7EC89C85));
        GL11.glVertex2d((double)x, (double)y);
        GL11.glColor4f(color2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.013414165f) ^ 0x7F24C716), color2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(6.233341E-5f) ^ 0x7BFDB8FF), color2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.10631739f) ^ 0x7EA6BCEF), alpha2 / Float.intBitsToFloat(Float.floatToIntBits(0.093597986f) ^ 0x7EC0B04D));
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glColor4f(color2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.01181007f) ^ 0x7F3E7F06), color2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.0496864f) ^ 0x7E3483F7), color2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.07141397f) ^ 0x7EED417D), alpha2 / Float.intBitsToFloat(Float.floatToIntBits(0.014228609f) ^ 0x7F161F1D));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glColor4f(color1.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.012489689f) ^ 0x7F33A18D), color1.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.06379611f) ^ 0x7EFDA789), color1.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.012435985f) ^ 0x7F34C04D), alpha1 / Float.intBitsToFloat(Float.floatToIntBits(0.056789067f) ^ 0x7E179BA7));
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glEnd();
        releaseGL();
    }

    public static void drawCircle(final float x, final float y, final float z, final float radius, final Color color) {
        final BlockPos pos = new BlockPos((double)x, (double)y, (double)z);
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() - RenderUtils.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - RenderUtils.mc.getRenderManager().viewerPosX, pos.getY() + 1 - RenderUtils.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - RenderUtils.mc.getRenderManager().viewerPosZ);
        drawCircleVertices(bb, radius, color);
    }

    public static void drawCircleVertices(final AxisAlignedBB bb, final float radius, final Color color) {
        final float r = color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.06799387f) ^ 0x7EF4405F);
        final float g = color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.14126728f) ^ 0x7D6FA85F);
        final float b = color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.10135498f) ^ 0x7EB09333);
        final float a = color.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.10700496f) ^ 0x7EA4256B);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(Float.intBitsToFloat(Float.floatToIntBits(4.711665f) ^ 0x7F16C5F6));
        for (int i = 0; i < 360; ++i) {
            buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(bb.getCenter().x + Math.sin(i * Double.longBitsToDouble(Double.doubleToLongBits(0.5234292570199359) ^ 0x7FE99E15FB87AA04L) / Double.longBitsToDouble(Double.doubleToLongBits(0.008476520640446645) ^ 0x7FE7DC23577A08EAL)) * radius, bb.minY, bb.getCenter().z + Math.cos(i * Double.longBitsToDouble(Double.doubleToLongBits(0.4296392219933493) ^ 0x7FD25ECECCF04CDDL) / Double.longBitsToDouble(Double.doubleToLongBits(0.3261844210137585) ^ 0x7FB260349F2E009FL)) * radius).color(r, g, b, a).endVertex();
            buffer.pos(bb.getCenter().x + Math.sin((i + 1) * Double.longBitsToDouble(Double.doubleToLongBits(0.6451235577936375) ^ 0x7FED852165C1923FL) / Double.longBitsToDouble(Double.doubleToLongBits(0.05192483434506725) ^ 0x7FCC15E452B97CF3L)) * radius, bb.minY, bb.getCenter().z + Math.cos((i + 1) * Double.longBitsToDouble(Double.doubleToLongBits(0.3999371395001485) ^ 0x7FD0B96ABCB420E1L) / Double.longBitsToDouble(Double.doubleToLongBits(0.015224943326098275) ^ 0x7FE9AE414D5A348EL)) * radius).color(r, g, b, a).endVertex();
            tessellator.draw();
        }
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawEntityOnScreen(final Entity ent, final int posX, final int posY, final int scale, final float mouseX, final float mouseY) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, Float.intBitsToFloat(Float.floatToIntBits(1.6824397f) ^ 0x7D9F5A2F));
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(Float.intBitsToFloat(Float.floatToIntBits(0.010010559f) ^ 0x7F100354), Float.intBitsToFloat(Float.floatToIntBits(3.2874774E38f) ^ 0x7F775283), Float.intBitsToFloat(Float.floatToIntBits(1.277499E38f) ^ 0x7EC03779), Float.intBitsToFloat(Float.floatToIntBits(7.556555f) ^ 0x7F71CF4C));
        GlStateManager.rotate(Float.intBitsToFloat(Float.floatToIntBits(0.08215728f) ^ 0x7EAF4213), Float.intBitsToFloat(Float.floatToIntBits(3.2189796E38f) ^ 0x7F722B4B), Float.intBitsToFloat(Float.floatToIntBits(11.650432f) ^ 0x7EBA682B), Float.intBitsToFloat(Float.floatToIntBits(1.8456703E38f) ^ 0x7F0ADA51));
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(Float.intBitsToFloat(Float.floatToIntBits(-0.07293611f) ^ 0x7E925F87), Float.intBitsToFloat(Float.floatToIntBits(4.4185975E37f) ^ 0x7E04F7A3), Float.intBitsToFloat(Float.floatToIntBits(7.4331884f) ^ 0x7F6DDCAE), Float.intBitsToFloat(Float.floatToIntBits(1.0855388E38f) ^ 0x7EA3556F));
        GlStateManager.rotate(-(float)Math.atan(mouseY / Float.intBitsToFloat(Float.floatToIntBits(0.04850099f) ^ 0x7F66A8F9)) * Float.intBitsToFloat(Float.floatToIntBits(0.45494023f) ^ 0x7F48EDED), Float.intBitsToFloat(Float.floatToIntBits(7.337801f) ^ 0x7F6ACF44), Float.intBitsToFloat(Float.floatToIntBits(5.3653236E37f) ^ 0x7E2174F3), Float.intBitsToFloat(Float.floatToIntBits(2.4617955E38f) ^ 0x7F393475));
        GlStateManager.translate(Float.intBitsToFloat(Float.floatToIntBits(9.835158E37f) ^ 0x7E93FBA7), Float.intBitsToFloat(Float.floatToIntBits(2.4084867E38f) ^ 0x7F3531C4), Float.intBitsToFloat(Float.floatToIntBits(2.8240162E38f) ^ 0x7F547493));
        final RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(Float.intBitsToFloat(Float.floatToIntBits(0.009158009f) ^ 0x7F220B79));
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, Double.longBitsToDouble(Double.doubleToLongBits(1.5829156308224974E308) ^ 0x7FEC2D44F635CC31L), Double.longBitsToDouble(Double.doubleToLongBits(1.7339437041438026E308) ^ 0x7FEEDD7F3831FB25L), Double.longBitsToDouble(Double.doubleToLongBits(8.225454125242748E307) ^ 0x7FDD4899452B1C5FL), Float.intBitsToFloat(Float.floatToIntBits(4.1008535E37f) ^ 0x7DF6CFA7), Float.intBitsToFloat(Float.floatToIntBits(4.240404f) ^ 0x7F07B164), false);
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static AxisAlignedBB fixBB(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - RenderUtils.mc.getRenderManager().viewerPosX, bb.minY - RenderUtils.mc.getRenderManager().viewerPosY, bb.minZ - RenderUtils.mc.getRenderManager().viewerPosZ, bb.maxX - RenderUtils.mc.getRenderManager().viewerPosX, bb.maxY - RenderUtils.mc.getRenderManager().viewerPosY, bb.maxZ - RenderUtils.mc.getRenderManager().viewerPosZ);
    }

    public static void drawOpenGradientBoxBB(final AxisAlignedBB bb, final Color startColor, final Color endColor, final boolean depth) {
        for (final EnumFacing face : EnumFacing.values()) {
            drawGradientPlaneBB(bb.grow(Double.longBitsToDouble(Double.doubleToLongBits(2433.56417635812) ^ 0x7FC3616D3BB93E9BL)), face, startColor, endColor, depth);
        }
    }

    public static void drawGradientPlaneBB(final AxisAlignedBB bb, final EnumFacing face, final Color startColor, final Color endColor, final boolean depth) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder builder = tessellator.getBuffer();
        final float red = startColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.27211377f) ^ 0x7DF4527F);
        final float green = startColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.12230372f) ^ 0x7E857A5F);
        final float blue = startColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.008009558f) ^ 0x7F7C3A85);
        final float alpha = startColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.013573141f) ^ 0x7F2161E1);
        final float red2 = endColor.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.015459908f) ^ 0x7F024B8E);
        final float green2 = endColor.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.007945256f) ^ 0x7F7D2CD2);
        final float blue2 = endColor.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.009713325f) ^ 0x7F6024A3);
        final float alpha2 = endColor.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.008760959f) ^ 0x7F708A20);
        double x1 = Double.longBitsToDouble(Double.doubleToLongBits(1.1084267949930563E308) ^ 0x7FE3BB0C007E4029L);
        double y1 = Double.longBitsToDouble(Double.doubleToLongBits(1.136153967114939E308) ^ 0x7FE43965F4022B39L);
        double z1 = Double.longBitsToDouble(Double.doubleToLongBits(4.3109616506545676E307) ^ 0x7FCEB1EFD5AC3A73L);
        double x2 = Double.longBitsToDouble(Double.doubleToLongBits(1.6087854571859715E308) ^ 0x7FECA3282BE4DCDFL);
        double y2 = Double.longBitsToDouble(Double.doubleToLongBits(1.3893593717110467E308) ^ 0x7FE8BB3E317C7857L);
        double z2 = Double.longBitsToDouble(Double.doubleToLongBits(1.5476281096126842E308) ^ 0x7FEB8C7743188780L);
        switch (face) {
            case DOWN: {
                x1 = bb.minX;
                x2 = bb.maxX;
                y1 = bb.minY;
                y2 = bb.minY;
                z1 = bb.minZ;
                z2 = bb.maxZ;
                break;
            }
            case UP: {
                x1 = bb.minX;
                x2 = bb.maxX;
                y1 = bb.maxY;
                y2 = bb.maxY;
                z1 = bb.minZ;
                z2 = bb.maxZ;
                break;
            }
            case EAST: {
                x1 = bb.maxX;
                x2 = bb.maxX;
                y1 = bb.minY;
                y2 = bb.maxY;
                z1 = bb.minZ;
                z2 = bb.maxZ;
                break;
            }
            case WEST: {
                x1 = bb.minX;
                x2 = bb.minX;
                y1 = bb.minY;
                y2 = bb.maxY;
                z1 = bb.minZ;
                z2 = bb.maxZ;
                break;
            }
            case SOUTH: {
                x1 = bb.minX;
                x2 = bb.maxX;
                y1 = bb.minY;
                y2 = bb.maxY;
                z1 = bb.maxZ;
                z2 = bb.maxZ;
                break;
            }
            case NORTH: {
                x1 = bb.minX;
                x2 = bb.maxX;
                y1 = bb.minY;
                y2 = bb.maxY;
                z1 = bb.minZ;
                z2 = bb.minZ;
                break;
            }
        }
        if (face != EnumFacing.DOWN) {
            if (face != EnumFacing.UP) {
                if (face != EnumFacing.EAST) {
                    if (face != EnumFacing.WEST) {
                        if (face != EnumFacing.SOUTH) {
                            if (face == EnumFacing.NORTH) {}
                        }
                    }
                }
            }
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        if (!depth) {
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
        }
        builder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
        }
        else if (face == EnumFacing.UP) {
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y1, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x1, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z1).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
            builder.pos(x2, y2, z2).color(red2, green2, blue2, alpha2).endVertex();
        }
        else if (face == EnumFacing.DOWN) {
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y1, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x1, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z1).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
            builder.pos(x2, y2, z2).color(red, green, blue, alpha).endVertex();
        }
        tessellator.draw();
        if (!depth) {
            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
        }
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawBoxBB(final AxisAlignedBB bb, final Color color) {
        RenderUtils.camera.setPosition(Objects.requireNonNull(RenderUtils.mc.getRenderViewEntity()).posX, RenderUtils.mc.getRenderViewEntity().posY, RenderUtils.mc.getRenderViewEntity().posZ);
        if (RenderUtils.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + RenderUtils.mc.getRenderManager().viewerPosX, bb.minY + RenderUtils.mc.getRenderManager().viewerPosY, bb.minZ + RenderUtils.mc.getRenderManager().viewerPosZ, bb.maxX + RenderUtils.mc.getRenderManager().viewerPosX, bb.maxY + RenderUtils.mc.getRenderManager().viewerPosY, bb.maxZ + RenderUtils.mc.getRenderManager().viewerPosZ))) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableTexture2D();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            RenderGlobal.renderFilledBox(bb, color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.015003811f) ^ 0x7F0AD28B), color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.008765428f) ^ 0x7F709CDF), color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.088410296f) ^ 0x7ECA1075), color.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.012969875f) ^ 0x7F2B7F99));
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public static void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    static {
        RenderUtils.camera = (ICamera)new Frustum();
        RenderUtils.frustrum = new Frustum();
        RenderUtils.INSTANCE = new RenderUtils();
        RenderUtils.DEFAULT_AABB = new AxisAlignedBB(Double.longBitsToDouble(Double.doubleToLongBits(1.4310172983923016E308) ^ 0x7FE979137A4093D9L), Double.longBitsToDouble(Double.doubleToLongBits(1.2283706187462381E308) ^ 0x7FE5DDA002B97AB6L), Double.longBitsToDouble(Double.doubleToLongBits(1.504232464556803E308) ^ 0x7FEAC6B6CA8AA20AL), Double.longBitsToDouble(Double.doubleToLongBits(100.00695490087308) ^ 0x7FA90071F2F7F2FFL), Double.longBitsToDouble(Double.doubleToLongBits(4.723666110916814) ^ 0x7FE2E508BA9E71E5L), Double.longBitsToDouble(Double.doubleToLongBits(2.1599342830113489E307) ^ 0x7FBEC22C15AE6577L));
    }
}
