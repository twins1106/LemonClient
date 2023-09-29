//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.misc.*;
import com.mojang.realmsclient.gui.*;
import java.util.function.*;
import net.minecraft.network.play.client.*;
import com.lemonclient.client.command.*;
import java.text.*;
import java.util.*;
import net.minecraft.util.text.*;

@Module.Declaration(name = "ChatModifier", category = Category.Misc)
public class ChatModifier extends Module
{
    public BooleanSetting clearBkg;
    public Pair<Object, Object> watermarkSpecial;
    BooleanSetting greenText;
    BooleanSetting chatTimeStamps;
    ModeSetting format;
    ModeSetting decoration;
    ModeSetting color;
    BooleanSetting space;
    @EventHandler
    private final Listener<ClientChatReceivedEvent> chatReceivedEventListener;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;
    
    public ChatModifier() {
        this.clearBkg = this.registerBoolean("Clear Chat", false);
        this.greenText = this.registerBoolean("Green Text", false);
        this.chatTimeStamps = this.registerBoolean("Chat Time Stamps", false);
        this.format = this.registerMode("Format", (List)Arrays.asList("H24:mm", "H12:mm", "H12:mm a", "H24:mm:ss", "H12:mm:ss", "H12:mm:ss a"), "H24:mm");
        this.decoration = this.registerMode("Deco", (List)Arrays.asList("< >", "[ ]", "{ }", " "), "[ ]");
        this.color = this.registerMode("Color", ColorUtil.colors, ChatFormatting.GRAY.getName());
        this.space = this.registerBoolean("Space", false);
        this.chatReceivedEventListener = (Listener<ClientChatReceivedEvent>)new Listener(event -> {
            if (this.chatTimeStamps.getValue()) {
                final String decoLeft = ((String)this.decoration.getValue()).equalsIgnoreCase(" ") ? "" : ((String)this.decoration.getValue()).split(" ")[0];
                String decoRight = ((String)this.decoration.getValue()).equalsIgnoreCase(" ") ? "" : ((String)this.decoration.getValue()).split(" ")[1];
                if (this.space.getValue()) {
                    decoRight += " ";
                }
                final String dateFormat = ((String)this.format.getValue()).replace("H24", "k").replace("H12", "h");
                final String date = new SimpleDateFormat(dateFormat).format(new Date());
                final TextComponentString time = new TextComponentString(ChatFormatting.getByName((String)this.color.getValue()) + decoLeft + date + decoRight + ChatFormatting.RESET);
                event.setMessage(time.appendSibling(event.getMessage()));
            }
        }, new Predicate[0]);
        this.listener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if ((boolean)this.greenText.getValue() && event.getPacket() instanceof CPacketChatMessage) {
                if (((CPacketChatMessage)event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage)event.getPacket()).getMessage().startsWith(CommandManager.getCommandPrefix())) {
                    return;
                }
                final String message = ((CPacketChatMessage)event.getPacket()).getMessage();
                String prefix = "";
                prefix = ">";
                final String s = prefix + message;
                if (s.length() > 255) {
                    return;
                }
                ((CPacketChatMessage)event.getPacket()).message = s;
            }
        }, new Predicate[0]);
    }
}
