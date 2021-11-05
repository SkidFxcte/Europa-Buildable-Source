/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.network;

import com.europa.api.manager.event.Event;
import java.util.UUID;

public class EventPlayerJoin
extends Event {
    public String name;
    public UUID uuid;

    public EventPlayerJoin(String n, UUID id) {
        this.name = n;
        this.uuid = id;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}

