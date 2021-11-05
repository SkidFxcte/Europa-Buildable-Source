/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.event;

import com.europa.api.manager.command.CommandManager;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.event.impl.network.EventPlayerJoin;
import com.europa.api.manager.event.impl.network.EventPlayerLeave;
import com.google.common.collect.Maps;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class EventManager {
    public static Minecraft mc = Minecraft.getMinecraft();
    public int colorRGBEffectRed;
    public int colorRGBEffectGreen;
    public int colorRGBEffectBlue;
    public CommandManager commandManager = new CommandManager();
    public Map<String, String> uuidNameCache = Maps.newConcurrentMap();

    public EventManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final float[] tick_color = { System.currentTimeMillis() % ((long)1483066307 ^ 0x5865E6C3L) / Float.intBitsToFloat(Float.floatToIntBits(4.213687E-5f) ^ 0x7E04BC1F) };
        final int colorInterpolated = Color.HSBtoRGB(tick_color[0], Float.intBitsToFloat(Float.floatToIntBits(12.152629f) ^ 0x7EC2712B), Float.intBitsToFloat(Float.floatToIntBits(4.6224623f) ^ 0x7F13EB36));
        this.colorRGBEffectRed = (colorInterpolated >> 16 & 0xFF);
        this.colorRGBEffectGreen = (colorInterpolated >> 8 & 0xFF);
        this.colorRGBEffectBlue = (colorInterpolated & 0xFF);
    }

    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem packet = (SPacketPlayerListItem)event.getPacket();
            if (packet.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                for (final SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != EventManager.mc.getSession().getProfile().getId()) {
                        new Thread(this::lambda$onReceive$0).start();
                    }
                }
            }
            if (packet.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (final SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != EventManager.mc.getSession().getProfile().getId()) {
                        new Thread(this::lambda$onReceive$1).start();
                    }
                }
            }
        }
    }

    private void lambda$onReceive$1() {
    }

    private void lambda$onReceive$0() {
    }


    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (this.uuidNameCache.containsKey(uuid)) {
            return this.uuidNameCache.get(uuid);
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        Object o;
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject) jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
            return null;
        }
        catch (IOException | ParseException ex) {
            final Throwable t = null;
            final Exception e = (Exception)(o = t);
        }
        ((Throwable)o).printStackTrace();
        return null;
    }



    public int[] getRGB() {
        return new int[]{this.colorRGBEffectRed, this.colorRGBEffectGreen, this.colorRGBEffectBlue};
    }

    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public void lambda$onReceive$1(final SPacketPlayerListItem.AddPlayerData playerData) {
        final UUID id = playerData.getProfile().getId();
        final EntityPlayer entity = EventManager.mc.world.getPlayerEntityByUUID(id);
        final String name = this.resolveName(playerData.getProfile().getId().toString());
        if (name != null) {
            if (EventManager.mc.player != null) {
                if (EventManager.mc.player.ticksExisted >= 1000) {
                    MinecraftForge.EVENT_BUS.post((Event)new EventPlayerLeave(name, id, entity));
                }
            }
        }
    }

    public void lambda$onReceive$0(final SPacketPlayerListItem.AddPlayerData playerData) {
        final UUID id = playerData.getProfile().getId();
        final String name = this.resolveName(playerData.getProfile().getId().toString());
        if (name != null && EventManager.mc.player != null && EventManager.mc.player.ticksExisted >= 1000) {
            MinecraftForge.EVENT_BUS.post((Event)new EventPlayerJoin(name, id));
        }
    }
    static {
        EventManager.mc = Minecraft.getMinecraft();
    }
}

