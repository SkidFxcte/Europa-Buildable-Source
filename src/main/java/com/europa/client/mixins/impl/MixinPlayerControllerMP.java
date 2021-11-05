/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.api.manager.event.impl.player.EventPlayerDestroyBlock;
import com.europa.api.manager.event.impl.world.EventBlock;
import com.europa.api.manager.event.impl.world.EventClickBlock;
import com.europa.client.modules.player.ModuleReach;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerControllerMP.class})
public class MixinPlayerControllerMP {
    @Shadow
    public GameType currentGameType;
    public Minecraft mc;

    @Overwrite
    public float getBlockReachDistance() {
        if (Europa.getModuleManager().isModuleEnabled("Reach") && ModuleReach.mode.getValue().equals((Object)ModuleReach.modes.Add)) {
            return (float)(5.0 + ModuleReach.addAmount.getValue().doubleValue());
        }
        if (Europa.getModuleManager().isModuleEnabled("Reach") && ModuleReach.mode.getValue().equals((Object)ModuleReach.modes.Change)) {
            return (float)ModuleReach.changeAmount.getValue().doubleValue();
        }
        return 5.0f;
    }

    @Inject(method={"clickBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void clickBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
        EventClickBlock event = new EventClickBlock(pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }

    @Inject(method={"onPlayerDamageBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void onPlayerDamageBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
        EventBlock event = new EventBlock(pos, face);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }

    @Inject(method={"onPlayerDestroyBlock"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V")}, cancellable=true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        MinecraftForge.EVENT_BUS.post((Event)new EventPlayerDestroyBlock(pos));
    }
}

