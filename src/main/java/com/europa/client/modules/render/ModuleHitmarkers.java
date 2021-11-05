/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.math.TimerUtils;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ModuleHitmarkers
extends Module {
    public static ValueNumber timeout = new ValueNumber("Timeout", "Timeout", "", 1000, 0, 3000);
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public Color color;
    public ResourceLocation hitmarkerResource = new ResourceLocation("europa:hitmarker.png");
    public TimerUtils timer = new TimerUtils();
    public boolean renderMark = false;

    public ModuleHitmarkers() {
        super("Hitmarkers", "Hitmarkers", "Draws a hitmarker when you hit an entity.", ModuleCategory.RENDER);
    }

    @Override
    public void onUpdate() {
        if (this.timer.hasReached(timeout.getValue().intValue())) {
            if (this.renderMark) {
                this.timer.reset();
                this.renderMark = false;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        void event;
        if (!event.getEntity().equals((Object)ModuleHitmarkers.mc.player)) {
            return;
        }
        this.renderMark = true;
    }

    @Override
    public void onRender2D(EventRender2D eventRender2D) {
        block0: {
            this.color = syncColor.getValue() ? ModuleHitmarkers.globalColor(255) : daColor.getValue();
            if (!this.renderMark || this.timer.hasReached(timeout.getValue().intValue())) break block0;
            ScaledResolution res = new ScaledResolution(mc);
            this.renderMark(res.getScaledWidth() / 2 - 9, res.getScaledHeight() / 2 - 9);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void renderMark(int n, int n2) {
        void y;
        void x;
        mc.getTextureManager().bindTexture(this.hitmarkerResource);
        GL11.glPushMatrix();
        GL11.glColor4f((float)((float)this.color.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.14868592f) ^ 0x7D67411F)), (float)((float)this.color.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.48562214f) ^ 0x7D87A377)), (float)((float)this.color.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.33453032f) ^ 0x7DD4478F)), (float)Float.intBitsToFloat(Float.floatToIntBits(4.194591f) ^ 0x7F063A17));
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, (float)Float.intBitsToFloat(Float.floatToIntBits(1.227151E38f) ^ 0x7EB8A423), (float)Float.intBitsToFloat(Float.floatToIntBits(3.364097E38f) ^ 0x7F7D1627), (int)420, (int)420, (int)18, (int)18, (float)Float.intBitsToFloat(Float.floatToIntBits(0.92636675f) ^ 0x7CBF265F), (float)Float.intBitsToFloat(Float.floatToIntBits(0.02943024f) ^ 0x7F2317B0));
        GL11.glPopMatrix();
    }
}

