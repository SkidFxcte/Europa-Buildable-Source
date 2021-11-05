/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.misc;

import com.europa.api.utilities.render.RainbowUtils;
import com.europa.client.modules.client.ModuleColor;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class ChatManager {
    public Minecraft mc = Minecraft.getMinecraft();
    public GuiNewChat gameChatGUI;
    public static String prefix = ModuleColor.getBracketColour() + " [" + ModuleColor.getTextColor() + "Europa" + ModuleColor.getBracketColour() + "] " + ModuleColor.getTextColor();
    public static ChatManager INSTANCE;

    public ChatManager() {
        INSTANCE = this;
    }

    public void printChatMessage(final String message) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        if (this.gameChatGUI == null) {
            this.gameChatGUI = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        }
        this.gameChatGUI.printChatMessage((ITextComponent)new TextComponentString(message));
    }

    public void sendChatMessage(final String message) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketChatMessage(message));
    }

    public static void sendRawMessage(final String message) {
        Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(message));
    }

    public static void printChatNotifyClient(final String message) {
        if (!ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow) && !ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Gradient)) {
            ChatManager.INSTANCE.printChatMessage((ModuleColor.prefix.getValue() ? (ChatFormatting.GRAY + " [" + ModuleColor.getTextColor() + "Europa" + ChatFormatting.GRAY + "] ") : "") + ChatFormatting.RESET + message);
        }
        else {
            ChatManager.INSTANCE.printChatMessage((ModuleColor.prefix.getValue() ? ("§+" + ChatFormatting.GRAY + " [" + ModuleColor.getTextColor() + "Europa" + ChatFormatting.GRAY + "] ") : "") + "§r" + ChatFormatting.RESET + message);
        }
    }
    public static void printTextComponentMessage(final TextComponentString message) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        if (ChatManager.INSTANCE.gameChatGUI == null) {
            ChatManager.INSTANCE.gameChatGUI = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        }
        ChatManager.INSTANCE.gameChatGUI.printChatMessage((ITextComponent)message);
    }

    public static void sendClientMessage(final String string, final int id) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        ITextComponent component;
        if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Gradient)) {
            component = (ITextComponent)new TextComponentString((ModuleColor.prefix.getValue() ? ("§+" + ChatFormatting.GRAY + " [" + ModuleColor.getTextColor() + "Europa" + ChatFormatting.GRAY + "] ") : "") + "§r" + ChatFormatting.RESET + string);
        }
        else {
            component = (ITextComponent)new TextComponentString((ModuleColor.prefix.getValue() ? (ModuleColor.getBracketColour() + " [" + ModuleColor.getTextColor() + "Europa" + ModuleColor.getBracketColour() + "] ") : "") + ChatFormatting.RESET + string);
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(component, id);
    }

    public void drawRainbowString(final String text, final float x, final float y, final boolean shadow) {
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        final int[] counterChing = { 1 };
        for (int i = 0; i < text.length(); ++i) {
            Color color;
            if (ModuleColor.prefixMode.getValue().equals(ModuleColor.prefixModes.Rainbow)) {
                color = RainbowUtils.anyRainbowColor(counterChing[0] * 150, 180, 255);
            }
            else {
                color = RainbowUtils.getGradientOffset(new Color(ModuleColor.prefixStart.getValue().getRed(), ModuleColor.prefixStart.getValue().getGreen(), ModuleColor.prefixStart.getValue().getBlue()), new Color(ModuleColor.prefixEnd.getValue().getRed(), ModuleColor.prefixEnd.getValue().getGreen(), ModuleColor.prefixEnd.getValue().getBlue()), Math.abs((System.currentTimeMillis() % ((long)(-1919065669) ^ 0xFFFFFFFF8D9D666BL) / Float.intBitsToFloat(Float.floatToIntBits(4.989551E-4f) ^ 0x7E78CC4F) + Float.intBitsToFloat(Float.floatToIntBits(0.4183691f) ^ 0x7F76347A) / (counterChing[0] * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.9570142f) ^ 0x7F74FEE2)) % Float.intBitsToFloat(Float.floatToIntBits(0.21266626f) ^ 0x7E59C52F) - Float.intBitsToFloat(Float.floatToIntBits(5.415172f) ^ 0x7F2D4917)));
            }
            final char currentChar = text.charAt(i);
            final char nextChar = text.charAt(MathHelper.clamp(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                shouldRainbow = false;
            }
            else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else {
                if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
                    final String escapeString = text.substring(i);
                    this.drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
                    return;
                }
                this.drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? color.getRGB() : Color.WHITE.getRGB(), shadow);
                if (String.valueOf(currentChar).equals("§")) {
                    shouldContinue = true;
                }
                currentWidth += this.getStringWidth(String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    final int[] array = counterChing;
                    final int n = 0;
                    ++array[n];
                }
            }
        }
    }


    public int getStringWidth(final String text) {
        return this.mc.fontRenderer.getStringWidth(text);
    }

    public float drawString(final String text, final float x, final float y, final int color, final boolean shadow) {
        this.mc.fontRenderer.drawString(text, x, y, color, shadow);
        return x;
    }


}

