/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.shader;

import com.europa.api.utilities.render.RenderUtils;
import com.europa.api.utilities.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class RedShader
extends FramebufferShader {
    public static RedShader RED_SHADER = new RedShader();
    public float time;

    public RedShader() {
        super("red.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform2f((int)this.getUniform("resolution"), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight());
        GL20.glUniform1f((int)this.getUniform("time"), (float)this.time);
        this.time += Float.intBitsToFloat(Float.floatToIntBits(626.72473f) ^ 0x7F5835C4) * (float)RenderUtils.deltaTime;
    }
}

