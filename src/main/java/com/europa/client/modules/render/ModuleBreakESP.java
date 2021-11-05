/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.Europa;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class ModuleBreakESP
extends Module {
    public Map percentMap = new HashMap();
    public static ValueBoolean name = new ValueBoolean("Name", "Name", "", false);
    public static ValueBoolean percent = new ValueBoolean("Percent", "Percent", "", false);
    public static ValueBoolean outline = new ValueBoolean("Outline", "Outline", "", false);
    public static ValueNumber red = new ValueNumber("Red", "Red", "", 255, 0, 255);
    public static ValueNumber green = new ValueNumber("Green", "Green", "", 0, 0, 255);
    public static ValueNumber blue = new ValueNumber("Blue", "Blue", "", 0, 0, 255);
    public static ValueNumber alpha = new ValueNumber("Alpha", "Alpha", "", 180, 0, 255);

    public ModuleBreakESP() {
        super("BreakESP", "Break ESP", "Highlights blocks which are being mined by yourself or others.", ModuleCategory.RENDER);
        this.percentMap.put(0, 10);
        this.percentMap.put(1, 20);
        this.percentMap.put(2, 30);
        this.percentMap.put(3, 40);
        this.percentMap.put(4, 50);
        this.percentMap.put(5, 60);
        this.percentMap.put(6, 70);
        this.percentMap.put(7, 80);
        this.percentMap.put(8, 90);
        this.percentMap.put(9, 100);
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        ModuleBreakESP.mc.renderGlobal.damagedBlocks.forEach(this::lambda$onRender3D$0);
    }

    /*
     * WARNING - void declaration
     */
    public static void drawText(BlockPos blockPos, String string, boolean bl) {
        void text;
        BlockPos pos;
        void font;
        if (font != false) {
            GlStateManager.pushMatrix();
            RenderUtils.glBillboardDistanceScaled((float)pos.getX() + Float.intBitsToFloat(Float.floatToIntBits(20.708643f) ^ 0x7EA5AB4D), (float)pos.getY() + Float.intBitsToFloat(Float.floatToIntBits(19.350775f) ^ 0x7E9ACE63), (float)pos.getZ() + Float.intBitsToFloat(Float.floatToIntBits(2.8721447f) ^ 0x7F37D138), (EntityPlayer)ModuleBreakESP.mc.player, Float.intBitsToFloat(Float.floatToIntBits(18.572966f) ^ 0x7E14956F));
            GlStateManager.disableDepth();
            GlStateManager.translate((double)(-((double)Europa.FONT_MANAGER.getStringWidth((String)text) / Double.longBitsToDouble(Double.doubleToLongBits(0.35480790991781513) ^ 0x7FD6B52C3C5D6263L))), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.5070836346222474E308) ^ 0x7FEAD3B4E8652237L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.4005978506939195E308) ^ 0x7FE8EE74C5D378B0L));
            Europa.FONT_MANAGER.drawString((String)text, Float.intBitsToFloat(Float.floatToIntBits(1.671886E38f) ^ 0x7EFB8EB5), Float.intBitsToFloat(Float.floatToIntBits(2.9987443E38f) ^ 0x7F6199B7), Color.WHITE);
            GlStateManager.popMatrix();
        } else {
            GlStateManager.pushMatrix();
            RenderUtils.glBillboardDistanceScaled((float)pos.getX() + Float.intBitsToFloat(Float.floatToIntBits(2.9254267f) ^ 0x7F3B3A31), (float)pos.getY() + Float.intBitsToFloat(Float.floatToIntBits(2.620229f) ^ 0x7F27B1D5), (float)pos.getZ() + Float.intBitsToFloat(Float.floatToIntBits(2.5236466f) ^ 0x7F21836D), (EntityPlayer)ModuleBreakESP.mc.player, Float.intBitsToFloat(Float.floatToIntBits(6.8190985f) ^ 0x7F5A360E));
            GlStateManager.disableDepth();
            GlStateManager.translate((double)(-((double)ModuleBreakESP.mc.fontRenderer.getStringWidth((String)text) / Double.longBitsToDouble(Double.doubleToLongBits(0.9636339461626108) ^ 0x7FEED616DB82AECDL))), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.0820626527415532E308) ^ 0x7FE342E822288829L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.084402768703347E308) ^ 0x7FE34D92109652A1L));
            ModuleBreakESP.mc.fontRenderer.drawStringWithShadow((String)text, Float.intBitsToFloat(Float.floatToIntBits(2.106353E38f) ^ 0x7F1E76E2), Float.intBitsToFloat(Float.floatToIntBits(2.5754297E38f) ^ 0x7F41C0F9), -1);
            GlStateManager.popMatrix();
        }
    }

    /*
     * WARNING - void declaration
     */
    public void lambda$onRender3D$0(Integer n, DestroyBlockProgress destroyBlockProgress) {
        block1: {
            void breakBlock;
            void breakingGuy;
            if (breakingGuy == null) {
                return;
            }
            RenderUtils.drawBoxESP(breakBlock.getPosition(), new Color(red.getValue().intValue(), green.getValue().intValue(), blue.getValue().intValue()), Float.intBitsToFloat(Float.floatToIntBits(16.120825f) ^ 0x7E00F773), outline.getValue(), true, alpha.getValue().intValue(), true, Double.longBitsToDouble(Double.doubleToLongBits(1.7524877085073546E307) ^ 0x7FB8F4CD052D6637L), false);
            if (!percent.getValue()) break block1;
            ModuleBreakESP.drawText(breakBlock.getPosition(), this.percentMap.get(breakBlock.getPartialBlockDamage()).toString() + "%" + (name.getValue() ? " " + ModuleBreakESP.mc.world.getEntityByID(breakingGuy.intValue()).getName() : ""), Europa.getModuleManager().isModuleEnabled("Font"));
        }
    }
}

