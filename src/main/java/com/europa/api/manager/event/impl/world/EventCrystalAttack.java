/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.world;

import com.europa.api.manager.event.Event;

public class EventCrystalAttack
extends Event {
    public int entityId;

    public EventCrystalAttack(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityID() {
        return this.entityId;
    }
}

