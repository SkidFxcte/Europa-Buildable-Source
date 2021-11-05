/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.click.components.impl;

import com.europa.Europa;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.client.gui.click.components.Component;
import com.europa.client.gui.click.components.impl.ModuleComponent;
import com.europa.client.modules.client.ModuleColor;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class BooleanComponent
extends Component {
    public ValueBoolean setting;

    public BooleanComponent(ValueBoolean setting, ModuleComponent parent, int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() + this.getWidth() - 12, this.getY() + 2, this.getX() + this.getWidth() - 2, this.getY() + 12, new Color(30, 30, 30).getRGB());
        if (this.setting.getValue()) {
            RenderUtils.prepareGL();
            GL11.glShadeModel(7425);
            GL11.glEnable(2848);
            GL11.glLineWidth(Float.intBitsToFloat(Float.floatToIntBits(0.04342526f) ^ 0x7D11DEAF));
            GL11.glBegin(1);
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.11525812f) ^ 0x7E930C73), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.010934432f) ^ 0x7F4C2655), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.18847941f) ^ 0x7D3E00BF));
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 10));
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.008369477f) ^ 0x7F762021), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.102402516f) ^ 0x7EAEB869), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.17724131f) ^ 0x7D4A7EBF));
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8 + 4), (double)(this.getY() + 4));
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.11534863f) ^ 0x7E933BE7), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.09277556f) ^ 0x7EC1011D), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.08886478f) ^ 0x7ECAFEBD));
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 8), (double)(this.getY() + 10));
            GL11.glColor3f(ModuleColor.getActualColor().getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.013863879f) ^ 0x7F1C2553), ModuleColor.getActualColor().getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.011107416f) ^ 0x7F4AFBE1), ModuleColor.getActualColor().getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.011841842f) ^ 0x7F3D0449));
            GL11.glVertex2d((double)(this.getX() + this.getWidth() - 10), (double)(this.getY() + 7));
            GL11.glEnd();
            RenderUtils.releaseGL();
        }
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            this.setting.setValue(!this.setting.getValue());
        }
    }
}

