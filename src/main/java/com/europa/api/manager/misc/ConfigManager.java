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
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Iterator;

public class ConfigManager {
    public void load() {
        Object var10000 = null;

        try {
            this.loadModules();
            this.loadElements();
            this.loadPrefix();
            this.loadFriends();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void save() {
        Object var10000 = null;

        try {
            if (!Files.exists(Paths.get("Europa/"), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/"));
            }

            if (!Files.exists(Paths.get("Europa/Modules/"), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/Modules/"));
            }

            if (!Files.exists(Paths.get("Europa/Elements/"), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/Elements/"));
            }

            if (!Files.exists(Paths.get("Europa/Client/"), new LinkOption[0])) {
                Files.createDirectories(Paths.get("Europa/Client/"));
            }

            ModuleCategory[] var1 = ModuleCategory.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                ModuleCategory category = var1[var3];
                if (!category.equals(ModuleCategory.HUD) && !Files.exists(Paths.get("Europa/Modules/" + category.getName() + "/"), new LinkOption[0])) {
                    Files.createDirectories(Paths.get("Europa/Modules/" + category.getName() + "/"));
                }
            }

            this.saveModules();
            this.saveElements();
            this.savePrefix();
            this.saveFriends();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public void attach() {
        Object var10000 = null;
        Runtime.getRuntime().addShutdownHook(new ConfigManager.SaveThread());
    }

    public void loadModules() throws IOException {
        Object var10000 = null;
        Iterator var1 = Europa.MODULE_MANAGER.getModules().iterator();

        while(true) {
            Module module;
            InputStream stream;
            JsonObject moduleJson;
            do {
                do {
                    while(true) {
                        do {
                            do {
                                if (!var1.hasNext()) {
                                    return;
                                }

                                module = (Module)var1.next();
                            } while(module.getCategory().equals(ModuleCategory.HUD));
                        } while(!Files.exists(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"), new LinkOption[0]));

                        stream = Files.newInputStream(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"));

                        try {
                            moduleJson = (new JsonParser()).parse(new InputStreamReader(stream)).getAsJsonObject();
                            break;
                        } catch (IllegalStateException var9) {
                            var9.printStackTrace();
                            Europa.LOGGER.error(module.getName());
                            Europa.LOGGER.error("Bailing out. You are on your own. Good luck.");
                        }
                    }
                } while(moduleJson.get("Name") == null);
            } while(moduleJson.get("Status") == null);

            if (moduleJson.get("Status").getAsBoolean()) {
                module.enable();
            }

            JsonObject valueJson = moduleJson.get("Values").getAsJsonObject();
            Iterator var6 = module.getValues().iterator();

            while(var6.hasNext()) {
                Value value = (Value)var6.next();
                JsonElement dataObject = valueJson.get(value.getName());
                if (dataObject != null && dataObject.isJsonPrimitive()) {
                    if (value instanceof ValueBoolean) {
                        ((ValueBoolean)value).setValue(dataObject.getAsBoolean());
                    } else if (value instanceof ValueNumber) {
                        if (((ValueNumber)value).getType() == 1) {
                            ((ValueNumber)value).setValue(dataObject.getAsInt());
                        } else if (((ValueNumber)value).getType() == 2) {
                            ((ValueNumber)value).setValue(dataObject.getAsDouble());
                        } else if (((ValueNumber)value).getType() == 3) {
                            ((ValueNumber)value).setValue(dataObject.getAsFloat());
                        }
                    } else if (value instanceof ValueEnum) {
                        ((ValueEnum)value).setValue(((ValueEnum)value).getEnumByName(dataObject.getAsString()));
                    } else if (value instanceof ValueString) {
                        ((ValueString)value).setValue(dataObject.getAsString());
                    } else if (value instanceof ValueColor) {
                        ((ValueColor)value).setValue(new Color(dataObject.getAsInt()));
                        if (valueJson.get(value.getName() + "-Rainbow") != null) {
                            ((ValueColor)value).setRainbow(valueJson.get(value.getName() + "-Rainbow").getAsBoolean());
                        }

                        if (valueJson.get(value.getName() + "-Alpha") != null) {
                            ((ValueColor)value).setValue(new Color(((ValueColor)value).getValue().getRed(), ((ValueColor)value).getValue().getGreen(), ((ValueColor)value).getValue().getBlue(), valueJson.get(value.getName() + "-Alpha").getAsInt()));
                        }
                    } else if (value instanceof ValueBind) {
                        ((ValueBind)value).setValue(dataObject.getAsInt());
                    }
                }
            }

            stream.close();
        }
    }

    public void saveModules() throws IOException {
        Object var10000 = null;
        Iterator var1 = Europa.MODULE_MANAGER.getModules().iterator();

        while(true) {
            Module module;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                module = (Module)var1.next();
            } while(module.getCategory().equals(ModuleCategory.HUD));

            if (Files.exists(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"), new LinkOption[0])) {
                File var3 = new File("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json");
                var3.delete();
            }

            Files.createFile(Paths.get("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"));
            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
            JsonObject moduleJson = new JsonObject();
            JsonObject valueJson = new JsonObject();
            moduleJson.add("Name", new JsonPrimitive(module.getName()));
            moduleJson.add("Status", new JsonPrimitive(module.isToggled()));
            Iterator var6 = module.getValues().iterator();

            while(var6.hasNext()) {
                Value value = (Value)var6.next();
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBoolean)value).getValue()));
                } else if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueNumber)value).getValue()));
                } else if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueEnum)value).getValue().name()));
                } else if (value instanceof ValueString) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueString)value).getValue()));
                } else if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", new JsonPrimitive(((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", new JsonPrimitive(((ValueColor)value).getRainbow()));
                } else if (value instanceof ValueBind) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBind)value).getValue()));
                }
            }

            moduleJson.add("Values", valueJson);
            OutputStreamWriter var9 = new OutputStreamWriter(new FileOutputStream("Europa/Modules/" + module.getCategory().getName() + "/" + module.getName() + ".json"), StandardCharsets.UTF_8);
            var9.write(gson.toJson((new JsonParser()).parse(moduleJson.toString())));
            var9.close();
        }
    }

    public void loadElements() throws IOException {
        Object var10000 = null;
        Iterator var1 = Europa.ELEMENT_MANAGER.getElements().iterator();

        while(true) {
            Element element;
            InputStream stream;
            JsonObject elementJson;
            do {
                do {
                    do {
                        do {
                            if (!var1.hasNext()) {
                                return;
                            }

                            element = (Element)var1.next();
                        } while(!Files.exists(Paths.get("Europa/Elements/" + element.getName() + ".json"), new LinkOption[0]));

                        stream = Files.newInputStream(Paths.get("Europa/Elements/" + element.getName() + ".json"));
                        elementJson = (new JsonParser()).parse(new InputStreamReader(stream)).getAsJsonObject();
                    } while(elementJson.get("Name") == null);
                } while(elementJson.get("Status") == null);
            } while(elementJson.get("Positions") == null);

            if (elementJson.get("Status").getAsBoolean()) {
                element.enable();
            }

            JsonObject valueJson = elementJson.get("Values").getAsJsonObject();
            JsonObject positionJson = elementJson.get("Positions").getAsJsonObject();
            Iterator var7 = element.getValues().iterator();

            while(var7.hasNext()) {
                Value value = (Value)var7.next();
                JsonElement dataObject = valueJson.get(value.getName());
                if (dataObject != null && dataObject.isJsonPrimitive()) {
                    if (value instanceof ValueBoolean) {
                        ((ValueBoolean)value).setValue(dataObject.getAsBoolean());
                    } else if (value instanceof ValueNumber) {
                        if (((ValueNumber)value).getType() == 1) {
                            ((ValueNumber)value).setValue(dataObject.getAsInt());
                        } else if (((ValueNumber)value).getType() == 2) {
                            ((ValueNumber)value).setValue(dataObject.getAsDouble());
                        } else if (((ValueNumber)value).getType() == 3) {
                            ((ValueNumber)value).setValue(dataObject.getAsFloat());
                        }
                    } else if (value instanceof ValueEnum) {
                        ((ValueEnum)value).setValue(((ValueEnum)value).getEnumByName(dataObject.getAsString()));
                    } else if (value instanceof ValueString) {
                        ((ValueString)value).setValue(dataObject.getAsString());
                    } else if (value instanceof ValueColor) {
                        ((ValueColor)value).setValue(new Color(dataObject.getAsInt()));
                        if (valueJson.get(value.getName() + "-Rainbow") != null) {
                            ((ValueColor)value).setRainbow(valueJson.get(value.getName() + "-Rainbow").getAsBoolean());
                        }

                        if (valueJson.get(value.getName() + "-Alpha") != null) {
                            ((ValueColor)value).setValue(new Color(((ValueColor)value).getValue().getRed(), ((ValueColor)value).getValue().getGreen(), ((ValueColor)value).getValue().getBlue(), valueJson.get(value.getName() + "-Alpha").getAsInt()));
                        }
                    } else if (value instanceof ValueBind) {
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
        Object var10000 = null;
        Iterator var1 = Europa.ELEMENT_MANAGER.getElements().iterator();

        while(var1.hasNext()) {
            Element element = (Element)var1.next();
            if (Files.exists(Paths.get("Europa/Elements/" + element.getName() + ".json"), new LinkOption[0])) {
                File var3 = new File("Europa/Elements/" + element.getName() + ".json");
                var3.delete();
            }

            Files.createFile(Paths.get("Europa/Elements/" + element.getName() + ".json"));
            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
            JsonObject elementJson = new JsonObject();
            JsonObject valueJson = new JsonObject();
            JsonObject positionJson = new JsonObject();
            elementJson.add("Name", new JsonPrimitive(element.getName()));
            elementJson.add("Status", new JsonPrimitive(element.isToggled()));
            Iterator var7 = element.getValues().iterator();

            while(var7.hasNext()) {
                Value value = (Value)var7.next();
                if (value instanceof ValueBoolean) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBoolean)value).getValue()));
                } else if (value instanceof ValueNumber) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueNumber)value).getValue()));
                } else if (value instanceof ValueEnum) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueEnum)value).getValue().name()));
                } else if (value instanceof ValueString) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueString)value).getValue()));
                } else if (value instanceof ValueColor) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueColor)value).getValue().getRGB()));
                    valueJson.add(value.getName() + "-Alpha", new JsonPrimitive(((ValueColor)value).getValue().getAlpha()));
                    valueJson.add(value.getName() + "-Rainbow", new JsonPrimitive(((ValueColor)value).getRainbow()));
                } else if (value instanceof ValueBind) {
                    valueJson.add(value.getName(), new JsonPrimitive(((ValueBind)value).getValue()));
                }
            }

            positionJson.add("X", new JsonPrimitive(element.frame.getX()));
            positionJson.add("Y", new JsonPrimitive(element.frame.getY()));
            elementJson.add("Values", valueJson);
            elementJson.add("Positions", positionJson);
            OutputStreamWriter var10 = new OutputStreamWriter(new FileOutputStream("Europa/Elements/" + element.getName() + ".json"), StandardCharsets.UTF_8);
            var10.write(gson.toJson((new JsonParser()).parse(elementJson.toString())));
            var10.close();
        }

    }

    public void loadPrefix() throws IOException {
        Object var10000 = null;
        if (Files.exists(Paths.get("Europa/Client/Prefix.json"), new LinkOption[0])) {
            InputStream stream = Files.newInputStream(Paths.get("Europa/Client/Prefix.json"));
            JsonObject prefixJson = (new JsonParser()).parse(new InputStreamReader(stream)).getAsJsonObject();
            if (prefixJson.get("Prefix") != null) {
                Europa.COMMAND_MANAGER.setPrefix(prefixJson.get("Prefix").getAsString());
                stream.close();
            }
        }
    }

    public void savePrefix() throws IOException {
        Object var10000 = null;
        if (Files.exists(Paths.get("Europa/Client/Prefix.json"), new LinkOption[0])) {
            File var1 = new File("Europa/Client/Prefix.json");
            var1.delete();
        }

        Files.createFile(Paths.get("Europa/Client/Prefix.json"));
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        JsonObject prefixJson = new JsonObject();
        prefixJson.add("Prefix", new JsonPrimitive(Europa.COMMAND_MANAGER.getPrefix()));
        OutputStreamWriter var3 = new OutputStreamWriter(new FileOutputStream("Europa/Client/Prefix.json"), StandardCharsets.UTF_8);
        var3.write(gson.toJson((new JsonParser()).parse(prefixJson.toString())));
        var3.close();
    }

    public void loadFriends() throws IOException {
        Object var10000 = null;
        if (Files.exists(Paths.get("Europa/Client/Friends.json"), new LinkOption[0])) {
            InputStream stream = Files.newInputStream(Paths.get("Europa/Client/Friends.json"));
            JsonObject mainObject = (new JsonParser()).parse(new InputStreamReader(stream)).getAsJsonObject();
            if (mainObject.get("Friends") != null) {
                JsonArray var3 = mainObject.get("Friends").getAsJsonArray();
                var3.forEach(ConfigManager::lambda$loadFriends$0);
                stream.close();
            }
        }
    }

    public void saveFriends() throws IOException {
        Object var10000 = null;
        if (Files.exists(Paths.get("Europa/Client/Friends.json"), new LinkOption[0])) {
            File var1 = new File("Europa/Client/Friends.json");
            var1.delete();
        }

        Files.createFile(Paths.get("Europa/Client/Friends.json"));
        Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
        JsonObject mainObject = new JsonObject();
        JsonArray friendArray = new JsonArray();
        Iterator var4 = Europa.FRIEND_MANAGER.getFriends().iterator();

        while(var4.hasNext()) {
            Friend var5 = (Friend)var4.next();
            friendArray.add(var5.getName());
        }

        mainObject.add("Friends", friendArray);
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("Europa/Client/Friends.json"), StandardCharsets.UTF_8);
        writer.write(gson.toJson((new JsonParser()).parse(mainObject.toString())));
        writer.close();
    }

    public static void lambda$loadFriends$0(JsonElement friend) {
        Object var10000 = null;
        Europa.FRIEND_MANAGER.addFriend(friend.getAsString());
    }

    public static class SaveThread extends Thread {
        public void run() {
            Object var10000 = null;
            Europa.CONFIG_MANAGER.save();
        }
    }
}
