/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.Europa;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.utilities.entity.InventoryUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class ModuleMiddleClick
extends Module {
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.XP);
    public int oldSlot = -1;
    public int delay = 0;

    public ModuleMiddleClick() {
        super("MiddleClick", "Middle Click", "", ModuleCategory.CLIENT);
    }

    @Override
    public void onMotionUpdate() {
        block2: {
            int pearlSlot;
            block1: {
                this.oldSlot = ModuleMiddleClick.mc.player.inventory.currentItem;
                pearlSlot = InventoryUtils.getHotbarItemSlot(Items.ENDER_PEARL);
                if (!this.mode.getValue().equals((Object)modes.XP)) break block1;
                if (!Mouse.isButtonDown((int)2) || this.hotbarXP() == -1) break block2;
                ModuleMiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.hotbarXP()));
                ModuleMiddleClick.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                ModuleMiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
                break block2;
            }
            if (!this.mode.getValue().equals((Object)modes.Pearl)) break block2;
            if (Mouse.isButtonDown((int)2) && pearlSlot != -1) {
                ModuleMiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(pearlSlot));
                ModuleMiddleClick.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                ModuleMiddleClick.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldSlot));
            }
        }
    }

    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals((Object)modes.MCF)) {
            ++this.delay;
            RayTraceResult object = ModuleMiddleClick.mc.objectMouseOver;
            if (object == null) {
                return;
            }
            if (object.typeOfHit == RayTraceResult.Type.ENTITY) {
                Entity entity = object.entityHit;
                if (entity instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !ModuleMiddleClick.mc.player.isDead && ModuleMiddleClick.mc.player.canEntityBeSeen(entity)) {
                    EntityPlayer player = (EntityPlayer)entity;
                    String ID = entity.getName();
                    if (Mouse.isButtonDown((int)2) && ModuleMiddleClick.mc.currentScreen == null && !Europa.FRIEND_MANAGER.isFriend(ID) && this.delay > 10) {
                        Europa.FRIEND_MANAGER.addFriend(ID);
                        ChatManager.printChatNotifyClient("" + ChatFormatting.GREEN + ChatFormatting.BOLD + "Added " + ChatFormatting.RESET + ChatFormatting.WHITE + ID + " as friend");
                        this.delay = 0;
                    }
                    if (Mouse.isButtonDown((int)2)) {
                        if (ModuleMiddleClick.mc.currentScreen == null) {
                            if (Europa.FRIEND_MANAGER.isFriend(ID)) {
                                if (this.delay > 10) {
                                    Europa.FRIEND_MANAGER.removeFriend(ID);
                                    ChatManager.printChatNotifyClient("" + ChatFormatting.RED + ChatFormatting.BOLD + "Removed " + ChatFormatting.RESET + ChatFormatting.WHITE + ID + " as friend");
                                    this.delay = 0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int hotbarXP() {
        for (int i = 0; i < 9; ++i) {
            if (ModuleMiddleClick.mc.player.inventory.getStackInSlot(i).getItem() != Items.EXPERIENCE_BOTTLE) continue;
            return i;
        }
        return -1;
    }

    public static enum modes {
        MCF,
        XP,
        Pearl;

    }
}

