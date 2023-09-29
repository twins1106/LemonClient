//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "Prefix", syntax = "prefix value (no letters or numbers)", alias = { "prefix", "setprefix", "cmdprefix", "commandprefix" })
public class PrefixCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0].toUpperCase().replaceAll("[a-zA-Z0-9]", null);
        final int size = message[0].length();
        if (size == 1) {
            CommandManager.setCommandPrefix(main);
            MessageBus.sendCommandMessage("Prefix set: \"" + main + "\"!", true);
        }
        else if (size != 1) {
            MessageBus.sendCommandMessage(this.getSyntax(), true);
        }
    }
}
