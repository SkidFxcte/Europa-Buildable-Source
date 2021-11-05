/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;

public class ModuleViewClip
extends Module {
    public static ValueBoolean extend = new ValueBoolean("Extend", "Extend", "", false);
    public static ValueNumber distance = new ValueNumber("Distance", "Distance", "", Double.longBitsToDouble(Double.doubleToLongBits(1.2969884478152263) ^ 0x7FD0C076F56A7FCBL), Double.longBitsToDouble(Double.doubleToLongBits(4.179908434366944E307) ^ 0x7FCDC30E3C385787L), Double.longBitsToDouble(Double.doubleToLongBits(1.2482517149116192) ^ 0x7FBAF8D6CA4B8A87L));

    public ModuleViewClip() {
        super("ViewClip", "View Clip", "Makes your camera be able to clip through walls.", ModuleCategory.RENDER);
    }
}

