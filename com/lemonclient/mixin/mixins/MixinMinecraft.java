//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import net.minecraft.client.*;
import club.minnced.discord.rpc.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.lemonclient.client.module.modules.misc.*;
import com.lemonclient.client.module.*;
import org.spongepowered.asm.mixin.injection.*;
import com.lemonclient.mixin.mixins.accessor.*;
import net.minecraft.crash.*;
import com.lemonclient.api.config.*;

@Mixin({ Minecraft.class })
public class MixinMinecraft
{
    private final DiscordRPC discordRPC;
    @Shadow
    public EntityPlayerSP player;
    @Shadow
    public PlayerControllerMP playerController;
    private boolean handActive;
    private boolean isHittingBlock;
    
    public MixinMinecraft() {
        this.discordRPC = DiscordRPC.INSTANCE;
        this.handActive = false;
        this.isHittingBlock = false;
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("HEAD") })
    public void rightClickMousePre(final CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled((Class)MultiTask.class)) {
            this.isHittingBlock = this.playerController.getIsHittingBlock();
            this.playerController.isHittingBlock = false;
        }
    }
    
    @Inject(method = { "rightClickMouse" }, at = { @At("RETURN") })
    public void rightClickMousePost(final CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled((Class)MultiTask.class) && !this.playerController.getIsHittingBlock()) {
            this.playerController.isHittingBlock = this.isHittingBlock;
        }
    }
    
    @Inject(method = { "sendClickBlockToController" }, at = { @At("HEAD") })
    public void sendClickBlockToControllerPre(final boolean leftClick, final CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled((Class)MultiTask.class)) {
            this.handActive = this.player.isHandActive();
            ((AccessorEntityPlayerSP)this.player).gsSetHandActive(false);
        }
    }
    
    @Inject(method = { "sendClickBlockToController" }, at = { @At("RETURN") })
    public void sendClickBlockToControllerPost(final boolean leftClick, final CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled((Class)MultiTask.class) && !this.player.isHandActive()) {
            ((AccessorEntityPlayerSP)this.player).gsSetHandActive(this.handActive);
        }
    }
    
    @Inject(method = { "crashed" }, at = { @At("HEAD") })
    public void crashed(final CrashReport crash, final CallbackInfo callbackInfo) {
        this.discordRPC.Discord_Shutdown();
        this.discordRPC.Discord_ClearPresence();
        SaveConfig.init();
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    public void shutdown(final CallbackInfo callbackInfo) {
        this.discordRPC.Discord_Shutdown();
        this.discordRPC.Discord_ClearPresence();
        SaveConfig.init();
    }
}
