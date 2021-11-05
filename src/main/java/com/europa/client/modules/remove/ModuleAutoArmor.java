/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ModuleAutoArmor
extends Module {
    public ModuleAutoArmor() {
        super("AutoArmor", "Auto Armor", "Automatically puts armor in the armor slots when they are empty.", ModuleCategory.COMBAT);
    }

    @Override
    public void onMotionUpdate() {
        int armorType;
        if (ModuleAutoArmor.mc.player == null) {
            return;
        }
        if (ModuleAutoArmor.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor = ModuleAutoArmor.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor != null && oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            ItemStack stack = ModuleAutoArmor.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() > 1) continue;
            if (stack == null) continue;
            if (!(stack.getItem() instanceof ItemArmor)) continue;
            ItemArmor armor = (ItemArmor)stack.getItem();
            int armorType2 = armor.armorType.ordinal() - 2;
            if (armorType2 == 2 && ModuleAutoArmor.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA)) continue;
            int armorValue = armor.damageReduceAmount;
            if (armorValue <= bestArmorValues[armorType2]) continue;
            bestArmorSlots[armorType2] = slot;
            bestArmorValues[armorType2] = armorValue;
        }
        for (armorType = 0; armorType < 4; ++armorType) {
            int slot2 = bestArmorSlots[armorType];
            if (slot2 == -1) continue;
            ItemStack oldArmor2 = ModuleAutoArmor.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor2 != null && oldArmor2 == ItemStack.EMPTY && ModuleAutoArmor.mc.player.inventory.getFirstEmptyStack() == -1) continue;
            if (slot2 < 9) {
                slot2 += 36;
            }
            ModuleAutoArmor.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)ModuleAutoArmor.mc.player);
            ModuleAutoArmor.mc.playerController.windowClick(0, slot2, 0, ClickType.QUICK_MOVE, (EntityPlayer)ModuleAutoArmor.mc.player);
            break;
        }
    }
}

