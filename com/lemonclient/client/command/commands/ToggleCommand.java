//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.client.module.*;

@Command.Declaration(name = "Toggle", syntax = "toggle [module]", alias = { "toggle", "t", "enable", "disable" })
public class ToggleCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        final Module module = ModuleManager.getModule(main);
        String string;
        if (module == null) {
            string = this.getSyntax();
        }
        else {
            module.toggle();
            if (module.isEnabled()) {
                string = "Module " + module.getName() + " set to: ENABLED!";
            }
            else {
                string = "Module " + module.getName() + " set to: DISABLED!";
            }
        }
        if (none) {
            MessageBus.sendServerMessage(string);
        }
        else {
            MessageBus.sendCommandMessage(string, true);
        }
    }
}
