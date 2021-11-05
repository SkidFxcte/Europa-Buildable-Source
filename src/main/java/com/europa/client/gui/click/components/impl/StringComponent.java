//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.click.components.impl;

import net.minecraft.util.ChatAllowedCharacters;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;
import org.lwjgl.input.Keyboard;
import com.europa.client.modules.client.ModuleColor;
import com.europa.Europa;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraftforge.common.MinecraftForge;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.client.gui.click.components.Component;

public class StringComponent extends Component
{
    public ValueString setting;
    public boolean listening;
    public String currentString;
    public TimerUtils timer;
    public TimerUtils backTimer;
    public TimerUtils deleteTimer;
    public boolean selecting;
    public boolean undoing;

    public StringComponent(final ValueString setting, final ModuleComponent parent, final int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.currentString = "";
        this.timer = new TimerUtils();
        this.backTimer = new TimerUtils();
        this.deleteTimer = new TimerUtils();
        this.selecting = false;
        this.undoing = false;
        this.setting = setting;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        if (this.timer.hasReached((long)34125802 ^ 0x208B67AL)) {
            this.undoing = !this.undoing;
            this.timer.reset();
        }
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + 14, new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + 14, new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + 1, this.getY() + 1, this.getX() + this.getWidth() - 1, this.getY() + 13, new Color(30, 30, 30).getRGB());
        if (this.selecting) {
            Gui.drawRect(this.getX() + 3, this.getY() + 3, (int)(this.getX() + 3 + Europa.FONT_MANAGER.getStringWidth(this.currentString)), (int)(this.getY() + Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(0.39460382f) ^ 0x7E8A0983)), new Color(ModuleColor.getActualColor().getRed(), ModuleColor.getActualColor().getGreen(), ModuleColor.getActualColor().getBlue(), 100).getRGB());
        }
        if (this.listening) {
            Europa.FONT_MANAGER.drawString(this.currentString + (this.selecting ? "" : (this.undoing ? (Europa.MODULE_MANAGER.isModuleEnabled("Font") ? "|" : "\u23d0") : "")), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.LIGHT_GRAY);
        }
        else {
            Europa.FONT_MANAGER.drawString(this.setting.getValue(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.LIGHT_GRAY);
        }
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY()) {
                if (mouseY <= this.getY() + this.getHeight()) {
                    this.listening = !this.listening;
                    this.currentString = this.setting.getValue();
                }
            }
        }
    }

    @Override
    public void keyTyped(final char typedChar, final int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.backTimer.reset();
        if (this.listening) {
            if (keyCode == 1) {
                this.selecting = false;
                return;
            }
            Label_0589: {
                if (keyCode == 28) {
                    this.updateString();
                    this.selecting = false;
                    this.listening = false;
                }
                else if (keyCode == 14) {
                    this.currentString = (this.selecting ? "" : this.removeLastCharacter(this.currentString));
                    this.selecting = false;
                }
                else {
                    Label_0389: {
                        if (keyCode == 47) {
                            if (!Keyboard.isKeyDown(157)) {
                                if (!Keyboard.isKeyDown(29)) {
                                    break Label_0389;
                                }
                            }
                            try {
                                this.currentString += Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            break Label_0589;
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
        if (this.currentString.length() > 0) {
            this.setting.setValue(this.currentString);
        }
        this.currentString = "";
    }

    public String removeLastCharacter(final String input) {
        if (input.length() > 0) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }
}
