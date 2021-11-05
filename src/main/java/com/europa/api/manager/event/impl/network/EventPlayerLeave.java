/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.network;

import com.europa.api.manager.event.Event;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerLeave
extends Event {
    public String name;
    public UUID uuid;
    public EntityPlayer entity;

    public EventPlayerLeave(String n, UUID id, EntityPlayer ent) {
        this.name = n;
        this.uuid = id;
        this.entity = ent;
    }

    public String getName() {
        return this.name;
    }

    public EntityPlayer getEntity() {
        return this.entity;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}

