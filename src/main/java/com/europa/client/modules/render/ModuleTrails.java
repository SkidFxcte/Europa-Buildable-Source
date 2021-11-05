/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.player.EventMotionUpdate;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ModuleTrails
extends Module {
    public static ValueNumber lineWidth = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(17.27955007873751) ^ 0x7FC14790980DC597L), Double.longBitsToDouble(Double.doubleToLongBits(93.95772593779462) ^ 0x7FB77D4B61BB56F7L), Double.longBitsToDouble(Double.doubleToLongBits(0.1654465991727702) ^ 0x7FD12D5AAA573A5DL));
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public Color color;
    public Map<Entity, List<Vec3d>> renderMap = new HashMap<Entity, List<Vec3d>>();

    public ModuleTrails() {
        super("Trails", "Trails", "Draws a trail on pearls and other selected items.", ModuleCategory.RENDER);
    }

    @SubscribeEvent
    public void onMotion(EventMotionUpdate eventMotionUpdate) {
        if (ModuleTrails.mc.player == null || ModuleTrails.mc.world == null) {
            return;
        }
        for (Entity entity : ModuleTrails.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityThrowable) && !(entity instanceof EntityArrow) || entity instanceof EntityExpBottle) continue;
            ArrayList<Vec3d> vectors = this.renderMap.get(entity) != null ? this.renderMap.get(entity) : new ArrayList<Vec3d>();
            vectors.add(new Vec3d(entity.posX, entity.posY, entity.posZ));
            this.renderMap.put(entity, vectors);
        }
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        this.color = syncColor.getValue() ? ModuleTrails.globalColor(255) : daColor.getValue();
        if (ModuleTrails.mc.player == null || ModuleTrails.mc.world == null) {
            return;
        }
        for (Entity entity : ModuleTrails.mc.world.loadedEntityList) {
            int[] counter = new int[]{1};
            if (!this.renderMap.containsKey(entity)) continue;
            GlStateManager.pushMatrix();
            RenderUtils.GLPre(lineWidth.getValue().floatValue());
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask((boolean)false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            GL11.glLineWidth((float)lineWidth.getValue().floatValue());
            GL11.glBegin((int)1);
            for (int i = 0; i < this.renderMap.get(entity).size() - 1; ++i) {
                GL11.glColor4f((float)((float)this.color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.012864648f) ^ 0x7F2DC63F)), (float)((float)this.color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.012751623f) ^ 0x7F2FEC2F)), (float)((float)this.color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.009840384f) ^ 0x7F5E3990)), (float)Float.intBitsToFloat(Float.floatToIntBits(23.044294f) ^ 0x7E385AB7));
                Vec3d pos = ModuleTrails.updateToCamera(this.renderMap.get(entity).get(i));
                Vec3d pos2 = ModuleTrails.updateToCamera(this.renderMap.get(entity).get(i + 1));
                GL11.glVertex3d((double)pos.x, (double)pos.y, (double)pos.z);
                GL11.glVertex3d((double)pos2.x, (double)pos2.y, (double)pos2.z);
                counter[0] = counter[0] + 1;
            }
            GL11.glEnd();
            GlStateManager.resetColor();
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            RenderUtils.GlPost();
            GlStateManager.popMatrix();
        }
    }

    public static Vec3d updateToCamera(Vec3d vec3d) {
        Vec3d vec;
        return new Vec3d(vec.x - ModuleTrails.mc.getRenderManager().viewerPosX, vec.y - ModuleTrails.mc.getRenderManager().viewerPosY, vec.z - ModuleTrails.mc.getRenderManager().viewerPosZ);
    }
}

