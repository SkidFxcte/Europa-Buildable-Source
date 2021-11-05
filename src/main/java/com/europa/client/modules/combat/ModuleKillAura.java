/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.combat;

import com.europa.Europa;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.EntityUtils;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.client.modules.combat.ModuleCriticals;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.GameType;

public class ModuleKillAura
extends Module {
    public static EntityLivingBase target = null;
    public static boolean isAttacking = false;
    public TimerUtils timer = new TimerUtils();
    public ValueEnum sorting = new ValueEnum("Sorting", "Sorting", "The mode for the Target sorting.", Sorts.Range);
    public ValueEnum criticals = new ValueEnum("Criticals", "Criticals", "Integrated criticals on the Kill Aura to prevent desync.", CriticalsModes.None);
    public ValueBoolean attackPacket = new ValueBoolean("AttackPacket", "AttackPacket", "Attacks through packets instead of normally.", false);
    public ValueBoolean attackDelay = new ValueBoolean("AttackDelay", "AttackDelay", "The 1.9 attack delay.", true);
    public ValueNumber attackSpeed = new ValueNumber("AttackSpeed", "AttackSpeed", "The speed for attacking.", 10, 1, 20);
    public ValueEnum weapon = new ValueEnum("Weapon", "Weapon", "The requirements for the weapon.", Requirements.None);
    public ValueEnum priority = new ValueEnum("Priority", "Priority", "The priority in weapon switching.", Priorities.Sword);
    public ValueNumber ticksExisted = new ValueNumber("TicksExisted", "TicksExisted", "The amount of ticks that the target shohuld have existed before attacking.", 20, 0, 150);
    public ValueNumber range = new ValueNumber("Range", "Range", "The maximum range that the target should be away.", Double.longBitsToDouble(Double.doubleToLongBits(0.003700867263951801) ^ 0x7F7A5147FBB241FFL), Double.longBitsToDouble(Double.doubleToLongBits(1.5165299965144532E308) ^ 0x7FEAFEC0D713B8F2L), Double.longBitsToDouble(Double.doubleToLongBits(1.4232743395521874) ^ 0x7FEEC5BB5059CD64L));
    public ValueNumber wallsRange = new ValueNumber("WallsRange", "WallsRange", "The maximum range that the target should be away through walls.", Double.longBitsToDouble(Double.doubleToLongBits(0.4792832706756725) ^ 0x7FD2AC93BD449A7DL), Double.longBitsToDouble(Double.doubleToLongBits(7.121461362646026E307) ^ 0x7FD95A6E3338ABA7L), Double.longBitsToDouble(Double.doubleToLongBits(0.20190742438921186) ^ 0x7FD1D81A3C491EF9L));
    public ValueBoolean rotation = new ValueBoolean("Rotation", "Rotation", "Rotates when attacking.", false);
    public ValueEnum swing = new ValueEnum("Swing", "Swing", "The arm to swing with.", Hands.Mainhand);
    public ValueBoolean silentSwing = new ValueBoolean("SilentSwing", "SilentSwing", "Swings serverside but not clientside.", false);
    public ValueBoolean players = new ValueBoolean("Players", "Players", "Attacks players.", true);
    public ValueBoolean animals = new ValueBoolean("Animals", "Animals", "Attacks animals.", true);
    public ValueBoolean hostiles = new ValueBoolean("Hostiles", "Hostiles", "Attacks hostiles.", true);
    public ValueBoolean invisibles = new ValueBoolean("Invisibles", "Invisibles", "Attacks invisibles.", true);
    public ValueBoolean vehicles = new ValueBoolean("Vehicles", "Vehicles", "Attacks vehicles.", true);
    public ValueBoolean projectiles = new ValueBoolean("Projectiles", "Projectiles", "Attacks projectiles.", true);

    public ModuleKillAura() {
        super("KillAura", "Kill Aura", "Automatically hits entities with weapons in order to drain their health and armor.", ModuleCategory.COMBAT);
    }

    @Override
    public void onUpdate() {
        block4: {
            block3: {
                target = this.getTarget();
                if (target == null) {
                    return;
                }
                if (this.weapon.getValue().equals((Object)Requirements.Require) && !(ModuleKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(ModuleKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
                    return;
                }
                if (this.weapon.getValue().equals((Object)Requirements.Switch) && !(ModuleKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(ModuleKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
                    int swordSlot = InventoryUtils.findItem(Items.DIAMOND_SWORD, 0, 9);
                    int axeSlot = InventoryUtils.findItem(Items.DIAMOND_AXE, 0, 9);
                    int slot = swordSlot == -1 && axeSlot == -1 ? -1 : (swordSlot != -1 && axeSlot == -1 ? swordSlot : (swordSlot == -1 ? axeSlot : (this.priority.getValue().equals((Object)Priorities.Sword) ? swordSlot : axeSlot)));
                    InventoryUtils.switchSlot(slot, false);
                }
                if (!this.attackDelay.getValue()) break block3;
                if (!(ModuleKillAura.mc.player.getCooledAttackStrength(Float.intBitsToFloat(Float.floatToIntBits(7.2705314E37f) ^ 0x7E5ACA23)) >= Float.intBitsToFloat(Float.floatToIntBits(12.720506f) ^ 0x7ECB8731))) break block4;
                this.attack((Entity)target);
                this.swing();
                break block4;
            }
            if (!this.timer.hasReached(((long)-894143224 ^ 0xFFFFFFFFCAB476E0L) / this.attackSpeed.getValue().longValue())) break block4;
            this.timer.reset();
            this.attack((Entity)target);
            this.swing();
        }
    }

    public EntityLivingBase getTarget() {
        EntityLivingBase targetEntity = null;
        for (final Entity e : new ArrayList<Entity>(ModuleKillAura.mc.world.loadedEntityList)) {
            if (!(e instanceof EntityLivingBase)) {
                continue;
            }
            final EntityLivingBase entity = (EntityLivingBase)e;
            if (ModuleKillAura.mc.player.getDistance((Entity)entity) > this.range.getValue().floatValue()) {
                continue;
            }
            if (!ModuleKillAura.mc.player.canEntityBeSeen((Entity)entity) && ModuleKillAura.mc.player.getDistance((Entity)entity) >= this.wallsRange.getValue().floatValue()) {
                continue;
            }
            if (Europa.FRIEND_MANAGER.isFriend(e.getName())) {
                continue;
            }
            if (!EntityUtils.isAlive((Entity)entity)) {
                continue;
            }
            if (entity == ModuleKillAura.mc.player) {
                if (entity.getName().equals(ModuleKillAura.mc.player.getName())) {
                    continue;
                }
            }
            if (entity.hurtTime != 0) {
                continue;
            }
            if (entity.ticksExisted <= this.ticksExisted.getValue().intValue()) {
                continue;
            }
            if (!this.projectiles.getValue() && EntityUtils.isProjectile((Entity)entity)) {
                continue;
            }
            if (!this.vehicles.getValue() && EntityUtils.isVehicle((Entity)entity)) {
                continue;
            }
            if (!this.hostiles.getValue() && EntityUtils.isMobAggressive((Entity)entity)) {
                continue;
            }
            if (!this.animals.getValue() && EntityUtils.isPassive((Entity)entity)) {
                continue;
            }
            if (!this.invisibles.getValue() && entity.isInvisible()) {
                continue;
            }
            if (!this.players.getValue() && entity instanceof EntityPlayer) {
                continue;
            }
            if (targetEntity == null) {
                targetEntity = entity;
            }
            else if (this.sorting.getValue().equals(Sorts.Range)) {
                if (ModuleKillAura.mc.player.getDistanceSq((Entity)entity) >= ModuleKillAura.mc.player.getDistanceSq((Entity)targetEntity)) {
                    continue;
                }
                targetEntity = entity;
            }
            else {
                if (entity.getHealth() >= targetEntity.getHealth()) {
                    continue;
                }
                targetEntity = entity;
            }
        }
        return targetEntity;
    }

    public void attack(final Entity entity) {
        ModuleCriticals.doCritical(this.criticals.getValue().toString());
        if (this.attackPacket.getValue()) {
            ModuleKillAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
            if (ModuleKillAura.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                ModuleKillAura.mc.player.resetCooldown();
            }
        }
        else {
            ModuleKillAura.mc.playerController.attackEntity((EntityPlayer)ModuleKillAura.mc.player, entity);
        }
    }


    public void swing() {
        if (!this.swing.getValue().equals(Hands.None)) {
            if (this.silentSwing.getValue()) {
                switch ((Hands)this.swing.getValue()) {
                    case Mainhand: {
                        ModuleKillAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        break;
                    }
                    case Offhand: {
                        ModuleKillAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                        break;
                    }
                    case Both: {
                        ModuleKillAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        ModuleKillAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                        break;
                    }
                }
            }
            else {
                switch ((Hands)this.swing.getValue()) {
                    case Mainhand: {
                        ModuleKillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                        break;
                    }
                    case Offhand: {
                        ModuleKillAura.mc.player.swingArm(EnumHand.OFF_HAND);
                        break;
                    }
                    case Both: {
                        ModuleKillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                        ModuleKillAura.mc.player.swingArm(EnumHand.OFF_HAND);
                        break;
                    }
                }
            }
        }
    }

    public static enum CriticalsModes {
        None,
        Packet,
        Jump,
        MiniJump,
        Bypass;

    }

    public static enum Hands {
        None,
        Mainhand,
        Offhand,
        Both;

    }

    public static enum Priorities {
        Sword,
        Axe;

    }

    public static enum Requirements {
        None,
        Switch,
        Require;

    }

    public static enum Sorts {
        Range,
        Health,
        Focus;

    }
}

