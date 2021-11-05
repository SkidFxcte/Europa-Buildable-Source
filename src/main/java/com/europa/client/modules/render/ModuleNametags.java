//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.modules.render;

import com.europa.client.minecraft.RenderManager;
import com.europa.client.modules.remove.ModulePopCounter;
import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import com.europa.api.utilities.entity.EntityUtils;
import com.europa.client.modules.client.ModuleStreamerMode;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.enchantment.Enchantment;
import com.europa.api.utilities.entity.DamageUtils;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import com.europa.api.utilities.render.RenderUtils;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.europa.Europa;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import com.europa.api.manager.event.impl.render.EventRender3D;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.europa.api.manager.event.impl.entity.EventRenderEntityName;
import com.europa.api.manager.module.ModuleCategory;
import java.awt.Color;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.module.Module;

public class ModuleNametags extends Module
{
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

    @SubscribeEvent
    public void onRenderName(final EventRenderEntityName event) {
        event.setCancelled(true);
    }

    @Override
    public void onRender3D(final EventRender3D event) {
        if (ModuleNametags.syncColor.getValue()) {
            this.color = Module.globalColor(255);
        }
        else {
            this.color = ModuleNametags.daColor.getValue();
        }
        for (final EntityPlayer player : ModuleNametags.mc.world.playerEntities) {
            if (player != null && !player.equals((Object)ModuleNametags.mc.player)) {
                if (!player.isEntityAlive()) {
                    continue;
                }
                if (player.isInvisible() && !ModuleNametags.show_invis.getValue()) {
                    continue;
                }
                final double x = this.interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - RenderManager.renderPosX;
                final double y = this.interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - RenderManager.renderPosY;
                final double z = this.interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - RenderManager.renderPosZ;
                this.renderNameTag(player, x, y, z, event.getPartialTicks());
            }
        }
    }

    public void renderNameTag(final EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.isSneaking() ? Double.longBitsToDouble(Double.doubleToLongBits(76.2828815955355) ^ 0x7FB3121ABB685DC7L) : Double.longBitsToDouble(Double.doubleToLongBits(26.938820961915102) ^ 0x7FDC9630F4765FE7L));
        final Entity camera = ModuleNametags.mc.getRenderViewEntity();
        if (!ModuleNametags.$assertionsDisabled) {
            if (camera == null) {
                throw new AssertionError();
            }
        }
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        final String displayTag = this.getDisplayTag(player);
        final double distance = camera.getDistance(x + ModuleNametags.mc.getRenderManager().viewerPosX, y + ModuleNametags.mc.getRenderManager().viewerPosY, z + ModuleNametags.mc.getRenderManager().viewerPosZ);
        final int width = (int)Europa.FONT_MANAGER.getStringWidth(displayTag) / 2;
        double scale = (Double.longBitsToDouble(Double.doubleToLongBits(1425.1523465435523) ^ 0x7FCB392348C4B34FL) + ModuleNametags.m_scale.getValue().intValue() * (distance * Double.longBitsToDouble(Double.doubleToLongBits(2.3786006062447873) ^ 0x7FD0346CF203BA4DL))) / Double.longBitsToDouble(Double.doubleToLongBits(0.0022947429857209053) ^ 0x7FED8C6CC271D775L);
        if (distance <= Double.longBitsToDouble(Double.doubleToLongBits(0.02853477523722511) ^ 0x7FBD383859C536F7L)) {
            scale = Double.longBitsToDouble(Double.doubleToLongBits(445.41611173082055) ^ 0x7FE2C02F4FC439F9L);
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(Float.intBitsToFloat(Float.floatToIntBits(63.73022f) ^ 0x7DFEEBBF), Float.intBitsToFloat(Float.floatToIntBits(-5.018857E-6f) ^ 0x7F1F7CA7));
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + Float.intBitsToFloat(Float.floatToIntBits(5.352486f) ^ 0x7F1874A2), (float)z);
        GlStateManager.rotate(-ModuleNametags.mc.getRenderManager().playerViewY, Float.intBitsToFloat(Float.floatToIntBits(2.4248813E38f) ^ 0x7F366D84), Float.intBitsToFloat(Float.floatToIntBits(27.112234f) ^ 0x7E58E5DB), Float.intBitsToFloat(Float.floatToIntBits(3.2431995E38f) ^ 0x7F73FDC0));
        GlStateManager.rotate(ModuleNametags.mc.getRenderManager().playerViewX, (ModuleNametags.mc.gameSettings.thirdPersonView == 2) ? Float.intBitsToFloat(Float.floatToIntBits(-12.657144f) ^ 0x7ECA83A9) : Float.intBitsToFloat(Float.floatToIntBits(4.0211077f) ^ 0x7F00ACEA), Float.intBitsToFloat(Float.floatToIntBits(2.406918E38f) ^ 0x7F35138E), Float.intBitsToFloat(Float.floatToIntBits(1.6532374E38f) ^ 0x7EF8C063));
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        RenderUtils.drawRectrgb(-width - 2 - Float.intBitsToFloat(Float.floatToIntBits(214.94334f) ^ 0x7CD6F17F), -(Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(18.984045f) ^ 0x7E17DF53)) - Float.intBitsToFloat(Float.floatToIntBits(20.876944f) ^ 0x7E2703FB), width + Float.intBitsToFloat(Float.floatToIntBits(0.08913862f) ^ 0x7DF68E4F), Float.intBitsToFloat(Float.floatToIntBits(0.49104056f) ^ 0x7EDB69AB) + (Europa.getModuleManager().isModuleEnabled("Font") ? Float.intBitsToFloat(Float.floatToIntBits(3.2741506f) ^ 0x7F518BAF) : Float.intBitsToFloat(Float.floatToIntBits(4.713597E37f) ^ 0x7E0DD83B)), Float.intBitsToFloat(Float.floatToIntBits(3.3383003E38f) ^ 0x7F7B2553), Float.intBitsToFloat(Float.floatToIntBits(2.5926814E38f) ^ 0x7F430D3A), Float.intBitsToFloat(Float.floatToIntBits(1.5249053E38f) ^ 0x7EE57137), (float)ModuleNametags.a.getValue().intValue());
        if (ModuleNametags.outline.getValue()) {
            RenderUtils.drawOutlineLine(-width - 2 - Float.intBitsToFloat(Float.floatToIntBits(7.630748f) ^ 0x7F742F16), -(Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(32.37167f) ^ 0x7D817C97)) - Float.intBitsToFloat(Float.floatToIntBits(7.160745f) ^ 0x7F6524D3), width + Float.intBitsToFloat(Float.floatToIntBits(0.40879574f) ^ 0x7E914DAD), Float.intBitsToFloat(Float.floatToIntBits(0.869111f) ^ 0x7F7E7E0F) + (Europa.getModuleManager().isModuleEnabled("Font") ? Float.intBitsToFloat(Float.floatToIntBits(9.448081f) ^ 0x7E172B57) : Float.intBitsToFloat(Float.floatToIntBits(2.8761844E38f) ^ 0x7F58614C)), (float)ModuleNametags.width.getValue().doubleValue(), this.color.getRGB());
        }
        GlStateManager.disableBlend();
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            if (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor) {
                com.europa.client.minecraft.ItemStack.stackSize = 1;
            }
        }
        if (!com.europa.client.minecraft.ItemStack.isEmpty && renderMainHand.getItem() != Items.AIR) {
            final String stackName = renderMainHand.getDisplayName();
            final int stackNameWidth = (int)Europa.FONT_MANAGER.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(Float.intBitsToFloat(Float.floatToIntBits(9.103614f) ^ 0x7E51A867), Float.intBitsToFloat(Float.floatToIntBits(2.3390443f) ^ 0x7F55B2E7), Float.intBitsToFloat(Float.floatToIntBits(2.2250566E38f) ^ 0x7F276508));
            Europa.FONT_MANAGER.drawString(stackName, (float)(-stackNameWidth), ModuleNametags.show_armor.getValue() ? ((float)(int)(-(this.getBiggestArmorTag(player) + Float.intBitsToFloat(Float.floatToIntBits(0.9004218f) ^ 0x7EF6820B)))) : ((float)(ModuleNametags.percent.getValue() ? -36 : -26)), Color.WHITE);
            GL11.glScalef(Float.intBitsToFloat(Float.floatToIntBits(4.9685235f) ^ 0x7F5EFE25), Float.intBitsToFloat(Float.floatToIntBits(6.0824866f) ^ 0x7F02A3BB), Float.intBitsToFloat(Float.floatToIntBits(6.6224422f) ^ 0x7F53EB0C));
            GL11.glPopMatrix();
        }
        GlStateManager.pushMatrix();
        int xOffset = -8;
        for (final ItemStack stack : player.inventory.armorInventory) {
            if (stack != null) {
                xOffset -= 8;
            }
        }
        xOffset -= 8;
        final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
        Label_2300: {
            if (renderOffhand.hasEffect()) {
                if (!(renderOffhand.getItem() instanceof ItemTool)) {
                    if (!(renderOffhand.getItem() instanceof ItemArmor)) {
                        break Label_2300;
                    }
                }
                com.europa.client.minecraft.ItemStack.stackSize = 1;
            }
        }
        if (ModuleNametags.show_armor.getValue()) {
            this.renderItemStack(renderOffhand, xOffset);
        }
        xOffset += 16;
        for (int i = player.inventory.armorInventory.size(); i > 0; --i) {
            final ItemStack stack2 = (ItemStack)player.inventory.armorInventory.get(i - 1);
            final ItemStack armourStack = stack2.copy();
            if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                com.europa.client.minecraft.ItemStack.stackSize = 1;
            }
            if (ModuleNametags.show_armor.getValue()) {
                this.renderItemStack(armourStack, xOffset);
            }
            if (ModuleNametags.percent.getValue()) {
                this.renderPercent(armourStack, xOffset);
            }
            xOffset += 16;
        }
        if (ModuleNametags.show_armor.getValue()) {
            this.renderItemStack(renderMainHand, xOffset);
        }
        GlStateManager.popMatrix();
        Europa.FONT_MANAGER.drawString(displayTag, (float)(-width), -(Europa.FONT_MANAGER.getHeight() - Float.intBitsToFloat(Float.floatToIntBits(15.061069f) ^ 0x7EF0FA23)) + (float)(Europa.getModuleManager().isModuleEnabled("Font") ? Double.longBitsToDouble(Double.doubleToLongBits(-2.3416846098178805) ^ 0x7FE2BBC52405B6ECL) : Double.longBitsToDouble(Double.doubleToLongBits(1.6430206804274589E308) ^ 0x7FED3F2A48284061L)), new Color(this.getDisplayColour(player)));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(Float.intBitsToFloat(Float.floatToIntBits(63.07754f) ^ 0x7DFC4F67), Float.intBitsToFloat(Float.floatToIntBits(4.952404E-6f) ^ 0x7F1137D4));
        GlStateManager.popMatrix();
    }

    public void renderPercent(final ItemStack stack, final int x) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(Float.intBitsToFloat(Float.floatToIntBits(2.753934f) ^ 0x7F304074), Float.intBitsToFloat(Float.floatToIntBits(91.08672f) ^ 0x7DB62C67), Float.intBitsToFloat(Float.floatToIntBits(3.3871047f) ^ 0x7F58C653));
        GlStateManager.disableDepth();
        if (DamageUtils.hasDurability(stack)) {
            final int percent = DamageUtils.getRoundedDamage(stack);
            String color;
            if (percent >= 60) {
                color = this.section_sign() + "a";
            }
            else if (percent >= 25) {
                color = this.section_sign() + "e";
            }
            else {
                color = this.section_sign() + "c";
            }
            Europa.FONT_MANAGER.drawString(color + percent + "%", (float)(x * 2), ModuleNametags.show_armor.getValue() ? ((this.enchantIgriega < -62) ? ((float)this.enchantIgriega) : Float.intBitsToFloat(Float.floatToIntBits(-0.036000524f) ^ 0x7F6B7549)) : Float.intBitsToFloat(Float.floatToIntBits(-0.033745576f) ^ 0x7F1A38CD), Color.WHITE);
        }
        GlStateManager.enableDepth();
        GlStateManager.scale(Float.intBitsToFloat(Float.floatToIntBits(0.06109446f) ^ 0x7D7A3E2F), Float.intBitsToFloat(Float.floatToIntBits(0.4611896f) ^ 0x7EEC210B), Float.intBitsToFloat(Float.floatToIntBits(0.1299073f) ^ 0x7E05066B));
        GlStateManager.popMatrix();
    }

    public void renderItemStack(final ItemStack stack, final int x) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        ModuleNametags.mc.getRenderItem().zLevel = Float.intBitsToFloat(Float.floatToIntBits(-0.013067931f) ^ 0x7F401AE0);
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        ModuleNametags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -29);
        ModuleNametags.mc.getRenderItem().renderItemOverlays(ModuleNametags.mc.fontRenderer, stack, x, -29);
        ModuleNametags.mc.getRenderItem().zLevel = Float.intBitsToFloat(Float.floatToIntBits(2.8014618E38f) ^ 0x7F52C231);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(Float.intBitsToFloat(Float.floatToIntBits(2.6432683f) ^ 0x7F292B4F), Float.intBitsToFloat(Float.floatToIntBits(3.682979f) ^ 0x7F6BB5EE), Float.intBitsToFloat(Float.floatToIntBits(3.8045685f) ^ 0x7F737E0D));
        GlStateManager.disableDepth();
        this.renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale(Float.intBitsToFloat(Float.floatToIntBits(0.38199833f) ^ 0x7EC39549), Float.intBitsToFloat(Float.floatToIntBits(0.88703716f) ^ 0x7F6314DE), Float.intBitsToFloat(Float.floatToIntBits(0.8380497f) ^ 0x7F568A6D));
        GlStateManager.popMatrix();
    }

    public void renderEnchantmentText(final ItemStack stack, final int x) {
        int enchantmentY = -37;
        final NBTTagList enchants = stack.getEnchantmentTagList();
        if (enchants.tagCount() > 2 && ModuleNametags.simplify.getValue()) {
            Europa.FONT_MANAGER.drawString("god", (float)(x * 2), (float)enchantmentY, new Color(-3977919));
            enchantmentY -= 8;
        }
        else {
            for (int index = 0; index < enchants.tagCount(); ++index) {
                final short id = enchants.getCompoundTagAt(index).getShort("id");
                final short level = enchants.getCompoundTagAt(index).getShort("lvl");
                final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                if (enc != null) {
                    String encName = enc.isCurse() ? (TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase()) : enc.getTranslatedName((int)level).substring(0, 1).toLowerCase();
                    encName += level;
                    Europa.FONT_MANAGER.drawString(encName, (float)(x * 2), (float)enchantmentY, Color.WHITE);
                    enchantmentY -= 8;
                }
            }
        }
        this.enchantIgriega = enchantmentY;
    }

    public float getBiggestArmorTag(final EntityPlayer player) {
        float enchantmentY = Float.intBitsToFloat(Float.floatToIntBits(3.0213878E38f) ^ 0x7F634DD0);
        boolean arm = false;
        for (final ItemStack stack : player.inventory.armorInventory) {
            float encY = Float.intBitsToFloat(Float.floatToIntBits(2.2340918E38f) ^ 0x7F28130B);
            if (stack != null) {
                final NBTTagList enchants = stack.getEnchantmentTagList();
                for (int index = 0; index < enchants.tagCount(); ++index) {
                    final short id = enchants.getCompoundTagAt(index).getShort("id");
                    final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc != null) {
                        encY += Float.intBitsToFloat(Float.floatToIntBits(0.02817726f) ^ 0x7DE6D3FF);
                        arm = true;
                    }
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            float encY2 = Float.intBitsToFloat(Float.floatToIntBits(2.7944675E37f) ^ 0x7DA82F97);
            final NBTTagList enchants2 = renderMainHand.getEnchantmentTagList();
            for (int index2 = 0; index2 < enchants2.tagCount(); ++index2) {
                final short id2 = enchants2.getCompoundTagAt(index2).getShort("id");
                final Enchantment enc2 = Enchantment.getEnchantmentByID((int)id2);
                if (enc2 != null) {
                    encY2 += Float.intBitsToFloat(Float.floatToIntBits(0.013875647f) ^ 0x7D6356AF);
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        final ItemStack renderOffHand = player.getHeldItemOffhand().copy();
        if (renderOffHand.hasEffect()) {
            float encY = Float.intBitsToFloat(Float.floatToIntBits(1.9902912E38f) ^ 0x7F15BB9D);
            final NBTTagList enchants = renderOffHand.getEnchantmentTagList();
            for (int index = 0; index < enchants.tagCount(); ++index) {
                final short id = enchants.getCompoundTagAt(index).getShort("id");
                final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                if (enc != null) {
                    encY += Float.intBitsToFloat(Float.floatToIntBits(0.22482035f) ^ 0x7F66374E);
                    arm = true;
                }
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (arm ? 0 : 20) + enchantmentY;
    }

    public String getDisplayTag(final EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if (name.contains(ModuleNametags.mc.getSession().getUsername())) {
            name = "You";
        }
        if (Europa.getModuleManager().isModuleEnabled("StreamerMode") && ModuleStreamerMode.hideName.getValue()) {
            if (Europa.FRIEND_MANAGER.isFriend(player.getName())) {
                name = "Friend";
            }
            else {
                name = ModuleStreamerMode.otherName.getValue();
            }
        }
        if (!ModuleNametags.show_health.getValue()) {
            return name;
        }
        final float health = EntityUtils.getHealth((Entity)player);
        String color;
        if (health > Float.intBitsToFloat(Float.floatToIntBits(0.4481791f) ^ 0x7F7577BB)) {
            color = TextFormatting.GREEN.toString();
        }
        else if (health > Float.intBitsToFloat(Float.floatToIntBits(0.41247305f) ^ 0x7F532FAB)) {
            color = TextFormatting.DARK_GREEN.toString();
        }
        else if (health > Float.intBitsToFloat(Float.floatToIntBits(0.16169353f) ^ 0x7F6592FD)) {
            color = TextFormatting.YELLOW.toString();
        }
        else if (health > Float.intBitsToFloat(Float.floatToIntBits(0.16080289f) ^ 0x7F24A983)) {
            color = TextFormatting.GOLD.toString();
        }
        else if (health > Float.intBitsToFloat(Float.floatToIntBits(0.21478052f) ^ 0x7EFBEF6D)) {
            color = TextFormatting.RED.toString();
        }
        else {
            color = TextFormatting.DARK_RED.toString();
        }
        String pingStr = "";
        if (ModuleNametags.show_ping.getValue()) {
            try {
                final int responseTime = Objects.requireNonNull(ModuleNametags.mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr = " " + pingStr + responseTime + "ms";
            }
            catch (Exception ex) {}
        }
        String idString = "";
        if (ModuleNametags.entityID.getValue()) {
            idString = idString + " ID: " + player.getEntityId() + " ";
        }
        String gameModeStr = "";
        if (ModuleNametags.gamemode.getValue()) {
            if (player.isCreative()) {
                gameModeStr += " [C]";
            }
            else if (!player.isSpectator() && !player.isInvisible()) {
                gameModeStr += " [S]";
            }
            else {
                gameModeStr += " [I]";
            }
        }
        String healthStr = "";
        if (ModuleNametags.show_health.getValue()) {
            if (Math.floor(health) == health) {
                healthStr = color + " " + ((health > Float.intBitsToFloat(Float.floatToIntBits(1.937389E38f) ^ 0x7F11C0C1)) ? Integer.valueOf((int)Math.floor(health)) : "dead");
            }
            else {
                healthStr = color + " " + ((health > Float.intBitsToFloat(Float.floatToIntBits(2.8416542E38f) ^ 0x7F55C845)) ? Integer.valueOf((int)health) : "dead");
            }
        }
        String popcolor = "";
        if (ModulePopCounter.totem_pop_counter.get(player.getName()) != null) {
            if (ModulePopCounter.totem_pop_counter.get(player.getName()) == 1) {
                popcolor = this.section_sign() + "a";
            }
            else if (ModulePopCounter.totem_pop_counter.get(player.getName()) == 2) {
                popcolor = this.section_sign() + "2";
            }
            else if (ModulePopCounter.totem_pop_counter.get(player.getName()) == 3) {
                popcolor = this.section_sign() + "e";
            }
            else if (ModulePopCounter.totem_pop_counter.get(player.getName()) == 4) {
                popcolor = this.section_sign() + "6";
            }
            else if (ModulePopCounter.totem_pop_counter.get(player.getName()) == 5) {
                popcolor = this.section_sign() + "c";
            }
            else {
                popcolor = this.section_sign() + "4";
            }
        }
        String popStr = "";
        if (ModuleNametags.show_totems.getValue()) {
            try {
                popStr += ((ModulePopCounter.totem_pop_counter.get(player.getName()) == null) ? "" : (popcolor + " -" + ModulePopCounter.totem_pop_counter.get(player.getName())));
            }
            catch (Exception ex2) {}
        }
        return name + idString + gameModeStr + pingStr + healthStr + popStr;
    }

    public int getDisplayColour(final EntityPlayer player) {
        int colour = -1;
        if (Europa.FRIEND_MANAGER.isFriend(player.getName())) {
            return -11157267;
        }
        if (player.isSneaking()) {
            colour = this.color.getRGB();
        }
        return colour;
    }

    public double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }

    public String section_sign() {
        return "§";
    }

    static {
        ModuleNametags.$assertionsDisabled = !ModuleNametags.class.desiredAssertionStatus();
        ModuleNametags.show_armor = new ValueBoolean("Armor", "ShowArmor", "", false);
        ModuleNametags.percent = new ValueBoolean("Percent", "Percent", "", false);
        ModuleNametags.show_health = new ValueBoolean("Health", "show_health", "", false);
        ModuleNametags.show_ping = new ValueBoolean("Ping", "show_ping", "", false);
        ModuleNametags.show_totems = new ValueBoolean("Pops", "show_totems", "", false);
        ModuleNametags.show_invis = new ValueBoolean("Invis", "show_invis", "", false);
        ModuleNametags.gamemode = new ValueBoolean("Gamemode", "gamemode", "", false);
        ModuleNametags.entityID = new ValueBoolean("EntityID", "EntityID", "", false);
        ModuleNametags.simplify = new ValueBoolean("Simple", "simplify", "", false);
        ModuleNametags.m_scale = new ValueNumber("Scale", "Scale", "", 4, 1, 15);
        ModuleNametags.width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(84.44984768126368) ^ 0x7FAD1CCA4DEDCD5FL), Double.longBitsToDouble(Double.doubleToLongBits(93.69301014521183) ^ 0x7FEEF5C3DEA0C753L), Double.longBitsToDouble(Double.doubleToLongBits(0.9585611505413623) ^ 0x7FE6AC886F195232L));
        ModuleNametags.outline = new ValueBoolean("Outline", "Outline", "", true);
        ModuleNametags.syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
        ModuleNametags.daColor = new ValueColor("Color", "Color", "", new Color(255, 255, 255, 255));
        ModuleNametags.a = new ValueNumber("Alpha", "Alpha", "", 255, 0, 255);
    }
}
