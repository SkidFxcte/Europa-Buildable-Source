/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.click.components.impl;

import com.europa.Europa;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.client.gui.click.components.Component;
import com.europa.client.gui.click.components.impl.ModuleComponent;
import com.europa.client.modules.client.ModuleColor;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public class ModeComponent
extends Component {
    public ValueEnum setting;
    public int enumSize;

    public ModeComponent(ValueEnum setting, ModuleComponent parent, int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Europa.FONT_MANAGER.drawString(this.setting.getValue().toString(), this.getX() + this.getWidth() - 3 - Europa.FONT_MANAGER.getStringWidth(this.setting.getValue().toString()), (float)(this.getY() + 3), ModuleColor.getActualColor());
    }


    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= this.getX()) {
            if (mouseX <= this.getX() + this.getWidth()) {
                if (mouseY >= this.getY()) {
                    if (mouseY <= this.getY() + this.getHeight()) {
                        if (mouseButton == 0) {
                            final int maxIndex = this.setting.getValues().size() - 1;
                            ++this.enumSize;
                            if (this.enumSize > maxIndex) {
                                this.enumSize = 0;
                            }
                            this.setting.setValue(this.setting.getValues().get(this.enumSize));
                        }
                        else if (mouseButton == 1) {
                            final int maxIndex = this.setting.getValues().size() - 1;
                            --this.enumSize;
                            if (this.enumSize < 0) {
                                this.enumSize = maxIndex;
                            }
                            this.setting.setValue(this.setting.getValues().get(this.enumSize));
                        }
                    }
                }
            }
        }
    }
}

