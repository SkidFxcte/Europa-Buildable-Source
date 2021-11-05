/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.click.components.impl;

import com.europa.Europa;
import com.europa.api.manager.value.impl.ValueBind;
import com.europa.client.gui.click.components.Component;
import com.europa.client.gui.click.components.impl.ModuleComponent;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class BindComponent
extends Component {
    public boolean binding;
    public ValueBind setting;

    public BindComponent(ValueBind setting, ModuleComponent parent, int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + 13, new Color(30, 30, 30).getRGB());
        Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Europa.FONT_MANAGER.drawString(this.binding ? "[...]" : ("[" + Keyboard.getKeyName(this.setting.getValue()).toUpperCase() + "]"), this.getX() + this.getWidth() - 3 - Europa.FONT_MANAGER.getStringWidth(this.binding ? "[...]" : ("[" + Keyboard.getKeyName(this.setting.getValue()).toUpperCase() + "]")), (float)(this.getY() + 3), Color.LIGHT_GRAY);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX >= this.getX()) {
                if (mouseX <= this.getX() + this.getWidth()) {
                    if (mouseY >= this.getY()) {
                        if (mouseY <= this.getY() + this.getHeight()) {
                            this.binding = !this.binding;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (this.binding) {
            if (keyCode == 211) {
                this.setting.setValue(0);
            }
            else if (keyCode != 1) {
                this.setting.setValue(keyCode);
            }
            this.binding = false;
        }
    }
}

