/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;

public class ModuleReach
extends Module {
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.Add);
    public static ValueNumber addAmount = new ValueNumber("AddAmount", "AddAmount", "", Double.longBitsToDouble(Double.doubleToLongBits(0.9968719054011582) ^ 0x7FE7E65FE8FFF949L), Double.longBitsToDouble(Double.doubleToLongBits(1.351193651572246E308) ^ 0x7FE80D52D8803229L), Double.longBitsToDouble(Double.doubleToLongBits(0.18694801918314294) ^ 0x7FCFEDE9A638C737L));
    public static ValueNumber changeAmount = new ValueNumber("ChangeAmount", "ChangeAmount", "", Double.longBitsToDouble(Double.doubleToLongBits(1.4297128589745522) ^ 0x7FE2E01A973F7638L), Double.longBitsToDouble(Double.doubleToLongBits(4.901261330433575) ^ 0x7FE39AE4400D7255L), Double.longBitsToDouble(Double.doubleToLongBits(0.24406480404528488) ^ 0x7FEF3D83F7BD54B1L));

    public ModuleReach() {
        super("Reach", "Reach", "Increases your reach.", ModuleCategory.PLAYER);
    }

    public static enum modes {
        Add,
        Change;

    }
}

