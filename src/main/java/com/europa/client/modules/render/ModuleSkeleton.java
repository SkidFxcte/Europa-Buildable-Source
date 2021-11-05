/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import java.awt.Color;
import java.util.HashMap;

import com.europa.client.mixins.impl.MixinPlayerModel;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class ModuleSkeleton
extends Module {
    public static ValueNumber width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(0.14556271208512925) ^ 0x7FCAA1CC87F61AD7L), Double.longBitsToDouble(Double.doubleToLongBits(29.175753760532427) ^ 0x7FDD2CFE32CDA2C3L), Double.longBitsToDouble(Double.doubleToLongBits(0.15918730500218467) ^ 0x7FD0603FE6761D03L));
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
    public Color color;
    public ICamera camera = new Frustum();
    public static HashMap<EntityPlayer, float[][]> entities = new HashMap();

    public ModuleSkeleton() {
        super("Skeleton", "Skeleton", "Draws the player skeletons.", ModuleCategory.RENDER);
    }

    /*
     * WARNING - void declaration
     */
    public Vec3d getVec3(EventRender3D eventRender3D, EntityPlayer entityPlayer) {
        void e;
        void event;
        float pt = event.getPartialTicks();
        double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)pt;
        double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)pt;
        double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)pt;
        return new Vec3d(x, y, z);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        void event;
        this.color = syncColor.getValue() ? ModuleSkeleton.globalColor(255) : daColor.getValue();
        if (mc.getRenderManager() == null || ModuleSkeleton.mc.getRenderManager().options == null) {
            return;
        }
        this.startEnd(true);
        GL11.glEnable((int)2903);
        GL11.glDisable((int)2848);
        entities.keySet().removeIf(this::doesntContain);
        ModuleSkeleton.mc.world.playerEntities.forEach(arg_0 -> this.lambda$onRender3D$0((EventRender3D)event, arg_0));
        Gui.drawRect((int)0, (int)0, (int)0, (int)0, (int)0);
        this.startEnd(false);
    }

    /*
     * WARNING - void declaration
     */
    public void drawSkeleton(EventRender3D eventRender3D, EntityPlayer entityPlayer) {
        block13: {
            void e;
            void event;
            Color rainbowColor1 = new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
            Color rainbowColor2 = new Color(rainbowColor1.getRed(), rainbowColor1.getGreen(), rainbowColor1.getBlue());
            double d3 = ModuleSkeleton.mc.player.lastTickPosX + (ModuleSkeleton.mc.player.posX - ModuleSkeleton.mc.player.lastTickPosX) * (double)event.getPartialTicks();
            double d4 = ModuleSkeleton.mc.player.lastTickPosY + (ModuleSkeleton.mc.player.posY - ModuleSkeleton.mc.player.lastTickPosY) * (double)event.getPartialTicks();
            double d5 = ModuleSkeleton.mc.player.lastTickPosZ + (ModuleSkeleton.mc.player.posZ - ModuleSkeleton.mc.player.lastTickPosZ) * (double)event.getPartialTicks();
            this.camera.setPosition(d3, d4, d5);
            float[][] entPos = entities.get(e);
            if (entPos == null || !e.isEntityAlive() || !this.camera.isBoundingBoxInFrustum(e.getEntityBoundingBox()) || e.isDead || e == ModuleSkeleton.mc.player || e.isPlayerSleeping()) break block13;
            GL11.glPushMatrix();
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)width.getValue().floatValue());
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.015047341f) ^ 0x7F09891F)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.06605535f) ^ 0x7EF84807)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.05479559f) ^ 0x7E1F7157)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.034987163f) ^ 0x7E704EB3)));
            Vec3d vec = this.getVec3((EventRender3D)event, (EntityPlayer)e);
            double x = vec.x - ModuleSkeleton.mc.getRenderManager().renderPosX;
            double y = vec.y - ModuleSkeleton.mc.getRenderManager().renderPosY;
            double z = vec.z - ModuleSkeleton.mc.getRenderManager().renderPosZ;
            GL11.glTranslated((double)x, (double)y, (double)z);
            float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
            GL11.glRotatef((float)(-xOff), (float)Float.intBitsToFloat(Float.floatToIntBits(2.5212027E38f) ^ 0x7F3DAC99), (float)Float.intBitsToFloat(Float.floatToIntBits(4.0520544f) ^ 0x7F01AA6E), (float)Float.intBitsToFloat(Float.floatToIntBits(2.1504826E38f) ^ 0x7F21C8CA));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(5.403704484787623E307) ^ 0x7FD33CE23340398BL), (double)Double.longBitsToDouble(Double.doubleToLongBits(6.975745800790336E307) ^ 0x7FD8D5A07123B453L), (double)(e.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(-15.735331150361763) ^ 0x7FE16C07B252BF46L) : Double.longBitsToDouble(Double.doubleToLongBits(1.386368129785723E308) ^ 0x7FE8AD9CABF64806L)));
            float yOff = e.isSneaking() ? Float.intBitsToFloat(Float.floatToIntBits(28.226748f) ^ 0x7EF849FB) : Float.intBitsToFloat(Float.floatToIntBits(25.704882f) ^ 0x7E8DA399);
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.009037509f) ^ 0x7F6B120F)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.11893516f) ^ 0x7E8C9447)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.061793204f) ^ 0x7E021ADF)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.011555503f) ^ 0x7F42534B)));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(-10.863209970959511) ^ 0x7FE5B9F6A845B455L), (double)yOff, (double)Double.longBitsToDouble(Double.doubleToLongBits(1.3187476740493592E308) ^ 0x7FE77978086056AFL));
            if (entPos[3][0] != Float.intBitsToFloat(Float.floatToIntBits(2.0100574E38f) ^ 0x7F17384C)) {
                GL11.glRotatef((float)(entPos[3][0] * Float.intBitsToFloat(Float.floatToIntBits(0.041616887f) ^ 0x7F4F5898)), (float)Float.intBitsToFloat(Float.floatToIntBits(9.539592f) ^ 0x7E98A22B), (float)Float.intBitsToFloat(Float.floatToIntBits(9.412339E37f) ^ 0x7E8D9F03), (float)Float.intBitsToFloat(Float.floatToIntBits(2.0130423E38f) ^ 0x7F1771C9));
            }
            if (entPos[3][1] != Float.intBitsToFloat(Float.floatToIntBits(3.4080895E37f) ^ 0x7DCD1DEF)) {
                GL11.glRotatef((float)(entPos[3][1] * Float.intBitsToFloat(Float.floatToIntBits(0.058424532f) ^ 0x7F0A6070)), (float)Float.intBitsToFloat(Float.floatToIntBits(9.81228E37f) ^ 0x7E93A387), (float)Float.intBitsToFloat(Float.floatToIntBits(10.586139f) ^ 0x7EA960D3), (float)Float.intBitsToFloat(Float.floatToIntBits(1.3178267E38f) ^ 0x7EC648D7));
            }
            if (entPos[3][2] != Float.intBitsToFloat(Float.floatToIntBits(2.0799055E38f) ^ 0x7F1C7986)) {
                GL11.glRotatef((float)(entPos[3][2] * Float.intBitsToFloat(Float.floatToIntBits(0.011649883f) ^ 0x7E5BF1C7)), (float)Float.intBitsToFloat(Float.floatToIntBits(1.977332E36f) ^ 0x7BBE68FF), (float)Float.intBitsToFloat(Float.floatToIntBits(1.1501261E37f) ^ 0x7D0A70FF), (float)Float.intBitsToFloat(Float.floatToIntBits(28.276106f) ^ 0x7E623577));
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(3.0593869048744946E307) ^ 0x7FC5C8973F22A30BL), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.6765039260532387E307) ^ 0x7FB7DFCC1ED6AEC7L), (double)Double.longBitsToDouble(Double.doubleToLongBits(2.408126638601064E307) ^ 0x7FC1257C7D82D207L));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(1.6002990055222894E308) ^ 0x7FEC7C7C0CFC61C8L), (double)(-yOff), (double)Double.longBitsToDouble(Double.doubleToLongBits(4.693072346115757E306) ^ 0x7F9ABB8D78CB619FL));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.009661949f) ^ 0x7F614D27)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.011697022f) ^ 0x7F40A4DE)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.008632781f) ^ 0x7F727082)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.012844812f) ^ 0x7F2D730C)));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(7.248792447347383) ^ 0x7FDCFEC37283652FL), (double)yOff, (double)Double.longBitsToDouble(Double.doubleToLongBits(1.1021999010924816E308) ^ 0x7FE39EABD5C32BCBL));
            if (entPos[4][0] != Float.intBitsToFloat(Float.floatToIntBits(5.92441E37f) ^ 0x7E3247FF)) {
                GL11.glRotatef((float)(entPos[4][0] * Float.intBitsToFloat(Float.floatToIntBits(0.05108233f) ^ 0x7F341555)), (float)Float.intBitsToFloat(Float.floatToIntBits(5.9425664f) ^ 0x7F3E2981), (float)Float.intBitsToFloat(Float.floatToIntBits(1.2563656E38f) ^ 0x7EBD0971), (float)Float.intBitsToFloat(Float.floatToIntBits(2.931665E38f) ^ 0x7F5C8DD0));
            }
            if (entPos[4][1] != Float.intBitsToFloat(Float.floatToIntBits(3.7476374E37f) ^ 0x7DE18D7F)) {
                GL11.glRotatef((float)(entPos[4][1] * Float.intBitsToFloat(Float.floatToIntBits(0.023042025f) ^ 0x7ED9EC41)), (float)Float.intBitsToFloat(Float.floatToIntBits(7.0361945E37f) ^ 0x7E53BCDF), (float)Float.intBitsToFloat(Float.floatToIntBits(4.5691776f) ^ 0x7F1236B4), (float)Float.intBitsToFloat(Float.floatToIntBits(1.4147917E38f) ^ 0x7ED4DFCB));
            }
            if (entPos[4][2] != Float.intBitsToFloat(Float.floatToIntBits(1.7616133E38f) ^ 0x7F048770)) {
                GL11.glRotatef((float)(entPos[4][2] * Float.intBitsToFloat(Float.floatToIntBits(0.05863024f) ^ 0x7F1508A3)), (float)Float.intBitsToFloat(Float.floatToIntBits(1.013535E38f) ^ 0x7E987FF3), (float)Float.intBitsToFloat(Float.floatToIntBits(2.424888E38f) ^ 0x7F366DA5), (float)Float.intBitsToFloat(Float.floatToIntBits(5.7490005f) ^ 0x7F37F7D0));
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(8.904524868206134E307) ^ 0x7FDFB37F465F178DL), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.0737792661280161E308) ^ 0x7FE31D28E77B51C1L), (double)Double.longBitsToDouble(Double.doubleToLongBits(5.458980625703218E307) ^ 0x7FD36F4300C5F6BBL));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(8.43429235345954E307) ^ 0x7FDE06EE8CE228D5L), (double)(-yOff), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.4447823279672625E308) ^ 0x7FE9B7CD7AABE31CL));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(1.3898846411191587E308) ^ 0x7FE8BDA2F63B389AL), (double)Double.longBitsToDouble(Double.doubleToLongBits(2.6685986921430544E307) ^ 0x7FC30045092F8283L), (double)(e.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(24.10427535694698) ^ 0x7FE81AB1CA2FDDB3L) : Double.longBitsToDouble(Double.doubleToLongBits(4.33163559866041E306) ^ 0x7F98AC7F2A466F9FL)));
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(1.0829123f) ^ 0x7CF59CDF)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.01017444f) ^ 0x7F59B2B2)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.12338073f) ^ 0x7E83AF09)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.10786671f) ^ 0x7EA3E939)));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(8.327752977088419E307) ^ 0x7FDDA5D538274C01L), (double)(e.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(-48.3631436118739) ^ 0x7FE1B7E2E4F1C66DL) : Double.longBitsToDouble(Double.doubleToLongBits(4.095139466530242E307) ^ 0x7FCD288A56A1B05FL)), (double)(e.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(-287.9140003824583) ^ 0x7FE0576459B1F84EL) : Double.longBitsToDouble(Double.doubleToLongBits(1.137474257946624E308) ^ 0x7FE43F6A2D6A8A18L)));
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.010024469f) ^ 0x7F5B3DAC)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.008016541f) ^ 0x7F7C57CF)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.1191785f) ^ 0x7E8B13DB)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.10189811f) ^ 0x7EAFAFF5)));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(-3.429055130747163) ^ 0x7FD36EB474D5EBA9L), (double)((double)yOff + Double.longBitsToDouble(Double.doubleToLongBits(3.753204877258891) ^ 0x7FEF9F09DEC1846DL)), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.2983645615952552E307) ^ 0x7FB27D4468AD1027L));
            if (entPos[1][0] != Float.intBitsToFloat(Float.floatToIntBits(1.3659332E38f) ^ 0x7ECD85D5)) {
                GL11.glRotatef((float)(entPos[1][0] * Float.intBitsToFloat(Float.floatToIntBits(1.8893174f) ^ 0x7D94FBC7)), (float)Float.intBitsToFloat(Float.floatToIntBits(8.663449f) ^ 0x7E8A9D7D), (float)Float.intBitsToFloat(Float.floatToIntBits(1.7195081E37f) ^ 0x7D4EFA6F), (float)Float.intBitsToFloat(Float.floatToIntBits(1.3876011E38f) ^ 0x7ED0C873));
            }
            if (entPos[1][1] != Float.intBitsToFloat(Float.floatToIntBits(5.4029596E37f) ^ 0x7E2296E3)) {
                GL11.glRotatef((float)(entPos[1][1] * Float.intBitsToFloat(Float.floatToIntBits(0.04391093f) ^ 0x7F56F513)), (float)Float.intBitsToFloat(Float.floatToIntBits(7.9617316E37f) ^ 0x7E6F96F3), (float)Float.intBitsToFloat(Float.floatToIntBits(7.7038307f) ^ 0x7F7685C8), (float)Float.intBitsToFloat(Float.floatToIntBits(1.269066E38f) ^ 0x7EBEF2A5));
            }
            if (entPos[1][2] != Float.intBitsToFloat(Float.floatToIntBits(2.175801E38f) ^ 0x7F23B067)) {
                GL11.glRotatef((float)(-entPos[1][2] * Float.intBitsToFloat(Float.floatToIntBits(0.020110471f) ^ 0x7EC19057)), (float)Float.intBitsToFloat(Float.floatToIntBits(6.9123664E37f) ^ 0x7E5002EF), (float)Float.intBitsToFloat(Float.floatToIntBits(3.3328502E38f) ^ 0x7F7ABC5C), (float)Float.intBitsToFloat(Float.floatToIntBits(7.759493f) ^ 0x7F784DC4));
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(4.732760092718761E307) ^ 0x7FD0D96433D11D55L), (double)Double.longBitsToDouble(Double.doubleToLongBits(5.378738149206139E307) ^ 0x7FD326212832F1E3L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.0206556537129657E308) ^ 0x7FE22B13FC5C905DL));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(7.604876727198813E307) ^ 0x7FDB1302B173DBF1L), (double)Double.longBitsToDouble(Double.doubleToLongBits(-2.7737327586502376) ^ 0x7FE6309ACCF1F91AL), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.6115861269832912E308) ^ 0x7FECAFEB60209AF7L));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(2.6478218094581627) ^ 0x7FDD2EBD336A111BL), (double)((double)yOff + Double.longBitsToDouble(Double.doubleToLongBits(3.6852617122610614) ^ 0x7FECE2F3E7837C4EL)), (double)Double.longBitsToDouble(Double.doubleToLongBits(8.810287561942942E306) ^ 0x7FA917B02B86991FL));
            if (entPos[2][0] != Float.intBitsToFloat(Float.floatToIntBits(6.559331E35f) ^ 0x7AFCA7FF)) {
                GL11.glRotatef((float)(entPos[2][0] * Float.intBitsToFloat(Float.floatToIntBits(0.035199642f) ^ 0x7F750360)), (float)Float.intBitsToFloat(Float.floatToIntBits(7.208206f) ^ 0x7F66A9A0), (float)Float.intBitsToFloat(Float.floatToIntBits(2.7231143E38f) ^ 0x7F4CDD46), (float)Float.intBitsToFloat(Float.floatToIntBits(2.3060684E38f) ^ 0x7F2D7D43));
            }
            if (entPos[2][1] != Float.intBitsToFloat(Float.floatToIntBits(1.7475675E38f) ^ 0x7F0378ED)) {
                GL11.glRotatef((float)(entPos[2][1] * Float.intBitsToFloat(Float.floatToIntBits(0.057636224f) ^ 0x7F093D16)), (float)Float.intBitsToFloat(Float.floatToIntBits(1.756426E38f) ^ 0x7F042389), (float)Float.intBitsToFloat(Float.floatToIntBits(4.124543f) ^ 0x7F03FC42), (float)Float.intBitsToFloat(Float.floatToIntBits(2.1026072E38f) ^ 0x7F1E2EBE));
            }
            if (entPos[2][2] != Float.intBitsToFloat(Float.floatToIntBits(1.2478853E37f) ^ 0x7D16356F)) {
                GL11.glRotatef((float)(-entPos[2][2] * Float.intBitsToFloat(Float.floatToIntBits(0.044336032f) ^ 0x7F50B753)), (float)Float.intBitsToFloat(Float.floatToIntBits(1.4066994E38f) ^ 0x7ED3A817), (float)Float.intBitsToFloat(Float.floatToIntBits(2.783657E38f) ^ 0x7F516B49), (float)Float.intBitsToFloat(Float.floatToIntBits(7.2809854f) ^ 0x7F68FDD5));
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(6.120638087967519E306) ^ 0x7FA16EA1C5187F1FL), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.609714582541605E308) ^ 0x7FECA76411F0731EL), (double)Double.longBitsToDouble(Double.doubleToLongBits(8.462316329389263E307) ^ 0x7FDE2078FD57D037L));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(6.925646365396771E307) ^ 0x7FD8A7F772657AEBL), (double)Double.longBitsToDouble(Double.doubleToLongBits(-12.527286133463496) ^ 0x7FC90DF872B5B853L), (double)Double.longBitsToDouble(Double.doubleToLongBits(4.934892133178324E307) ^ 0x7FD1919CD28127FFL));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef((float)(xOff - e.rotationYawHead), (float)Float.intBitsToFloat(Float.floatToIntBits(7.7650247E37f) ^ 0x7E69AB93), (float)Float.intBitsToFloat(Float.floatToIntBits(32.780727f) ^ 0x7D831F77), (float)Float.intBitsToFloat(Float.floatToIntBits(3.1485788E38f) ^ 0x7F6CDF6C));
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.11632726f) ^ 0x7E913CFD)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.04103334f) ^ 0x7E571293)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.42872974f) ^ 0x7DA48277)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.09937588f) ^ 0x7EB48595)));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(1.3591655875225433E307) ^ 0x7FB35AEBCB5AEFEFL), (double)((double)yOff + Double.longBitsToDouble(Double.doubleToLongBits(29.787437059761967) ^ 0x7FDC500CE03ACF9DL)), (double)Double.longBitsToDouble(Double.doubleToLongBits(9.14079344220742E307) ^ 0x7FE0456A3ED7FB65L));
            if (entPos[0][0] != Float.intBitsToFloat(Float.floatToIntBits(7.3682987E37f) ^ 0x7E5DBB4F)) {
                GL11.glRotatef((float)(entPos[0][0] * Float.intBitsToFloat(Float.floatToIntBits(0.059573807f) ^ 0x7F112D4A)), (float)Float.intBitsToFloat(Float.floatToIntBits(55.529293f) ^ 0x7DDE1DFF), (float)Float.intBitsToFloat(Float.floatToIntBits(1.3883566E38f) ^ 0x7ED0E58D), (float)Float.intBitsToFloat(Float.floatToIntBits(2.0551212E38f) ^ 0x7F1A9C32));
            }
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(8.753674028331618E307) ^ 0x7FDF2A035FED9FBBL), (double)Double.longBitsToDouble(Double.doubleToLongBits(5.275056455168797E307) ^ 0x7FD2C7A2919C36FDL), (double)Double.longBitsToDouble(Double.doubleToLongBits(6.134083739931153E307) ^ 0x7FD5D68B4BFD3693L));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(5.0015027731704E307) ^ 0x7FD1CE5224DDE301L), (double)Double.longBitsToDouble(Double.doubleToLongBits(216.78585954862172) ^ 0x7FB82A16F1DFA1BFL), (double)Double.longBitsToDouble(Double.doubleToLongBits(3.398100157580596E307) ^ 0x7FC831FD9FE28277L));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef((float)(e.isSneaking() ? Float.intBitsToFloat(Float.floatToIntBits(0.267397f) ^ 0x7F40E842) : Float.intBitsToFloat(Float.floatToIntBits(2.1370689E38f) ^ 0x7F20C673)), (float)Float.intBitsToFloat(Float.floatToIntBits(6.917873f) ^ 0x7F5D5F37), (float)Float.intBitsToFloat(Float.floatToIntBits(4.7774947E37f) ^ 0x7E0FC47B), (float)Float.intBitsToFloat(Float.floatToIntBits(2.947121E37f) ^ 0x7DB15F97));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(1.5101014948080614E308) ^ 0x7FEAE1757B5002B3L), (double)(e.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(-8.24174195325498) ^ 0x7FE4CFFCC1FEF80AL) : Double.longBitsToDouble(Double.doubleToLongBits(4.0313329203111533E307) ^ 0x7FCCB43C2C9EF2EBL)), (double)(e.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(-19.07508936968066) ^ 0x7FEDAF53706AD561L) : Double.longBitsToDouble(Double.doubleToLongBits(1.1533484335193439E308) ^ 0x7FE487C0AA633A6DL)));
            GL11.glPushMatrix();
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(4.713167309055103E307) ^ 0x7FD0C788E4F51A23L), (double)yOff, (double)Double.longBitsToDouble(Double.doubleToLongBits(5.654280738118358E307) ^ 0x7FD42141A01BADD1L));
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(-8.677758625537097) ^ 0x7FE15B032DB68709L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.6713698250974398E308) ^ 0x7FEDC059D043CA89L), (double)Double.longBitsToDouble(Double.doubleToLongBits(7.812184409194593E307) ^ 0x7FDBCFF2DF5E718DL));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(11.131234654468333) ^ 0x7FE64331304A1632L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.668722332801535E307) ^ 0x7FB7C36DD7B3D05FL), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.3091215310522114E308) ^ 0x7FE74D9A5F14E935L));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color((float)((float)rainbowColor2.getRed() / Float.intBitsToFloat(Float.floatToIntBits(0.10790608f) ^ 0x7EA3FDDD)), (float)((float)rainbowColor2.getGreen() / Float.intBitsToFloat(Float.floatToIntBits(0.065650515f) ^ 0x7EF973C7)), (float)((float)rainbowColor2.getBlue() / Float.intBitsToFloat(Float.floatToIntBits(0.013854139f) ^ 0x7F1DFC78)), (float)((float)daColor.getValue().getAlpha() / Float.intBitsToFloat(Float.floatToIntBits(0.0104828f) ^ 0x7F54C00D)));
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(5.356538370113538E307) ^ 0x7FD311E59871C321L), (double)yOff, (double)Double.longBitsToDouble(Double.doubleToLongBits(6.52810085383979E307) ^ 0x7FD73DA5C1967649L));
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(9.690457043467357E307) ^ 0x7FE13FE4E02BB723L), (double)Double.longBitsToDouble(Double.doubleToLongBits(3.5752289489445923E307) ^ 0x7FC974DB8D8D21A7L), (double)Double.longBitsToDouble(Double.doubleToLongBits(2.4101647436252657E307) ^ 0x7FC1293388F0032FL));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(9.776428684599419E307) ^ 0x7FE1671226BA2FE6L), (double)Double.longBitsToDouble(Double.doubleToLongBits(2.4746994969627543) ^ 0x7FE255B6A66E9621L), (double)Double.longBitsToDouble(Double.doubleToLongBits(9.21060385103845E307) ^ 0x7FE0653A2F50DA87L));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated((double)Double.longBitsToDouble(Double.doubleToLongBits(3.2348892845238795E307) ^ 0x7FC7087E3D5432E7L), (double)((double)yOff + Double.longBitsToDouble(Double.doubleToLongBits(3.7080956759138757) ^ 0x7FEC33B7894A7029L)), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.0706702953482443E308) ^ 0x7FE30EFE0AE63ED1L));
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(-3.2419027389193578) ^ 0x7FD1EF6AB403C591L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.6261865595875449E307) ^ 0x7FB7285CC0C21DA7L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.3693476573847778E308) ^ 0x7FE8600CEFE90C47L));
            GL11.glVertex3d((double)Double.longBitsToDouble(Double.doubleToLongBits(30.636897654374415) ^ 0x7FE6A30BB9846FEEL), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.5511290123340546E308) ^ 0x7FEB9C6B57F0F184L), (double)Double.longBitsToDouble(Double.doubleToLongBits(1.696522920342647E308) ^ 0x7FEE32F8E63E7972L));
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    /*
     * WARNING - void declaration
     */
    public void startEnd(boolean bl) {
        void revert;
        if (revert != false) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable((int)2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint((int)3154, (int)4354);
        } else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable((int)2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask((revert == false ? 1 : 0) != 0);
    }

    /*
     * WARNING - void declaration
     */
    public static void addEntity(EntityPlayer entityPlayer, MixinPlayerModel modelPlayer) {
        void model;
        EntityPlayer e;
        entities.put(e, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
    }

    /*
     * WARNING - void declaration
     */
    public boolean doesntContain(EntityPlayer entityPlayer) {
        void var0;
        return !ModuleSkeleton.mc.world.playerEntities.contains(var0);
    }

    /*
     * WARNING - void declaration
     */
    public void lambda$onRender3D$0(EventRender3D eventRender3D, EntityPlayer entityPlayer) {
        void e;
        void event;
        this.drawSkeleton((EventRender3D)event, (EntityPlayer)e);
    }
}

