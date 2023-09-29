//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import com.mojang.realmsclient.gui.*;
import net.minecraft.client.*;
import com.lemonclient.client.module.modules.hud.*;
import net.minecraft.util.text.*;
import com.lemonclient.api.util.chat.*;
import com.lemonclient.client.module.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class MessageBus
{
    public static String watermark;
    public static ChatFormatting messageFormatting;
    protected static final Minecraft mc;
    
    public static void sendClientPrefixMessage(final String message) {
        final TextComponentString string1 = new TextComponentString(MessageBus.watermark + MessageBus.messageFormatting + message);
        final TextComponentString string2 = new TextComponentString(MessageBus.messageFormatting + message);
        final Notifications notifications = ModuleManager.getModule(Notifications.class);
        notifications.addMessage(string2);
        if (notifications.isEnabled() && (boolean)notifications.disableChat.getValue()) {
            return;
        }
        MessageBus.mc.player.sendMessage((ITextComponent)string1);
    }
    
    public static void sendClientDeleteMessage(final String message, final String uniqueWord, final int senderID) {
        final TextComponentString string2 = new TextComponentString(MessageBus.messageFormatting + message);
        final Notifications notifications = ModuleManager.getModule(Notifications.class);
        notifications.addMessage(string2);
        if (notifications.isEnabled() && (boolean)notifications.disableChat.getValue()) {
            return;
        }
        ChatUtil.sendDeleteMessage(MessageBus.watermark + MessageBus.messageFormatting + message, uniqueWord, senderID);
    }
    
    public static void sendCommandMessage(final String message, final boolean prefix) {
        final String watermark1 = prefix ? MessageBus.watermark : "";
        ChatUtil.sendDeleteMessage(watermark1 + MessageBus.messageFormatting + message, "Command", 6);
    }
    
    public static void sendMessage(final String message, final boolean prefix) {
        final TextComponentString string = new TextComponentString(MessageBus.messageFormatting + message);
        MessageBus.mc.player.sendMessage((ITextComponent)string);
    }
    
    public static void sendClientRawMessage(final String message) {
        final TextComponentString string = new TextComponentString(MessageBus.messageFormatting + message);
        final Notifications notifications = ModuleManager.getModule(Notifications.class);
        notifications.addMessage(string);
        if (ModuleManager.isModuleEnabled(Notifications.class) && (boolean)notifications.disableChat.getValue()) {
            return;
        }
        MessageBus.mc.player.sendMessage((ITextComponent)string);
    }
    
    public static void sendServerMessage(final String message) {
        MessageBus.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(message));
    }
    
    static {
        MessageBus.watermark = ChatFormatting.GREEN + "[" + ChatFormatting.YELLOW + "Lemon" + ChatFormatting.GREEN + "] " + ChatFormatting.RESET;
        MessageBus.messageFormatting = ChatFormatting.GRAY;
        mc = Minecraft.getMinecraft();
    }
}
