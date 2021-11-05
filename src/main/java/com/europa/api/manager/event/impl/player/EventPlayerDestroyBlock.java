/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event.impl.player;

import com.europa.api.manager.event.Event;
import net.minecraft.util.math.BlockPos;

public class EventPlayerDestroyBlock
extends Event {
    public BlockPos pos;

    public EventPlayerDestroyBlock(BlockPos blockPos) {
        this.pos = blockPos;
    }

    public BlockPos getBlockPos() {
        return this.pos;
    }

    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
}

