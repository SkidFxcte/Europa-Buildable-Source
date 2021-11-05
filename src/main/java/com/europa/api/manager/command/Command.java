/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.command;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public abstract class Command {
    public static Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public String description;
    public String syntax;
    public List<String> aliases;

    public Command(String name, String description, String syntax, String ... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }

    public abstract void onCommand(String[] var1);

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSyntax() {
        return this.syntax;
    }

    public List<String> getAliases() {
        return this.aliases;
    }
}

