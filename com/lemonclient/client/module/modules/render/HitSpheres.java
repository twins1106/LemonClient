//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.player.social.*;

@Module.Declaration(name = "HitSpheres", category = Category.Render)
public class HitSpheres extends Module
{
    IntegerSetting range;
    DoubleSetting lineWidth;
    IntegerSetting slices;
    IntegerSetting stacks;
    
    public HitSpheres() {
        this.range = this.registerInteger("Range", 100, 10, 260);
        this.lineWidth = this.registerDouble("Line Width", 2.0, 1.0, 5.0);
        this.slices = this.registerInteger("Slices", 20, 10, 30);
        this.stacks = this.registerInteger("Stacks", 15, 10, 20);
    }
    
    public void onWorldRender(final RenderEvent event) {
        final double posX;
        final double posY;
        final double posZ;
        final GSColor color;
        HitSpheres.mc.world.playerEntities.stream().filter(this::isValidPlayer).forEach(entityPlayer -> {
            posX = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * HitSpheres.mc.timer.renderPartialTicks;
            posY = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * HitSpheres.mc.timer.renderPartialTicks;
            posZ = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * HitSpheres.mc.timer.renderPartialTicks;
            color = this.findRenderColor(entityPlayer);
            RenderUtil.drawSphere(posX, posY, posZ, 6.0f, (int)this.slices.getValue(), (int)this.stacks.getValue(), ((Double)this.lineWidth.getValue()).floatValue(), color);
        });
    }
    
    private boolean isValidPlayer(final EntityPlayer entityPlayer) {
        return entityPlayer != HitSpheres.mc.player && entityPlayer.getDistance((Entity)HitSpheres.mc.player) <= (int)this.range.getValue();
    }
    
    private GSColor findRenderColor(final EntityPlayer entityPlayer) {
        final String name = entityPlayer.getName();
        final double distance = HitSpheres.mc.player.getDistance((Entity)entityPlayer);
        final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
        if (SocialManager.isFriend(name)) {
            return colorMain.getFriendGSColor();
        }
        if (distance >= 8.0) {
            return new GSColor(0, 255, 0, 255);
        }
        if (distance < 8.0) {
            return new GSColor(255, (int)(HitSpheres.mc.player.getDistance((Entity)entityPlayer) * 255.0f / 150.0f), 0, 255);
        }
        if (SocialManager.isEnemy(name)) {
            return colorMain.getEnemyGSColor();
        }
        return new GSColor(1, 1, 1, 255);
    }
}
