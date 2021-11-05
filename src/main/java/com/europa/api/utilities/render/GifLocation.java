/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.render;

import net.minecraft.util.ResourceLocation;

public class GifLocation {
    public String folder;
    public int frames;
    public int fpt;
    public int currentTick = 0;
    public int currentFrame = 0;
    public ResourceLocation[] textures;

    public GifLocation(String folder, int frames, int fpt) {
        this.folder = folder;
        this.frames = frames;
        this.fpt = fpt;
        this.textures = new ResourceLocation[frames];
        for (int i = 0; i < frames; ++i) {
            this.textures[i] = new ResourceLocation(folder + "/" + i + ".png");
        }
    }

    public ResourceLocation getTexture() {
        return this.textures[this.currentFrame];
    }

    public void update() {
        if (this.currentTick > this.fpt) {
            this.currentTick = 0;
            ++this.currentFrame;
            if (this.currentFrame > this.textures.length - 1) {
                this.currentFrame = 0;
            }
        }
        ++this.currentTick;
    }
}

