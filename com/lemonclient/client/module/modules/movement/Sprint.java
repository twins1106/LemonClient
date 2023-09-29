//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.entity.*;

@Module.Declaration(name = "Sprint", category = Category.Movement)
public class Sprint extends Module
{
    private BooleanSetting multiDirection;
    
    public Sprint() {
        this.multiDirection = this.registerBoolean("Multi Direction", true);
    }
    
    public void onUpdate() {
        final EntityPlayerSP player = Sprint.mc.player;
        if (player != null) {
            player.setSprinting(this.shouldSprint(player));
        }
    }
    
    public boolean shouldSprint(final EntityPlayerSP player) {
        return !Sprint.mc.gameSettings.keyBindSneak.isKeyDown() && player.getFoodStats().getFoodLevel() > 6 && !player.isElytraFlying() && !Sprint.mc.player.capabilities.isFlying && this.checkMovementInput(player);
    }
    
    private boolean checkMovementInput(final EntityPlayerSP player) {
        return this.multiDirection.getValue() ? (player.moveForward != 0.0f || player.moveStrafing != 0.0f) : (player.moveForward > 0.0f);
    }
}
