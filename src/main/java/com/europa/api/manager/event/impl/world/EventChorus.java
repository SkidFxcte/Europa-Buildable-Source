/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.world;

import com.europa.api.manager.event.Event;
import net.minecraft.entity.EntityLivingBase;

public class EventChorus
extends Event {
    public EntityLivingBase entityLivingBase;
    public double x;
    public double y;
    public double z;
    public boolean successful;

    public EventChorus(EntityLivingBase entityLivingBase, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityLivingBase = entityLivingBase;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public void setSuccessful(final boolean successful) {
        this.successful = successful;
    }


    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }

    public boolean isCancelable() {
        return true;
    }
}

