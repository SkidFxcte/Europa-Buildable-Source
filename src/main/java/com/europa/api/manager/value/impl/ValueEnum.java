/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.value.impl;

import com.europa.api.manager.event.impl.client.EventClient;
import com.europa.api.manager.value.Value;
import java.util.ArrayList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ValueEnum
extends Value {
    public Enum value;

    public ValueEnum(String name, String tag, String description, Enum value) {
        super(name, tag, description);
        this.value = value;
    }

    public Enum getValue() {
        return this.value;
    }


    public void setValue(final Enum value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }

    public Enum getEnumByName(final String name) {
        Enum enumRequested = null;
        for (final Enum enums : this.getValues()) {
            if (enums.name().equals(name)) {
                enumRequested = enums;
                break;
            }
        }
        return enumRequested;
    }


    public ArrayList<Enum> getValues() {
        ArrayList<Enum> enumList = new ArrayList<Enum>();
        for (Enum enums : (Enum[])this.value.getClass().getEnumConstants()) {
            enumList.add(enums);
        }
        return enumList;
    }
}

