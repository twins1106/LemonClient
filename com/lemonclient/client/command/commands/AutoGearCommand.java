//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.misc.*;
import com.google.gson.*;
import net.minecraft.item.*;
import java.util.*;
import java.io.*;
import com.lemonclient.client.module.modules.combat.*;

@Command.Declaration(name = "AutoGear", syntax = "gear set/save/del/list [name]", alias = { "gear", "gr", "kit" })
public class AutoGearCommand extends Command
{
    private static final String pathSave = "LemonClient/Misc/AutoGear.json";
    private static final HashMap<String, String> errorMessage;
    
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String lowerCase = message[0].toLowerCase();
        switch (lowerCase) {
            case "list": {
                if (message.length == 1) {
                    this.listMessage();
                    break;
                }
                errorMessage("NoPar");
                break;
            }
            case "set": {
                if (message.length == 2) {
                    this.set(message[1]);
                    break;
                }
                errorMessage("NoPar");
                break;
            }
            case "save":
            case "add":
            case "create": {
                if (message.length == 2) {
                    this.save(message[1]);
                    break;
                }
                errorMessage("NoPar");
                break;
            }
            case "del": {
                if (message.length == 2) {
                    this.delete(message[1]);
                    break;
                }
                errorMessage("NoPar");
                break;
            }
            default: {
                MessageBus.sendCommandMessage("AutoGear message is: gear set/save/del/list [name]", true);
                break;
            }
        }
    }
    
    private void listMessage() {
        JsonObject completeJson = new JsonObject();
        String string = "";
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject();
            for (int lenghtJson = completeJson.entrySet().size(), i = 0; i < lenghtJson; ++i) {
                final String item = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject().entrySet().toArray()[i].toString().split("=")[0];
                if (!item.equals("pointer")) {
                    if (string.equals("")) {
                        string = item;
                    }
                    else {
                        string = string + ", " + item;
                    }
                }
            }
            MessageBus.sendCommandMessage("Kit avaible: " + string, true);
        }
        catch (IOException e) {
            errorMessage("NoEx");
        }
    }
    
    private void delete(final String name) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject();
            if (completeJson.get(name) != null && !name.equals("pointer")) {
                completeJson.remove(name);
                if (completeJson.get("pointer").getAsString().equals(name)) {
                    completeJson.addProperty("pointer", "none");
                }
                this.saveFile(completeJson, name, "deleted");
            }
            else {
                errorMessage("NoEx");
            }
        }
        catch (IOException e) {
            errorMessage("NoEx");
        }
    }
    
    private void set(final String name) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject();
            if (completeJson.get(name) != null && !name.equals("pointer")) {
                completeJson.addProperty("pointer", name);
                this.saveFile(completeJson, name, "selected");
            }
            else {
                errorMessage("NoEx");
            }
        }
        catch (IOException e) {
            errorMessage("NoEx");
        }
    }
    
    private void save(final String name) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject();
            if (completeJson.get(name) != null && !name.equals("pointer")) {
                errorMessage("Exist");
                return;
            }
        }
        catch (IOException e) {
            completeJson.addProperty("pointer", "none");
        }
        final StringBuilder jsonInventory = new StringBuilder();
        for (final ItemStack item : AutoGearCommand.mc.player.inventory.mainInventory) {
            jsonInventory.append(item.getItem().getRegistryName().toString() + item.getMetadata()).append(" ");
        }
        completeJson.addProperty(name, jsonInventory.toString());
        this.saveFile(completeJson, name, "saved");
    }
    
    private void saveFile(final JsonObject completeJson, final String name, final String operation) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter("LemonClient/Misc/AutoGear.json"));
            bw.write(completeJson.toString());
            bw.close();
            PistonCrystal.printDebug("Kit " + name + " " + operation, false);
        }
        catch (IOException e) {
            errorMessage("Saving");
        }
    }
    
    private static void errorMessage(final String e) {
        PistonCrystal.printDebug("Error: " + AutoGearCommand.errorMessage.get(e), true);
    }
    
    public static String getCurrentSet() {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject();
            if (!completeJson.get("pointer").getAsString().equals("none")) {
                return completeJson.get("pointer").getAsString();
            }
        }
        catch (IOException ex) {}
        errorMessage("NoEx");
        return "";
    }
    
    public static String getInventoryKit(final String kit) {
        JsonObject completeJson = new JsonObject();
        try {
            completeJson = new JsonParser().parse((Reader)new FileReader("LemonClient/Misc/AutoGear.json")).getAsJsonObject();
            return completeJson.get(kit).getAsString();
        }
        catch (IOException ex) {
            errorMessage("NoEx");
            return "";
        }
    }
    
    static {
        errorMessage = new HashMap<String, String>() {
            {
                this.put("NoPar", "Not enough parameters");
                this.put("Exist", "This kit arleady exist");
                this.put("Saving", "Error saving the file");
                this.put("NoEx", "Kit not found");
            }
        };
    }
}
