//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.manager.managers;

import com.lemonclient.client.manager.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import com.lemonclient.client.*;
import net.minecraft.entity.*;

public enum TotemPopManager implements Manager
{
    INSTANCE;
    
    public boolean sendMsgs;
    public ChatFormatting chatFormatting;
    public ChatFormatting nameFormatting;
    public ChatFormatting friFormatting;
    public ChatFormatting numberFormatting;
    public boolean friend;
    public String self;
    public String type4;
    private final HashMap<String, Integer> playerPopCount;
    @EventHandler
    private final Listener<PacketEvent.Receive> packetEventListener;
    @EventHandler
    private final Listener<TotemPopEvent> totemPopEventListener;
    
    private TotemPopManager() {
        this.sendMsgs = false;
        this.chatFormatting = ChatFormatting.WHITE;
        this.nameFormatting = ChatFormatting.WHITE;
        this.friFormatting = ChatFormatting.WHITE;
        this.numberFormatting = ChatFormatting.WHITE;
        this.playerPopCount = new HashMap<String, Integer>();
        this.packetEventListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (this.getPlayer() == null || this.getWorld() == null) {
                return;
            }
            if (event.getPacket() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
                final Entity entity = packet.getEntity((World)this.getWorld());
                if (packet.getOpCode() == 35) {
                    LemonClient.EVENT_BUS.post((Object)new TotemPopEvent(entity));
                }
            }
        }, new Predicate[0]);
        this.totemPopEventListener = (Listener<TotemPopEvent>)new Listener(event -> {
            if (this.getPlayer() == null || this.getWorld() == null) {
                return;
            }
            if (event.getEntity() == null) {
                return;
            }
            final String entityName = event.getEntity().getName();
            if (this.playerPopCount.get(entityName) == null) {
                this.playerPopCount.put(entityName, 1);
                if (this.sendMsgs) {
                    if (Minecraft.getMinecraft().player.getName().equals(entityName) && this.self.equals("Disable")) {
                        return;
                    }
                    String name = (entityName.equals(Minecraft.getMinecraft().player.getName()) && this.self.equals("I")) ? "I" : entityName;
                    if (name.equals("") || name.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name) && !this.type4.equals("Enemy")) {
                        if (this.friend) {
                            name = "My Friend " + name;
                        }
                        MessageBus.sendClientDeleteMessage(this.friFormatting + name + this.chatFormatting + " popped " + this.numberFormatting + 1 + this.chatFormatting + " totem!", "TotemPopCounter" + name, 1000);
                    }
                    if (!SocialManager.isFriend(name) && !this.type4.equals("Friend")) {
                        MessageBus.sendClientDeleteMessage(this.nameFormatting + name + this.chatFormatting + " popped " + this.numberFormatting + 1 + this.chatFormatting + " totem!", "TotemPopCounter" + name, 1000);
                    }
                }
            }
            else {
                final int popCounter = this.playerPopCount.get(entityName) + 1;
                this.playerPopCount.put(entityName, popCounter);
                if (this.sendMsgs) {
                    if (Minecraft.getMinecraft().player.getName().equals(entityName) && this.self.equals("Disable")) {
                        return;
                    }
                    String name2 = (entityName.equals(Minecraft.getMinecraft().player.getName()) && this.self.equals("I")) ? "I" : entityName;
                    if (name2.equals("") || name2.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name2) && !this.type4.equals("Enemy")) {
                        if (this.friend) {
                            name2 = "My Friend " + name2;
                        }
                        MessageBus.sendClientDeleteMessage(this.friFormatting + name2 + this.chatFormatting + " popped " + this.numberFormatting + popCounter + this.chatFormatting + " totems!", "TotemPopCounter" + name2, 1000);
                    }
                    if (!SocialManager.isFriend(name2) && !this.type4.equals("Friend")) {
                        MessageBus.sendClientDeleteMessage(this.nameFormatting + name2 + this.chatFormatting + " popped " + this.numberFormatting + popCounter + this.chatFormatting + " totems!", "TotemPopCounter" + name2, 1000);
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void death(final EntityPlayer entityPlayer) {
        if (!this.playerPopCount.containsKey(entityPlayer.getName())) {
            return;
        }
        final int pop = this.getPlayerPopCount(entityPlayer.getName());
        if (this.sendMsgs) {
            if (Minecraft.getMinecraft().player.getName().equals(entityPlayer.getName()) && this.self.equals("Disable")) {
                return;
            }
            String name = (entityPlayer.getName().equals(Minecraft.getMinecraft().player.getName()) && this.self.equals("I")) ? "I" : entityPlayer.getName();
            if (name.equals("") || name.equals(" ")) {
                return;
            }
            if (SocialManager.isFriend(name) && !this.type4.equals("Enemy")) {
                if (this.friend) {
                    name = "My Friend " + name;
                }
                if (pop == 1) {
                    MessageBus.sendClientPrefixMessage(this.friFormatting + name + this.chatFormatting + " died after popping " + this.numberFormatting + this.getPlayerPopCount(entityPlayer.getName()) + this.chatFormatting + " totem!");
                }
                else {
                    MessageBus.sendClientPrefixMessage(this.friFormatting + name + this.chatFormatting + " died after popping " + this.numberFormatting + this.getPlayerPopCount(entityPlayer.getName()) + this.chatFormatting + " totems!");
                }
            }
            if (!SocialManager.isFriend(name) && !this.type4.equals("Friend")) {
                if (pop == 1) {
                    MessageBus.sendClientPrefixMessage(this.nameFormatting + name + this.chatFormatting + " died after popping " + this.numberFormatting + this.getPlayerPopCount(entityPlayer.getName()) + this.chatFormatting + " totem!");
                }
                else {
                    MessageBus.sendClientPrefixMessage(this.nameFormatting + name + this.chatFormatting + " died after popping " + this.numberFormatting + this.getPlayerPopCount(entityPlayer.getName()) + this.chatFormatting + " totems!");
                }
            }
        }
        this.playerPopCount.remove(entityPlayer.getName());
    }
    
    public int getPlayerPopCount(final String name) {
        if (this.playerPopCount.containsKey(name)) {
            return this.playerPopCount.get(name);
        }
        return 0;
    }
}
