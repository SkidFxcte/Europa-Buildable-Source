//
// Decompiled by Procyon v0.5.36
//

package com.europa.api.manager.misc;

import com.europa.api.manager.friend.Friend;
import com.google.gson.JsonArray;
import java.util.function.Consumer;
import com.europa.api.manager.element.Element;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;
import com.google.gson.JsonPrimitive;
import com.google.gson.GsonBuilder;
import java.io.File;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.util.Iterator;
import com.europa.api.manager.value.impl.ValueBind;
import java.awt.Color;
import com.europa.api.manager.value.impl.ValueColor;
import com.europa.api.manager.value.impl.ValueString;
import com.europa.api.manager.value.impl.ValueEnum;
import com.europa.api.manager.value.impl.ValueNumber;
import com.europa.api.manager.value.impl.ValueBoolean;
import com.europa.api.manager.value.Value;
import java.io.Reader;
import java.io.InputStreamReader;
import com.google.gson.JsonParser;
import java.nio.file.OpenOption;
import com.europa.api.manager.module.Module;
import com.europa.Europa;
import com.europa.api.manager.module.ModuleCategory;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.io.IOException;

public class ConfigManager
{
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
        IOException ex;
        try {
            if (!Files.exists(Paths.get("Europa/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get("Europa/Modules/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/Modules/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get("Europa/Elements/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/Elements/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get("Europa/Client/", new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/Client/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            for (final ModuleCategory category : ModuleCategory.values()) {
                if (!category.equals(ModuleCategory.HUD)) {
                    if (!Files.exists(Paths.get("Europa/Modules/" + category.getName() + "/", new String[0]), new LinkOption[0])) {
                        Files.createDirectories(Paths.get("Europa/Modules/" + category.getName() + "/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
                    }
                }
            }
            this.saveModules();
            this.saveElements();
            this.savePrefix();
            this.saveFriends();
            return;
        }
        catch (IOException exception) {
            ex = exception;
        }
        ex.printStackTrace();
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
        for (final Module module : Europa.MODULE_MANAGER.getModules()) {
            if (module.getCategory().equals(ModuleCategory.HUD)) {
                continue;
            }
            if (Files.exists(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), new LinkOption[0])) {
                final File file = new File("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json");
                file.delete();
            }
            Files.createFile(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject moduleJson = new JsonObject();
            final JsonObject valueJson = new JsonObject();
            moduleJson.add("Name", (JsonElement)new JsonPrimitive(module.getName()));
            moduleJson.add("Status", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isToggled())));
            for (final Value value : module.getValues()) {
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((ValueBoolean)value).getValue())));
                }
                else if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueNumber)value).getValue()));
                }
                else if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueEnum)value).getValue().name()));
                }
                else if (value instanceof ValueString) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueString)value).getValue()));
                }
                else if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", (JsonElement)new JsonPrimitive(((ValueColor)value).getRainbow()));
                }
                else {
                    if (!(value instanceof ValueBind)) {
                        continue;
                    }
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueBind)value).getValue()));
                }
            }
            moduleJson.add("Values", (JsonElement)valueJson);
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"), StandardCharsets.UTF_8);
            writer.write(gson.toJson(new JsonParser().parse(moduleJson.toString())));
            writer.close();
        }
    }

    public void loadElements() throws IOException {
        for (final Element element : Europa.ELEMENT_MANAGER.getElements()) {
            if (!Files.exists(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new LinkOption[0])) {
                continue;
            }
            final InputStream stream = Files.newInputStream(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new OpenOption[0]);
            final JsonObject elementJson = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
            if (elementJson.get("Name") == null || elementJson.get("Status") == null) {
                continue;
            }
            if (elementJson.get("Positions") == null) {
                continue;
            }
            if (elementJson.get("Status").getAsBoolean()) {
                element.enable();
            }
            final JsonObject valueJson = elementJson.get("Values").getAsJsonObject();
            final JsonObject positionJson = elementJson.get("Positions").getAsJsonObject();
            for (final Value value : element.getValues()) {
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
            if (positionJson.get("X") != null && positionJson.get("Y") != null) {
                element.frame.setX(positionJson.get("X").getAsFloat());
                element.frame.setY(positionJson.get("Y").getAsFloat());
            }
            stream.close();
        }
    }

    public void saveElements() throws IOException {
        for (final Element element : Europa.ELEMENT_MANAGER.getElements()) {
            if (Files.exists(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), new LinkOption[0])) {
                final File file = new File("Europa/Elements/" + element.getName() + ".json");
                file.delete();
            }
            Files.createFile(Paths.get("Europa/Elements/" + element.getName() + ".json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject elementJson = new JsonObject();
            final JsonObject valueJson = new JsonObject();
            final JsonObject positionJson = new JsonObject();
            elementJson.add("Name", (JsonElement)new JsonPrimitive(element.getName()));
            elementJson.add("Status", (JsonElement)new JsonPrimitive(Boolean.valueOf(element.isToggled())));
            for (final Value value : element.getValues()) {
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((ValueBoolean)value).getValue())));
                }
                else if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueNumber)value).getValue()));
                }
                else if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueEnum)value).getValue().name()));
                }
                else if (value instanceof ValueString) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive(((ValueString)value).getValue()));
                }
                else if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", (JsonElement)new JsonPrimitive((Number)((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", (JsonElement)new JsonPrimitive(((ValueColor)value).getRainbow()));
                }
                else {
                    if (!(value instanceof ValueBind)) {
                        continue;
                    }
                    valueJson.add(value.getName(), (JsonElement)new JsonPrimitive((Number)((ValueBind)value).getValue()));
                }
            }
            positionJson.add("X", (JsonElement)new JsonPrimitive((Number)element.frame.getX()));
            positionJson.add("Y", (JsonElement)new JsonPrimitive((Number)element.frame.getY()));
            elementJson.add("Values", (JsonElement)valueJson);
            elementJson.add("Positions", (JsonElement)positionJson);
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Europa/Elements/" + element.getName() + ".json"), StandardCharsets.UTF_8);
            writer.write(gson.toJson(new JsonParser().parse(elementJson.toString())));
            writer.close();
        }
    }

    public void loadPrefix() throws IOException {
        if (!Files.exists(Paths.get("Europa/Client/Prefix.json", new String[0]), new LinkOption[0])) {
            return;
        }
        final InputStream stream = Files.newInputStream(Paths.get("Europa/Client/Prefix.json", new String[0]), new OpenOption[0]);
        final JsonObject prefixJson = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        if (prefixJson.get("Prefix") == null) {
            return;
        }
        Europa.COMMAND_MANAGER.setPrefix(prefixJson.get("Prefix").getAsString());
        stream.close();
    }

    public void savePrefix() throws IOException {
        if (Files.exists(Paths.get("Europa/Client/Prefix.json", new String[0]), new LinkOption[0])) {
            final File file = new File("Europa/Client/Prefix.json");
            file.delete();
        }
        Files.createFile(Paths.get("Europa/Client/Prefix.json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonObject prefixJson = new JsonObject();
        prefixJson.add("Prefix", (JsonElement)new JsonPrimitive(Europa.COMMAND_MANAGER.getPrefix()));
        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Europa/Client/Prefix.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson(new JsonParser().parse(prefixJson.toString())));
        writer.close();
    }

    public void loadFriends() throws IOException {
        if (!Files.exists(Paths.get("Europa/Client/Friends.json", new String[0]), new LinkOption[0])) {
            return;
        }
        final InputStream stream = Files.newInputStream(Paths.get("Europa/Client/Friends.json", new String[0]), new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        if (mainObject.get("Friends") == null) {
            return;
        }
        final JsonArray friendArray = mainObject.get("Friends").getAsJsonArray();
        friendArray.forEach((Consumer)ConfigManager::lambdaloadFriends0);
        stream.close();
    }

    private static void lambdaloadFriends0(Object o) {
    }

    public void saveFriends() throws IOException {
        if (Files.exists(Paths.get("Europa/Client/Friends.json", new String[0]), new LinkOption[0])) {
            final File file = new File("Europa/Client/Friends.json");
            file.delete();
        }
        Files.createFile(Paths.get("Europa/Client/Friends.json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonObject mainObject = new JsonObject();
        final JsonArray friendArray = new JsonArray();
        for (final Friend friend : Europa.FRIEND_MANAGER.getFriends()) {
            friendArray.add(friend.getName());
        }
        mainObject.add("Friends", (JsonElement)friendArray);
        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Europa/Client/Friends.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson(new JsonParser().parse(mainObject.toString())));
        writer.close();
    }

    public static void lambdaloadFriends0(final JsonElement friend) {
        Europa.FRIEND_MANAGER.addFriend(friend.getAsString());
    }

    public static class SaveThread extends Thread
    {
        @Override
        public void run() {
            Europa.CONFIG_MANAGER.save();
        }
    }
}
