/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.Europa;
import com.europa.api.manager.event.impl.client.EventClient;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.client.gui.special.particles.ParticleSystem;
import java.awt.Color;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleParticles
extends Module {
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255));
    public static ValueNumber lineWidth = new ValueNumber("LineWidth", "LineWidth", "", Double.longBitsToDouble(Double.doubleToLongBits(0.47370997011349786) ^ 0x7FDE51439F5B4D97L), Double.longBitsToDouble(Double.doubleToLongBits(23.539912913623535) ^ 0x7FC78A37BB92B37FL), Double.longBitsToDouble(Double.doubleToLongBits(0.5061897672629005) ^ 0x7FE832B4E1FED92AL));
    public static ValueNumber amount = new ValueNumber("Population", "Amounts", "", 100, 50, 400);
    public static ValueNumber radius = new ValueNumber("Radius", "Radius", "", 100, 50, 300);
    public static ValueNumber speed = new ValueNumber("Speed", "Speed", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(89.10955f) ^ 0x7F7EF4DA)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(218.60843f) ^ 0x7E96570F)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.3970357f) ^ 0x7E92D211)));
    public static ValueNumber delta = new ValueNumber("Delta", "Delta", "", 1, 1, 10);
    public boolean changeAmount = false;
    public ParticleSystem ps;

    public ModuleParticles() {
        super("Particles", "Particles", "Renders fancy particles on the screen.", ModuleCategory.CLIENT);
    }

    @Override
    public void onEnable() {
        this.ps = new ParticleSystem(amount.getValue().intValue(), radius.getValue().intValue());
    }

    @SubscribeEvent
    public void onSetting(final EventClient event) {
        if (ModuleParticles.mc.player != null && ModuleParticles.mc.world != null) {
            if (event.getSetting() == ModuleParticles.amount) {
                this.changeAmount = true;
            }
        }
    }

    @Override
    public void onUpdate() {
        if (this.changeAmount) {
            this.ps.changeParticles(amount.getValue().intValue());
            this.changeAmount = false;
        }
        this.ps.tick(delta.getValue().intValue());
        this.ps.dist = radius.getValue().intValue();
        ParticleSystem.SPEED = (float)speed.getValue().doubleValue();
    }

    @Override
    public void onRender2D(EventRender2D eventRender2D) {
        block1: {
            block0: {
                if (ModuleParticles.mc.ingameGUI.getChatGUI().getChatOpen() || ModuleParticles.mc.currentScreen == Europa.CLICK_GUI) break block0;
                if (!(ModuleParticles.mc.currentScreen instanceof GuiContainer)) break block1;
            }
            this.ps.render();
        }
    }
}

