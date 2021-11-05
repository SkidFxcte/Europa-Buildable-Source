/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.manager.misc;

import com.europa.Europa;
import com.europa.api.manager.element.Element;
import com.europa.api.manager.friend.Friend;
import com.europa.api.manager.module.Module;
import com.europa.api.manager.module.ModuleCategory;
import com.europa.api.manager.value.Value;
import com.europa.api.manager.value.impl.ValueBind;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueString;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class ConfigManager {
    public void load() {
        IOException ex;
        try {
            this.loadModules();
            this.loadElements();
            this.loadPrefix();
            this.loadFriends();
            return;
        }
        catch (IOException exception) {
            ex = exception;
        }
        ex.printStackTrace();
    }


    public void save() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [50[TRYBLOCK]], but top level block is 98[FORLOOP]
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

    public void attach() {
        Runtime.getRuntime().addShutdownHook(new SaveThread());
    }

    public void loadModules() throws IOException {
        for (final Module module : Europa.MODULE_MANAGER.getModules()) {
            if (module.getCategory().equals(ModuleCategory.HUD)) {
                continue;
            }
            if (!Files.exists(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                continue;
            }
            final InputStream stream = Files.newInputStream(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new OpenOption[0]);
            JsonObject moduleJson;
            try {
                moduleJson = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
            }
            catch (IllegalStateException exception) {
                exception.printStackTrace();
                Europa.LOGGER.error(module.getName());
                Europa.LOGGER.error("Bailing out. You are on your own. Good luck.");
                continue;
            }
            if (moduleJson.get("Name") == null) {
                continue;
            }
            if (moduleJson.get("Status") == null) {
                continue;
            }
            if (moduleJson.get("Status").getAsBoolean()) {
                module.enable();
            }
            final JsonObject valueJson = moduleJson.get("Values").getAsJsonObject();
            for (final Value value : module.getValues()) {
                final JsonElement dataObject = valueJson.get(value.getName());
                if (dataObject != null) {
                    if (!dataObject.isJsonPrimitive()) {
                        continue;
                    }
                    if (value instanceof ValueBoolean) {
                        ((ValueBoolean)value).setValue(dataObject.getAsBoolean());
                    }
                    else if (value instanceof ValueNumber) {
                        if (((ValueNumber)value).getType() == 1) {
                            ((ValueNumber)value).setValue(dataObject.getAsInt());
                        }
                        else if (((ValueNumber)value).getType() == 2) {
                            ((ValueNumber)value).setValue(dataObject.getAsDouble());
                        }
                        else {
                            if (((ValueNumber)value).getType() != 3) {
                                continue;
                            }
                            ((ValueNumber)value).setValue(dataObject.getAsFloat());
                        }
                    }
                    else if (value instanceof ValueEnum) {
                        ((ValueEnum)value).setValue(((ValueEnum)value).getEnumByName(dataObject.getAsString()));
                    }
                    else if (value instanceof ValueString) {
                        ((ValueString)value).setValue(dataObject.getAsString());
                    }
                    else if (value instanceof ValueColor) {
                        ((ValueColor)value).setValue(new Color(dataObject.getAsInt()));
                        if (valueJson.get(value.getName() + "-Rainbow") != null) {
                            ((ValueColor)value).setRainbow(valueJson.get(value.getName() + "-Rainbow").getAsBoolean());
                        }
                        if (valueJson.get(value.getName() + "-Alpha") == null) {
                            continue;
                        }
                        ((ValueColor)value).setValue(new Color(((ValueColor)value).getValue().getRed(), ((ValueColor)value).getValue().getGreen(), ((ValueColor)value).getValue().getBlue(), valueJson.get(value.getName() + "-Alpha").getAsInt()));
                    }
                    else {
                        if (!(value instanceof ValueBind)) {
                            continue;
                        }
                        ((ValueBind)value).setValue(dataObject.getAsInt());
                    }
                }
            }
            stream.close();
        }
    }


    public void saveModules() throws IOException {
        for (Module module : Europa.MODULE_MANAGER.getModules()) {
            if (module.getCategory().equals((Object)ModuleCategory.HUD)) continue;
            if (Files.exists(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                File file = new File("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json");
                file.delete();
            }
            Files.createFile(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new FileAttribute[0]);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject moduleJson = new JsonObject();
            JsonObject valueJson = new JsonObject();
            moduleJson.add("Name", (JsonElement)new JsonPrimitive(module.getName()));
            moduleJson.add("Status", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isToggled())));
            for (Value value : module.getValues()) {
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((ValueBoolean)value).getValue())));
                    continue;
                }
                if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueNumber)value).getValue()));
                    continue;
                }
                if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueEnum)value).getValue().name()));
                    continue;
                }
                if (value instanceof ValueString) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueString)value).getValue()));
                    continue;
                }
                if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", (JsonElement)new JsonPrimitive(((ValueColor)value).getRainbow()));
                    continue;
                }
                if (!(value instanceof ValueBind)) continue;
                valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueBind)value).getValue()));
            }
            moduleJson.add("Values", (JsonElement)valueJson);
            OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"), StandardCharsets.UTF_8);
            writer.write(gson.toJson(new JsonParser().parse(moduleJson.toString())));
            writer.close();
        }
    }

    public void loadElements() throws IOException {
        for (Element element : Europa.ELEMENT_MANAGER.getElements()) {
            InputStream stream;
            JsonObject elementJson;
            if (!Files.exists(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new LinkOption[0]) || (elementJson = new JsonParser().parse((Reader)new InputStreamReader(stream = Files.newInputStream(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new OpenOption[0]))).getAsJsonObject()).get("Name") == null || elementJson.get("Status") == null || elementJson.get("Positions") == null) continue;
            if (elementJson.get("Status").getAsBoolean()) {
                element.enable();
            }
            JsonObject valueJson = elementJson.get("Values").getAsJsonObject();
            JsonObject positionJson = elementJson.get("Positions").getAsJsonObject();
            for (Value value : element.getValues()) {
                JsonElement dataObject = valueJson.get(value.getName());
                if (dataObject == null || !dataObject.isJsonPrimitive()) continue;
                if (value instanceof ValueBoolean) {
                    ((ValueBoolean)value).setValue(dataObject.getAsBoolean());
                    continue;
                }
                if (value instanceof ValueNumber) {
                    if (((ValueNumber)value).getType() == 1) {
                        ((ValueNumber)value).setValue(dataObject.getAsInt());
                        continue;
                    }
                    if (((ValueNumber)value).getType() == 2) {
                        ((ValueNumber)value).setValue(dataObject.getAsDouble());
                        continue;
                    }
                    if (((ValueNumber)value).getType() != 3) continue;
                    ((ValueNumber)value).setValue(Float.valueOf(dataObject.getAsFloat()));
                    continue;
                }
                if (value instanceof ValueEnum) {
                    ((ValueEnum)value).setValue(((ValueEnum)value).getEnumByName(dataObject.getAsString()));
                    continue;
                }
                if (value instanceof ValueString) {
                    ((ValueString)value).setValue(dataObject.getAsString());
                    continue;
                }
                if (value instanceof ValueColor) {
                    ((ValueColor)value).setValue(new Color(dataObject.getAsInt()));
                    if (valueJson.get(value.getName() + "-Rainbow") != null) {
                        ((ValueColor)value).setRainbow(valueJson.get(value.getName() + "-Rainbow").getAsBoolean());
                    }
                    if (valueJson.get(value.getName() + "-Alpha") == null) continue;
                    ((ValueColor)value).setValue(new Color(((ValueColor)value).getValue().getRed(), ((ValueColor)value).getValue().getGreen(), ((ValueColor)value).getValue().getBlue(), valueJson.get(value.getName() + "-Alpha").getAsInt()));
                    continue;
                }
                if (!(value instanceof ValueBind)) continue;
                ((ValueBind)value).setValue(dataObject.getAsInt());
            }
            if (positionJson.get("X") != null && positionJson.get("Y") != null) {
                element.frame.setX(positionJson.get("X").getAsFloat());
                element.frame.setY(positionJson.get("Y").getAsFloat());
            }
            stream.close();
        }
    }

    public void saveElements() throws IOException {
        for (Element element : Europa.ELEMENT_MANAGER.getElements()) {
            if (Files.exists(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new LinkOption[0])) {
                File file = new File("Europa/Elements/" + element.getName() + ".json");
                file.delete();
            }
            Files.createFile(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new FileAttribute[0]);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject elementJson = new JsonObject();
            JsonObject valueJson = new JsonObject();
            JsonObject positionJson = new JsonObject();
            elementJson.add("Name", (JsonElement)new JsonPrimitive(element.getName()));
            elementJson.add("Status", (JsonElement)new JsonPrimitive(Boolean.valueOf(element.isToggled())));
            for (Value value : element.getValues()) {
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((ValueBoolean)value).getValue())));
                    continue;
                }
                if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueNumber)value).getValue()));
                    continue;
                }
                if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueEnum)value).getValue().name()));
                    continue;
                }
                if (value instanceof ValueString) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueString)value).getValue()));
                    continue;
                }
                if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", (JsonElement)new JsonPrimitive(((ValueColor)value).getRainbow()));
                    continue;
                }
                if (!(value instanceof ValueBind)) continue;
                valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueBind)value).getValue()));
            }
            positionJson.add("X", (JsonElement)new JsonPrimitive((Number)Float.valueOf(element.frame.getX())));
            positionJson.add("Y", (JsonElement)new JsonPrimitive((Number)Float.valueOf(element.frame.getY())));
            elementJson.add("Values", (JsonElement)valueJson);
            elementJson.add("Positions", (JsonElement)positionJson);
            OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Europa/Elements/" + element.getName() + ".json"), StandardCharsets.UTF_8);
            writer.write(gson.toJson(new JsonParser().parse(elementJson.toString())));
            writer.close();
        }
    }

    public void loadPrefix() throws IOException {
        if (!Files.exists(Paths.get("Europa/Client/Prefix.json", new String[0]), new LinkOption[0])) {
            return;
        }
        InputStream stream = Files.newInputStream(Paths.get("Europa/Client/Prefix.json", new String[0]), new OpenOption[0]);
        JsonObject prefixJson = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        if (prefixJson.get("Prefix") == null) {
            return;
        }
        Europa.COMMAND_MANAGER.setPrefix(prefixJson.get("Prefix").getAsString());
        stream.close();
    }

    public void savePrefix() throws IOException {
        if (Files.exists(Paths.get("Europa/Client/Prefix.json", new String[0]), new LinkOption[0])) {
            File file = new File("Europa/Client/Prefix.json");
            file.delete();
        }
        Files.createFile(Paths.get("Europa/Client/Prefix.json", new String[0]), new FileAttribute[0]);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject prefixJson = new JsonObject();
        prefixJson.add("Prefix", (JsonElement)new JsonPrimitive(Europa.COMMAND_MANAGER.getPrefix()));
        OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Europa/Client/Prefix.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson(new JsonParser().parse(prefixJson.toString())));
        writer.close();
    }

    public void loadFriends() throws IOException {
        if (!Files.exists(Paths.get("Europa/Client/Friends.json", new String[0]), new LinkOption[0])) {
            return;
        }
        InputStream stream = Files.newInputStream(Paths.get("Europa/Client/Friends.json", new String[0]), new OpenOption[0]);
        JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        if (mainObject.get("Friends") == null) {
            return;
        }
        JsonArray friendArray = mainObject.get("Friends").getAsJsonArray();
        friendArray.forEach(ConfigManager::lambda$loadFriends$0);
        stream.close();
    }

    public void saveFriends() throws IOException {
        if (Files.exists(Paths.get("Europa/Client/Friends.json", new String[0]), new LinkOption[0])) {
            File file = new File("Europa/Client/Friends.json");
            file.delete();
        }
        Files.createFile(Paths.get("Europa/Client/Friends.json", new String[0]), new FileAttribute[0]);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject mainObject = new JsonObject();
        JsonArray friendArray = new JsonArray();
        for (Friend friend : Europa.FRIEND_MANAGER.getFriends()) {
            friendArray.add(friend.getName());
        }
        mainObject.add("Friends", (JsonElement)friendArray);
        OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Europa/Client/Friends.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson(new JsonParser().parse(mainObject.toString())));
        writer.close();
    }

    public static void lambda$loadFriends$0(final JsonElement friend) {
        Europa.FRIEND_MANAGER.addFriend(friend.getAsString());
    }
    public static class SaveThread
    extends Thread {
        @Override
        public void run() {
            Europa.CONFIG_MANAGER.save();
        }
    }
}

