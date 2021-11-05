/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.hud;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.client.gui.click.components.Frame;
import com.europa.client.gui.hud.ElementFrame;
import com.europa.client.modules.client.ModuleGUI;
import com.europa.client.modules.client.ModuleHUDEditor;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Mouse;

public class HudEditorScreen
extends GuiScreen {
    public ArrayList<ElementFrame> elementFrames = new ArrayList();
    public Frame frame = new Frame(20, 20);

    public HudEditorScreen() {
        for (Element element : Europa.ELEMENT_MANAGER.getElements()) {
            this.addElement(element);
            element.setFrame(this.getFrame(element));
        }
    }
    public void addElement(final Element element) {
        this.elementFrames.add(new ElementFrame(element, Float.intBitsToFloat(Float.floatToIntBits(0.8995771f) ^ 0x7E464AAF), Float.intBitsToFloat(Float.floatToIntBits(1.853094f) ^ 0x7ECD322F), Float.intBitsToFloat(Float.floatToIntBits(0.11447282f) ^ 0x7F4A70BA), Float.intBitsToFloat(Float.floatToIntBits(0.16486806f) ^ 0x7F58D32C), this));
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.frame.drawScreen(mouseX, mouseY);
        this.frame.updatePosition(mouseX, mouseY);
        this.frame.refreshPosition();
        this.mouseScroll();
        for (final ElementFrame frame : this.elementFrames) {
            frame.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.frame.mouseClicked(mouseX, mouseY, mouseButton);
        for (final ElementFrame frame : this.elementFrames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.frame.mouseReleased(mouseX, mouseY, state);
        for (final ElementFrame frame : this.elementFrames) {
            frame.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.frame.keyTyped(typedChar, keyCode);
    }


    public void onGuiClosed() {
        block0: {
            super.onGuiClosed();
            if (ModuleHUDEditor.INSTANCE == null) break block0;
            ModuleHUDEditor.INSTANCE.disable();
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void mouseScroll() {
        block1: {
            int scroll;
            block0: {
                scroll = Mouse.getDWheel();
                if (scroll >= 0) break block0;
                this.frame.setY(this.frame.getY() - ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
                break block1;
            }
            if (scroll <= 0) break block1;
            this.frame.setY(this.frame.getY() + ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
        }
    }

    public Frame getFrame() {
        return this.frame;
    }

    public ArrayList<ElementFrame> getElementFrames() {
        return this.elementFrames;
    }

    public ElementFrame getFrame(final Element element) {
        for (final ElementFrame frame : this.elementFrames) {
            if (!frame.getElement().equals(element)) {
                continue;
            }
            return frame;
        }
        return null;
    }
}

