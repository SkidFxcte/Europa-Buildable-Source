//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.manager.event.impl.render;

import com.europa.client.mixins.impl.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;
import com.europa.api.manager.event.Event;

public class EventBlockGetRenderLayer extends Event
{
    public BlockRenderLayer _layer;
    public MixinBlock _block;
    public MixinBlockBeacon _blockBeacon;
    public MixinBlockBed _blockBed;
    public MixinBlockBrewingStand _blockBrewingStand;
    public MixinBlockBush _blockBush;
    public MixinBlockCactus _blockCactus;
    public MixinBlockCake _blockCake;
    public MixinBlockChorusFlower _blockChorusFlower;
    public MixinBlockChorusPlant _blockChorusPlant;
    public MixinBlockCocoa _blockCocoa;
    public MixinBlockDoor _blockDoor;
    public MixinBlockEndRod _blockEndRod;
    public MixinBlockFire _blockFire;
    public MixinBlockFlowerPot _blockFlowerPot;
    public MixinBlockGrass _blockGrass;
    public MixinBlockHopper _blockHopper;
    public MixinBlockIce _blockIce;
    public MixinBlockLadder _blockLadder;
    public MixinBlockLeaves _blockLeaves;
    public MixinBlockLiquid _blockLiquid;
    public MixinBlockMobSpawner _blockMobSpawner;
    public MixinBlockPane _blockPane;
    public MixinBlockPortal _blockPortal;
    public MixinBlockRailBase _blockRailBase;
    public MixinBlockRedstoneDiode _blockRedstoneDiode;
    public MixinBlockRedstoneWire _blockRedstoneWire;
    public MixinBlockReed _blockReed;
    public MixinBlockSlime _blockSlime;
    public MixinBlockStainedGlass _blockStainedGlass;
    public MixinBlockStainedGlassPane _blockStainedGlassPane;
    public MixinBlockStairs _blockStairs;
    public MixinBlockTorch _blockTorch;
    public MixinBlockTrapDoor _blockTrapDoor;
    public MixinBlockTripWire _blockTripwire;
    public MixinBlockTripWireHook _blockTripWireHook;
    public MixinBlockVine _blockVine;
    public MixinBlockWeb _blockWeb;

    public EventBlockGetRenderLayer(final MixinBlock block) {
        this._block = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockBeacon block) {
        this._blockBeacon = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockBed block) {
        this._blockBed = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockBrewingStand block) {
        this._blockBrewingStand = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockBush block) {
        this._blockBush = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockCactus block) {
        this._blockCactus = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockCake block) {
        this._blockCake = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockCocoa block) {
        this._blockCocoa = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockChorusFlower block) {
        this._blockChorusFlower = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockChorusPlant block) {
        this._blockChorusPlant = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockDoor block) {
        this._blockDoor = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockEndRod block) {
        this._blockEndRod = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockFire block) {
        this._blockFire = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockFlowerPot block) {
        this._blockFlowerPot = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockGrass block) {
        this._blockGrass = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockHopper block) {
        this._blockHopper = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockIce block) {
        this._blockIce = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockLadder block) {
        this._blockLadder = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockLiquid block) {
        this._blockLiquid = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockMobSpawner block) {
        this._blockMobSpawner = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockPane block) {
        this._blockPane = block;
    }
    public EventBlockGetRenderLayer(final MixinBlockPortal block) {
        this._blockPortal = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockRailBase block) {
        this._blockRailBase = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockRedstoneDiode block) {
        this._blockRedstoneDiode = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockRedstoneWire block) {
        this._blockRedstoneWire = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockReed block) {
        this._blockReed = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockSlime block) {
        this._blockSlime = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockStainedGlass block) {
        this._blockStainedGlass = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockStainedGlassPane block) {
        this._blockStainedGlassPane = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockStairs block) {
        this._blockStairs = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockTorch block) {
        this._blockTorch = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockTrapDoor block) {
        this._blockTrapDoor = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockTripWire block) {
        this._blockTripwire = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockTripWireHook block) {
        this._blockTripWireHook = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockVine block) {
        this._blockVine = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockWeb block) {
        this._blockWeb = block;
    }

    public EventBlockGetRenderLayer(final MixinBlockLeaves block) {
        this._blockLeaves = block;
    }

    public Block getBlock() {
        return this._block;
    }

    public void setLayer(final BlockRenderLayer layer) {
        this._layer = layer;
    }

    public BlockRenderLayer getBlockRenderLayer() {
        return this._layer;
    }
}
