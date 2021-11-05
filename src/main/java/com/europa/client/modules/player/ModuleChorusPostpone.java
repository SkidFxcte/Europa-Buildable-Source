/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.event.impl.player.EventPlayerTravel;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueNumber;
import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleChorusPostpone
extends Module {
    public ValueNumber sDelay = new ValueNumber("Delay", "Delay", "", 18, 0, 500);
    public int delay = 0;
    public int delay2 = 0;
    public boolean ateChorus = false;
    public boolean hackPacket = false;
    public boolean posTp = false;
    public double posX;
    public double posY;
    public double posZ;
    public Queue<CPacketPlayer> packets = new LinkedList<CPacketPlayer>();
    public Queue<CPacketConfirmTeleport> packetss = new LinkedList<CPacketConfirmTeleport>();

    public ModuleChorusPostpone() {
        super("ChorusPostpone", "ChorusPostpone", "", ModuleCategory.PLAYER);
    }

    @Override
    public void onEnable() {
        this.ateChorus = false;
        this.hackPacket = false;
        this.posTp = false;
    }

    @Override
    public void onUpdate() {
        block2: {
            if (this.ateChorus) {
                ++this.delay;
                ++this.delay2;
                if (!ModuleChorusPostpone.mc.player.getPosition().equals((Object)new BlockPos(this.posX, this.posY, this.posZ)) && !this.posTp && ModuleChorusPostpone.mc.player.getDistance(this.posX, this.posY, this.posZ) > Double.longBitsToDouble(Double.doubleToLongBits(16.526375309693773) ^ 0x7FC086C088448E7FL)) {
                    ModuleChorusPostpone.mc.player.setPosition(this.posX, this.posY, this.posZ);
                    this.posTp = true;
                }
            }
            if (!this.ateChorus || this.delay2 <= this.sDelay.getValue().intValue()) break block2;
            this.ateChorus = false;
            this.delay = 0;
            this.hackPacket = true;
            this.delay2 = 0;
            this.sendPackets();
        }
    }

    public void sendPackets() {
        while (!this.packets.isEmpty()) {
            ModuleChorusPostpone.mc.player.connection.sendPacket((Packet)this.packets.poll());
        }
        while (!this.packetss.isEmpty()) {
            ModuleChorusPostpone.mc.player.connection.sendPacket((Packet)this.packetss.poll());
        }
        this.hackPacket = false;
        this.delay2 = 0;
        this.ateChorus = false;
    }

    @SubscribeEvent
    public void Event(EventPlayerTravel eventPlayerTravel) {
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Finish finish) {
        if (finish.getEntity() == ModuleChorusPostpone.mc.player && finish.getResultStack().getItem().equals(Items.CHORUS_FRUIT)) {
            this.posX = ModuleChorusPostpone.mc.player.posX;
            this.posY = ModuleChorusPostpone.mc.player.posY;
            this.posZ = ModuleChorusPostpone.mc.player.posZ;
            this.posTp = false;
            this.ateChorus = true;
        }
    }

    @SubscribeEvent
    public void finishEating(LivingEntityUseItemEvent.Start start) {
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onSend(EventPacket.Send send) {
        block1: {
            if (send.getPacket() instanceof CPacketConfirmTeleport && this.ateChorus && this.delay2 < this.sDelay.getValue().intValue()) {
                this.packetss.add((CPacketConfirmTeleport)send.getPacket());
                send.setCancelled(true);
            }
            if (!(send.getPacket() instanceof CPacketPlayer) || !this.ateChorus || this.delay2 >= this.sDelay.getValue().intValue()) break block1;
            this.packets.add((CPacketPlayer)send.getPacket());
            send.setCancelled(true);
        }
    }
}

