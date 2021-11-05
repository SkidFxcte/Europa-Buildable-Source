/*
 * Decompiled with CFR 0.151.
 */
package com.europa.client.commands;

import com.europa.api.manager.command.Command;

public class CommandPrefix
extends Command {
    public CommandPrefix() {
        super("prefix", "Let's you change the prefix using commands.", "prefix <input>", "cmdprefix", "commandprefix", "cmdp", "commandp");
    }

    @Override
    public void onCommand(String[] stringArray) {
    }
}

