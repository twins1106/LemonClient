//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.misc.*;
import java.util.regex.*;
import java.util.*;

@Module.Declaration(name = "ChatBot", category = Category.qwq)
public class ChatBot extends Module
{
    ModeSetting mode;
    IntegerSetting delay;
    String botmessage;
    boolean msg;
    int waited;
    private final Pattern CHAT_PATTERN;
    private final Pattern CHAT_PATTERN2;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public ChatBot() {
        this.mode = this.registerMode("Mode", (List)Arrays.asList("Client", "Everyone"), "Everyone");
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.CHAT_PATTERN = Pattern.compile("<.*?> ");
        this.CHAT_PATTERN2 = Pattern.compile("(.*?)");
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (this.msg) {
                return;
            }
            if (event.getPacket() instanceof SPacketChat) {
                String s = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText();
                final Matcher matcher = this.CHAT_PATTERN.matcher(s);
                String username = "unnamed";
                final Matcher matcher2 = this.CHAT_PATTERN2.matcher(s);
                if (matcher2.find()) {
                    matcher2.group();
                    s = matcher2.replaceFirst("");
                }
                if (matcher.find()) {
                    username = matcher.group();
                    username = username.substring(1, username.length() - 2);
                    s = matcher.replaceFirst("");
                }
                final StringBuilder builder = new StringBuilder();
                if (!s.startsWith("!")) {
                    return;
                }
                s = s.substring(Math.min(s.length(), 1));
                if (s.startsWith("online")) {
                    return;
                }
                if (s.startsWith("ping")) {
                    s = s.substring(Math.min(s.length(), 5));
                    final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
                    for (final Entity qwq : ChatBot.mc.world.loadedEntityList) {
                        if (qwq instanceof EntityPlayer && s.contains(qwq.getName())) {
                            s = qwq.getName();
                        }
                    }
                    final String finalS = s;
                    final NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> finalS.toLowerCase().contains(networkPlayerInfo.getGameProfile().getName().toLowerCase())).findFirst().orElse(null);
                    if (profile != null) {
                        final StringBuilder message = new StringBuilder();
                        message.append(profile.getGameProfile().getName());
                        message.append("'s ping is ");
                        message.append(profile.getResponseTime());
                        String messageSanitized = message.toString().replaceAll("\u79ae", "");
                        if (messageSanitized.length() > 255) {
                            messageSanitized = messageSanitized.substring(0, 255);
                        }
                        this.botmessage = messageSanitized;
                        this.msg = true;
                    }
                }
                else if (s.startsWith("myping")) {
                    final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
                    final String finalUsername = username;
                    final NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getName().equalsIgnoreCase(finalUsername)).findFirst().orElse(null);
                    if (profile != null) {
                        final StringBuilder message = new StringBuilder();
                        message.append("Your ping is ");
                        message.append(profile.getResponseTime());
                        String messageSanitized = message.toString().replaceAll("\u79ae", "");
                        if (messageSanitized.length() > 255) {
                            messageSanitized = messageSanitized.substring(0, 255);
                        }
                        this.botmessage = messageSanitized;
                        this.msg = true;
                    }
                }
                else if (s.startsWith("tps")) {
                    final StringBuilder message2 = new StringBuilder();
                    message2.append("The tps is now ");
                    message2.append(ServerUtil.getTPS());
                    String messageSanitized2 = message2.toString().replaceAll("\u79ae", "");
                    if (messageSanitized2.length() > 255) {
                        messageSanitized2 = messageSanitized2.substring(0, 255);
                    }
                    this.botmessage = messageSanitized2;
                    this.msg = true;
                }
                else if (s.startsWith("help")) {
                    final String uwu = "The commands are : tps, myping, ping playername";
                    String messageSanitized2 = uwu.replaceAll("\u79ae", "");
                    if (messageSanitized2.length() > 255) {
                        messageSanitized2 = messageSanitized2.substring(0, 255);
                    }
                    this.botmessage = messageSanitized2;
                    this.msg = true;
                }
                else if (s.startsWith("gay")) {
                    s = s.substring(Math.min(s.length(), 4));
                    final ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
                    for (final Entity qwq : ChatBot.mc.world.loadedEntityList) {
                        if (qwq instanceof EntityPlayer && s.contains(qwq.getName())) {
                            s = qwq.getName();
                        }
                    }
                    final String finalS = s;
                    final String finalS2;
                    final NetworkPlayerInfo profile = infoMap.stream().filter(networkPlayerInfo -> finalS2.toLowerCase().contains(networkPlayerInfo.getGameProfile().getName().toLowerCase())).findFirst().orElse(null);
                    if (profile != null) {
                        this.botmessage = profile.getGameProfile().getName() + " is " + String.format("%.1f", Math.random() * 100.0) + "% gay";
                        this.msg = true;
                    }
                }
                else if (s.startsWith("byebyebot")) {
                    this.botmessage = "!online owob";
                    this.msg = true;
                }
                else {
                    final String uwu = "Sorry, I cant understand this command";
                    String messageSanitized2 = uwu.replaceAll("\u79ae", "");
                    if (messageSanitized2.length() > 255) {
                        messageSanitized2 = messageSanitized2.substring(0, 255);
                    }
                    this.botmessage = messageSanitized2;
                    this.msg = true;
                }
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (this.msg) {
            if (((String)this.mode.getValue()).equals("Client")) {
                MessageBus.sendClientDeleteMessage(this.botmessage, "ChatBot", 4);
                this.msg = false;
            }
            else if (this.waited++ >= (int)this.delay.getValue()) {
                MessageBus.sendServerMessage(this.botmessage);
                this.waited = 0;
                this.msg = false;
            }
        }
    }
}
