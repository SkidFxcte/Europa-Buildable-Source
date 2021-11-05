/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.click.components.impl;

import com.europa.Europa;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.value.Value;
import com.europa.api.manager.value.impl.ValueBind;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValuePreview;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.client.gui.click.components.Component;
import com.europa.client.gui.click.components.Frame;
import com.europa.client.gui.click.components.impl.BindComponent;
import com.europa.client.gui.click.components.impl.BooleanComponent;
import com.europa.client.gui.click.components.impl.ColorComponent;
import com.europa.client.gui.click.components.impl.ModeComponent;
import com.europa.client.gui.click.components.impl.NumberComponent;
import com.europa.client.gui.click.components.impl.PreviewComponent;
import com.europa.client.gui.click.components.impl.StringComponent;
import com.europa.client.modules.client.ModuleColor;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.gui.Gui;

public class ModuleComponent
extends Component {
    public Module module;
    public ArrayList<Component> subButtons;
    public boolean open;

    public ModuleComponent(Module module, int x, int y, Frame parent) {
        super(x, y, parent);
        this.module = module;
        this.subButtons = new ArrayList();
        this.open = false;
        int tempY = y + 14;
        if (module.getValues() != null && !module.getValues().isEmpty()) {
            for (Value setting : module.getValues()) {
                if (setting instanceof ValueBoolean) {
                    this.subButtons.add(new BooleanComponent((ValueBoolean)setting, this, tempY));
                    tempY += 14;
                }
                if (setting instanceof ValueNumber) {
                    this.subButtons.add(new NumberComponent((ValueNumber)setting, this, tempY));
                    tempY += 14;
                }
                if (setting instanceof ValueEnum) {
                    this.subButtons.add(new ModeComponent((ValueEnum)setting, this, tempY));
                    tempY += 14;
                }
                if (setting instanceof ValueString) {
                    this.subButtons.add(new StringComponent((ValueString)setting, this, tempY));
                    tempY += 14;
                }
                if (setting instanceof ValueColor) {
                    this.subButtons.add(new ColorComponent((ValueColor)setting, this, tempY));
                    tempY += 100;
                }
                if (setting instanceof ValuePreview) {
                    this.subButtons.add(new PreviewComponent((ValuePreview)setting, this, tempY));
                    tempY += 70;
                }
                if (!(setting instanceof ValueBind)) continue;
                this.subButtons.add(new BindComponent((ValueBind)setting, this, tempY));
                tempY += 14;
            }
        }
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, this.module.isToggled() ? ModuleColor.getColor() : new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Europa.FONT_MANAGER.drawString(this.module.getTag(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        if (this.open && !this.getSubButtons().isEmpty()) {
            for (final Component button : this.getSubButtons()) {
                button.drawScreen(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX >= this.getX()) {
            if (mouseX <= this.getX() + this.getWidth()) {
                if (mouseY >= this.getY()) {
                    if (mouseY <= this.getY() + this.getHeight()) {
                        if (mouseButton == 0) {
                            this.module.toggle();
                        }
                        else if (mouseButton == 1) {
                            this.open = !this.open;
                        }
                    }
                }
            }
        }
    }

    public boolean isOpen() {
        return this.open;
    }

    public ArrayList<Component> getSubButtons() {
        return this.subButtons;
    }
}

