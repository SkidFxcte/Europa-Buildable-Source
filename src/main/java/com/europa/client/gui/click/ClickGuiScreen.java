//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.gui.click;

import org.lwjgl.input.Mouse;
import com.europa.client.modules.client.ModuleGUI;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.gui.ScaledResolution;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.client.gui.click.components.Frame;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiScreen extends GuiScreen
{
    public static ClickGuiScreen INSTANCE;
    public ArrayList<Frame> frames;

    public ClickGuiScreen() {
        this.frames = new ArrayList<Frame>();
        int offset = 0;
        for (final ModuleCategory category : ModuleCategory.values()) {
            if (category != ModuleCategory.HUD) {
                this.frames.add(new Frame(category, 10 + offset, 20));
                offset += 124;
            }
        }
    }

    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        for (final Frame panel : this.frames) {
            panel.drawScreen(mouseX, mouseY);
            panel.updatePosition(mouseX, mouseY);
            panel.refreshPosition();
            this.mouseScroll();
        }
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (final Frame panel : this.frames) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (final Frame panel : this.frames) {
            panel.mouseReleased(mouseX, mouseY, state);
        }
    }

    public void keyTyped(final char typedChar, final int key) throws IOException {
        super.keyTyped(typedChar, key);
        for (final Frame panel : this.frames) {
            panel.keyTyped(typedChar, key);
        }
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        ModuleGUI.INSTANCE.disable();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void mouseScroll() {
        final int scroll = Mouse.getDWheel();
        for (final Frame panel : this.frames) {
            if (scroll < 0) {
                panel.setY(panel.getY() - ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
            }
            else {
                if (scroll <= 0) {
                    continue;
                }
                panel.setY(panel.getY() + ModuleGUI.INSTANCE.scrollSpeed.getValue().intValue());
            }
        }
    }

    public static String capitalize(final String input) {
        return input;
    }

    static {
        ClickGuiScreen.INSTANCE = new ClickGuiScreen();
    }
}
