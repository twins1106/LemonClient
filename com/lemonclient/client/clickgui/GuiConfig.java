//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.clickgui;

import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;
import com.lukflug.panelstudio.config.*;
import com.google.gson.*;
import java.awt.*;

public class GuiConfig implements IConfigList
{
    private final String fileLocation;
    private JsonObject panelObject;
    
    public GuiConfig(final String fileLocation) {
        this.panelObject = null;
        this.fileLocation = fileLocation;
    }
    
    @Override
    public void begin(final boolean loading) {
        if (loading) {
            final Path path = Paths.get(this.fileLocation + "ClickGUI.json", new String[0]);
            if (!Files.exists(path, new LinkOption[0])) {
                return;
            }
            try {
                final InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);
                final JsonObject mainObject = new JsonParser().parse((Reader)new InputStreamReader(inputStream)).getAsJsonObject();
                if (mainObject.get("Panels") == null) {
                    return;
                }
                this.panelObject = mainObject.get("Panels").getAsJsonObject();
                inputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            this.panelObject = new JsonObject();
        }
    }
    
    @Override
    public void end(final boolean loading) {
        if (this.panelObject == null) {
            return;
        }
        if (!loading) {
            try {
                final Gson gson = new GsonBuilder().setPrettyPrinting().create();
                final OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(this.fileLocation + "ClickGUI.json"), StandardCharsets.UTF_8);
                final JsonObject mainObject = new JsonObject();
                mainObject.add("Panels", (JsonElement)this.panelObject);
                final String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
                fileOutputStreamWriter.write(jsonString);
                fileOutputStreamWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.panelObject = null;
    }
    
    @Override
    public IPanelConfig addPanel(final String title) {
        if (this.panelObject == null) {
            return null;
        }
        final JsonObject valueObject = new JsonObject();
        this.panelObject.add(title, (JsonElement)valueObject);
        return new GSPanelConfig(valueObject);
    }
    
    @Override
    public IPanelConfig getPanel(final String title) {
        if (this.panelObject == null) {
            return null;
        }
        final JsonElement configObject = this.panelObject.get(title);
        if (configObject != null && configObject.isJsonObject()) {
            return new GSPanelConfig(configObject.getAsJsonObject());
        }
        return null;
    }
    
    private static class GSPanelConfig implements IPanelConfig
    {
        private final JsonObject configObject;
        
        public GSPanelConfig(final JsonObject configObject) {
            this.configObject = configObject;
        }
        
        @Override
        public void savePositon(final Point position) {
            this.configObject.add("PosX", (JsonElement)new JsonPrimitive((Number)position.x));
            this.configObject.add("PosY", (JsonElement)new JsonPrimitive((Number)position.y));
        }
        
        @Override
        public void saveSize(final Dimension size) {
        }
        
        @Override
        public Point loadPosition() {
            final Point point = new Point();
            final JsonElement panelPosXObject = this.configObject.get("PosX");
            if (panelPosXObject == null || !panelPosXObject.isJsonPrimitive()) {
                return null;
            }
            point.x = panelPosXObject.getAsInt();
            final JsonElement panelPosYObject = this.configObject.get("PosY");
            if (panelPosYObject != null && panelPosYObject.isJsonPrimitive()) {
                point.y = panelPosYObject.getAsInt();
                return point;
            }
            return null;
        }
        
        @Override
        public Dimension loadSize() {
            return null;
        }
        
        @Override
        public void saveState(final boolean state) {
            this.configObject.add("State", (JsonElement)new JsonPrimitive(Boolean.valueOf(state)));
        }
        
        @Override
        public boolean loadState() {
            final JsonElement panelOpenObject = this.configObject.get("State");
            return panelOpenObject != null && panelOpenObject.isJsonPrimitive() && panelOpenObject.getAsBoolean();
        }
    }
}
