/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.client.EventClient;
import com.europa.api.manager.event.impl.render.EventBlockGetRenderLayer;
import com.europa.api.manager.event.impl.render.EventRenderPutColorMultiplier;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import java.util.ArrayList;

import com.europa.client.mixins.impl.blocks.MixinBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ModuleWallhack
extends Module {
    public static ArrayList<Block> blocks;
    public static ValueEnum Mode;
    public static ValueNumber Opacity;
    public static ValueBoolean HandBlock;
    public ValueBoolean SoftReload = new ValueBoolean("SoftReload", "SoftReload", "", false);
    public boolean doReload = false;
    public static Block _block;

    public ModuleWallhack() {
        super("WallHack", "Wall Hack", "Makes blocks transparent so that you can find target blocks easily.", ModuleCategory.RENDER);
        blocks = new ArrayList();
        blocks.add(Blocks.GOLD_ORE);
        blocks.add(Blocks.IRON_ORE);
        blocks.add(Blocks.COAL_ORE);
        blocks.add(Blocks.LAPIS_ORE);
        blocks.add(Blocks.DIAMOND_ORE);
        blocks.add(Blocks.REDSTONE_ORE);
        blocks.add(Blocks.LIT_REDSTONE_ORE);
        blocks.add(Blocks.TNT);
        blocks.add(Blocks.EMERALD_ORE);
        blocks.add(Blocks.FURNACE);
        blocks.add(Blocks.LIT_FURNACE);
        blocks.add(Blocks.DIAMOND_BLOCK);
        blocks.add(Blocks.IRON_BLOCK);
        blocks.add(Blocks.GOLD_BLOCK);
        blocks.add(Blocks.QUARTZ_ORE);
        blocks.add((Block)Blocks.BEACON);
        blocks.add(Blocks.MOB_SPAWNER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onSetting(EventClient eventClient) {
        void event;
        block5: {
            block4: {
                if (ModuleWallhack.mc.player == null) break block4;
                if (ModuleWallhack.mc.world != null) break block5;
            }
            return;
        }
        if (event.getSetting() == Mode || event.getSetting() == Opacity || event.getSetting() == HandBlock || event.getSetting() == this.SoftReload) {
            this.doReload = true;
        }
    }

    @Override
    public void onUpdate() {
        if (this.doReload) {
            this.reloadWorld();
            this.doReload = false;
        }
    }

    public String getMetaData() {
        return String.valueOf(Mode.getValue());
    }

    @Override
    public void onEnable() {
        ItemStack stack;
        super.onEnable();
        ModuleWallhack.mc.renderChunksMany = false;
        this.reloadWorld();
        ForgeModContainer.forgeLightPipelineEnabled = false;
        if (HandBlock.getValue() && (stack = ModuleWallhack.mc.player.getHeldItemMainhand()).getItem() instanceof ItemBlock) {
            ItemBlock item = (ItemBlock)stack.getItem();
            _block = item.getBlock();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ModuleWallhack.mc.renderChunksMany = false;
        this.reloadWorld();
        ForgeModContainer.forgeLightPipelineEnabled = true;
    }

    public void reloadWorld() {
        if (ModuleWallhack.mc.world == null || ModuleWallhack.mc.renderGlobal == null) {
            return;
        }
        if (this.SoftReload.getValue()) {
            mc.addScheduledTask(ModuleWallhack::lambda$reloadWorld$0);
        } else {
            ModuleWallhack.mc.renderGlobal.loadRenderers();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean containsBlock(Block block) {
        Block block2;
        if (HandBlock.getValue() && _block != null) {
            if (block2 != _block) return false;
            return true;
        }
        if (Mode.getValue().equals("Normal") && block2 != null) {
            return blocks.contains(block2);
        }
        if (block2 == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE) return true;
        if (block2 == Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE) return true;
        if (block2 == Blocks.STONE_PRESSURE_PLATE) return true;
        if (block2 == Blocks.WOODEN_PRESSURE_PLATE) return true;
        if (block2 == Blocks.STONE_BUTTON) return true;
        if (block2 == Blocks.WOODEN_BUTTON) return true;
        if (block2 == Blocks.LEVER) return true;
        if (block2 == Blocks.COMMAND_BLOCK) return true;
        if (block2 == Blocks.CHAIN_COMMAND_BLOCK) return true;
        if (block2 == Blocks.REPEATING_COMMAND_BLOCK) return true;
        if (block2 == Blocks.DAYLIGHT_DETECTOR) return true;
        if (block2 == Blocks.DAYLIGHT_DETECTOR_INVERTED) return true;
        if (block2 == Blocks.DISPENSER) return true;
        if (block2 == Blocks.DROPPER) return true;
        if (block2 == Blocks.HOPPER) return true;
        if (block2 == Blocks.OBSERVER) return true;
        if (block2 == Blocks.TRAPDOOR) return true;
        if (block2 == Blocks.IRON_TRAPDOOR) return true;
        if (block2 == Blocks.REDSTONE_BLOCK) return true;
        if (block2 == Blocks.REDSTONE_LAMP) return true;
        if (block2 == Blocks.REDSTONE_TORCH) return true;
        if (block2 == Blocks.UNLIT_REDSTONE_TORCH) return true;
        if (block2 == Blocks.REDSTONE_WIRE) return true;
        if (block2 == Blocks.POWERED_REPEATER) return true;
        if (block2 == Blocks.UNPOWERED_REPEATER) return true;
        if (block2 == Blocks.POWERED_COMPARATOR) return true;
        if (block2 == Blocks.UNPOWERED_COMPARATOR) return true;
        if (block2 == Blocks.LIT_REDSTONE_LAMP) return true;
        if (block2 == Blocks.REDSTONE_ORE) return true;
        if (block2 == Blocks.LIT_REDSTONE_ORE) return true;
        if (block2 == Blocks.ACACIA_DOOR) return true;
        if (block2 == Blocks.DARK_OAK_DOOR) return true;
        if (block2 == Blocks.BIRCH_DOOR) return true;
        if (block2 == Blocks.JUNGLE_DOOR) return true;
        if (block2 == Blocks.OAK_DOOR) return true;
        if (block2 == Blocks.SPRUCE_DOOR) return true;
        if (block2 == Blocks.DARK_OAK_DOOR) return true;
        if (block2 == Blocks.IRON_DOOR) return true;
        if (block2 == Blocks.OAK_FENCE) return true;
        if (block2 == Blocks.SPRUCE_FENCE) return true;
        if (block2 == Blocks.BIRCH_FENCE) return true;
        if (block2 == Blocks.JUNGLE_FENCE) return true;
        if (block2 == Blocks.DARK_OAK_FENCE) return true;
        if (block2 == Blocks.ACACIA_FENCE) return true;
        if (block2 == Blocks.OAK_FENCE_GATE) return true;
        if (block2 == Blocks.SPRUCE_FENCE_GATE) return true;
        if (block2 == Blocks.BIRCH_FENCE_GATE) return true;
        if (block2 == Blocks.JUNGLE_FENCE_GATE) return true;
        if (block2 == Blocks.DARK_OAK_FENCE_GATE) return true;
        if (block2 == Blocks.ACACIA_FENCE_GATE) return true;
        if (block2 == Blocks.JUKEBOX) return true;
        if (block2 == Blocks.NOTEBLOCK) return true;
        if (block2 == Blocks.PISTON) return true;
        if (block2 == Blocks.PISTON_EXTENSION) return true;
        if (block2 == Blocks.PISTON_HEAD) return true;
        if (block2 == Blocks.STICKY_PISTON) return true;
        if (block2 == Blocks.TNT) return true;
        if (block2 == Blocks.SLIME_BLOCK) return true;
        if (block2 == Blocks.TRIPWIRE) return true;
        if (block2 == Blocks.TRIPWIRE_HOOK) return true;
        if (block2 == Blocks.RAIL) return true;
        if (block2 == Blocks.ACTIVATOR_RAIL) return true;
        if (block2 == Blocks.DETECTOR_RAIL) return true;
        if (block2 != Blocks.GOLDEN_RAIL) return false;
        return true;
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onGetBlockRenderLayer(EventBlockGetRenderLayer eventBlockGetRenderLayer) {
        block0: {
            void event;
            if (blocks.contains(event.getBlock()) || ModuleWallhack.containsBlock(event.getBlock())) break block0;
            event.setCancelled(true);
            event.setLayer(BlockRenderLayer.TRANSLUCENT);
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void renderPutColor(EventRenderPutColorMultiplier eventRenderPutColorMultiplier) {
        void event;
        event.setCancelled(true);
        event.setOpacity(Opacity.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.012516583f) ^ 0x7F32125B));
    }

    /*
     * WARNING - void declaration
     */
    public static void processShouldSideBeRendered(MixinBlock block, IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        block0: {
            void callback;
            Block block2;
            if (!blocks.contains(block2)) break block0;
            callback.setReturnValue(true);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void processGetLightValue(MixinBlock block, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        block0: {
            void callback;
            Block block2;
            if (!blocks.contains(block2)) break block0;
            callback.setReturnValue(1);
        }
    }

    public static void lambda$reloadWorld$0() {
        int x = (int)ModuleWallhack.mc.player.posX;
        int y = (int)ModuleWallhack.mc.player.posY;
        int z = (int)ModuleWallhack.mc.player.posZ;
        int distance = ModuleWallhack.mc.gameSettings.renderDistanceChunks * 16;
        ModuleWallhack.mc.renderGlobal.markBlockRangeForRenderUpdate(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);
    }

    static {
        Mode = new ValueEnum("Mode", "Mode", "", modes.Normal);
        Opacity = new ValueNumber("Opacity", "Opacity", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.11912391f) ^ 0x7EF3F73D)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(3.0370866E38f) ^ 0x7F647C29)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.07696035f) ^ 0x7EE29D63)));
        HandBlock = new ValueBoolean("HandBlock", "HandBlock", "", false);
    }

    public static enum modes {
        Normal,
        Circuits;

    }
}

