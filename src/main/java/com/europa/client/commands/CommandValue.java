//
// Decompiled by Procyon v0.5.36
//

package com.europa.client.commands;

import java.util.Iterator;
import com.europa.api.manager.value.Value;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.misc.ChatManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.europa.Europa;
import com.europa.api.manager.command.Command;

public class CommandValue extends Command
{
    public CommandValue() {
        super("value", "Let's you change module values with commands.", "value <module> <setting> <value>", new String[] { "val", "v", "set" });
    }

    @Override
    public void onCommand(final String[] args) {
        final Module m = Europa.getModuleManager().getModule(args[0]);
        if (m != null) {
            if (args.length == 1) {
                ChatManager.printChatNotifyClient("Showing settings for " + args[0] + ": " + ChatFormatting.GRAY + "Placeholder");
            }
        }
        if (m == null) {
            ChatManager.printChatNotifyClient("Unknown module.");
            return;
        }
        if (m != null) {
            if (args[1] != null) {
                final Value v = m.getValue(args[1]);
                if (v == null) {
                    ChatManager.printChatNotifyClient("Unknown setting.");
                    return;
                }
                if (v != null) {
                    if (v instanceof ValueBoolean) {
                        if (args[2] == null) {
                            ChatManager.printChatNotifyClient("Please give a value.");
                            return;
                        }
                        if (args[2] != null) {
                            ((ValueBoolean)v).setValue(Boolean.parseBoolean(args[2]));
                            ChatManager.printChatNotifyClient(args[1] + " from " + args[0] + " has been set to " + args[2]);
                        }
                    }
                    else if (v instanceof ValueNumber) {
                        if (args[2] == null) {
                            ChatManager.printChatNotifyClient("Please give a value.");
                            return;
                        }
                        if (args[2] != null) {
                            if (((ValueNumber)v).getType() == 3) {
                                ((ValueNumber)v).setValue(Float.parseFloat(args[2]));
                                ChatManager.printChatNotifyClient(args[1] + " from " + args[0] + " has been set to " + args[2]);
                            }
                            else if (((ValueNumber)v).getType() == 2) {
                                ((ValueNumber)v).setValue(Double.parseDouble(args[2]));
                                ChatManager.printChatNotifyClient(args[1] + " from " + args[0] + " has been set to " + args[2]);
                            }
                            else if (((ValueNumber)v).getType() == 1) {
                                ((ValueNumber)v).setValue(Integer.parseInt(args[2]));
                                ChatManager.printChatNotifyClient(args[1] + " from " + args[0] + " has been set to " + args[2]);
                            }
                        }
                    }
                    else if (v instanceof ValueEnum) {
                        if (args[2] == null) {
                            ChatManager.printChatNotifyClient("Please give a value.");
                            return;
                        }
                        if (args[2] != null) {
                            for (Enum enumValue : ((ValueEnum)v).getValues()) {
                                enumValue = ((ValueEnum)v).getEnumByName(args[2]);
                                if (enumValue != null) {
                                    ((ValueEnum)v).setValue(enumValue);
                                    ChatManager.printChatNotifyClient(args[1] + " from " + args[0] + " has been set to " + args[2]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
