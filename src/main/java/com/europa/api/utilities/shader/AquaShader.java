/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.shader;

import com.europa.api.utilities.render.RenderUtils;
import com.europa.api.utilities.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class AquaShader
extends FramebufferShader {
    public static AquaShader AQUA_SHADER = new AquaShader();
    public float time;

    public AquaShader() {
        super("aqua.frag");
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
        this.time += Float.intBitsToFloat(Float.floatToIntBits(1015.0615f) ^ 0x7F395856) * (float)RenderUtils.deltaTime;
    }
}

