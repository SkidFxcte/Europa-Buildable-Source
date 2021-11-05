package com.europa.api.manager.module;

import com.europa.api.manager.event.impl.render.EventRender2D;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import com.europa.api.manager.event.impl.render.EventRender3D;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import com.europa.api.manager.event.impl.player.EventMotionUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import com.europa.api.manager.value.Value;
import java.util.function.Function;
import java.util.Comparator;
import com.europa.client.modules.client.ModuleHUDEditor;
import com.europa.client.modules.client.ModuleParticles;
import com.europa.client.modules.client.ModuleFont;
import com.europa.client.modules.client.ModuleColor;
import com.europa.client.modules.client.ModuleStreamerMode;
import com.europa.client.modules.client.ModuleGUI;
import com.europa.client.modules.client.ModuleHud;
import com.europa.client.modules.client.ModuleNotifications;
import com.europa.client.modules.client.ModuleMiddleClick;
import com.europa.client.modules.remove.ModuleViewModel;
import com.europa.client.modules.render.ModuleTracer;
import com.europa.client.modules.render.ModuleShulkerViewer;
import com.europa.client.modules.render.ModuleLogoutSpots;
import com.europa.client.modules.remove.ModuleBlockOutline;
import com.europa.client.modules.render.ModuleNametags;
import com.europa.client.modules.render.ModuleSkeleton;
import com.europa.client.modules.render.ModuleSkyColor;
import com.europa.client.modules.render.ModuleNoRender;
import com.europa.client.modules.render.ModuleFullBright;
import com.europa.client.modules.render.ModuleESP;
import com.europa.client.modules.render.ModulePlayerChams;
import com.europa.client.modules.render.ModuleCrystalChams;
import com.europa.client.modules.render.ModuleViewClip;
import com.europa.client.modules.render.ModuleCustomFOV;
import com.europa.client.modules.render.ModuleWallhack;
import com.europa.client.modules.render.ModuleTrails;
import com.europa.client.modules.render.ModuleHitmarkers;
import com.europa.client.modules.render.ModuleCityESP;
import com.europa.client.modules.render.ModuleCrosshair;
import com.europa.client.modules.render.ModuleModelChanger;
import com.europa.client.modules.render.ModulePopChams;
import com.europa.client.modules.render.ModuleGlintModify;
import com.europa.client.modules.render.ModuleBreadCrumbs;
import com.europa.client.modules.render.ModuleShaderChams;
import com.europa.client.modules.render.ModuleHoleESP;
import com.europa.client.modules.render.ModuleAnimations;
import com.europa.client.modules.movement.ModuleVelocity;
import com.europa.client.modules.movement.ModuleStep;
import com.europa.client.modules.movement.ModuleSpeed;
import com.europa.client.modules.movement.ModuleFastFall;
import com.europa.client.modules.remove.ModuleIceSpeed;
import com.europa.client.modules.movement.ModuleFastSwim;
import com.europa.client.modules.remove.ModuleAntiWebSpam;
import com.europa.client.modules.movement.ModuleAntiWeb;
import com.europa.client.modules.movement.ModuleAntiLevitation;
import com.europa.client.modules.movement.ModuleNoSlow;
import com.europa.client.modules.movement.ModuleSprint;
import com.europa.client.modules.movement.ModuleAnchor;
import com.europa.client.modules.misc.ModuleWelcomer;
import com.europa.client.modules.misc.ModuleTimer;
import com.europa.client.modules.misc.ModuleEntityFinder;
import com.europa.client.modules.misc.ModuleChatModifier;
import com.europa.client.modules.misc.ModuleAutoReconnect;
import com.europa.client.modules.misc.ModuleAutoGhastFarmer;
import com.europa.client.modules.misc.ModuleAutoRespawn;
import com.europa.client.modules.client.ModuleDiscordPresence;
import com.europa.client.modules.misc.ModuleSpawns;
import com.europa.client.modules.misc.ModuleAnnouncer;
import com.europa.client.modules.misc.ModuleKillsay;
import com.europa.client.modules.misc.ModuleTest;
import com.europa.client.modules.player.ModuleNoEntityTrace;
import com.europa.client.modules.player.ModuleMultiTask;
import com.europa.client.modules.player.ModuleSpeedMine;
import com.europa.client.modules.player.ModuleReach;
import com.europa.client.modules.player.ModulePacketMine;
import com.europa.client.modules.remove.ModuleFakePlayer;
import com.europa.client.modules.remove.ModuleHotbarFill;
import com.europa.client.modules.player.ModuleFastPlace;
import com.europa.client.modules.player.ModuleAntiHunger;
import com.europa.client.modules.misc.ModuleAutoFish;
import com.europa.client.modules.player.ModuleAutoTool;
import com.europa.client.modules.player.ModuleAntiVoid;
import com.europa.client.modules.player.ModuleChorusPostpone;
import com.europa.client.modules.combat.ModuleSurround;
import com.europa.client.modules.combat.ModuleSelfFill;
import com.europa.client.modules.remove.ModulePopCounter;
import com.europa.client.modules.combat.ModuleHoleFill;
import com.europa.client.modules.combat.ModuleCriticals;
import com.europa.client.modules.combat.ModuleCrystalBasePlace;
import com.europa.client.modules.combat.ModuleAutoTrap;
import com.europa.client.modules.remove.ModuleAutoTotem;
import com.europa.client.modules.combat.ModuleAutoCrystal;
import com.europa.client.modules.remove.ModuleAutoArmor;
import com.europa.client.modules.combat.ModuleKillAura;
import com.europa.client.modules.combat.ModuleSurroundBreaker;
import com.europa.client.modules.render.ModuleKillEffects;
import net.minecraftforge.common.MinecraftForge;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class ModuleManager
{
    public static Minecraft mc;
    public ArrayList<Module> modules;

    public ModuleManager() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.modules = new ArrayList<Module>();
        this.addModule(new ModuleKillEffects());
        this.addModule(new ModuleSurroundBreaker());
        this.addModule(new ModuleKillAura());
        this.addModule(new ModuleAutoArmor());
        this.addModule(new ModuleAutoCrystal());
        this.addModule(new ModuleAutoTotem());
        this.addModule(new ModuleAutoTrap());
        this.addModule(new ModuleCrystalBasePlace());
        this.addModule(new ModuleCriticals());
        this.addModule(new ModuleHoleFill());
        this.addModule(new ModulePopCounter());
        this.addModule(new ModuleSelfFill());
        this.addModule(new ModuleSurround());
        this.addModule(new ModuleChorusPostpone());
        this.addModule(new ModuleAntiVoid());
        this.addModule(new ModuleAutoTool());
        this.addModule(new ModuleAutoFish());
        this.addModule(new ModuleAntiHunger());
        this.addModule(new ModuleFastPlace());
        this.addModule(new ModuleHotbarFill());
        this.addModule(new ModuleFakePlayer());
        this.addModule(new ModulePacketMine());
        this.addModule(new ModuleReach());
        this.addModule(new ModuleSpeedMine());
        this.addModule(new ModuleMultiTask());
        this.addModule(new ModuleNoEntityTrace());
        this.addModule(new ModuleTest());
        this.addModule(new ModuleKillsay());
        this.addModule(new ModuleAnnouncer());
        this.addModule(new ModuleSpawns());
        this.addModule(new ModuleDiscordPresence());
        this.addModule(new ModuleAutoRespawn());
        this.addModule(new ModuleAutoGhastFarmer());
        this.addModule(new ModuleAutoReconnect());
        this.addModule(new ModuleChatModifier());
        this.addModule(new ModuleEntityFinder());
        this.addModule(new ModuleTimer());
        this.addModule(new ModuleWelcomer());
        this.addModule(new ModuleAnchor());
        this.addModule(new ModuleSprint());
        this.addModule(new ModuleNoSlow());
        this.addModule(new ModuleAntiLevitation());
        this.addModule(new ModuleAntiWeb());
        this.addModule(new ModuleAntiWebSpam());
        this.addModule(new ModuleFastSwim());
        this.addModule(new ModuleIceSpeed());
        this.addModule(new ModuleFastFall());
        this.addModule(new ModuleSpeed());
        this.addModule(new ModuleStep());
        this.addModule(new ModuleVelocity());
        this.addModule(new ModuleAnimations());
        this.addModule(new ModuleHoleESP());
        this.addModule(new ModuleShaderChams());
        this.addModule(new ModuleBreadCrumbs());
        this.addModule(new ModuleGlintModify());
        this.addModule(new ModulePopChams());
        this.addModule(new ModuleModelChanger());
        this.addModule(new ModuleCrosshair());
        this.addModule(new ModuleCityESP());
        this.addModule(new ModuleHitmarkers());
        this.addModule(new ModuleTrails());
        this.addModule(new ModuleWallhack());
        this.addModule(new ModuleCustomFOV());
        this.addModule(new ModuleViewClip());
        this.addModule(new ModuleCrystalChams());
        this.addModule(new ModulePlayerChams());
        this.addModule(new ModuleESP());
        this.addModule(new ModuleFullBright());
        this.addModule(new ModuleNoRender());
        this.addModule(new ModuleSkyColor());
        this.addModule(new ModuleSkeleton());
        this.addModule(new ModuleNametags());
        this.addModule(new ModuleBlockOutline());
        this.addModule(new ModuleLogoutSpots());
        this.addModule(new ModuleShulkerViewer());
        this.addModule(new ModuleTracer());
        this.addModule(new ModuleViewModel());
        this.addModule(new ModuleMiddleClick());
        this.addModule(new ModuleNotifications());
        this.addModule(new ModuleHud());
        this.addModule(new ModuleGUI());
        this.addModule(new ModuleStreamerMode());
        this.addModule(new ModuleColor());
        this.addModule(new ModuleFont());
        this.addModule(new ModuleParticles());
        this.addModule(new ModuleHUDEditor());
        this.modules.sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
    }

    public void addModule(final Module module) {
        IllegalAccessException ex;
        try {
            for (final Field field : module.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    module.addValue((Value)field.get(module));
                }
            }
            module.addValue(module.tag);
            module.addValue(module.chatNotify);
            module.addValue(module.drawn);
            module.addValue(module.bind);
            this.modules.add(module);
            return;
        }
        catch (IllegalAccessException e) {
            ex = e;
        }
        ex.printStackTrace();
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public ArrayList<Module> getModules(final ModuleCategory category) {
        return (ArrayList<Module>) this.modules.stream().filter(ModuleManager::lambda$getModules$0);
    }

    private static boolean lambda$getModules$0(Module module) {
        return false;
    }

    public Module getModule(final String name) {
        for (final Module module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public boolean isModuleEnabled(final String name) {
        final Module module = this.modules.stream().filter(ModuleManager::lambda$isModuleEnabled$1).findFirst().orElse(null);
        return module != null && module.isToggled();
    }

    private static boolean lambda$isModuleEnabled$1(Module module) {
        return false;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (ModuleManager.mc.player != null) {
            if (ModuleManager.mc.world != null) {
                this.modules.stream().filter(Module::isToggled).forEach(Module::onUpdate);
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(final EventMotionUpdate event) {
        if (ModuleManager.mc.player != null && ModuleManager.mc.world != null) {
            this.modules.stream().filter(Module::isToggled).forEach(Module::onMotionUpdate);
        }
    }

    @SubscribeEvent
    public void onRender2D(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            this.modules.stream().filter(Module::isToggled).forEach(ModuleManager::lambda$onRender2D$2);
            GlStateManager.color(Float.intBitsToFloat(Float.floatToIntBits(4.4062624f) ^ 0x7F0D001A), Float.intBitsToFloat(Float.floatToIntBits(6.6551237f) ^ 0x7F54F6C6), Float.intBitsToFloat(Float.floatToIntBits(12.824576f) ^ 0x7ECD3177), Float.intBitsToFloat(Float.floatToIntBits(31.08959f) ^ 0x7E78B77B));
        }
    }

    private static void lambda$onRender2D$2(Module module) {
    }

    @SubscribeEvent
    public void onRender3D(final RenderWorldLastEvent event) {
        ModuleManager.mc.mcProfiler.startSection("europa");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(Float.intBitsToFloat(Float.floatToIntBits(7.7310553f) ^ 0x7F7764CE));
        final EventRender3D renderEvent = new EventRender3D(event.getPartialTicks());
        this.modules.stream().filter(Module::isToggled).forEach(ModuleManager::lambda$onRender3D$3);
        GlStateManager.glLineWidth(Float.intBitsToFloat(Float.floatToIntBits(10.098111f) ^ 0x7EA191DD));
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        ModuleManager.mc.mcProfiler.endSection();
    }

    private static void lambda$onRender3D$3(Module module) {

    }

    @SubscribeEvent
    public void onLogin(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.modules.stream().filter(Module::isToggled).forEach(Module::onLogin);
    }

    @SubscribeEvent
    public void onLogout(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.modules.stream().filter(Module::isToggled).forEach(Module::onLogout);
    }

    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 0) {
                return;
            }
            for (final Module module : this.modules) {
                if (module.getBind() == Keyboard.getEventKey()) {
                    module.toggle();
                }
            }
        }
    }

    public static void lambda$onRender3D$3(final EventRender3D renderEvent, final Module mm) {
        mm.onRender3D(renderEvent);
    }

    public static void lambda$onRender2D$2(final RenderGameOverlayEvent.Post event, final Module m) {
        m.onRender2D(new EventRender2D(event.getPartialTicks()));
    }

    public static boolean lambda$isModuleEnabled$1(final String name, final Module m) {
        return m.getName().equals(name);
    }

    public static boolean lambda$getModules$0(final ModuleCategory category, final Module m) {
        return m.getCategory().equals(category);
    }

    static {
        ModuleManager.mc = Minecraft.getMinecraft();
    }
}
