//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.module.modules.qwq.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "AutoEz", syntax = "autoez add/del [message] (use _ for spaces, {name} for name)", alias = { "autoez", "ez" })
public class AutoEzCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        final String value = message[1].replace("_", " ");
        if (main.equalsIgnoreCase("add") && !AutoEz.getAutoEzMessages().contains(value)) {
            AutoEz.addAutoEzMessage(value);
            MessageBus.sendCommandMessage("Added AutoEz message: " + value + "!", true);
        }
        else if (main.equalsIgnoreCase("del") && AutoEz.getAutoEzMessages().contains(value)) {
            AutoEz.getAutoEzMessages().remove(value);
            MessageBus.sendCommandMessage("Deleted AutoEz message: " + value + "!", true);
        }
    }
}
