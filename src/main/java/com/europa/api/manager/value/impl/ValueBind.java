/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.value.impl;

import com.europa.api.manager.event.impl.client.EventClient;
import com.europa.api.manager.value.Value;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ValueBind
extends Value {
    public int value;

    public ValueBind(String name, String tag, String description, int value) {
        super(name, tag, description);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(final int value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
}

