//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command;

import java.util.*;
import com.lemonclient.client.command.commands.*;
import com.lemonclient.api.util.misc.*;

public class CommandManager
{
    private static String commandPrefix;
    public static final ArrayList<Command> commands;
    public static boolean isValidCommand;
    
    public static void init() {
        addCommand(new AutoEzCommand());
        addCommand(new AutoGearCommand());
        addCommand(new AutoRespawnCommand());
        addCommand(new BackupConfigCommand());
        addCommand(new BindCommand());
        addCommand(new CmdListCommand());
        addCommand(new DisableAllCommand());
        addCommand(new DrawnCommand());
        addCommand(new EnemyCommand());
        addCommand(new FixGUICommand());
        addCommand(new FixHUDCommand());
        addCommand(new FontCommand());
        addCommand(new FriendCommand());
        addCommand(new LoadCapeCommand());
        addCommand(new LoadConfigCommand());
        addCommand(new ModulesCommand());
        addCommand(new MsgsCommand());
        addCommand(new OpenFolderCommand());
        addCommand(new PrefixCommand());
        addCommand(new SaveConfigCommand());
        addCommand(new SetCommand());
        addCommand(new ToggleCommand());
    }
    
    public static void addCommand(final Command command) {
        CommandManager.commands.add(command);
    }
    
    public static ArrayList<Command> getCommands() {
        return CommandManager.commands;
    }
    
    public static String getCommandPrefix() {
        return CommandManager.commandPrefix;
    }
    
    public static void setCommandPrefix(final String prefix) {
        CommandManager.commandPrefix = prefix;
    }
    
    public static void callCommand(final String input, final boolean none) {
        final String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        final String command2 = split[0];
        final String args = input.substring(command2.length()).trim();
        CommandManager.isValidCommand = false;
        final String[] array;
        int length;
        int i = 0;
        String string;
        final String s;
        final String s2;
        CommandManager.commands.forEach(command -> {
            command.getAlias();
            for (length = array.length; i < length; ++i) {
                string = array[i];
                if (string.equalsIgnoreCase(s)) {
                    CommandManager.isValidCommand = true;
                    try {
                        command.onCommand(s2, s2.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"), none);
                    }
                    catch (Exception e) {
                        MessageBus.sendCommandMessage(command.getSyntax(), true);
                    }
                }
            }
            return;
        });
        if (!CommandManager.isValidCommand) {
            MessageBus.sendCommandMessage("Error! Invalid command!", true);
        }
    }
    
    static {
        CommandManager.commandPrefix = "-";
        commands = new ArrayList<Command>();
        CommandManager.isValidCommand = false;
    }
}
