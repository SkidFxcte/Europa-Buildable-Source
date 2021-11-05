/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;

public class ModuleEntityFinder
extends Module {
    public static ValueBoolean donkey = new ValueBoolean("Donkey", "donkey", "", false);
    public static ValueBoolean llama = new ValueBoolean("Llama", "llama", "", false);
    public static ValueBoolean mule = new ValueBoolean("Mule", "Mule", "", false);
    public static ValueBoolean ghast = new ValueBoolean("Ghast", "Ghast", "", false);
    public static ValueBoolean enderMan = new ValueBoolean("EnderMan", "EnderMan", "", false);

    public ModuleEntityFinder() {
        super("EntityFinder", "Entity Finder", "Gives you the coordinates of certain entities to find them easily.", ModuleCategory.MISC);
    }

    @Override
    public void onUpdate() {
        for (Entity entity : ModuleEntityFinder.mc.world.loadedEntityList) {
            if (entity instanceof EntityDonkey) {
                if (donkey.getValue()) {
                    ChatManager.printChatNotifyClient("Hi " + ChatFormatting.BLUE + ModuleEntityFinder.mc.player.getName() + ChatFormatting.RESET + "You just missed a donkey at: " + ChatFormatting.GREEN + Math.round(entity.lastTickPosX) + ", " + Math.round(entity.lastTickPosY) + ", " + Math.round(entity.lastTickPosZ));
                }
            }
            if (entity instanceof EntityLlama && llama.getValue()) {
                ChatManager.printChatNotifyClient("Hi " + ChatFormatting.BLUE + ModuleEntityFinder.mc.player.getName() + ChatFormatting.RESET + "You just missed a llama at: " + ChatFormatting.GREEN + Math.round(entity.lastTickPosX) + ", " + Math.round(entity.lastTickPosY) + ", " + Math.round(entity.lastTickPosZ));
            }
            if (entity instanceof EntityMule && mule.getValue()) {
                ChatManager.printChatNotifyClient("Hi " + ChatFormatting.BLUE + ModuleEntityFinder.mc.player.getName() + ChatFormatting.RESET + "You just missed a mule at: " + ChatFormatting.GREEN + Math.round(entity.lastTickPosX) + ", " + Math.round(entity.lastTickPosY) + ", " + Math.round(entity.lastTickPosZ));
            }
            if (entity instanceof EntityGhast && ghast.getValue()) {
                ChatManager.printChatNotifyClient("Hi " + ChatFormatting.BLUE + ModuleEntityFinder.mc.player.getName() + ChatFormatting.RESET + "You just missed a ghast at: " + ChatFormatting.GREEN + Math.round(entity.lastTickPosX) + ", " + Math.round(entity.lastTickPosY) + ", " + Math.round(entity.lastTickPosZ));
            }
            if (!(entity instanceof EntityEnderman) || !enderMan.getValue()) continue;
            ChatManager.printChatNotifyClient("Hi " + ChatFormatting.BLUE + ModuleEntityFinder.mc.player.getName() + ChatFormatting.RESET + "You just missed an enderman at: " + ChatFormatting.GREEN + Math.round(entity.lastTickPosX) + ", " + Math.round(entity.lastTickPosY) + ", " + Math.round(entity.lastTickPosZ));
        }
    }
}

