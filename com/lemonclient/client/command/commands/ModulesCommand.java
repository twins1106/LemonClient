//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.module.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import com.lemonclient.client.command.*;
import java.util.*;

@Command.Declaration(name = "Modules", syntax = "modules (click to toggle)", alias = { "modules", "module", "modulelist", "mod", "mods" })
public class ModulesCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final TextComponentString msg = new TextComponentString("¡×7Modules: ¡×f ");
        final Collection<Module> modules = ModuleManager.getModules();
        final int size = modules.size();
        int index = 0;
        for (final Module module : modules) {
            msg.appendSibling(new TextComponentString((module.isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED) + module.getName() + "¡×7" + ((index == size - 1) ? "" : ", ")).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(module.getCategory().name()))).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, CommandManager.getCommandPrefix() + "toggle " + module.getName()))));
            ++index;
        }
        msg.appendSibling((ITextComponent)new TextComponentString(ChatFormatting.GRAY + "!"));
        ModulesCommand.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)msg);
    }
}
