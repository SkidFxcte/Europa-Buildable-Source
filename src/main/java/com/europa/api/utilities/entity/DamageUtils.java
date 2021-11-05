//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.utilities.entity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.EntityLivingBase;
import com.europa.api.utilities.IMinecraft;

public class DamageUtils implements IMinecraft
{
    public static float calculateDamage(final double posX, final double posY, final double posZ, final EntityLivingBase entity) {
        try {
            final double distance = entity.getDistance(posX, posY, posZ) / Double.longBitsToDouble(Double.doubleToLongBits(0.7061696223198352) ^ 0x7FCE98F109295B0BL);
            final double value = (Double.longBitsToDouble(Double.doubleToLongBits(27.029805348929738) ^ 0x7FCB07A152C6E62BL) - distance) * DamageUtils.mc.world.getBlockDensity(new Vec3d(posX, posY, posZ), entity.getEntityBoundingBox());
            float damage = (int)((value * value + value) / Double.longBitsToDouble(Double.doubleToLongBits(0.8903081150566055) ^ 0x7FEC7D6771B10242L) * Double.longBitsToDouble(Double.doubleToLongBits(1.7109126140714253) ^ 0x7FE75FE5E7BC02A0L) * Double.longBitsToDouble(Double.doubleToLongBits(1.106780737933289) ^ 0x7FD9B55FB8144273L) + Double.longBitsToDouble(Double.doubleToLongBits(7.542607152744057) ^ 0x7FEE2BA1359E7213L)) * ((DamageUtils.mc.world.getDifficulty().getId() == 0) ? Float.intBitsToFloat(Float.floatToIntBits(2.8729047E38f) ^ 0x7F582222) : ((DamageUtils.mc.world.getDifficulty().getId() == 2) ? Float.intBitsToFloat(Float.floatToIntBits(11.309852f) ^ 0x7EB4F527) : ((DamageUtils.mc.world.getDifficulty().getId() == 1) ? Float.intBitsToFloat(Float.floatToIntBits(2.8048782f) ^ 0x7F338320) : Float.intBitsToFloat(Float.floatToIntBits(6.0613766f) ^ 0x7F01F6CC))));
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            damage *= Float.intBitsToFloat(Float.floatToIntBits(48.782467f) ^ 0x7DC3213F) - MathHelper.clamp((float)EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), DamageSource.causeExplosionDamage(new Explosion((World)DamageUtils.mc.world, (Entity)null, posX, posY, posZ, Float.intBitsToFloat(Float.floatToIntBits(1.0126694f) ^ 0x7F419F27), false, true))), Float.intBitsToFloat(Float.floatToIntBits(3.0601562E38f) ^ 0x7F663877), Float.intBitsToFloat(Float.floatToIntBits(0.48258448f) ^ 0x7F571550)) / Float.intBitsToFloat(Float.floatToIntBits(0.27084562f) ^ 0x7F42AC47);
            if (entity.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(11)))) {
                damage -= damage / Float.intBitsToFloat(Float.floatToIntBits(0.18720047f) ^ 0x7EBFB17B);
            }
            return damage;
        }
        catch (NullPointerException | ConcurrentModificationException ex2) {
            final RuntimeException ex;
            final RuntimeException exception = ex2;
            return Float.intBitsToFloat(Float.floatToIntBits(9.840829E37f) ^ 0x7E94117F);
        }
    }

    public static boolean shouldBreakArmor(final EntityLivingBase entity, final float targetPercent) {
        for (final ItemStack stack : entity.getArmorInventoryList()) {
            if (stack == null || stack.getItem() == Items.AIR) {
                return true;
            }
            final float armorPercent = (stack.getMaxDamage() - stack.getItemDamage()) / (float)stack.getMaxDamage() * Float.intBitsToFloat(Float.floatToIntBits(0.11806387f) ^ 0x7F39CB78);
            if (targetPercent >= armorPercent && stack.getMaxStackSize() < 2) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }

    public static int getRoundedDamage(final ItemStack stack) {
        return (int)((stack.getMaxDamage() - stack.getItemDamage()) / (float)stack.getMaxDamage() * Float.intBitsToFloat(Float.floatToIntBits(0.26034543f) ^ 0x7C4D4BFF));
    }
}
