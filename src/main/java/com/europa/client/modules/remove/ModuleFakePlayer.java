/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.utilities.entity.DamageUtils;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleFakePlayer
extends Module {
    public static ValueBoolean copyInv = new ValueBoolean("Copy Inventory", "copyInv", "", true);
    public static ValueBoolean pops = new ValueBoolean("Pops", "Pops", "", false);
    public EntityOtherPlayerMP fakePlayer;

    public ModuleFakePlayer() {
        super("FakePlayer", "Fake Player", "Creates a fake player", ModuleCategory.PLAYER);
    }

    @Override
    public void onEnable() {
        if (ModuleFakePlayer.mc.world == null) {
            return;
        }
        this.spawnSinglePlayer();
    }

    @Override
    public void onDisable() {
        if (ModuleFakePlayer.mc.world == null) {
            return;
        }
        ModuleFakePlayer.mc.world.removeEntityFromWorld(-100);
    }

    @Override
    public void onUpdate() {
        block2: {
            if (!pops.getValue()) break block2;
            if (this.fakePlayer != null) {
                this.fakePlayer.inventory.offHandInventory.set(0, (ItemStack) new ItemStack(Items.TOTEM_OF_UNDYING));
                if (this.fakePlayer.getHealth() <= Float.intBitsToFloat(Float.floatToIntBits(2.2270844E38f) ^ 0x7F278C16)) {
                    this.fakePop((Entity)this.fakePlayer);
                    this.fakePlayer.setHealth(Float.intBitsToFloat(Float.floatToIntBits(0.41757607f) ^ 0x7F75CC88));
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onPacketReceive(EventPacket.Receive receive) {
        block1: {
            double damage;
            SPacketExplosion explosion;
            if (this.fakePlayer == null) {
                return;
            }
            if (!(receive.getPacket() instanceof SPacketExplosion) || !(this.fakePlayer.getDistance((explosion = (SPacketExplosion)receive.getPacket()).getX(), explosion.getY(), explosion.getZ()) <= Double.longBitsToDouble(Double.doubleToLongBits(1.2157535937387267) ^ 0x7FDD73BA0A51A307L)) || !((damage = (double)DamageUtils.calculateDamage(explosion.getX(), explosion.getY(), explosion.getZ(), (EntityLivingBase)this.fakePlayer)) > Double.longBitsToDouble(Double.doubleToLongBits(2.2285043487829035E307) ^ 0x7FBFBC26084B0ECFL)) || !pops.getValue()) break block1;
            this.fakePlayer.setHealth((float)((double)this.fakePlayer.getHealth() - MathHelper.clamp((double)damage, (double)Double.longBitsToDouble(Double.doubleToLongBits(8.687006971746397E307) ^ 0x7FDEED40E3DDD5C1L), (double)Double.longBitsToDouble(Double.doubleToLongBits(0.002305631539810083) ^ 0x7FEDDB4281EC6CB5L))));
        }
    }

    /*
     * WARNING - void declaration
     */
    public void fakePop(Entity entity) {
        ModuleFakePlayer.mc.effectRenderer.emitParticleAtEntity((Entity)entity, EnumParticleTypes.TOTEM, 30);
        ModuleFakePlayer.mc.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), Float.intBitsToFloat(Float.floatToIntBits(5.1085615f) ^ 0x7F237956), Float.intBitsToFloat(Float.floatToIntBits(16.397932f) ^ 0x7E032EF7), false);
    }

    public void spawnSinglePlayer() {
        block0: {
            this.fakePlayer = new EntityOtherPlayerMP((World)ModuleFakePlayer.mc.world, new GameProfile(ModuleFakePlayer.mc.player.getUniqueID(), "FakePlayer"));
            this.fakePlayer.copyLocationAndAnglesFrom((Entity)ModuleFakePlayer.mc.player);
            this.fakePlayer.rotationYawHead = ModuleFakePlayer.mc.player.rotationYawHead;
            ModuleFakePlayer.mc.world.addEntityToWorld(-100, (Entity)this.fakePlayer);
            if (!copyInv.getValue()) break block0;
            this.fakePlayer.inventory.copyInventory(ModuleFakePlayer.mc.player.inventory);
        }
    }

    public static enum modes {
        Single,
        Multi;

    }
}

