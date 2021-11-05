//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.special;

import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.image.BufferedImage;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import com.europa.Europa;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public class EuropaMainMenu extends GuiScreen
{
    public ResourceLocation resourceLocation;
    public int y;
    public int x;
    public int singleplayerWidth;
    public int multiplayerWidth;
    public int settingsWidth;
    public int exitWidth;
    public int textHeight;
    public float xOffset;
    public float yOffset;

    public EuropaMainMenu() {
        this.resourceLocation = new ResourceLocation("europa:mainmenu.png");
    }

    public void initGui() {
        this.x = this.width / 2;
        this.y = this.height / 4 + 48;
        this.buttonList.add(new TextButton(0, this.x, this.y + 20, "Singleplayer"));
        this.buttonList.add(new TextButton(1, this.x, this.y + 44, "Multiplayer"));
        this.buttonList.add(new TextButton(2, this.x, this.y + 66, "Options"));
        this.buttonList.add(new TextButton(2, this.x, this.y + 88, "Quit"));
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void updateScreen() {
        super.updateScreen();
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (isHovered(this.x - (int)Europa.FONT_MANAGER.getStringWidth("Singleplayer") / 2, this.y + 20, (int)Europa.FONT_MANAGER.getStringWidth("Singleplayer"), (int)Europa.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen((GuiScreen)new GuiWorldSelection((GuiScreen)this));
        }
        else if (isHovered(this.x - (int)Europa.FONT_MANAGER.getStringWidth("Multiplayer") / 2, this.y + 44, (int)Europa.FONT_MANAGER.getStringWidth("Multiplayer"), (int)Europa.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
        }
        else if (isHovered(this.x - (int)Europa.FONT_MANAGER.getStringWidth("Options") / 2, this.y + 66, (int)Europa.FONT_MANAGER.getStringWidth("Options"), (int)Europa.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
        }
        else if (isHovered(this.x - (int)Europa.FONT_MANAGER.getStringWidth("Quit") / 2, this.y + 88, (int)Europa.FONT_MANAGER.getStringWidth("Quit"), (int)Europa.FONT_MANAGER.getHeight(), mouseX, mouseY)) {
            this.mc.shutdown();
        }
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.xOffset = Float.intBitsToFloat(Float.floatToIntBits(-110.63207f) ^ 0x7D5D439F) * ((mouseX - this.width / Float.intBitsToFloat(Float.floatToIntBits(0.42138848f) ^ 0x7ED7C03B)) / (this.width / Float.intBitsToFloat(Float.floatToIntBits(0.056305815f) ^ 0x7F66A0ED)));
        this.yOffset = Float.intBitsToFloat(Float.floatToIntBits(-5.81684f) ^ 0x7F3A238E) * ((mouseY - this.height / Float.intBitsToFloat(Float.floatToIntBits(0.8170179f) ^ 0x7F512816)) / (this.height / Float.intBitsToFloat(Float.floatToIntBits(0.3316505f) ^ 0x7F39CE18)));
        this.x = this.width / 2;
        this.y = this.height / 4 + 48;
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        this.mc.getTextureManager().bindTexture(this.resourceLocation);
        drawCompleteImage(Float.intBitsToFloat(Float.floatToIntBits(-0.55055946f) ^ 0x7E8CF177) + this.xOffset, Float.intBitsToFloat(Float.floatToIntBits(-0.016965298f) ^ 0x7D9AFACF) + this.yOffset, (float)(this.width + 32), (float)(this.height + 18));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public static void drawCompleteImage(final float posX, final float posY, final float width, final float height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, Float.intBitsToFloat(Float.floatToIntBits(3.0431784E38f) ^ 0x7F64F17C));
        GL11.glBegin(7);
        GL11.glTexCoord2f(Float.intBitsToFloat(Float.floatToIntBits(1.1640711E38f) ^ 0x7EAF2663), Float.intBitsToFloat(Float.floatToIntBits(1.524874E38f) ^ 0x7EE57003));
        GL11.glVertex3f(Float.intBitsToFloat(Float.floatToIntBits(1.6939362E38f) ^ 0x7EFEE00D), Float.intBitsToFloat(Float.floatToIntBits(1.7422403E38f) ^ 0x7F031254), Float.intBitsToFloat(Float.floatToIntBits(1.5163773E38f) ^ 0x7EE428BB));
        GL11.glTexCoord2f(Float.intBitsToFloat(Float.floatToIntBits(7.240035E37f) ^ 0x7E59DF33), Float.intBitsToFloat(Float.floatToIntBits(4.7188916f) ^ 0x7F170129));
        GL11.glVertex3f(Float.intBitsToFloat(Float.floatToIntBits(3.0378397E38f) ^ 0x7F648AAA), height, Float.intBitsToFloat(Float.floatToIntBits(1.969856E38f) ^ 0x7F14320C));
        GL11.glTexCoord2f(Float.intBitsToFloat(Float.floatToIntBits(11.200683f) ^ 0x7EB335FF), Float.intBitsToFloat(Float.floatToIntBits(7.7575216f) ^ 0x7F783D9E));
        GL11.glVertex3f(width, height, Float.intBitsToFloat(Float.floatToIntBits(1.6734088E38f) ^ 0x7EFBC95D));
        GL11.glTexCoord2f(Float.intBitsToFloat(Float.floatToIntBits(5.5092797f) ^ 0x7F304C05), Float.intBitsToFloat(Float.floatToIntBits(1.7766657E38f) ^ 0x7F05A956));
        GL11.glVertex3f(width, Float.intBitsToFloat(Float.floatToIntBits(2.0478936E38f) ^ 0x7F1A10FF), Float.intBitsToFloat(Float.floatToIntBits(2.7254236E38f) ^ 0x7F4D09C0));
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public BufferedImage parseBackground(final BufferedImage background) {
        int width;
        int srcWidth;
        int srcHeight;
        int height;
        for (width = 1920, srcWidth = background.getWidth(), srcHeight = background.getHeight(), height = 1080; width < srcWidth || height < srcHeight; width *= 2, height *= 2) {}
        final BufferedImage imgNew = new BufferedImage(width, height, 2);
        final Graphics g = imgNew.getGraphics();
        g.drawImage(background, 0, 0, null);
        g.dispose();
        return imgNew;
    }

    public static boolean isHovered(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        if (mouseX >= x) {
            if (mouseX <= x + width) {
                if (mouseY >= y) {
                    if (mouseY < y + height) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isCustomFont() {
        return Europa.getModuleManager().isModuleEnabled("Font");
    }

    private static class TextButton extends GuiButton
    {
        public TextButton(final int buttonId, final int x, final int y, final String buttonText) {
            super(buttonId, x, y, (int)Europa.FONT_MANAGER.getStringWidth(buttonText), (int)Europa.FONT_MANAGER.getHeight(), buttonText);
        }

        public void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partialTicks) {
            if (this.visible) {
                this.enabled = true;
                this.hovered = (mouseX >= this.x - Europa.FONT_MANAGER.getStringWidth(this.displayString) / Float.intBitsToFloat(Float.floatToIntBits(0.36750862f) ^ 0x7EBC2A17) && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
                this.drawGradientRect(this.x - 40 + (this.hovered ? -2 : 0), this.y - 5 + (this.hovered ? -2 : 0), this.x + 40 + (this.hovered ? 2 : 0), (int)(this.y + Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(0.26383278f) ^ 0x7E271517) + (this.hovered ? 2 : 0)), new Color(40, 71, 237).getRGB(), new Color(239, 151, 244).getRGB());
                RenderUtils.drawOutlineLine(this.x - 40 + (this.hovered ? -2 : 0), this.y - 5 + (this.hovered ? -2 : 0), this.x + 40 + (this.hovered ? 2 : 0), this.y + Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(1.328367f) ^ 0x7F0A07EE) + (this.hovered ? 2 : 0), Float.intBitsToFloat(Float.floatToIntBits(0.6850277f) ^ 0x7F2F5DFA), new Color(0, 0, 0).getRGB());
                Europa.FONT_MANAGER.drawString(this.displayString, (float)(this.x - (int)Europa.FONT_MANAGER.getStringWidth(this.displayString) / 2), (float)this.y, Color.WHITE);
            }
        }

        public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
            return this.enabled && this.visible && mouseX >= this.x - Europa.FONT_MANAGER.getStringWidth(this.displayString) / Float.intBitsToFloat(Float.floatToIntBits(0.46001193f) ^ 0x7EEB86AF) && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        }
    }
}
