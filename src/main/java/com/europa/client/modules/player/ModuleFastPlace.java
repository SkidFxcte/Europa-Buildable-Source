/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class ModuleFastPlace
extends Module {
    public static ValueBoolean exp = new ValueBoolean("EXP", "exp", "", false);
    public static ValueBoolean crystals = new ValueBoolean("Crystals", "crystals", "", false);
    public static ValueBoolean blocks = new ValueBoolean("Blocks", "blocks", "", false);
    public static ValueBoolean echest = new ValueBoolean("EChest", "echest", "", false);
    public Object BlockEnderChest;

    public ModuleFastPlace() {
        super("FastPlace", "Fast Place", "Makes usage of certain items faster.", ModuleCategory.PLAYER);
    }

    @Override
    public void onUpdate() {
        block8: {
            block7: {
                block6: {
                    Item main = ModuleFastPlace.mc.player.getHeldItemMainhand().getItem();
                    Item off = ModuleFastPlace.mc.player.getHeldItemOffhand().getItem();
                    boolean mainExp = main instanceof ItemExpBottle;
                    boolean offExp = off instanceof ItemExpBottle;
                    boolean mainCry = main instanceof ItemEndCrystal;
                    boolean offCry = off instanceof ItemEndCrystal;
                    if (mainExp || offExp && exp.getValue()) {
                        ModuleFastPlace.mc.rightClickDelayTimer = 0;
                    }
                    if (mainCry || offCry && crystals.getValue()) {
                        ModuleFastPlace.mc.rightClickDelayTimer = 0;
                    }
                    if (blocks.getValue()) {
                        ModuleFastPlace.mc.rightClickDelayTimer = 0;
                    }
                    if (!echest.getValue()) break block6;
                    if (ModuleFastPlace.mc.player.getHeldItemMainhand().getItem() == this.BlockEnderChest) break block7;
                }
                if (ModuleFastPlace.mc.player.getHeldItemOffhand().getItem() != this.BlockEnderChest) break block8;
            }
            ModuleFastPlace.mc.rightClickDelayTimer = 0;
        }
    }
}

