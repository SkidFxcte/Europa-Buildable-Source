/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.elements;

import com.europa.api.manager.element.Element;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;

public class ElementPlayerViewer
extends Element {
    public static ValueBoolean viewTarget = new ValueBoolean("ViewTarget", "ViewTarget", "Views the nearest player, and when there are no players it renders you.", false);
    public static ValueEnum lookMode = new ValueEnum("Look", "Look", "The mode for the player's looking.", LookModes.None);
    public static ValueNumber scale = new ValueNumber("Scale", "Scale", "The scale for the player.", 3, 1, 10);

    public ElementPlayerViewer() {
        super("PlayerViewer", "Player Viewer", "Renders yourself on the screen.");
    }

    public static enum LookModes {
        None,
        Free,
        Mouse;

    }
}

