/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.value.impl;

import com.europa.api.manager.event.impl.client.EventClient;
import com.europa.api.manager.value.Value;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ValueString
extends Value {
    public String value;

    public ValueString(String name, String tag, String description, String value) {
        super(name, tag, description);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
}

