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
import com.europa.client.modules.client.ModuleColor;
import com.europa.client.modules.client.ModuleStreamerMode;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import net.minecraft.client.gui.ScaledResolution;

public class ElementWelcomer
extends Element {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "The mode for the Welcomer.", Modes.Shorter);
    public static ValueBoolean center = new ValueBoolean("Center", "Center", "Makes the Welcomer be positioned to the center.", true);
    public static ValueString customValue = new ValueString("CustomValue", "CustomValue", "The value for the Custom mode.", "Hello, <player>!");
    public static ValueEnum nameColor = new ValueEnum("NameColor", "NameColor", "The color for thet player's name.", NameColors.Normal);
    public static ValueBoolean emoji = new ValueBoolean("Emoji", "Emoji", "Renders a nice face after the welcomer text.", true);
    public static ValueString emojiValue = new ValueString("EmojiValue", "EmojiValue", "The value for the Emoji.", ">:)");

    public ElementWelcomer() {
        super("Welcomer", "Renders a nice greeting message.");
    }

    @Override
    public void onRender2D(EventRender2D eventRender2D) {
        this.frame.setWidth(Europa.FONT_MANAGER.getStringWidth(this.getText()));
        this.frame.setHeight(Europa.FONT_MANAGER.getHeight());
        ScaledResolution resolution = new ScaledResolution(mc);
        Europa.FONT_MANAGER.drawString(this.getText(), center.getValue() ? (float)resolution.getScaledWidth() / Float.intBitsToFloat(Float.floatToIntBits(0.478121f) ^ 0x7EF4CC47) - Europa.FONT_MANAGER.getStringWidth(this.getText()) / Float.intBitsToFloat(Float.floatToIntBits(0.07288914f) ^ 0x7D9546E7) : this.frame.getX(), this.frame.getY(), ModuleColor.getActualColor());
    }

    public String getText() {
        return this.getWelcomeMessage() + (emoji.getValue() ? " " + emojiValue.getValue() : "");
    }

    public String getWelcomeMessage() {
        switch ((Modes)ElementWelcomer.mode.getValue()) {
            case Short: {
                return "Greetings, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Time: {
                return this.getTimeOfDay() + ", " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Holiday: {
                return this.getHoliday() + ", " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Hebrew: {
                return "Shalom, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Long: {
                return "Welcome to Europa, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
            case Custom: {
                return ElementWelcomer.customValue.getValue().replaceAll("<player>", this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET);
            }
            default: {
                return "Hello, " + this.getNameColor() + this.getPlayerName() + ChatFormatting.RESET + "!";
            }
        }
    }


    public ChatFormatting getNameColor() {
        if (nameColor.getValue().equals((Object)NameColors.White)) {
            return ChatFormatting.WHITE;
        }
        if (nameColor.getValue().equals((Object)NameColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }

    public String getPlayerName() {
        if (Europa.MODULE_MANAGER.isModuleEnabled("StreamerMode")) {
            if (ModuleStreamerMode.hideYou.getValue()) {
                return ModuleStreamerMode.yourName.getValue();
            }
        }
        return ElementWelcomer.mc.player.getName();
    }

    public String getTimeOfDay() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(11);
        if (timeOfDay < 12) {
            return "Good morning";
        }
        if (timeOfDay < 16) {
            return "Good afternoon";
        }
        if (timeOfDay < 21) {
            return "Good evening";
        }
        return "Good night";
    }

    public String getHoliday() {
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
        int day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
        switch (month) {
            case 1: {
                if (day == 1) {
                    return "Happy New Years";
                }
            }
            case 2: {
                if (day == 14) {
                    return "Happy Valentines Day";
                }
                break;
            }
            case 10: {
                if (day != 31) break;
                return "Happy Halloween";
            }
            case 11: {
                LocalDate thanksGiving = Year.of(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))).atMonth(Month.NOVEMBER).atDay(1).with(TemporalAdjusters.lastInMonth(DayOfWeek.WEDNESDAY));
                if (thanksGiving.getDayOfMonth() == day) {
                    return "Happy Thanksgiving";
                }
            }
            case 12: {
                if (day != 25) break;
                return "Merry Christmas";
            }
        }
        return "No holiday is currently going on";
    }

    public static enum NameColors {
        Normal,
        White,
        Gray;

    }

    public static enum Modes {
        Shorter,
        Short,
        Holiday,
        Time,
        Hebrew,
        Long,
        Custom;

    }
}

