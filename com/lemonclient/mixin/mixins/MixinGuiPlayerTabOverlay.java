//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.scoreboard.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay
{
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerNameHead(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable<String> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)this.getPlayerNameGS(networkPlayerInfoIn));
    }
    
    private String getPlayerNameGS(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String displayName = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (SocialManager.isFriend(displayName)) {
            return ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getFriendColor() + displayName;
        }
        if (SocialManager.isEnemy(displayName)) {
            return ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnemyColor() + displayName;
        }
        return displayName;
    }
}
