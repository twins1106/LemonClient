//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.util.render.*;

@Command.Declaration(name = "LoadCape", syntax = "loadcape", alias = { "loadcape", "capeload", "reloadcape", "capereload" })
public class LoadCapeCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        MessageBus.sendCommandMessage("Reloaded Cape UUID", true);
        CapeUtil.init();
    }
}
