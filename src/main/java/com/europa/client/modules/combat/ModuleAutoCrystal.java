//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.modules.combat;

import java.awt.Color;
import net.minecraft.util.math.MathHelper;
import com.europa.api.utilities.entity.DamageUtils;
import net.minecraft.world.GameType;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import com.europa.api.manager.event.impl.network.EventPacket;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.europa.api.manager.event.impl.world.EventCrystalAttack;
import java.util.function.Consumer;
import net.minecraft.util.math.AxisAlignedBB;
import com.europa.api.utilities.render.RenderUtils;
import java.util.function.Predicate;
import com.europa.api.manager.event.impl.render.EventRender3D;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import net.minecraft.item.ItemSword;
import net.minecraft.init.MobEffects;
import java.util.Collection;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import com.europa.api.utilities.entity.InventoryUtils;
import net.minecraft.init.Items;
import com.europa.Europa;
import com.europa.api.utilities.math.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import com.europa.api.utilities.crystal.CrystalUtils;
import net.minecraft.util.NonNullList;
import java.util.ArrayList;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueBoolean;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.item.EntityEnderCrystal;
import com.europa.api.manager.module.Module;

public class ModuleAutoCrystal extends Module
{
    public static ModuleAutoCrystal INSTANCE;
    public EntityEnderCrystal targetCrystal;
    public BlockPos targetPosition;
    public List<Integer> blacklist;
    public List<FadePosition> fadePositions;
    public static BlockPos renderPosition;
    public float damageNumber;
    public static boolean isSequential;
    public EntityPlayer target;
    public int breakTicks;
    public int placeTicks;
    public static ValueBoolean breakMode;
    public static ValueNumber breakDelay;
    public static ValueNumber breakRange;
    public static ValueNumber breakWallsRange;
    public static ValueBoolean antiWeakness;
    public static ValueBoolean inhibit;
    public static ValueBoolean sequential;
    public static ValueBoolean sync;
    public static ValueBoolean place;
    public static ValueNumber placeDelay;
    public static ValueNumber placeRange;
    public static ValueNumber placeWallsRange;
    public static ValueBoolean placeUnderBlock;
    public static ValueEnum switchMode;
    public static ValueEnum multiPlace;
    public static ValueBoolean holePlace;
    public static ValueBoolean rotation;
    public static ValueEnum timing;
    public static ValueNumber targetRange;
    public static ValueEnum swing;
    public static ValueBoolean packetSwing;
    public static ValueNumber minimumDamage;
    public static ValueNumber maxSelfDamage;
    public static ValueBoolean facePlace;
    public static ValueNumber facePlaceHealth;
    public static ValueBoolean armorBreaker;
    public static ValueNumber armorPercent;
    public static ValueEnum render;
    public static ValueEnum fill;
    public static ValueEnum outline;
    public static ValueBoolean renderDamage;
    public static ValueNumber shrinkSpeed;
    public static ValueNumber fadeDuration;
    public static ValueNumber lineWidth;
    public static ValueColor fillColor;
    public static ValueColor outlineColor;

    public ModuleAutoCrystal() {
        super("AutoCrystal", "Auto Crystal", "Automatically places and breaks crystals in order to kill the enemies.", ModuleCategory.COMBAT);
        this.targetCrystal = null;
        this.targetPosition = null;
        this.blacklist = new ArrayList<Integer>();
        this.fadePositions = new ArrayList<FadePosition>();
        this.damageNumber = Float.intBitsToFloat(Float.floatToIntBits(5.4387783E37f) ^ 0x7E23AAD3);
        this.target = null;
        ModuleAutoCrystal.INSTANCE = this;
    }

    @Override
    public void onMotionUpdate() {
        this.doAutoCrystal();
    }

    public void doAutoCrystal() {
        if (ModuleAutoCrystal.mc.player == null || ModuleAutoCrystal.mc.world == null) {
            return;
        }
        double maxCrystalDamage = Double.longBitsToDouble(Double.doubleToLongBits(1.4087661019685725E308) ^ 0x7FE913ADAFFBBAA4L);
        double maxPositionDamage = Double.longBitsToDouble(Double.doubleToLongBits(2.5986735741899057E307) ^ 0x7FC280CFC0677437L);
        if (ModuleAutoCrystal.place.getValue()) {
            if (this.placeTicks++ > ModuleAutoCrystal.placeDelay.getValue().intValue()) {
                this.placeTicks = 0;
                final NonNullList<BlockPos> positions = NonNullList.create();
                for (final BlockPos position : CrystalUtils.getSphere(ModuleAutoCrystal.placeRange.getValue().floatValue(), true, false)) {
                    if (ModuleAutoCrystal.mc.world.getBlockState(position).getBlock() == Blocks.AIR) {
                        continue;
                    }
                    if (!CrystalUtils.canPlaceCrystal(position, ModuleAutoCrystal.placeUnderBlock.getValue(), ModuleAutoCrystal.multiPlace.getValue().equals(MultiPlaceModes.Static) || (ModuleAutoCrystal.multiPlace.getValue().equals(MultiPlaceModes.Dynamic) && CrystalUtils.isEntityMoving((EntityLivingBase)ModuleAutoCrystal.mc.player) && CrystalUtils.isEntityMoving((EntityLivingBase)this.target)), ModuleAutoCrystal.holePlace.getValue())) {
                        continue;
                    }
                    positions.add((BlockPos) position);
                }
                for (final EntityPlayer player : ModuleAutoCrystal.mc.world.playerEntities) {
                    if (ModuleAutoCrystal.mc.player.getDistanceSq((Entity)player) > MathUtils.square(ModuleAutoCrystal.targetRange.getValue().floatValue())) {
                        continue;
                    }
                    if (player == ModuleAutoCrystal.mc.player) {
                        continue;
                    }
                    if (Europa.FRIEND_MANAGER.isFriend(player.getName())) {
                        continue;
                    }
                    if (player.isDead) {
                        continue;
                    }
                    if (player.getHealth() <= Float.intBitsToFloat(Float.floatToIntBits(2.0385629E38f) ^ 0x7F195D4B)) {
                        continue;
                    }
                    for (final BlockPos position2 : positions) {
                        final float targetDamage = this.filterPosition(position2, player);
                        if (targetDamage == Float.intBitsToFloat(Float.floatToIntBits(-4.9015f) ^ 0x7F1CD917)) {
                            continue;
                        }
                        if (targetDamage <= maxPositionDamage) {
                            continue;
                        }
                        maxPositionDamage = targetDamage;
                        this.targetPosition = position2;
                        this.damageNumber = targetDamage;
                        this.target = player;
                    }
                }
                if (this.targetPosition != null) {
                    final int slot = InventoryUtils.findItem(Items.END_CRYSTAL, 0, 9);
                    final int lastSlot = ModuleAutoCrystal.mc.player.inventory.currentItem;
                    if (!ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.None)) {
                        if (slot != -1) {
                            InventoryUtils.switchSlot(slot, ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.Silent));
                        }
                    }
                    Label_2013: {
                        if (ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
                            if (ModuleAutoCrystal.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                                if (!ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.Silent)) {
                                    ModuleAutoCrystal.renderPosition = null;
                                    break Label_2013;
                                }
                            }
                        }
                        ModuleAutoCrystal.renderPosition = this.targetPosition;
                        final RayTraceResult result = ModuleAutoCrystal.mc.world.rayTraceBlocks(new Vec3d(ModuleAutoCrystal.mc.player.posX, ModuleAutoCrystal.mc.player.posY + ModuleAutoCrystal.mc.player.getEyeHeight(), ModuleAutoCrystal.mc.player.posZ), new Vec3d(this.targetPosition.getX() + Double.longBitsToDouble(Double.doubleToLongBits(2.0585482766064893) ^ 0x7FE077E828AA18A5L), this.targetPosition.getY() - Double.longBitsToDouble(Double.doubleToLongBits(18.64274749914699) ^ 0x7FD2A48B19A06C0FL), this.targetPosition.getZ() + Double.longBitsToDouble(Double.doubleToLongBits(3.2686479786919134) ^ 0x7FEA2630E954F239L)));
                        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
                        ModuleAutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.targetPosition, facing, ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.Silent) ? EnumHand.MAIN_HAND : ((ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND), Float.intBitsToFloat(Float.floatToIntBits(29.637854f) ^ 0x7EED1A53), Float.intBitsToFloat(Float.floatToIntBits(3.3075676f) ^ 0x7F53AF30), Float.intBitsToFloat(Float.floatToIntBits(25347.998f) ^ 0x79C607FF)));
                        ModuleAutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketAnimation(ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.Silent) ? EnumHand.MAIN_HAND : ((ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND)));
                    }
                    if (ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.Silent) && lastSlot != -1) {
                        InventoryUtils.switchSlot(lastSlot, ModuleAutoCrystal.switchMode.getValue().equals(SwitchModes.Silent));
                    }
                }
                else {
                    ModuleAutoCrystal.renderPosition = null;
                }
                this.targetPosition = null;
            }
        }
        if (ModuleAutoCrystal.breakMode.getValue() && this.breakTicks++ > ModuleAutoCrystal.breakDelay.getValue().intValue()) {
            this.breakTicks = 0;
            for (final EntityPlayer player2 : ModuleAutoCrystal.mc.world.playerEntities) {
                if (ModuleAutoCrystal.mc.player.getDistanceSq((Entity)player2) > MathUtils.square(ModuleAutoCrystal.targetRange.getValue().floatValue())) {
                    continue;
                }
                if (player2 == ModuleAutoCrystal.mc.player) {
                    continue;
                }
                if (Europa.FRIEND_MANAGER.isFriend(player2.getName())) {
                    continue;
                }
                if (player2.isDead) {
                    continue;
                }
                if (player2.getHealth() <= Float.intBitsToFloat(Float.floatToIntBits(1.0582841E38f) ^ 0x7E9F3B9F)) {
                    continue;
                }
                for (final Entity entity : new ArrayList<Entity>(ModuleAutoCrystal.mc.world.loadedEntityList)) {
                    if (!(entity instanceof EntityEnderCrystal)) {
                        continue;
                    }
                    final EntityEnderCrystal crystal = (EntityEnderCrystal)entity;
                    if (this.blacklist.contains(crystal.getEntityId()) && ModuleAutoCrystal.inhibit.getValue()) {
                        continue;
                    }
                    final double targetDamage2 = this.filterCrystal(crystal, player2);
                    if (targetDamage2 == Double.longBitsToDouble(Double.doubleToLongBits(-10.430420888648806) ^ 0x7FD4DC6020708615L)) {
                        continue;
                    }
                    if (targetDamage2 <= maxCrystalDamage) {
                        continue;
                    }
                    maxCrystalDamage = targetDamage2;
                    this.targetCrystal = crystal;
                    this.target = player2;
                }
            }
            if (this.targetCrystal != null) {
                final int swordSlot = InventoryUtils.findItem(Items.DIAMOND_SWORD, 0, 9);
                final int appleSlot = InventoryUtils.findItem(Items.GOLDEN_APPLE, 0, 9);
                Label_3183: {
                    if (ModuleAutoCrystal.antiWeakness.getValue()) {
                        if (ModuleAutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
                            if (swordSlot != -1) {
                                if (!(ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                                    InventoryUtils.switchSlot(swordSlot, false);
                                }
                                break Label_3183;
                            }
                        }
                    }
                    if (ModuleAutoCrystal.antiWeakness.getValue() && !ModuleAutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS) && appleSlot != -1 && ModuleAutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                        InventoryUtils.switchSlot(appleSlot, false);
                    }
                }
                ModuleAutoCrystal.mc.playerController.attackEntity((EntityPlayer)ModuleAutoCrystal.mc.player, (Entity)this.targetCrystal);
                this.swingItem();
                this.targetCrystal = null;
            }
        }
    }

    @Override
    public void onRender3D(final EventRender3D event) {
        if (!ModuleAutoCrystal.render.getValue().equals(RenderModes.None)) {
            if (ModuleAutoCrystal.render.getValue().equals(RenderModes.Normal) || ModuleAutoCrystal.render.getValue().equals(RenderModes.Fade) || ModuleAutoCrystal.render.getValue() == RenderModes.Size) {
                if (ModuleAutoCrystal.renderPosition != null) {
                    if (ModuleAutoCrystal.render.getValue() == RenderModes.Fade || ModuleAutoCrystal.render.getValue() == RenderModes.Size) {
                        this.fadePositions.removeIf(ModuleAutoCrystal::lambda$onRender3D$0);
                        this.fadePositions.add(new FadePosition(ModuleAutoCrystal.renderPosition));
                    }
                    if (ModuleAutoCrystal.fill.getValue().equals(Renders.Normal)) {
                        RenderUtils.drawBox(ModuleAutoCrystal.renderPosition, ModuleAutoCrystal.fillColor.getValue());
                    }
                    if (ModuleAutoCrystal.outline.getValue().equals(Renders.Normal)) {
                        RenderUtils.drawBlockOutline(new AxisAlignedBB(ModuleAutoCrystal.renderPosition.getX() - ModuleAutoCrystal.mc.getRenderManager().viewerPosX, ModuleAutoCrystal.renderPosition.getY() - ModuleAutoCrystal.mc.getRenderManager().viewerPosY, ModuleAutoCrystal.renderPosition.getZ() - ModuleAutoCrystal.mc.getRenderManager().viewerPosZ, ModuleAutoCrystal.renderPosition.getX() + 1 - ModuleAutoCrystal.mc.getRenderManager().viewerPosX, ModuleAutoCrystal.renderPosition.getY() + 1 - ModuleAutoCrystal.mc.getRenderManager().viewerPosY, ModuleAutoCrystal.renderPosition.getZ() + 1 - ModuleAutoCrystal.mc.getRenderManager().viewerPosZ), ModuleAutoCrystal.outlineColor.getValue(), ModuleAutoCrystal.lineWidth.getValue().floatValue());
                    }
                }
                if (ModuleAutoCrystal.render.getValue().equals(RenderModes.Fade)) {
                    this.fadePositions.forEach(ModuleAutoCrystal::lambda$onRender3D$1);
                    this.fadePositions.removeIf(ModuleAutoCrystal::lambda$onRender3D$2);
                }
                else if (ModuleAutoCrystal.render.getValue().equals(RenderModes.Size)) {
                    this.fadePositions.stream().distinct().forEach((Consumer<? super Object>)ModuleAutoCrystal::lambda$onRender3D$3);
                    this.fadePositions.removeIf(ModuleAutoCrystal::lambda$onRender3D$4);
                }
            }
            if (ModuleAutoCrystal.renderDamage.getValue() && ModuleAutoCrystal.renderPosition != null) {
                CrystalUtils.drawText(ModuleAutoCrystal.renderPosition, ((Math.floor(this.damageNumber) == this.damageNumber) ? Integer.valueOf((int)this.damageNumber) : String.format("%.1f", this.damageNumber)) + "");
            }
        }
    }

    private static void lambda$onRender3D$3(Object o) {
    }

    @SubscribeEvent
    public void onCrystalAttack(final EventCrystalAttack event) {
        this.blacklist.add(event.getEntityID());
    }

    @SubscribeEvent
    public void onPacketReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketSoundEffect) {
            if (ModuleAutoCrystal.sync.getValue()) {
                final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    for (final Entity entity : new ArrayList<Entity>(ModuleAutoCrystal.mc.world.loadedEntityList)) {
                        if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(packet.getX(), packet.getY(), packet.getZ()) <= Double.longBitsToDouble(Double.doubleToLongBits(0.03533007623236061) ^ 0x7FE016C8A3F762CFL)) {
                            entity.setDead();
                        }
                    }
                }
            }
        }
        if (event.getPacket() instanceof SPacketSpawnObject && ModuleAutoCrystal.sequential.getValue()) {
            final SPacketSpawnObject packet2 = (SPacketSpawnObject)event.getPacket();
            if (this.target == null) {
                return;
            }
            if (packet2.getType() == 51) {
                if (ModuleAutoCrystal.breakMode.getValue()) {
                    final EntityEnderCrystal crystal = new EntityEnderCrystal((World)ModuleAutoCrystal.mc.world, packet2.getX(), packet2.getY(), packet2.getZ());
                    if (this.target != null && this.filterCrystal(crystal, this.target) != Float.intBitsToFloat(Float.floatToIntBits(-26.224195f) ^ 0x7E51CB27)) {
                        if (this.blacklist.contains(packet2.getEntityID())) {
                            return;
                        }
                        ModuleAutoCrystal.isSequential = true;
                        final CPacketUseEntity crystalPacket = new CPacketUseEntity();
                        crystalPacket.entityId = packet2.getEntityID();
                        crystalPacket.action = CPacketUseEntity.Action.ATTACK;
                        ModuleAutoCrystal.mc.player.connection.sendPacket((Packet)crystalPacket);
                        if (ModuleAutoCrystal.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                            ModuleAutoCrystal.mc.player.resetCooldown();
                        }
                        this.swingItem();
                        this.blacklist.add(crystalPacket.entityId);
                        ModuleAutoCrystal.isSequential = false;
                    }
                }
            }
        }
    }


    public float filterCrystal(final EntityEnderCrystal crystal, final EntityPlayer target) {
        Label_0178: {
            if (ModuleAutoCrystal.mc.player.canEntityBeSeen((Entity)crystal)) {
                if (ModuleAutoCrystal.mc.player.getDistanceSq((Entity)crystal) <= MathUtils.square(ModuleAutoCrystal.breakRange.getValue().floatValue())) {
                    break Label_0178;
                }
            }
            else if (ModuleAutoCrystal.mc.player.getDistanceSq((Entity)crystal) <= MathUtils.square(ModuleAutoCrystal.breakWallsRange.getValue().floatValue())) {
                break Label_0178;
            }
            return Float.intBitsToFloat(Float.floatToIntBits(-5.0406475f) ^ 0x7F214CFC);
        }
        if (crystal.isDead) {
            return Float.intBitsToFloat(Float.floatToIntBits(-208.54588f) ^ 0x7CD08BBF);
        }
        final float targetDamage = DamageUtils.calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (EntityLivingBase)target);
        final float selfDamage = DamageUtils.calculateDamage(crystal.posX, crystal.posY, crystal.posZ, (EntityLivingBase)ModuleAutoCrystal.mc.player);
        return this.returnDamage(target, targetDamage, selfDamage);
    }

    public float filterPosition(final BlockPos position, final EntityPlayer target) {
        Label_0190: {
            if (CrystalUtils.canSeePos(position)) {
                if (ModuleAutoCrystal.mc.player.getDistanceSq(position) <= MathUtils.square(ModuleAutoCrystal.placeRange.getValue().floatValue())) {
                    break Label_0190;
                }
            }
            else if (ModuleAutoCrystal.mc.player.getDistanceSq(position) <= MathUtils.square(ModuleAutoCrystal.placeWallsRange.getValue().floatValue())) {
                break Label_0190;
            }
            return Float.intBitsToFloat(Float.floatToIntBits(-7.1987925f) ^ 0x7F665C82);
        }
        final float targetDamage = DamageUtils.calculateDamage(position.getX() + Double.longBitsToDouble(Double.doubleToLongBits(2.926604140248566) ^ 0x7FE769AF6E75A574L), position.getY() + Double.longBitsToDouble(Double.doubleToLongBits(10.872572781808893) ^ 0x7FD5BEC1DC127F75L), position.getZ() + Double.longBitsToDouble(Double.doubleToLongBits(20.94846926721453) ^ 0x7FD4F2CEE1C3F28FL), (EntityLivingBase)target);
        final float selfDamage = DamageUtils.calculateDamage(position.getX() + Double.longBitsToDouble(Double.doubleToLongBits(3.800241793394989) ^ 0x7FEE66E52B5C30E1L), position.getY() + Double.longBitsToDouble(Double.doubleToLongBits(8.346756871161473) ^ 0x7FD0B18A1DDA9A87L), position.getZ() + Double.longBitsToDouble(Double.doubleToLongBits(16.53449891037378) ^ 0x7FD088D4EBABCD93L), (EntityLivingBase)ModuleAutoCrystal.mc.player);
        return this.returnDamage(target, targetDamage, selfDamage);
    }

    public float returnDamage(final EntityPlayer target, final float targetDamage, final float selfDamage) {
        if (targetDamage < this.getMinimumDamage((EntityLivingBase)target)) {
            if (targetDamage < target.getHealth() + target.getAbsorptionAmount()) {
                return Float.intBitsToFloat(Float.floatToIntBits(-5.012834f) ^ 0x7F206923);
            }
        }
        if (selfDamage > ModuleAutoCrystal.maxSelfDamage.getValue().floatValue()) {
            return Float.intBitsToFloat(Float.floatToIntBits(-17.910734f) ^ 0x7E0F492F);
        }
        if (ModuleAutoCrystal.mc.player.getHealth() + ModuleAutoCrystal.mc.player.getAbsorptionAmount() <= selfDamage) {
            return Float.intBitsToFloat(Float.floatToIntBits(-6.6046715f) ^ 0x7F535978);
        }
        return targetDamage;
    }

    public void swingItem() {
        if (!ModuleAutoCrystal.swing.getValue().equals(Hands.None)) {
            if (ModuleAutoCrystal.packetSwing.getValue()) {
                if (ModuleAutoCrystal.swing.getValue().equals(Hands.Mainhand)) {
                    ModuleAutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                }
                else if (ModuleAutoCrystal.swing.getValue().equals(Hands.Offhand)) {
                    ModuleAutoCrystal.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
                }
            }
            else if (ModuleAutoCrystal.swing.getValue().equals(Hands.Mainhand)) {
                ModuleAutoCrystal.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            else if (ModuleAutoCrystal.swing.getValue().equals(Hands.Offhand)) {
                ModuleAutoCrystal.mc.player.swingArm(EnumHand.OFF_HAND);
            }
        }
    }

    public float getMinimumDamage(final EntityLivingBase entity) {
        if ((ModuleAutoCrystal.facePlace.getValue() && entity.getHealth() + entity.getAbsorptionAmount() < ModuleAutoCrystal.facePlaceHealth.getValue().floatValue()) || (ModuleAutoCrystal.armorBreaker.getValue() && DamageUtils.shouldBreakArmor(entity, (float)ModuleAutoCrystal.armorPercent.getValue().intValue()))) {
            return Float.intBitsToFloat(Float.floatToIntBits(15.796245f) ^ 0x7EFCBD6B);
        }
        return ModuleAutoCrystal.minimumDamage.getValue().floatValue();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ModuleAutoCrystal.isSequential = false;
        this.target = null;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ModuleAutoCrystal.isSequential = false;
        this.target = null;
    }

    @Override
    public void onLogout() {
        this.blacklist.clear();
    }

    public static boolean lambda$onRender3D$4(final FadePosition p) {
        return System.currentTimeMillis() - FadePosition.access$000(p) >= ModuleAutoCrystal.shrinkSpeed.getValue().intValue() * ((long)(-1142890069) ^ 0xFFFFFFFFBBE0E1A1L) || ModuleAutoCrystal.mc.world.getBlockState(FadePosition.access$100(p)).getBlock() == Blocks.AIR;
    }

    public static void lambda$onRender3D$3(final FadePosition p) {
        if (!FadePosition.access$100(p).equals((Object)ModuleAutoCrystal.renderPosition)) {
            AxisAlignedBB bb = RenderUtils.fixBB(new AxisAlignedBB(FadePosition.access$100(p)));
            final long time = System.currentTimeMillis();
            final long duration = time - FadePosition.access$000(p);
            final float startAlpha = ModuleAutoCrystal.fillColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.19909953f) ^ 0x7D34E0BF);
            if (duration < ModuleAutoCrystal.shrinkSpeed.getValue().intValue() * ((long)224288018 ^ 0xD5E5D18L)) {
                float opacity = startAlpha - duration / (float)(ModuleAutoCrystal.shrinkSpeed.getValue().intValue() * 10);
                opacity = MathHelper.clamp(opacity, Float.intBitsToFloat(Float.floatToIntBits(-4.7571034f) ^ 0x7F183A31), Float.intBitsToFloat(Float.floatToIntBits(2.7682826E38f) ^ 0x7F50432F));
                bb = bb.shrink((double)(-opacity));
                RenderUtils.drawFilledBox(bb, ModuleAutoCrystal.fillColor.getValue().getRGB());
                RenderUtils.drawBlockOutline(bb, ModuleAutoCrystal.outlineColor.getValue(), Float.intBitsToFloat(Float.floatToIntBits(4.049864f) ^ 0x7F01987C));
            }
        }
    }

    public static boolean lambda$onRender3D$2(final FadePosition fadePosition) {
        return System.currentTimeMillis() - fadePosition.getStartTime() >= ModuleAutoCrystal.fadeDuration.getValue().intValue() * ((long)1778390701 ^ 0x6A0016C9L);
    }

    public static void lambda$onRender3D$1(final FadePosition pos) {
        if (!pos.getPosition().equals((Object)ModuleAutoCrystal.renderPosition)) {
            final long time = System.currentTimeMillis();
            final long duration = time - pos.getStartTime();
            if (duration < ModuleAutoCrystal.fadeDuration.getValue().intValue() * ((long)(-1970346968) ^ 0xFFFFFFFF8A8EE44CL)) {
                final float opacity = ModuleAutoCrystal.fillColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.3990955f) ^ 0x7DB3563F) - duration / (float)(ModuleAutoCrystal.fadeDuration.getValue().intValue() * ((long)1706775068 ^ 0x65BB5278L));
                final int alpha = MathHelper.clamp((int)(opacity * Float.intBitsToFloat(Float.floatToIntBits(0.7680854f) ^ 0x7C3BA13F)), 0, 255);
                if (ModuleAutoCrystal.fill.getValue().equals(Renders.Normal)) {
                    RenderUtils.drawBox(pos.getPosition(), new Color(ModuleAutoCrystal.fillColor.getValue().getRed(), ModuleAutoCrystal.fillColor.getValue().getGreen(), ModuleAutoCrystal.fillColor.getValue().getBlue(), alpha));
                }
                if (ModuleAutoCrystal.outline.getValue().equals(Renders.Normal)) {
                    RenderUtils.prepare(7);
                    RenderUtils.drawBoundingBoxBlockPos(pos.getPosition(), ModuleAutoCrystal.lineWidth.getValue().floatValue(), ModuleAutoCrystal.outlineColor.getValue().getRed(), ModuleAutoCrystal.outlineColor.getValue().getGreen(), ModuleAutoCrystal.outlineColor.getValue().getBlue(), alpha);
                    RenderUtils.release();
                }
            }
        }
    }

    public static boolean lambda$onRender3D$0(final FadePosition pos) {
        return FadePosition.access$100(pos).equals((Object)ModuleAutoCrystal.renderPosition);
    }

    static {
        ModuleAutoCrystal.renderPosition = null;
        ModuleAutoCrystal.isSequential = false;
        ModuleAutoCrystal.breakMode = new ValueBoolean("Break", "Break", "Breaks the crystals.", true);
        ModuleAutoCrystal.breakDelay = new ValueNumber("BreakDelay", "BreakDelay", "The delay for breaking.", 0, 0, 20);
        ModuleAutoCrystal.breakRange = new ValueNumber("BreakRange", "BreakRange", "The range for breaking.", Float.intBitsToFloat(Float.floatToIntBits(1.0256348f) ^ 0x7F234800), Float.intBitsToFloat(Float.floatToIntBits(1.1883357E38f) ^ 0x7EB2CD07), Float.intBitsToFloat(Float.floatToIntBits(1.6635215f) ^ 0x7F14EE46));
        ModuleAutoCrystal.breakWallsRange = new ValueNumber("BreakWallsRange", "BreakWallsRange", "The range for breaking through walls.", Float.intBitsToFloat(Float.floatToIntBits(0.56104803f) ^ 0x7F6FA0D8), Float.intBitsToFloat(Float.floatToIntBits(1.5817177E38f) ^ 0x7EEDFD8D), Float.intBitsToFloat(Float.floatToIntBits(0.17075278f) ^ 0x7EEED9D1));
        ModuleAutoCrystal.antiWeakness = new ValueBoolean("AntiWeakness", "AntiWeakness", "Switches to a sword when you have weakness.", false);
        ModuleAutoCrystal.inhibit = new ValueBoolean("Inhibit", "Inhibit", "Prevents an a crystal which is going to explode from being attacked again.", true);
        ModuleAutoCrystal.sequential = new ValueBoolean("Sequential", "Sequential", "Breaks crystals when they spawn. Good for strictless servers.", true);
        ModuleAutoCrystal.sync = new ValueBoolean("Sync", "Sync", "Syncs crystals based on sounds.", true);
        ModuleAutoCrystal.place = new ValueBoolean("Place", "Place", "Places the crystals.", true);
        ModuleAutoCrystal.placeDelay = new ValueNumber("PlaceDelay", "PlaceDelay", "The delay for placing.", 0, 0, 20);
        ModuleAutoCrystal.placeRange = new ValueNumber("PlaceRange", "PlaceRange", "The range for placing.", Float.intBitsToFloat(Float.floatToIntBits(0.22218512f) ^ 0x7EC3847F), Float.intBitsToFloat(Float.floatToIntBits(8.3361226E37f) ^ 0x7E7ADB27), Float.intBitsToFloat(Float.floatToIntBits(1.0495443f) ^ 0x7F465778));
        ModuleAutoCrystal.placeWallsRange = new ValueNumber("PlaceWallsRange", "PlaceWallsRange", "The range for breaking through walls.", Float.intBitsToFloat(Float.floatToIntBits(0.28594783f) ^ 0x7EF267C1), Float.intBitsToFloat(Float.floatToIntBits(1.5344281E38f) ^ 0x7EE6E005), Float.intBitsToFloat(Float.floatToIntBits(1.7122473f) ^ 0x7F1B2AEB));
        ModuleAutoCrystal.placeUnderBlock = new ValueBoolean("PlaceUnderBlock", "PlaceUnderBlock", "Places under blocks.", false);
        ModuleAutoCrystal.switchMode = new ValueEnum("Switch", "Switch", "Automatically switches to a crystal.", SwitchModes.None);
        ModuleAutoCrystal.multiPlace = new ValueEnum("MultiPlace", "MultiPlace", "Places in more positions than one.", MultiPlaceModes.None);
        ModuleAutoCrystal.holePlace = new ValueBoolean("HolePlace", "HolePlace", "Places in the hole when the player jumps.", true);
        ModuleAutoCrystal.rotation = new ValueBoolean("Rotation", "Rotation", "Rotates to the crystal and position when placing.", false);
        ModuleAutoCrystal.timing = new ValueEnum("Timing", "Timing", "The timing for the breaking.", Timings.Break);
        ModuleAutoCrystal.targetRange = new ValueNumber("TargetRange", "TargetRange", "The range for targeting.", Float.intBitsToFloat(Float.floatToIntBits(1.5514623f) ^ 0x7EB69651), Float.intBitsToFloat(Float.floatToIntBits(2.1071864E38f) ^ 0x7F1E86EF), Float.intBitsToFloat(Float.floatToIntBits(0.59863883f) ^ 0x7EE94065));
        ModuleAutoCrystal.swing = new ValueEnum("Swing", "Swing", "The hand to swing with.", Hands.Mainhand);
        ModuleAutoCrystal.packetSwing = new ValueBoolean("PacketSwing", "PacketSwing", "Swings serverside but not clientside.", false);
        ModuleAutoCrystal.minimumDamage = new ValueNumber("MinimumDamage", "MinimumDamage", "The minimum damage that is required for the target.", Float.intBitsToFloat(Float.floatToIntBits(1.5494468f) ^ 0x7F065446), Float.intBitsToFloat(Float.floatToIntBits(2.975486E37f) ^ 0x7DB3149F), Float.intBitsToFloat(Float.floatToIntBits(0.03962459f) ^ 0x7F324D65));
        ModuleAutoCrystal.maxSelfDamage = new ValueNumber("MaxSelfDamage", "MaxSelfDamage", "The minimum damage that is required for the target.", Float.intBitsToFloat(Float.floatToIntBits(0.21364413f) ^ 0x7E9AC587), Float.intBitsToFloat(Float.floatToIntBits(2.5525206E38f) ^ 0x7F4007C2), Float.intBitsToFloat(Float.floatToIntBits(0.008906226f) ^ 0x7E01EB6B));
        ModuleAutoCrystal.facePlace = new ValueBoolean("FacePlace", "FacePlace", "Faceplaces the target when the opportunity is right.", true);
        ModuleAutoCrystal.facePlaceHealth = new ValueNumber("FacePlaceHealth", "FacePlaceHealth", "The health at which faceplacing would start.", Float.intBitsToFloat(Float.floatToIntBits(0.2479648f) ^ 0x7F3DEA7C), Float.intBitsToFloat(Float.floatToIntBits(2.5063878E38f) ^ 0x7F3C8F46), Float.intBitsToFloat(Float.floatToIntBits(0.01991236f) ^ 0x7EB31F3F));
        ModuleAutoCrystal.armorBreaker = new ValueBoolean("ArmorBreaker", "ArmorBreaker", "Starts faceplacing the enemy when their armor is low.", true);
        ModuleAutoCrystal.armorPercent = new ValueNumber("ArmorPercent", "ArmorPercent", "The percent required for the armor breaking to start.", 20, 0, 100);
        ModuleAutoCrystal.render = new ValueEnum("Render", "Render", "Renders the current target position.", RenderModes.Normal);
        ModuleAutoCrystal.fill = new ValueEnum("Fill", "Fill", "The mode for filling the position.", Renders.Normal);
        ModuleAutoCrystal.outline = new ValueEnum("Outline", "Outline", "The mode for outlining the position.", Renders.Normal);
        ModuleAutoCrystal.renderDamage = new ValueBoolean("RenderDamage", "RenderDamage", "Renders the amount of damage that the position does.", false);
        ModuleAutoCrystal.shrinkSpeed = new ValueNumber("ShrinkSpeed", "ShrinkSpeed", "", 150, 10, 500);
        ModuleAutoCrystal.fadeDuration = new ValueNumber("FadeDuration", "FadeDuration", "The duration of the fade.", 15, 1, 50);
        ModuleAutoCrystal.lineWidth = new ValueNumber("Width", "Width", "The width for the outline.", Float.intBitsToFloat(Float.floatToIntBits(4.8967586f) ^ 0x7F1CB23F), Float.intBitsToFloat(Float.floatToIntBits(2.1313062E38f) ^ 0x7F205777), Float.intBitsToFloat(Float.floatToIntBits(1.2066184f) ^ 0x7F3A7279));
        ModuleAutoCrystal.fillColor = new ValueColor("FillColor", "FillColor", "The color for the filling.", new Color(0, 0, 255, 100));
        ModuleAutoCrystal.outlineColor = new ValueColor("OutlineColor", "OutlineColor", "The color for the outline.", new Color(0, 0, 255, 255));
    }

    public static class FadePosition
    {
        public BlockPos position;
        public long startTime;

        public FadePosition(final BlockPos position) {
            this.position = position;
            this.startTime = System.currentTimeMillis();
        }

        public BlockPos getPosition() {
            return this.position;
        }

        public long getStartTime() {
            return this.startTime;
        }

        public static long access$000(final FadePosition x0) {
            return x0.startTime;
        }

        public static BlockPos access$100(final FadePosition x0) {
            return x0.position;
        }
    }

    public enum Timings
    {
        Break,
        Place;

        public static Timings[] $VALUES;

        static {
            Timings.$VALUES = new Timings[] { Timings.Break, Timings.Place };
        }
    }

    public enum SwitchModes
    {
        None,
        Normal,
        Silent;

        public static SwitchModes[] $VALUES;

        static {
            SwitchModes.$VALUES = new SwitchModes[] { SwitchModes.None, SwitchModes.Normal, SwitchModes.Silent };
        }
    }

    public enum Hands
    {
        None,
        Mainhand,
        Offhand;

        public static Hands[] $VALUES;

        static {
            Hands.$VALUES = new Hands[] { Hands.None, Hands.Mainhand, Hands.Offhand };
        }
    }

    public enum MultiPlaceModes
    {
        None,
        Dynamic,
        Static;

        public static MultiPlaceModes[] $VALUES;

        static {
            MultiPlaceModes.$VALUES = new MultiPlaceModes[] { MultiPlaceModes.None, MultiPlaceModes.Dynamic, MultiPlaceModes.Static };
        }
    }

    public enum RenderModes
    {
        None,
        Normal,
        Fade,
        Size;

        public static RenderModes[] $VALUES;

        static {
            RenderModes.$VALUES = new RenderModes[] { RenderModes.None, RenderModes.Normal, RenderModes.Fade, RenderModes.Size };
        }
    }

    public enum Renders
    {
        None,
        Normal;

        public static Renders[] $VALUES;

        static {
            Renders.$VALUES = new Renders[] { Renders.None, Renders.Normal };
        }
    }
}
