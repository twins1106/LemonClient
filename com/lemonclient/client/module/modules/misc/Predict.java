//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.api.util.player.*;
import java.util.function.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;

@Module.Declaration(name = "Predict", category = Category.Misc)
public class Predict extends Module
{
    IntegerSetting range;
    IntegerSetting tickPredict;
    BooleanSetting calculateYPredict;
    IntegerSetting startDecrease;
    IntegerSetting exponentStartDecrease;
    IntegerSetting decreaseY;
    IntegerSetting exponentDecreaseY;
    IntegerSetting increaseY;
    IntegerSetting exponentIncreaseY;
    BooleanSetting splitXZ;
    BooleanSetting hideSelf;
    IntegerSetting width;
    BooleanSetting justOnce;
    BooleanSetting debug;
    BooleanSetting showPredictions;
    BooleanSetting manualOutHole;
    BooleanSetting aboveHoleManual;
    BooleanSetting stairPredict;
    IntegerSetting nStair;
    DoubleSetting speedActivationStair;
    ColorSetting mainColor;
    
    public Predict() {
        this.range = this.registerInteger("Range", 10, 0, 100);
        this.tickPredict = this.registerInteger("Tick Predict", 8, 0, 30);
        this.calculateYPredict = this.registerBoolean("Calculate Y Predict", true);
        this.startDecrease = this.registerInteger("Start Decrease", 39, 0, 200, () -> (Boolean)this.calculateYPredict.getValue());
        this.exponentStartDecrease = this.registerInteger("Exponent Start", 2, 1, 5, () -> (Boolean)this.calculateYPredict.getValue());
        this.decreaseY = this.registerInteger("Decrease Y", 2, 1, 5, () -> (Boolean)this.calculateYPredict.getValue());
        this.exponentDecreaseY = this.registerInteger("Exponent Decrease Y", 1, 1, 3, () -> (Boolean)this.calculateYPredict.getValue());
        this.increaseY = this.registerInteger("Increase Y", 3, 1, 5, () -> (Boolean)this.calculateYPredict.getValue());
        this.exponentIncreaseY = this.registerInteger("Exponent Increase Y", 2, 1, 3, () -> (Boolean)this.calculateYPredict.getValue());
        this.splitXZ = this.registerBoolean("Split XZ", true);
        this.hideSelf = this.registerBoolean("Hide Self", false);
        this.width = this.registerInteger("Line Width", 2, 1, 5);
        this.justOnce = this.registerBoolean("Just Once", false);
        this.debug = this.registerBoolean("Debug", false);
        this.showPredictions = this.registerBoolean("Show Predictions", false);
        this.manualOutHole = this.registerBoolean("Manual Out Hole", false);
        this.aboveHoleManual = this.registerBoolean("Above Hole Manual", false, () -> (Boolean)this.manualOutHole.getValue());
        this.stairPredict = this.registerBoolean("Stair Predict", false);
        this.nStair = this.registerInteger("N Stair", 2, 1, 4, () -> (Boolean)this.stairPredict.getValue());
        this.speedActivationStair = this.registerDouble("Speed Activation Stair", 0.3, 0.0, 1.0, () -> (Boolean)this.stairPredict.getValue());
        this.mainColor = this.registerColor("Color");
    }
    
    public void onWorldRender(final RenderEvent event) {
        final PredictUtil.PredictSettings settings = new PredictUtil.PredictSettings((int)this.tickPredict.getValue(), (boolean)this.calculateYPredict.getValue(), (int)this.startDecrease.getValue(), (int)this.exponentStartDecrease.getValue(), (int)this.decreaseY.getValue(), (int)this.exponentDecreaseY.getValue(), (int)this.increaseY.getValue(), (int)this.exponentIncreaseY.getValue(), (boolean)this.splitXZ.getValue(), (int)this.width.getValue(), (boolean)this.debug.getValue(), (boolean)this.showPredictions.getValue(), (boolean)this.manualOutHole.getValue(), (boolean)this.aboveHoleManual.getValue(), (boolean)this.stairPredict.getValue(), (int)this.nStair.getValue(), (double)this.speedActivationStair.getValue());
        final EntityPlayer clonedPlayer;
        Predict.mc.world.playerEntities.stream().filter(entity -> !(boolean)this.hideSelf.getValue() || entity != Predict.mc.player).filter(this::rangeEntityCheck).forEach(entity -> {
            clonedPlayer = PredictUtil.predictPlayer(entity, settings);
            RenderUtil.drawBoundingBox(clonedPlayer.getEntityBoundingBox(), (double)(int)this.width.getValue(), this.mainColor.getColor());
            return;
        });
        if (this.justOnce.getValue()) {
            this.disable();
        }
    }
    
    private boolean rangeEntityCheck(final Entity entity) {
        return entity.getDistance((Entity)Predict.mc.player) <= (int)this.range.getValue();
    }
}
