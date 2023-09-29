//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.client.entity.*;
import com.lemonclient.api.util.world.*;
import java.util.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import java.awt.*;

@Module.Declaration(name = "Speedometer", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 70)
public class Speedometer extends HUDModule
{
    private static final String MPS = "m/s";
    private static final String KMH = "km/h";
    private static final String MPH = "mph";
    ModeSetting speedUnit;
    BooleanSetting averageSpeed;
    IntegerSetting averageSpeedTicks;
    private final ArrayDeque<Double> speedDeque;
    private String speedString;
    @EventHandler
    private final Listener<TickEvent.ClientTickEvent> listener;
    
    public Speedometer() {
        this.speedUnit = this.registerMode("Unit", (List)Arrays.asList("m/s", "km/h", "mph"), "km/h");
        this.averageSpeed = this.registerBoolean("Average Speed", true);
        this.averageSpeedTicks = this.registerInteger("Average Time", 20, 5, 100);
        this.speedDeque = new ArrayDeque<Double>();
        this.speedString = "";
        this.listener = (Listener<TickEvent.ClientTickEvent>)new Listener(event -> {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }
            final EntityPlayerSP player = Speedometer.mc.player;
            if (player == null) {
                return;
            }
            final String unit = (String)this.speedUnit.getValue();
            double displaySpeed;
            final double speed = displaySpeed = this.calcSpeed(player, unit);
            if (this.averageSpeed.getValue()) {
                if (speed > 0.0 || player.ticksExisted % 4 == 0) {
                    this.speedDeque.add(speed);
                }
                else {
                    this.speedDeque.pollFirst();
                }
                while (!this.speedDeque.isEmpty() && this.speedDeque.size() > (int)this.averageSpeedTicks.getValue()) {
                    this.speedDeque.poll();
                }
                displaySpeed = this.average(this.speedDeque);
            }
            this.speedString = String.format("%.2f", displaySpeed) + ' ' + unit;
        }, new Predicate[0]);
    }
    
    protected void onDisable() {
        this.speedDeque.clear();
        this.speedString = "";
    }
    
    private double calcSpeed(final EntityPlayerSP player, final String unit) {
        final double tps = 1000.0 / TimerUtils.getTickLength();
        final double xDiff = player.posX - player.prevPosX;
        final double zDiff = player.posZ - player.prevPosZ;
        double speed = Math.hypot(xDiff, zDiff) * tps;
        switch (unit) {
            case "km/h": {
                speed *= 3.6;
                break;
            }
            case "mph": {
                speed *= 2.237;
                break;
            }
        }
        return speed;
    }
    
    private double average(final Collection<Double> collection) {
        if (collection.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        int size = 0;
        for (final double element : collection) {
            sum += element;
            ++size;
        }
        return sum / size;
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), new SpeedLabel(), 9, 1);
    }
    
    private class SpeedLabel implements HUDList
    {
        @Override
        public int getSize() {
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            return Speedometer.this.speedString;
        }
        
        @Override
        public Color getItemColor(final int index) {
            return new Color(255, 255, 255);
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
