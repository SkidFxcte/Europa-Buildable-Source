//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.click.components.impl;

import net.minecraft.client.renderer.GlStateManager;
import com.europa.client.modules.client.ModuleColor;
import org.lwjgl.opengl.GL11;
import com.europa.Europa;
import com.europa.api.utilities.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.util.ResourceLocation;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.client.gui.click.components.Component;

public class ColorComponent extends Component
{
    public ValueColor setting;
    public boolean open;
    public ResourceLocation alphaBG;
    public boolean hueDragging;
    public float hueWidth;
    public boolean saturationDragging;
    public float satWidth;
    public boolean brightnessDragging;
    public float briWidth;
    public boolean alphaDragging;
    public float alphaWidth;

    public ColorComponent(final ValueColor setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.open = false;
        this.alphaBG = new ResourceLocation("europa:alpha_texture.png");
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        final float[] hsb = Color.RGBtoHSB(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), null);
        final Color color = Color.getHSBColor(hsb[0], Float.intBitsToFloat(Float.floatToIntBits(6.4887953f) ^ 0x7F4FA436), Float.intBitsToFloat(Float.floatToIntBits(4.629535f) ^ 0x7F142527));
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() + this.getWidth() - 12, this.getY() + 2, this.getX() + this.getWidth() - 2, this.getY() + 12, this.setting.getValue().getRGB());
        RenderUtils.drawOutline((float)(this.getX() + this.getWidth() - 12), (float)(this.getY() + 2), (float)(this.getX() + this.getWidth() - 2), (float)(this.getY() + 12), Float.intBitsToFloat(Float.floatToIntBits(2.7144578f) ^ 0x7F2DB9AD), new Color(20, 20, 20).getRGB());
        if (this.open) {
            Gui.drawRect(this.getX(), this.getY() + 14, this.getX() + this.getWidth(), this.getY() + 28, new Color(40, 40, 40).getRGB());
            for (float i = Float.intBitsToFloat(Float.floatToIntBits(1.3378998E38f) ^ 0x7EC94E07); i + Float.intBitsToFloat(Float.floatToIntBits(13.8331995f) ^ 0x7EDD54C9) < Float.intBitsToFloat(Float.floatToIntBits(0.07128618f) ^ 0x7F51FE7D); i += Float.intBitsToFloat(Float.floatToIntBits(3.807338f) ^ 0x7E95CD0B)) {
                RenderUtils.drawRecta(this.getX() + 2 + i, (float)(this.getY() + 16), Float.intBitsToFloat(Float.floatToIntBits(7.249331f) ^ 0x7F67FA85), Float.intBitsToFloat(Float.floatToIntBits(1.7045807f) ^ 0x7EEA2FB3), Color.getHSBColor(i / Float.intBitsToFloat(Float.floatToIntBits(0.115068644f) ^ 0x7F2BA91C), Float.intBitsToFloat(Float.floatToIntBits(4.3161592f) ^ 0x7F0A1DFA), Float.intBitsToFloat(Float.floatToIntBits(21.075346f) ^ 0x7E289A4F)).getRGB());
            }
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 16), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 27), Float.intBitsToFloat(Float.floatToIntBits(2.7503529f) ^ 0x7F3005C8), new Color(0, 0, 0).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.hueWidth, (float)(this.getY() + 16), Float.intBitsToFloat(Float.floatToIntBits(5.200255f) ^ 0x7F26687D), Float.intBitsToFloat(Float.floatToIntBits(1.2665411f) ^ 0x7E921E05), new Color(255, 255, 255).getRGB());
            Gui.drawRect(this.getX(), this.getY() + 28, this.getX() + this.getWidth(), this.getY() + 42, new Color(40, 40, 40).getRGB());
            RenderUtils.drawSidewaysGradient((float)(this.getX() + 2), (float)(this.getY() + 29), (float)(this.getWidth() - 4), Float.intBitsToFloat(Float.floatToIntBits(0.19645536f) ^ 0x7F792B98), new Color(255, 255, 255), color, 255, 255);
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 29), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 40), Float.intBitsToFloat(Float.floatToIntBits(103.69628f) ^ 0x7DCF647F), new Color(0, 0, 0).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.satWidth, (float)(this.getY() + 29), Float.intBitsToFloat(Float.floatToIntBits(7.3489017f) ^ 0x7F6B2A34), Float.intBitsToFloat(Float.floatToIntBits(0.1948352f) ^ 0x7F7782E1), new Color(255, 255, 255).getRGB());
            Gui.drawRect(this.getX(), this.getY() + 42, this.getX() + this.getWidth(), this.getY() + 56, new Color(40, 40, 40).getRGB());
            RenderUtils.drawSidewaysGradient((float)(this.getX() + 2), (float)(this.getY() + 42), (float)(this.getWidth() - 4), Float.intBitsToFloat(Float.floatToIntBits(1.5246161f) ^ 0x7EF3269F), new Color(0, 0, 0), color, 255, 255);
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 42), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 53), Float.intBitsToFloat(Float.floatToIntBits(3.7803736f) ^ 0x7F71F1A4), new Color(0, 0, 0).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.briWidth, (float)(this.getY() + 42), Float.intBitsToFloat(Float.floatToIntBits(8.346171f) ^ 0x7E8589EB), Float.intBitsToFloat(Float.floatToIntBits(0.08925866f) ^ 0x7C86CD3F), new Color(255, 255, 255).getRGB());
            Gui.drawRect(this.getX(), this.getY() + 56, this.getX() + this.getWidth(), this.getY() + 70, new Color(40, 40, 40).getRGB());
            this.renderAlphaBG(this.getX() + 2, this.getY() + 55, this.alphaBG);
            RenderUtils.drawSidewaysGradient((float)(this.getX() + 2), (float)(this.getY() + 55), (float)(this.getWidth() - 4), Float.intBitsToFloat(Float.floatToIntBits(0.13166903f) ^ 0x7F36D43F), new Color(0, 0, 0), color, 0, 255);
            RenderUtils.drawOutline((float)(this.getX() + 2), (float)(this.getY() + 55), (float)(this.getX() + 2 + this.getWidth() - 4), (float)(this.getY() + 66), Float.intBitsToFloat(Float.floatToIntBits(19.69502f) ^ 0x7E9D8F67), new Color(0, 0, 0).getRGB());
            RenderUtils.drawRecta(this.getX() + 2 + this.alphaWidth, (float)(this.getY() + 55), Float.intBitsToFloat(Float.floatToIntBits(6.702013f) ^ 0x7F5676E4), Float.intBitsToFloat(Float.floatToIntBits(0.13652846f) ^ 0x7F3BCE1E), new Color(255, 255, 255).getRGB());
            Gui.drawRect(this.getX(), this.getY() + 70, this.getX() + this.getWidth(), this.getY() + 84, new Color(40, 40, 40).getRGB());
            Europa.FONT_MANAGER.drawString("Rainbow", (float)(this.getX() + 3), this.getY() + 78 - Europa.FONT_MANAGER.getHeight() / Float.intBitsToFloat(Float.floatToIntBits(0.8730777f) ^ 0x7F5F8205), Color.WHITE);
            Gui.drawRect(this.getX() + this.getWidth() - 12, this.getY() + 72, this.getX() + this.getWidth() - 2, this.getY() + 82, new Color(30, 30, 30).getRGB());
            if (this.setting.getRainbow()) {
                RenderUtils.prepareGL();
                GL11.glShadeModel(7425);
                GL11.glEnable(2848);
                GL11.glLineWidth(Float.intBitsToFloat(Float.floatToIntBits(0.2713932f) ^ 0x7EAAF40D));
                GL11.glBegin(1);
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.015137452f) ^ 0x7F070313), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(1.1948546f) ^ 0x7CE7F0FF), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.36357376f) ^ 0x7DC52657));
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 80));
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.015521388f) ^ 0x7F014D6B), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.01025841f) ^ 0x7F5712E4), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.10675689f) ^ 0x7EA5A35B));
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8 + 4), (double)(this.getY() + 74));
                GL11.glEnd();
                GL11.glBegin(1);
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.009417259f) ^ 0x7F654AD9), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.07014828f) ^ 0x7EF0A9E7), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.013465701f) ^ 0x7F239F3E));
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 80));
                GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.0155056f) ^ 0x7F010B33), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.011914493f) ^ 0x7F3C3501), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.012230922f) ^ 0x7F376434));
                GL11.glVertex2d((double)(this.getX() + this.getWidth() - 10), (double)(this.getY() + 77));
                GL11.glEnd();
                RenderUtils.releaseGL();
            }
        }
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 84, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 84, new Color(30, 30, 30).getRGB());
        Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth()) {
            if (mouseY >= this.getY()) {
                if (mouseY <= this.getY() + this.getHeight() && mouseButton == 1) {
                    this.open = !this.open;
                }
            }
        }
        if (this.isMouseOnHue(mouseX, mouseY)) {
            if (mouseButton == 0) {
                if (this.open) {
                    this.hueDragging = true;
                    return;
                }
            }
        }
        if (this.isMouseOnSat(mouseX, mouseY)) {
            if (mouseButton == 0) {
                if (this.open) {
                    this.saturationDragging = true;
                    return;
                }
            }
        }
        if (this.isMouseOnBri(mouseX, mouseY) && mouseButton == 0 && this.open) {
            this.brightnessDragging = true;
        }
        else {
            if (this.isMouseOnAlpha(mouseX, mouseY)) {
                if (mouseButton == 0) {
                    if (this.open) {
                        this.alphaDragging = true;
                        return;
                    }
                }
            }
            if (this.isMouseOnRainbow(mouseX, mouseY)) {
                if (mouseButton == 0) {
                    if (this.open) {
                        this.setting.setRainbow(!this.setting.getRainbow());
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.hueDragging = false;
        this.saturationDragging = false;
        this.brightnessDragging = false;
        this.alphaDragging = false;
    }

    public void renderAlphaBG(final int x, final int y, final ResourceLocation texture) {
        ColorComponent.mc.getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glColor4f(Float.intBitsToFloat(Float.floatToIntBits(84.80346f) ^ 0x7D299B5F), Float.intBitsToFloat(Float.floatToIntBits(356.26364f) ^ 0x7C3221BF), Float.intBitsToFloat(Float.floatToIntBits(4.4841223f) ^ 0x7F0F7DEE), Float.intBitsToFloat(Float.floatToIntBits(6.9323945f) ^ 0x7F5DD62D));
        Gui.drawScaledCustomSizeModalRect(x, y, Float.intBitsToFloat(Float.floatToIntBits(1.9387513E38f) ^ 0x7F11DAFE), Float.intBitsToFloat(Float.floatToIntBits(1.7625584E38f) ^ 0x7F0499A4), 104, 16, this.getWidth() - 4, 11, Float.intBitsToFloat(Float.floatToIntBits(0.112598404f) ^ 0x7F3699FE), Float.intBitsToFloat(Float.floatToIntBits(0.60222334f) ^ 0x7E9A2B4F));
        GL11.glPopMatrix();
        GlStateManager.clear(256);
    }

    public boolean isMouseOnHue(final int x, final int y) {
        if (x > this.getX() + 2) {
            if (x < this.getX() + 2 + this.getWidth() - 4) {
                if (y > this.getY() + 16) {
                    if (y < this.getY() + 27) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isMouseOnSat(final int x, final int y) {
        return x > this.getX() + 2 && x < this.getX() + 2 + this.getWidth() - 4 && y > this.getY() + 29 && y < this.getY() + 40;
    }

    public boolean isMouseOnBri(final int x, final int y) {
        if (x > this.getX() + 2) {
            if (x < this.getX() + 2 + this.getWidth() - 4) {
                if (y > this.getY() + 42 && y < this.getY() + 53) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMouseOnAlpha(final int x, final int y) {
        return x > this.getX() + 2 && x < this.getX() + 2 + this.getWidth() - 4 && y > this.getY() + 55 && y < this.getY() + 66;
    }

    public boolean isMouseOnRainbow(final int x, final int y) {
        if (x > this.getX() + this.getWidth() - 12) {
            if (x < this.getX() + this.getWidth() - 2 && y > this.getY() + 72 && y < this.getY() + 82) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(final int mouseX, final int mouseY) {
        super.update(mouseX, mouseY);
        final float[] hsb = Color.RGBtoHSB(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), null);
        final double difference = Math.min(95, Math.max(0, mouseX - this.getX()));
        this.hueWidth = Float.intBitsToFloat(Float.floatToIntBits(0.012939732f) ^ 0x7EEB012B) * (hsb[0] * Float.intBitsToFloat(Float.floatToIntBits(0.22324012f) ^ 0x7DD0990F) / Float.intBitsToFloat(Float.floatToIntBits(0.07544195f) ^ 0x7E2E814F));
        this.satWidth = Float.intBitsToFloat(Float.floatToIntBits(0.009555363f) ^ 0x7EA18E19) * (hsb[1] * Float.intBitsToFloat(Float.floatToIntBits(0.021556562f) ^ 0x7F049763) / Float.intBitsToFloat(Float.floatToIntBits(0.026331188f) ^ 0x7F63B481));
        this.briWidth = Float.intBitsToFloat(Float.floatToIntBits(0.02392782f) ^ 0x7E790447) * (hsb[2] * Float.intBitsToFloat(Float.floatToIntBits(0.09763377f) ^ 0x7E73F437) / Float.intBitsToFloat(Float.floatToIntBits(0.019418718f) ^ 0x7F2B1401));
        this.alphaWidth = Float.intBitsToFloat(Float.floatToIntBits(0.010174015f) ^ 0x7E9BB0E9) * (this.setting.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.0089911735f) ^ 0x7F6C4FB7));
        this.changeColor(difference, new Color(Color.HSBtoRGB((float)(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.15404371830294214) ^ 0x7F9477B45E21F7BFL) * Double.longBitsToDouble(Double.doubleToLongBits(0.050973544293479105) ^ 0x7FDC99345367453FL) / Double.longBitsToDouble(Double.doubleToLongBits(0.03014217321508198) ^ 0x7FE85D9700C1AF0AL)), hsb[1], hsb[2])), new Color(Color.HSBtoRGB(Float.intBitsToFloat(Float.floatToIntBits(1.8279414E38f) ^ 0x7F0984DF), hsb[1], hsb[2])), this.hueDragging);
        this.changeColor(difference, new Color(Color.HSBtoRGB(hsb[0], (float)(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.1223112785883676) ^ 0x7FE88FCABD780F54L) * Double.longBitsToDouble(Double.doubleToLongBits(0.026943886254004668) ^ 0x7FED172D9927021DL) / Double.longBitsToDouble(Double.doubleToLongBits(0.05427001644334754) ^ 0x7FDD4947938E1C55L)), hsb[2])), new Color(Color.HSBtoRGB(hsb[0], Float.intBitsToFloat(Float.floatToIntBits(1.1082437E38f) ^ 0x7EA6BFFF), hsb[2])), this.saturationDragging);
        this.changeColor(difference, new Color(Color.HSBtoRGB(hsb[0], hsb[1], (float)(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.12328622126775308) ^ 0x7FE84FAF90647595L) * Double.longBitsToDouble(Double.doubleToLongBits(0.09854681448488288) ^ 0x7FCFBA5D315669BFL) / Double.longBitsToDouble(Double.doubleToLongBits(0.029067112480345214) ^ 0x7FEB43C4E5F80CC0L)))), new Color(Color.HSBtoRGB(hsb[0], hsb[1], Float.intBitsToFloat(Float.floatToIntBits(3.3573391E38f) ^ 0x7F7C9400))), this.brightnessDragging);
        this.changeAlpha(difference, (float)(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.014823398455503097) ^ 0x7FD99BBADCA7DC11L) * Double.longBitsToDouble(Double.doubleToLongBits(0.013271171619186513) ^ 0x7FE4CDEA80AC0D24L) / Double.longBitsToDouble(Double.doubleToLongBits(0.08218747250746601) ^ 0x7FDAEA3CFA8F7AADL)), this.alphaDragging);
    }

    public void changeColor(final double difference, final Color color, final Color zeroColor, final boolean dragging) {
        if (dragging) {
            if (difference == Double.longBitsToDouble(Double.doubleToLongBits(1.2749872908217061E308) ^ 0x7FE6B20E10D32E17L)) {
                this.setting.setValue(new Color(zeroColor.getRed(), zeroColor.getGreen(), zeroColor.getBlue(), this.setting.getValue().getAlpha()));
            }
            else {
                this.setting.setValue(new Color(color.getRed(), color.getGreen(), color.getBlue(), this.setting.getValue().getAlpha()));
            }
        }
    }

    public void changeAlpha(final double difference, final float alpha, final boolean dragging) {
        if (dragging) {
            if (difference == Double.longBitsToDouble(Double.doubleToLongBits(7.977172206938858E307) ^ 0x7FDC6651265A7509L)) {
                this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), 0));
            }
            else {
                this.setting.setValue(new Color(this.setting.getValue().getRed(), this.setting.getValue().getGreen(), this.setting.getValue().getBlue(), (int)(alpha * Float.intBitsToFloat(Float.floatToIntBits(0.015395311f) ^ 0x7F033C9D))));
            }
        }
    }
}
