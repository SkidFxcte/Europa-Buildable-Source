/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueNumber;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ModuleHotbarFill
extends Module {
    public int delayStep = 0;
    public static ValueNumber tickDelay = new ValueNumber("TickDelay", "AutoReplenishtickDelay", "", Double.longBitsToDouble(Double.doubleToLongBits(0.17366014393764148) ^ 0x7FD63A7EDF6A5867L), Double.longBitsToDouble(Double.doubleToLongBits(1.2182871942179136E308) ^ 0x7FE5AFACE4E17072L), Double.longBitsToDouble(Double.doubleToLongBits(0.44737251486213175) ^ 0x7FE8A1C0541D9389L));
    public static ValueNumber threshold = new ValueNumber("Threshold", "Threshold", "", Double.longBitsToDouble(Double.doubleToLongBits(1.2416387224105203) ^ 0x7FE3DDC090A334DEL), Double.longBitsToDouble(Double.doubleToLongBits(1.4830463687647025E308) ^ 0x7FEA662B86911D10L), Double.longBitsToDouble(Double.doubleToLongBits(0.29776523344968353) ^ 0x7FE70E95E8E3572EL));

    public ModuleHotbarFill() {
        super("HotbarFill", "Hotbar Fill", "Replenishes hotbar slots.", ModuleCategory.PLAYER);
    }

    public static Map<Integer, ItemStack> getInventory() {
        return ModuleHotbarFill.getInventorySlots(9, 35);
    }

    public static Map<Integer, ItemStack> getHotbar() {
        return ModuleHotbarFill.getInventorySlots(36, 44);
    }

    /*
     * WARNING - void declaration
     */
    public static Map<Integer, ItemStack> getInventorySlots(int n, int n2) {
        int current;
        HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (++n <= n2) {
            fullInventorySlots.put(n, (ItemStack)ModuleHotbarFill.mc.player.inventoryContainer.getInventory().get(n));
        }
        return fullInventorySlots;
    }

    @Override
    public void onUpdate() {
        if (ModuleHotbarFill.mc.player == null) {
            return;
        }
        if (ModuleHotbarFill.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if ((double)this.delayStep < tickDelay.getValue().doubleValue()) {
            ++this.delayStep;
            return;
        }
        this.delayStep = 0;
        Pair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        int inventorySlot = slots.getKey();
        int hotbarSlot = slots.getValue();
        ModuleHotbarFill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ModuleHotbarFill.mc.player);
        ModuleHotbarFill.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)ModuleHotbarFill.mc.player);
        ModuleHotbarFill.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ModuleHotbarFill.mc.player);
    }

    public Pair<Integer, Integer> findReplenishableHotbarSlot() {
        Pair<Integer, Integer> returnPair = null;
        for (Map.Entry<Integer, ItemStack> hotbarSlot : ModuleHotbarFill.getHotbar().entrySet()) {
            int inventorySlot;
            ItemStack stack = hotbarSlot.getValue();
            if (stack.isEmpty() || stack.getItem() == Items.AIR || !stack.isStackable() || stack.getCount() >= stack.getMaxStackSize() || (double)stack.getCount() > threshold.getValue().doubleValue() || (inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) continue;
            returnPair = new Pair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
        }
        return returnPair;
    }

    /*
     * WARNING - void declaration
     */
    public int findCompatibleInventorySlot(ItemStack itemStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (Map.Entry<Integer, ItemStack> entry : ModuleHotbarFill.getInventory().entrySet()) {
            int currentStackSize;
            ItemStack inventoryStack = entry.getValue();
            if (inventoryStack.isEmpty() || inventoryStack.getItem() == Items.AIR || !this.isCompatibleStacks((ItemStack)itemStack, inventoryStack) || smallestStackSize <= (currentStackSize = ((ItemStack)ModuleHotbarFill.mc.player.inventoryContainer.getInventory().get(entry.getKey().intValue())).getCount())) continue;
            smallestStackSize = currentStackSize;
            inventorySlot = entry.getKey();
        }
        return inventorySlot;
    }

    /*
     * WARNING - void declaration
     */
    public boolean isCompatibleStacks(ItemStack itemStack, ItemStack itemStack2) {
        if (!itemStack.getItem().equals(itemStack2.getItem())) {
            return false;
        }
        if (itemStack.getItem() instanceof ItemBlock) {
            if (itemStack2.getItem() instanceof ItemBlock) {
                Block block1 = ((ItemBlock)itemStack.getItem()).getBlock();
                Block block = ((ItemBlock)itemStack2.getItem()).getBlock();
            }
        }
        if (!itemStack.getDisplayName().equals(itemStack2.getDisplayName())) {
            return false;
        }
        return itemStack.getItemDamage() == itemStack2.getItemDamage();
    }

    public static class Pair<T, S> {
        public T key;
        public S value;

        public Pair(T key, S value) {
            this.key = key;
            this.value = value;
        }

        public T getKey() {
            return this.key;
        }

        public S getValue() {
            return this.value;
        }

        /*
         * WARNING - void declaration
         */
        public void setKey(T t) {
            this.key = t;
        }

        /*
         * WARNING - void declaration
         */
        public void setValue(S s) {
            this.value = s;
        }
    }
}

