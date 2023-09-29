//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.event.world.*;
import java.util.concurrent.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.client.renderer.*;
import com.lemonclient.api.util.render.*;
import java.text.*;
import java.util.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "LogoutSpots", category = Category.Render)
public class LogoutSpots extends Module
{
    IntegerSetting range;
    BooleanSetting disconncetMsg;
    BooleanSetting reconncetMsg;
    BooleanSetting nameTag;
    IntegerSetting lineWidth;
    ModeSetting renderMode;
    ColorSetting color;
    Map<Entity, String> loggedPlayers;
    Set<EntityPlayer> worldPlayers;
    Timer timer;
    Timer timer2;
    @EventHandler
    private final Listener<PlayerJoinEvent> playerJoinEventListener;
    @EventHandler
    private final Listener<PlayerLeaveEvent> playerLeaveEventListener;
    @EventHandler
    private final Listener<WorldEvent.Unload> unloadListener;
    @EventHandler
    private final Listener<WorldEvent.Load> loadListener;
    
    public LogoutSpots() {
        this.range = this.registerInteger("Range", 100, 10, 260);
        this.disconncetMsg = this.registerBoolean("Disconncet Msgs", true);
        this.reconncetMsg = this.registerBoolean("Reconncet Msgs", true);
        this.nameTag = this.registerBoolean("Nametag", true);
        this.lineWidth = this.registerInteger("Width", 1, 1, 10);
        this.renderMode = this.registerMode("Render", (List)Arrays.asList("Both", "Outline", "Fill", "None"), "Both");
        this.color = this.registerColor("Color", new GSColor(255, 0, 0, 255));
        this.loggedPlayers = new ConcurrentHashMap<Entity, String>();
        this.worldPlayers = (Set<EntityPlayer>)ConcurrentHashMap.newKeySet();
        this.timer = new Timer();
        this.timer2 = new Timer();
        this.playerJoinEventListener = (Listener<PlayerJoinEvent>)new Listener(event -> {
            if (LogoutSpots.mc.world != null) {
                this.loggedPlayers.keySet().removeIf(entity -> {
                    if (entity.getName().equalsIgnoreCase(event.getName())) {
                        if ((boolean)this.reconncetMsg.getValue() && this.timer2.getTimePassed() / 50L >= 5L) {
                            MessageBus.sendClientPrefixMessage(event.getName() + " reconnected.");
                            this.timer2.reset();
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            }
        }, new Predicate[0]);
        this.playerLeaveEventListener = (Listener<PlayerLeaveEvent>)new Listener(event -> {
            if (LogoutSpots.mc.world != null) {
                String date;
                String location;
                this.worldPlayers.removeIf(entity -> {
                    if (entity.getName().equalsIgnoreCase(event.getName())) {
                        date = new SimpleDateFormat("k:mm").format(new Date());
                        this.loggedPlayers.put((Entity)entity, date);
                        if ((boolean)this.disconncetMsg.getValue() && this.timer.getTimePassed() / 50L >= 5L) {
                            location = "(" + (int)entity.posX + "," + (int)entity.posY + "," + (int)entity.posZ + ")";
                            MessageBus.sendClientPrefixMessage(event.getName() + " disconnected at " + location + ".");
                            this.timer.reset();
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            }
        }, new Predicate[0]);
        this.unloadListener = (Listener<WorldEvent.Unload>)new Listener(event -> {
            this.worldPlayers.clear();
            if (LogoutSpots.mc.player == null || LogoutSpots.mc.world == null) {
                this.loggedPlayers.clear();
            }
        }, new Predicate[0]);
        this.loadListener = (Listener<WorldEvent.Load>)new Listener(event -> {
            this.worldPlayers.clear();
            if (LogoutSpots.mc.player == null || LogoutSpots.mc.world == null) {
                this.loggedPlayers.clear();
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        LogoutSpots.mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != LogoutSpots.mc.player).filter(entityPlayer -> entityPlayer.getDistance((Entity)LogoutSpots.mc.player) <= (int)this.range.getValue()).forEach(entityPlayer -> this.worldPlayers.add(entityPlayer));
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (LogoutSpots.mc.player != null && LogoutSpots.mc.world != null) {
            this.loggedPlayers.forEach(this::startFunction);
        }
    }
    
    public void onEnable() {
        this.loggedPlayers.clear();
        this.worldPlayers = (Set<EntityPlayer>)ConcurrentHashMap.newKeySet();
    }
    
    public void onDisable() {
        this.worldPlayers.clear();
    }
    
    private void startFunction(final Entity entity, final String string) {
        if (entity.getDistance((Entity)LogoutSpots.mc.player) > (int)this.range.getValue()) {
            return;
        }
        final int posX = (int)entity.posX;
        final int posY = (int)entity.posY;
        final int posZ = (int)entity.posZ;
        final String[] nameTagMessage = { entity.getName() + " (" + string + ")", "(" + posX + "," + posY + "," + posZ + ")" };
        GlStateManager.pushMatrix();
        if (this.nameTag.getValue()) {
            RenderUtil.drawNametag(entity, nameTagMessage, this.color.getValue(), 0);
        }
        final String s = (String)this.renderMode.getValue();
        switch (s) {
            case "Both": {
                RenderUtil.drawBoundingBox(entity.getRenderBoundingBox(), (double)(int)this.lineWidth.getValue(), this.color.getValue());
                RenderUtil.drawBox(entity.getRenderBoundingBox(), true, -0.4, new GSColor(this.color.getValue(), 50), 63);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(entity.getRenderBoundingBox(), (double)(int)this.lineWidth.getValue(), this.color.getValue());
                break;
            }
            case "Fill": {
                RenderUtil.drawBox(entity.getRenderBoundingBox(), true, -0.4, new GSColor(this.color.getValue(), 50), 63);
                break;
            }
        }
        GlStateManager.popMatrix();
    }
}
