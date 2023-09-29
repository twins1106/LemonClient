//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.concurrent.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.event.entity.living.*;
import java.util.function.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import java.util.*;

@Module.Declaration(name = "AutoEz", category = Category.qwq)
public class AutoEz extends Module
{
    IntegerSetting delay;
    public static AutoEz INSTANCE;
    static List<String> AutoEzMessages;
    private ConcurrentHashMap targetedPlayers;
    int index;
    int waited;
    @EventHandler
    private final Listener<PacketEvent.Send> sendListener;
    @EventHandler
    private final Listener<LivingDeathEvent> livingDeathEventListener;
    
    public AutoEz() {
        this.delay = this.registerInteger("Delay", 0, 0, 20);
        this.targetedPlayers = null;
        this.index = -1;
        this.sendListener = (Listener<PacketEvent.Send>)new Listener(event -> {
            if (AutoEz.mc.player != null) {
                if (this.targetedPlayers == null) {
                    this.targetedPlayers = new ConcurrentHashMap();
                }
                if (this.waited > 0) {
                    return;
                }
                if (event.getPacket() instanceof CPacketUseEntity) {
                    final CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
                    if (cPacketUseEntity.getAction().equals((Object)CPacketUseEntity.Action.ATTACK)) {
                        final Entity targetEntity = cPacketUseEntity.getEntityFromWorld((World)AutoEz.mc.world);
                        if (targetEntity instanceof EntityPlayer) {
                            this.addTargetedPlayer(targetEntity.getName());
                        }
                    }
                }
            }
        }, new Predicate[0]);
        this.livingDeathEventListener = (Listener<LivingDeathEvent>)new Listener(event -> {
            if (AutoEz.mc.player != null) {
                if (this.targetedPlayers == null) {
                    this.targetedPlayers = new ConcurrentHashMap();
                }
                if (this.waited > 0) {
                    return;
                }
                final EntityLivingBase entity = event.getEntityLiving();
                if (entity != null && entity instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)entity;
                    if (player.getHealth() <= 0.0f) {
                        final String name = player.getName();
                        if (this.shouldAnnounce(name)) {
                            this.doAnnounce(name);
                        }
                    }
                }
            }
        }, new Predicate[0]);
        AutoEz.INSTANCE = this;
    }
    
    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap();
    }
    
    public void onDisable() {
        this.targetedPlayers = null;
    }
    
    public void onUpdate() {
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }
        --this.waited;
        if (this.waited > 0) {
            return;
        }
        for (final Entity entity : AutoEz.mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)entity;
                if (player.getHealth() > 0.0f) {
                    continue;
                }
                final String name = player.getName();
                if (this.shouldAnnounce(name)) {
                    this.doAnnounce(name);
                    break;
                }
                continue;
            }
        }
        this.targetedPlayers.forEach((namex, timeout) -> {
            if (timeout <= 0) {
                this.targetedPlayers.remove(namex);
            }
            else {
                this.targetedPlayers.put(namex, timeout - 1);
            }
        });
    }
    
    private boolean shouldAnnounce(final String name) {
        return this.targetedPlayers.containsKey(name);
    }
    
    private void doAnnounce(final String name) {
        this.targetedPlayers.remove(name);
        if (this.index >= AutoEz.AutoEzMessages.size() - 1) {
            this.index = -1;
        }
        ++this.index;
        String message;
        if (AutoEz.AutoEzMessages.size() > 0) {
            message = AutoEz.AutoEzMessages.get(this.index);
        }
        else {
            message = ">Ez";
        }
        String messageSanitized = message.replace("{name}", name);
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }
        MessageBus.sendServerMessage(messageSanitized);
        this.waited = (int)this.delay.getValue();
    }
    
    public void addTargetedPlayer(final String name) {
        if (!Objects.equals(name, AutoEz.mc.player.getName())) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }
            this.targetedPlayers.put(name, 20);
        }
    }
    
    public static void addAutoEzMessage(final String s) {
        AutoEz.AutoEzMessages.add(s);
    }
    
    public static List<String> getAutoEzMessages() {
        return AutoEz.AutoEzMessages;
    }
    
    static {
        AutoEz.AutoEzMessages = new ArrayList<String>();
    }
}
