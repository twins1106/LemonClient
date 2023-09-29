//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.config.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "LoadConfig", syntax = "loadconfig", alias = { "config load", "loadconfig", "load" })
public class LoadConfigCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        LoadConfig.init();
        MessageBus.sendCommandMessage("Config loaded!", true);
        if (none) {
            MessageBus.sendServerMessage("Config loaded!");
        }
        else {
            MessageBus.sendCommandMessage("Config loaded!", true);
        }
    }
}
