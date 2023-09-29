//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import com.lemonclient.api.util.player.social.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import com.lemonclient.client.*;
import net.minecraft.entity.*;

@Module.Declaration(name = "TotemPopCounter", category = Category.Dev)
public class TotemPopCounter extends Module
{
    BooleanSetting countFriends;
    BooleanSetting countSelf;
    BooleanSetting resetDeaths;
    BooleanSetting resetSelfDeaths;
    ModeSetting announceSetting;
    BooleanSetting thanksTo;
    ModeSetting chatColor;
    ModeSetting nameColor;
    ModeSetting friColor;
    ModeSetting numberColor;
    ModeSetting self;
    private HashMap<String, Integer> playerList;
    boolean isdead;
    @EventHandler
    private final Listener<DeathEvent> deathEventListener;
    @EventHandler
    public Listener<EntityUseTotemEvent> listListener;
    @EventHandler
    public Listener<PacketEvent.Receive> popListener;
    
    public TotemPopCounter() {
        this.countFriends = this.registerBoolean("Count Friends", true);
        this.countSelf = this.registerBoolean("Count Self", false);
        this.resetDeaths = this.registerBoolean("Reset On Death", true);
        this.resetSelfDeaths = this.registerBoolean("Reset Self Death", true);
        this.announceSetting = this.registerMode("Mode", (List)Arrays.asList("Client", "Everyone"), "Client");
        this.thanksTo = this.registerBoolean("Thanks to", false);
        this.chatColor = this.registerMode("Color", ColorUtil.colors, "Light Purple");
        this.nameColor = this.registerMode("Name Color", ColorUtil.colors, "Light Purple");
        this.friColor = this.registerMode("Friend Color", ColorUtil.colors, "Light Purple");
        this.numberColor = this.registerMode("Number Color", ColorUtil.colors, "Light Purple");
        this.self = this.registerMode("Self", (List)Arrays.asList("I", "Name"), "Name");
        this.playerList = new HashMap<String, Integer>();
        this.deathEventListener = (Listener<DeathEvent>)new Listener(event -> {
            if (event.player == TotemPopCounter.mc.player) {
                this.isdead = true;
                return;
            }
            if (!this.playerList.containsKey(event.player.getName())) {
                return;
            }
            if (this.resetDeaths.getValue()) {
                final int popCount = this.playerList.get(event.player.getName());
                if (popCount > 1) {
                    this.sendDeathMessage(this.formatName(event.player.getName()) + " died after popping " + this.formatNumber(this.playerList.get(event.player.getName())) + " totems" + this.ending());
                }
                else {
                    this.sendDeathMessage(this.formatName(event.player.getName()) + " died after popping " + this.formatNumber(this.playerList.get(event.player.getName())) + " totem" + this.ending());
                }
                this.playerList.remove(event.player.getName(), this.playerList.get(event.player.getName()));
            }
        }, new Predicate[0]);
        this.listListener = (Listener<EntityUseTotemEvent>)new Listener(event -> {
            if (this.playerList == null) {
                this.playerList = new HashMap<String, Integer>();
            }
            final String name = event.getEntity().getName();
            if (this.playerList.get(name) == null) {
                this.playerList.put(name, 1);
                this.sendMessage(this.formatName(name) + " popped " + this.formatNumber(1) + " totem" + this.ending());
            }
            else if (this.playerList.get(name) != null) {
                int popCounter = this.playerList.get(name);
                ++popCounter;
                this.playerList.put(name, popCounter);
                this.sendMessage(this.formatName(name) + " popped " + this.formatNumber(popCounter) + " totems" + this.ending());
            }
        }, new Predicate[0]);
        this.popListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (TotemPopCounter.mc.player == null) {
                return;
            }
            if (event.getPacket() instanceof SPacketEntityStatus) {
                final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
                if (packet.getOpCode() == 35) {
                    final Entity entity = packet.getEntity((World)TotemPopCounter.mc.world);
                    if (this.friendCheck(entity.getName()) || this.selfCheck(entity.getName())) {
                        LemonClient.EVENT_BUS.post((Object)new EntityUseTotemEvent(entity));
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (this.isdead && (boolean)this.resetSelfDeaths.getValue() && (0.0f >= TotemPopCounter.mc.player.getHealth() || !TotemPopCounter.mc.player.isEntityAlive() || TotemPopCounter.mc.player.isDead)) {
            this.sendDeathMessage(this.formatName(TotemPopCounter.mc.player.getName()) + " died and " + this.grammar(TotemPopCounter.mc.player.getName()) + " pop list was reset!");
            this.playerList.clear();
            this.isdead = false;
        }
    }
    
    private boolean friendCheck(final String name) {
        return !SocialManager.isFriend(name) || (boolean)this.countFriends.getValue();
    }
    
    private boolean selfCheck(final String name) {
        return ((boolean)this.countSelf.getValue() && name.equalsIgnoreCase(TotemPopCounter.mc.player.getName())) || (boolean)this.countSelf.getValue() || !name.equalsIgnoreCase(TotemPopCounter.mc.player.getName());
    }
    
    private boolean isSelf(final String name) {
        return name.equalsIgnoreCase(TotemPopCounter.mc.player.getName());
    }
    
    private boolean isFriend(final String name) {
        return SocialManager.isFriend(name);
    }
    
    private ChatFormatting getColor(final String name) {
        if (SocialManager.isFriend(name) || Objects.equals(name, TotemPopCounter.mc.player.getName())) {
            return ColorUtil.textToChatFormatting(this.friColor);
        }
        return ColorUtil.textToChatFormatting(this.nameColor);
    }
    
    private String formatName(String name) {
        String extraText = "";
        if (this.isFriend(name) && this.isPublic()) {
            extraText = "My friend ";
        }
        if (this.isSelf(name)) {
            extraText = "";
            name = ((name.equals(TotemPopCounter.mc.player.getName()) && ((String)this.self.getValue()).equals("I")) ? "I" : name);
        }
        if (((String)this.announceSetting.getValue()).equals("Everyone")) {
            return extraText + name;
        }
        return extraText + this.getColor(name) + name + ColorUtil.textToChatFormatting(this.chatColor);
    }
    
    private String grammar(final String name) {
        if (this.isSelf(name)) {
            return "my";
        }
        return "their";
    }
    
    private String ending() {
        if (this.thanksTo.getValue()) {
            return " thanks to Lemon Client!";
        }
        return "!";
    }
    
    private boolean isPublic() {
        return ((String)this.announceSetting.getValue()).equals("Everyone");
    }
    
    private String formatNumber(final int message) {
        if (((String)this.announceSetting.getValue()).equals("Everyone")) {
            return "" + message;
        }
        return ColorUtil.textToChatFormatting(this.numberColor) + "" + message + ColorUtil.textToChatFormatting(this.chatColor);
    }
    
    private void sendMessage(final String message) {
        final String s = (String)this.announceSetting.getValue();
        switch (s) {
            case "Client": {
                MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.chatColor) + message, "TotemPopCounter", 1000);
            }
            case "Everyone": {
                MessageBus.sendServerMessage(message);
            }
            default: {}
        }
    }
    
    private void sendDeathMessage(final String message) {
        final String s = (String)this.announceSetting.getValue();
        switch (s) {
            case "Client": {
                MessageBus.sendClientPrefixMessage(ColorUtil.textToChatFormatting(this.chatColor) + message);
            }
            case "Everyone": {
                MessageBus.sendServerMessage(message);
            }
            default: {}
        }
    }
}
