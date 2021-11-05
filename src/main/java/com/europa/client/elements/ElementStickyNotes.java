/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.elements;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.api.manager.event.impl.render.EventRender2D;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.client.modules.client.ModuleColor;

public class ElementStickyNotes
extends Element {
    public static ValueEnum lines = new ValueEnum("Lines", "Lines", "The amount of lines that should be rendered.", LinesAmount.One);
    public static ValueString lineOne = new ValueString("LineOne", "LineOne", "The first line.", "Placeholder");
    public static ValueString lineTwo = new ValueString("LineTwo", "LineTwo", "The second line.", "Placeholder");
    public static ValueString lineThree = new ValueString("LineThree", "LineThree", "The third line.", "Placeholder");
    public static ValueString lineFour = new ValueString("LineFour", "LineFour", "The fourth line.", "Placeholder");

    public ElementStickyNotes() {
        super("StickyNotes", "Sticky Notes", "Let's you write custom stuff on the screen.");
    }

    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        this.frame.setWidth(Europa.FONT_MANAGER.getStringWidth(ElementStickyNotes.lineOne.getValue()));
        this.frame.setHeight(Europa.FONT_MANAGER.getHeight() * this.getMultiplier() + this.getMultiplier());
        for (int i = 0; i <= this.getMultiplier() - 1; ++i) {
            Europa.FONT_MANAGER.drawString((i == 1) ? ElementStickyNotes.lineTwo.getValue() : ((i == 2) ? ElementStickyNotes.lineThree.getValue() : ((i == 3) ? ElementStickyNotes.lineFour.getValue() : ElementStickyNotes.lineOne.getValue())), this.frame.getX(), this.frame.getY() + (Europa.FONT_MANAGER.getHeight() + Float.intBitsToFloat(Float.floatToIntBits(14.699412f) ^ 0x7EEB30CB)) * i, ModuleColor.getActualColor());
        }
    }


    public int getMultiplier() {
        switch ((LinesAmount)ElementStickyNotes.lines.getValue()) {
            case Two: {
                return 2;
            }
            case Three: {
                return 3;
            }
            case Four: {
                return 4;
            }
            default: {
                return 1;
            }
        }
    }

    static {
        ElementStickyNotes.lines = new ValueEnum("Lines", "Lines", "The amount of lines that should be rendered.", LinesAmount.One);
        ElementStickyNotes.lineOne = new ValueString("LineOne", "LineOne", "The first line.", "Placeholder");
        ElementStickyNotes.lineTwo = new ValueString("LineTwo", "LineTwo", "The second line.", "Placeholder");
        ElementStickyNotes.lineThree = new ValueString("LineThree", "LineThree", "The third line.", "Placeholder");
        ElementStickyNotes.lineFour = new ValueString("LineFour", "LineFour", "The fourth line.", "Placeholder");
    }

    public static enum LinesAmount {
        One,
        Two,
        Three,
        Four;
    }
}

