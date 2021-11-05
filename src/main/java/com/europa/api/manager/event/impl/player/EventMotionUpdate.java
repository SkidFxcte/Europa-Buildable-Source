/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.player;

import com.europa.api.manager.event.Event;

public class EventMotionUpdate
extends Event {
    public int stage;

    public EventMotionUpdate(int stage) {
        this.stage = stage;
    }
}

