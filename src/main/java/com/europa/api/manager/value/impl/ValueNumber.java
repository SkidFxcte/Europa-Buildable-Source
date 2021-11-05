/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.value.impl;

import com.europa.api.manager.event.impl.client.EventClient;
import com.europa.api.manager.value.Value;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ValueNumber
extends Value {
    public static int INTEGER;
    public static int DOUBLE;
    public static int FLOAT;
    public Number value;
    public Number minimum;
    public Number maximum;

    public ValueNumber(String name, String tag, String description, Number value, Number minimum, Number maximum) {
        super(name, tag, description);
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Number getValue() {
        return this.value;
    }

    public void setValue(final Number value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }

    public Number getMinimum() {
        return this.minimum;
    }

    public Number getMaximum() {
        return this.maximum;
    }

    public int getType() {
        if (this.value.getClass() == Integer.class) {
            return 1;
        }
        if (this.value.getClass() == Double.class) {
            return 2;
        }
        if (this.value.getClass() == Float.class) {
            return 3;
        }
        return -1;
    }

    static {
        FLOAT = 3;
        DOUBLE = 2;
        INTEGER = 1;
    }
}

