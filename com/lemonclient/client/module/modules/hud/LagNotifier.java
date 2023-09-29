//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.network.play.server.*;
import java.awt.*;
import com.lemonclient.api.util.render.*;

@Module.Declaration(name = "LagNotifier", category = Category.HUD)
@HUDModule.Declaration(posX = 50, posZ = 50)
public class LagNotifier extends HUDModule
{
    public boolean lag;
    int tmr;
    boolean lagB;
    IntegerSetting delay;
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener;
    
    public LagNotifier() {
        this.delay = this.registerInteger("Hide Delay Ticks", 20, 0, 60);
        this.receiveListener = (Listener<PacketEvent.Receive>)new Listener(event -> {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                this.lagB = true;
                this.tmr = 0;
            }
        }, new Predicate[0]);
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), new LagNotifierList(), 9, 1);
    }
    
    public void onUpdate() {
        ++this.tmr;
        this.lag = (this.tmr < (int)this.delay.getValue());
    }
    
    private class LagNotifierList implements HUDList
    {
        @Override
        public int getSize() {
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            if (LagNotifier.this.lag) {
                return "Rubberband Detected [" + ((int)LagNotifier.this.delay.getValue() - LagNotifier.this.tmr) + "]";
            }
            return "";
        }
        
        @Override
        public Color getItemColor(final int index) {
            return GSColor.red;
        }
        
        @Override
        public boolean sortUp() {
            return false;
        }
        
        @Override
        public boolean sortRight() {
            return false;
        }
    }
}
