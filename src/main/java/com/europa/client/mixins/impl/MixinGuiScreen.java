/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.mixins.impl;

import com.europa.Europa;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiScreen.class})
public class MixinGuiScreen {
    RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
    ResourceLocation resource;
    FontRenderer fontRenderer;

    public MixinGuiScreen() {
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    @Inject(method={"renderToolTip"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderToolTip(ItemStack stack, int x, int y, CallbackInfo info) {
        NBTTagCompound blockEntityTag;
        NBTTagCompound tagCompound;
        this.resource = new ResourceLocation("textures/gui/container/shulker_box.png");
        if (Europa.getModuleManager().isModuleEnabled("ShulkerViewer") && stack.getItem() instanceof ItemShulkerBox && (tagCompound = stack.getTagCompound()) != null && tagCompound.hasKey("BlockEntityTag", 10) && (blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            info.cancel();
            NonNullList nonnulllist = NonNullList.withSize((int)27, (Object)ItemStack.EMPTY);
            ItemStackHelper.loadAllItems((NBTTagCompound)blockEntityTag, (NonNullList)nonnulllist);
            GlStateManager.enableBlend();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int x1 = x + 4;
            int y1 = y - 30;
            this.itemRender.zLevel = 300.0f;
            Minecraft.getMinecraft().renderEngine.bindTexture(this.resource);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x1, y1, 7, 5, 162, 66);
            this.fontRenderer.drawString(stack.getDisplayName(), x + 6, y - 28, Color.DARK_GRAY.getRGB());
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < nonnulllist.size(); ++i) {
                int iX = x + 5 + i % 9 * 18;
                int iY = y + 1 + (i / 9 - 1) * 18;
                ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                this.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, itemStack, iX, iY, null);
            }
            RenderHelper.disableStandardItemLighting();
            this.itemRender.zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }
}

