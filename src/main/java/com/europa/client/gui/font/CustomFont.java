//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.font;

import java.awt.FontMetrics;
import java.awt.RenderingHints;
import net.minecraft.client.renderer.texture.TextureUtil;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.util.HashMap;
import java.awt.Font;

public class CustomFont
{
    public static boolean assumeNonVolatile;
    public static int gcTicks;
    public static int GC_TICKS;
    public static int CACHED_FONT_REMOVAL_TIME;
    public Font font;
    public int fontHeight;
    public CharLocation[] charLocations;
    public HashMap<String, CachedFont> cachedStrings;
    public int textureID;
    public int textureWidth;
    public int textureHeight;

    public CustomFont(final Font font) {
        this.fontHeight = -1;
        this.charLocations = null;
        this.cachedStrings = new HashMap<String, CachedFont>();
        this.textureID = 0;
        this.textureWidth = 0;
        this.textureHeight = 0;
        this.font = font;
        this.charLocations = new CharLocation[256];
        this.renderBitmap(0, 256);
    }

    public int getHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public void drawString(final String text, final double x, final double y, final int color) {
        final double scale = Double.longBitsToDouble(Double.doubleToLongBits(6.615448407929718) ^ 0x7FCA76381B81BBCFL);
        final double reverse = Double.longBitsToDouble(Double.doubleToLongBits(0.2351066577787205) ^ 0x7FDE17F9971DA0C3L);
        GlStateManager.pushMatrix();
        GlStateManager.scale(Double.longBitsToDouble(Double.doubleToLongBits(29.078499140215964) ^ 0x7FED14188507FDDFL), Double.longBitsToDouble(Double.doubleToLongBits(16.976301963401497) ^ 0x7FE0F9EEECEBD47CL), Double.longBitsToDouble(Double.doubleToLongBits(4.177890442298529) ^ 0x7FC0B628E97FC5CBL));
        GL11.glTranslated(x * Double.longBitsToDouble(Double.doubleToLongBits(0.969132070377238) ^ 0x7FEF03214278CCDDL), y * Double.longBitsToDouble(Double.doubleToLongBits(0.7475017061180943) ^ 0x7FE7EB88B2AF67C7L) - Double.longBitsToDouble(Double.doubleToLongBits(0.7570690305052032) ^ 0x7FE839E8D4DAB260L), Double.longBitsToDouble(Double.doubleToLongBits(1.3494038906871303E308) ^ 0x7FE8052AF282A96FL));
        GlStateManager.bindTexture(this.textureID);
        final float red = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.120077275f) ^ 0x7E8AEB13);
        final float green = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.015050172f) ^ 0x7F0994FF);
        final float blue = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.09046397f) ^ 0x7EC6452D);
        final float alpha = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.07047931f) ^ 0x7EEF5775);
        GlStateManager.color(red, green, blue, alpha);
        double currX = Double.longBitsToDouble(Double.doubleToLongBits(1.5746733532606273E308) ^ 0x7FEC07B5B085168DL);
        final CachedFont cached = this.cachedStrings.get(text);
        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());
            cached.setLastUsage(System.currentTimeMillis());
            GlStateManager.popMatrix();
            return;
        }
        int list = -1;
        if (CustomFont.assumeNonVolatile) {
            list = GL11.glGenLists(1);
            GL11.glNewList(list, 4865);
        }
        GL11.glBegin(7);
        for (final char ch : text.toCharArray()) {
            if (Character.getNumericValue(ch) >= this.charLocations.length) {
                GL11.glEnd();
                GlStateManager.scale(Double.longBitsToDouble(Double.doubleToLongBits(1.775826226404408) ^ 0x7FEC69C8C2DC938EL), Double.longBitsToDouble(Double.doubleToLongBits(1.2044081174990917) ^ 0x7FE34541723B20DEL), Double.longBitsToDouble(Double.doubleToLongBits(0.22849597015411957) ^ 0x7FDD3F5B1F8A34C7L));
                Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(ch), (float)currX * Float.intBitsToFloat(Float.floatToIntBits(26.218346f) ^ 0x7F51BF2C) + Float.intBitsToFloat(Float.floatToIntBits(27.870398f) ^ 0x7E5EF693), Float.intBitsToFloat(Float.floatToIntBits(0.14261268f) ^ 0x7E12090F), color, false);
                currX += Minecraft.getMinecraft().fontRenderer.getStringWidth(String.valueOf(ch)) * Double.longBitsToDouble(Double.doubleToLongBits(0.20191953364354756) ^ 0x7FD9D87FD0B61809L);
                GlStateManager.scale(Double.longBitsToDouble(Double.doubleToLongBits(28.4737501596925) ^ 0x7FEC7947B0C25AA4L), Double.longBitsToDouble(Double.doubleToLongBits(147.14406412028185) ^ 0x7FB2649C2C5BA467L), Double.longBitsToDouble(Double.doubleToLongBits(26.71160465005691) ^ 0x7FEAB62BB8EBAD0BL));
                GlStateManager.bindTexture(this.textureID);
                GlStateManager.color(red, green, blue, alpha);
                GL11.glBegin(7);
            }
            else if (this.charLocations.length > ch) {
                final CharLocation fontChar = this.charLocations[ch];
                if (fontChar != null) {
                    this.drawChar(fontChar, (float)currX, Float.intBitsToFloat(Float.floatToIntBits(1.0698629E38f) ^ 0x7EA0F99F));
                    currX += CharLocation.access$000(fontChar) - Double.longBitsToDouble(Double.doubleToLongBits(1.4705512228097988) ^ 0x7FD78760B810F96DL);
                }
            }
        }
        GL11.glEnd();
        if (CustomFont.assumeNonVolatile) {
            this.cachedStrings.put((String)text, new CachedFont(this, list, System.currentTimeMillis()));
            GL11.glEndList();
        }
        GlStateManager.popMatrix();
    }

    public void drawChar(final CharLocation ch, final float x, final float y) {
        final float width = (float)CharLocation.access$000(ch);
        final float height = (float)CharLocation.access$100(ch);
        final float srcX = (float)CharLocation.access$200(ch);
        final float srcY = (float)CharLocation.access$300(ch);
        final float renderX = srcX / this.textureWidth;
        final float renderY = srcY / this.textureHeight;
        final float renderWidth = width / this.textureWidth;
        final float renderHeight = height / this.textureHeight;
        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, y);
    }

    public void renderBitmap(final int startChar, final int stopChar) {
        final BufferedImage[] fontImages = new BufferedImage[stopChar];
        int rowHeight = 0;
        int charX = 0;
        int charY = 0;
        for (int targetChar = startChar; targetChar < stopChar; ++targetChar) {
            final BufferedImage fontImage = this.drawCharToImage((char)targetChar);
            final CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());
            if (CharLocation.access$100(fontChar) > this.fontHeight) {
                this.fontHeight = CharLocation.access$100(fontChar);
            }
            if (CharLocation.access$100(fontChar) > rowHeight) {
                rowHeight = CharLocation.access$100(fontChar);
            }
            if (this.charLocations.length > targetChar) {
                this.charLocations[targetChar] = fontChar;
                fontImages[targetChar] = fontImage;
                charX += CharLocation.access$000(fontChar);
                if (charX > 2048) {
                    if (charX > this.textureWidth) {
                        this.textureWidth = charX;
                    }
                    charX = 0;
                    charY += rowHeight;
                    rowHeight = 0;
                }
            }
        }
        this.textureHeight = charY + rowHeight;
        final BufferedImage bufferedImage = new BufferedImage(this.textureWidth, this.textureHeight, 2);
        final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.textureWidth, this.textureHeight);
        graphics2D.setColor(Color.WHITE);
        for (int targetChar2 = startChar; targetChar2 < stopChar; ++targetChar2) {
            if (fontImages[targetChar2] != null && this.charLocations[targetChar2] != null) {
                graphics2D.drawImage(fontImages[targetChar2], CharLocation.access$200(this.charLocations[targetChar2]), CharLocation.access$300(this.charLocations[targetChar2]), null);
            }
        }
        this.textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedImage, true, true);
    }

    public BufferedImage drawCharToImage(final char ch) {
        final Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(this.font);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int charWidth = fontMetrics.charWidth(ch) + 8;
        if (charWidth <= 8) {
            charWidth = 7;
        }
        int charHeight = fontMetrics.getHeight() + 3;
        if (charHeight <= 0) {
            charHeight = this.font.getSize();
        }
        final BufferedImage fontImage = new BufferedImage(charWidth, charHeight, 2);
        final Graphics2D graphics = (Graphics2D)fontImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(this.font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
        return fontImage;
    }

    public int getStringWidth(final String text) {
        int width = 0;
        for (final char ch : text.toCharArray()) {
            int index;
            if (ch < this.charLocations.length) {
                index = ch;
            }
            else {
                index = 3;
            }
            if (this.charLocations.length > index) {
                final CharLocation fontChar = this.charLocations[index];
                if (fontChar != null) {
                    width += CharLocation.access$000(fontChar) - 8;
                }
            }
        }
        return width / 2;
    }

    public Font getFont() {
        return this.font;
    }

    static {
        CustomFont.CACHED_FONT_REMOVAL_TIME = 30000;
        CustomFont.GC_TICKS = 600;
    }

    private static class CharLocation
    {
        public int x;
        public int y;
        public int width;
        public int height;

        public CharLocation(final int x, final int y, final int width, final int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public static int access$000(final CharLocation x0) {
            return x0.width;
        }

        public static int access$100(final CharLocation x0) {
            return x0.height;
        }

        public static int access$200(final CharLocation x0) {
            return x0.x;
        }

        public static int access$300(final CharLocation x0) {
            return x0.y;
        }
    }

    public class CachedFont
    {
        public int displayList;
        public long lastUsage;
        public boolean deleted;
        public CustomFont this$0;

        public CachedFont(final CustomFont this$0, final int displayList, final long lastUsage, final boolean deleted) {
            this.this$0 = this$0;
            this.deleted = false;
            this.displayList = displayList;
            this.lastUsage = lastUsage;
            this.deleted = deleted;
        }

        public CachedFont(final CustomFont this$0, final int displayList, final long lastUsage) {
            this.this$0 = this$0;
            this.deleted = false;
            this.displayList = displayList;
            this.lastUsage = lastUsage;
        }

        public void finalize() {
            if (!this.deleted) {
                GL11.glDeleteLists(this.displayList, 1);
            }
        }

        public int getDisplayList() {
            return this.displayList;
        }

        public long getLastUsage() {
            return this.lastUsage;
        }

        public boolean isDeleted() {
            return this.deleted;
        }

        public void setLastUsage(final long lastUsage) {
            this.lastUsage = lastUsage;
        }

        public void setDeleted(final boolean deleted) {
            this.deleted = deleted;
        }
    }
}
