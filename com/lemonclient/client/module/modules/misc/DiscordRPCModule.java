//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import club.minnced.discord.rpc.*;
import net.minecraft.client.multiplayer.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import java.util.function.*;

@Module.Declaration(name = "DiscordRPC", category = Category.Misc)
public class DiscordRPCModule extends Module
{
    private static final String applicationId = "899193061324775454";
    BooleanSetting PlayerID;
    BooleanSetting ServerIp;
    BooleanSetting coords;
    private final DiscordRPC discordRPC;
    DiscordEventHandlers handlers;
    DiscordRichPresence presence;
    static String lastChat;
    static ServerData svr;
    @EventHandler
    private final Listener<ClientChatReceivedEvent> listener;
    
    public DiscordRPCModule() {
        this.PlayerID = this.registerBoolean("Player ID", true);
        this.ServerIp = this.registerBoolean("Server IP", true);
        this.coords = this.registerBoolean("Coords", true);
        this.discordRPC = DiscordRPC.INSTANCE;
        this.handlers = new DiscordEventHandlers();
        this.presence = new DiscordRichPresence();
        this.listener = (Listener<ClientChatReceivedEvent>)new Listener(event -> DiscordRPCModule.lastChat = event.getMessage().getUnformattedText(), new Predicate[0]);
    }
    
    public void onEnable() {
        this.init();
    }
    
    public void onDisable() {
        this.discordRPC.Discord_Shutdown();
        this.discordRPC.Discord_ClearPresence();
    }
    
    private void init() {
        this.discordRPC.Discord_Initialize("899193061324775454", this.handlers, true, "");
        this.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        this.presence.state = "Main Menu";
        if (this.PlayerID.getValue()) {
            this.presence.details = ID();
        }
        else {
            this.presence.details = "";
        }
        this.presence.largeImageKey = "lemonclient";
        this.presence.largeImageText = "Lemon Client v0.0.6";
        this.discordRPC.Discord_UpdatePresence(this.presence);
        String dimension;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() && this.isEnabled()) {
                try {
                    this.discordRPC.Discord_RunCallbacks();
                    if (this.PlayerID.getValue()) {
                        this.presence.details = ID();
                    }
                    else {
                        this.presence.details = "";
                    }
                    this.presence.state = "";
                    if ((boolean)this.coords.getValue() && DiscordRPCModule.mc.player != null && DiscordRPCModule.mc.world != null) {
                        this.presence.smallImageKey = "lazy_crocodile";
                        if (this.dimension() == -1) {
                            dimension = "Nether";
                        }
                        else if (this.dimension() == 0) {
                            dimension = "Overworld";
                        }
                        else {
                            dimension = "The End";
                        }
                        this.presence.smallImageText = "X:" + (int)DiscordRPCModule.mc.player.posX + " Y:" + (int)DiscordRPCModule.mc.player.posY + " Z:" + (int)DiscordRPCModule.mc.player.posZ + " (" + dimension + ")";
                    }
                    else {
                        this.presence.smallImageText = "";
                    }
                    if (DiscordRPCModule.mc.isIntegratedServerRunning()) {
                        this.presence.state = "Single Player";
                    }
                    else if (DiscordRPCModule.mc.getCurrentServerData() != null) {
                        DiscordRPCModule.svr = DiscordRPCModule.mc.getCurrentServerData();
                        if (!DiscordRPCModule.svr.serverIP.equals("")) {
                            if (this.ServerIp.getValue()) {
                                this.presence.state = "Multi Player (" + DiscordRPCModule.svr.serverIP + ")";
                                if (DiscordRPCModule.svr.serverIP.equals("2b2t.org")) {
                                    try {
                                        if (DiscordRPCModule.lastChat.contains("Position in queue: ")) {
                                            this.presence.details = this.presence.details + " (in queue" + Integer.parseInt(DiscordRPCModule.lastChat.substring(19)) + ")";
                                        }
                                    }
                                    catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else {
                                this.presence.state = "Multi Player";
                            }
                        }
                    }
                    else {
                        this.presence.details = "Main Menu";
                    }
                    this.discordRPC.Discord_UpdatePresence(this.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }
    
    private int dimension() {
        return DiscordRPCModule.mc.player.dimension;
    }
    
    public static String ID() {
        if (DiscordRPCModule.mc.player != null) {
            return DiscordRPCModule.mc.player.getName();
        }
        return DiscordRPCModule.mc.getSession().getUsername();
    }
}
