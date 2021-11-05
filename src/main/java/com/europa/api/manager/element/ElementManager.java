//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.manager.element;

import com.europa.api.manager.event.impl.render.EventRender2D;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import com.europa.api.manager.event.impl.player.EventMotionUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Iterator;
import java.lang.reflect.Field;
import com.europa.api.manager.value.Value;
import java.util.function.Function;
import java.util.Comparator;
import com.europa.api.manager.module.Module;
import com.europa.client.elements.ElementPlayerViewer;
import com.europa.client.elements.ElementArmor;
import com.europa.client.elements.ElementFriends;
import com.europa.client.elements.ElementStickyNotes;
import com.europa.client.elements.ElementCoordinates;
import com.europa.client.elements.ElementWelcomer;
import com.europa.client.elements.ElementWatermark;
import net.minecraftforge.common.MinecraftForge;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class ElementManager
{
    public static Minecraft mc;
    public ArrayList<Element> elements;

    public ElementManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.elements = new ArrayList<Element>();
        this.addElement(new ElementWatermark());
        this.addElement(new ElementWelcomer());
        this.addElement(new ElementCoordinates());
        this.addElement(new ElementStickyNotes());
        this.addElement(new ElementFriends());
        this.addElement(new ElementArmor());
        this.addElement(new ElementPlayerViewer());
        this.elements.sort(Comparator.comparing((Function<? super Element, ? extends Comparable>)Module::getName));
    }

    public void addElement(final Element element) {
        IllegalAccessException ex;
        try {
            for (final Field field : element.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    element.addValue((Value)field.get(element));
                }
            }
            this.elements.add(element);
            return;
        }
        catch (IllegalAccessException e) {
            ex = e;
        }
        ex.printStackTrace();
    }

    public ArrayList<Element> getElements() {
        return this.elements;
    }

    public Element getElement(final String name) {
        for (final Element module : this.elements) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public boolean isElementEnabled(final String name) {
        final Element module = this.elements.stream().filter(ElementManager::lambda$isElementEnabled$0).findFirst().orElse(null);
        return module != null && module.isToggled();
    }

    private static boolean lambda$isElementEnabled$0(Element element) {
        return false;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (ElementManager.mc.player != null) {
            if (ElementManager.mc.world != null) {
                this.elements.stream().filter(Module::isToggled).forEach(Element::onUpdate);
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(final EventMotionUpdate event) {
        if (ElementManager.mc.player != null && ElementManager.mc.world != null) {
            this.elements.stream().filter(Module::isToggled).forEach(Element::onMotionUpdate);
        }
    }

    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            this.elements.stream().filter(Module::isToggled).forEach(ElementManager::lambda$onRender2D$1);
            GlStateManager.color(Float.intBitsToFloat(Float.floatToIntBits(6.726699f) ^ 0x7F57411E), Float.intBitsToFloat(Float.floatToIntBits(8.872096f) ^ 0x7E8DF41B), Float.intBitsToFloat(Float.floatToIntBits(14.271403f) ^ 0x7EE457AB), Float.intBitsToFloat(Float.floatToIntBits(19.808035f) ^ 0x7E1E76DB));
        }
    }

    private static void lambda$onRender2D$1(Element element) {
    }

    @SubscribeEvent
    public void onLogin(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.elements.stream().filter(Module::isToggled).forEach(Element::onLogin);
    }

    @SubscribeEvent
    public void onLogout(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.elements.stream().filter(Module::isToggled).forEach(Element::onLogout);
    }

    public static void lambda$onRender2D$1(final RenderGameOverlayEvent.Post event, final Element m) {
        m.onRender2D(new EventRender2D(event.getPartialTicks()));
    }

    public static boolean lambda$isElementEnabled$0(final String name, final Element m) {
        return m.getName().equals(name);
    }

    static {
        ElementManager.mc = Minecraft.getMinecraft();
    }
}
