/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.render;

import com.europa.api.utilities.IMinecraft;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class OutlineUtils
implements IMinecraft {
    public static void renderOne(final float width) {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(width);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }


    public static void renderTwo() {
        GL11.glStencilFunc((int) 512, (int) 0, (int) 15);
        GL11.glStencilOp((int) 7681, (int) 7681, (int) 7681);
        GL11.glPolygonMode((int) 1032, (int) 6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc((int) 514, (int) 1, (int) 15);
        GL11.glStencilOp((int) 7680, (int) 7680, (int) 7680);
        GL11.glPolygonMode((int) 1032, (int) 6913);
    }

    public static void renderFour() {
        OutlineUtils.setColor(new Color(255, 255, 255));
        GL11.glDepthMask((boolean) false);
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 10754);
        GL11.glPolygonOffset((float) Float.intBitsToFloat(Float.floatToIntBits(22.41226f) ^ 0x7E334C4F), (float) Float.intBitsToFloat(Float.floatToIntBits(-1.3566593E-5f) ^ 0x7E97B813));
        OpenGlHelper.setLightmapTextureCoords((int) OpenGlHelper.lightmapTexUnit, (float) Float.intBitsToFloat(Float.floatToIntBits(0.01000088f) ^ 0x7F53DABB), (float) Float.intBitsToFloat(Float.floatToIntBits(0.011808969f) ^ 0x7F317A68));
    }

    public static void renderFive() {
        GL11.glPolygonOffset((float) Float.intBitsToFloat(Float.floatToIntBits(12.714713f) ^ 0x7ECB6F77), (float) Float.intBitsToFloat(Float.floatToIntBits(1.3271895E-5f) ^ 0x7EAA8E5B));
        GL11.glDisable((int) 10754);
        GL11.glEnable((int) 2929);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 2960);
        GL11.glDisable((int) 2848);
        GL11.glHint((int) 3154, (int) 4352);
        GL11.glEnable((int) 3042);
        GL11.glEnable((int) 2896);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 3008);
        GL11.glPopAttrib();
    }

    public static void setColor(final Color c) {
        GL11.glColor4d((double) (c.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.0098663345f) ^ 0x7F5EA668)), (double) (c.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(1.0011557f) ^ 0x7CFF25DF)), (double) (c.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.114814304f) ^ 0x7E9423C3)), (double) (c.getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.09551593f) ^ 0x7EBC9DDB)));
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            OutlineUtils.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
}

