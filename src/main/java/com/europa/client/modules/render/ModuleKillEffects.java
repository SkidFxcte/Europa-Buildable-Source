/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.network.EventDeath;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.mojang.authlib.GameProfile;
import java.awt.Color;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ModuleKillEffects
extends Module {
    public ValueBoolean deathChams = new ValueBoolean("DeathChams", "DeathChams", "", false);
    public static ValueColor chamColor = new ValueColor("ChamColor", "ChamColor", "", new Color(255, 0, 0, 140));
    public ValueBoolean angel = new ValueBoolean("Angel", "Angel", "", false);
    public ValueNumber angelSpeed = new ValueNumber("AngelSpeed", "AngelSpeed", "", 150, 10, 500);
    public ValueNumber fadeSpeed = new ValueNumber("FadeSpeed", "FadeSpeed", "", 200, 10, 500);
    public ValueBoolean lightning = new ValueBoolean("Lightning", "Lightning", "", false);
    public ValueBoolean lightningSound = new ValueBoolean("LightningSound", "LightningSound", "", false);
    public static EntityOtherPlayerMP player;
    public static EntityPlayer entity;
    public long startTime;
    public static float opacity;
    public static long time;
    public static long duration;
    public static float startAlpha;

    public ModuleKillEffects() {
        super("KillEffects", "Kill Effects", "", ModuleCategory.RENDER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onEntityDeath(EventDeath eventDeath) {
        void event;
        if (ModuleKillEffects.mc.player == null || ModuleKillEffects.mc.world == null) {
            return;
        }
        if (this.deathChams.getValue() && (entity = event.player) != null && entity != ModuleKillEffects.mc.player) {
            GameProfile profile = new GameProfile(ModuleKillEffects.mc.player.getUniqueID(), "");
            player = new EntityOtherPlayerMP((World)ModuleKillEffects.mc.world, profile);
            player.copyLocationAndAnglesFrom((Entity)event.player);
            ModuleKillEffects.player.rotationYaw = ModuleKillEffects.entity.rotationYaw;
            ModuleKillEffects.player.rotationYawHead = ModuleKillEffects.entity.rotationYawHead;
            ModuleKillEffects.player.rotationPitch = ModuleKillEffects.entity.rotationPitch;
            ModuleKillEffects.player.prevRotationPitch = ModuleKillEffects.entity.prevRotationPitch;
            ModuleKillEffects.player.prevRotationYaw = ModuleKillEffects.entity.prevRotationYaw;
            ModuleKillEffects.player.renderYawOffset = ModuleKillEffects.entity.renderYawOffset;
            this.startTime = System.currentTimeMillis();
        }
        if (this.lightning.getValue()) {
            EntityLightningBolt bolt = new EntityLightningBolt((World)ModuleKillEffects.mc.world, Double.longBitsToDouble(Double.doubleToLongBits(2.700619365101586E307) ^ 0x7FC33AA2E6830ED7L), Double.longBitsToDouble(Double.doubleToLongBits(4.288545480809007E306) ^ 0x7F986DA963B0A5BFL), Double.longBitsToDouble(Double.doubleToLongBits(3.3865723560928404E307) ^ 0x7FC81CFA62BC4207L), false);
            if (this.lightningSound.getValue()) {
                ModuleKillEffects.mc.world.playSound(event.player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, Float.intBitsToFloat(Float.floatToIntBits(13.150525f) ^ 0x7ED2688D), Float.intBitsToFloat(Float.floatToIntBits(10.325938f) ^ 0x7EA5370B), false);
            }
            bolt.setLocationAndAngles(event.player.posX, event.player.posY, event.player.posZ, Float.intBitsToFloat(Float.floatToIntBits(3.2116163E38f) ^ 0x7F719D7B), Float.intBitsToFloat(Float.floatToIntBits(2.2278233E38f) ^ 0x7F279A51));
            ModuleKillEffects.mc.world.spawnEntity((Entity)bolt);
        }
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        block4: {
            if (ModuleKillEffects.mc.player == null || ModuleKillEffects.mc.world == null) {
                return;
            }
            opacity = Float.intBitsToFloat(Float.floatToIntBits(2.8309784E38f) ^ 0x7F54FAA9);
            time = System.currentTimeMillis();
            duration = time - this.startTime;
            startAlpha = (float)chamColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.010453977f) ^ 0x7F544728);
            if (player == null || entity == null) break block4;
            if (duration < (long)(this.fadeSpeed.getValue().intValue() * 10)) {
                opacity = startAlpha - (float)duration / (float)(this.fadeSpeed.getValue().intValue() * 10);
            }
            if (duration < (long)(this.fadeSpeed.getValue().intValue() * 10)) {
                GL11.glPushMatrix();
                if (this.angel.getValue()) {
                    GlStateManager.translate((float)Float.intBitsToFloat(Float.floatToIntBits(3.2438587E38f) ^ 0x7F740A72), (float)((float)duration / (float)(this.angelSpeed.getValue().intValue() * 10)), (float)Float.intBitsToFloat(Float.floatToIntBits(2.3625644E38f) ^ 0x7F31BD56));
                }
                ModuleKillEffects.mc.renderManager.renderEntityStatic((Entity)player, Float.intBitsToFloat(Float.floatToIntBits(6.8827176f) ^ 0x7F5C3F39), false);
                GlStateManager.translate((float)Float.intBitsToFloat(Float.floatToIntBits(1.6242844E38f) ^ 0x7EF46529), (float)Float.intBitsToFloat(Float.floatToIntBits(2.681521E38f) ^ 0x7F49BC37), (float)Float.intBitsToFloat(Float.floatToIntBits(1.5263033E38f) ^ 0x7EE5A711));
                GL11.glPopMatrix();
            }
        }
    }
}

