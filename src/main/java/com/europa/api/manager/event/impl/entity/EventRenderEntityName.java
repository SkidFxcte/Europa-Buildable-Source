/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.entity;

import com.europa.api.manager.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;

public class EventRenderEntityName
extends Event {
    public AbstractClientPlayer Entity;
    public double X;
    public double Y;
    public double Z;
    public String Name;
    public double DistanceSq;

    public EventRenderEntityName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
        this.Entity = entityIn;
        x = this.X;
        y = this.Y;
        z = this.Z;
        this.Name = name;
        this.DistanceSq = distanceSq;
    }
}

