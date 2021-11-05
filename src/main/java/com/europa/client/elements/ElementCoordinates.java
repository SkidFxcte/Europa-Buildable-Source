/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.elements;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.client.gui.font.FontManager;
import com.europa.client.modules.client.ModuleColor;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.DecimalFormat;
import net.minecraft.client.gui.GuiChat;

public class ElementCoordinates
extends Element {
    public DecimalFormat format = new DecimalFormat("#.#");
    public static ValueEnum color = new ValueEnum("Color", "Color", "The color of the Coordinates.", Colors.Gray);
    public static ValueEnum direction = new ValueEnum("Direction", "Direction", "Renders the direction you are facing on screen.", Directions.Normal);
    public static ValueBoolean nether = new ValueBoolean("Opposite", "Opposite", "Renders the coordinates of the opposite dimension that you are currently in.", true);
    public static ValueString firstSymbol = new ValueString("FirstSymbol", "FirstSymbol", "The first character to be rendered on the coordinates.", "[");
    public static ValueString secondSymbol = new ValueString("SecondSymbol", "SecondSymbol", "The first character to be rendered on the coordinates.", "]");
    public static ValueBoolean chatMove = new ValueBoolean("ChatMove", "ChatMove", "Moves the coordinates above the chat textbox when it is opened.", true);

    public ElementCoordinates() {
        super("Coordinates", "Renders your coordinates and direction on screen.");
    }

    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        this.frame.setWidth(Europa.FONT_MANAGER.getStringWidth(this.getCoordinatesText()));
        this.frame.setHeight(ElementCoordinates.direction.getValue().equals(Directions.Normal) ? (Europa.FONT_MANAGER.getHeight() * Float.intBitsToFloat(Float.floatToIntBits(0.7439812f) ^ 0x7F3E758D) + Float.intBitsToFloat(Float.floatToIntBits(4.2017727f) ^ 0x7F0674EC)) : Europa.FONT_MANAGER.getHeight());
        if (ElementCoordinates.direction.getValue().equals(Directions.Normal)) {
            Europa.FONT_MANAGER.drawString(this.getDirectionName() + " " + ElementCoordinates.firstSymbol.getValue() + this.getColor() + this.getFacing(ElementCoordinates.mc.player.getHorizontalFacing().getName()) + ChatFormatting.RESET + ElementCoordinates.secondSymbol.getValue(), this.frame.getX(), this.frame.getY() - ((ElementCoordinates.chatMove.getValue() && ElementCoordinates.mc.currentScreen instanceof GuiChat) ? 11 : 0), ModuleColor.getActualColor());
        }
        final FontManager font_MANAGER = Europa.FONT_MANAGER;
        final String coordinatesText = this.getCoordinatesText();
        final float x = this.frame.getX();
        float y;
        if (ElementCoordinates.direction.getValue().equals(Directions.Normal)) {
            y = this.frame.getY() + Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(5.720674f) ^ 0x7F370FC3) - ((ElementCoordinates.chatMove.getValue() && ElementCoordinates.mc.currentScreen instanceof GuiChat) ? 11 : 0);
        }
        else {
            final float y2 = this.frame.getY();
            int n = 0;
            Label_0822: {
                if (ElementCoordinates.chatMove.getValue()) {
                    if (ElementCoordinates.mc.currentScreen instanceof GuiChat) {
                        n = 12;
                        break Label_0822;
                    }
                }
                n = 0;
            }
            y = y2 - n;
        }
        font_MANAGER.drawString(coordinatesText, x, y, ModuleColor.getActualColor());
    }


    public String getCoordinatesText() {
        return (direction.getValue().equals((Object)Directions.Merged) ? this.getDirectionName() + " " + firstSymbol.getValue() + this.getColor() + this.getFacing(ElementCoordinates.mc.player.getHorizontalFacing().getName()) + ChatFormatting.RESET + secondSymbol.getValue() + " " : "") + "X: " + this.getColor() + this.format.format(ElementCoordinates.mc.player.posX) + ChatFormatting.RESET + " Y: " + this.getColor() + this.format.format(ElementCoordinates.mc.player.posY) + ChatFormatting.RESET + " Z: " + this.getColor() + this.format.format(ElementCoordinates.mc.player.posZ) + ChatFormatting.RESET + (nether.getValue() ? " " + firstSymbol.getValue() + this.getColor() + this.format.format(ElementCoordinates.mc.player.dimension == -1 ? ElementCoordinates.mc.player.posX * Double.longBitsToDouble(Double.doubleToLongBits(0.17893497952173382) ^ 0x7FE6E757669400DEL) : ElementCoordinates.mc.player.posX / Double.longBitsToDouble(Double.doubleToLongBits(0.13457276128336518) ^ 0x7FE139AE24527DF1L)) + ChatFormatting.RESET + ", " + this.getColor() + this.format.format(ElementCoordinates.mc.player.dimension == -1 ? ElementCoordinates.mc.player.posX * Double.longBitsToDouble(Double.doubleToLongBits(1.1626092686361902) ^ 0x7FD29A0C2D2D1A47L) : ElementCoordinates.mc.player.posX / Double.longBitsToDouble(Double.doubleToLongBits(0.018678801680366838) ^ 0x7FB320892961F0DFL)) + ChatFormatting.RESET + secondSymbol.getValue() : "");
    }

    public String getDirectionName() {
        return ElementCoordinates.mc.player.getHorizontalFacing().getName().substring(0, 1).toUpperCase() + ElementCoordinates.mc.player.getHorizontalFacing().getName().substring(1).toLowerCase();
    }

    public ChatFormatting getColor() {
        if (color.getValue().equals((Object)Colors.White)) {
            return ChatFormatting.WHITE;
        }
        if (color.getValue().equals((Object)Colors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }

    public String getFacing(final String input) {
        final String lowerCase = input.toLowerCase();
        switch (lowerCase) {
            case "north": {
                return "-Z";
            }
            case "east": {
                return "+X";
            }
            case "south": {
                return "+Z";
            }
            default: {
                return "-X";
            }
        }
    }


    public static enum Directions {
        None,
        Normal,
        Merged;

    }

    public static enum Colors {
        Normal,
        White,
        Gray;

    }
}

