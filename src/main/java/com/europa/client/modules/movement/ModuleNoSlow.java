/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.Europa;
import com.europa.api.manager.event.impl.player.EventKey;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ModuleNoSlow
extends Module {
    public static ValueBoolean guiMove = new ValueBoolean("GuiMove", "GuiMove", "", true);
    public static ValueBoolean noSlow = new ValueBoolean("NoSlow", "NoSlow", "", true);
    public boolean sneaking = false;
    public static KeyBinding[] keys = new KeyBinding[]{ModuleNoSlow.mc.gameSettings.keyBindForward, ModuleNoSlow.mc.gameSettings.keyBindBack, ModuleNoSlow.mc.gameSettings.keyBindLeft, ModuleNoSlow.mc.gameSettings.keyBindRight, ModuleNoSlow.mc.gameSettings.keyBindJump, ModuleNoSlow.mc.gameSettings.keyBindSprint};

    public ModuleNoSlow() {
        super("NoSlow", "No Slow", "", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onMotionUpdate() {
        if (guiMove.getValue()) {
            if (ModuleNoSlow.mc.currentScreen instanceof GuiOptions || ModuleNoSlow.mc.currentScreen instanceof GuiVideoSettings || ModuleNoSlow.mc.currentScreen instanceof GuiScreenOptionsSounds || ModuleNoSlow.mc.currentScreen instanceof GuiContainer || ModuleNoSlow.mc.currentScreen instanceof GuiIngameMenu || ModuleNoSlow.mc.currentScreen == Europa.CLICK_GUI) {
                for (KeyBinding bind : keys) {
                    KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)Keyboard.isKeyDown((int)bind.getKeyCode()));
                }
            } else if (ModuleNoSlow.mc.currentScreen == null) {
                for (KeyBinding bind : keys) {
                    if (Keyboard.isKeyDown((int)bind.getKeyCode())) continue;
                    KeyBinding.setKeyBindState((int)bind.getKeyCode(), (boolean)false);
                }
            }
        }
        Item item = ModuleNoSlow.mc.player.getActiveItemStack().getItem();
    }

    @SubscribeEvent
    public void onInput(InputUpdateEvent inputUpdateEvent) {
        if (noSlow.getValue() && ModuleNoSlow.mc.player.isHandActive() && !ModuleNoSlow.mc.player.isRiding()) {
            inputUpdateEvent.getMovementInput().moveStrafe *= Float.intBitsToFloat(Float.floatToIntBits(1.5738807f) ^ 0x7F6974EC);
            inputUpdateEvent.getMovementInput().moveForward *= Float.intBitsToFloat(Float.floatToIntBits(0.3323823f) ^ 0x7E0A2E03);
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onKeyEvent(EventKey eventKey) {
        if (guiMove.getValue() && !(ModuleNoSlow.mc.currentScreen instanceof GuiChat)) {
            eventKey.info = eventKey.pressed;
        }
    }
}

