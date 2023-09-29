//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.config;

import java.nio.file.attribute.*;
import com.lemonclient.client.module.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import com.lemonclient.api.setting.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.client.command.*;
import com.lemonclient.client.*;
import com.google.gson.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.client.clickgui.*;
import com.lukflug.panelstudio.config.*;
import com.lemonclient.client.module.modules.qwq.*;
import java.util.function.*;
import com.lemonclient.client.module.modules.misc.*;

public class SaveConfig
{
    public static final String fileName = "LemonClient/";
    private static final String moduleName = "Modules/";
    private static final String mainName = "Main/";
    private static final String miscName = "Misc/";
    
    public static void init() {
        try {
            saveConfig();
            saveModules();
            saveEnabledModules();
            saveModuleKeybinds();
            saveDrawnModules();
            saveToggleMessagesModules();
            saveCommandPrefix();
            saveCustomFont();
            saveFriendsList();
            saveEnemiesList();
            saveClickGUIPositions();
            saveAutoGG();
            saveAutoRespawn();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void saveConfig() throws IOException {
        final Path path = Paths.get("LemonClient/", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final Path path2 = Paths.get("LemonClient/Modules/", new String[0]);
        if (!Files.exists(path2, new LinkOption[0])) {
            Files.createDirectories(path2, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final Path path3 = Paths.get("LemonClient/Main/", new String[0]);
        if (!Files.exists(path3, new LinkOption[0])) {
            Files.createDirectories(path3, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final Path path4 = Paths.get("LemonClient/Misc/", new String[0]);
        if (!Files.exists(path4, new LinkOption[0])) {
            Files.createDirectories(path4, (FileAttribute<?>[])new FileAttribute[0]);
        }
    }
    
    private static void registerFiles(final String location, final String name) throws IOException {
        final Path path = Paths.get("LemonClient/" + location + name + ".json", new String[0]);
        if (Files.exists(path, new LinkOption[0])) {
            final File file = new File("LemonClient/" + location + name + ".json");
            file.delete();
        }
        Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
    }
    
    private static void saveModules() throws IOException {
        for (final Module module : ModuleManager.getModules()) {
            try {
                saveModuleDirect(module);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static void saveModuleDirect(final Module module) throws IOException {
        registerFiles("Modules/", module.getName());
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Modules/" + module.getName() + ".json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject moduleObject = new JsonObject();
        final JsonObject settingObject = new JsonObject();
        moduleObject.add("Module", (JsonElement)new JsonPrimitive(module.getName()));
        for (final Setting setting : SettingsManager.getSettingsForModule(module)) {
            if (setting instanceof BooleanSetting) {
                settingObject.add(setting.getConfigName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(((BooleanSetting)setting).getValue())));
            }
            else if (setting instanceof IntegerSetting) {
                settingObject.add(setting.getConfigName(), (JsonElement)new JsonPrimitive((Number)((Setting<Number>)setting).getValue()));
            }
            else if (setting instanceof DoubleSetting) {
                settingObject.add(setting.getConfigName(), (JsonElement)new JsonPrimitive((Number)((Setting<Number>)setting).getValue()));
            }
            else if (setting instanceof ColorSetting) {
                settingObject.add(setting.getConfigName(), (JsonElement)new JsonPrimitive((Number)((ColorSetting)setting).toLong()));
            }
            else if (setting instanceof ModeSetting) {
                settingObject.add(setting.getConfigName(), (JsonElement)new JsonPrimitive((String)((ModeSetting)setting).getValue()));
            }
            else {
                if (!(setting instanceof StringSetting)) {
                    continue;
                }
                settingObject.add(setting.getConfigName(), (JsonElement)new JsonPrimitive(((StringSetting)setting).getText()));
            }
        }
        moduleObject.add("Settings", (JsonElement)settingObject);
        final String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveEnabledModules() throws IOException {
        registerFiles("Main/", "Toggle");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Main/Toggle.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject moduleObject = new JsonObject();
        final JsonObject enabledObject = new JsonObject();
        for (final Module module : ModuleManager.getModules()) {
            enabledObject.add(module.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isEnabled())));
        }
        moduleObject.add("Modules", (JsonElement)enabledObject);
        final String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveModuleKeybinds() throws IOException {
        registerFiles("Main/", "Bind");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Main/Bind.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject moduleObject = new JsonObject();
        final JsonObject bindObject = new JsonObject();
        for (final Module module : ModuleManager.getModules()) {
            bindObject.add(module.getName(), (JsonElement)new JsonPrimitive((Number)module.getBind()));
        }
        moduleObject.add("Modules", (JsonElement)bindObject);
        final String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveDrawnModules() throws IOException {
        registerFiles("Main/", "Drawn");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Main/Drawn.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject moduleObject = new JsonObject();
        final JsonObject drawnObject = new JsonObject();
        for (final Module module : ModuleManager.getModules()) {
            drawnObject.add(module.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isDrawn())));
        }
        moduleObject.add("Modules", (JsonElement)drawnObject);
        final String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveToggleMessagesModules() throws IOException {
        registerFiles("Main/", "ToggleMessages");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Main/ToggleMessages.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject moduleObject = new JsonObject();
        final JsonObject toggleMessagesObject = new JsonObject();
        for (final Module module : ModuleManager.getModules()) {
            toggleMessagesObject.add(module.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isToggleMsg())));
        }
        moduleObject.add("Modules", (JsonElement)toggleMessagesObject);
        final String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveCommandPrefix() throws IOException {
        registerFiles("Main/", "CommandPrefix");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Main/CommandPrefix.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject prefixObject = new JsonObject();
        prefixObject.add("Prefix", (JsonElement)new JsonPrimitive(CommandManager.getCommandPrefix()));
        final String jsonString = gson.toJson(new JsonParser().parse(prefixObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveCustomFont() throws IOException {
        registerFiles("Misc/", "CustomFont");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Misc/CustomFont.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject fontObject = new JsonObject();
        fontObject.add("Font Name", (JsonElement)new JsonPrimitive(LemonClient.INSTANCE.cFontRenderer.getFontName()));
        fontObject.add("Font Size", (JsonElement)new JsonPrimitive((Number)LemonClient.INSTANCE.cFontRenderer.getFontSize()));
        final String jsonString = gson.toJson(new JsonParser().parse(fontObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveFriendsList() throws IOException {
        registerFiles("Misc/", "Friends");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Misc/Friends.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject mainObject = new JsonObject();
        final JsonArray friendArray = new JsonArray();
        for (final Friend friend : SocialManager.getFriends()) {
            friendArray.add(friend.getName());
        }
        mainObject.add("Friends", (JsonElement)friendArray);
        final String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveEnemiesList() throws IOException {
        registerFiles("Misc/", "Enemies");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Misc/Enemies.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject mainObject = new JsonObject();
        final JsonArray enemyArray = new JsonArray();
        for (final Enemy enemy : SocialManager.getEnemies()) {
            enemyArray.add(enemy.getName());
        }
        mainObject.add("Enemies", (JsonElement)enemyArray);
        final String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveClickGUIPositions() throws IOException {
        registerFiles("Main/", "ClickGUI");
        LemonClientGUI.gui.saveConfig(new GuiConfig("LemonClient/Main/"));
    }
    
    private static void saveAutoGG() throws IOException {
        registerFiles("Misc/", "AutoGG");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Misc/AutoGG.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject mainObject = new JsonObject();
        final JsonArray messageArray = new JsonArray();
        AutoEz.getAutoEzMessages().forEach(messageArray::add);
        mainObject.add("Messages", (JsonElement)messageArray);
        final String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
    
    private static void saveAutoRespawn() throws IOException {
        registerFiles("Misc/", "AutoRespawn");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(Files.newOutputStream(Paths.get("LemonClient/Misc/AutoRespawn.json", new String[0]), new OpenOption[0]), StandardCharsets.UTF_8);
        final JsonObject mainObject = new JsonObject();
        mainObject.add("Message", (JsonElement)new JsonPrimitive(AutoRespawn.getAutoRespawnMessages()));
        final String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }
}
