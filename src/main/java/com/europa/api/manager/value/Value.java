/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.value;

public class Value {
    public String name;
    public String tag;
    public String description;

    public Value(String name, String tag, String description) {
        this.name = name;
        this.tag = tag;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDescription() {
        return this.description;
    }
}

