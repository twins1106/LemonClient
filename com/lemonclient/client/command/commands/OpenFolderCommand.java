//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import java.awt.*;
import com.lemonclient.api.util.misc.*;
import java.io.*;

@Command.Declaration(name = "OpenFolder", syntax = "openfolder", alias = { "openfolder", "open", "folder" })
public class OpenFolderCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        try {
            Desktop.getDesktop().open(new File("LemonClient/".replace("/", "")));
            MessageBus.sendCommandMessage("Opened config folder!", true);
        }
        catch (IOException e) {
            MessageBus.sendCommandMessage("Could not open config folder!", true);
            e.printStackTrace();
        }
    }
}
