/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.Europa;
import com.europa.api.manager.event.impl.entity.EventRenderEntityName;
import com.europa.api.manager.event.impl.render.EventRender3D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.entity.DamageUtils;
import com.europa.api.utilities.entity.EntityUtils;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.client.modules.client.ModuleStreamerMode;
import com.europa.client.modules.remove.ModulePopCounter;
import java.awt.Color;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ModuleNametags
extends Module {
    public static ValueBoolean show_armor;
    public static ValueBoolean percent;
    public static ValueBoolean show_health;
    public static ValueBoolean show_ping;
    public static ValueBoolean show_totems;
    public static ValueBoolean show_invis;
    public static ValueBoolean gamemode;
    public static ValueBoolean entityID;
    public static ValueBoolean simplify;
    public static ValueNumber m_scale;
    public static ValueNumber width;
    public static ValueBoolean outline;
    public static ValueBoolean syncColor;
    public static ValueColor daColor;
    public static ValueNumber a;
    public Color color;
    public int enchantIgriega;
    public static boolean $assertionsDisabled;

    public ModuleNametags() {
        super("NameTags", "Name Tags", "Draws nametags on other players and yourself.", ModuleCategory.RENDER);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onRenderName(EventRenderEntityName eventRenderEntityName) {
        void event;
        event.setCancelled(true);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onRender3D(EventRender3D eventRender3D) {
        this.color = syncColor.getValue() ? ModuleNametags.globalColor(255) : daColor.getValue();
        for (EntityPlayer player : ModuleNametags.mc.world.playerEntities) {
            void event;
            if (player == null || player.equals((Object)ModuleNametags.mc.player) || !player.isEntityAlive()) continue;
            if (player.isInvisible() && !show_invis.getValue()) continue;
            double x = this.interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - ModuleNametags.mc.getRenderManager().renderPosX;
            double y = this.interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - ModuleNametags.mc.getRenderManager().renderPosY;
            double z = this.interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - ModuleNametags.mc.getRenderManager().renderPosZ;
            this.renderNameTag(player, x, y, z, event.getPartialTicks());
        }
    }

    /*
     * WARNING - void declaration
     */
    public void renderNameTag(EntityPlayer entityPlayer, double d, double d2, double d3, float f) {
        ItemStack renderOffhand;
        int xOffset;
        ItemStack renderMainHand;
        int width;
        String displayTag;
        double originalPositionZ;
        double originalPositionY;
        double originalPositionX;
        void player;
        block18: {
            block19: {
                void z;
                void x;
                void delta;
                void y;
                void tempY = y;
                tempY += player.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(76.2828815955355) ^ 0x7FB3121ABB685DC7L) : Double.longBitsToDouble(Double.doubleToLongBits(26.938820961915102) ^ 0x7FDC9630F4765FE7L);
                Entity camera = mc.getRenderViewEntity();
                if (!$assertionsDisabled) {
                    if (camera == null) {
                        throw new AssertionError();
                    }
                }
                originalPositionX = camera.posX;
                originalPositionY = camera.posY;
                originalPositionZ = camera.posZ;
                camera.posX = this.interpolate(camera.prevPosX, camera.posX, (float)delta);
                camera.posY = this.interpolate(camera.prevPosY, camera.posY, (float)delta);
                camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, (float)delta);
                displayTag = this.getDisplayTag((EntityPlayer)player);
                double distance = camera.getDistance((double)(x + ModuleNametags.mc.getRenderManager().viewerPosX), (double)(y + ModuleNametags.mc.getRenderManager().viewerPosY), (double)(z + ModuleNametags.mc.getRenderManager().viewerPosZ));
                width = (int)Europa.FONT_MANAGER.getStringWidth(displayTag) / 2;
                double scale = (Double.longBitsToDouble(Double.doubleToLongBits(1425.1523465435523) ^ 0x7FCB392348C4B34FL) + (double)m_scale.getValue().intValue() * (distance * Double.longBitsToDouble(Double.doubleToLongBits(2.3786006062447873) ^ 0x7FD0346CF203BA4DL))) / Double.longBitsToDouble(Double.doubleToLongBits(0.0022947429857209053) ^ 0x7FED8C6CC271D775L);
                if (distance <= Double.longBitsToDouble(Double.doubleToLongBits(0.02853477523722511) ^ 0x7FBD383859C536F7L)) {
                    scale = Double.longBitsToDouble(Double.doubleToLongBits(445.41611173082055) ^ 0x7FE2C02F4FC439F9L);
                }
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                GlStateManager.enablePolygonOffset();
                GlStateManager.doPolygonOffset((float)Float.intBitsToFloat(Float.floatToIntBits(63.73022f) ^ 0x7DFEEBBF), (float)Float.intBitsToFloat(Float.floatToIntBits(-5.018857E-6f) ^ 0x7F1F7CA7));
                GlStateManager.disableLighting();
                GlStateManager.translate((float)((float)x), (float)((float)tempY + Float.intBitsToFloat(Float.floatToIntBits(5.352486f) ^ 0x7F1874A2)), (float)((float)z));
                GlStateManager.rotate((float)(-ModuleNametags.mc.getRenderManager().playerViewY), (float)Float.intBitsToFloat(Float.floatToIntBits(2.4248813E38f) ^ 0x7F366D84), (float)Float.intBitsToFloat(Float.floatToIntBits(27.112234f) ^ 0x7E58E5DB), (float)Float.intBitsToFloat(Float.floatToIntBits(3.2431995E38f) ^ 0x7F73FDC0));
                GlStateManager.rotate((float)ModuleNametags.mc.getRenderManager().playerViewX, (float)(ModuleNametags.mc.gameSettings.thirdPersonView == 2 ? Float.intBitsToFloat(Float.floatToIntBits(-12.657144f) ^ 0x7ECA83A9) : Float.intBitsToFloat(Float.floatToIntBits(4.0211077f) ^ 0x7F00ACEA)), (float)Float.intBitsToFloat(Float.floatToIntBits(2.406918E38f) ^ 0x7F35138E), (float)Float.intBitsToFloat(Float.floatToIntBits(1.6532374E38f) ^ 0x7EF8C063));
                GlStateManager.scale((double)(-scale), (double)(-scale), (double)scale);
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                GlStateManager.enableBlend();
                RenderUtils.drawRectrgb((float)(-width - 2) - Float.intBitsToFloat(Float.floatToIntBits(214.94334f) ^ 0x7CD6F17F), -(Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(18.984045f) ^ 0x7E17DF53)) - Float.intBitsToFloat(Float.floatToIntBits(20.876944f) ^ 0x7E2703FB), (float)width + Float.intBitsToFloat(Float.floatToIntBits(0.08913862f) ^ 0x7DF68E4F), Float.intBitsToFloat(Float.floatToIntBits(0.49104056f) ^ 0x7EDB69AB) + (Europa.getModuleManager().isModuleEnabled("Font") ? Float.intBitsToFloat(Float.floatToIntBits(3.2741506f) ^ 0x7F518BAF) : Float.intBitsToFloat(Float.floatToIntBits(4.713597E37f) ^ 0x7E0DD83B)), Float.intBitsToFloat(Float.floatToIntBits(3.3383003E38f) ^ 0x7F7B2553), Float.intBitsToFloat(Float.floatToIntBits(2.5926814E38f) ^ 0x7F430D3A), Float.intBitsToFloat(Float.floatToIntBits(1.5249053E38f) ^ 0x7EE57137), a.getValue().intValue());
                if (outline.getValue()) {
                    RenderUtils.drawOutlineLine((float)(-width - 2) - Float.intBitsToFloat(Float.floatToIntBits(7.630748f) ^ 0x7F742F16), -(Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(32.37167f) ^ 0x7D817C97)) - Float.intBitsToFloat(Float.floatToIntBits(7.160745f) ^ 0x7F6524D3), (float)width + Float.intBitsToFloat(Float.floatToIntBits(0.40879574f) ^ 0x7E914DAD), Float.intBitsToFloat(Float.floatToIntBits(0.869111f) ^ 0x7F7E7E0F) + (Europa.getModuleManager().isModuleEnabled("Font") ? Float.intBitsToFloat(Float.floatToIntBits(9.448081f) ^ 0x7E172B57) : Float.intBitsToFloat(Float.floatToIntBits(2.8761844E38f) ^ 0x7F58614C)), (float)ModuleNametags.width.getValue().doubleValue(), this.color.getRGB());
                }
                GlStateManager.disableBlend();
                renderMainHand = player.getHeldItemMainhand().copy();
                if (renderMainHand.hasEffect()) {
                    if (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor) {
                        renderMainHand.stackSize = 1;
                    }
                }
                if (!renderMainHand.isEmpty && renderMainHand.getItem() != Items.AIR) {
                    String stackName = renderMainHand.getDisplayName();
                    int stackNameWidth = (int)Europa.FONT_MANAGER.getStringWidth(stackName) / 2;
                    GL11.glPushMatrix();
                    GL11.glScalef((float)Float.intBitsToFloat(Float.floatToIntBits(9.103614f) ^ 0x7E51A867), (float)Float.intBitsToFloat(Float.floatToIntBits(2.3390443f) ^ 0x7F55B2E7), (float)Float.intBitsToFloat(Float.floatToIntBits(2.2250566E38f) ^ 0x7F276508));
                    Europa.FONT_MANAGER.drawString(stackName, -stackNameWidth, show_armor.getValue() ? (float)((int)(-(this.getBiggestArmorTag((EntityPlayer)player) + Float.intBitsToFloat(Float.floatToIntBits(0.9004218f) ^ 0x7EF6820B)))) : (float)(percent.getValue() ? -36 : -26), Color.WHITE);
                    GL11.glScalef((float)Float.intBitsToFloat(Float.floatToIntBits(4.9685235f) ^ 0x7F5EFE25), (float)Float.intBitsToFloat(Float.floatToIntBits(6.0824866f) ^ 0x7F02A3BB), (float)Float.intBitsToFloat(Float.floatToIntBits(6.6224422f) ^ 0x7F53EB0C));
                    GL11.glPopMatrix();
                }
                GlStateManager.pushMatrix();
                xOffset = -8;
                for (ItemStack stack : player.inventory.armorInventory) {
                    if (stack == null) continue;
                    xOffset -= 8;
                }
                xOffset -= 8;
                renderOffhand = player.getHeldItemOffhand().copy();
                if (!renderOffhand.hasEffect()) break block18;
                if (renderOffhand.getItem() instanceof ItemTool) break block19;
                if (!(renderOffhand.getItem() instanceof ItemArmor)) break block18;
            }
            renderOffhand.stackSize = 1;
        }
        if (show_armor.getValue()) {
            this.renderItemStack(renderOffhand, xOffset);
        }
        xOffset += 16;
        for (int i = player.inventory.armorInventory.size(); i > 0; --i) {
            ItemStack stack2 = (ItemStack)player.inventory.armorInventory.get(i - 1);
            ItemStack armourStack = stack2.copy();
            if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                armourStack.stackSize = 1;
            }
            if (show_armor.getValue()) {
                this.renderItemStack(armourStack, xOffset);
            }
            if (percent.getValue()) {
                this.renderPercent(armourStack, xOffset);
            }
            xOffset += 16;
        }
        if (show_armor.getValue()) {
            this.renderItemStack(renderMainHand, xOffset);
        }
        GlStateManager.popMatrix();
        Europa.FONT_MANAGER.drawString(displayTag, -width, -(Europa.FONT_MANAGER.getHeight() - Float.intBitsToFloat(Float.floatToIntBits(15.061069f) ^ 0x7EF0FA23)) + (float)(Europa.getModuleManager().isModuleEnabled("Font") ? Double.longBitsToDouble(Double.doubleToLongBits(-2.3416846098178805) ^ 0x7FE2BBC52405B6ECL) : Double.longBitsToDouble(Double.doubleToLongBits(1.6430206804274589E308) ^ 0x7FED3F2A48284061L)), new Color(this.getDisplayColour((EntityPlayer)player)));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset((float)Float.intBitsToFloat(Float.floatToIntBits(63.07754f) ^ 0x7DFC4F67), (float)Float.intBitsToFloat(Float.floatToIntBits(4.952404E-6f) ^ 0x7F1137D4));
        GlStateManager.popMatrix();
    }

    /*
     * WARNING - void declaration
     */
    public void renderPercent(ItemStack itemStack, int n) {
        void stack;
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale((float)Float.intBitsToFloat(Float.floatToIntBits(2.753934f) ^ 0x7F304074), (float)Float.intBitsToFloat(Float.floatToIntBits(91.08672f) ^ 0x7DB62C67), (float)Float.intBitsToFloat(Float.floatToIntBits(3.3871047f) ^ 0x7F58C653));
        GlStateManager.disableDepth();
        if (DamageUtils.hasDurability((ItemStack)stack)) {
            void x;
            int percent = DamageUtils.getRoundedDamage((ItemStack)stack);
            String color = percent >= 60 ? this.section_sign() + "a" : (percent >= 25 ? this.section_sign() + "e" : this.section_sign() + "c");
            Europa.FONT_MANAGER.drawString(color + percent + "%", (float)(x * 2), show_armor.getValue() ? (this.enchantIgriega < -62 ? (float)this.enchantIgriega : Float.intBitsToFloat(Float.floatToIntBits(-0.036000524f) ^ 0x7F6B7549)) : Float.intBitsToFloat(Float.floatToIntBits(-0.033745576f) ^ 0x7F1A38CD), Color.WHITE);
        }
        GlStateManager.enableDepth();
        GlStateManager.scale((float)Float.intBitsToFloat(Float.floatToIntBits(0.06109446f) ^ 0x7D7A3E2F), (float)Float.intBitsToFloat(Float.floatToIntBits(0.4611896f) ^ 0x7EEC210B), (float)Float.intBitsToFloat(Float.floatToIntBits(0.1299073f) ^ 0x7E05066B));
        GlStateManager.popMatrix();
    }

    /*
     * WARNING - void declaration
     */
    public void renderItemStack(ItemStack itemStack, int n) {
        void x;
        void stack;
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        RenderHelper.enableStandardItemLighting();
        ModuleNametags.mc.getRenderItem().zLevel = Float.intBitsToFloat(Float.floatToIntBits(-0.013067931f) ^ 0x7F401AE0);
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)stack, (int)x, -29);
        mc.getRenderItem().renderItemOverlays(ModuleNametags.mc.fontRenderer, (ItemStack)stack, (int)x, -29);
        ModuleNametags.mc.getRenderItem().zLevel = Float.intBitsToFloat(Float.floatToIntBits(2.8014618E38f) ^ 0x7F52C231);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale((float)Float.intBitsToFloat(Float.floatToIntBits(2.6432683f) ^ 0x7F292B4F), (float)Float.intBitsToFloat(Float.floatToIntBits(3.682979f) ^ 0x7F6BB5EE), (float)Float.intBitsToFloat(Float.floatToIntBits(3.8045685f) ^ 0x7F737E0D));
        GlStateManager.disableDepth();
        this.renderEnchantmentText((ItemStack)stack, (int)x);
        GlStateManager.enableDepth();
        GlStateManager.scale((float)Float.intBitsToFloat(Float.floatToIntBits(0.38199833f) ^ 0x7EC39549), (float)Float.intBitsToFloat(Float.floatToIntBits(0.88703716f) ^ 0x7F6314DE), (float)Float.intBitsToFloat(Float.floatToIntBits(0.8380497f) ^ 0x7F568A6D));
        GlStateManager.popMatrix();
    }

    /*
     * WARNING - void declaration
     */
    public void renderEnchantmentText(ItemStack itemStack, int n) {
        void x;
        void stack;
        int enchantmentY = -37;
        NBTTagList enchants = stack.getEnchantmentTagList();
        if (enchants.tagCount() > 2 && simplify.getValue()) {
            Europa.FONT_MANAGER.drawString("god", (float)(x * 2), enchantmentY, new Color(-3977919));
            enchantmentY -= 8;
        } else {
            for (int index = 0; index < enchants.tagCount(); ++index) {
                short id = enchants.getCompoundTagAt(index).getShort("id");
                short level = enchants.getCompoundTagAt(index).getShort("lvl");
                Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                if (enc == null) continue;
                String encName = enc.isCurse() ? TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase() : enc.getTranslatedName((int)level).substring(0, 1).toLowerCase();
                encName = encName + level;
                Europa.FONT_MANAGER.drawString(encName, (float)(x * 2), enchantmentY, Color.WHITE);
                enchantmentY -= 8;
            }
        }
        this.enchantIgriega = enchantmentY;
    }

    /*
     * WARNING - void declaration
     */
    public float getBiggestArmorTag(EntityPlayer entityPlayer) {
        ItemStack renderOffHand;
        Enchantment enc;
        int index;
        void player;
        float enchantmentY = Float.intBitsToFloat(Float.floatToIntBits(3.0213878E38f) ^ 0x7F634DD0);
        boolean arm = false;
        for (ItemStack stack : player.inventory.armorInventory) {
            float encY = Float.intBitsToFloat(Float.floatToIntBits(2.2340918E38f) ^ 0x7F28130B);
            if (stack != null) {
                NBTTagList enchants = stack.getEnchantmentTagList();
                for (index = 0; index < enchants.tagCount(); ++index) {
                    short id = enchants.getCompoundTagAt(index).getShort("id");
                    enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc == null) continue;
                    encY += Float.intBitsToFloat(Float.floatToIntBits(0.02817726f) ^ 0x7DE6D3FF);
                    arm = true;
                }
            }
            if (!(encY > enchantmentY)) continue;
            enchantmentY = encY;
        }
        ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            float encY2 = Float.intBitsToFloat(Float.floatToIntBits(2.7944675E37f) ^ 0x7DA82F97);
            NBTTagList enchants2 = renderMainHand.getEnchantmentTagList();
            for (int index2 = 0; index2 < enchants2.tagCount(); ++index2) {
                short id2 = enchants2.getCompoundTagAt(index2).getShort("id");
                Enchantment enc2 = Enchantment.getEnchantmentByID((int)id2);
                if (enc2 == null) continue;
                encY2 += Float.intBitsToFloat(Float.floatToIntBits(0.013875647f) ^ 0x7D6356AF);
                arm = true;
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        if ((renderOffHand = player.getHeldItemOffhand().copy()).hasEffect()) {
            float encY = Float.intBitsToFloat(Float.floatToIntBits(1.9902912E38f) ^ 0x7F15BB9D);
            NBTTagList enchants = renderOffHand.getEnchantmentTagList();
            for (index = 0; index < enchants.tagCount(); ++index) {
                short id = enchants.getCompoundTagAt(index).getShort("id");
                enc = Enchantment.getEnchantmentByID((int)id);
                if (enc == null) continue;
                encY += Float.intBitsToFloat(Float.floatToIntBits(0.22482035f) ^ 0x7F66374E);
                arm = true;
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (float)(arm ? 0 : 20) + enchantmentY;
    }

    /*
     * WARNING - void declaration
     */
    public String getDisplayTag(EntityPlayer entityPlayer) {
        String popStr;
        String healthStr;
        String gameModeStr;
        String idString;
        String pingStr;
        String name;
        block66: {
            String string;
            StringBuilder stringBuilder;
            block65: {
                StringBuilder stringBuilder2;
                StringBuilder stringBuilder3;
                void player;
                name = player.getDisplayName().getFormattedText();
                if (name.contains(mc.getSession().getUsername())) {
                    name = "You";
                }
                if (Europa.getModuleManager().isModuleEnabled("StreamerMode") && ModuleStreamerMode.hideName.getValue()) {
                    name = Europa.FRIEND_MANAGER.isFriend(player.getName()) ? "Friend" : ModuleStreamerMode.otherName.getValue();
                }
                if (!show_health.getValue()) {
                    return name;
                }
                float health = EntityUtils.getHealth((Entity)player);
                String color = health > Float.intBitsToFloat(Float.floatToIntBits(0.4481791f) ^ 0x7F7577BB) ? TextFormatting.GREEN.toString() : (health > Float.intBitsToFloat(Float.floatToIntBits(0.41247305f) ^ 0x7F532FAB) ? TextFormatting.DARK_GREEN.toString() : (health > Float.intBitsToFloat(Float.floatToIntBits(0.16169353f) ^ 0x7F6592FD) ? TextFormatting.YELLOW.toString() : (health > Float.intBitsToFloat(Float.floatToIntBits(0.16080289f) ^ 0x7F24A983) ? TextFormatting.GOLD.toString() : (health > Float.intBitsToFloat(Float.floatToIntBits(0.21478052f) ^ 0x7EFBEF6D) ? TextFormatting.RED.toString() : TextFormatting.DARK_RED.toString()))));
                pingStr = "";
                if (show_ping.getValue()) {
                    StringBuilder stringBuilder4;
                    Minecraft minecraft = mc;
                    NetHandlerPlayClient netHandlerPlayClient = minecraft.getConnection();
                    NetHandlerPlayClient netHandlerPlayClient2 = Objects.requireNonNull(netHandlerPlayClient);
                    NetHandlerPlayClient netHandlerPlayClient3 = netHandlerPlayClient2;
                    void v4 = player;
                    UUID uUID = v4.getUniqueID();
                    NetworkPlayerInfo networkPlayerInfo = netHandlerPlayClient3.getPlayerInfo(uUID);
                    int n = networkPlayerInfo.getResponseTime();
                    int responseTime22 = n;
                    StringBuilder stringBuilder5 = stringBuilder4;
                    StringBuilder stringBuilder6 = stringBuilder4;
                    stringBuilder5();
                    String string2 = " ";
                    StringBuilder stringBuilder7 = stringBuilder6.append(string2);
                    String string3 = pingStr;
                    StringBuilder stringBuilder8 = stringBuilder7.append(string3);
                    int n2 = responseTime22;
                    StringBuilder stringBuilder9 = stringBuilder8.append(n2);
                    String string4 = "ms";
                    StringBuilder stringBuilder10 = stringBuilder9.append(string4);
                    String string5 = stringBuilder10.toString();
                    try {
                        pingStr = string5;
                    }
                    catch (Exception responseTime22) {
                        // empty catch block
                    }
                }
                idString = "";
                if (entityID.getValue()) {
                    idString = idString + " ID: " + player.getEntityId() + " ";
                }
                gameModeStr = "";
                if (gamemode.getValue()) {
                    gameModeStr = player.isCreative() ? gameModeStr + " [C]" : (player.isSpectator() || player.isInvisible() ? gameModeStr + " [I]" : gameModeStr + " [S]");
                }
                healthStr = "";
                if (show_health.getValue()) {
                    healthStr = Math.floor(health) == (double)health ? color + " " + (health > Float.intBitsToFloat(Float.floatToIntBits(1.937389E38f) ^ 0x7F11C0C1) ? Integer.valueOf((int)Math.floor(health)) : "dead") : color + " " + (health > Float.intBitsToFloat(Float.floatToIntBits(2.8416542E38f) ^ 0x7F55C845) ? Integer.valueOf((int)health) : "dead");
                }
                String popcolor = "";
                if (ModulePopCounter.totem_pop_counter.get(player.getName()) != null) {
                    popcolor = ModulePopCounter.totem_pop_counter.get(player.getName()) == 1 ? this.section_sign() + "a" : (ModulePopCounter.totem_pop_counter.get(player.getName()) == 2 ? this.section_sign() + "2" : (ModulePopCounter.totem_pop_counter.get(player.getName()) == 3 ? this.section_sign() + "e" : (ModulePopCounter.totem_pop_counter.get(player.getName()) == 4 ? this.section_sign() + "6" : (ModulePopCounter.totem_pop_counter.get(player.getName()) == 5 ? this.section_sign() + "c" : this.section_sign() + "4"))));
                }
                popStr = "";
                if (!show_totems.getValue()) break block66;
                StringBuilder stringBuilder11 = stringBuilder3;
                StringBuilder stringBuilder12 = stringBuilder3;
                stringBuilder11();
                String string6 = popStr;
                stringBuilder = stringBuilder12.append(string6);
                HashMap<String, Integer> hashMap = ModulePopCounter.totem_pop_counter;
                void v26 = player;
                String string7 = v26.getName();
                Integer n = hashMap.get(string7);
                if (n == null) {
                    string = "";
                    break block65;
                }
                StringBuilder stringBuilder13 = stringBuilder2;
                StringBuilder stringBuilder14 = stringBuilder2;
                stringBuilder13();
                String string8 = popcolor;
                StringBuilder stringBuilder15 = stringBuilder14.append(string8);
                String string9 = " -";
                StringBuilder stringBuilder16 = stringBuilder15.append(string9);
                HashMap<String, Integer> hashMap2 = ModulePopCounter.totem_pop_counter;
                void v38 = player;
                String string10 = v38.getName();
                Integer n3 = hashMap2.get(string10);
                StringBuilder stringBuilder17 = stringBuilder16.append(n3);
                string = stringBuilder17.toString();
            }
            StringBuilder stringBuilder18 = stringBuilder.append(string);
            String string11 = stringBuilder18.toString();
            try {
                popStr = string11;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return name + idString + gameModeStr + pingStr + healthStr + popStr;
    }

    /*
     * WARNING - void declaration
     */
    public int getDisplayColour(EntityPlayer entityPlayer) {
        void player;
        int colour = -1;
        if (Europa.FRIEND_MANAGER.isFriend(player.getName())) {
            return -11157267;
        }
        if (player.isSneaking()) {
            colour = this.color.getRGB();
        }
        return colour;
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

    public String section_sign() {
        return "\u00a7";
    }

    static {
        $assertionsDisabled = !ModuleNametags.class.desiredAssertionStatus();
        show_armor = new ValueBoolean("Armor", "ShowArmor", "", false);
        percent = new ValueBoolean("Percent", "Percent", "", false);
        show_health = new ValueBoolean("Health", "show_health", "", false);
        show_ping = new ValueBoolean("Ping", "show_ping", "", false);
        show_totems = new ValueBoolean("Pops", "show_totems", "", false);
        show_invis = new ValueBoolean("Invis", "show_invis", "", false);
        gamemode = new ValueBoolean("Gamemode", "gamemode", "", false);
        entityID = new ValueBoolean("EntityID", "EntityID", "", false);
        simplify = new ValueBoolean("Simple", "simplify", "", false);
        m_scale = new ValueNumber("Scale", "Scale", "", 4, 1, 15);
        width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(84.44984768126368) ^ 0x7FAD1CCA4DEDCD5FL), Double.longBitsToDouble(Double.doubleToLongBits(93.69301014521183) ^ 0x7FEEF5C3DEA0C753L), Double.longBitsToDouble(Double.doubleToLongBits(0.9585611505413623) ^ 0x7FE6AC886F195232L));
        outline = new ValueBoolean("Outline", "Outline", "", true);
        syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
        daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
        a = new ValueNumber("Alpha", "Alpha", "", 255, 0, 255);
    }
}

