//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.module.modules.misc.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "AutoRespawn", syntax = "autorespawn get/set [message] (do NOT use _ for spaces)", alias = { "autorespawn", "respawn" })
public class AutoRespawnCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        if (main.equalsIgnoreCase("get")) {
            MessageBus.sendCommandMessage("AutoRespawn message is: " + AutoRespawn.getAutoRespawnMessages() + "!", true);
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < message.length; ++i) {
            stringBuilder.append(message[i]);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        final String value = stringBuilder.toString();
        if (main.equalsIgnoreCase("set") && !AutoRespawn.getAutoRespawnMessages().equals(value)) {
            AutoRespawn.setAutoRespawnMessage(value);
            MessageBus.sendCommandMessage("Set AutoRespawn message to: " + value + "!", true);
        }
    }
}
