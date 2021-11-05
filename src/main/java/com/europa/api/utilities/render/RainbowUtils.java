//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.render;

import java.awt.Color;
import com.europa.api.utilities.IMinecraft;

public class RainbowUtils implements IMinecraft
{
    public static int toARGB(final int r, final int g, final int b, final int a) {
        return new Color(r, g, b, a).getRGB();
    }

    public static int toRGBA(final int r, final int g, final int b) {
        return toRGBA(r, g, b, 255);
    }

    public static int toRGBA(final int r, final int g, final int b, final int a) {
        return (r << 16) + (g << 8) + b + (a << 24);
    }

    public static int toRGBA(final float r, final float g, final float b, final float a) {
        return toRGBA((int)(r * Float.intBitsToFloat(Float.floatToIntBits(0.014847554f) ^ 0x7F0C4328)), (int)(g * Float.intBitsToFloat(Float.floatToIntBits(0.01154858f) ^ 0x7F423641)), (int)(b * Float.intBitsToFloat(Float.floatToIntBits(0.010420265f) ^ 0x7F55B9C2)), (int)(a * Float.intBitsToFloat(Float.floatToIntBits(0.010057102f) ^ 0x7F5BC68B)));
    }

    public static int toRGBA(final float[] colors) {
        if (colors.length != 4) {
            throw new IllegalArgumentException("colors[] must have a length of 4!");
        }
        return toRGBA(colors[0], colors[1], colors[2], colors[3]);
    }

    public static int anyRainbow(final int delay, final int sat, final int bri) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / Double.longBitsToDouble(Double.doubleToLongBits(0.022914943232476444) ^ 0x7FA37703CF17BD3FL));
        rainbowState %= Double.longBitsToDouble(Double.doubleToLongBits(0.05937126820716549) ^ 0x7FD8E5E92E8B2C09L);
        return Color.getHSBColor((float)(rainbowState / Double.longBitsToDouble(Double.doubleToLongBits(0.017367872616212837) ^ 0x7FE748E2338D0898L)), sat / Float.intBitsToFloat(Float.floatToIntBits(0.009182317f) ^ 0x7F69716E), bri / Float.intBitsToFloat(Float.floatToIntBits(0.012179142f) ^ 0x7F388B06)).getRGB();
    }

    public static Color anyRainbowColor(final int delay, final int sat, final int bri) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / Double.longBitsToDouble(Double.doubleToLongBits(0.7392742866635912) ^ 0x7FD3A8228C7FCDA5L));
        rainbowState %= Double.longBitsToDouble(Double.doubleToLongBits(0.027469858334671717) ^ 0x7FEAA10EFCB152F7L);
        return Color.getHSBColor((float)(rainbowState / Double.longBitsToDouble(Double.doubleToLongBits(0.0961027319223821) ^ 0x7FCE1A304AA9B2EBL)), sat / Float.intBitsToFloat(Float.floatToIntBits(0.014070428f) ^ 0x7F1987A7), bri / Float.intBitsToFloat(Float.floatToIntBits(0.009068173f) ^ 0x7F6B92AD));
    }

    public static int toRGBA(final double[] colors) {
        if (colors.length != 4) {
            throw new IllegalArgumentException("colors[] must have a length of 4!");
        }
        return toRGBA((float)colors[0], (float)colors[1], (float)colors[2], (float)colors[3]);
    }

    public static int toRGBA(final Color color) {
        return toRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color rainbowNormal(final float speed, final float off) {
        double time = System.currentTimeMillis() / (double)speed;
        time += off;
        time %= Double.longBitsToDouble(Double.doubleToLongBits(0.09226162585257923) ^ 0x7FD87E7539B66DFFL);
        return Color.getHSBColor((float)(time / Double.longBitsToDouble(Double.doubleToLongBits(0.012411825080666463) ^ 0x7FE68B5EF670EAA1L)), Float.intBitsToFloat(Float.floatToIntBits(4.9533916f) ^ 0x7F1E822F), Float.intBitsToFloat(Float.floatToIntBits(26.237951f) ^ 0x7E51E753));
    }

    public static Color astolfo(final int index, final int speed, final float saturation, final float brightness, final float opacity) {
        int angle = (int)((System.currentTimeMillis() / speed + index) % ((long)59358098 ^ 0x389BAFAL));
        angle = ((angle > 180) ? (360 - angle) : angle) + 180;
        final float hue = angle / Float.intBitsToFloat(Float.floatToIntBits(0.019563697f) ^ 0x7F14440C);
        final int color = Color.HSBtoRGB(brightness, saturation, hue);
        final Color obj = new Color(color);
        return new Color(obj.getRed(), obj.getGreen(), obj.getBlue(), Math.max(0, Math.min(255, (int)(opacity * Float.intBitsToFloat(Float.floatToIntBits(0.052466433f) ^ 0x7E29E70B)))));
    }

    public static Color interpolatedGradient(final double x, final double minX, final double maxX, final Color from, final Color to) {
        final double range = maxX - minX;
        final double p = (x - minX) / range;
        return new Color(from.getRed() * (int)p + to.getRed() * (int)(Double.longBitsToDouble(Double.doubleToLongBits(632.8699769040103) ^ 0x7F73C6F5B67377FFL) - p), from.getGreen() * (int)p + to.getGreen() * (int)(Double.longBitsToDouble(Double.doubleToLongBits(16.31921111884513) ^ 0x7FC051B7D1E3F59BL) - p), from.getBlue() * (int)p + to.getBlue() * (int)(Double.longBitsToDouble(Double.doubleToLongBits(5.21714459297351) ^ 0x7FE4DE5B26F54C3EL) - p), 255);
    }

    public static Color astolfoRainbow(final Color color, final int index, final int count) {
        final float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((System.currentTimeMillis() % ((long)1230675006 ^ 0x495A9BEEL) / Float.intBitsToFloat(Float.floatToIntBits(0.0013786979f) ^ 0x7ECEB56D) + index / (float)count * Float.intBitsToFloat(Float.floatToIntBits(0.09192204f) ^ 0x7DBC419F)) % Float.intBitsToFloat(Float.floatToIntBits(0.7858098f) ^ 0x7F492AD5) - Float.intBitsToFloat(Float.floatToIntBits(6.46708f) ^ 0x7F4EF252));
        brightness = Float.intBitsToFloat(Float.floatToIntBits(18.996923f) ^ 0x7E97F9B3) + Float.intBitsToFloat(Float.floatToIntBits(2.7958195f) ^ 0x7F32EEB5) * brightness;
        hsb[2] = brightness % Float.intBitsToFloat(Float.floatToIntBits(0.8992331f) ^ 0x7F663424);
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color getGradientOffset(final Color color1, final Color color2, double offset) {
        if (offset > Double.longBitsToDouble(Double.doubleToLongBits(14.635660535507155) ^ 0x7FDD45754C36B8DFL)) {
            final double left = offset % Double.longBitsToDouble(Double.doubleToLongBits(9.734916040076966) ^ 0x7FD37846EA4ADF6FL);
            final int off = (int)offset;
            offset = ((off % 2 == 0) ? left : (Double.longBitsToDouble(Double.doubleToLongBits(10.358130487678105) ^ 0x7FD4B75CE1188E55L) - left));
        }
        final double inverse_percent = Double.longBitsToDouble(Double.doubleToLongBits(18.65536939116546) ^ 0x7FC2A7C649D5DAE7L) - offset;
        final int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offset);
        final int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        final int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    public static Color getGradientAlpha(final Color color1, final Color color2, double offset) {
        if (offset > Double.longBitsToDouble(Double.doubleToLongBits(7.715984796512777) ^ 0x7FEEDD2B1E55D287L)) {
            final double left = offset % Double.longBitsToDouble(Double.doubleToLongBits(6.822523680431255) ^ 0x7FEB4A43A5CE8D1AL);
            final int off = (int)offset;
            offset = ((off % 2 == 0) ? left : (Double.longBitsToDouble(Double.doubleToLongBits(7.22625564608858) ^ 0x7FECE7AF8F61F176L) - left));
        }
        final double inverse_percent = Double.longBitsToDouble(Double.doubleToLongBits(65.90898138550982) ^ 0x7FA07A2CC042DBFFL) - offset;
        final int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offset);
        final int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        final int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        final int alphaPart = (int)(color1.getAlpha() * inverse_percent + color2.getAlpha() * offset);
        return new Color(redPart, greenPart, bluePart, alphaPart);
    }
}
