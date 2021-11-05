/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.font;

import com.europa.Europa;
import com.europa.api.utilities.IMinecraft;
import com.europa.client.gui.font.FontRenderer;
import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;

public class FontManager
implements IMinecraft {
    public FontRenderer FONT_RENDERER;

    public void load() {
        this.FONT_RENDERER = new FontRenderer(FontManager.getFont("Lato-Medium.ttf", Float.intBitsToFloat(Float.floatToIntBits(0.042888146f) ^ 0x7F0FAB7B)));
    }

    public float drawString(final String text, final float x, final float y, final Color color) {
        if (Europa.MODULE_MANAGER.isModuleEnabled("Font")) {
            return (float)this.FONT_RENDERER.drawStringWithShadow(text, x, y, color.getRGB());
        }
        return (float)FontManager.mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
    }

    public float getStringWidth(final String text) {
        if (Europa.MODULE_MANAGER.isModuleEnabled("Font")) {
            return (float)this.FONT_RENDERER.getStringWidth(text);
        }
        return (float)FontManager.mc.fontRenderer.getStringWidth(text);
    }

    public float getHeight() {
        if (Europa.MODULE_MANAGER.isModuleEnabled("Font")) {
            return this.FONT_RENDERER.getHeight();
        }
        return FontManager.mc.fontRenderer.FONT_HEIGHT;
    }

    public static Font getFont(final String fontName, final float size) {
        try {
            final InputStream inputStream = FontManager.class.getResourceAsStream("/assets/europa/fonts/" + fontName);
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            inputStream.close();
            return awtClientFont;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, (int)size);
        }
    }
}


