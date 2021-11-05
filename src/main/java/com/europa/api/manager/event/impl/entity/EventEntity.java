/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.entity;

import com.europa.api.manager.event.Event;
import net.minecraft.entity.Entity;

public class EventEntity
extends Event {
    public Entity entity;

    public EventEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity get_entity() {
        return this.entity;
    }

    public static class EventColision
    extends EventEntity {
        public double x;
        public double y;
        public double z;

        public EventColision(Entity entity, double x, double y, double z) {
            super(entity);
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void set_x(final double x) {
            this.x = x;
        }

        public void set_y(final double y) {
            this.y = y;
        }

        public void set_z(double d) {
            this.z = this.z;
        }

        public double get_x() {
            return this.x;
        }

        public double get_y() {
            return this.y;
        }

        public double get_z() {
            return this.z;
        }
    }
}

