//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.modules.render;

import com.europa.client.mixins.impl.blocks.MixinBlock;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import com.europa.api.manager.event.impl.render.EventRenderPutColorMultiplier;
import net.minecraft.util.BlockRenderLayer;
import com.europa.api.manager.event.impl.render.EventBlockGetRenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.europa.api.manager.event.impl.client.EventClient;
import net.minecraft.init.Blocks;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueEnum;
import net.minecraft.block.Block;
import java.util.ArrayList;
import com.europa.api.manager.module.Module;

public class ModuleWallhack extends Module
{
    public static ArrayList<Block> blocks;
    public static ValueEnum Mode;
    public static ValueNumber Opacity;
    public static ValueBoolean HandBlock;
    public ValueBoolean SoftReload;
    public boolean doReload;
    public static Block _block;

    public ModuleWallhack() {
        super("WallHack", "Wall Hack", "Makes blocks transparent so that you can find target blocks easily.", ModuleCategory.RENDER);
        this.SoftReload = new ValueBoolean("SoftReload", "SoftReload", "", false);
        this.doReload = false;
        (ModuleWallhack.blocks = new ArrayList<Block>()).add(Blocks.GOLD_ORE);
        ModuleWallhack.blocks.add(Blocks.IRON_ORE);
        ModuleWallhack.blocks.add(Blocks.COAL_ORE);
        ModuleWallhack.blocks.add(Blocks.LAPIS_ORE);
        ModuleWallhack.blocks.add(Blocks.DIAMOND_ORE);
        ModuleWallhack.blocks.add(Blocks.REDSTONE_ORE);
        ModuleWallhack.blocks.add(Blocks.LIT_REDSTONE_ORE);
        ModuleWallhack.blocks.add(Blocks.TNT);
        ModuleWallhack.blocks.add(Blocks.EMERALD_ORE);
        ModuleWallhack.blocks.add(Blocks.FURNACE);
        ModuleWallhack.blocks.add(Blocks.LIT_FURNACE);
        ModuleWallhack.blocks.add(Blocks.DIAMOND_BLOCK);
        ModuleWallhack.blocks.add(Blocks.IRON_BLOCK);
        ModuleWallhack.blocks.add(Blocks.GOLD_BLOCK);
        ModuleWallhack.blocks.add(Blocks.QUARTZ_ORE);
        ModuleWallhack.blocks.add((Block)Blocks.BEACON);
        ModuleWallhack.blocks.add(Blocks.MOB_SPAWNER);
    }

    @SubscribeEvent
    public void onSetting(final EventClient event) {
        if (ModuleWallhack.mc.player != null) {
            if (ModuleWallhack.mc.world != null) {
                if (event.getSetting() == ModuleWallhack.Mode || event.getSetting() == ModuleWallhack.Opacity || event.getSetting() == ModuleWallhack.HandBlock || event.getSetting() == this.SoftReload) {
                    this.doReload = true;
                }
            }
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
        return String.valueOf(ModuleWallhack.Mode.getValue());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ModuleWallhack.mc.renderChunksMany = false;
        this.reloadWorld();
        ForgeModContainer.forgeLightPipelineEnabled = false;
        if (ModuleWallhack.HandBlock.getValue()) {
            final ItemStack stack = ModuleWallhack.mc.player.getHeldItemMainhand();
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock item = (ItemBlock)stack.getItem();
                ModuleWallhack._block = item.getBlock();
            }
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
            ModuleWallhack.mc.addScheduledTask(ModuleWallhack::lambda$reloadWorld$0);
        }
        else {
            ModuleWallhack.mc.renderGlobal.loadRenderers();
        }
    }

    public static boolean containsBlock(final Block block) {
        if (ModuleWallhack.HandBlock.getValue() && ModuleWallhack._block != null) {
            return block == ModuleWallhack._block;
        }
        if (ModuleWallhack.Mode.getValue().equals("Normal") && block != null) {
            return ModuleWallhack.blocks.contains(block);
        }
        if (block != Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE) {
            if (block != Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                if (block != Blocks.STONE_PRESSURE_PLATE) {
                    if (block != Blocks.WOODEN_PRESSURE_PLATE) {
                        if (block != Blocks.STONE_BUTTON) {
                            if (block != Blocks.WOODEN_BUTTON && block != Blocks.LEVER) {
                                if (block != Blocks.COMMAND_BLOCK) {
                                    if (block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.DAYLIGHT_DETECTOR && block != Blocks.DAYLIGHT_DETECTOR_INVERTED && block != Blocks.DISPENSER) {
                                        if (block != Blocks.DROPPER) {
                                            if (block != Blocks.HOPPER && block != Blocks.OBSERVER) {
                                                if (block != Blocks.TRAPDOOR) {
                                                    if (block != Blocks.IRON_TRAPDOOR && block != Blocks.REDSTONE_BLOCK && block != Blocks.REDSTONE_LAMP) {
                                                        if (block != Blocks.REDSTONE_TORCH) {
                                                            if (block != Blocks.UNLIT_REDSTONE_TORCH && block != Blocks.REDSTONE_WIRE) {
                                                                if (block != Blocks.POWERED_REPEATER) {
                                                                    if (block != Blocks.UNPOWERED_REPEATER && block != Blocks.POWERED_COMPARATOR && block != Blocks.UNPOWERED_COMPARATOR) {
                                                                        if (block != Blocks.LIT_REDSTONE_LAMP) {
                                                                            if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE) {
                                                                                if (block != Blocks.ACACIA_DOOR) {
                                                                                    if (block != Blocks.DARK_OAK_DOOR) {
                                                                                        if (block != Blocks.BIRCH_DOOR) {
                                                                                            if (block != Blocks.JUNGLE_DOOR) {
                                                                                                if (block != Blocks.OAK_DOOR && block != Blocks.SPRUCE_DOOR && block != Blocks.DARK_OAK_DOOR && block != Blocks.IRON_DOOR && block != Blocks.OAK_FENCE && block != Blocks.SPRUCE_FENCE) {
                                                                                                    if (block != Blocks.BIRCH_FENCE) {
                                                                                                        if (block != Blocks.JUNGLE_FENCE) {
                                                                                                            if (block != Blocks.DARK_OAK_FENCE) {
                                                                                                                if (block != Blocks.ACACIA_FENCE && block != Blocks.OAK_FENCE_GATE && block != Blocks.SPRUCE_FENCE_GATE && block != Blocks.BIRCH_FENCE_GATE && block != Blocks.JUNGLE_FENCE_GATE && block != Blocks.DARK_OAK_FENCE_GATE && block != Blocks.ACACIA_FENCE_GATE && block != Blocks.JUKEBOX && block != Blocks.NOTEBLOCK && block != Blocks.PISTON && block != Blocks.PISTON_EXTENSION && block != Blocks.PISTON_HEAD && block != Blocks.STICKY_PISTON && block != Blocks.TNT && block != Blocks.SLIME_BLOCK) {
                                                                                                                    if (block != Blocks.TRIPWIRE) {
                                                                                                                        if (block != Blocks.TRIPWIRE_HOOK && block != Blocks.RAIL && block != Blocks.ACTIVATOR_RAIL && block != Blocks.DETECTOR_RAIL && block != Blocks.GOLDEN_RAIL) {
                                                                                                                            return false;
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onGetBlockRenderLayer(final EventBlockGetRenderLayer event) {
        if (!ModuleWallhack.blocks.contains(event.getBlock())) {
            if (!containsBlock(event.getBlock())) {
                event.setCancelled(true);
                event.setLayer(BlockRenderLayer.TRANSLUCENT);
            }
        }
    }

    @SubscribeEvent
    public void renderPutColor(final EventRenderPutColorMultiplier event) {
        event.setCancelled(true);
        event.setOpacity(ModuleWallhack.Opacity.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.012516583f) ^ 0x7F32125B));
    }

    public static void processShouldSideBeRendered(final MixinBlock block, final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callback) {
        if (ModuleWallhack.blocks.contains(block)) {
            callback.setReturnValue(true);
        }
    }

    public static void processGetLightValue(final Block block, final CallbackInfoReturnable<Integer> callback) {
        if (ModuleWallhack.blocks.contains(block)) {
            callback.setReturnValue(1);
        }
    }

    public static void lambda$reloadWorld$0() {
        final int x = (int)ModuleWallhack.mc.player.posX;
        final int y = (int)ModuleWallhack.mc.player.posY;
        final int z = (int)ModuleWallhack.mc.player.posZ;
        final int distance = ModuleWallhack.mc.gameSettings.renderDistanceChunks * 16;
        ModuleWallhack.mc.renderGlobal.markBlockRangeForRenderUpdate(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);
    }

    static {
        ModuleWallhack.Mode = new ValueEnum("Mode", "Mode", "", modes.Normal);
        ModuleWallhack.Opacity = new ValueNumber("Opacity", "Opacity", "", Float.intBitsToFloat(Float.floatToIntBits(0.11912391f) ^ 0x7EF3F73D), Float.intBitsToFloat(Float.floatToIntBits(3.0370866E38f) ^ 0x7F647C29), Float.intBitsToFloat(Float.floatToIntBits(0.07696035f) ^ 0x7EE29D63));
        ModuleWallhack.HandBlock = new ValueBoolean("HandBlock", "HandBlock", "", false);
    }

    public enum modes
    {
        Normal,
        Circuits;

        public static modes[] $VALUES;

        static {
            modes.$VALUES = new modes[] { modes.Normal, modes.Circuits };
        }
    }
}
