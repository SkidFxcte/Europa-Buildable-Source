/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.commands;

import com.europa.Europa;
import com.europa.api.manager.command.Command;
import com.europa.api.manager.misc.ChatManager;

public class CommandFriend
extends Command {
    public CommandFriend() {
        super("friend", "Adds friends using commands.", "friend <add|del> <name> | clear", "f", "friends");
    }

    @Override
    public void onCommand(final String[] args) {
        if (args[0].equalsIgnoreCase("add")) {
            if (Europa.FRIEND_MANAGER.isFriend(args[1])) {
                ChatManager.printChatNotifyClient(args[1] + " is already a friend!");
                return;
            }
            if (!Europa.FRIEND_MANAGER.isFriend(args[1])) {
                Europa.FRIEND_MANAGER.addFriend(args[1]);
                ChatManager.printChatNotifyClient("Added " + args[1] + " to friends list");
            }
        }
        if (!args[0].equalsIgnoreCase("del")) {
            if (!args[0].equalsIgnoreCase("remove")) {
                return;
            }
        }
        if (!Europa.FRIEND_MANAGER.isFriend(args[1])) {
            ChatManager.printChatNotifyClient(args[1] + " is not a friend!");
            return;
        }
        if (Europa.FRIEND_MANAGER.isFriend(args[1])) {
            Europa.FRIEND_MANAGER.removeFriend(args[1]);
            ChatManager.printChatNotifyClient("Removed " + args[1] + " from friends list");
        }
    }
}


