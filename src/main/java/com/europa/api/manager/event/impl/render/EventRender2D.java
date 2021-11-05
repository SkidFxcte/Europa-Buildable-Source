/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.render;

import com.europa.api.manager.event.Event;

public class EventRender2D
extends Event {
    public float partialTicks;

    public EventRender2D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

