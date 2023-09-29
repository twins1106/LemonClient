//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;

@Command.Declaration(name = "FixHUD", syntax = "fixhud", alias = { "fixhud", "hud", "resethud" })
public class FixHUDCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        for (final Module module : ModuleManager.getModules()) {
            if (module instanceof HUDModule) {
                ((HUDModule)module).resetPosition();
            }
        }
        MessageBus.sendCommandMessage("HUD positions reset!", true);
    }
}
