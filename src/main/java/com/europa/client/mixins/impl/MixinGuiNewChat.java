/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import com.europa.client.modules.misc.ModuleChatModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={GuiNewChat.class})
public abstract class MixinGuiNewChat {
    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void drawBackground(int left, int top, int right, int bottom, int colour) {
        if (Europa.getModuleManager().isModuleEnabled("ChatModifier") && ModuleChatModifier.background.getValue()) {
            Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, (int)0);
        } else {
            Gui.drawRect((int)left, (int)top, (int)right, (int)bottom, (int)colour);
        }
    }

    @Redirect(method={"drawChat"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
        if (text.contains("\u00a7+")) {
            Europa.CHAT_MANAGER.drawRainbowString(text, x, y, true);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
        }
        return 0;
    }
}

