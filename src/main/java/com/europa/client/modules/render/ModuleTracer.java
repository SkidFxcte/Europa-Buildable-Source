/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.Europa;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.EntityUtils;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ModuleTracer
extends Module {
    public static ValueNumber width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(77.08508086619962) ^ 0x7FA34571F70475EFL), Double.longBitsToDouble(Double.doubleToLongBits(83.31896804519248) ^ 0x7FED4DF0616B3D1DL), Double.longBitsToDouble(Double.doubleToLongBits(0.22711651757456694) ^ 0x7FD912276FAE9D43L));
    public static ValueNumber distance = new ValueNumber("Distance", "Distance", "", 300, 0, 300);
    public static ValueBoolean customColor = new ValueBoolean("CustomColor", "CustomColor", "", false);
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public Color color;
    public static ValueNumber alpha = new ValueNumber("Alpha", "Alpha", "", 255, 0, 255);

    public ModuleTracer() {
        super("Tracer", "Tracer", "Draws 2D lines to players.", ModuleCategory.RENDER);
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        this.color = syncColor.getValue() ? ModuleTracer.globalColor(255) : daColor.getValue();
        if (ModuleTracer.mc.player == null || ModuleTracer.mc.world == null) {
            return;
        }
        GlStateManager.pushMatrix();
        RenderUtils.prepareGL();
        GL11.glEnable((int)2848);
        ModuleTracer.mc.world.loadedEntityList.stream().filter(EntityUtils::isLiving).filter(this::lambda$onRender3D$0).forEach(this::lambda$onRender3D$1);
        GL11.glDisable((int)2848);
        GlStateManager.popMatrix();
        RenderUtils.releaseGL();
    }

    /*
     * WARNING - void declaration
     */
    public double interpolate(double d, double d2) {
        void now;
        void then;
        return (double)(then + (now - then) * (double)mc.getRenderPartialTicks());
    }

    /*
     * WARNING - void declaration
     */
    public double[] interpolate(Entity entity) {
        void entity2;
        double posX = this.interpolate(entity2.posX, entity2.lastTickPosX) - ModuleTracer.mc.getRenderManager().renderPosX;
        double posY = this.interpolate(entity2.posY, entity2.lastTickPosY) - ModuleTracer.mc.getRenderManager().renderPosY;
        double posZ = this.interpolate(entity2.posZ, entity2.lastTickPosZ) - ModuleTracer.mc.getRenderManager().renderPosZ;
        return new double[]{posX, posY, posZ};
    }

    /*
     * WARNING - void declaration
     */
    public void drawLineToEntity(Entity entity, float f, float f2, float f3, float f4) {
        void opacity;
        void blue;
        void green;
        void red;
        void e;
        double[] xyz = this.interpolate((Entity)e);
        this.drawLine(xyz[0], xyz[1], xyz[2], e.height, (float)red, (float)green, (float)blue, (float)opacity);
    }

    /*
     * WARNING - void declaration
     */
    public void drawLine(double d, double d2, double d3, double d4, float f, float f2, float f3, float f4) {
        void opacity;
        void blue;
        void green;
        void red;
        void up;
        void posz;
        void posy;
        void posx;
        Vec3d eyes = new Vec3d(Double.longBitsToDouble(Double.doubleToLongBits(6.140566268598193E307) ^ 0x7FD5DC73C61C8545L), Double.longBitsToDouble(Double.doubleToLongBits(3.921998520913766E307) ^ 0x7FCBECF14404A94FL), Double.longBitsToDouble(Double.doubleToLongBits(16.93179457663778) ^ 0x7FC0EE8A16E13FDBL)).rotatePitch(-((float)Math.toRadians(ModuleTracer.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(ModuleTracer.mc.player.rotationYaw)));
        this.drawLineFromPosToPos(eyes.x, eyes.y + (double)ModuleTracer.mc.player.getEyeHeight(), eyes.z, (double)posx, (double)posy, (double)posz, (double)up, (float)red, (float)green, (float)blue, (float)opacity);
    }

    /*
     * WARNING - void declaration
     */
    public void drawLineFromPosToPos(double d, double d2, double d3, double d4, double d5, double d6, double d7, float f, float f2, float f3, float f4) {
        void posz2;
        void posy2;
        void posx2;
        void posz;
        void posy;
        void posx;
        void opacity;
        void blue;
        void green;
        void red;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)((float)width.getValue().doubleValue()));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ModuleTracer.mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)Double.longBitsToDouble(Double.doubleToLongBits(182.50691981359975) ^ 0x7F96D038AFE6A35FL), (double)Double.longBitsToDouble(Double.doubleToLongBits(6.45737355790168) ^ 0x7FE9D459BBE4F8B5L), (double)Double.longBitsToDouble(Double.doubleToLongBits(7.257707946765154) ^ 0x7FED07E4978D1A43L));
        GlStateManager.enableLighting();
    }

    /*
     * WARNING - void declaration
     */
    public float[] getColorByDistance(Entity entity) {
        void entity2;
        if (entity2 instanceof EntityPlayer) {
            if (Europa.FRIEND_MANAGER.isFriend(entity2.getName())) {
                return new float[]{Float.intBitsToFloat(Float.floatToIntBits(3.0322054E38f) ^ 0x7F641E27), Float.intBitsToFloat(Float.floatToIntBits(19.037096f) ^ 0x7E984BF9), Float.intBitsToFloat(Float.floatToIntBits(7.059633f) ^ 0x7F61E883), Float.intBitsToFloat(Float.floatToIntBits(6.2702007f) ^ 0x7F48A57C)};
            }
        }
        if (customColor.getValue()) {
            return new float[]{(float)this.color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.102850236f) ^ 0x7EADA325), (float)this.color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.47068784f) ^ 0x7D8FFDFF), (float)this.color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.040353503f) ^ 0x7E5A49B7), (float)alpha.getValue().intValue() / Float.intBitsToFloat(Float.floatToIntBits(0.0156092085f) ^ 0x7F00BDC4)};
        }
        Color col = new Color(Color.HSBtoRGB((float)(Math.max(Double.longBitsToDouble(Double.doubleToLongBits(1.0634190405096188E308) ^ 0x7FE2EDF2E08DD27BL), Math.min(ModuleTracer.mc.player.getDistanceSq((Entity)entity2), Double.longBitsToDouble(Double.doubleToLongBits(7.353319370067243E-5) ^ 0x7FB0CEBAA6016D7FL)) / Double.longBitsToDouble(Double.doubleToLongBits(8.188375296731588E-4) ^ 0x7FE95CE834914820L)) / Double.longBitsToDouble(Double.doubleToLongBits(0.12973579243682173) ^ 0x7FC89B2EB4D18273L)), Float.intBitsToFloat(Float.floatToIntBits(14.886441f) ^ 0x7EEE2EDD), Float.intBitsToFloat(Float.floatToIntBits(62.456367f) ^ 0x7D351F9F)) | 0xFF000000);
        return new float[]{(float)col.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.010041873f) ^ 0x7F5B86AB), (float)col.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.047238704f) ^ 0x7E3E7D5F), (float)col.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.013810545f) ^ 0x7F1D45A0), (float)alpha.getValue().intValue() / Float.intBitsToFloat(Float.floatToIntBits(0.11432249f) ^ 0x7E9521E9)};
    }

    /*
     * WARNING - void declaration
     */
    public void lambda$onRender3D$1(Entity entity) {
        void entity2;
        float[] colour = this.getColorByDistance((Entity)entity2);
        this.drawLineToEntity((Entity)entity2, colour[0], colour[1], colour[2], colour[3]);
    }

    /*
     * WARNING - void declaration
     */
    public boolean lambda$onRender3D$0(Entity entity) {
        void entity2;
        return entity2 instanceof EntityPlayer && entity2 != ModuleTracer.mc.player;
    }
}

