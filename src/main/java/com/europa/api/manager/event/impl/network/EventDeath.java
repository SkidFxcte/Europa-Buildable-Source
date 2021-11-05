/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.network;

import com.europa.api.manager.event.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventDeath
extends Event {
    public EntityPlayer player;

    public EventDeath(EntityPlayer player) {
        this.player = player;
    }
}

