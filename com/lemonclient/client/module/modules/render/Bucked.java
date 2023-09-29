//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.api.setting.values.*;
import java.util.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.world.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;

@Module.Declaration(name = "Bucked", category = Category.Render)
public class Bucked extends Module
{
    IntegerSetting range;
    BooleanSetting self;
    BooleanSetting friend;
    BooleanSetting enemiesOnly;
    ModeSetting heightMode;
    ModeSetting renderMode;
    IntegerSetting width;
    ColorSetting color;
    
    public Bucked() {
        this.range = this.registerInteger("Range", 100, 10, 260);
        this.self = this.registerBoolean("Self", false);
        this.friend = this.registerBoolean("Friend", true);
        this.enemiesOnly = this.registerBoolean("Only Enemies", false);
        this.heightMode = this.registerMode("Height", (List)Arrays.asList("Single", "Double"), "Single");
        this.renderMode = this.registerMode("Render", (List)Arrays.asList("Outline", "Fill", "Both"), "Both");
        this.width = this.registerInteger("Line Width", 2, 1, 5);
        this.color = this.registerColor("Color", new GSColor(0, 255, 0, 255));
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (Bucked.mc.player == null || Bucked.mc.world == null) {
            return;
        }
        final BlockPos blockPos;
        Bucked.mc.world.playerEntities.stream().filter(this::isValidTarget).forEach(entityPlayer -> {
            blockPos = new BlockPos(this.roundValueToCenter(entityPlayer.posX), this.roundValueToCenter(entityPlayer.posY), this.roundValueToCenter(entityPlayer.posZ));
            if (!this.isSurrounded(blockPos)) {
                this.renderESP(blockPos, this.findGSColor(entityPlayer));
            }
        });
    }
    
    private boolean isValidTarget(final EntityPlayer entityPlayer) {
        return entityPlayer != null && !entityPlayer.isDead && entityPlayer.getHealth() > 0.0f && (!(boolean)this.enemiesOnly.getValue() || SocialManager.isEnemy(entityPlayer.getName())) && entityPlayer.getDistance((Entity)Bucked.mc.player) <= (int)this.range.getValue() && ((boolean)this.self.getValue() || entityPlayer != Bucked.mc.player) && ((boolean)this.friend.getValue() || !SocialManager.isFriend(entityPlayer.getName()));
    }
    
    private boolean isSurrounded(final BlockPos blockPos) {
        return HoleUtil.isHole(blockPos, true, false, false).getType() != HoleUtil.HoleType.NONE;
    }
    
    private void renderESP(final BlockPos blockPos, final GSColor color) {
        final int upValue = ((String)this.heightMode.getValue()).equalsIgnoreCase("Double") ? 2 : 1;
        final GSColor gsColor1 = new GSColor(color, 255);
        final GSColor gsColor2 = new GSColor(color, 50);
        final String s = (String)this.renderMode.getValue();
        switch (s) {
            case "Both": {
                RenderUtil.drawBox(blockPos, (double)upValue, gsColor2, 63);
                RenderUtil.drawBoundingBox(blockPos, (double)upValue, (float)(int)this.width.getValue(), gsColor1);
                break;
            }
            case "Outline": {
                RenderUtil.drawBoundingBox(blockPos, (double)upValue, (float)(int)this.width.getValue(), gsColor1);
                break;
            }
            case "Fill": {
                RenderUtil.drawBox(blockPos, (double)upValue, gsColor2, 63);
                break;
            }
        }
    }
    
    private GSColor findGSColor(final EntityPlayer entityPlayer) {
        if (SocialManager.isFriend(entityPlayer.getName())) {
            return ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getFriendGSColor();
        }
        if (SocialManager.isEnemy(entityPlayer.getName())) {
            return ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnemyGSColor();
        }
        return this.color.getValue();
    }
    
    private double roundValueToCenter(final double inputVal) {
        double roundVal = (double)Math.round(inputVal);
        if (roundVal > inputVal) {
            roundVal -= 0.5;
        }
        else if (roundVal <= inputVal) {
            roundVal += 0.5;
        }
        return roundVal;
    }
}
