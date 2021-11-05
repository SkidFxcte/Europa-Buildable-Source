//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import java.util.HashMap;
import net.minecraft.client.gui.inventory.GuiCrafting;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.init.Blocks;
import com.europa.api.utilities.IMinecraft;

public class InventoryUtils implements IMinecraft
{
    public static int getCombatBlock(final String input) {
        final int obsidianSlot = findBlock(Blocks.OBSIDIAN, 0, 9);
        final int chestSlot = findBlock(Blocks.ENDER_CHEST, 0, 9);
        if (obsidianSlot == -1 && chestSlot == -1) {
            return -1;
        }
        if (obsidianSlot != -1 && chestSlot == -1) {
            return obsidianSlot;
        }
        if (obsidianSlot == -1) {
            return chestSlot;
        }
        if (input.equals("Obsidian")) {
            return obsidianSlot;
        }
        return chestSlot;
    }

    public static void switchSlot(final int slot, final boolean silent) {
        if (silent) {
            InventoryUtils.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            InventoryUtils.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            InventoryUtils.mc.player.inventory.currentItem = slot;
        }
    }

    public static int findItem(final Item item, final int minimum, final int maximum) {
        for (int i = minimum; i <= maximum; ++i) {
            final ItemStack stack = InventoryUtils.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static int findBlock(final Block block, final int minimum, final int maximum) {
        for (int i = minimum; i <= maximum; ++i) {
            final ItemStack stack = InventoryUtils.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock item = (ItemBlock)stack.getItem();
                if (item.getBlock() == block) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void switchToSlot(final Class<? extends Item> clazz) {
        if (InventoryUtils.mc.player.getHeldItemMainhand().getItem().getClass().isAssignableFrom(clazz)) {
            return;
        }
        final int slot = getHotbarItemSlot(clazz);
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.player.inventory.currentItem = slot;
    }

    public static void switchToSlot(final Item item) {
        if (InventoryUtils.mc.player.getHeldItemMainhand().getItem() == item) {
            return;
        }
        final int slot = getHotbarItemSlot(item.getClass());
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.player.inventory.currentItem = slot;
    }

    public static void switchToPacketSlot(final Item item) {
        if (InventoryUtils.mc.player.getHeldItemMainhand().getItem() == item) {
            return;
        }
        final int slot = getHotbarItemSlot(item.getClass());
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
    }

    public static void switchToPacketSlot(final Class<? extends Item> clazz) {
        if (InventoryUtils.mc.player.getHeldItemMainhand().getItem().getClass().isAssignableFrom(clazz)) {
            return;
        }
        final int slot = getHotbarItemSlot(clazz);
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
    }

    public static int findItemInventorySlot(final Item item, final boolean offHand) {
        final AtomicInteger slot = new AtomicInteger();
        slot.set(-1);
        for (final Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
            if (entry.getValue().getItem() == item) {
                if (entry.getKey() == 45) {
                    if (!offHand) {
                        continue;
                    }
                }
                slot.set(entry.getKey());
                return slot.get();
            }
        }
        return slot.get();
    }

    public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
        if (InventoryUtils.mc.currentScreen instanceof GuiCrafting) {
            return getOtherSlot(10, 45);
        }
        return getInventorySlots(9, 44);
    }

    public static Map<Integer, ItemStack> getInventorySlots(final int currentI, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        for (int current = currentI; current <= last; ++current) {
            fullInventorySlots.put(current, (ItemStack)InventoryUtils.mc.player.inventoryContainer.getInventory().get(current));
        }
        return fullInventorySlots;
    }

    public static Map<Integer, ItemStack> getOtherSlot(final int currentI, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        for (int current = currentI; current <= last; ++current) {
            fullInventorySlots.put(current, (ItemStack)InventoryUtils.mc.player.openContainer.getInventory().get(current));
        }
        return fullInventorySlots;
    }

    public static void offhandItem(final Item item) {
        final int slot = findItemInventorySlot(item, false);
        if (slot != -1) {
            InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtils.mc.player);
            InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtils.mc.player);
            InventoryUtils.mc.playerController.windowClick(InventoryUtils.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtils.mc.player);
            InventoryUtils.mc.playerController.updateController();
        }
    }

    public static int getHotbarItemSlot(final Class<? extends Item> item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtils.mc.player.inventory.getStackInSlot(i).getItem().getClass().isAssignableFrom(item)) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int getHotbarBlockSlot(final Block block) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item item = InventoryUtils.mc.player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().equals(block)) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int getHotbarItemSlot(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item selection = InventoryUtils.mc.player.inventory.getStackInSlot(i).getItem();
            if (selection.equals(item)) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int getInventoryItemSlot(final Item item) {
        for (int i = 0; i < 36; ++i) {
            final Item cacheItem = InventoryUtils.mc.player.inventory.getStackInSlot(i).getItem();
            if (cacheItem == item) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }

    public boolean isHotbar(final int slot) {
        return slot == 0 || slot == 1 || slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8;
    }

    public enum Items
    {
        Obsidian,
        Chest;

        public static Items[] $VALUES;

        static {
            Items.$VALUES = new Items[] { Items.Obsidian, Items.Chest };
        }
    }

    public enum SwitchModes
    {
        Normal,
        Silent,
        Strict;

        public static SwitchModes[] $VALUES;

        static {
            SwitchModes.$VALUES = new SwitchModes[] { SwitchModes.Normal, SwitchModes.Silent, SwitchModes.Strict };
        }
    }
}
