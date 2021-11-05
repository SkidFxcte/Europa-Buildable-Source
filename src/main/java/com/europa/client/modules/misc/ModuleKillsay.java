/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventDeath;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.client.modules.combat.ModuleAutoCrystal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleKillsay
extends Module {
    public static ArrayList<String> deathMessages = new ArrayList();
    public ValueBoolean greenText = new ValueBoolean("GreenText", "GreenText", "", false);
    public ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.Default);
    public ValueString customGG = new ValueString("CustomGG", "CustomGG", "", "GG <player>, Europa on top!");
    public ValueNumber delay = new ValueNumber("Delay", "Delay", "", 10, 0, 30);
    public ValueNumber reset = new ValueNumber("Reset", "Reset", "", 30, 0, 90);
    public Map<EntityPlayer, Integer> targets = new ConcurrentHashMap<EntityPlayer, Integer>();
    public List<String> messages = new ArrayList<String>();
    public EntityPlayer cauraTarget;
    public TimerUtils timer = new TimerUtils();
    public TimerUtils cooldownTimer = new TimerUtils();
    public boolean cooldown;

    public ModuleKillsay() {
        super("Killsay", "Killsay", "Automatically sends a message in chat when you kill a player.", ModuleCategory.MISC);
        deathMessages.add("<player> you have been put to sleep by Europa, goodnight!");
        deathMessages.add("Sit <player>, Europa owns me and all");
        deathMessages.add("Well played <player>!");
        deathMessages.add("See you later <player> :P");
        deathMessages.add("<player> zZz >o<");
    }

    @Override
    public void onEnable() {
        this.timer.reset();
        this.cooldownTimer.reset();
    }

    @Override
    public void onUpdate() {
        if (ModuleAutoCrystal.INSTANCE.target != null && this.cauraTarget != ModuleAutoCrystal.INSTANCE.target) {
            this.cauraTarget = ModuleAutoCrystal.INSTANCE.target;
        }
        if (!this.cooldown) {
            this.cooldownTimer.reset();
        }
        if (this.cooldownTimer.passed(this.delay.getValue().intValue() * 1000) && this.cooldown) {
            this.cooldown = false;
            this.cooldownTimer.reset();
        }
        if (ModuleAutoCrystal.INSTANCE.target != null) {
            this.targets.put(ModuleAutoCrystal.INSTANCE.target, (int)(this.timer.getTimePassed() / ((long)-979539421 ^ 0xFFFFFFFFC59D69CBL)));
        }
        this.targets.replaceAll(this::lambda$onUpdate$0);
        for (EntityPlayer player : this.targets.keySet()) {
            if (this.targets.get(player) <= this.reset.getValue().intValue()) continue;
            this.targets.remove(player);
            this.timer.reset();
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onEntityDeath(EventDeath eventDeath) {
        if (this.targets.containsKey(eventDeath.player) && !this.cooldown) {
            this.announceDeath(eventDeath.player);
            this.cooldown = true;
            this.targets.remove(eventDeath.player);
        }
        if (eventDeath.player == this.cauraTarget) {
            if (!this.cooldown) {
                this.announceDeath(eventDeath.player);
                this.cooldown = true;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
        if (attackEntityEvent.getTarget() instanceof EntityPlayer && !Europa.FRIEND_MANAGER.isFriend(attackEntityEvent.getEntityPlayer().getName())) {
            this.targets.put((EntityPlayer)attackEntityEvent.getTarget(), 0);
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onSendAttackPacket(EventPacket.Send send) {
        CPacketUseEntity packet;
        if (send.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)send.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)ModuleKillsay.mc.world) instanceof EntityPlayer) {
            if (!Europa.FRIEND_MANAGER.isFriend(((EntityPlayer)packet.getEntityFromWorld((World)ModuleKillsay.mc.world)).getName())) {
                this.targets.put((EntityPlayer)packet.getEntityFromWorld((World)ModuleKillsay.mc.world), 0);
            }
        }
    }

    public String returnRandomMessage() {
        Random random = new Random();
        return deathMessages.get(random.nextInt(deathMessages.size()));
    }

    /*
     * WARNING - void declaration
     */
    public void announceDeath(EntityPlayer entityPlayer) {
        switch (mode.getValue().ordinal()) {
            case 1: {
                ModuleKillsay.mc.player.connection.sendPacket((Packet)new CPacketChatMessage((this.greenText.getValue() ? ">" : "") + "GG " + entityPlayer.getDisplayNameString() + ", Europa owns me and all!"));
                break;
            }
            case 2: {
                ModuleKillsay.mc.player.connection.sendPacket((Packet)new CPacketChatMessage((this.greenText.getValue() ? ">" : "") + this.returnRandomMessage().replaceAll("<player>", entityPlayer.getDisplayNameString())));
                break;
            }
            case 3: {
                ModuleKillsay.mc.player.connection.sendPacket((Packet)new CPacketChatMessage((this.greenText.getValue() ? ">" : "") + this.customGG.getValue().replaceAll("<player>", entityPlayer.getDisplayNameString())));
                break;
            }
        }
    }

    public Integer lambda$onUpdate$0(EntityPlayer entityPlayer, Integer n) {
        return (int)(this.timer.getTimePassed() / ((long)1567124149 ^ 0x5D68695DL));
    }

    public enum modes {
        Default,
        Random,
        Custom;

    }
}

