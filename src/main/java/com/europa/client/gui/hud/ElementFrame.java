/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.hud;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.utilities.IMinecraft;
import com.europa.client.gui.hud.HudEditorScreen;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class ElementFrame
implements IMinecraft {
    public Element element;
    public float x;
    public float y;
    public float width;
    public float height;
    public float dragX;
    public float dragY;
    public boolean dragging;
    public boolean visible;
    public HudEditorScreen parent;

    public ElementFrame(Element element, float x, float y, float width, float height, HudEditorScreen parent) {
        this.element = element;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;
        this.dragging = false;
        this.visible = true;
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.element != null) {
            if (Europa.ELEMENT_MANAGER.isElementEnabled(this.element.getName())) {
                if (this.dragging) {
                    this.x = this.dragX + mouseX;
                    this.y = this.dragY + mouseY;
                    final ScaledResolution resolution = new ScaledResolution(ElementFrame.mc);
                    if (this.x < Double.longBitsToDouble(Double.doubleToLongBits(1.6916582217715779E308) ^ 0x7FEE1CCDD7767A58L)) {
                        this.x = Float.intBitsToFloat(Float.floatToIntBits(4.9986014E37f) ^ 0x7E166BD3);
                    }
                    if (this.y < Double.longBitsToDouble(Double.doubleToLongBits(9.52408722466754E307) ^ 0x7FE0F414836643D0L)) {
                        this.y = Float.intBitsToFloat(Float.floatToIntBits(2.3789903E38f) ^ 0x7F32F9B0);
                    }
                    if (this.x > resolution.getScaledWidth() - this.width) {
                        this.x = resolution.getScaledWidth() - this.width;
                    }
                    if (this.y > resolution.getScaledHeight() - this.height) {
                        this.y = resolution.getScaledHeight() - this.height;
                    }
                }
                if (this.dragging) {
                    Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), new Color(Color.DARK_GRAY.getRed(), Color.DARK_GRAY.getGreen(), Color.DARK_GRAY.getBlue(), 100).getRGB());
                }
                else {
                    Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 100).getRGB());
                }
                this.element.onRender2D(new EventRender2D(partialTicks));
            }
        }
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.dragX = this.x - mouseX;
            this.dragY = this.y - mouseY;
            this.dragging = true;
        }
    }

    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.dragging = false;
    }

    public boolean isHovering(final int mouseX, final int mouseY) {
        if (mouseX >= this.x) {
            if (mouseX <= this.x + this.width) {
                if (mouseY >= this.y && mouseY <= this.y + this.height) {
                    return true;
                }
            }
        }
        return false;
    }


    public Element getElement() {
        return this.element;
    }

    public HudEditorScreen getParent() {
        return this.parent;
    }

    public float getX() {
        return this.x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(final float y) {
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(final float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
}

