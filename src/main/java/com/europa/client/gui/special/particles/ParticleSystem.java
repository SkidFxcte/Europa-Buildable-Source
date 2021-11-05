//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.special.particles;

import org.lwjgl.opengl.GL11;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import com.europa.client.modules.client.ModuleParticles;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ParticleSystem
{
    public static float SPEED;
    public List<Particle> particleList;
    public int dist;

    public ParticleSystem(final int initAmount, final int dist) {
        this.particleList = new ArrayList<Particle>();
        this.addParticles(initAmount);
        this.dist = dist;
    }

    public void addParticles(final int amount) {
        for (int i = 0; i < amount; ++i) {
            this.particleList.add(Particle.generateParticle());
        }
    }

    public void changeParticles(final int amount) {
        this.particleList.clear();
        for (int i = 0; i < amount; ++i) {
            this.particleList.add(Particle.generateParticle());
        }
    }

    public void tick(final int delta) {
        for (final Particle particle : this.particleList) {
            particle.tick(delta, ParticleSystem.SPEED);
        }
    }

    public void render() {
        for (final Particle particle : this.particleList) {
            final Color color = new Color(ModuleParticles.daColor.getValue().getRed(), ModuleParticles.daColor.getValue().getGreen(), ModuleParticles.daColor.getValue().getBlue(), 255);
            for (final Particle particle2 : this.particleList) {
                final float distance = particle.getDistanceTo(particle2);
                if (particle.getDistanceTo(particle2) < this.dist) {
                    final float alpha = Math.min(Float.intBitsToFloat(Float.floatToIntBits(171.23094f) ^ 0x7CAB3B1F), Math.min(Float.intBitsToFloat(Float.floatToIntBits(14.271226f) ^ 0x7EE456F1), Float.intBitsToFloat(Float.floatToIntBits(14.363122f) ^ 0x7EE5CF59) - distance / this.dist));
                    RenderUtils.prepareGL();
                    GL11.glEnable(2848);
                    GL11.glDisable(3553);
                    GL11.glLineWidth(ModuleParticles.lineWidth.getValue().floatValue());
                    GL11.glBegin(1);
                    GL11.glColor4f(ModuleParticles.daColor.getValue().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.009421679f) ^ 0x7F655D63), ModuleParticles.daColor.getValue().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.014374525f) ^ 0x7F148321), ModuleParticles.daColor.getValue().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.09082467f) ^ 0x7EC50249), alpha);
                    GL11.glVertex2d((double)particle.getX(), (double)particle.getY());
                    GL11.glVertex2d((double)particle2.getX(), (double)particle2.getY());
                    GL11.glEnd();
                    GL11.glEnable(3553);
                    RenderUtils.releaseGL();
                }
            }
            RenderUtils.drawCircle(particle.getX(), particle.getY(), particle.getSize(), color.getRGB());
        }
    }

    public void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    static {
        ParticleSystem.SPEED = Float.intBitsToFloat(Float.floatToIntBits(186.37817f) ^ 0x7EF6AC1D);
    }
}
