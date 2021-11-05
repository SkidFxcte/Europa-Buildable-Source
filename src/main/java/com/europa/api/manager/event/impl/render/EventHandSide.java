/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.render;

import com.europa.api.manager.event.Event;
import net.minecraft.util.EnumHandSide;

public class EventHandSide
extends Event {
    public EnumHandSide handSide;

    public EventHandSide(EnumHandSide handSide) {
        this.handSide = handSide;
    }

    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}

