/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.EntityUtils;
import com.europa.api.utilities.render.RenderUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ModuleESP
extends Module {
    public static ValueBoolean players = new ValueBoolean("Players", "Players", "", false);
    public static ValueBoolean mobs = new ValueBoolean("Mobs", "Mobs", "", false);
    public static ValueBoolean animals = new ValueBoolean("Animals", "Animals", "", false);
    public static ValueBoolean others = new ValueBoolean("Others", "Others", "", false);
    public static ValueBoolean chorus = new ValueBoolean("Chorus", "Chorus", "", false);
    public static ValueNumber chorusFadeSpeed = new ValueNumber("ChorusFadeSpeed", "ChorusFadeSpeed", "", 200, 10, 500);
    public static ValueBoolean items = new ValueBoolean("Items", "Items", "", false);
    public static ValueBoolean itemNames = new ValueBoolean("ItemNames", "ItemNames", "", false);
    public static ValueBoolean xpbottles = new ValueBoolean("Xp", "Xp", "", false);
    public static ValueBoolean xporbs = new ValueBoolean("XpOrbs", "XpOrbs", "", false);
    public static ValueBoolean pearls = new ValueBoolean("Pearls", "Pearls", "", false);
    public static ValueNumber itemAlpha = new ValueNumber("ItemAlpha", "ItemAlpha", "", 180, 0, 255);
    public static ValueNumber width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(0.4081298114441702) ^ 0x7FD21ECC802B390FL), Double.longBitsToDouble(Double.doubleToLongBits(3.1746221269585098) ^ 0x7FE965A049238EDEL), Double.longBitsToDouble(Double.doubleToLongBits(1.198681145276213) ^ 0x7FE72DCC47D4B0C8L));
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public HashMap<BlockPos, Sound> chorusPositions = new HashMap();
    public List<BlockPos> eatingPositions = new ArrayList<BlockPos>();
    public static Color color = daColor.getValue();

    public ModuleESP() {
        super("ESP", "ESP", "Draws boxes around certain entities or events.", ModuleCategory.RENDER);
    }

    @Override
    public void onUpdate() {
        color = syncColor.getValue() ? ModuleESP.globalColor(255) : daColor.getValue();
        for (EntityPlayer player : ModuleESP.mc.world.playerEntities) {
            if (!(player.getHeldItemMainhand().getItem() instanceof ItemChorusFruit) || !player.isHandActive()) continue;
            BlockPos pos = new BlockPos(Math.floor(player.posX), player.posY, Math.floor(player.posZ));
            this.eatingPositions.add(pos);
        }
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onReceive(EventPacket.Receive receive) {
        block2: {
            SPacketSoundEffect packet;
            block3: {
                void event;
                if (!(event.getPacket() instanceof SPacketSoundEffect)) break block2;
                packet = (SPacketSoundEffect)event.getPacket();
                if (packet.getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) break block3;
                if (packet.getSound() != SoundEvents.ENTITY_ENDERMEN_TELEPORT) break block2;
            }
            BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
            this.chorusPositions.put(pos, new Sound(this, pos));
            this.chorusPositions.get((Object)pos).starTime = System.currentTimeMillis();
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void drawText(BlockPos blockPos, String string, int n) {
        void alpha;
        void text;
        BlockPos pos;
        if (pos == null || text == null) {
            return;
        }
        GlStateManager.pushMatrix();
        RenderUtils.glBillboardDistanceScaled((float)pos.getX() + Float.intBitsToFloat(Float.floatToIntBits(3.801634f) ^ 0x7F734DF9), (float)pos.getY() + Float.intBitsToFloat(Float.floatToIntBits(3.1258135f) ^ 0x7F480D54), (float)pos.getZ() + Float.intBitsToFloat(Float.floatToIntBits(3.872001f) ^ 0x7F77CEDD), (EntityPlayer)ModuleESP.mc.player, Float.intBitsToFloat(Float.floatToIntBits(13.906509f) ^ 0x7E6DB223));
        GlStateManager.disableDepth();
        GlStateManager.translate((double)(-((double)Europa.FONT_MANAGER.getStringWidth((String)text) / Double.longBitsToDouble(Double.doubleToLongBits(0.081734107416205) ^ 0x7FB4EC86C652028FL))), (double)Double.longBitsToDouble(Double.doubleToLongBits(8.635002465843543E307) ^ 0x7FDEBDDB69696349L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.5280009210702316E308) ^ 0x7FEB330695F9CB87L));
        Europa.FONT_MANAGER.drawString((String)text, Float.intBitsToFloat(Float.floatToIntBits(2.4994416E37f) ^ 0x7D966DFF), Float.intBitsToFloat(Float.floatToIntBits(2.6550794E38f) ^ 0x7F47BEF8), new Color(195, 54, 252, (int)alpha));
        GlStateManager.popMatrix();
    }

    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        Color rainbowColor2;
        AxisAlignedBB bb;
        if (ModuleESP.mc.player == null || ModuleESP.mc.world == null) {
            return;
        }
        for (Map.Entry<BlockPos, Sound> entry : this.chorusPositions.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().starTime >= (long)(chorusFadeSpeed.getValue().intValue() * 10)) continue;
            int opacity = 5;
            long time = System.currentTimeMillis();
            long duration = time - entry.getValue().starTime;
            int minusO = MathHelper.clamp((int)((int)((float)duration / (float)(chorusFadeSpeed.getValue().intValue() * 10) * Float.intBitsToFloat(Float.floatToIntBits(0.011263163f) ^ 0x7F478921))), (int)0, (int)250);
            if (duration < (long)(chorusFadeSpeed.getValue().intValue() * 10)) {
                opacity = 255 - minusO;
            }
            ModuleESP.drawText(entry.getValue().pos, "Player teleports", opacity);
        }
        for (Entity entity : ModuleESP.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityItem) || !(ModuleESP.mc.player.getDistanceSq(entity) < Double.longBitsToDouble(Double.doubleToLongBits(7.650115203161708E-5) ^ 0x7FB785E7C8C9C047L))) continue;
            if (itemNames.getValue()) {
                this.drawText(entity);
            }
            if (!items.getValue()) continue;
            int i = 0;
            Vec3d interp = EntityUtils.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
            bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - Double.longBitsToDouble(Double.doubleToLongBits(49.95357121497192) ^ 0x7FE163970686C295L) - entity.posX + interp.x, entity.getEntityBoundingBox().minY - Double.longBitsToDouble(Double.doubleToLongBits(1.4432660015114346E308) ^ 0x7FE9B0E49054A47BL) - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - Double.longBitsToDouble(Double.doubleToLongBits(23.489968617922518) ^ 0x7F9EE4F70CCF92BFL) - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + Double.longBitsToDouble(Double.doubleToLongBits(392.2515215363747) ^ 0x7FD11D9FA2EBD61BL) - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + Double.longBitsToDouble(Double.doubleToLongBits(74.84317836478473) ^ 0x7FEB2C6F3BFAC5E4L) - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + Double.longBitsToDouble(Double.doubleToLongBits(57.29299485563897) ^ 0x7FE53C194364F757L) - entity.posZ + interp.z);
            Color rainbowColor1 = new Color(color.getRed(), color.getGreen(), color.getBlue());
            rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask((boolean)false);
            GL11.glEnable((int)2848);
            GL11.glHint((int)3154, (int)4354);
            GL11.glLineWidth((float)Float.intBitsToFloat(Float.floatToIntBits(6.5253186f) ^ 0x7F50CF69));
            RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.01141853f) ^ 0x7F4414C9)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.014998874f) ^ 0x7F0ABDD6)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.06195764f) ^ 0x7E02C74B)), (float)((float)itemAlpha.getValue().intValue() / Float.intBitsToFloat(Float.floatToIntBits(0.012247075f) ^ 0x7F37A7F5)));
            GL11.glDisable((int)2848);
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            RenderUtils.drawBlockOutline(bb, new Color(rainbowColor2.getRed(), rainbowColor2.getGreen(), rainbowColor2.getBlue(), 255), Float.intBitsToFloat(Float.floatToIntBits(8.158364f) ^ 0x7E8288A9));
            if (++i < 50) continue;
            break;
        }
        if (xporbs.getValue()) {
            int i = 0;
            for (Entity entity : ModuleESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityXPOrb) || !(ModuleESP.mc.player.getDistanceSq(entity) < Double.longBitsToDouble(Double.doubleToLongBits(0.005055479838038055) ^ 0x7FD73D0E091E8F3DL))) continue;
                Vec3d interp = EntityUtils.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - Double.longBitsToDouble(Double.doubleToLongBits(356.35985567376855) ^ 0x7FDFDC58619C7841L) - entity.posX + interp.x, entity.getEntityBoundingBox().minY - Double.longBitsToDouble(Double.doubleToLongBits(3.0202868340707697E307) ^ 0x7FC58151E8EB5B53L) - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - Double.longBitsToDouble(Double.doubleToLongBits(37.19330009192545) ^ 0x7FEB0127972B0880L) - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + Double.longBitsToDouble(Double.doubleToLongBits(33.82966635409238) ^ 0x7FE973AB18492C16L) - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + Double.longBitsToDouble(Double.doubleToLongBits(249.5446896389229) ^ 0x7FD6A8F7816EAD01L) - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + Double.longBitsToDouble(Double.doubleToLongBits(45.45284601601803) ^ 0x7FEF206F422FEC74L) - entity.posZ + interp.z);
                Color rainbowColor1 = new Color(color.getRed(), color.getGreen(), color.getBlue());
                rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)Float.intBitsToFloat(Float.floatToIntBits(14.362411f) ^ 0x7EE5CC6F));
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.013531744f) ^ 0x7F22B43F)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.083207674f) ^ 0x7ED568C9)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.009101371f) ^ 0x7F6A1DEB)), (float)((float)itemAlpha.getValue().intValue() / Float.intBitsToFloat(Float.floatToIntBits(0.0091495365f) ^ 0x7F6AE7F0)));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtils.drawBlockOutline(bb, new Color(rainbowColor2.getRed(), rainbowColor2.getGreen(), rainbowColor2.getBlue(), 255), Float.intBitsToFloat(Float.floatToIntBits(25.987982f) ^ 0x7E4FE763));
                if (++i < 50) continue;
                break;
            }
        }
        if (pearls.getValue()) {
            int i = 0;
            for (Entity entity : ModuleESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderPearl)) continue;
                if (!(ModuleESP.mc.player.getDistanceSq(entity) < Double.longBitsToDouble(Double.doubleToLongBits(9.716324116734731E-4) ^ 0x7FEC5EA4B7478E00L))) continue;
                Vec3d interp = EntityUtils.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - Double.longBitsToDouble(Double.doubleToLongBits(404.60787320059575) ^ 0x7FD0D02040A6535DL) - entity.posX + interp.x, entity.getEntityBoundingBox().minY - Double.longBitsToDouble(Double.doubleToLongBits(9.793275082382436E307) ^ 0x7FE16EBF6AE4680AL) - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - Double.longBitsToDouble(Double.doubleToLongBits(302.88108197975447) ^ 0x7FDB778170F29BA5L) - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + Double.longBitsToDouble(Double.doubleToLongBits(32.39362720990923) ^ 0x7FE9ABFBF9C52987L) - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + Double.longBitsToDouble(Double.doubleToLongBits(469.8433793070274) ^ 0x7FC4C4E7E2D5457FL) - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + Double.longBitsToDouble(Double.doubleToLongBits(35.35264247771337) ^ 0x7FE834BAFA1BE351L) - entity.posZ + interp.z);
                Color rainbowColor1 = new Color(color.getRed(), color.getGreen(), color.getBlue());
                rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)Float.intBitsToFloat(Float.floatToIntBits(4.2904253f) ^ 0x7F094B2A));
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.07456594f) ^ 0x7EE7B607)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.015339391f) ^ 0x7F045212)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.12467239f) ^ 0x7E80543D)), (float)((float)itemAlpha.getValue().intValue() / Float.intBitsToFloat(Float.floatToIntBits(0.013501829f) ^ 0x7F2236C6)));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtils.drawBlockOutline(bb, new Color(rainbowColor2.getRed(), rainbowColor2.getGreen(), rainbowColor2.getBlue(), 255), Float.intBitsToFloat(Float.floatToIntBits(764.7421f) ^ 0x7BBF2F7F));
                if (++i < 50) continue;
                break;
            }
        }
        if (xpbottles.getValue()) {
            int i = 0;
            for (Entity entity : ModuleESP.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityExpBottle) || !(ModuleESP.mc.player.getDistanceSq(entity) < Double.longBitsToDouble(Double.doubleToLongBits(7.772014863802573E-4) ^ 0x7FEAFFA37BB32F61L))) continue;
                Vec3d interp = EntityUtils.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - Double.longBitsToDouble(Double.doubleToLongBits(140.35616308474667) ^ 0x7FC812FC29B9B96BL) - entity.posX + interp.x, entity.getEntityBoundingBox().minY - Double.longBitsToDouble(Double.doubleToLongBits(3.6629299267265153E307) ^ 0x7FCA14B79E6E4803L) - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - Double.longBitsToDouble(Double.doubleToLongBits(52.027045789921985) ^ 0x7FE39AEFA51E0354L) - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + Double.longBitsToDouble(Double.doubleToLongBits(37.85314789450246) ^ 0x7FEB74AA6AD95C93L) - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + Double.longBitsToDouble(Double.doubleToLongBits(5.397919932440449) ^ 0x7FAC0EE1CB3891EFL) - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + Double.longBitsToDouble(Double.doubleToLongBits(277.79693241661215) ^ 0x7FD8C559A5AD3E11L) - entity.posZ + interp.z);
                Color rainbowColor1 = new Color(color.getRed(), color.getGreen(), color.getBlue());
                rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)Float.intBitsToFloat(Float.floatToIntBits(15.309398f) ^ 0x7EF4F34B));
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb, (float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.05234645f) ^ 0x7E29693B)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.013357414f) ^ 0x7F25D90E)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.011055276f) ^ 0x7F4A2130)), (float)((float)itemAlpha.getValue().intValue() / Float.intBitsToFloat(Float.floatToIntBits(0.013524949f) ^ 0x7F2297BF)));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                RenderUtils.drawBlockOutline(bb, new Color(rainbowColor2.getRed(), rainbowColor2.getGreen(), rainbowColor2.getBlue(), 255), Float.intBitsToFloat(Float.floatToIntBits(4.4187307f) ^ 0x7F0D663E));
                if (++i < 50) continue;
                break;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void drawText(Entity entity) {
        void entityIn;
        if (ModuleESP.mc.player == null || ModuleESP.mc.world == null || ModuleESP.mc.getRenderManager().options == null) {
            return;
        }
        GlStateManager.pushMatrix();
        double scale = Double.longBitsToDouble(Double.doubleToLongBits(7.544489495959348) ^ 0x7FEE2D8EA788A4C9L);
        String name = entityIn instanceof EntityItem ? ((EntityItem)entityIn).getItem().getDisplayName() : (entityIn instanceof EntityEnderPearl ? "Thrown Ender Pearl" : (entityIn instanceof EntityExpBottle ? "Thrown Exp Bottle" : "null"));
        Vec3d interp = EntityUtils.getInterpolatedRenderPos((Entity)entityIn, mc.getRenderPartialTicks());
        float yAdd = entityIn.height / Float.intBitsToFloat(Float.floatToIntBits(0.75474775f) ^ 0x7F413726) + Float.intBitsToFloat(Float.floatToIntBits(3.058384f) ^ 0x7F43BC90);
        double x = interp.x;
        double y = interp.y + (double)yAdd;
        double z = interp.z;
        float viewerYaw = ModuleESP.mc.getRenderManager().playerViewY;
        float viewerPitch = ModuleESP.mc.getRenderManager().playerViewX;
        boolean isThirdPersonFrontal = ModuleESP.mc.getRenderManager().options.thirdPersonView == 2;
        GlStateManager.translate((double)x, (double)y, (double)z);
        GlStateManager.rotate((float)(-viewerYaw), (float)Float.intBitsToFloat(Float.floatToIntBits(2.4975824E38f) ^ 0x7F3BE5B0), (float)Float.intBitsToFloat(Float.floatToIntBits(5.8653016f) ^ 0x7F3BB08D), (float)Float.intBitsToFloat(Float.floatToIntBits(2.025141E38f) ^ 0x7F185ACC));
        GlStateManager.rotate((float)((float)(isThirdPersonFrontal ? -1 : 1) * viewerPitch), (float)Float.intBitsToFloat(Float.floatToIntBits(11.825183f) ^ 0x7EBD33F3), (float)Float.intBitsToFloat(Float.floatToIntBits(1.8907396E38f) ^ 0x7F0E3E52), (float)Float.intBitsToFloat(Float.floatToIntBits(6.16727E37f) ^ 0x7E3996EB));
        float f = ModuleESP.mc.player.getDistance((Entity)entityIn);
        float m = f / Float.intBitsToFloat(Float.floatToIntBits(1.0004f) ^ 0x7E800D1B) * (float)Math.pow(Double.longBitsToDouble(Double.doubleToLongBits(4.8110394395124665) ^ 0x7FE71A0E1F71E38CL), Double.longBitsToDouble(Double.doubleToLongBits(4.67944022789239) ^ 0x7FE2B7BF2DD989D5L));
        GlStateManager.scale((float)m, (float)m, (float)m);
        FontRenderer fontRendererIn = ModuleESP.mc.fontRenderer;
        GlStateManager.scale((float)Float.intBitsToFloat(Float.floatToIntBits(-357.211f) ^ 0x7F7E57CF), (float)Float.intBitsToFloat(Float.floatToIntBits(-452.38806f) ^ 0x7F2EFD61), (float)Float.intBitsToFloat(Float.floatToIntBits(392.9144f) ^ 0x7F08B9C6));
        String str = name + (entityIn instanceof EntityItem ? " x" + ((EntityItem)entityIn).getItem().getCount() : "");
        int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glNormal3f((float)Float.intBitsToFloat(Float.floatToIntBits(1.9863452E38f) ^ 0x7F156F9E), (float)Float.intBitsToFloat(Float.floatToIntBits(99.03271f) ^ 0x7D4610BF), (float)Float.intBitsToFloat(Float.floatToIntBits(3.0166113E38f) ^ 0x7F62F1D2));
        if (Europa.getModuleManager().isModuleEnabled("Font")) {
            Europa.FONT_MANAGER.drawString(str, -i, Float.intBitsToFloat(Float.floatToIntBits(1.7880019f) ^ 0x7EF4DD3F), Color.WHITE);
        } else {
            GlStateManager.enableTexture2D();
            fontRendererIn.drawStringWithShadow(str, (float)(-i), Float.intBitsToFloat(Float.floatToIntBits(0.19404618f) ^ 0x7F56B40B), Color.WHITE.getRGB());
            GlStateManager.disableTexture2D();
        }
        GlStateManager.glNormal3f((float)Float.intBitsToFloat(Float.floatToIntBits(1.4840141E38f) ^ 0x7EDF4A25), (float)Float.intBitsToFloat(Float.floatToIntBits(2.7919246E38f) ^ 0x7F520A83), (float)Float.intBitsToFloat(Float.floatToIntBits(1.5804284E38f) ^ 0x7EEDCBE3));
        GlStateManager.popMatrix();
    }

    public class Sound {
        public BlockPos pos;
        public long starTime;
        public ModuleESP this$0;

        public Sound(ModuleESP this$0, BlockPos pos) {
            this.this$0 = this$0;
            this.pos = pos;
            this.starTime = (long)292408538 ^ 0x116DCCDAL;
        }
    }
}

