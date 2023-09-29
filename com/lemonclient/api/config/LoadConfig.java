//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.api.config;

import com.lemonclient.client.module.*;
import java.util.*;
import com.lemonclient.api.setting.*;
import com.lemonclient.api.setting.values.*;
import java.io.*;
import java.nio.file.*;
import com.lemonclient.client.command.*;
import com.lemonclient.client.*;
import java.awt.*;
import com.lemonclient.api.util.font.*;
import com.lemonclient.api.util.player.social.*;
import com.google.gson.*;
import com.lemonclient.client.clickgui.*;
import com.lukflug.panelstudio.config.*;
import com.lemonclient.client.module.modules.qwq.*;
import com.lemonclient.client.module.modules.misc.*;

public class LoadConfig
{
    private static final String fileName = "LemonClient/";
    private static final String moduleName = "Modules/";
    private static final String mainName = "Main/";
    private static final String miscName = "Misc/";
    
    public static void init() {
        try {
            loadModules();
            loadEnabledModules();
            loadModuleKeybinds();
            loadDrawnModules();
            loadToggleMessageModules();
            loadCommandPrefix();
            loadCustomFont();
            loadFriendsList();
            loadEnemiesList();
            loadClickGUIPositions();
            loadAutoGG();
            loadAutoRespawn();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void loadModules() {
        final String moduleLocation = "LemonClient/Modules/";
        for (final Module module : ModuleManager.getModules()) {
            try {
                loadModuleDirect(moduleLocation, module);
            }
            catch (IOException e) {
                System.out.println(module.getName());
                e.printStackTrace();
            }
        }
    }
    
    private static void loadModuleDirect(final String moduleLocation, final Module module) throws IOException {
        if (!Files.exists(Paths.get(moduleLocation + module.getName() + ".json", new String[0]), new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(Paths.get(moduleLocation + module.getName() + ".json", new String[0]), new OpenOption[0]);
        JsonObject moduleObject;
        try {
            moduleObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        }
        catch (IllegalStateException e) {
            return;
        }
        if (moduleObject.get("Module") == null) {
            return;
        }
        final JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
        for (final Setting setting : SettingsManager.getSettingsForModule(module)) {
            final JsonElement dataObject = settingObject.get(setting.getConfigName());
            try {
                if (dataObject == null || !dataObject.isJsonPrimitive()) {
                    continue;
                }
                if (setting instanceof BooleanSetting) {
                    setting.setValue(dataObject.getAsBoolean());
                }
                else if (setting instanceof IntegerSetting) {
                    setting.setValue(dataObject.getAsInt());
                }
                else if (setting instanceof DoubleSetting) {
                    setting.setValue(dataObject.getAsDouble());
                }
                else if (setting instanceof ColorSetting) {
                    ((ColorSetting)setting).fromLong(dataObject.getAsLong());
                }
                else if (setting instanceof ModeSetting) {
                    setting.setValue(dataObject.getAsString());
                }
                else {
                    if (!(setting instanceof StringSetting)) {
                        continue;
                    }
                    setting.setValue(dataObject.getAsString());
                    ((StringSetting)setting).setText(dataObject.getAsString());
                }
            }
            catch (NumberFormatException e2) {
                System.out.println(setting.getConfigName() + " " + module.getName());
                System.out.println(dataObject);
            }
        }
        inputStream.close();
    }
    
    private static void loadEnabledModules() throws IOException {
        final String enabledLocation = "LemonClient/Main/";
        final Path path = Paths.get(enabledLocation + "Toggle.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject moduleObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (moduleObject.get("Modules") == null) {
            return;
        }
        final JsonObject settingObject = moduleObject.get("Modules").getAsJsonObject();
        for (final Module module : ModuleManager.getModules()) {
            final JsonElement dataObject = settingObject.get(module.getName());
            if (dataObject != null && dataObject.isJsonPrimitive() && dataObject.getAsBoolean()) {
                try {
                    module.enable();
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        inputStream.close();
    }
    
    private static void loadModuleKeybinds() throws IOException {
        final String bindLocation = "LemonClient/Main/";
        final Path path = Paths.get(bindLocation + "Bind.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject moduleObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (moduleObject.get("Modules") == null) {
            return;
        }
        final JsonObject settingObject = moduleObject.get("Modules").getAsJsonObject();
        for (final Module module : ModuleManager.getModules()) {
            final JsonElement dataObject = settingObject.get(module.getName());
            if (dataObject != null && dataObject.isJsonPrimitive()) {
                module.setBind(dataObject.getAsInt());
            }
        }
        inputStream.close();
    }
    
    private static void loadDrawnModules() throws IOException {
        final String drawnLocation = "LemonClient/Main/";
        final Path path = Paths.get(drawnLocation + "Drawn.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject moduleObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (moduleObject.get("Modules") == null) {
            return;
        }
        final JsonObject settingObject = moduleObject.get("Modules").getAsJsonObject();
        for (final Module module : ModuleManager.getModules()) {
            final JsonElement dataObject = settingObject.get(module.getName());
            if (dataObject != null && dataObject.isJsonPrimitive()) {
                module.setDrawn(dataObject.getAsBoolean());
            }
        }
        inputStream.close();
    }
    
    private static void loadToggleMessageModules() throws IOException {
        final String toggleMessageLocation = "LemonClient/Main/";
        final Path path = Paths.get(toggleMessageLocation + "ToggleMessages.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject moduleObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (moduleObject.get("Modules") == null) {
            return;
        }
        final JsonObject toggleObject = moduleObject.get("Modules").getAsJsonObject();
        for (final Module module : ModuleManager.getModules()) {
            final JsonElement dataObject = toggleObject.get(module.getName());
            if (dataObject != null && dataObject.isJsonPrimitive()) {
                module.setToggleMsg(dataObject.getAsBoolean());
            }
        }
        inputStream.close();
    }
    
    private static void loadCommandPrefix() throws IOException {
        final String prefixLocation = "LemonClient/Main/";
        final Path path = Paths.get(prefixLocation + "CommandPrefix.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (mainObject.get("Prefix") == null) {
            return;
        }
        final JsonElement prefixObject = mainObject.get("Prefix");
        if (prefixObject != null && prefixObject.isJsonPrimitive()) {
            CommandManager.setCommandPrefix(prefixObject.getAsString());
        }
        inputStream.close();
    }
    
    private static void loadCustomFont() throws IOException {
        final String fontLocation = "LemonClient/Misc/";
        final Path path = Paths.get(fontLocation + "CustomFont.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (mainObject.get("Font Name") == null || mainObject.get("Font Size") == null) {
            return;
        }
        final JsonElement fontNameObject = mainObject.get("Font Name");
        String name = null;
        if (fontNameObject != null && fontNameObject.isJsonPrimitive()) {
            name = fontNameObject.getAsString();
        }
        final JsonElement fontSizeObject = mainObject.get("Font Size");
        int size = -1;
        if (fontSizeObject != null && fontSizeObject.isJsonPrimitive()) {
            size = fontSizeObject.getAsInt();
        }
        if (name != null && size != -1) {
            (LemonClient.INSTANCE.cFontRenderer = new CFontRenderer(new Font(name, 0, size), true, true)).setFont(new Font(name, 0, size));
            LemonClient.INSTANCE.cFontRenderer.setAntiAlias(true);
            LemonClient.INSTANCE.cFontRenderer.setFractionalMetrics(true);
            LemonClient.INSTANCE.cFontRenderer.setFontName(name);
            LemonClient.INSTANCE.cFontRenderer.setFontSize(size);
        }
        inputStream.close();
    }
    
    private static void loadFriendsList() throws IOException {
        final String friendLocation = "LemonClient/Misc/";
        final Path path = Paths.get(friendLocation + "Friends.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (mainObject.get("Friends") == null) {
            return;
        }
        final JsonArray friendObject = mainObject.get("Friends").getAsJsonArray();
        friendObject.forEach(object -> SocialManager.addFriend(object.getAsString()));
        inputStream.close();
    }
    
    private static void loadEnemiesList() throws IOException {
        final String enemyLocation = "LemonClient/Misc/";
        final Path path = Paths.get(enemyLocation + "Enemies.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (mainObject.get("Enemies") == null) {
            return;
        }
        final JsonArray enemyObject = mainObject.get("Enemies").getAsJsonArray();
        enemyObject.forEach(object -> SocialManager.addEnemy(object.getAsString()));
        inputStream.close();
    }
    
    private static void loadClickGUIPositions() {
        LemonClientGUI.gui.loadConfig(new GuiConfig("LemonClient/Main/"));
    }
    
    private static void loadAutoGG() throws IOException {
        final String fileLocation = "LemonClient/Misc/";
        final Path path = Paths.get(fileLocation + "AutoEz.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (mainObject.get("Messages") == null) {
            return;
        }
        final JsonArray messageObject = mainObject.get("Messages").getAsJsonArray();
        messageObject.forEach(object -> AutoEz.addAutoEzMessage(object.getAsString()));
        inputStream.close();
    }
    
    private static void loadAutoRespawn() throws IOException {
        final String fileLocation = "LemonClient/Misc/";
        final Path path = Paths.get(fileLocation + "AutoRespawn.json", new String[0]);
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
        final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
        if (mainObject.get("Message") == null) {
            return;
        }
        final JsonElement dataObject = mainObject.get("Message");
        if (dataObject != null && dataObject.isJsonPrimitive()) {
            AutoRespawn.setAutoRespawnMessage(dataObject.getAsString());
        }
        inputStream.close();
    }
}
