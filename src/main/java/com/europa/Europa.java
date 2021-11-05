/*
 * Decompiled with CFR 0.151.
 */
package com.europa;

import com.europa.api.manager.command.CommandManager;
import com.europa.api.manager.element.ElementManager;
import com.europa.api.manager.event.EventManager;
import com.europa.api.manager.friend.FriendManager;
import com.europa.api.manager.misc.ChatManager;
import com.europa.api.manager.misc.ConfigManager;
import com.europa.api.manager.module.ModuleManager;
import com.europa.api.utilities.math.TPSUtils;
import com.europa.api.utilities.sound.SoundRegisterListener;
import com.europa.client.gui.click.ClickGuiScreen;
import com.europa.client.gui.font.FontManager;
import com.europa.client.gui.hud.HudEditorScreen;
import com.europa.client.gui.special.EuropaMainMenu;
import com.europa.client.modules.client.notifications.NotificationProcessor;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid="europa", name="Europa", version="1.1.5")
public class Europa {
    public static String NAME;
    public static String VERSION;
    public static String MODID;
    public static Logger LOGGER;
    @Mod.Instance(value="europa")
    public static Europa INSTANCE;
    public static NotificationProcessor NOTIFICATION_PROCESSOR;
    public static ChatManager CHAT_MANAGER;
    public static ModuleManager MODULE_MANAGER;
    public static ElementManager ELEMENT_MANAGER;
    public static CommandManager COMMAND_MANAGER;
    public static FriendManager FRIEND_MANAGER;
    public static EventManager EVENT_MANAGER;
    public static SoundRegisterListener SOUND_MANAGER;
    public static FontManager FONT_MANAGER;
    public static ClickGuiScreen CLICK_GUI;
    public static HudEditorScreen HUD_EDITOR;
    public static EuropaMainMenu MAIN_MENU;
    public static ConfigManager CONFIG_MANAGER;

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent fMLInitializationEvent) {
        LOGGER.info("Started Initialization process for Europa v1.1.5!");
        this.setEuropaIcon();
        CHAT_MANAGER = new ChatManager();
        MODULE_MANAGER = new ModuleManager();
        ELEMENT_MANAGER = new ElementManager();
        COMMAND_MANAGER = new CommandManager();
        FRIEND_MANAGER = new FriendManager();
        EVENT_MANAGER = new EventManager();
        SOUND_MANAGER = new SoundRegisterListener();
        FONT_MANAGER = new FontManager();
        FONT_MANAGER.load();
        CLICK_GUI = new ClickGuiScreen();
        HUD_EDITOR = new HudEditorScreen();
        MAIN_MENU = new EuropaMainMenu();
        NOTIFICATION_PROCESSOR = new NotificationProcessor();
        CONFIG_MANAGER = new ConfigManager();
        CONFIG_MANAGER.load();
        CONFIG_MANAGER.attach();
        new TPSUtils();
        LOGGER.info("Finished Initialization process for Europa v1.1.5!");
    }

    @Mod.EventHandler
    public void postInitialize(FMLPostInitializationEvent fMLPostInitializationEvent) {
        LOGGER.info("Started Post-Initialization process for Europa v1.1.5!");
        Display.setTitle((String)"Europa 1.1.5 | Looking up at Jupiter");
        LOGGER.info("Finished Post-Initialization process for Europa v1.1.5!");
    }

    public static ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }

    /*
     * Exception decompiling
     */
    public void setWindowIcon() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [20[TRYBLOCK]], but top level block is 49[CATCHBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:845)
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

    /*
     * WARNING - void declaration
     */
    public ByteBuffer readImageToBuffer(final InputStream inputStream) throws IOException {
        final BufferedImage bufferedimage = ImageIO.read(inputStream);
        final int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        final ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        final IntStream map = Arrays.stream(aint).map(Europa::lambda$readImageToBuffer$0);
        final ByteBuffer byteBuffer = bytebuffer;
        byteBuffer.getClass();
        map.forEach(byteBuffer::putInt);
        bytebuffer.flip();
        return bytebuffer;
    }

    public void setEuropaIcon() {
        this.setWindowIcon();
    }

    public static int lambda$readImageToBuffer$0(final int i) {
        return i << 8 | (i >> 24 & 0xFF);
    }


    static {
        MODID = "europa";
        VERSION = "1.1.5";
        NAME = "Europa";
        LOGGER = LogManager.getLogger((String)"Europa");
    }
}

