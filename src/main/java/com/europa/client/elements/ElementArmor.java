/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.elements;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.client.modules.client.ModuleColor;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

public class ElementArmor
extends Element {
    public static ValueBoolean percentage = new ValueBoolean("Percentage", "Percentage", "Renders the percentage that the armor's durability is at.", true);
    public static ValueEnum percentageColor = new ValueEnum("PercentageColor", "PercentageColor", "The color for the percentage.", PercentageColors.Damage);

    public ElementArmor() {
        super("Armor", "Renders the status of your armor on screen.");
    }

    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        GlStateManager.enableTexture2D();
        this.frame.setWidth(Float.intBitsToFloat(Float.floatToIntBits(0.08226579f) ^ 0x7F1C7AF7));
        this.frame.setHeight(Float.intBitsToFloat(Float.floatToIntBits(0.1368522f) ^ 0x7F7C22FC));
        int index = 0;
        for (final ItemStack stack : ElementArmor.mc.player.inventory.armorInventory) {
            ++index;
            if (stack.isEmpty()) {
                continue;
            }
            GlStateManager.enableDepth();
            ElementArmor.mc.getRenderItem().zLevel = Float.intBitsToFloat(Float.floatToIntBits(0.009574704f) ^ 0x7F54DF38);
            ElementArmor.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, (int)(this.frame.getX() - Float.intBitsToFloat(Float.floatToIntBits(0.095117025f) ^ 0x7F76CCB7) + (9 - index) * 20 + Float.intBitsToFloat(Float.floatToIntBits(0.30395243f) ^ 0x7E9B9FA7)), (int)this.frame.getY());
            ElementArmor.mc.getRenderItem().renderItemOverlayIntoGUI(ElementArmor.mc.fontRenderer, stack, (int)(this.frame.getX() - Float.intBitsToFloat(Float.floatToIntBits(0.012901281f) ^ 0x7EE75FE5) + (9 - index) * 20 + Float.intBitsToFloat(Float.floatToIntBits(0.22137742f) ^ 0x7E62B0C3)), (int)this.frame.getY(), "");
            ElementArmor.mc.getRenderItem().zLevel = Float.intBitsToFloat(Float.floatToIntBits(2.9045706E37f) ^ 0x7DAECFFF);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (stack.getCount() > 1) ? (stack.getCount() + "") : "";
            ElementArmor.mc.fontRenderer.drawStringWithShadow(s, this.frame.getX() - Float.intBitsToFloat(Float.floatToIntBits(0.10808174f) ^ 0x7F6959F6) + (9 - index) * 20 + Float.intBitsToFloat(Float.floatToIntBits(0.1475635f) ^ 0x7E171AE3) + Float.intBitsToFloat(Float.floatToIntBits(0.39271408f) ^ 0x7F5111D2) - Float.intBitsToFloat(Float.floatToIntBits(0.22988103f) ^ 0x7E6B65EF) - ElementArmor.mc.fontRenderer.getStringWidth(s), this.frame.getY() + Float.intBitsToFloat(Float.floatToIntBits(1.0631813f) ^ 0x7E981653), 16777215);
            if (!ElementArmor.percentage.getValue()) {
                continue;
            }
            final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
            final float red = Float.intBitsToFloat(Float.floatToIntBits(213.37059f) ^ 0x7CD55EDF) - green;
            final int dmg = 100 - (int)(red * Float.intBitsToFloat(Float.floatToIntBits(0.030838517f) ^ 0x7E34A10F));
            Europa.FONT_MANAGER.drawString(this.getPercentageColor() + "" + dmg + "", this.frame.getX() - Float.intBitsToFloat(Float.floatToIntBits(0.06363344f) ^ 0x7F365240) + (9 - index) * 20 + Float.intBitsToFloat(Float.floatToIntBits(0.07000857f) ^ 0x7D8F60A7) + Float.intBitsToFloat(Float.floatToIntBits(0.0257446f) ^ 0x7DD2E657) - Europa.FONT_MANAGER.getStringWidth(dmg + "") / Float.intBitsToFloat(Float.floatToIntBits(0.8698598f) ^ 0x7F5EAF22), this.frame.getY() - Float.intBitsToFloat(Float.floatToIntBits(0.24599867f) ^ 0x7F4BE713), ElementArmor.percentageColor.getValue().equals(PercentageColors.Damage) ? new Color(red, green, Float.intBitsToFloat(Float.floatToIntBits(3.3989909E37f) ^ 0x7DCC91BF)) : ModuleColor.getActualColor());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    public ChatFormatting getPercentageColor() {
        if (percentageColor.getValue().equals((Object)PercentageColors.White)) {
            return ChatFormatting.WHITE;
        }
        if (percentageColor.getValue().equals((Object)PercentageColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }

    public static enum PercentageColors {
        Normal,
        Gray,
        White,
        Damage;

    }
}

