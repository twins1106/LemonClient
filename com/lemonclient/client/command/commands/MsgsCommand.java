//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.client.module.*;

@Command.Declaration(name = "Msgs", syntax = "msgs [module]", alias = { "msgs", "togglemsgs", "showmsgs", "messages" })
public class MsgsCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        final Module module = ModuleManager.getModule(main);
        if (module == null) {
            MessageBus.sendCommandMessage(this.getSyntax(), true);
            return;
        }
        if (module.isToggleMsg()) {
            module.setToggleMsg(false);
            MessageBus.sendCommandMessage("Module " + module.getName() + " message toggle set to: FALSE!", true);
        }
        else {
            module.setToggleMsg(true);
            MessageBus.sendCommandMessage("Module " + module.getName() + " message toggle set to: TRUE!", true);
        }
    }
}
