/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.player;

import com.europa.api.manager.event.Event;

public class EventKey
extends Event {
    public boolean info;
    public boolean pressed;

    public EventKey(boolean info, boolean pressed) {
        this.info = info;
        this.pressed = pressed;
    }
}

