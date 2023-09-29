//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.client.event.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

@Module.Declaration(name = "NoRender", category = Category.Render)
public class NoRender extends Module
{
    public BooleanSetting armor;
    BooleanSetting fire;
    BooleanSetting blind;
    BooleanSetting nausea;
    public BooleanSetting hurtCam;
    public BooleanSetting noSkylight;
    public BooleanSetting noOverlay;
    BooleanSetting noBossBar;
    public BooleanSetting noCluster;
    IntegerSetting maxNoClusterRender;
    public int currentClusterAmount;
    @EventHandler
    public Listener<RenderBlockOverlayEvent> blockOverlayEventListener;
    @EventHandler
    private final Listener<EntityViewRenderEvent.FogDensity> fogDensityListener;
    @EventHandler
    private final Listener<RenderBlockOverlayEvent> renderBlockOverlayEventListener;
    @EventHandler
    private final Listener<RenderGameOverlayEvent> renderGameOverlayEventListener;
    @EventHandler
    private final Listener<BossbarEvent> bossbarEventListener;
    
    public NoRender() {
        this.armor = this.registerBoolean("Armor", false);
        this.fire = this.registerBoolean("Fire", false);
        this.blind = this.registerBoolean("Blind", false);
        this.nausea = this.registerBoolean("Nausea", false);
        this.hurtCam = this.registerBoolean("HurtCam", false);
        this.noSkylight = this.registerBoolean("Skylight", false);
        this.noOverlay = this.registerBoolean("No Overlay", false);
        this.noBossBar = this.registerBoolean("No Boss Bar", false);
        this.noCluster = this.registerBoolean("No Cluster", false);
        this.maxNoClusterRender = this.registerInteger("No Cluster Max", 5, 1, 25);
        this.currentClusterAmount = 0;
        this.blockOverlayEventListener = (Listener<RenderBlockOverlayEvent>)new Listener(event -> {
            if ((boolean)this.fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
                event.setCanceled(true);
            }
            if ((boolean)this.noOverlay.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER) {
                event.setCanceled(true);
            }
            if ((boolean)this.noOverlay.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK) {
                event.setCanceled(true);
            }
        }, new Predicate[0]);
        this.fogDensityListener = (Listener<EntityViewRenderEvent.FogDensity>)new Listener(event -> {
            if ((boolean)this.noOverlay.getValue() && (event.getState().getMaterial().equals(Material.WATER) || event.getState().getMaterial().equals(Material.LAVA))) {
                event.setDensity(0.0f);
                event.setCanceled(true);
            }
        }, new Predicate[0]);
        this.renderBlockOverlayEventListener = (Listener<RenderBlockOverlayEvent>)new Listener(event -> {
            if (this.noOverlay.getValue()) {
                event.setCanceled(true);
            }
        }, new Predicate[0]);
        this.renderGameOverlayEventListener = (Listener<RenderGameOverlayEvent>)new Listener(event -> {
            if (this.noOverlay.getValue()) {
                if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.HELMET)) {
                    event.setCanceled(true);
                }
                if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.PORTAL)) {
                    event.setCanceled(true);
                }
            }
        }, new Predicate[0]);
        this.bossbarEventListener = (Listener<BossbarEvent>)new Listener(event -> {
            if (this.noBossBar.getValue()) {
                event.cancel();
            }
        }, new Predicate[0]);
    }
    
    public void onUpdate() {
        if ((boolean)this.blind.getValue() && NoRender.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            NoRender.mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if ((boolean)this.nausea.getValue() && NoRender.mc.player.isPotionActive(MobEffects.NAUSEA)) {
            NoRender.mc.player.removePotionEffect(MobEffects.NAUSEA);
        }
    }
    
    public void onRender() {
        this.currentClusterAmount = 0;
    }
    
    public boolean incrementNoClusterRender() {
        ++this.currentClusterAmount;
        return this.currentClusterAmount <= (int)this.maxNoClusterRender.getValue();
    }
    
    public boolean getNoClusterRender() {
        return this.currentClusterAmount <= (int)this.maxNoClusterRender.getValue();
    }
}
