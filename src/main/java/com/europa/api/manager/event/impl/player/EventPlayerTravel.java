/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.player;

import com.europa.api.manager.event.Event;

public class EventPlayerTravel
extends Event {
    public float strafe;
    public float vertical;
    public float forward;

    public EventPlayerTravel(float strafe, float vertical, float forward) {
        this.strafe = strafe;
        this.vertical = vertical;
        this.forward = forward;
    }
}

