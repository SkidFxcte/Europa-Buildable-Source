/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;

public class ModuleBlockOutline
extends Module {
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public Color color;

    public ModuleBlockOutline() {
        super("BlockOutline", "Block Outline", "", ModuleCategory.RENDER);
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        block0: {
            this.color = syncColor.getValue() ? ModuleBlockOutline.globalColor(255) : daColor.getValue();
            if (ModuleBlockOutline.mc.objectMouseOver == null || ModuleBlockOutline.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK || ModuleBlockOutline.mc.objectMouseOver.getBlockPos() == null || ModuleBlockOutline.mc.world.getBlockState(ModuleBlockOutline.mc.objectMouseOver.getBlockPos()).getMaterial() == Material.AIR) break block0;
            RenderUtils.prepareGL();
            GL11.glEnable((int)2848);
            RenderUtils.drawBoundingBoxBlockPos(ModuleBlockOutline.mc.objectMouseOver.getBlockPos(), Float.intBitsToFloat(Float.floatToIntBits(9.31125f) ^ 0x7E94FAE1), this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255);
            RenderUtils.releaseGL();
        }
    }
}

