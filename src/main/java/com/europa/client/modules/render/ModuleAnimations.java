/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import net.minecraft.entity.player.EntityPlayer;

public class ModuleAnimations
extends Module {
    public static ValueBoolean playersDisableAnimations = new ValueBoolean("DisableAnimations", "DisableAnimations", "Disables player animations.", false);
    public static ValueBoolean changeMainhand = new ValueBoolean("ChangeMainhand", "ChangeMainhand", "Changes the hand progress of Mainhand.", true);
    public static ValueNumber mainhand = new ValueNumber("Mainhand", "Mainhand", "The hand progress for Mainhand.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(4.7509747f) ^ 0x7F1807FC)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.63819E38f) ^ 0x7EF67CC9)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(30.789412f) ^ 0x7E7650B7)));
    public static ValueBoolean changeOffhand = new ValueBoolean("ChangeOffhand", "ChangeOffhand", "Changes the hand progress of Offhand.", true);
    public static ValueNumber offhand = new ValueNumber("Offhand", "Offhand", "The hand progress for Offhand.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(15.8065405f) ^ 0x7EFCE797)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(3.3688825E38f) ^ 0x7F7D7251)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(7.3325067f) ^ 0x7F6AA3E5)));
    public static ValueBoolean changeSwing = new ValueBoolean("ChangeSwing", "ChangeSwing", "Changes the swing speed.", false);
    public static ValueNumber swingDelay = new ValueNumber("SwingDelay", "SwingDelay", "The delay for swinging.", 6, 1, 20);

    public ModuleAnimations() {
        super("Animations", "Animations", "Let's you change swing speed and other related things.", ModuleCategory.RENDER);
    }

    @Override
    public void onUpdate() {
        if (playersDisableAnimations.getValue()) {
            for (EntityPlayer player : ModuleAnimations.mc.world.playerEntities) {
                player.limbSwing = Float.intBitsToFloat(Float.floatToIntBits(1.8755627E38f) ^ 0x7F0D1A06);
                player.limbSwingAmount = Float.intBitsToFloat(Float.floatToIntBits(6.103741E37f) ^ 0x7E37AD83);
                player.prevLimbSwingAmount = Float.intBitsToFloat(Float.floatToIntBits(4.8253957E37f) ^ 0x7E11357F);
            }
        }
        if (changeMainhand.getValue() && ModuleAnimations.mc.entityRenderer.itemRenderer.equippedProgressMainHand != mainhand.getValue().floatValue()) {
            ModuleAnimations.mc.entityRenderer.itemRenderer.equippedProgressMainHand = mainhand.getValue().floatValue();
            ModuleAnimations.mc.entityRenderer.itemRenderer.itemStackMainHand = ModuleAnimations.mc.player.getHeldItemMainhand();
        }
        if (changeOffhand.getValue() && ModuleAnimations.mc.entityRenderer.itemRenderer.equippedProgressOffHand != offhand.getValue().floatValue()) {
            ModuleAnimations.mc.entityRenderer.itemRenderer.equippedProgressOffHand = offhand.getValue().floatValue();
            ModuleAnimations.mc.entityRenderer.itemRenderer.itemStackOffHand = ModuleAnimations.mc.player.getHeldItemOffhand();
        }
    }
}

