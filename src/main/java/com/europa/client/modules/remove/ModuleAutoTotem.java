/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.InventoryUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import org.lwjgl.input.Mouse;

public class ModuleAutoTotem
extends Module {
    public static ValueEnum offhandMode = new ValueEnum("Mode", "Mode", "", modes.Totem);
    public static ValueNumber health = new ValueNumber("Health", "Health", "", 15, 1, 36);
    public static ValueNumber fallingDistance = new ValueNumber("Falling", "Falling", "", 15, 1, 100);
    public static ValueBoolean gapWhenSword = new ValueBoolean("GapSword", "GapSword", "", false);
    public static ValueNumber gapSwordHp = new ValueNumber("GapSwordHP", "GapSwordHP", "", 10, 1, 36);
    public static ValueBoolean oldfagMode = new ValueBoolean("OldfagMode", "OldfagMode", "", false);
    public int totemCount;

    public ModuleAutoTotem() {
        super("AutoTotem", "Auto Totem", "Better offhand", ModuleCategory.COMBAT);
    }

    @Override
    public void onMotionUpdate() {
        block13: {
            float playerHealth;
            block15: {
                boolean gapSword;
                block14: {
                    boolean doingOlFag;
                    block12: {
                        block11: {
                            this.totemCount = ModuleAutoTotem.mc.player.inventory.mainInventory.stream().filter(ModuleAutoTotem::lambda$onMotionUpdate$0).mapToInt(ItemStack::getCount).sum();
                            if (ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                                this.totemCount += ModuleAutoTotem.mc.player.getHeldItemOffhand().getCount();
                            }
                            doingOlFag = false;
                            gapSword = false;
                            if (ModuleAutoTotem.mc.currentScreen instanceof GuiContainer) {
                                return;
                            }
                            playerHealth = ModuleAutoTotem.mc.player.getHealth() + ModuleAutoTotem.mc.player.getAbsorptionAmount();
                            if (ModuleAutoTotem.mc.player == null || ModuleAutoTotem.mc.world == null) {
                                return;
                            }
                            if (oldfagMode.getValue()) {
                                for (TileEntity tile : ModuleAutoTotem.mc.world.loadedTileEntityList) {
                                    if (!(tile instanceof TileEntityBed)) continue;
                                    doingOlFag = true;
                                }
                            }
                            if (gapWhenSword.getValue() && ModuleAutoTotem.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && (float)gapSwordHp.getValue().intValue() < playerHealth) {
                                if (Mouse.isButtonDown((int)1)) {
                                    gapSword = true;
                                }
                            }
                            if ((float)health.getValue().intValue() >= playerHealth) break block11;
                            if (!(ModuleAutoTotem.mc.player.fallDistance >= (float)fallingDistance.getValue().intValue())) break block12;
                            if (ModuleAutoTotem.mc.player.isElytraFlying()) break block12;
                        }
                        if (ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) break block13;
                        InventoryUtils.offhandItem(Items.TOTEM_OF_UNDYING);
                        break block13;
                    }
                    if (!doingOlFag) break block14;
                    if (ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) break block13;
                    InventoryUtils.offhandItem(Items.TOTEM_OF_UNDYING);
                    break block13;
                }
                if (!gapSword) break block15;
                if (ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) break block13;
                InventoryUtils.offhandItem(Items.GOLDEN_APPLE);
                break block13;
            }
            if (!((float)health.getValue().intValue() < playerHealth)) break block13;
            if (offhandMode.getValue().equals((Object)modes.Crystal) && ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                InventoryUtils.offhandItem(Items.END_CRYSTAL);
            }
            if (offhandMode.getValue().equals((Object)modes.Gapple)) {
                if (ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE) {
                    InventoryUtils.offhandItem(Items.GOLDEN_APPLE);
                }
            }
            if (offhandMode.getValue().equals((Object)modes.Totem) && ModuleAutoTotem.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                InventoryUtils.offhandItem(Items.TOTEM_OF_UNDYING);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void switchItem(int n) {
        ModuleAutoTotem.mc.playerController.windowClick(0, (int)n, 0, ClickType.PICKUP, (EntityPlayer)ModuleAutoTotem.mc.player);
        ModuleAutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)ModuleAutoTotem.mc.player);
        ModuleAutoTotem.mc.playerController.windowClick(0, (int)n, 0, ClickType.PICKUP, (EntityPlayer)ModuleAutoTotem.mc.player);
    }

    @Override
    public String getHudInfo() {
        String t = "";
        t = " [" + ChatFormatting.WHITE + this.totemCount + ChatFormatting.GRAY + "]";
        return t;
    }

    public static boolean lambda$onMotionUpdate$0(ItemStack itemStack) {
        return itemStack.getItem() == Items.TOTEM_OF_UNDYING;
    }

    public static enum modes {
        Totem,
        Crystal,
        Gapple;

    }
}

