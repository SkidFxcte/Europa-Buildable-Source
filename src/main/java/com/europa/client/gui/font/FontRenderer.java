//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.font;

import net.minecraft.util.ChatAllowedCharacters;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import com.europa.api.utilities.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL20;
import java.awt.Font;

public class FontRenderer
{
    public Font font;
    public CustomFont defaultFont;
    public CustomFont boldFont;
    public CustomFont italicFont;
    public CustomFont boldItalicFont;

    public FontRenderer(final Font font) {
        this.font = font;
        this.defaultFont = new CustomFont(font);
        this.boldFont = new CustomFont(font.deriveFont(1));
        this.italicFont = new CustomFont(font.deriveFont(2));
    }

    public int getHeight() {
        return this.defaultFont.getHeight() / 2;
    }

    public int getSize() {
        return this.defaultFont.getFont().getSize();
    }

    public void drawString(final String s, final float x, final float y, final int color) {
        this.drawString(s, x, y, color, false);
    }

    public int drawStringWithShadow(final String text, final float x, final float y, final int color) {
        return this.drawString(text, x, y, color, true);
    }

    public void drawCenteredString(final String s, final float x, final float y, final int color, final boolean shadow) {
        this.drawString(s, x - this.getStringWidth(s) / Float.intBitsToFloat(Float.floatToIntBits(0.06558233f) ^ 0x7D865007), y, color, shadow);
    }

    public void drawCenteredString(final String s, final float x, final float y, final int color) {
        this.drawString(s, x - this.getStringWidth(s) / Float.intBitsToFloat(Float.floatToIntBits(0.9740728f) ^ 0x7F795CD6), y, color);
    }

    public int drawString(final String text, final float x, final float y, final int color, final boolean dropShadow) {
        final float currY = y - Float.intBitsToFloat(Float.floatToIntBits(0.5678633f) ^ 0x7F515F7D);
        if (text.contains("\n")) {
            final String[] parts = text.split("\n");
            float newY = Float.intBitsToFloat(Float.floatToIntBits(2.5433182E38f) ^ 0x7F3F5687);
            for (final String s : parts) {
                this.drawText(s, x, currY + newY, color, dropShadow);
                newY += this.getHeight();
            }
            return 0;
        }
        if (dropShadow) {
            GL20.glUseProgram(0);
            final int alpha = 1 - (color >> 24 & 0xFF) / 255;
            this.drawText(text, x + Float.intBitsToFloat(Float.floatToIntBits(4.1587253f) ^ 0x7F051447), currY + Float.intBitsToFloat(Float.floatToIntBits(7.4003906f) ^ 0x7F6CD000), -16777216, true);
        }
        return this.drawText(text, x, currY, color, false);
    }

    public int drawText(final String text, final float x, final float y, final int color, final boolean ignoreColor) {
        if (text == null) {
            return 0;
        }
        if (text.isEmpty()) {
            return (int)x;
        }
        GlStateManager.translate(x - Double.longBitsToDouble(Double.doubleToLongBits(4.38206358885917) ^ 0x7FE9873BAD6C91F2L), y + Double.longBitsToDouble(Double.doubleToLongBits(2.232718711342534) ^ 0x7FE1DC9BA0B3114DL), Double.longBitsToDouble(Double.doubleToLongBits(4.631075733333635E307) ^ 0x7FD07CB79FC5E477L));
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableTexture2D();
        int currentColor = color;
        if ((currentColor & 0xFC000000) == 0x0) {
            currentColor |= 0xFF000000;
        }
        final int alpha = currentColor >> 24 & 0xFF;
        if (text.contains("§")) {
            final String[] parts = text.split("§");
            CustomFont currentFont = this.defaultFont;
            double width = Double.longBitsToDouble(Double.doubleToLongBits(1.0591535192481754E307) ^ 0x7FAE2A6AE450258FL);
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikeThrough = false;
            boolean underline = false;
            for (int index = 0; index < parts.length; ++index) {
                final String part = parts[index];
                if (!part.isEmpty()) {
                    if (index == 0) {
                        currentFont.drawString(part, width, Double.longBitsToDouble(Double.doubleToLongBits(1.7403185334101932E308) ^ 0x7FEEFA8BF6FD04E3L), currentColor);
                        width += currentFont.getStringWidth(part);
                    }
                    else {
                        final String words = part.substring(1);
                        final char type = part.charAt(0);
                        final int colorIndex = "0123456789abcdefklmnor".indexOf(type);
                        switch (colorIndex) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15: {
                                if (!ignoreColor) {
                                    currentColor = (ColorUtils.hexColors[colorIndex] | alpha << 24);
                                }
                                bold = false;
                                italic = false;
                                randomCase = false;
                                underline = false;
                                strikeThrough = false;
                                break;
                            }
                            case 16: {
                                randomCase = true;
                                break;
                            }
                            case 17: {
                                bold = true;
                                break;
                            }
                            case 18: {
                                strikeThrough = true;
                                break;
                            }
                            case 19: {
                                underline = true;
                                break;
                            }
                            case 20: {
                                italic = true;
                                break;
                            }
                            case 21: {
                                currentColor = color;
                                if ((currentColor & 0xFC000000) == 0x0) {
                                    currentColor |= 0xFF000000;
                                }
                                bold = false;
                                italic = false;
                                randomCase = false;
                                underline = false;
                                strikeThrough = false;
                                break;
                            }
                        }
                        if (bold && italic) {
                            currentFont = this.boldItalicFont;
                        }
                        else if (bold) {
                            currentFont = this.boldFont;
                        }
                        else if (italic) {
                            currentFont = this.italicFont;
                        }
                        else {
                            currentFont = this.defaultFont;
                        }
                        if (randomCase) {
                            currentFont.drawString(ColorUtils.randomMagicText(words), width, Double.longBitsToDouble(Double.doubleToLongBits(1.3058468768906788E308) ^ 0x7FE73EAE3A179A5DL), currentColor);
                        }
                        else {
                            currentFont.drawString(words, width, Double.longBitsToDouble(Double.doubleToLongBits(7.763767538645454E307) ^ 0x7FDBA3D272534C33L), currentColor);
                        }
                        if (strikeThrough) {
                            RenderUtils.drawLine(width / Double.longBitsToDouble(Double.doubleToLongBits(0.17700542826788399) ^ 0x7FC6A81D26D0012BL) + Double.longBitsToDouble(Double.doubleToLongBits(5.651080109473515) ^ 0x7FE69AB4BE850F34L), currentFont.getHeight() / Double.longBitsToDouble(Double.doubleToLongBits(0.713411139170128) ^ 0x7FEED44398EACF4BL), (width + currentFont.getStringWidth(words)) / Double.longBitsToDouble(Double.doubleToLongBits(0.10496540302048672) ^ 0x7FBADF033D2F381FL) + Double.longBitsToDouble(Double.doubleToLongBits(8.26073021668133) ^ 0x7FD0857E6E53735BL), currentFont.getHeight() / Double.longBitsToDouble(Double.doubleToLongBits(0.5296440772389001) ^ 0x7FE8F2D822C85B89L), this.getHeight() / Float.intBitsToFloat(Float.floatToIntBits(0.5113f) ^ 0x7E82E48F));
                        }
                        if (underline) {
                            RenderUtils.drawLine(width / Double.longBitsToDouble(Double.doubleToLongBits(0.6500457943014719) ^ 0x7FE4CD2CD66DAB56L) + Double.longBitsToDouble(Double.doubleToLongBits(4.716072070397632) ^ 0x7FE2DD41FF2FBF53L), currentFont.getHeight() / Double.longBitsToDouble(Double.doubleToLongBits(0.6659823723165498) ^ 0x7FE54FBA4399FC89L), (width + currentFont.getStringWidth(words)) / Double.longBitsToDouble(Double.doubleToLongBits(0.9891211714029902) ^ 0x7FEFA6E1715EA02EL) + Double.longBitsToDouble(Double.doubleToLongBits(14.815455926410829) ^ 0x7FDDA183706E871DL), currentFont.getHeight() / Double.longBitsToDouble(Double.doubleToLongBits(0.5080189605191866) ^ 0x7FE041B0FAA5B0B7L), this.getHeight() / Float.intBitsToFloat(Float.floatToIntBits(0.93159944f) ^ 0x7EEE7D4D));
                        }
                        width += currentFont.getStringWidth(words);
                    }
                }
            }
        }
        else {
            this.defaultFont.drawString(text, Double.longBitsToDouble(Double.doubleToLongBits(1.3252565393574456E308) ^ 0x7FE797212446ECCAL), Double.longBitsToDouble(Double.doubleToLongBits(1.725745634513711E308) ^ 0x7FEEB82384F45173L), currentColor);
        }
        GlStateManager.disableBlend();
        GlStateManager.translate(-(x - Double.longBitsToDouble(Double.doubleToLongBits(9.379497132989217) ^ 0x7FDAC24D72BE3A99L)), -(y + Double.longBitsToDouble(Double.doubleToLongBits(12.356911352889389) ^ 0x7FC8B6BD15B8764BL)), Double.longBitsToDouble(Double.doubleToLongBits(1.0390626317735182E308) ^ 0x7FE27EF53081780CL));
        GlStateManager.color(Float.intBitsToFloat(Float.floatToIntBits(4.432856f) ^ 0x7F0DD9F5), Float.intBitsToFloat(Float.floatToIntBits(7.113679f) ^ 0x7F63A342), Float.intBitsToFloat(Float.floatToIntBits(7.094939f) ^ 0x7F6309BE), Float.intBitsToFloat(Float.floatToIntBits(6.4096394f) ^ 0x7F4D1BC4));
        return (int)(x + this.getStringWidth(text));
    }

    public int getColorCode(final char charCode) {
        return ColorUtils.hexColors[getColorIndex(charCode)];
    }

    public int getStringWidth(final String text) {
        final String currentText = text;
        if (text.contains("§")) {
            final String[] parts = text.split("§");
            CustomFont currentFont = this.defaultFont;
            int width = 0;
            boolean bold = false;
            boolean italic = false;
            for (int index = 0; index < parts.length; ++index) {
                final String part = parts[index];
                if (!part.isEmpty()) {
                    if (index == 0) {
                        width += currentFont.getStringWidth(part);
                    }
                    else {
                        final String words = part.substring(1);
                        final char type = part.charAt(0);
                        final int colorIndex = getColorIndex(type);
                        if (colorIndex < 16) {
                            bold = false;
                            italic = false;
                        }
                        else if (colorIndex == 17) {
                            bold = true;
                        }
                        else if (colorIndex == 20) {
                            italic = true;
                        }
                        else if (colorIndex == 21) {
                            bold = false;
                            italic = false;
                        }
                        if (bold && italic) {
                            currentFont = this.boldItalicFont;
                        }
                        else if (bold) {
                            currentFont = this.boldFont;
                        }
                        else if (italic) {
                            currentFont = this.italicFont;
                        }
                        else {
                            currentFont = this.defaultFont;
                        }
                        width += currentFont.getStringWidth(words);
                    }
                }
            }
            return width / 2;
        }
        return this.defaultFont.getStringWidth(currentText) / 2;
    }

    public int getCharWidth(final char character) {
        return this.getStringWidth(String.valueOf(character));
    }

    public void onResourceManagerReload(final IResourceManager resourceManager) {
    }

    public void bindTexture(final ResourceLocation location) {
    }

    public static int getColorIndex(final char type) {
        switch (type) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                return type - '0';
            }
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f': {
                return type - 'a' + 10;
            }
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o': {
                return type - 'k' + 16;
            }
            case 'r': {
                return 21;
            }
            default: {
                return -1;
            }
        }
    }

    private static class ColorUtils
    {
        public static int[] hexColors;
        public static Random random;
        public static String magicAllowedCharacters;

        public ColorUtils() {
        }

        public static String randomMagicText(final String text) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (final char ch : text.toCharArray()) {
                if (ChatAllowedCharacters.isAllowedCharacter(ch)) {
                    final int index = ColorUtils.random.nextInt("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00c2£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00c2ª\u00c2º\u00c2¿\u00c2®\u00c2¬\u00c2½\u00c2¼\u00c2¡\u00c2«\u00c2»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00c2±\u2265\u2264\u2320\u2321\u00f7\u2248\u00c2°\u2219\u00c2·\u221a\u207f\u00c2²\u25a0\u0000".length());
                    stringBuilder.append("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00c2£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00c2ª\u00c2º\u00c2¿\u00c2®\u00c2¬\u00c2½\u00c2¼\u00c2¡\u00c2«\u00c2»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00c2±\u2265\u2264\u2320\u2321\u00f7\u2248\u00c2°\u2219\u00c2·\u221a\u207f\u00c2²\u25a0\u0000".charAt(index));
                }
            }
            return stringBuilder.toString();
        }

        static {
            ColorUtils.magicAllowedCharacters = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00c2£\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00c2ª\u00c2º\u00c2¿\u00c2®\u00c2¬\u00c2½\u00c2¼\u00c2¡\u00c2«\u00c2»\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00c2±\u2265\u2264\u2320\u2321\u00f7\u2248\u00c2°\u2219\u00c2·\u221a\u207f\u00c2²\u25a0\u0000";
            (ColorUtils.hexColors = new int[16])[0] = 0;
            ColorUtils.hexColors[1] = 170;
            ColorUtils.hexColors[2] = 43520;
            ColorUtils.hexColors[3] = 43690;
            ColorUtils.hexColors[4] = 11141120;
            ColorUtils.hexColors[5] = 11141290;
            ColorUtils.hexColors[6] = 16755200;
            ColorUtils.hexColors[7] = 11184810;
            ColorUtils.hexColors[8] = 5592405;
            ColorUtils.hexColors[9] = 5592575;
            ColorUtils.hexColors[10] = 5635925;
            ColorUtils.hexColors[11] = 5636095;
            ColorUtils.hexColors[12] = 16733525;
            ColorUtils.hexColors[13] = 16733695;
            ColorUtils.hexColors[14] = 16777045;
            ColorUtils.hexColors[15] = 16777215;
            ColorUtils.random = new Random();
        }
    }
}
