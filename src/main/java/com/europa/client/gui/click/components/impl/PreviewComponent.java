/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.gui.click.components.impl;

import com.europa.Europa;
import com.europa.api.manager.value.impl.ValuePreview;
import com.europa.api.utilities.render.RenderUtils;
import com.europa.client.gui.click.components.Component;
import com.europa.client.gui.click.components.impl.ModuleComponent;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class PreviewComponent
extends Component {
    public ValuePreview setting;
    public static EntityEnderCrystal entityEnderCrystal;
    public boolean open = false;

    public PreviewComponent(ValuePreview setting, ModuleComponent parent, int offset) {
        super(parent.getParent().getX(), parent.getParent().getY() + offset, parent.getParent());
        this.setting = setting;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY) {
        super.drawScreen(mouseX, mouseY);
        final Entity entity = this.setting.getEntity();
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + (this.open ? 100 : 14), new Color(40, 40, 40).getRGB());
        Gui.drawRect(this.getX() - 1, this.getY(), this.getX(), this.getY() + (this.open ? 100 : 14), new Color(30, 30, 30).getRGB());
        Gui.drawRect(this.getX() + this.getWidth(), this.getY(), this.getX() + this.getWidth() + 1, this.getY() + (this.open ? 100 : 14), new Color(30, 30, 30).getRGB());
        if (this.open) {
            if (entity instanceof EntityEnderCrystal) {
                final EntityEnderCrystal ent = new EntityEnderCrystal((World)PreviewComponent.mc.world, Double.longBitsToDouble(Double.doubleToLongBits(9.310613315809524E306) ^ 0x7FAA847B55B02A7FL), Double.longBitsToDouble(Double.doubleToLongBits(1.7125394916952668E308) ^ 0x7FEE7BF580E967CDL), Double.longBitsToDouble(Double.doubleToLongBits(1.351057559302745E308) ^ 0x7FE80CB4154FF45AL));
                (PreviewComponent.entityEnderCrystal = ent).setShowBottom(false);
                ent.rotationYaw = Float.intBitsToFloat(Float.floatToIntBits(1.1630837E38f) ^ 0x7EAF005B);
                ent.rotationPitch = Float.intBitsToFloat(Float.floatToIntBits(2.1111544E38f) ^ 0x7F1ED35B);
                ent.innerRotation = 0;
                ent.prevRotationYaw = Float.intBitsToFloat(Float.floatToIntBits(3.176926E38f) ^ 0x7F6F015F);
                ent.prevRotationPitch = Float.intBitsToFloat(Float.floatToIntBits(2.4984888E38f) ^ 0x7F3BF725);
                if (ent != null) {
                    GL11.glScalef(Float.intBitsToFloat(Float.floatToIntBits(6.72125f) ^ 0x7F57147B), Float.intBitsToFloat(Float.floatToIntBits(8.222657f) ^ 0x7E839001), Float.intBitsToFloat(Float.floatToIntBits(7.82415f) ^ 0x7F7A5F70));
                    RenderUtils.drawEntityOnScreen((Entity)ent, this.getX() + this.getWidth() / 2, this.getY() + 90, 40, Float.intBitsToFloat(Float.floatToIntBits(4.219836E36f) ^ 0x7C4B2D7F), Float.intBitsToFloat(Float.floatToIntBits(8.549953E37f) ^ 0x7E80A539));
                }
            }
        }
        Europa.FONT_MANAGER.drawString(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 3), Color.WHITE);
        Europa.FONT_MANAGER.drawString(this.open ? "-" : "+", this.getX() + this.getWidth() - 3 - Europa.FONT_MANAGER.getStringWidth("+"), (float)(this.getY() + 3), Color.WHITE);
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 && mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY()) {
            if (mouseY <= this.getY() + this.getHeight()) {
                this.open = !this.open;
            }
        }
    }

}

