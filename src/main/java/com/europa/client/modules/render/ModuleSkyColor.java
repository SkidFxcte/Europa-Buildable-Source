/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueColor;
import java.awt.Color;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleSkyColor
extends Module {
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));

    public ModuleSkyColor() {
        super("SkyColor", "Sky Color", "Changes the color of the sky.", ModuleCategory.RENDER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onEntityRender(EntityViewRenderEvent.FogColors fogColors) {
        void event;
        event.setRed((float)daColor.getValue().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.009777151f) ^ 0x7F5F3058));
        event.setGreen((float)daColor.getValue().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.06490492f) ^ 0x7EFBECDF));
        event.setBlue((float)daColor.getValue().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.009649655f) ^ 0x7F611996));
    }
}

