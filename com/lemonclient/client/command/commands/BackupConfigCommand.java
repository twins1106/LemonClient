//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import java.text.*;
import java.util.*;
import java.io.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "BackupConfig", syntax = "backupconfig", alias = { "backupconfig" })
public class BackupConfigCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String filename = "lemonclient-cofig-backup-v0.0.6-" + new SimpleDateFormat("yyyyMMdd.HHmmss.SSS").format(new Date()) + ".zip";
        ZipUtils.zip(new File("LemonClient/"), new File(filename));
        MessageBus.sendCommandMessage("Config successfully saved in " + filename + "!", true);
    }
}
