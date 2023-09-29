//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.network.play.client.*;

@Module.Declaration(name = "AutoRespawn", category = Category.Misc)
public class AutoRespawn extends Module
{
    BooleanSetting coords;
    BooleanSetting respawnMessage;
    IntegerSetting respawnMessageDelay;
    private static String AutoRespawnMessage;
    private boolean isDead;
    private boolean sentRespawnMessage;
    long timeSinceRespawn;
    BlockPos deathpos;
    @EventHandler
    private final Listener<GuiOpenEvent> livingDeathEventListener;
    
    public AutoRespawn() {
        this.coords = this.registerBoolean("Death Coords", false);
        this.respawnMessage = this.registerBoolean("Respawn Message", false);
        this.respawnMessageDelay = this.registerInteger("Msg Delay(ms)", 0, 0, 5000);
        this.sentRespawnMessage = true;
        this.livingDeathEventListener = (Listener<GuiOpenEvent>)new Listener(event -> {
            if (event.getGui() instanceof GuiGameOver) {
                event.setCanceled(true);
                this.isDead = true;
                this.deathpos = PlayerUtil.getPlayerPos();
                this.sentRespawnMessage = true;
                AutoRespawn.mc.player.connection.sendPacket((Packet)new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if (AutoRespawn.mc.player == null) {
            return;
        }
        if (this.isDead && AutoRespawn.mc.player.isEntityAlive()) {
            if (this.coords.getValue()) {
                MessageBus.sendClientPrefixMessage("You died at X:" + this.deathpos.getX() + ", Y:" + this.deathpos.getY() + ", Z:" + this.deathpos.getZ() + ".");
            }
            if (this.respawnMessage.getValue()) {
                this.sentRespawnMessage = false;
                this.timeSinceRespawn = System.currentTimeMillis();
            }
            this.isDead = false;
        }
        if (!this.sentRespawnMessage && System.currentTimeMillis() - this.timeSinceRespawn > (int)this.respawnMessageDelay.getValue()) {
            AutoRespawn.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(AutoRespawn.AutoRespawnMessage));
            this.sentRespawnMessage = true;
        }
    }
    
    public static void setAutoRespawnMessage(final String string) {
        AutoRespawn.AutoRespawnMessage = string;
    }
    
    public static String getAutoRespawnMessages() {
        return AutoRespawn.AutoRespawnMessage;
    }
    
    static {
        AutoRespawn.AutoRespawnMessage = "/kit";
    }
}
