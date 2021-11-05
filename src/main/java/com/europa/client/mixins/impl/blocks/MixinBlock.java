package com.europa.client.mixins.impl.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.europa.*;
import com.europa.client.modules.render.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.*;
import com.europa.api.manager.event.impl.render.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mixin({ Block.class })
public class MixinBlock extends Block {
    public MixinBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    public void shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callback) {
        if (Europa.getModuleManager().isModuleEnabled("Wallhack")) {
            ModuleWallhack.processShouldSideBeRendered(this, blockState, blockAccess, pos, side, callback);
        }
    }

    @Inject(method = { "getRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void getRenderLayer(final CallbackInfoReturnable<BlockRenderLayer> callback) {
        final EventBlockGetRenderLayer event = new EventBlockGetRenderLayer(this);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue(event.getBlockRenderLayer());
        }
    }

    @Inject(method = { "getLightValue" }, at = { @At("HEAD") }, cancellable = true)
    public void getLightValue(final CallbackInfoReturnable<Integer> callback) {
        if (Europa.getModuleManager().isModuleEnabled("Wallhack")) {
            ModuleWallhack.processGetLightValue(this, callback);
        }
    }
}
