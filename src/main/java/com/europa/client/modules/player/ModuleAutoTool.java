/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.player;

import com.europa.api.manager.event.impl.world.EventBlock;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleAutoTool
extends Module {
    public ModuleAutoTool() {
        super("AutoTool", "Auto Tool", "Automatically switches to a specific tool perfect for mining the block you're trying to mine.", ModuleCategory.PLAYER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onDamage(EventBlock eventBlock) {
        if (ModuleAutoTool.mc.player == null || ModuleAutoTool.mc.world == null) {
            return;
        }
        if (ModuleAutoTool.canBlockBeBroken(eventBlock.pos)) {
            IBlockState block = ModuleAutoTool.mc.world.getBlockState(eventBlock.pos);
            float hard = Float.intBitsToFloat(Float.floatToIntBits(18.395994f) ^ 0x7E132AFF);
            int bestTool = 1;
            for (int i = 0; i < 9; ++i) {
                ItemStack current = (ItemStack)ModuleAutoTool.mc.player.inventory.mainInventory.get(i);
                if (current == null) continue;
                if (!(current.getDestroySpeed(block) > hard)) continue;
                hard = current.getDestroySpeed(block);
                bestTool = i;
            }
            if (bestTool != -1) {
                ModuleAutoTool.mc.player.inventory.currentItem = bestTool;
            }
        }
    }

    public static boolean canBlockBeBroken(BlockPos blockPos) {
        IBlockState blockState = ModuleAutoTool.mc.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)ModuleAutoTool.mc.world, blockPos) != Float.intBitsToFloat(Float.floatToIntBits(-12.078275f) ^ 0x7EC1409D);
    }
}

