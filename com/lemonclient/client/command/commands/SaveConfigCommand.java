//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.config.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "SaveConfig", syntax = "saveconfig", alias = { "config save", "saveconfig", "save" })
public class SaveConfigCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        SaveConfig.init();
        MessageBus.sendCommandMessage("Config saved!", true);
    }
}
