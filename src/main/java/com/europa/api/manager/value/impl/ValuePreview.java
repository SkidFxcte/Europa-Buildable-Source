/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.value.impl;

import com.europa.api.manager.value.Value;
import net.minecraft.entity.Entity;

public class ValuePreview
extends Value {
    public Entity entity;

    public ValuePreview(String name, String tag, String description, Entity entity) {
        super(name, tag, description);
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}

