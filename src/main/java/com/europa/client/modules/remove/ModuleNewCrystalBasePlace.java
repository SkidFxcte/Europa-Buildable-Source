/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.Europa;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.crystal.CrystalUtils;
import com.europa.api.utilities.entity.DamageUtils;
import com.europa.api.utilities.entity.InventoryUtils;
import com.europa.api.utilities.math.MathUtils;
import com.europa.api.utilities.world.BlockUtils;
import com.europa.client.modules.combat.ModuleAutoCrystal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class ModuleNewCrystalBasePlace
extends Module {
    public static ValueEnum switchMode = new ValueEnum("Switch", "Switch", "The mode for switching to the target block.", InventoryUtils.SwitchModes.Normal);
    public static ValueBoolean crystalAuraCheck = new ValueBoolean("CrystalAuraCheck", "CrystalAuraCheck", "Runs only when the autocrystal is enabled and has special checks for it.", false);
    public static ValueBoolean yCheck = new ValueBoolean("YCheck", "YCheck", "Checks if the position's Y is the same as the player's.", false);
    public static ValueNumber placeRange = new ValueNumber("PlaceRange", "PlaceRange", "The range for placing.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.007834314f) ^ 0x7CA05B7F)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(2.8965124E37f) ^ 0x7DAE53D7)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.67379206f) ^ 0x7E0C7DA3)));
    public static ValueNumber targetRange = new ValueNumber("TargetRange", "TargetRange", "The range for targeting.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.008263736f) ^ 0x7D07649F)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(2.7480012E38f) ^ 0x7F4EBC94)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.36163035f) ^ 0x7F49279D)));
    public static ValueNumber minimumDamage = new ValueNumber("MinimumDamage", "MinimumDamage", "The minimum damage that is required for the target.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.20678781f) ^ 0x7E93C02F)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.8581853E38f) ^ 0x7F0BCB59)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.021685882f) ^ 0x7EA1A697)));
    public static ValueNumber maxSelfDamage = new ValueNumber("MaxSelfDamage", "MaxSelfDamage", "The minimum damage that is required for the target.", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.10188567f) ^ 0x7D10A96F)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(2.8877588E38f) ^ 0x7F594036)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.02501596f) ^ 0x7EDCEE45)));
    public EntityPlayer target = null;
    public BlockPos placedPosition = null;

    public ModuleNewCrystalBasePlace() {
        super("CrystalBasePlace", "CrystalBasePlace", "Automatically places obsidian blocks at target's", ModuleCategory.COMBAT);
    }

    @Override
    public void onMotionUpdate() {
        if (crystalAuraCheck.getValue() && !Europa.MODULE_MANAGER.isModuleEnabled("AutoCrystal")) {
            return;
        }
        int slot = InventoryUtils.findBlock(Blocks.OBSIDIAN, 0, 9);
        int lastSlot = ModuleNewCrystalBasePlace.mc.player.inventory.currentItem;
        BlockPos currentPosition = null;
        double maxDamage = Double.longBitsToDouble(Double.doubleToLongBits(1.1762782938084433E308) ^ 0x7FE4F03E2BCA7647L);
        if (slot == -1) {
            return;
        }
        this.target = CrystalUtils.getTarget(targetRange.getValue().floatValue());
        if (this.target == null) {
            return;
        }
        if (this.placedPosition == null) {
            if (crystalAuraCheck.getValue()) {
                if (ModuleAutoCrystal.renderPosition != null) {
                    return;
                }
            }
            for (BlockPos pos : CrystalUtils.getSphere(placeRange.getValue().floatValue(), true, false)) {
                float targetDamage = this.filterPosition(pos);
                if (targetDamage == Float.intBitsToFloat(Float.floatToIntBits(-47.056423f) ^ 0x7DBC39C7) || !((double)targetDamage > maxDamage)) continue;
                maxDamage = targetDamage;
                currentPosition = pos;
            }
            if (currentPosition != null) {
                InventoryUtils.switchSlot(slot, switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
                BlockUtils.placeBlock(currentPosition, EnumHand.MAIN_HAND, true);
                this.placedPosition = currentPosition;
                if (switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Strict)) {
                    InventoryUtils.switchSlot(lastSlot, switchMode.getValue().equals((Object)InventoryUtils.SwitchModes.Silent));
                }
            }
        }
        if (this.placedPosition != null && this.filterPosition(this.placedPosition) == Float.intBitsToFloat(Float.floatToIntBits(-10.85126f) ^ 0x7EAD9EC3)) {
            this.placedPosition = null;
        }
    }

    /*
     * WARNING - void declaration
     */
    public float filterPosition(BlockPos blockPos) {
        if (yCheck.getValue() && blockPos.getY() != ModuleNewCrystalBasePlace.mc.player.getPosition().getY()) {
            return Float.intBitsToFloat(Float.floatToIntBits(-7.911857f) ^ 0x7F7D2DEF);
        }
        if (!ModuleNewCrystalBasePlace.mc.world.getBlockState((BlockPos)blockPos).getBlock().isReplaceable((IBlockAccess)ModuleNewCrystalBasePlace.mc.world, (BlockPos)blockPos) || !ModuleNewCrystalBasePlace.mc.world.getBlockState(blockPos.up()).getBlock().isReplaceable((IBlockAccess)ModuleNewCrystalBasePlace.mc.world, blockPos.up())) {
            return Float.intBitsToFloat(Float.floatToIntBits(-8.346309f) ^ 0x7E858A7B);
        }
        if (BlockUtils.isIntercepted((BlockPos)blockPos) || BlockUtils.isIntercepted(blockPos.up())) {
            return Float.intBitsToFloat(Float.floatToIntBits(-17.143347f) ^ 0x7E092593);
        }
        if (ModuleNewCrystalBasePlace.mc.player.getDistanceSq((BlockPos)blockPos) > (double)MathUtils.square(placeRange.getValue().floatValue())) {
            return Float.intBitsToFloat(Float.floatToIntBits(-7.7399526f) ^ 0x7F77ADB1);
        }
        float targetDamage = DamageUtils.calculateDamage((double)blockPos.getX() + Double.longBitsToDouble(Double.doubleToLongBits(3.859775411443625) ^ 0x7FEEE0D1EE507148L), (double)blockPos.getY() + Double.longBitsToDouble(Double.doubleToLongBits(11.291667435420424) ^ 0x7FD695556F20E0B9L), (double)blockPos.getZ() + Double.longBitsToDouble(Double.doubleToLongBits(3.287754204373365) ^ 0x7FEA4D5213888F92L), (EntityLivingBase)this.target);
        float selfDamage = DamageUtils.calculateDamage((double)blockPos.getX() + Double.longBitsToDouble(Double.doubleToLongBits(3.0321794875384644) ^ 0x7FE841E751B4A351L), (double)blockPos.getY() + Double.longBitsToDouble(Double.doubleToLongBits(5.216471668479226) ^ 0x7FE4DDAABFC283ECL), (double)blockPos.getZ() + Double.longBitsToDouble(Double.doubleToLongBits(70.4361477151085) ^ 0x7FB19BE9D81B276FL), (EntityLivingBase)ModuleNewCrystalBasePlace.mc.player);
        if (targetDamage < minimumDamage.getValue().floatValue()) {
            if (targetDamage < this.target.getHealth() + this.target.getAbsorptionAmount()) {
                return Float.intBitsToFloat(Float.floatToIntBits(-7.3302693f) ^ 0x7F6A9191);
            }
        }
        if (selfDamage > maxSelfDamage.getValue().floatValue()) {
            return Float.intBitsToFloat(Float.floatToIntBits(-8.953656f) ^ 0x7E8F422D);
        }
        if (ModuleNewCrystalBasePlace.mc.player.getHealth() + ModuleNewCrystalBasePlace.mc.player.getAbsorptionAmount() <= selfDamage) {
            return Float.intBitsToFloat(Float.floatToIntBits(-6.2899423f) ^ 0x7F494735);
        }
        return targetDamage;
    }

    @Override
    public void onEnable() {
        this.target = null;
        this.placedPosition = null;
    }

    @Override
    public void onDisable() {
        this.target = null;
        this.placedPosition = null;
    }
}

