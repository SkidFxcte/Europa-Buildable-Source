/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.elements;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.client.modules.client.ModuleColor;
import com.mojang.realmsclient.gui.ChatFormatting;

public class ElementWatermark
extends Element {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "The mode for the watermark.", Modes.Normal);
    public static ValueString customValue = new ValueString("CustomValue", "CustomValue", "The value for the Custom Watermark.", "Europa");
    public static ValueEnum version = new ValueEnum("Version", "Version", "Renders the Version on the watermark.", Versions.Normal);
    public static ValueEnum versionColor = new ValueEnum("VersionColor", "VersionColor", "The color for the version.", VersionColors.Normal);

    public ElementWatermark() {
        super("Watermark", "The client's watermark.");
    }

    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        this.frame.setWidth(Europa.FONT_MANAGER.getStringWidth(this.getText()));
        this.frame.setHeight(Europa.FONT_MANAGER.getHeight());
        Europa.FONT_MANAGER.drawString(this.getText(), this.frame.getX(), this.frame.getY(), ModuleColor.getActualColor());
    }


    public String getText() {
        return (mode.getValue().equals((Object)Modes.Custom) ? customValue.getValue() : "Europa") + (!version.getValue().equals((Object)Versions.None) ? " " + this.getVersionColor() + (version.getValue().equals((Object)Versions.Normal) ? "v" : "") + "1.1.5" : "");
    }

    public ChatFormatting getVersionColor() {
        if (versionColor.getValue().equals((Object)VersionColors.White)) {
            return ChatFormatting.WHITE;
        }
        if (versionColor.getValue().equals((Object)VersionColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }

    public static enum VersionColors {
        Normal,
        White,
        Gray;

    }

    public static enum Versions {
        None,
        Simple,
        Normal;

    }

    public static enum Modes {
        Normal,
        Custom;

    }
}

