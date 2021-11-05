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
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.lwjgl.opengl.GL11;

public class ModuleCrosshair
extends Module {
    public static ValueBoolean dynamic = new ValueBoolean("Dynamic", "Dynamic", "", false);
    public static ValueBoolean attackIndicator = new ValueBoolean("AttackIndicator", "AttackIndicator", "", false);
    public static ValueBoolean outline = new ValueBoolean("Outline", "Outline", "", false);
    public static ValueNumber lineWidth = new ValueNumber("OutlineWidth", "OutlineWidth", "", Double.longBitsToDouble(Double.doubleToLongBits(121.29573769957732) ^ 0x7FAE52ED5DD0F83FL), Double.longBitsToDouble(Double.doubleToLongBits(8.717931802531621E307) ^ 0x7FDF0970251CFFB5L), Double.longBitsToDouble(Double.doubleToLongBits(1.3833776984518509) ^ 0x7FE22250A74DDCFAL));
    public static ValueNumber length = new ValueNumber("Length", "Length", "", Double.longBitsToDouble(Double.doubleToLongBits(1.7378639429799982) ^ 0x7FDFCE4A6BFFF27DL), Double.longBitsToDouble(Double.doubleToLongBits(1.0596756262038056E308) ^ 0x7FE2DCE3E2FE00E2L), Double.longBitsToDouble(Double.doubleToLongBits(0.4908308166647887) ^ 0x7FEB69C5A85C6E6BL));
    public static ValueNumber thick = new ValueNumber("Thick", "Thick", "", Double.longBitsToDouble(Double.doubleToLongBits(0.8168031053003805) ^ 0x7FCE234044112A03L), Double.longBitsToDouble(Double.doubleToLongBits(8.852490284413103E307) ^ 0x7FDF8412C765EC9BL), Double.longBitsToDouble(Double.doubleToLongBits(0.3657596415598512) ^ 0x7FE3689B20AC8F40L));
    public static ValueNumber gap = new ValueNumber("Gap", "Gap", "", Double.longBitsToDouble(Double.doubleToLongBits(0.6830313443513106) ^ 0x7FC1DB648CC437A7L), Double.longBitsToDouble(Double.doubleToLongBits(9.690302828753905E307) ^ 0x7FE13FD2E2A2A6A2L), Double.longBitsToDouble(Double.doubleToLongBits(0.45096597085211265) ^ 0x7FE8DCA0601ACC32L));
    public static ValueNumber opacity = new ValueNumber("Alpha", "Alpha", "", 255, 0, 255);
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(0, 255, 0, 255));
    public Color color;

    public ModuleCrosshair() {
        super("Crosshair", "Crosshair", "Renders a better looking crosshair over the vanilla Minecraft one.", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        GuiIngameForge.renderCrosshairs = false;
    }

    @Override
    public void onDisable() {
        GuiIngameForge.renderCrosshairs = true;
    }

    @Override
    public void onRender2D(EventRender2D eventRender2D) {
        float f;
        this.color = syncColor.getValue() ? ModuleCrosshair.globalColor(255) : daColor.getValue();
        int red = this.color.getRed();
        int green = this.color.getGreen();
        int blue = this.color.getBlue();
        int alpha = opacity.getValue().intValue();
        int color = new Color(red, green, blue, alpha).getRGB();
        int black = new Color(0, 0, 0, 255).getRGB();
        ScaledResolution resolution = new ScaledResolution(mc);
        RenderUtils.drawRecta((float)(resolution.getScaledWidth() / 2) - gap.getValue().floatValue() - length.getValue().floatValue() - (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledHeight() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.15277664f) ^ 0x7E1C717B), length.getValue().floatValue(), thick.getValue().floatValue(), color);
        RenderUtils.drawRecta((float)(resolution.getScaledWidth() / 2) + gap.getValue().floatValue() + (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledHeight() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.62074363f) ^ 0x7F1EE90E), length.getValue().floatValue(), thick.getValue().floatValue(), color);
        RenderUtils.drawRecta((float)(resolution.getScaledWidth() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.49233422f) ^ 0x7EFC133B), (float)(resolution.getScaledHeight() / 2) - gap.getValue().floatValue() - length.getValue().floatValue() - (float)(this.moving() ? 2 : 0), thick.getValue().floatValue(), length.getValue().floatValue(), color);
        RenderUtils.drawRecta((float)(resolution.getScaledWidth() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.60295755f) ^ 0x7F1A5B6D), (float)(resolution.getScaledHeight() / 2) + gap.getValue().floatValue() + (float)(this.moving() ? 2 : 0), thick.getValue().floatValue(), length.getValue().floatValue(), color);
        if (outline.getValue()) {
            RenderUtils.drawOutlineLine((float)(resolution.getScaledWidth() / 2) - gap.getValue().floatValue() - length.getValue().floatValue() - (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledHeight() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.11063086f) ^ 0x7DE2926F), (float)(resolution.getScaledWidth() / 2) - gap.getValue().floatValue() - (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledHeight() / 2) + thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.84218675f) ^ 0x7F57998D), lineWidth.getValue().floatValue(), black);
            RenderUtils.drawOutlineLine((float)(resolution.getScaledWidth() / 2) + gap.getValue().floatValue() + (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledHeight() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.46624455f) ^ 0x7EEEB79B), (float)(resolution.getScaledWidth() / 2) + length.getValue().floatValue() + gap.getValue().floatValue() + (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledHeight() / 2) + thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.86076933f) ^ 0x7F5C5B61), lineWidth.getValue().floatValue(), black);
            RenderUtils.drawOutlineLine((float)(resolution.getScaledWidth() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.6369833f) ^ 0x7F231156), (float)(resolution.getScaledHeight() / 2) - gap.getValue().floatValue() - length.getValue().floatValue() - (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledWidth() / 2) + thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.25581852f) ^ 0x7E82FAA5), (float)(resolution.getScaledHeight() / 2) - gap.getValue().floatValue() - (float)(this.moving() ? 2 : 0), lineWidth.getValue().floatValue(), black);
            RenderUtils.drawOutlineLine((float)(resolution.getScaledWidth() / 2) - thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.5095226f) ^ 0x7F027013), (float)(resolution.getScaledHeight() / 2) + gap.getValue().floatValue() + (float)(this.moving() ? 2 : 0), (float)(resolution.getScaledWidth() / 2) + thick.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.4514692f) ^ 0x7EE726F9), (float)(resolution.getScaledHeight() / 2) + length.getValue().floatValue() + gap.getValue().floatValue() + (float)(this.moving() ? 2 : 0), lineWidth.getValue().floatValue(), black);
        }
        if (attackIndicator.getValue() && (f = ModuleCrosshair.mc.player.getCooledAttackStrength(Float.intBitsToFloat(Float.floatToIntBits(2.6898302E38f) ^ 0x7F4A5C3F))) < Float.intBitsToFloat(Float.floatToIntBits(739.4765f) ^ 0x7BB8DE7F)) {
            int k = (int)(f * Float.intBitsToFloat(Float.floatToIntBits(0.3690825f) ^ 0x7F1CF862));
            RenderUtils.drawRecta(resolution.getScaledWidth() / 2 - 10, (float)(resolution.getScaledHeight() / 2) + gap.getValue().floatValue() + length.getValue().floatValue() + (float)(this.moving() ? 2 : 0) + Float.intBitsToFloat(Float.floatToIntBits(0.7063763f) ^ 0x7F34D514), k, Float.intBitsToFloat(Float.floatToIntBits(0.5684455f) ^ 0x7F1185A5), color);
            RenderUtils.drawOutlineLine(resolution.getScaledWidth() / 2 - 10, (float)(resolution.getScaledHeight() / 2) + gap.getValue().floatValue() + length.getValue().floatValue() + (float)(this.moving() ? 2 : 0) + Float.intBitsToFloat(Float.floatToIntBits(0.6157259f) ^ 0x7F1DA036), resolution.getScaledWidth() / 2 - 10 + k, (float)(resolution.getScaledHeight() / 2) + gap.getValue().floatValue() + length.getValue().floatValue() + (float)(this.moving() ? 2 : 0) + Float.intBitsToFloat(Float.floatToIntBits(1.5334219f) ^ 0x7F44472B), Float.intBitsToFloat(Float.floatToIntBits(29.272482f) ^ 0x7E6A2E0B), black);
        }
        GL11.glEnable((int)2929);
    }

    public boolean moving() {
        return (ModuleCrosshair.mc.player.isSneaking() || ModuleCrosshair.mc.player.moveStrafing != Float.intBitsToFloat(Float.floatToIntBits(3.3700814E38f) ^ 0x7F7D8968) || ModuleCrosshair.mc.player.moveForward != Float.intBitsToFloat(Float.floatToIntBits(2.1583157E38f) ^ 0x7F225FA6) || !ModuleCrosshair.mc.player.onGround) && dynamic.getValue();
        {
        }
    }
}

