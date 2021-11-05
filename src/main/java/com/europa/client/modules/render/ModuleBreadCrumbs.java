//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.modules.render;

import com.europa.client.minecraft.RenderManager;
import org.lwjgl.opengl.GL11;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.api.manager.event.impl.render.EventRender3D;
import java.util.Collection;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import com.europa.api.manager.module.ModuleCategory;
import java.util.ArrayList;
import java.awt.Color;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.module.Module;

public class ModuleBreadCrumbs extends Module
{
    public static ValueNumber length;
    public static ValueNumber width;
    public static ValueBoolean syncColor;
    public static ValueColor daColor;
    public Color color;
    public static ArrayList<double[]> vecs;

    public ModuleBreadCrumbs() {
        super("BreadCrumbs", "Bread Crumbs", "Makes a short line following players.", ModuleCategory.RENDER);
    }

    @Override
    public void onUpdate() {
        if (ModuleBreadCrumbs.syncColor.getValue()) {
            this.color = Module.globalColor(255);
        }
        else {
            this.color = ModuleBreadCrumbs.daColor.getValue();
        }
        try {
            final double renderPosX = RenderManager.renderPosX;
            final double renderPosY = RenderManager.renderPosY;
            final double renderPosZ = RenderManager.renderPosZ;
            if (this.isToggled()) {
                final Iterator<EntityPlayer> iterator = ModuleBreadCrumbs.mc.world.playerEntities.iterator();
                while (iterator.hasNext()) {
                    final EntityPlayer next;
                    if ((next = iterator.next()) instanceof EntityPlayer) {
                        final EntityPlayer entityPlayer;
                        final boolean b = (entityPlayer = next) == ModuleBreadCrumbs.mc.player;
                        double n = renderPosY + Double.longBitsToDouble(Double.doubleToLongBits(0.48965838138858014) ^ 0x7FDF56901B91AE07L);
                        if (ModuleBreadCrumbs.mc.player.isElytraFlying()) {
                            n -= Double.longBitsToDouble(Double.doubleToLongBits(29.56900080933637) ^ 0x7FC591AA097B7F4BL);
                        }
                        if (!b) {
                            continue;
                        }
                        ModuleBreadCrumbs.vecs.add(new double[] { renderPosX, n - entityPlayer.height, renderPosZ });
                    }
                }
            }
        }
        catch (Exception ex) {}
        if (ModuleBreadCrumbs.vecs.size() > ModuleBreadCrumbs.length.getValue().intValue()) {
            ModuleBreadCrumbs.vecs.remove(0);
        }
    }

    @Override
    public void onDisable() {
        ModuleBreadCrumbs.vecs.removeAll(ModuleBreadCrumbs.vecs);
    }

    public static double M(final double n) {
        if (n == Double.longBitsToDouble(Double.doubleToLongBits(1.7931000183463725E308) ^ 0x7FEFEB11C3AAD037L)) {
            return n;
        }
        if (n < Double.longBitsToDouble(Double.doubleToLongBits(1.1859585260803721E308) ^ 0x7FE51C5AEE8AD07FL)) {
            return n * Double.longBitsToDouble(Double.doubleToLongBits(-12.527781766526259) ^ 0x7FD90E3969654F8FL);
        }
        return n;
    }

    @Override
    public void onRender3D(final EventRender3D event) {
        try {
            final double renderPosX = RenderManager.renderPosX;
            final double renderPosY = RenderManager.renderPosY;
            final double renderPosZ = RenderManager.renderPosZ;
            final float n = this.color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.49987957f) ^ 0x7D80F037);
            final float n2 = this.color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.4340212f) ^ 0x7DA13807);
            final float n3 = this.color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.0131841665f) ^ 0x7F270267);
            if (this.isToggled()) {
                RenderUtils.prepareGL();
                GL11.glPushMatrix();
                GL11.glEnable(2848);
                GL11.glLineWidth(ModuleBreadCrumbs.width.getValue().floatValue());
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(ModuleBreadCrumbs.width.getValue().floatValue());
                GL11.glDepthMask(false);
                GL11.glBegin(3);
                Iterator<double[]> iterator3;
                final Iterator<double[]> iterator2 = iterator3 = ModuleBreadCrumbs.vecs.iterator();
                while (iterator3.hasNext()) {
                    final double[] array;
                    final double m;
                    if ((m = M(Math.hypot((array = iterator2.next())[0] - ModuleBreadCrumbs.mc.player.posX, array[1] - ModuleBreadCrumbs.mc.player.posY))) > ModuleBreadCrumbs.length.getValue().intValue()) {
                        iterator3 = iterator2;
                    }
                    else {
                        GL11.glColor4f(n, n2, n3, Float.intBitsToFloat(Float.floatToIntBits(14.099797f) ^ 0x7EE198C5) - (float)(m / ModuleBreadCrumbs.length.getValue().intValue()));
                        iterator3 = iterator2;
                        GL11.glVertex3d(array[0] - renderPosX, array[1] - renderPosY, array[2] - renderPosZ);
                    }
                }
                GL11.glEnd();
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
                RenderUtils.releaseGL();
            }
        }
        catch (Exception ex) {}
    }

    static {
        ModuleBreadCrumbs.length = new ValueNumber("Length", "Length", "", 14, 5, 40);
        ModuleBreadCrumbs.width = new ValueNumber("Width", "Width", "", Float.intBitsToFloat(Float.floatToIntBits(15.599429f) ^ 0x7EB99743), Float.intBitsToFloat(Float.floatToIntBits(2.076195f) ^ 0x7F04E061), Float.intBitsToFloat(Float.floatToIntBits(1.3190416f) ^ 0x7F08D65B));
        ModuleBreadCrumbs.syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
        ModuleBreadCrumbs.daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
        ModuleBreadCrumbs.vecs = new ArrayList<double[]>();
    }
}
