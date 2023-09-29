//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;
import org.lwjgl.input.*;
import java.util.*;

@Command.Declaration(name = "Bind", syntax = "bind [module] key", alias = { "bind", "b", "setbind", "key" })
public class BindCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        final String value = message[1].toUpperCase();
        for (final Module module : ModuleManager.getModules()) {
            if (module.getName().equalsIgnoreCase(main)) {
                if (value.equalsIgnoreCase("none")) {
                    module.setBind(0);
                    MessageBus.sendCommandMessage("Module " + module.getName() + " bind set to: " + value + "!", true);
                }
                else if (value.length() == 1) {
                    final int key = Keyboard.getKeyIndex(value);
                    module.setBind(key);
                    MessageBus.sendCommandMessage("Module " + module.getName() + " bind set to: " + value + "!", true);
                }
                else {
                    if (value.length() <= 1) {
                        continue;
                    }
                    MessageBus.sendCommandMessage(this.getSyntax(), true);
                }
            }
        }
    }
}
