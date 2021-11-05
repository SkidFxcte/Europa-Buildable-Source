/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.module;

import com.europa.Europa;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.Value;
import com.europa.api.manager.value.impl.ValueBind;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.client.modules.client.ModuleColor;
import com.europa.client.modules.client.ModuleNotifications;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public ArrayList<Value> values;
    public String name;
    public String description;
    public ModuleCategory category;
    public boolean toggled;
    public boolean persistent;
    public ValueString tag = new ValueString("Tag", "Tag", "Let's you customize the module's name.", "Placeholder");
    public ValueBoolean chatNotify = new ValueBoolean("ChatNotify", "ChatNotify", "Sends a message when the module is toggled.", true);
    public ValueBoolean drawn = new ValueBoolean("Drawn", "Drawn", "Puts the module on the array list hud component.", true);
    public ValueBind bind = new ValueBind("Bind", "Bind", "The bind for the module.", 0);

    public Module(String name, String tag, String description, ModuleCategory category) {
        this.name = name;
        this.tag.setValue(tag);
        this.description = description;
        this.category = category;
        this.persistent = false;
        this.values = new ArrayList();
    }

    public Module(String name, String tag, String description, ModuleCategory category, boolean persistent) {
        this.name = name;
        this.tag.setValue(tag);
        this.description = description;
        this.category = category;
        this.persistent = persistent;
        this.values = new ArrayList();
        this.persist();
    }

    public void onUpdate() {
    }

    public void onMotionUpdate() {
    }

    public void onRender2D(EventRender2D eventRender2D) {
    }

    public void onRender3D(EventRender3D eventRender3D) {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onLogin() {
    }

    public void onLogout() {
    }

    public void onDeath() {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ModuleCategory getCategory() {
        return this.category;
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
    }


    public String getTag() {
        return this.tag.getValue();
    }

    public void setTag(final String tag) {
        this.tag.setValue(tag);
    }

    public boolean isChatNotify() {
        return this.chatNotify.getValue();
    }

    public void setChatNotify(final boolean chatNotify) {
        this.chatNotify.setValue(chatNotify);
    }

    public boolean isDrawn() {
        return this.drawn.getValue();
    }

    public void setDrawn(final boolean drawn) {
        this.drawn.setValue(drawn);
    }

    public int getBind() {
        return this.bind.getValue();
    }

    public void setBind(final int bind) {
        this.bind.setValue(bind);
    }

    public String getHudInfo() {
        return "";
    }

    public void persist() {
        block0: {
            if (!this.persistent) break block0;
            this.setToggled(true);
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }

    public void toggle() {
        if (this.toggled) {
            this.disable();
        } else {
            this.enable();
        }
    }

    public void enable() {
        block6: {
            if (this.persistent) break block6;
            this.setToggled(true);
            this.onEnable();
            int moduleNumber = 0;
            for (char character : this.name.toCharArray()) {
                moduleNumber += character;
                moduleNumber *= 10;
            }
            if (this.isChatNotify()) {
                if (ModuleColor.prefixMode.getValue().equals((Object)ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals((Object)ModuleColor.prefixModes.Gradient)) {
                    ChatManager.sendClientMessage(this.name + "\u00a7r" + ChatFormatting.GREEN + ChatFormatting.BOLD + " Enabled!", moduleNumber);
                } else {
                    ChatManager.sendClientMessage(this.name + ChatFormatting.GREEN + ChatFormatting.BOLD + " Enabled!", moduleNumber);
                }
            }
            if (Europa.getModuleManager().isModuleEnabled("Notifications")) {
                if (ModuleNotifications.chatNotify.getValue() && this.isChatNotify()) {
                    Europa.NOTIFICATION_PROCESSOR.addNotification(this.name + ChatFormatting.GREEN + ChatFormatting.BOLD + " Enabled!", ModuleNotifications.lifetime.getValue().intValue(), ModuleNotifications.inOutTime.getValue().intValue());
                }
            }
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
    }

    public void disable() {
        block6: {
            if (this.persistent) break block6;
            this.setToggled(false);
            this.onDisable();
            int moduleNumber = 0;
            for (char character : this.name.toCharArray()) {
                moduleNumber += character;
                moduleNumber *= 10;
            }
            if (this.isChatNotify()) {
                if (ModuleColor.prefixMode.getValue().equals((Object)ModuleColor.prefixModes.Rainbow) || ModuleColor.prefixMode.getValue().equals((Object)ModuleColor.prefixModes.Gradient)) {
                    ChatManager.sendClientMessage(this.name + "\u00a7r" + ChatFormatting.RED + ChatFormatting.BOLD + " Disabled!", moduleNumber);
                } else {
                    ChatManager.sendClientMessage(this.name + ChatFormatting.RED + ChatFormatting.BOLD + " Disabled!", moduleNumber);
                }
            }
            if (Europa.getModuleManager().isModuleEnabled("Notifications")) {
                if (ModuleNotifications.chatNotify.getValue() && this.isChatNotify()) {
                    Europa.NOTIFICATION_PROCESSOR.addNotification(this.name + ChatFormatting.RED + ChatFormatting.BOLD + " Disabled!", ModuleNotifications.lifetime.getValue().intValue(), ModuleNotifications.inOutTime.getValue().intValue());
                }
            }
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }

    public Value getValue(final String name) {
        for (final Value value : this.values) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public void addValue(final Value value) {
        this.values.add(value);
    }


    public ArrayList<Value> getValues() {
        return this.values;
    }

    public static Color globalColor(final int alpha) {
        return new Color(ModuleColor.daColor.getValue().getRed(), ModuleColor.daColor.getValue().getGreen(), ModuleColor.daColor.getValue().getBlue(), alpha);
    }
}

