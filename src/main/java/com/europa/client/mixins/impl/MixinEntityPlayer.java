/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.api.manager.event.impl.player.EventPlayerJump;
import com.europa.api.manager.event.impl.player.EventPlayerTravel;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityPlayer.class})
public abstract class MixinEntityPlayer
extends EntityLivingBase {
    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract String getName();

    @Inject(method={"jump"}, at={@At(value="HEAD")}, cancellable=true)
    public void onJump(CallbackInfo ci) {
        if (Minecraft.getMinecraft().player.getName() == this.getName()) {
            MinecraftForge.EVENT_BUS.post((Event)new EventPlayerJump());
        }
    }

    @Inject(method={"travel"}, at={@At(value="HEAD")}, cancellable=true)
    public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
        EventPlayerTravel event = new EventPlayerTravel(strafe, vertical, forward);
        MinecraftForge.EVENT_BUS.post((Event)new EventPlayerTravel(strafe, vertical, forward));
        if (event.isCancelled()) {
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            info.cancel();
        }
    }
}

