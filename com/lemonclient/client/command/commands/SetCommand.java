//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.*;

@Command.Declaration(name = "Set", syntax = "set [module] [setting] value (no color support)", alias = { "set", "setmodule", "changesetting", "setting" })
public class SetCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0];
        final Module module = ModuleManager.getModule(main);
        final String[] string = { null };
        if (module == null) {
            string[0] = this.getSyntax();
            return;
        }
        final Object o;
        final Module module2;
        SettingsManager.getSettingsForModule(module).stream().filter(setting -> setting.getConfigName().equalsIgnoreCase(message[1])).forEach(setting -> {
            if (setting instanceof BooleanSetting) {
                if (message[2].equalsIgnoreCase("true") || message[2].equalsIgnoreCase("false")) {
                    ((Setting)setting).setValue((Object)Boolean.parseBoolean(message[2]));
                    o[0] = module2.getName() + " " + ((Setting)setting).getConfigName() + " set to: " + ((Setting)setting).getValue() + "!";
                }
                else {
                    o[0] = this.getSyntax();
                }
            }
            else if (setting instanceof IntegerSetting) {
                if (Integer.parseInt(message[2]) > ((IntegerSetting)setting).getMax()) {
                    ((Setting)setting).setValue((Object)((IntegerSetting)setting).getMax());
                }
                if (Integer.parseInt(message[2]) < ((IntegerSetting)setting).getMin()) {
                    ((Setting)setting).setValue((Object)((IntegerSetting)setting).getMin());
                }
                if (Integer.parseInt(message[2]) < ((IntegerSetting)setting).getMax() && Integer.parseInt(message[2]) > ((IntegerSetting)setting).getMin()) {
                    ((Setting)setting).setValue((Object)Integer.parseInt(message[2]));
                }
                o[0] = module2.getName() + " " + ((Setting)setting).getConfigName() + " set to: " + ((Setting)setting).getValue() + "!";
            }
            else if (setting instanceof DoubleSetting) {
                if (Double.parseDouble(message[2]) > ((DoubleSetting)setting).getMax()) {
                    ((Setting)setting).setValue((Object)((DoubleSetting)setting).getMax());
                }
                if (Double.parseDouble(message[2]) < ((DoubleSetting)setting).getMin()) {
                    ((Setting)setting).setValue((Object)((DoubleSetting)setting).getMin());
                }
                if (Double.parseDouble(message[2]) < ((DoubleSetting)setting).getMax() && Double.parseDouble(message[2]) > ((DoubleSetting)setting).getMin()) {
                    ((Setting)setting).setValue((Object)Double.parseDouble(message[2]));
                }
                o[0] = module2.getName() + " " + ((Setting)setting).getConfigName() + " set to: " + ((Setting)setting).getValue() + "!";
            }
            else if (setting instanceof ModeSetting) {
                if (!setting.getModes().contains(message[2])) {
                    o[0] = this.getSyntax();
                }
                else {
                    ((Setting)setting).setValue((Object)message[2]);
                    o[0] = module2.getName() + " " + ((Setting)setting).getConfigName() + " set to: " + ((Setting)setting).getValue() + "!";
                }
            }
            else {
                o[0] = this.getSyntax();
            }
            return;
        });
        if (none) {
            MessageBus.sendServerMessage(string[0]);
        }
        else {
            MessageBus.sendCommandMessage(string[0], true);
        }
    }
}
