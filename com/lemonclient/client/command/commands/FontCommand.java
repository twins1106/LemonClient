//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.command.commands;

import com.lemonclient.client.command.*;
import com.lemonclient.client.*;
import java.awt.*;
import com.lemonclient.api.util.font.*;
import com.lemonclient.api.util.misc.*;

@Command.Declaration(name = "Font", syntax = "font [name] size (use _ for spaces)", alias = { "font", "setfont", "customfont", "fonts", "chatfont" })
public class FontCommand extends Command
{
    public void onCommand(final String command, final String[] message, final boolean none) {
        final String main = message[0].replace("_", " ");
        int value = Integer.parseInt(message[1]);
        if (value >= 21 || value <= 15) {
            value = 18;
        }
        (LemonClient.INSTANCE.cFontRenderer = new CFontRenderer(new Font(main, 0, value), true, true)).setFontName(main);
        LemonClient.INSTANCE.cFontRenderer.setFontSize(value);
        MessageBus.sendCommandMessage("Font set to: " + main.toUpperCase() + ", size " + value + "!", true);
    }
}
