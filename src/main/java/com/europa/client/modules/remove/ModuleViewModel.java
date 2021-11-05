/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.remove;

import com.europa.api.manager.event.impl.render.EventHandSide;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.utilities.math.TimerUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleViewModel
extends Module {
    public static ValueBoolean overrideSwing = new ValueBoolean("OverrideSwing", "OverrideSwing", "", false);
    public static ValueNumber swingSpeed = new ValueNumber("SwingSpeed", "SwingSpeed", "", Double.longBitsToDouble(Double.doubleToLongBits(0.035266072429762256) ^ 0x7FBA0E65077D0747L), Double.longBitsToDouble(Double.doubleToLongBits(0.19736312027021405) ^ 0x7FC94331D97FA073L), Double.longBitsToDouble(Double.doubleToLongBits(0.4094625495217116) ^ 0x7FEE34A268C87B67L));
    public static ValueNumber viewAlpha = new ValueNumber("ViewModelAlpha", "ViewModelAlpha", "", 100, 0, 255);
    public static ValueNumber scaleRX = new ValueNumber("ScaleRX", "scalerX", "Changes the scale of the right hand", 10, 0, 50);
    public static ValueNumber scaleRY = new ValueNumber("ScaleRY", "scalerY", "Changes the scale of the right hand", 10, 0, 50);
    public static ValueNumber scaleRZ = new ValueNumber("ScaleRZ", "scalerZ", "Changes the scale of the right hand", 10, 0, 50);
    public static ValueNumber scaleLX = new ValueNumber("ScaleLX", "scalelX", "Changes the scale of the left hand", 10, 0, 50);
    public static ValueNumber scaleLY = new ValueNumber("ScaleLY", "scalelY", "Changes the scale of the left hand", 10, 0, 50);
    public static ValueNumber scaleLZ = new ValueNumber("ScaleLZ", "scalelZ", "Changes the scale of the left hand", 10, 0, 50);
    public static ValueNumber xr = new ValueNumber("RightX", "xr", "changes the position x ways", 0, -50, 50);
    public static ValueNumber yr = new ValueNumber("RightY", "yr", "changes the position y ways", 0, -50, 50);
    public static ValueNumber zr = new ValueNumber("RightZ", "zr", "changes the position z ways", 0, -50, 50);
    public static ValueNumber yawR = new ValueNumber("YawR", "yawr", "yaw rotate", 0, -360, 360);
    public static ValueNumber pitchR = new ValueNumber("PitchR", "pitchr", "pitch rotate", 0, -360, 360);
    public static ValueNumber rollR = new ValueNumber("RollR", "rollr", "Roll rotate", 0, -360, 360);
    public static ValueNumber xl = new ValueNumber("LeftX", "xl", "changes the position x ways", 0, -50, 50);
    public static ValueNumber yl = new ValueNumber("LeftY", "yl", "changes the position y ways", 0, -50, 50);
    public static ValueNumber zl = new ValueNumber("LeftZ", "zl", "changes the position z ways", 0, -50, 50);
    public static ValueNumber yawL = new ValueNumber("YawL", "yawl", "yaw rotate", 0, -360, 360);
    public static ValueNumber pitchL = new ValueNumber("PitchL", "pitchl", "pitch rotate", 0, -360, 360);
    public static ValueNumber rollL = new ValueNumber("RollL", "rolll", "Roll rotate", 0, -360, 360);
    public static ValueEnum animation = new ValueEnum("Animation", "Animation", "", animations.None);
    public static ValueEnum animationMode = new ValueEnum("Mode", "Mode", "", animModes.Heart);
    public static ValueNumber minHeartSize = new ValueNumber("MinHeartSize", "MinHeartSize", "", Double.longBitsToDouble(Double.doubleToLongBits(0.6300194025331697) ^ 0x7FC0291E733736ABL), Double.longBitsToDouble(Double.doubleToLongBits(1.2621865668470429E308) ^ 0x7FE677B900D52440L), Double.longBitsToDouble(Double.doubleToLongBits(0.03444535423972899) ^ 0x7FE8A2D24BEAE407L));
    public static ValueNumber maxHeartSize = new ValueNumber("MaxHeartSize", "MaxHeartSize", "", Double.longBitsToDouble(Double.doubleToLongBits(1.3401475054272851) ^ 0x7FDB713E82BA04CDL), Double.longBitsToDouble(Double.doubleToLongBits(1.589057561091059E308) ^ 0x7FEC4942030816C2L), Double.longBitsToDouble(Double.doubleToLongBits(0.00817288303003203) ^ 0x7FC9BCF1CA9FC973L));
    public static double altura = minHeartSize.getValue().doubleValue();
    public boolean isReversing = false;
    public TimerUtils alturaTimer = new TimerUtils();

    public ModuleViewModel() {
        super("ViewModel", "View Model", "Changes the view of your models", ModuleCategory.RENDER);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onUpdate() {
        if (!this.alturaTimer.hasReached((long)600089761 ^ 0x23C4A4A4L)) return;
        altura += this.isReversing ? Double.longBitsToDouble(Double.doubleToLongBits(-20.001006978058644) ^ 0x7F8D99D867D04DFFL) : Double.longBitsToDouble(Double.doubleToLongBits(118.46035207220301) ^ 0x7FE404EFF11029E5L);
        if (this.isReversing && altura <= minHeartSize.getValue().doubleValue()) {
            this.isReversing = false;
        } else if (!this.isReversing && altura >= maxHeartSize.getValue().doubleValue()) {
            this.isReversing = true;
        }
        this.alturaTimer.reset();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @SubscribeEvent
    public void onHandSideEvent(EventHandSide eventHandSide) {
        switch (animationMode.getValue().ordinal()) {
            case 1: {
                GlStateManager.translate((float)(xl.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.0096426485f) ^ 0x7ED5FC33)), (float)(yl.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.07549902f) ^ 0x7F529F3B)), (float)(zl.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.11592148f) ^ 0x7F25683E)));
                if (animation.getValue().equals((Object)animations.Both) || animation.getValue().equals((Object)animations.Offhand) && animationMode.getValue().equals((Object)animModes.Heart)) {
                    GlStateManager.scale((double)(altura / Double.longBitsToDouble(Double.doubleToLongBits(1.2213141812862605) ^ 0x7FD78A80BD2C3F81L)), (double)(altura / Double.longBitsToDouble(Double.doubleToLongBits(0.14060805857412004) ^ 0x7FE5FF71E290A07FL)), (double)(altura / Double.longBitsToDouble(Double.doubleToLongBits(0.15044740191835532) ^ 0x7FE741DC4780F5E2L)));
                } else {
                    GlStateManager.scale((float)(scaleLX.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.5355796f) ^ 0x7E291BBF)), (float)(scaleLY.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.14170949f) ^ 0x7F311C4B)), (float)(scaleLZ.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.24059306f) ^ 0x7F565E07)));
                }
                GlStateManager.rotate((float)yawL.getValue().floatValue(), (float)Float.intBitsToFloat(Float.floatToIntBits(2.9921361E38f) ^ 0x7F611A72), (float)Float.intBitsToFloat(Float.floatToIntBits(5.0854316f) ^ 0x7F22BBDB), (float)Float.intBitsToFloat(Float.floatToIntBits(7.1439205E37f) ^ 0x7E56FAC3));
                GlStateManager.rotate((float)pitchL.getValue().floatValue(), (float)Float.intBitsToFloat(Float.floatToIntBits(6.8108497f) ^ 0x7F59F27B), (float)Float.intBitsToFloat(Float.floatToIntBits(1.0165949E37f) ^ 0x7CF4BC7F), (float)Float.intBitsToFloat(Float.floatToIntBits(1.2626087E38f) ^ 0x7EBDF9EB));
                GlStateManager.rotate((float)rollL.getValue().floatValue(), (float)Float.intBitsToFloat(Float.floatToIntBits(2.0254012E38f) ^ 0x7F185FCF), (float)Float.intBitsToFloat(Float.floatToIntBits(9.951271E37f) ^ 0x7E95BAE7), (float)Float.intBitsToFloat(Float.floatToIntBits(29.316168f) ^ 0x7E6A8783));
                return;
            }
            case 2: {
                GlStateManager.translate((float)(xr.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.01317834f) ^ 0x7E9FE9F7)), (float)(yr.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.080546595f) ^ 0x7F6CF59D)), (float)(zr.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.107238755f) ^ 0x7F139FFE)));
                if (animation.getValue().equals((Object)animations.Both) || animation.getValue().equals((Object)animations.Mainhand) && animationMode.getValue().equals((Object)animModes.Heart)) {
                    GlStateManager.scale((double)(altura / Double.longBitsToDouble(Double.doubleToLongBits(1.3930622224604916) ^ 0x7FD249FB9CEC2C99L)), (double)(altura / Double.longBitsToDouble(Double.doubleToLongBits(0.23815114052124278) ^ 0x7FEA7BBC90059C7DL)), (double)(altura / Double.longBitsToDouble(Double.doubleToLongBits(1.0008509574158981) ^ 0x7FD4037C4B24585BL)));
                } else {
                    GlStateManager.scale((float)(scaleRX.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.23591617f) ^ 0x7F519402)), (float)(scaleRY.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(1.746698f) ^ 0x7EFF93CD)), (float)(scaleRZ.getValue().floatValue() / Float.intBitsToFloat(Float.floatToIntBits(0.87534136f) ^ 0x7E40165F)));
                }
                GlStateManager.rotate((float)yawR.getValue().floatValue(), (float)Float.intBitsToFloat(Float.floatToIntBits(1.84379E38f) ^ 0x7F0AB61B), (float)Float.intBitsToFloat(Float.floatToIntBits(5.2929015f) ^ 0x7F295F73), (float)Float.intBitsToFloat(Float.floatToIntBits(9.71058E37f) ^ 0x7E921BCB));
                GlStateManager.rotate((float)pitchR.getValue().floatValue(), (float)Float.intBitsToFloat(Float.floatToIntBits(8.37643f) ^ 0x7E8605DB), (float)Float.intBitsToFloat(Float.floatToIntBits(7.930389E37f) ^ 0x7E6EA57F), (float)Float.intBitsToFloat(Float.floatToIntBits(2.1885302E38f) ^ 0x7F24A58F));
                GlStateManager.rotate((float)rollR.getValue().floatValue(), (float)Float.intBitsToFloat(Float.floatToIntBits(4.3508846E37f) ^ 0x7E02EDFF), (float)Float.intBitsToFloat(Float.floatToIntBits(1.0768742E36f) ^ 0x7B4F65FF), (float)Float.intBitsToFloat(Float.floatToIntBits(225.541f) ^ 0x7CE18A7F));
                return;
            }
        }
    }

    public static enum animModes {
        Heart;

    }

    public static enum animations {
        None,
        Offhand,
        Mainhand,
        Both;

    }
}

