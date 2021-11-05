/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.client;

import com.europa.api.manager.event.Event;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.value.Value;

public class EventClient
extends Event {
    public Module module;
    public Value setting;
    public int stage;

    public EventClient(Value setting) {
        this.setting = setting;
    }

    public EventClient(int stage) {
        this.stage = stage;
    }

    public Module getModule() {
        return this.module;
    }

    public Value getSetting() {
        return this.setting;
    }
}

