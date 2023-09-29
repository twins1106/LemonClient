//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import net.minecraft.client.*;
import java.awt.*;
import com.lemonclient.api.util.misc.*;
import java.awt.datatransfer.*;

@Module.Declaration(name = "Copy IP", category = Category.Misc)
public class CopyIp extends Module
{
    String server;
    
    public void onEnable() {
        final Minecraft mc = Minecraft.getMinecraft();
        try {
            this.server = mc.getCurrentServerData().serverIP;
        }
        catch (Exception e) {
            this.server = "Singleplayer";
        }
        final String myString = this.server;
        final StringSelection stringSelection = new StringSelection(myString);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        MessageBus.sendClientPrefixMessage("Copied '" + this.server + "' to clipboard.");
        this.disable();
    }
}
