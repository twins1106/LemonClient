//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.*;
import com.lemonclient.client.clickgui.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "FixGUI", syntax = "fixgui", alias = { "fixgui", "gui", "resetgui" })
public class FixGUICommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        LemonClient.INSTANCE.gameSenseGUI = new LemonClientGUI();
        MessageBus.sendCommandMessage("ClickGUI positions reset!", true);
    }
}
