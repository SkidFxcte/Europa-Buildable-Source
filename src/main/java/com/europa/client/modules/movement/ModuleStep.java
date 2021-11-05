/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.movement;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleStep
extends Module {
    public float oldHeight = Float.intBitsToFloat(Float.floatToIntBits(-4.724937f) ^ 0x7F1732AF);
    public float timerSpeed;
    public static ValueNumber height = new ValueNumber("Height", "Height", "", Double.longBitsToDouble(Double.doubleToLongBits(0.34686506251484367) ^ 0x7FD6330984E81477L), Double.longBitsToDouble(Double.doubleToLongBits(1.2866951786535576E308) ^ 0x7FE6E7683F78156AL), Double.longBitsToDouble(Double.doubleToLongBits(0.34943626743804573) ^ 0x7FD25D29EF2BB19FL));
    public static ValueBoolean timer = new ValueBoolean("UseTimer", "UseTimer", "", false);

    public ModuleStep() {
        super("Step", "Step", "Increases your vanilla step height.", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (ModuleStep.mc.player != null) {
            if (Europa.getModuleManager().isModuleEnabled("Speed")) {
                ModuleStep.mc.player.stepHeight = this.oldHeight;
                return;
            }
            ModuleStep.mc.player.stepHeight = (float)height.getValue().doubleValue();
        }
    }

    @Override
    public void onMotionUpdate() {
        if (ModuleStep.mc.player != null && timer.getValue()) {
            this.timerSpeed = (double)ModuleStep.mc.player.moveForward != Double.longBitsToDouble(Double.doubleToLongBits(1.8611519510179413E307) ^ 0x7FBA80F1678630AFL) || (double)ModuleStep.mc.player.moveStrafing != Double.longBitsToDouble(Double.doubleToLongBits(1.1401433617423325E307) ^ 0x7FB03C7651C3ADCFL) ? Math.min(this.timerSpeed + Float.intBitsToFloat(Float.floatToIntBits(1260.3894f) ^ 0x7F5917D0), Float.intBitsToFloat(Float.floatToIntBits(22.442043f) ^ 0x7E3F4583)) : Float.intBitsToFloat(Float.floatToIntBits(11.449611f) ^ 0x7EB7319B);
            ModuleStep.mc.timer.tickLength = Float.intBitsToFloat(Float.floatToIntBits(0.017081684f) ^ 0x7EC3EEE3) / this.timerSpeed;
        }
    }

    @Override
    public void onEnable() {
        this.timerSpeed = Float.intBitsToFloat(Float.floatToIntBits(6.7851777f) ^ 0x7F59202D);
        if (ModuleStep.mc.player != null) {
            this.oldHeight = ModuleStep.mc.player.stepHeight;
        }
    }

    @Override
    public void onDisable() {
        ModuleStep.mc.player.stepHeight = this.oldHeight;
        this.oldHeight = Float.intBitsToFloat(Float.floatToIntBits(-4.8051877f) ^ 0x7F19C419);
        ModuleStep.mc.timer.tickLength = Float.intBitsToFloat(Float.floatToIntBits(0.061392717f) ^ 0x7F3376EE);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        if (timer.getValue() && receive.getPacket() instanceof SPacketPlayerPosLook) {
            this.timerSpeed = Float.intBitsToFloat(Float.floatToIntBits(5.8221817f) ^ 0x7F3A4F50);
        }
    }

    @Override
    public void onLogout() {
        this.disable();
    }

    @Override
    public void onDeath() {
        this.disable();
    }
}

