//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.api.setting.values.*;
import java.util.*;
import com.lemonclient.api.event.events.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "Tracers", category = Category.Render)
public class Tracers extends Module
{
    IntegerSetting renderDistance;
    ModeSetting pointsTo;
    BooleanSetting colorType;
    ColorSetting nearColor;
    ColorSetting midColor;
    ColorSetting farColor;
    GSColor tracerColor;
    
    public Tracers() {
        this.renderDistance = this.registerInteger("Distance", 100, 10, 260);
        this.pointsTo = this.registerMode("Draw To", (List)Arrays.asList("Head", "Feet"), "Feet");
        this.colorType = this.registerBoolean("Color Sync", true);
        this.nearColor = this.registerColor("Near Color", new GSColor(255, 0, 0, 255));
        this.midColor = this.registerColor("Middle Color", new GSColor(255, 255, 0, 255));
        this.farColor = this.registerColor("Far Color", new GSColor(0, 255, 0, 255));
    }
    
    public void onWorldRender(final RenderEvent event) {
        final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
        final ColorMain colorMain2;
        Tracers.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).filter(e -> e != Tracers.mc.player).forEach(e -> {
            if (Tracers.mc.player.getDistance(e) <= (int)this.renderDistance.getValue()) {
                if (SocialManager.isFriend(e.getName())) {
                    this.tracerColor = colorMain2.getFriendGSColor();
                }
                else if (SocialManager.isEnemy(e.getName())) {
                    this.tracerColor = colorMain2.getEnemyGSColor();
                }
                else {
                    if (Tracers.mc.player.getDistance(e) < 20.0f) {
                        this.tracerColor = this.nearColor.getValue();
                    }
                    if (Tracers.mc.player.getDistance(e) >= 20.0f && Tracers.mc.player.getDistance(e) < 50.0f) {
                        this.tracerColor = this.midColor.getValue();
                    }
                    if (Tracers.mc.player.getDistance(e) >= 50.0f) {
                        this.tracerColor = this.farColor.getValue();
                    }
                    if (this.colorType.getValue()) {
                        this.tracerColor = this.getDistanceColor((int)Tracers.mc.player.getDistance(e));
                    }
                }
                this.drawLineToEntityPlayer(e, this.tracerColor);
            }
        });
    }
    
    public void drawLineToEntityPlayer(final Entity e, final GSColor color) {
        final double[] xyz = interpolate(e);
        this.drawLine1(xyz[0], xyz[1], xyz[2], e.height, color);
    }
    
    public static double[] interpolate(final Entity entity) {
        final double posX = interpolate(entity.posX, entity.lastTickPosX);
        final double posY = interpolate(entity.posY, entity.lastTickPosY);
        final double posZ = interpolate(entity.posZ, entity.lastTickPosZ);
        return new double[] { posX, posY, posZ };
    }
    
    public static double interpolate(final double now, final double then) {
        return then + (now - then) * Tracers.mc.getRenderPartialTicks();
    }
    
    public void drawLine1(final double posx, final double posy, final double posz, final double up, final GSColor color) {
        final Vec3d eyes = ActiveRenderInfo.getCameraPosition().add(Tracers.mc.getRenderManager().viewerPosX, Tracers.mc.getRenderManager().viewerPosY, Tracers.mc.getRenderManager().viewerPosZ);
        RenderUtil.prepare();
        if (((String)this.pointsTo.getValue()).equalsIgnoreCase("Head")) {
            RenderUtil.drawLine(eyes.x, eyes.y, eyes.z, posx, posy + up, posz, color);
        }
        else {
            RenderUtil.drawLine(eyes.x, eyes.y, eyes.z, posx, posy, posz, color);
        }
        RenderUtil.release();
    }
    
    private GSColor getDistanceColor(int distance) {
        if (distance > 50) {
            distance = 50;
        }
        final int red = (int)(255.0 - distance * 5.1);
        final int green = 255 - red;
        return new GSColor(red, green, 0, 255);
    }
}
