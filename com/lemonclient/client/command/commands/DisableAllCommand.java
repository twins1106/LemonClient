//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;

@Command.Declaration(name = "DisableAll", syntax = "disableall", alias = { "disableall", "stop" })
public class DisableAllCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        int count = 0;
        for (final Module module : ModuleManager.getModules()) {
            if (module.isEnabled()) {
                module.disable();
                ++count;
            }
        }
        MessageBus.sendCommandMessage("Disabled " + count + " modules!", true);
    }
}
