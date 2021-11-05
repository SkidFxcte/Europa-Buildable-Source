/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.client.gui.special.EuropaMainMenu;
import com.europa.client.modules.client.ModuleGUI;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class}, priority=999)
public abstract class MixinMinecraft {
    private long lastFrame = this.getTime();

    public long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    @Shadow
    public abstract void displayGuiScreen(@Nullable GuiScreen var1);

    @Inject(method={"runTick()V"}, at={@At(value="RETURN")})
    private void runTick(CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && ModuleGUI.customMenu.getValue()) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new EuropaMainMenu());
        }
    }

    @Inject(method={"runGameLoop"}, at={@At(value="HEAD")})
    private void onRunGameLoopPre(CallbackInfo ci) {
        long currentTime = this.getTime();
        int deltaTime = (int)(currentTime - this.lastFrame);
        this.lastFrame = currentTime;
        RenderUtils.deltaTime = deltaTime;
    }

    @Inject(method={"displayGuiScreen"}, at={@At(value="HEAD")})
    private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
        if (screen instanceof GuiMainMenu) {
            this.displayGuiScreen(new EuropaMainMenu());
        }
    }

    @Redirect(method={"sendClickBlockToController"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveWrapper(EntityPlayerSP playerSP) {
        return !Europa.getModuleManager().isModuleEnabled("MultiTask") && playerSP.isHandActive();
    }

    @Redirect(method={"rightClickMouse"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal=0))
    private boolean isHittingBlockHook(PlayerControllerMP playerControllerMP) {
        return !Europa.getModuleManager().isModuleEnabled("MultiTask") && playerControllerMP.getIsHittingBlock();
    }

    @Redirect(method={"createDisplay"}, at=@At(value="INVOKE", target="Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private void createDisplay(String title) {
        Display.setTitle((String)"Europa 1.1.5 | Waiting for Jupiter to appear in the sky");
    }
}

