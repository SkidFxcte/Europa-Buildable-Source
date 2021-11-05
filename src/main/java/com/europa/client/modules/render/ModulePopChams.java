/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.network.EventPacket;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ModulePopChams
extends Module {
    public static ValueBoolean angel = new ValueBoolean("Angel", "Angel", "", false);
    public static ValueNumber angelSpeed = new ValueNumber("AngelSpeed", "AngelSpeed", "", 150, 10, 500);
    public static ValueNumber fadeSpeed = new ValueNumber("FadeSpeed", "FadeSpeed", "", 200, 10, 500);
    public static ValueBoolean outline = new ValueBoolean("Outline", "Outline", "", false);
    public static ValueNumber width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(0.10667784123174527) ^ 0x7FB34F3D2F4C588FL), Double.longBitsToDouble(Double.doubleToLongBits(2.8356779810862056) ^ 0x7FE6AF77EFF6053EL), Double.longBitsToDouble(Double.doubleToLongBits(0.14239240361793695) ^ 0x7FD639EA0E5E7291L));
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor fillColor = new ValueColor("FillColor", "FillColor", "", new Color(255, 255, 255, 180));
    public static ValueColor outColor = new ValueColor("OutlineColor", "OutlineColor", "", new Color(255, 255, 255, 180));
    public static Color color;
    public static Color outlineColor;
    public static EntityOtherPlayerMP player;
    public static EntityPlayer entity;
    public long startTime;
    public static float opacity;
    public static long time;
    public static long duration;
    public static float startAlpha;

    public ModulePopChams() {
        super("PopChams", "Pop Chams", "Renders a cham which fades out when a player pops.", ModuleCategory.RENDER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        SPacketEntityStatus packet;
        void event;
        if (ModulePopChams.mc.player == null || ModulePopChams.mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.getPacket()).getEntity((World)ModulePopChams.mc.world) instanceof EntityPlayer) {
            entity = (EntityPlayer)packet.getEntity((World)ModulePopChams.mc.world);
            if (packet.getOpCode() == 35 && entity != null) {
                if (entity != ModulePopChams.mc.player) {
                    GameProfile profile = new GameProfile(ModulePopChams.mc.player.getUniqueID(), "");
                    player = new EntityOtherPlayerMP((World)ModulePopChams.mc.world, profile);
                    player.copyLocationAndAnglesFrom(packet.getEntity((World)ModulePopChams.mc.world));
                    ModulePopChams.player.rotationYaw = ModulePopChams.entity.rotationYaw;
                    ModulePopChams.player.rotationYawHead = ModulePopChams.entity.rotationYawHead;
                    ModulePopChams.player.rotationPitch = ModulePopChams.entity.rotationPitch;
                    ModulePopChams.player.prevRotationPitch = ModulePopChams.entity.prevRotationPitch;
                    ModulePopChams.player.prevRotationYaw = ModulePopChams.entity.prevRotationYaw;
                    ModulePopChams.player.renderYawOffset = ModulePopChams.entity.renderYawOffset;
                    this.startTime = System.currentTimeMillis();
                }
            }
        }
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        block6: {
            if (ModulePopChams.mc.player == null || ModulePopChams.mc.world == null) {
                return;
            }
            if (syncColor.getValue()) {
                color = ModulePopChams.globalColor(255);
                outlineColor = ModulePopChams.globalColor(255);
            } else {
                color = fillColor.getValue();
                outlineColor = outColor.getValue();
            }
            opacity = Float.intBitsToFloat(Float.floatToIntBits(1.6358529E38f) ^ 0x7EF622C3);
            time = System.currentTimeMillis();
            duration = time - this.startTime;
            startAlpha = (float)fillColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.0119778095f) ^ 0x7F3B3E93);
            if (player == null || entity == null) break block6;
            if (duration < (long)(fadeSpeed.getValue().intValue() * 10)) {
                opacity = startAlpha - (float)duration / (float)(fadeSpeed.getValue().intValue() * 10);
            }
            if (duration < (long)(fadeSpeed.getValue().intValue() * 10)) {
                GL11.glPushMatrix();
                if (angel.getValue()) {
                    GlStateManager.translate((float)Float.intBitsToFloat(Float.floatToIntBits(1.240196E38f) ^ 0x7EBA9A9D), (float)((float)duration / (float)(angelSpeed.getValue().intValue() * 10)), (float)Float.intBitsToFloat(Float.floatToIntBits(3.0414126E38f) ^ 0x7F64CF7A));
                }
                ModulePopChams.mc.renderManager.renderEntityStatic((Entity)player, Float.intBitsToFloat(Float.floatToIntBits(6.159893f) ^ 0x7F451DD8), false);
                GlStateManager.translate((float)Float.intBitsToFloat(Float.floatToIntBits(3.0715237E38f) ^ 0x7F671365), (float)Float.intBitsToFloat(Float.floatToIntBits(1.9152719E37f) ^ 0x7D668ADF), (float)Float.intBitsToFloat(Float.floatToIntBits(1.9703683E38f) ^ 0x7F143BEA));
                GL11.glPopMatrix();
            }
        }
    }
}

