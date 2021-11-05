/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.client;

import com.europa.Europa;
import com.europa.api.manager.event.impl.network.EventPacket;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.api.utilities.math.AnimationUtils;
import com.europa.api.utilities.math.TimerUtils;
import com.europa.api.utilities.render.GifLocation;
import com.europa.api.utilities.render.RainbowUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleHud
extends Module {
    public static ValueEnum colorMode = new ValueEnum("ColorMode", "ColorMode", "", colorModes.Wave);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(255, 0, 255));
    public static ValueColor gradient1 = new ValueColor("Gradient1", "Gradient1", "", new Color(255, 0, 255));
    public static ValueColor gradient2 = new ValueColor("Gradient2", "Gradient2", "", new Color(0, 0, 255));
    public static ValueNumber rainbowOffset = new ValueNumber("RainbowOffset", "RainbowOffset", "", 255, 0, 255);
    public static ValueNumber rainbowSat = new ValueNumber("RainbowSaturation", "RainbowSat", "", 255, 0, 255);
    public static ValueNumber rainbowBri = new ValueNumber("RainbowBrightness", "RainbowSat", "", 255, 0, 255);
    public static ValueEnum infoColor = new ValueEnum("InfoColor", "InfoColor", "", infoColors.Gray);
    public static ValueEnum waterMode = new ValueEnum("WaterMode", "WaterMode", "", waterModes.Default);
    public static ValueString waterString = new ValueString("WaterString", "WaterStrig", "", "Europa");
    public static ValueBoolean watermark = new ValueBoolean("Watermark", "Watermark", "", true);
    public static ValueBoolean watermarkVersion = new ValueBoolean("WatermarkVersion", "WatermarkVersion", "", true);
    public static ValueBoolean welcomer = new ValueBoolean("Welcomer", "Welcomer", "", false);
    public static ValueBoolean fps = new ValueBoolean("FPS", "FPS", "", true);
    public static ValueBoolean tps = new ValueBoolean("TPS", "TPS", "", true);
    public static ValueBoolean ping = new ValueBoolean("Ping", "Ping", "", true);
    public static ValueBoolean packetPS = new ValueBoolean("Packets", "Packets", "", false);
    public static ValueBoolean speed = new ValueBoolean("Speed", "Speed", "", true);
    public static ValueBoolean brand = new ValueBoolean("ServerBrand", "ServerBrand", "", true);
    public static ValueEnum effectHud = new ValueEnum("EffectHud", "EffectHud", "", effectHudModes.Move);
    public static ValueBoolean potionEffects = new ValueBoolean("Effects", "Effect", "", true);
    public static ValueBoolean potionSync = new ValueBoolean("EffectSync", "EffectSync", "", false);
    public static ValueBoolean coords = new ValueBoolean("Coords", "Coords", "", true);
    public static ValueBoolean netherCoords = new ValueBoolean("NetherCoords", "NetherCoords", "", true);
    public static ValueBoolean direction = new ValueBoolean("Direction", "Direction", "", true);
    public static ValueBoolean lagNotifier = new ValueBoolean("LagNotifier", "LagNotifier", "", false);
    public static ValueBoolean rubberNotifier = new ValueBoolean("RubberNotifier", "RubberNotifier", "", false);
    public static ValueBoolean armor = new ValueBoolean("Armor", "Armor", "", false);
    public static ValueBoolean arrayList = new ValueBoolean("ArrayList", "ArrayList", "", true);
    public static ValueEnum arrayRendering = new ValueEnum("Rendering", "Rendering", "", renderingModes.Up);
    public static ValueEnum ordering = new ValueEnum("Ordering", "Ordering", "", orderModes.Length);
    public int components;
    public int leftComponents;
    public int packets;
    public AnimationUtils anim = new AnimationUtils((long)-1959169653 ^ 0xFFFFFFFF8B39707FL, Float.intBitsToFloat(Float.floatToIntBits(9.571654f) ^ 0x7E99257F), Float.intBitsToFloat(Float.floatToIntBits(0.03032185f) ^ 0x7E306587));
    public TimerUtils packetTimer = new TimerUtils();
    public Color colorHud;
    public boolean rubberbanded;
    public TimerUtils serverTimer = new TimerUtils();
    public TimerUtils rubberTimer = new TimerUtils();
    public static RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

    public ModuleHud() {
        super("Hud", "Hud", "", ModuleCategory.CLIENT);
    }

    public void renderGif() {
        GifLocation gif = new GifLocation("clientname/ban", 44, 1);
        gif.update();
        mc.getTextureManager().bindTexture(gif.getTexture());
        Gui.drawModalRectWithCustomSizedTexture((int)0, (int)0, (float)Float.intBitsToFloat(Float.floatToIntBits(1.2337768E38f) ^ 0x7EB9A35B), (float)Float.intBitsToFloat(Float.floatToIntBits(8.861761E37f) ^ 0x7E855643), (int)498, (int)494, (float)Float.intBitsToFloat(Float.floatToIntBits(0.037162792f) ^ 0x7EE13803), (float)Float.intBitsToFloat(Float.floatToIntBits(0.024583342f) ^ 0x7F3E6301));
    }

    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        this.serverTimer.reset();
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.rubberbanded = true;
            this.rubberTimer.reset();
        }
    }

    @SubscribeEvent
    public void onSend(EventPacket.Send send) {
        ++this.packets;
    }

    @Override
    public void onUpdate() {
        block0: {
            if (!this.packetTimer.hasReached((long)227554878 ^ 0xD9035D6L)) break block0;
            this.packets = 0;
            this.packetTimer.reset();
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onRender2D(EventRender2D var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 1[TRYBLOCK] [11 : 46->52)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public ChatFormatting getInfoColor() {
        if (infoColor.getValue().equals((Object)infoColors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.WHITE;
    }

    /*
     * WARNING - void declaration
     */
    public void drawStringWithShadow(String string, float f, float f2, int n) {
        Europa.FONT_MANAGER.drawString((String) text, (float) x, (float) y, new Color((int) color));
    }

    public float getStringWidth(final String text) {
        return Europa.FONT_MANAGER.getStringWidth(text);
    }

    /*
     * WARNING - void declaration
     */
    public void drawRainbowString(String string, float f, float f2, int n) {
        void text;
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        int[] counterChing = new int[]{1};
        for (int i = 0; i < text.length(); ++i) {
            void y;
            void x;
            Color color = RainbowUtils.anyRainbowColor(counterChing[0] * rainbowOffset.getValue().intValue(), rainbowSat.getValue().intValue(), rainbowBri.getValue().intValue());
            char currentChar = text.charAt(i);
            char nextChar = text.charAt(MathHelper.clamp((int)(i + 1), (int)0, (int)(text.length() - 1)));
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                shouldRainbow = false;
            } else if ((String.valueOf(currentChar) + nextChar).equals("\u00a7+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
                continue;
            }
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                String escapeString = text.subString(i);
                Europa.FONT_MANAGER.drawString(escapeString, (float)(x + (float)currentWidth), (float)y, Color.WHITE);
                break;
            }
            Europa.FONT_MANAGER.drawString(String.valueOf(currentChar).equals("\u00a7") ? "" : String.valueOf(currentChar), (float)(x + (float)currentWidth), (float)y, shouldRainbow ? color : Color.WHITE);
            if (String.valueOf(currentChar).equals("\u00a7")) {
                shouldContinue = true;
            }
            currentWidth = (int)((float)currentWidth + this.getStringWidth(String.valueOf(currentChar)));
            if (String.valueOf(currentChar).equals(" ")) continue;
            counterChing[0] = counterChing[0] + 1;
        }
    }

    public static String getFacing() {
        switch (MathHelper.floor((double)((double)(ModuleHud.mc.player.rotationYaw * Float.intBitsToFloat(Float.floatToIntBits(0.24909489f) ^ 0x7F7F12BB) / Float.intBitsToFloat(Float.floatToIntBits(0.07333299f) ^ 0x7E222F9B)) + Double.longBitsToDouble(Double.doubleToLongBits(29.27572401542503) ^ 0x7FDD4695D95CF8E9L))) & 7) {
            case 0: {
                return "South";
            }
            case 1: {
                return "South";
            }
            case 2: {
                return "West";
            }
            case 3: {
                return "West";
            }
            case 4: {
                return "North";
            }
            case 5: {
                return "North";
            }
            case 6: {
                return "East";
            }
            case 7: {
                return "East";
            }
        }
        return "Invalid";
    }

    public static String getTowards() {
        switch (MathHelper.floor((double)((double)(ModuleHud.mc.player.rotationYaw * Float.intBitsToFloat(Float.floatToIntBits(1.0236098f) ^ 0x7E8305A5) / Float.intBitsToFloat(Float.floatToIntBits(0.01776704f) ^ 0x7F258C2F)) + Double.longBitsToDouble(Double.doubleToLongBits(21.564711397510802) ^ 0x7FD59090ED17FCC3L))) & 7) {
            case 0: {
                return "+Z";
            }
            case 1: {
                return "+Z";
            }
            case 2: {
                return "-X";
            }
            case 3: {
                return "-X";
            }
            case 4: {
                return "-Z";
            }
            case 5: {
                return "-Z";
            }
            case 6: {
                return "+X";
            }
            case 7: {
                return "+X";
            }
        }
        return "Invalid";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getPing() {
        if (ModuleHud.mc.player == null) return -1;
        if (mc.getConnection() == null) return -1;
        if (mc.getConnection().getPlayerInfo(ModuleHud.mc.player.getName()) == null) {
            return -1;
        }
        ModuleHud.mc.player.getName();
        return Objects.requireNonNull(mc.getConnection().getPlayerInfo(ModuleHud.mc.player.getName())).getResponseTime();
    }

    public static String getServerBrand() {
        String s;
        if (mc.getCurrentServerData() == null) {
            s = "Vanilla";
        } else {
            EntityPlayerSP it = ModuleHud.mc.player;
            boolean n = false;
            String getServerBrand = ModuleHud.mc.player.getServerBrand();
            s = getServerBrand == null ? "Vanilla" : getServerBrand;
        }
        return s;
    }
    public void lambda$onRender2D$9(final Color color1, final Color color2, final int[] mods, final Color color, final ScaledResolution scaledRes, final Module m) {
        final String string = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
        final Color gradientColor = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % ((long)2108332745 ^ 0x7DAA9D19L) / Float.intBitsToFloat(Float.floatToIntBits(8.5311214E-4f) ^ 0x7E25A363) + Float.intBitsToFloat(Float.floatToIntBits(0.26764295f) ^ 0x7F29087F) / (mods[0] * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.36572203f) ^ 0x7EBB3FEB)) % Float.intBitsToFloat(Float.floatToIntBits(0.8683099f) ^ 0x7F5E498F) - Float.intBitsToFloat(Float.floatToIntBits(183.88963f) ^ 0x7CB7E3BF)));
        final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
            this.drawStringWithShadow("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(scaledRes.getScaledHeight() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        else {
            this.drawStringWithShadow(string, scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(scaledRes.getScaledHeight() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        final int n = 0;
        ++mods[n];
    }

    public static String lambda$onRender2D$8(Module module) {
        Module module2;
        return module.getTag();
    }


    public void lambda$onRender2D$7(final Color color1, final Color color2, final int[] mods, final Color color, final ScaledResolution scaledRes, final Module m) {
        final String string = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
        final Color gradientColor = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % ((long)(-2097645959) ^ 0xFFFFFFFF82F871A9L) / Float.intBitsToFloat(Float.floatToIntBits(0.0037705372f) ^ 0x7F0D1B1E) + Float.intBitsToFloat(Float.floatToIntBits(0.515287f) ^ 0x7EA3E9D9) / (mods[0] * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.7716851f) ^ 0x7F458D28)) % Float.intBitsToFloat(Float.floatToIntBits(0.67488766f) ^ 0x7F2CC570) - Float.intBitsToFloat(Float.floatToIntBits(10.664624f) ^ 0x7EAAA24D)));
        final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
            this.drawStringWithShadow("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(scaledRes.getScaledHeight() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        else {
            this.drawStringWithShadow(string, scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(scaledRes.getScaledHeight() + mods[0] * -10 - 12), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        final int n = 0;
        ++mods[n];
    }

    public Float lambda$onRender2D$6(final Module module) {
        return this.getStringWidth(module.getTag() + module.getHudInfo()) * Float.intBitsToFloat(Float.floatToIntBits(-7.326278f) ^ 0x7F6A70DF);
    }

    public void lambda$onRender2D$5(final Color color1, final Color color2, final Color color, final ScaledResolution scaledRes, final int[] potCount, final PotionEffect effect) {
        final Color gradientColor = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % ((long)(-2026668457) ^ 0xFFFFFFFF87337987L) / Float.intBitsToFloat(Float.floatToIntBits(0.0028532243f) ^ 0x7F40FD29) + Float.intBitsToFloat(Float.floatToIntBits(0.01413565f) ^ 0x7DC79937) / (this.components * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.28400162f) ^ 0x7E9168A9)) % Float.intBitsToFloat(Float.floatToIntBits(0.39718184f) ^ 0x7ECB5B6B) - Float.intBitsToFloat(Float.floatToIntBits(9.475102f) ^ 0x7E979A05)));
        final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
        final String name = I18n.format(effect.getPotion().getName(), new Object[0]);
        final double duration = effect.getDuration() / Float.intBitsToFloat(Float.floatToIntBits(1.5897324f) ^ 0x7E5497DF);
        final int amplifier = effect.getAmplifier() + 1;
        final int potionColor = effect.getPotion().getLiquidColor();
        final double p1 = duration % Double.longBitsToDouble(Double.doubleToLongBits(0.02308112197540173) ^ 0x7FD9A293E028480DL);
        final DecimalFormat format2 = new DecimalFormat("00");
        final String seconds = format2.format(p1);
        final String s = name + " " + amplifier + this.getInfoColor() + " " + (int)duration / 60 + ":" + seconds;
        final String sR = "§+" + name + " " + amplifier + "§r" + this.getInfoColor() + " " + (int)duration / 60 + ":" + seconds;
        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
            this.drawStringWithShadow(sR, scaledRes.getScaledWidth() - 2 - this.getStringWidth(s), (float)(2 + potCount[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.player.getActivePotionEffects().isEmpty()) ? 25 : 0)), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB())) : potionColor);
        }
        else {
            this.drawStringWithShadow(s, scaledRes.getScaledWidth() - 2 - this.getStringWidth(s), (float)(2 + potCount[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.player.getActivePotionEffects().isEmpty()) ? 25 : 0)), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB())) : potionColor);
        }
        final int n = 0;
        ++potCount[n];
        ++this.components;
    }
    public void lambda$onRender2D$4(final Color color1, final Color color2, final int[] mods, final Color color, final ScaledResolution scaledRes, final Module m) {
        final String string = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
        final Color gradientColor = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % ((long)2117042502 ^ 0x7E2F8696L) / Float.intBitsToFloat(Float.floatToIntBits(0.0010046951f) ^ 0x7EF9AFF9) + Float.intBitsToFloat(Float.floatToIntBits(0.736677f) ^ 0x7E9C96DD) / (mods[0] * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.5016872f) ^ 0x7F006E93)) % Float.intBitsToFloat(Float.floatToIntBits(0.3803688f) ^ 0x7EC2BFB3) - Float.intBitsToFloat(Float.floatToIntBits(4.333528f) ^ 0x7F0AAC43)));
        final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
            this.drawRainbowString("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.player.getActivePotionEffects().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        else {
            this.drawStringWithShadow(string, scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.player.getActivePotionEffects().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        final int n = 0;
        ++mods[n];
    }


    public static String lambda$onRender2D$3(Module module) {
        Module module2;
        return module.getTag();
    }


    public void lambda$onRender2D$2(final Color color1, final Color color2, final int[] mods, final Color color, final ScaledResolution scaledRes, final Module m) {
        final String string = m.getTag() + ChatFormatting.GRAY + m.getHudInfo();
        final Color gradientColor = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % ((long)2129097541 ^ 0x7EE77495L) / Float.intBitsToFloat(Float.floatToIntBits(8.66834E-4f) ^ 0x7E193C3F) + Float.intBitsToFloat(Float.floatToIntBits(1.5896639f) ^ 0x7E6B7A1B) / (mods[0] * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.21881954f) ^ 0x7E60123B)) % Float.intBitsToFloat(Float.floatToIntBits(0.7683363f) ^ 0x7F44B1B0) - Float.intBitsToFloat(Float.floatToIntBits(35.610073f) ^ 0x7D8E70B7)));
        final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, mods[0] * 2 + 10);
        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow)) {
            this.drawRainbowString("§+" + m.getTag() + "§r" + ChatFormatting.GRAY + m.getHudInfo(), scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.player.getActivePotionEffects().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        else {
            this.drawStringWithShadow(string, scaledRes.getScaledWidth() - 2 - this.getStringWidth(string), (float)(2 + mods[0] * 10 + ((ModuleHud.effectHud.getValue().equals(effectHudModes.Move) && !ModuleHud.mc.player.getActivePotionEffects().isEmpty()) ? 25 : 0)), ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB()));
        }
        final int n = 0;
        ++mods[n];
    }

    public Float lambda$onRender2D$1(final Module module) {
        return this.getStringWidth(module.getTag() + module.getHudInfo()) * Float.intBitsToFloat(Float.floatToIntBits(-22.808203f) ^ 0x7E367733);
    }

    public void lambda$onRender2D$0(final Color color1, final Color color2, final Color color, final ScaledResolution scaledRes, final int[] potCount, final PotionEffect effect) {
        final Color gradientColor = RainbowUtils.getGradientOffset(color1, color2, Math.abs((System.currentTimeMillis() % ((long)1193219488 ^ 0x471F1270L) / Float.intBitsToFloat(Float.floatToIntBits(0.0032956018f) ^ 0x7F2DFB06) + Float.intBitsToFloat(Float.floatToIntBits(0.9891066f) ^ 0x7EDD3617) / (this.components * 2 + 10) * Float.intBitsToFloat(Float.floatToIntBits(0.866138f) ^ 0x7F5DBB38)) % Float.intBitsToFloat(Float.floatToIntBits(0.42879692f) ^ 0x7EDB8B45) - Float.intBitsToFloat(Float.floatToIntBits(4.1907325f) ^ 0x7F061A7B)));
        final Color waveColor = RainbowUtils.astolfoRainbow(color, 50, this.components * 2 + 10);
        final String name = I18n.format(effect.getPotion().getName(), new Object[0]);
        final double duration = effect.getDuration() / Float.intBitsToFloat(Float.floatToIntBits(0.4254866f) ^ 0x7F4632E4);
        final int amplifier = effect.getAmplifier() + 1;
        final int potionColor = effect.getPotion().getLiquidColor();
        final double p1 = duration % Double.longBitsToDouble(Double.doubleToLongBits(0.7415499151072334) ^ 0x7FA9BAC6E33796AFL);
        final DecimalFormat format2 = new DecimalFormat("00");
        final String seconds = format2.format(p1);
        final String s = name + " " + amplifier + this.getInfoColor() + " " + (int)duration / 60 + ":" + seconds;
        final String sR = "§+" + name + " " + amplifier + "§r" + this.getInfoColor() + " " + (int)duration / 60 + ":" + seconds;
        if (ModuleHud.colorMode.getValue().equals(colorModes.Rainbow) && ModuleHud.potionSync.getValue()) {
            this.drawRainbowString(sR, scaledRes.getScaledWidth() - 2 - this.getStringWidth(s), (float)(scaledRes.getScaledHeight() + potCount[0] * -10 - 10 - 2), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB())) : potionColor);
        }
        else {
            this.drawStringWithShadow(s, scaledRes.getScaledWidth() - 2 - this.getStringWidth(s), (float)(scaledRes.getScaledHeight() + potCount[0] * -10 - 10 - 2), ModuleHud.potionSync.getValue() ? (ModuleHud.colorMode.getValue().equals(colorModes.Static) ? this.colorHud.getRGB() : (ModuleHud.colorMode.getValue().equals(colorModes.Wave) ? waveColor.getRGB() : gradientColor.getRGB())) : potionColor);
        }
        final int n = 0;
        ++potCount[n];
        ++this.components;
    }

    static {
        ModuleHud.colorMode = new ValueEnum("ColorMode", "ColorMode", "", colorModes.Wave);
        ModuleHud.daColor = new ValueColor("Color", "Color", "", new Color(255, 0, 255));
        ModuleHud.gradient1 = new ValueColor("Gradient1", "Gradient1", "", new Color(255, 0, 255));
        ModuleHud.gradient2 = new ValueColor("Gradient2", "Gradient2", "", new Color(0, 0, 255));
        ModuleHud.rainbowOffset = new ValueNumber("RainbowOffset", "RainbowOffset", "", 255, 0, 255);
        ModuleHud.rainbowSat = new ValueNumber("RainbowSaturation", "RainbowSat", "", 255, 0, 255);
        ModuleHud.rainbowBri = new ValueNumber("RainbowBrightness", "RainbowSat", "", 255, 0, 255);
        ModuleHud.infoColor = new ValueEnum("InfoColor", "InfoColor", "", infoColors.Gray);
        ModuleHud.waterMode = new ValueEnum("WaterMode", "WaterMode", "", waterModes.Default);
        ModuleHud.waterString = new ValueString("WaterString", "WaterStrig", "", "Europa");
        ModuleHud.watermark = new ValueBoolean("Watermark", "Watermark", "", true);
        ModuleHud.watermarkVersion = new ValueBoolean("WatermarkVersion", "WatermarkVersion", "", true);
        ModuleHud.welcomer = new ValueBoolean("Welcomer", "Welcomer", "", false);
        ModuleHud.fps = new ValueBoolean("FPS", "FPS", "", true);
        ModuleHud.tps = new ValueBoolean("TPS", "TPS", "", true);
        ModuleHud.ping = new ValueBoolean("Ping", "Ping", "", true);
        ModuleHud.packetPS = new ValueBoolean("Packets", "Packets", "", false);
        ModuleHud.speed = new ValueBoolean("Speed", "Speed", "", true);
        ModuleHud.brand = new ValueBoolean("ServerBrand", "ServerBrand", "", true);
        ModuleHud.effectHud = new ValueEnum("EffectHud", "EffectHud", "", effectHudModes.Move);
        ModuleHud.potionEffects = new ValueBoolean("Effects", "Effect", "", true);
        ModuleHud.potionSync = new ValueBoolean("EffectSync", "EffectSync", "", false);
        ModuleHud.coords = new ValueBoolean("Coords", "Coords", "", true);
        ModuleHud.netherCoords = new ValueBoolean("NetherCoords", "NetherCoords", "", true);
        ModuleHud.direction = new ValueBoolean("Direction", "Direction", "", true);
        ModuleHud.lagNotifier = new ValueBoolean("LagNotifier", "LagNotifier", "", false);
        ModuleHud.rubberNotifier = new ValueBoolean("RubberNotifier", "RubberNotifier", "", false);
        ModuleHud.armor = new ValueBoolean("Armor", "Armor", "", false);
        ModuleHud.arrayList = new ValueBoolean("ArrayList", "ArrayList", "", true);
        ModuleHud.arrayRendering = new ValueEnum("Rendering", "Rendering", "", renderingModes.Up);
        ModuleHud.ordering = new ValueEnum("Ordering", "Ordering", "", orderModes.Length);
        ModuleHud.itemRender = Minecraft.getMinecraft().getRenderItem();
    }

    public static enum orderModes {
        ABC,
        Length;

    }

    public static enum renderingModes {
        Up,
        Down;

    }

    public static enum colorModes {
        Static,
        Wave,
        Gradient,
        Rainbow;

    }

    public static enum waterModes {
        Default,
        Custom;

    }

    public static enum infoColors {
        Gray,
        White;

    }

    public static enum effectHudModes {
        Hide,
        Keep,
        Move;

    }
}

