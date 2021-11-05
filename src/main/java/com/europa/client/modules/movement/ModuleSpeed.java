/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.event.impl.player.EventPlayerMove;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.client.minecraft.Entity;
import com.europa.client.minecraft.Minecraft;
import com.europa.client.minecraft.Timer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleSpeed
extends Module {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.Strafe);
    public static ValueBoolean timer = new ValueBoolean("UseTimer", "UseTimer", "", false);
    public static ValueBoolean inWater = new ValueBoolean("SpeedInWater", "SpeedInWater", "", false);
    public double speed;
    public float timerSpeed;

    public ModuleSpeed() {
        super("Speed", "Speed", "Fast", ModuleCategory.MOVEMENT);
    }

    /*
     * WARNING - void declaration
     */
    public void manageY(EventPlayerMove eventPlayerMove) {
        double jump = Double.longBitsToDouble(Double.doubleToLongBits(2.695160498823184) ^ 0x7FDC2275B8D18993L);
        if (ModuleSpeed.mc.player.moveForward != Float.intBitsToFloat(Float.floatToIntBits(2.3037E38f) ^ 0x7F2D4FA6) || ModuleSpeed.mc.player.moveStrafing != Float.intBitsToFloat(Float.floatToIntBits(1.7930399E38f) ^ 0x7F06E4B1)) {
            if (ModuleSpeed.mc.player.onGround) {
                if (ModuleSpeed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    jump += (double)((float)(ModuleSpeed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * Float.intBitsToFloat(Float.floatToIntBits(219.02307f) ^ 0x7E97C925));
                }
                ModuleSpeed.mc.player.motionY = jump;
                eventPlayerMove.setY(ModuleSpeed.mc.player.motionY);
                this.speed *= Double.longBitsToDouble(Double.doubleToLongBits(0.23248319010177745) ^ 0x7FCCF324B0557A97L);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        if (timer.getValue() && receive.getPacket() instanceof SPacketPlayerPosLook) {
            this.timerSpeed = Float.intBitsToFloat(Float.floatToIntBits(5.066575f) ^ 0x7F222162);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onMotionUpdate() {
        if (timer.getValue()) {
            this.timerSpeed = (double)ModuleSpeed.mc.player.moveForward != Double.longBitsToDouble(Double.doubleToLongBits(1.168460553614635E307) ^ 0x7FB0A3B1B96EA6F7L) || (double)ModuleSpeed.mc.player.moveStrafing != Double.longBitsToDouble(Double.doubleToLongBits(5.114650836328078E307) ^ 0x7FD235716300478BL) ? Math.min(this.timerSpeed + Float.intBitsToFloat(Float.floatToIntBits(4392.4673f) ^ 0x7E4DD81B), Float.intBitsToFloat(Float.floatToIntBits(8.297638f) ^ 0x7E880FED)) : Float.intBitsToFloat(Float.floatToIntBits(13.313794f) ^ 0x7ED5054D);
            Timer.tickLength = Float.intBitsToFloat(Float.floatToIntBits(1.9499959f) ^ 0x7DB19977) / this.timerSpeed;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @SubscribeEvent
    public void onMove(EventPlayerMove eventPlayerMove) {
        float rotationYaw = ModuleSpeed.mc.player.rotationYaw;
        this.speed = Double.longBitsToDouble(Double.doubleToLongBits(17.891275207625373) ^ 0x7FE387351CAC7C91L);
        if (ModuleSpeed.mc.player == null) return;
        if (ModuleSpeed.mc.world == null) {
            return;
        }
        if (ModuleSpeed.mc.player.isSneaking()) return;
        if (ModuleSpeed.mc.player.isOnLadder()) return;
        if (Entity.isInWeb) return;
        if (ModuleSpeed.mc.player.capabilities.isFlying) {
            return;
        }
        if ((ModuleSpeed.mc.player.isInWater() || ModuleSpeed.mc.player.isInLava()) && !inWater.getValue()) {
            return;
        }
        if (ModuleSpeed.mc.player.isPotionActive(MobEffects.SPEED)) {
            int amp = ModuleSpeed.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            this.speed *= Double.longBitsToDouble(Double.doubleToLongBits(4.402556667386651) ^ 0x7FE19C37D0A4D984L) + Double.longBitsToDouble(Double.doubleToLongBits(8.456795135867578) ^ 0x7FE9707894CA924DL) * (double)(amp + 1);
        }
        double moveForward = ModuleSpeed.mc.player.movementInput.moveForward;
        double moveStrafe = ModuleSpeed.mc.player.movementInput.moveStrafe;
        if (moveForward == Double.longBitsToDouble(Double.doubleToLongBits(5.344542829584502E307) ^ 0x7FD306F6D85833E3L) && moveStrafe == Double.longBitsToDouble(Double.doubleToLongBits(1.3883286443143587E308) ^ 0x7FE8B68BC4908BF8L)) {
            eventPlayerMove.setX(Double.longBitsToDouble(Double.doubleToLongBits(5.932969827784931E307) ^ 0x7FD51F4038F4EC95L));
            eventPlayerMove.setZ(Double.longBitsToDouble(Double.doubleToLongBits(5.698898304712973E307) ^ 0x7FD449EB9D037E99L));
        } else if (moveForward != Double.longBitsToDouble(Double.doubleToLongBits(6.426659128430416E307) ^ 0x7FD6E131C9CC2805L)) {
            if (moveStrafe >= Double.longBitsToDouble(Double.doubleToLongBits(7.986324745706278) ^ 0x7FEFF1FF1D382D09L)) {
                rotationYaw += moveForward > Double.longBitsToDouble(Double.doubleToLongBits(1.5740612702072556E308) ^ 0x7FEC04EBA55E9172L) ? Float.intBitsToFloat(Float.floatToIntBits(-0.03403187f) ^ 0x7F3F6501) : Float.intBitsToFloat(Float.floatToIntBits(0.03760315f) ^ 0x7F2E05C3);
                moveStrafe = Double.longBitsToDouble(Double.doubleToLongBits(1.3023789255603225E307) ^ 0x7FB28BE6DE65239FL);
            } else if (moveStrafe <= Double.longBitsToDouble(Double.doubleToLongBits(-12.510358550424234) ^ 0x7FD9054DB7469CB3L)) {
                rotationYaw += moveForward > Double.longBitsToDouble(Double.doubleToLongBits(4.111085142701572E307) ^ 0x7FCD459B191ECD37L) ? Float.intBitsToFloat(Float.floatToIntBits(0.02753775f) ^ 0x7ED596D9) : Float.intBitsToFloat(Float.floatToIntBits(-0.039721966f) ^ 0x7F16B380);
                moveStrafe = Double.longBitsToDouble(Double.doubleToLongBits(1.5901144238976076E308) ^ 0x7FEC4E12ED2557B6L);
            }
            if (moveForward > Double.longBitsToDouble(Double.doubleToLongBits(6.060807454000401E307) ^ 0x7FD593C2C61DD8BFL)) {
                moveForward = Double.longBitsToDouble(Double.doubleToLongBits(12.35378859094669) ^ 0x7FD8B523C737A0BFL);
            } else if (moveForward < Double.longBitsToDouble(Double.doubleToLongBits(2.4445500367792817E307) ^ 0x7FC167E0CE74B69FL)) {
                moveForward = Double.longBitsToDouble(Double.doubleToLongBits(-96.32603718313065) ^ 0x7FA814DDCB0FB4DFL);
            }
        }
        this.manageY((EventPlayerMove)eventPlayerMove);
        double toRadiansX = Math.cos(Math.toRadians(rotationYaw + Float.intBitsToFloat(Float.floatToIntBits(0.063318506f) ^ 0x7F35AD22)));
        double toRadiansZ = Math.sin(Math.toRadians(rotationYaw + Float.intBitsToFloat(Float.floatToIntBits(0.07827416f) ^ 0x7F144E34)));
        eventPlayerMove.setX(moveForward * this.speed * toRadiansX + moveStrafe * this.speed * toRadiansZ);
        eventPlayerMove.setZ(moveForward * this.speed * toRadiansZ - moveStrafe * this.speed * toRadiansX);
        eventPlayerMove.setCancelled(true);
    }

    @Override
    public String getHudInfo() {
        String t = "";
        t = " [" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        return t;
    }

    @Override
    public void onEnable() {
        this.timerSpeed = Float.intBitsToFloat(Float.floatToIntBits(13.131528f) ^ 0x7ED21ABD);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        Timer.tickLength = Float.intBitsToFloat(Float.floatToIntBits(0.023921477f) ^ 0x7E8BF6F9);
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    public static enum modes {
        Strafe;
    }
}

