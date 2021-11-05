/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.lwjgl.opengl.GL11;

public class ModuleSpawns
extends Module {
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public static Color color8;
    public static HashMap<UUID, Thingering> thingers;

    public ModuleSpawns() {
        super("Spawns", "Spawns", "Renders a circle around newly spawned entities.", ModuleCategory.MISC);
    }

    @Override
    public void onUpdate() {
        color8 = syncColor.getValue() ? ModuleSpawns.globalColor(255) : daColor.getValue();
        for (Entity entity : ModuleSpawns.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || thingers.containsKey(entity.getUniqueID())) continue;
            thingers.put(entity.getUniqueID(), new Thingering(this, entity));
            ModuleSpawns.thingers.get((Object)entity.getUniqueID()).starTime = System.currentTimeMillis();
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        if (ModuleSpawns.mc.player == null || ModuleSpawns.mc.world == null) {
            return;
        }
        for (Map.Entry<UUID, Thingering> entry : thingers.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().starTime >= ((long)180056445 ^ 0xABB74A1L)) continue;
            float opacity = Float.intBitsToFloat(Float.floatToIntBits(1.2886874E38f) ^ 0x7EC1E66F);
            long time = System.currentTimeMillis();
            long duration = time - entry.getValue().starTime;
            if (duration < ((long)-1747803867 ^ 0xFFFFFFFF97D2A4F9L)) {
                opacity = Float.intBitsToFloat(Float.floatToIntBits(13.7902155f) ^ 0x7EDCA4B9) - (float)duration / Float.intBitsToFloat(Float.floatToIntBits(6.1687006E-4f) ^ 0x7E9A3573);
            }
            ModuleSpawns.drawCircle(entry.getValue().entity, eventRender3D.getPartialTicks(), Double.longBitsToDouble(Double.doubleToLongBits(205.3116845075892) ^ 0x7F89A9F951C9D87FL), (float)(System.currentTimeMillis() - entry.getValue().starTime) / Float.intBitsToFloat(Float.floatToIntBits(0.025765074f) ^ 0x7E1B1147), opacity);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void drawCircle(Entity entity, float f, double d, float f2, float f3) {
        Color color = new Color(color8.getRed(), color8.getGreen(), color8.getBlue());
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        ModuleSpawns.startSmooth();
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)Float.intBitsToFloat(Float.floatToIntBits(0.8191538f) ^ 0x7F11B410));
        GL11.glBegin((int)3);
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - ModuleSpawns.mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - ModuleSpawns.mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - ModuleSpawns.mc.getRenderManager().viewerPosZ;
        float r = Float.intBitsToFloat(Float.floatToIntBits(3180.4917f) ^ 0x7EC6475F) * (float)color.getRed();
        float g = Float.intBitsToFloat(Float.floatToIntBits(4554.3037f) ^ 0x7E0ED2EF) * (float)color.getGreen();
        float b = Float.intBitsToFloat(Float.floatToIntBits(29994.996f) ^ 0x7D6AD57F) * (float)color.getBlue();
        double pix2 = Double.longBitsToDouble(Double.doubleToLongBits(0.12418750450734782) ^ 0x7FA6EB3BC22A7D2FL);
        for (int i = 0; i <= 90; ++i) {
            GL11.glColor4f((float)r, (float)g, (float)b, (float)alpha);
            GL11.glVertex3d((double)(x + rad * Math.cos((double)i * Double.longBitsToDouble(Double.doubleToLongBits(0.038923223119235344) ^ 0x7FBACC45F0F011C7L) / Double.longBitsToDouble(Double.doubleToLongBits(0.010043755046771538) ^ 0x7FC211D1FBA3AC6BL))), (double)(y + (double)(plusY / Float.intBitsToFloat(Float.floatToIntBits(0.13022153f) ^ 0x7F2558CB))), (double)(z + rad * Math.sin((double)i * Double.longBitsToDouble(Double.doubleToLongBits(0.012655047216797511) ^ 0x7F90CB18FB234FBFL) / Double.longBitsToDouble(Double.doubleToLongBits(0.00992417958121009) ^ 0x7FC2D320D5ED6BD3L))));
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2929);
        ModuleSpawns.endSmooth();
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    public static void startSmooth() {
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glEnable((int)2832);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glHint((int)3153, (int)4354);
    }

    public static void endSmooth() {
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glEnable((int)2832);
    }

    static {
        thingers = new HashMap();
    }

    public class Thingering {
        public Entity entity;
        public long starTime;
        public ModuleSpawns this$0;

        public Thingering(ModuleSpawns this$0, Entity entity) {
            this.this$0 = this$0;
            this.entity = entity;
            this.starTime = (long)1417733199 ^ 0x5480E44FL;
        }
    }
}

