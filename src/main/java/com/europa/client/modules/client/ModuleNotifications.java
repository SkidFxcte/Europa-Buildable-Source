/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.Europa;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import net.minecraft.client.gui.ScaledResolution;

public class ModuleNotifications
extends Module {
    public static ValueNumber lifetime = new ValueNumber("Lifetime", "Lifetime", "", 500, 500, 5000);
    public static ValueNumber inOutTime = new ValueNumber("InOutTime", "InOutTime", "", 200, 50, 500);
    public ValueNumber height = new ValueNumber("Height", "Height", "", 50, 0, new ScaledResolution(mc).getScaledHeight());
    public ValueNumber max = new ValueNumber("Max", "Max", "", 7, 1, 20);
    public static ValueBoolean addType = new ValueBoolean("AddDecrease", "AddDecrease", "", false);
    public static ValueBoolean pops = new ValueBoolean("Pops", "Pops", "", true);
    public static ValueBoolean chatNotify = new ValueBoolean("ChatNotify", "ChatNotify", "", true);

    public ModuleNotifications() {
        super("Notifications", "Notifications", "Renders notifications on your screen.", ModuleCategory.CLIENT);
    }

    @Override
    public void onRender2D(EventRender2D eventRender2D) {
        block5: {
            block4: {
                if (ModuleNotifications.mc.player == null) break block4;
                if (ModuleNotifications.mc.world != null) break block5;
            }
            return;
        }
        if (Europa.NOTIFICATION_PROCESSOR.notifications.size() > this.max.getValue().intValue()) {
            Europa.NOTIFICATION_PROCESSOR.notifications.remove(0);
        }
        Europa.NOTIFICATION_PROCESSOR.handleNotifications(this.height.getValue().intValue());
    }
}

