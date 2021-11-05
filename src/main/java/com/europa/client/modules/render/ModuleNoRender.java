/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.render.EventBossBar;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleNoRender
extends Module {
    public static ValueBoolean fire = new ValueBoolean("Fire", "fire", "fire", false);
    public static ValueBoolean armor = new ValueBoolean("Armor", "Armor", "", false);
    public static ValueBoolean totemPop = new ValueBoolean("TotemPop", "TotemPop", "", false);
    public static ValueBoolean pumpkin = new ValueBoolean("Pumpkin", "Pumpkin", "", false);
    public static ValueBoolean hurtCam = new ValueBoolean("HurtCam", "HurtCam", "", false);
    public static ValueBoolean bossBar = new ValueBoolean("BossBar", "BossBar", "", false);
    public static ValueBoolean suffocation = new ValueBoolean("Suffocation", "Suffocation", "", false);

    public ModuleNoRender() {
        super("NoRender", "No Render", "Prevents certain things from rendering.", ModuleCategory.RENDER);
    }
    @SubscribeEvent
    public void onRenderBlock(final RenderBlockOverlayEvent event) {
        if (ModuleNoRender.fire.getValue()) {
            if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onBossBar(final EventBossBar event) {
        if (ModuleNoRender.bossBar.getValue()) {
            event.setCancelled(true);
        }
    }
}

