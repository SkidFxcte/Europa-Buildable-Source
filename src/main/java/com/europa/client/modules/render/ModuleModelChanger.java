/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.render;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueNumber;

public class ModuleModelChanger
extends Module {
    public static ValueBoolean activeHand = new ValueBoolean("ActiveIgnore", "ActiveIgnore", "ActiveIgnore", false);
    public static ValueNumber translateX = new ValueNumber("TranslateX", "TranslateX", "The X for the translation.", 0, -360, 360);
    public static ValueNumber translateY = new ValueNumber("TranslateY", "TranslateY", "The Y for the translation.", 0, -360, 360);
    public static ValueNumber translateZ = new ValueNumber("TranslateZ", "TranslateZ", "The Z for the translation.", 0, -360, 360);
    public static ValueNumber rotateX = new ValueNumber("RotateX", "RotateX", "The X for the rotation.", 0, -360, 360);
    public static ValueNumber rotateY = new ValueNumber("RotateY", "RotateY", "The Y for the rotation.", 0, -360, 360);
    public static ValueNumber rotateZ = new ValueNumber("RotateZ", "RotateZ", "The Z for the rotation.", 0, -360, 360);
    public static ValueNumber scaleX = new ValueNumber("ScaleX", "ScaleX", "The X for the scale.", 0, -360, 360);
    public static ValueNumber scaleY = new ValueNumber("ScaleY", "ScaleY", "The Y for the scale.", 0, -360, 360);
    public static ValueNumber scaleZ = new ValueNumber("ScaleZ", "ScaleZ", "The Z for the scale.", 0, -360, 360);

    public ModuleModelChanger() {
        super("ModelChanger", "Model Changer", "Let's you change your viewmodel.", ModuleCategory.RENDER);
    }
}

