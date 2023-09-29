//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;

@Command.Declaration(name = "Commands", syntax = "commands", alias = { "commands", "cmd", "command", "commandlist", "help" })
public class CmdListCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        for (final Command command2 : CommandManager.getCommands()) {
            MessageBus.sendMessage(command2.getName() + ": \"" + command2.getSyntax() + "\"!", true);
        }
    }
}
