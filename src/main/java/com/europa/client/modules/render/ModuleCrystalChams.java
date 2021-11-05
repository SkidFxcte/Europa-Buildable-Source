/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValuePreview;
import com.europa.api.utilities.render.RainbowUtils;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.World;

public class ModuleCrystalChams
extends Module {
    public ValuePreview preview;
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "", modes.Fill);
    public static ValueNumber size = new ValueNumber("CrystalSize", "CrystalSize", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(3.4975066f) ^ 0x7F5FD726)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(69.30632f) ^ 0x7F46501B)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.4137918f) ^ 0x7E94F721)));
    public static ValueNumber crystalSpeed = new ValueNumber("CrystalSpeed", "CrystalSpeed", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.4599326f) ^ 0x7EAB7C49)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(12879.924f) ^ 0x7B85F37F)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.021654459f) ^ 0x7EF964B1)));
    public static ValueNumber crystalBounce = new ValueNumber("CrystalBounce", "CrystalBounce", "", Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(15.202122f) ^ 0x7F3FF729)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(1.8727913E38f) ^ 0x7F0CE4A6)), Float.valueOf(Float.intBitsToFloat(Float.floatToIntBits(0.7651908f) ^ 0x7E63E38B)));
    public static ValueBoolean insideCube = new ValueBoolean("InsideCube", "InsideCube", "", true);
    public static ValueBoolean outsideCube = new ValueBoolean("OutsideCube", "OutsideCube", "", true);
    public static ValueBoolean outsideCube2 = new ValueBoolean("OutsideCube2", "OutsideCube2", "", true);
    public static ValueBoolean texture = new ValueBoolean("Texture", "Texture", "", false);
    public static ValueBoolean enchanted = new ValueBoolean("Glint", "Glint", "", false);
    public static ValueColor enchantedColor = new ValueColor("GlintColor", "GlintColor", "", new Color(0, 255, 120, 255));
    public static ValueBoolean outline = new ValueBoolean("Outline", "Outline", "", false);
    public static ValueEnum outlineMode = new ValueEnum("OutlineMode", "OutlineMode", "", outlineModes.Wire);
    public static ValueNumber width = new ValueNumber("Width", "Width", "", Double.longBitsToDouble(Double.doubleToLongBits(0.2708256168642957) ^ 0x7FD95534F7D679ABL), Double.longBitsToDouble(Double.doubleToLongBits(2.9779557303322504) ^ 0x7FE7D2DA7435B3D6L), Double.longBitsToDouble(Double.doubleToLongBits(0.20847645645433857) ^ 0x7FDEAF5B453A8A85L));
    public static ValueBoolean syncColor = new ValueBoolean("SyncColor", "SyncColor", "", true);
    public static ValueColor daColor = new ValueColor("Color", "Color", "", new Color(0, 255, 120, 255));
    public static ValueColor hiddenColor = new ValueColor("HiddenColor", "HiddenColor", "", new Color(0, 100, 255, 255));
    public static ValueBoolean hiddenSync = new ValueBoolean("HiddenSync", "HiddenSync", "", true);
    public static ValueColor outlineColor = new ValueColor("OutColor", "OutColor", "", new Color(255, 255, 255));
    public static ValueBoolean lifetimeColor = new ValueBoolean("Lifetime", "Lifetime", "", false);
    public static ValueNumber lifeTime = new ValueNumber("LifeSpeed", "LifeSpeed", "", 400, 50, 500);
    public static ValueColor lifeStart = new ValueColor("LifeColorStart", "LifeColorStart", "", new Color(255, 0, 0));
    public static ValueColor lifeEnd = new ValueColor("LifeColorEnd", "LifeColorEnd", "", new Color(0, 0, 255));
    public static Color color;
    public static Color outColor;
    public static Color hideColor;
    public static HashMap<UUID, Thingering> thingers;

    public ModuleCrystalChams() {
        super("CrystalChams", "Crystal Chams", "Renders chams over crystals to make them look better.", ModuleCategory.RENDER);
        this.preview = new ValuePreview("CrystalPreview", "CrystalPreview", "", (Entity)new EntityEnderCrystal((World)ModuleCrystalChams.mc.world));
    }

    @Override
    public void onUpdate() {
        if (syncColor.getValue()) {
            color = ModuleCrystalChams.globalColor(255);
            outColor = ModuleCrystalChams.globalColor(255);
            hideColor = ModuleCrystalChams.globalColor(255);
        } else {
            color = daColor.getValue();
            outColor = outlineColor.getValue();
            hideColor = hiddenColor.getValue();
        }
        for (Entity entity : ModuleCrystalChams.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || thingers.containsKey(entity.getUniqueID())) continue;
            thingers.put(entity.getUniqueID(), new Thingering(this, entity));
            ModuleCrystalChams.thingers.get((Object)entity.getUniqueID()).starTime = System.currentTimeMillis();
        }
        if (ModuleCrystalChams.mc.player == null || ModuleCrystalChams.mc.world == null) {
            return;
        }
        int others = lifeTime.getValue().intValue() * 10;
        for (Map.Entry<UUID, Thingering> entry : thingers.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue().starTime >= (long)others) continue;
            float offset = Float.intBitsToFloat(Float.floatToIntBits(3.5727036E37f) ^ 0x7DD70637);
            long time = System.currentTimeMillis();
            long duration = time - entry.getValue().starTime;
            if (duration < (long)(lifeTime.getValue().intValue() * 10)) {
                offset = Float.intBitsToFloat(Float.floatToIntBits(7.7510977f) ^ 0x7F7808FE) - (float)duration / (float)others;
            }
            entry.getValue().color = RainbowUtils.getGradientAlpha(lifeEnd.getValue(), lifeStart.getValue(), offset);
        }
    }

    static {
        thingers = new HashMap();
    }

    public class Thingering {
        public Entity entity;
        public long starTime;
        public Color color;
        public ModuleCrystalChams this$0;

        public Thingering(ModuleCrystalChams this$0, Entity entity) {
            this.this$0 = this$0;
            this.entity = entity;
            this.starTime = (long)1417623533 ^ 0x547F37EDL;
        }
    }

    public static enum outlineModes {
        Wire,
        Flat;

    }

    public static enum modes {
        Fill,
        Wireframe;

    }
}

