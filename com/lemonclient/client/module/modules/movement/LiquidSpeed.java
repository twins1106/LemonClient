//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.*;
import java.util.function.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.client.module.*;

@Module.Declaration(name = "LiquidSpeed", category = Category.Movement)
public class LiquidSpeed extends Module
{
    DoubleSetting timerVal;
    DoubleSetting XZWater;
    DoubleSetting YPWater;
    DoubleSetting YMWater;
    ModeSetting YWaterMotion;
    IntegerSetting magnitudeMinWater;
    DoubleSetting XZLava;
    DoubleSetting YPLava;
    DoubleSetting YMLava;
    ModeSetting YLavaMotion;
    IntegerSetting magnitudeMinLava;
    BooleanSetting groundIgnore;
    private final Timer timer;
    boolean beforeUp;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    
    public LiquidSpeed() {
        this.timerVal = this.registerDouble("Timer Speed", 1.0, 1.0, 2.0);
        this.XZWater = this.registerDouble("XZ Water", 1.0, 1.0, 5.0);
        this.YPWater = this.registerDouble("Y+ Water", 1.0, 1.0, 5.0);
        this.YMWater = this.registerDouble("Y- Water", 1.0, 0.0, 10.0);
        this.YWaterMotion = this.registerMode("Y Water motion", (List)Arrays.asList("None", "Zero", "Bounding", "Min"), "None");
        this.magnitudeMinWater = this.registerInteger("Magnitude Min Water", 0, 0, 6);
        this.XZLava = this.registerDouble("XZ Lava", 1.0, 1.0, 5.0);
        this.YPLava = this.registerDouble("Y+ Lava", 1.0, 1.0, 5.0);
        this.YMLava = this.registerDouble("Y- Lava", 1.0, 0.0, 10.0);
        this.YLavaMotion = this.registerMode("Y Lava motion", (List)Arrays.asList("None", "Zero", "Bounding", "Min"), "None");
        this.magnitudeMinLava = this.registerInteger("Magnitude Min Lava", 0, 0, 6);
        this.groundIgnore = this.registerBoolean("Ground Ignore", true);
        this.timer = new Timer();
        this.beforeUp = true;
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if (LiquidSpeed.mc.player == null || LiquidSpeed.mc.world == null) {
                return;
            }
            final Boolean isMovingUp = LiquidSpeed.mc.gameSettings.keyBindJump.isKeyDown();
            final Boolean isMovingDown = LiquidSpeed.mc.gameSettings.keyBindSneak.isKeyDown();
            Double velX = event.getX();
            final Double memY;
            Double velY = memY = event.getY();
            Double velZ = event.getZ();
            if ((boolean)this.groundIgnore.getValue() || !LiquidSpeed.mc.player.onGround) {
                if (LiquidSpeed.mc.player.isInWater()) {
                    if (!ModuleManager.isModuleEnabled((Class)TickShift.class) && (double)this.timerVal.getValue() != 1.0) {
                        TimerUtils.setTickLength(((Double)this.timerVal.getValue()).floatValue());
                    }
                    velX *= (double)this.XZWater.getValue();
                    velY *= (double)(isMovingUp ? this.YPWater.getValue() : ((Double)this.YMWater.getValue()));
                    velZ *= (double)this.XZWater.getValue();
                    if (!isMovingUp && !isMovingDown) {
                        final String s = (String)this.YWaterMotion.getValue();
                        switch (s) {
                            case "Zero": {
                                velY = 0.0;
                                break;
                            }
                            case "Bounding": {
                                velY = memY;
                                if (this.beforeUp) {
                                    velY *= -1.0;
                                }
                                this.beforeUp = !this.beforeUp;
                                break;
                            }
                            case "Min": {
                                velY = this.getMagnitude((int)this.magnitudeMinWater.getValue());
                                if (this.beforeUp) {
                                    velY *= -1.0;
                                }
                                this.beforeUp = !this.beforeUp;
                                break;
                            }
                        }
                    }
                }
                if (LiquidSpeed.mc.player.isInLava()) {
                    if (!ModuleManager.isModuleEnabled((Class)TickShift.class) && (double)this.timerVal.getValue() != 1.0) {
                        TimerUtils.setTickLength(((Double)this.timerVal.getValue()).floatValue());
                    }
                    velX *= (double)this.XZLava.getValue();
                    velY *= (double)(isMovingUp ? this.YPLava.getValue() : ((Double)this.YMLava.getValue()));
                    velZ *= (double)this.XZLava.getValue();
                    if (!isMovingUp && !isMovingDown) {
                        final String s2 = (String)this.YLavaMotion.getValue();
                        switch (s2) {
                            case "Zero": {
                                velY = 0.0;
                                break;
                            }
                            case "Bounding": {
                                velY = memY;
                                if (this.beforeUp) {
                                    velY *= -1.0;
                                }
                                this.beforeUp = !this.beforeUp;
                                break;
                            }
                            case "Min": {
                                velY = this.getMagnitude((int)this.magnitudeMinLava.getValue());
                                if (this.beforeUp) {
                                    velY *= -1.0;
                                }
                                this.beforeUp = !this.beforeUp;
                                break;
                            }
                        }
                    }
                }
            }
            event.setX((double)velX);
            event.setY((double)velY);
            event.setZ((double)velZ);
        }, new Predicate[0]);
    }
    
    public void onDisable() {
        this.timer.reset();
        TimerUtils.setTickLength(50.0f);
    }
    
    double getMagnitude(final int level) {
        return 1.0 / Math.pow(10.0, level / 2.0);
    }
}
