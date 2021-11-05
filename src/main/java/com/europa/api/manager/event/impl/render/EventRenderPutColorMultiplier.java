/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.render;

import com.europa.api.manager.event.Event;

public class EventRenderPutColorMultiplier
extends Event {
    public float _opacity;

    public void setOpacity(final float opacity) {
        this._opacity = opacity;
    }

    public float getOpacity() {
        return this._opacity;
    }
}

