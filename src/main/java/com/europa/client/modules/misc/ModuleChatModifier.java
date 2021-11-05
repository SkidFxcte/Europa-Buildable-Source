/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.modules.misc;

import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueEnum;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModuleChatModifier
extends Module {
    public static ValueBoolean background = new ValueBoolean("NoBackground", "background", "", false);
    public static ValueBoolean chatSuffix = new ValueBoolean("ChatSuffix", "ChatSuffix", "", false);
    public static ValueEnum mode = new ValueEnum("Mode", "Mode", "Mode", modes.europa);
    public static ValueBoolean greenText = new ValueBoolean("GreenText", "GreenText", "", false);
    public enum modes {
        EUROPA, europa, EuropaLite;
    }

    public ModuleChatModifier() {
        super("ChatModifier", "Chat Modifier", "Let's you customize your chat.", ModuleCategory.MISC);
    }

    /*
     * WARNING - void declaration
     */
    @SubscribeEvent
    public void onChat(ClientChatEvent clientChatEvent) {
        block14: {
            if (chatSuffix.getValue()) {
                String europa = " \u23d0 \u1d49\u1d58\u02b3\u1d52\u1d56\u1d43";
                String EUROPA = " \u23d0 \u1d07\u1d1c\u0280\u1d0f\u1d18\u1d00";
                String EuropaLite = " \u23d0 \u1d07\u1d1c\u0280\u1d0f\u1d18\u1d00 \u029f\u026a\u1d1b\u1d07";
                if (clientChatEvent.getMessage().startsWith(".")) {
                    return;
                }
                if (clientChatEvent.getMessage().startsWith(",")) {
                    return;
                }
                if (clientChatEvent.getMessage().startsWith("/")) {
                    return;
                }
                if (clientChatEvent.getMessage().startsWith("*")) {
                    return;
                }
                switch (mode.getValue().ordinal()) {
                    case 1: {
                        clientChatEvent.setMessage(clientChatEvent.getMessage() + europa);
                        break;
                    }
                    case 2: {
                        clientChatEvent.setMessage(clientChatEvent.getMessage() + EUROPA);
                        break;
                    }
                    case 3: {
                        clientChatEvent.setMessage(clientChatEvent.getMessage() + EuropaLite);
                        break;
                    }
                }
            }
            if (!greenText.getValue()) break block14;
            String greeeeen = "> ";
            if (clientChatEvent.getMessage().startsWith(".")) {
                return;
            }
            if (clientChatEvent.getMessage().startsWith(",")) {
                return;
            }
            if (clientChatEvent.getMessage().startsWith("/")) {
                return;
            }
            if (clientChatEvent.getMessage().startsWith("*")) {
                return;
            }
            clientChatEvent.setMessage(greeeeen + clientChatEvent.getMessage());
        }
    }

}

