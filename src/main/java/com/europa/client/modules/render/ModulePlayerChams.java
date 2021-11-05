/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import java.awt.Color;

public class ModulePlayerChams
extends Module {
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueBoolean outline = new ValueBoolean("Outline", "Outline", "", false);
    public static ValueEnum outlineMode = new ValueEnum("OutlineMode", "OutlineMode", "", outlineModes.Wire);
    public static ValueNumber width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(0.04985685091218272) ^ 0x7FA186D6504C7A8FL), Double.longBitsToDouble(Double.doubleToLongBits(3.155963099864922) ^ 0x7FE93F6994EA6BC5L), Double.longBitsToDouble(Double.doubleToLongBits(0.2458582575795987) ^ 0x7FDB78488BE0C201L));
    public static ValueBoolean enchanted = new ValueBoolean("Glint", "Glint", "", false);
    public static ValueColor enchantedColor = new ValueColor("GlintColor", "GlintColor", "", new Color(0, 255, 120, 255));
    public static ValueBoolean visible = new ValueBoolean("Visible", "Visible", "", false);
    public static ValueBoolean hidden = new ValueBoolean("Hidden", "Hidden", "", false);
    public static ValueColor daColor = new ValueColor("VisibleColor", "VisibleColor", "", new Color(0, 255, 120, 255));
    public static ValueColor hiddenColor = new ValueColor("HiddenColor", "HiddenColor", "", new Color(0, 100, 255, 255));
    public static ValueBoolean hiddenSync = new ValueBoolean("HiddenSync", "HiddenSync", "", true);
    public static ValueColor outlineColor = new ValueColor("OutColor", "OutColor", "", new Color(255, 255, 255));
    public static ValueBoolean syncFriend = new ValueBoolean("SyncFriend", "SyncFriend", "", true);
    public static ValueBoolean fvisible = new ValueBoolean("FriendVisible", "FriendVisible", "", false);
    public static ValueBoolean fhidden = new ValueBoolean("FriendHidden", "FriendHidden", "", false);
    public static ValueColor fdaColor = new ValueColor("FriendVisibleColor", "FriendVisibleColor", "", new Color(255, 150, 255, 255));
    public static ValueColor fhiddenColor = new ValueColor("FriendHiddenColor", "FriendHiddenColor", "", new Color(255, 100, 100, 255));
    public static ValueBoolean fhiddenSync = new ValueBoolean("HiddenSync", "FriendHiddenSync", "", true);
    public static ValueColor foutlineColor = new ValueColor("FriendOutColor", "FriendOutColor", "", new Color(255, 255, 255));
    public static Color color;
    public static Color outColor;
    public static Color hideColor;
    public static Color fcolor;
    public static Color foutColor;
    public static Color fhideColor;

    public ModulePlayerChams() {
        super("PlayerChams", "Player Chams", "Draws chams on players to make them render better.", ModuleCategory.RENDER);
    }

    @Override
    public void onUpdate() {
        if (syncColor.getValue()) {
            color = ModulePlayerChams.globalColor(255);
            outColor = ModulePlayerChams.globalColor(255);
            hideColor = ModulePlayerChams.globalColor(255);
            fcolor = ModulePlayerChams.globalColor(255);
            foutColor = ModulePlayerChams.globalColor(255);
            fhideColor = ModulePlayerChams.globalColor(255);
        } else {
            color = daColor.getValue();
            outColor = outlineColor.getValue();
            hideColor = hiddenColor.getValue();
            if (syncFriend.getValue()) {
                fcolor = daColor.getValue();
                foutColor = outlineColor.getValue();
                fhideColor = hiddenColor.getValue();
            } else {
                fcolor = fdaColor.getValue();
                foutColor = foutlineColor.getValue();
                fhideColor = fhiddenColor.getValue();
            }
        }
    }

    public static enum outlineModes {
        Wire,
        Flat;

    }
}

