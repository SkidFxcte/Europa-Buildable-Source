/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPlayerJoin;
import com.europa.api.manager.event.impl.network.EventPlayerLeave;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.math.MathUtils;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.client.modules.client.ModuleColor;
import com.europa.client.modules.render.ModuleNametags;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleLogoutSpots
extends Module {
    public List<LogoutPos> spots = new CopyOnWriteArrayList<LogoutPos>();
    public AxisAlignedBB bb;
    public double x;
    public double y;
    public double z;
    public static ValueNumber range;
    public static ValueBoolean coords;
    public static ValueBoolean message;
    public static ValueBoolean syncColor;
    public static ValueColor daColor;
    public Color color;
    public static boolean $assertionsDisabled;

    public ModuleLogoutSpots() {
        super("LogoutSpots", "Logout Spots", "Draws an ESP on logout spots from other players.", ModuleCategory.RENDER);
    }

    @Override
    public void onDisable() {
        this.spots.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        this.color = syncColor.getValue() ? ModuleLogoutSpots.globalColor(255) : daColor.getValue();
        if (!this.spots.isEmpty()) {
            List<LogoutPos> list = this.spots;
            synchronized (list) {
                void event;
                List<LogoutPos> list2 = this.spots;
                Consumer<LogoutPos> consumer = arg_0 -> this.lambda$onRender3D$0((EventRender3D)event, arg_0);
                list2.forEach(consumer);
            }
        }
    }

    @Override
    public void onUpdate() {
        this.spots.removeIf(this::lambda$onUpdate$1);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onJoin(EventPlayerJoin eventPlayerJoin) {
        void event;
        UUID uuid = event.getUuid();
        EntityPlayer entity = ModuleLogoutSpots.mc.world.getPlayerEntityByUUID(uuid);
        if (entity != null && message.getValue()) {
            ChatManager.printChatNotifyClient(ChatFormatting.GOLD + entity.getName() + ChatFormatting.GREEN + " logged in" + ChatFormatting.WHITE + (coords.getValue() ? " at [" + (int)entity.posX + ", " + (int)entity.posY + ", " + (int)entity.posZ + "]!" : "!"));
        }
        this.spots.removeIf(arg_0 -> ModuleLogoutSpots.lambda$onJoin$2((EventPlayerJoin)event, arg_0));
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onLeave(EventPlayerLeave eventPlayerLeave) {
        void event;
        EntityPlayer entity2 = event.getEntity();
        UUID uuid2 = event.getUuid();
        String name = event.getName();
        if (message.getValue()) {
            ChatManager.printChatNotifyClient(ChatFormatting.GOLD + event.getName() + ChatFormatting.RED + " logged out" + ChatFormatting.WHITE + (coords.getValue() ? " at [" + (int)entity2.posX + ", " + (int)entity2.posY + ", " + (int)entity2.posZ + "]!" : "!"));
        }
        if (name != null && entity2 != null) {
            if (uuid2 != null) {
                this.spots.add(new LogoutPos(name, uuid2, entity2));
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void renderNameTag(String string, double d, double d2, double d3, float f, double d4, double d5, double d6) {
        void z;
        void x;
        void zPos;
        void yPos;
        void xPos;
        void name;
        void delta;
        void yi;
        void y = yi + Double.longBitsToDouble(Double.doubleToLongBits(19.2691604693764) ^ 0x7FD52281D5333E99L);
        Entity camera = mc.getRenderViewEntity();
        if (!$assertionsDisabled && camera == null) {
            throw new AssertionError();
        }
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, (float)delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, (float)delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, (float)delta);
        String displayTag = (String)name + "_logout_spot XYZ " + String.format("%.1f", (double)xPos) + " " + String.format("%.1f", (double)yPos) + " " + String.format("%.1f", (double)zPos);
        double distance = camera.getDistance((double)(x + ModuleLogoutSpots.mc.getRenderManager().viewerPosX), (double)(y + ModuleLogoutSpots.mc.getRenderManager().viewerPosY), (double)(z + ModuleLogoutSpots.mc.getRenderManager().viewerPosZ));
        int width = (int)Europa.FONT_MANAGER.getStringWidth(displayTag) / 2;
        double scale = (Double.longBitsToDouble(Double.doubleToLongBits(6534.3787814246125) ^ 0x7FE4FBDFBFAE0C99L) + (double)ModuleNametags.m_scale.getValue().intValue() * (distance * Double.longBitsToDouble(Double.doubleToLongBits(5.421360593101863) ^ 0x7FC69C4A158FFB6FL))) / Double.longBitsToDouble(Double.doubleToLongBits(0.002893720671430056) ^ 0x7FE8F49274B8BD67L);
        if (distance <= Double.longBitsToDouble(Double.doubleToLongBits(0.1456175720542212) ^ 0x7FE2A398BAD91018L)) {
            scale = Double.longBitsToDouble(Double.doubleToLongBits(15340.026133200949) ^ 0x7F54E084735721FFL);
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset((float)Float.intBitsToFloat(Float.floatToIntBits(10.776967f) ^ 0x7EAC6E75), (float)Float.intBitsToFloat(Float.floatToIntBits(-1.855493E-7f) ^ 0x7DF02067));
        GlStateManager.disableLighting();
        GlStateManager.translate((float)((float)x), (float)((float)y + Float.intBitsToFloat(Float.floatToIntBits(8.775761f) ^ 0x7EBF5AB7)), (float)((float)z));
        GlStateManager.rotate((float)(-ModuleLogoutSpots.mc.getRenderManager().playerViewY), (float)Float.intBitsToFloat(Float.floatToIntBits(1.5161972E38f) ^ 0x7EE421CB), (float)Float.intBitsToFloat(Float.floatToIntBits(12.240474f) ^ 0x7EC3D8FB), (float)Float.intBitsToFloat(Float.floatToIntBits(1.2810288E37f) ^ 0x7D1A32BF));
        GlStateManager.rotate((float)ModuleLogoutSpots.mc.getRenderManager().playerViewX, (float)(ModuleLogoutSpots.mc.gameSettings.thirdPersonView == 2 ? Float.intBitsToFloat(Float.floatToIntBits(-5.1935806f) ^ 0x7F2631D0) : Float.intBitsToFloat(Float.floatToIntBits(29.95828f) ^ 0x7E6FAA8F)), (float)Float.intBitsToFloat(Float.floatToIntBits(1.8887807E38f) ^ 0x7F0E1898), (float)Float.intBitsToFloat(Float.floatToIntBits(1.3995906E38f) ^ 0x7ED29645));
        GlStateManager.scale((double)(-scale), (double)(-scale), (double)scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        RenderUtils.drawRectrgb(-width - 2, -(Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(50.871395f) ^ 0x7DCB7C4F)), (float)width + Float.intBitsToFloat(Float.floatToIntBits(0.22964416f) ^ 0x7E2B27D7), Float.intBitsToFloat(Float.floatToIntBits(0.9696441f) ^ 0x7F583A99), Float.intBitsToFloat(Float.floatToIntBits(3.2892732E38f) ^ 0x7F777519), Float.intBitsToFloat(Float.floatToIntBits(1.5434712E38f) ^ 0x7EE83C59), Float.intBitsToFloat(Float.floatToIntBits(2.3539138E38f) ^ 0x7F3116BB), ModuleNametags.a.getValue().intValue());
        if (ModuleNametags.outline.getValue()) {
            RenderUtils.drawOutlineLine(-width - 2, -(Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(84.843376f) ^ 0x7D29AFCF)), (float)width + Float.intBitsToFloat(Float.floatToIntBits(0.8480899f) ^ 0x7F191C6B), Double.longBitsToDouble(Double.doubleToLongBits(0.8225587648286231) ^ 0x7FEE5266C23F4455L), (float)ModuleNametags.width.getValue().doubleValue(), ModuleColor.getColor());
        }
        GlStateManager.disableBlend();
        Europa.FONT_MANAGER.drawString(displayTag, -width, -(Europa.FONT_MANAGER.getHeight() - Float.intBitsToFloat(Float.floatToIntBits(4.2953334f) ^ 0x7F09735F)), new Color(-5592406));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset((float)Float.intBitsToFloat(Float.floatToIntBits(7.1674623f) ^ 0x7F655BDA), (float)Float.intBitsToFloat(Float.floatToIntBits(7.4100535E-6f) ^ 0x7F4FB8E0));
        GlStateManager.popMatrix();
    }

    /*
     * WARNING - void declaration
     */
    public double interpolate(double d, double d2, float f) {
        void delta;
        void current;
        void previous;
        return (double)(previous + (current - previous) * (double)delta);
    }

    /*
     * WARNING - void declaration
     */
    public static boolean lambda$onJoin$2(EventPlayerJoin eventPlayerJoin, LogoutPos logoutPos) {
        EventPlayerJoin event;
        void pos;
        return pos.getName().equalsIgnoreCase(event.getName());
    }

    /*
     * WARNING - void declaration
     */
    public boolean lambda$onUpdate$1(LogoutPos logoutPos) {
        void spot;
        return ModuleLogoutSpots.mc.player.getDistanceSq((Entity)spot.getEntity()) >= (double)MathUtils.square(range.getValue().floatValue());
    }

    /*
     * WARNING - void declaration
     */
    public void lambda$onRender3D$0(EventRender3D eventRender3D, LogoutPos logoutPos) {
        block0: {
            void event;
            void spot;
            if (spot.getEntity() == null) break block0;
            this.bb = RenderUtils.interpolateAxis(spot.getEntity().getEntityBoundingBox());
            RenderUtils.drawBlockOutline(this.bb, new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), 255), Float.intBitsToFloat(Float.floatToIntBits(9.055419f) ^ 0x7E90E2FF));
            this.x = this.interpolate(spot.getEntity().lastTickPosX, spot.getEntity().posX, event.getPartialTicks()) - ModuleLogoutSpots.mc.getRenderManager().renderPosX;
            this.y = this.interpolate(spot.getEntity().lastTickPosY, spot.getEntity().posY, event.getPartialTicks()) - ModuleLogoutSpots.mc.getRenderManager().renderPosY;
            this.z = this.interpolate(spot.getEntity().lastTickPosZ, spot.getEntity().posZ, event.getPartialTicks()) - ModuleLogoutSpots.mc.getRenderManager().renderPosZ;
            this.renderNameTag(spot.getName(), this.x, this.y, this.z, event.getPartialTicks(), spot.getX(), spot.getY(), spot.getZ());
        }
    }

    static {
        $assertionsDisabled = !ModuleLogoutSpots.class.desiredAssertionStatus();
        range = new ValueNumber("Range", "Range", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.023362359f) ^ 0x7F29626B)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.039723963f) ^ 0x7F6AB598)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.029179262f) ^ 0x7F150959)));
        coords = new ValueBoolean("Coords", "Coords", "", true);
        message = new ValueBoolean("Message", "Message", "", false);
        syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
        daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    }

    private static class LogoutPos {
        public String name;
        public UUID uuid;
        public EntityPlayer entity;
        public double x;
        public double y;
        public double z;

        public LogoutPos(String name, UUID uuid, EntityPlayer entity) {
            this.name = name;
            this.uuid = uuid;
            this.entity = entity;
            this.x = entity.posX;
            this.y = entity.posY;
            this.z = entity.posZ;
        }

        public String getName() {
            return this.name;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public EntityPlayer getEntity() {
            return this.entity;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }

        public double getZ() {
            return this.z;
        }
    }
}

