//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.click.components.impl;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;
import org.lwjgl.input.Keyboard;
import com.europa.api.utilities.math.MathUtils;
import org.lwjgl.opengl.GL11;
import com.europa.Europa;
import com.europa.client.modules.client.ModuleColor;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import com.europa.api.utilities.math.AnimationUtils;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.client.gui.click.components.Component;

public class NumberComponent extends Component
{
    public ValueNumber setting;
    public double sliderWidth;
    public boolean dragging;
    public boolean typing;
    public String currentString;
    public boolean selecting;
    public TimerUtils backTimer;
    public boolean undoing;
    public TimerUtils timer;
    public AnimationUtils animationUtils;
    public boolean animated;

    public NumberComponent(final ValueNumber setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.currentString = "";
        this.selecting = false;
        this.backTimer = new TimerUtils();
        this.undoing = false;
        this.timer = new TimerUtils();
        this.animated = false;
        this.setting = setting;
        this.dragging = false;
        this.typing = false;
        this.animationUtils = new AnimationUtils((long)(-1770284037) ^ 0xFFFFFFFF967B9A0FL, Float.intBitsToFloat(Float.floatToIntBits(9.933269E37f) ^ 0x7E95758F), (float)(this.getWidth() - 1));
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (this.timer.hasReached((long)1320065972 ^ 0x4EAE9A24L)) {
            this.undoing = !this.undoing;
            this.timer.reset();
        }
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + 13, new Color(50, 50, 50).getRGB());
        if (!this.typing) {
            Gui.drawRect(this.getX() + 1, this.getY() + 1, (int)(this.getX() + 1 + this.sliderWidth), this.getY() + 13, ModuleColor.getColor());
        }
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Europa.FONT_MANAGER.drawString(this.setting.getValue() + ((this.setting.getType() == 1) ? ".0" : ""), this.getX() + this.getWidth() - 3 - Europa.FONT_MANAGER.getStringWidth(this.setting.getValue() + ((this.setting.getType() == 1) ? ".0" : "")), (float)(this.getY() + 3), Color.WHITE);
        if (!this.typing) {
            Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
            Europa.FONT_MANAGER.drawString(this.setting.getValue() + ((this.setting.getType() == 1) ? ".0" : ""), this.getX() + this.getWidth() - 3 - Europa.FONT_MANAGER.getStringWidth(this.setting.getValue() + ((this.setting.getType() == 1) ? ".0" : "")), (float)(this.getY() + 3), Color.WHITE);
            this.animated = false;
        }
        else {
            if (!this.animated) {
                this.animationUtils.reset();
            }
            if (!this.animationUtils.isDone()) {
                GL11.glEnable(3089);
                scissor(this.getX() + 1, this.getY() + 1, this.getWidth(), Double.longBitsToDouble(Double.doubleToLongBits(0.23670126609511655) ^ 0x7FE44C3A226670DCL));
                drawRect((float)(this.getX() + 1), (float)(this.getY() + 1), this.getX() + 1 + (float)this.sliderWidth - this.animationUtils.getValue(), (float)(this.getY() + 13), ModuleColor.getColor());
                GL11.glDisable(3089);
            }
            this.animated = true;
            Europa.FONT_MANAGER.drawString(this.currentString + (this.selecting ? "" : (this.undoing ? (Europa.MODULE_MANAGER.isModuleEnabled("Font") ? "|" : "\u23d0") : "")), this.getX() + (this.getWidth() - 1) / Float.intBitsToFloat(Float.floatToIntBits(0.37152806f) ^ 0x7EBE38ED) - Europa.FONT_MANAGER.getStringWidth(this.currentString) / Float.intBitsToFloat(Float.floatToIntBits(0.6535927f) ^ 0x7F2751DA), (float)(this.getY() + 3), Color.WHITE);
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 2 && mouseY <= this.getY() + this.getHeight() - 2) {
                this.dragging = true;
                return;
            }
        }
        if (mouseButton == 1 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 2 && mouseY <= this.getY() + this.getHeight() - 2) {
            this.typing = !this.typing;
            this.currentString = this.setting.getValue().toString();
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }

    @Override
    public void update(final int mouseX, final int mouseY) {
        final double difference = Math.min(98, Math.max(0, mouseX - this.getX()));
        if (this.setting.getType() == 1) {
            this.sliderWidth = Float.intBitsToFloat(Float.floatToIntBits(0.014527884f) ^ 0x7EAA065D) * (this.setting.getValue().intValue() - this.setting.getMinimum().intValue()) / (this.setting.getMaximum().intValue() - this.setting.getMinimum().intValue());
            if (this.dragging) {
                if (difference == Double.longBitsToDouble(Double.doubleToLongBits(1.4007454973032137E308) ^ 0x7FE8EF2103A723F1L)) {
                    this.setting.setValue(this.setting.getMinimum());
                }
                else {
                    final int value = (int)MathUtils.roundToPlaces(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.014794065701436805) ^ 0x7FD6CC59E2E67EB7L) * (this.setting.getMaximum().intValue() - this.setting.getMinimum().intValue()) + this.setting.getMinimum().intValue(), 0);
                    this.setting.setValue(value);
                }
            }
        }
        else if (this.setting.getType() == 2) {
            this.sliderWidth = Double.longBitsToDouble(Double.doubleToLongBits(0.06935230413301949) ^ 0x7FE9411296274FFEL) * (this.setting.getValue().doubleValue() - this.setting.getMinimum().doubleValue()) / (this.setting.getMaximum().doubleValue() - this.setting.getMinimum().doubleValue());
            if (this.dragging) {
                if (difference == Double.longBitsToDouble(Double.doubleToLongBits(1.3453966521905491E308) ^ 0x7FE7F2E82F57B15AL)) {
                    this.setting.setValue(this.setting.getMinimum());
                }
                else {
                    final double value2 = MathUtils.roundToPlaces(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.10167540468383177) ^ 0x7FE2876639ECB412L) * (this.setting.getMaximum().doubleValue() - this.setting.getMinimum().doubleValue()) + this.setting.getMinimum().doubleValue(), 2);
                    this.setting.setValue(value2);
                }
            }
        }
        else if (this.setting.getType() == 3) {
            this.sliderWidth = Float.intBitsToFloat(Float.floatToIntBits(0.011726105f) ^ 0x7E841ED9) * (this.setting.getValue().floatValue() - this.setting.getMinimum().floatValue()) / (this.setting.getMaximum().floatValue() - this.setting.getMinimum().floatValue());
            if (this.dragging) {
                if (difference == Double.longBitsToDouble(Double.doubleToLongBits(1.402772054672317E308) ^ 0x7FE8F85D2793C1F3L)) {
                    this.setting.setValue(this.setting.getMinimum());
                }
                else {
                    final float value3 = (float)MathUtils.roundToPlaces(difference / Double.longBitsToDouble(Double.doubleToLongBits(0.0639820208900176) ^ 0x7FE8E1202F412E69L) * (this.setting.getMaximum().floatValue() - this.setting.getMinimum().floatValue()) + this.setting.getMinimum().floatValue(), 2);
                    this.setting.setValue(value3);
                }
            }
        }
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.backTimer.reset();
        if (this.typing) {
            if (keyCode == 1) {
                this.selecting = false;
                return;
            }
            Label_0531: {
                if (keyCode == 28) {
                    this.updateString();
                    this.selecting = false;
                    this.typing = false;
                }
                else if (keyCode == 14) {
                    this.currentString = (this.selecting ? "" : this.removeLastCharacter(this.currentString));
                    this.selecting = false;
                }
                else {
                    Label_0387: {
                        if (keyCode == 47) {
                            if (!Keyboard.isKeyDown(157)) {
                                if (!Keyboard.isKeyDown(29)) {
                                    break Label_0387;
                                }
                            }
                            try {
                                this.currentString += Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break Label_0531;
                        }
                    }
                    if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                        this.currentString = (this.selecting ? ("" + typedChar) : (this.currentString + typedChar));
                        this.selecting = false;
                    }
                }
            }
            if (keyCode == 30 && Keyboard.isKeyDown(29)) {
                this.selecting = true;
            }
        }
    }

    public void updateString() {
        Label_0804: {
            if (this.currentString.length() > 0) {
                if (this.setting.getType() == 1) {
                    try {
                        if (Integer.parseInt(this.currentString) <= this.setting.getMaximum().intValue() && Integer.parseInt(this.currentString) >= this.setting.getMinimum().intValue()) {
                            this.setting.setValue(Integer.parseInt(this.currentString));
                        }
                        else {
                            this.setting.setValue(this.setting.getValue());
                        }
                    }
                    catch (NumberFormatException e) {
                        this.setting.setValue(this.setting.getValue());
                    }
                }
                else if (this.setting.getType() == 3) {
                    try {
                        if (Float.parseFloat(this.currentString) <= this.setting.getMaximum().floatValue() && Float.parseFloat(this.currentString) >= this.setting.getMinimum().floatValue()) {
                            this.setting.setValue(Float.parseFloat(this.currentString));
                        }
                        else {
                            this.setting.setValue(this.setting.getValue());
                        }
                    }
                    catch (NumberFormatException e) {
                        this.setting.setValue(this.setting.getValue());
                    }
                }
                else if (this.setting.getType() == 2) {
                    ValueNumber setting;
                    Number value;
                    try {
                        if (Double.parseDouble(this.currentString) <= this.setting.getMaximum().doubleValue() && Double.parseDouble(this.currentString) >= this.setting.getMinimum().doubleValue()) {
                            this.setting.setValue(Double.parseDouble(this.currentString));
                        }
                        else {
                            this.setting.setValue(this.setting.getValue());
                        }
                        break Label_0804;
                    }
                    catch (NumberFormatException e) {
                        setting = this.setting;
                        value = this.setting.getValue();
                    }
                    setting.setValue(value);
                }
            }
        }
        this.currentString = "";
    }

    public String removeLastCharacter(final String input) {
        if (input.length() > 0) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }

    public static void drawRect(float left, float top, float right, float bottom, final int color) {
        if (left < right) {
            final float j = left;
            left = right;
            right = j;
        }
        if (top < bottom) {
            final float j = top;
            top = bottom;
            bottom = j;
        }
        final float f3 = (color >> 24 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.015153162f) ^ 0x7F0744F8);
        final float f4 = (color >> 16 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.10281151f) ^ 0x7EAD8ED7);
        final float f5 = (color >> 8 & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.049893465f) ^ 0x7E335D17);
        final float f6 = (color & 0xFF) / Float.intBitsToFloat(Float.floatToIntBits(0.097316556f) ^ 0x7EB84DE7);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f4, f5, f6, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)left, (double)bottom, Double.longBitsToDouble(Double.doubleToLongBits(1.019881991218291E307) ^ 0x7FAD0C1583E04CDFL)).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, Double.longBitsToDouble(Double.doubleToLongBits(1.7481753651024542E308) ^ 0x7FEF1E599571C332L)).endVertex();
        bufferbuilder.pos((double)right, (double)top, Double.longBitsToDouble(Double.doubleToLongBits(1.1079463692462994E307) ^ 0x7FAF8E2C134985AFL)).endVertex();
        bufferbuilder.pos((double)left, (double)top, Double.longBitsToDouble(Double.doubleToLongBits(3.929654556867817E307) ^ 0x7FCBFAE5D0CB7E47L)).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void scissor(double x, double y, double width, double height) {
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final double scale = sr.getScaleFactor();
        y = sr.getScaledHeight() - y;
        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;
        GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
    }
}
