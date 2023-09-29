//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import com.lemonclient.client.module.modules.misc.*;
import com.lemonclient.client.module.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiNewChat.class })
public abstract class MixinGuiNewChat
{
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawRectBackgroundClean(final int left, final int top, final int right, final int bottom, final int color) {
        final ChatModifier chatModifier = (ChatModifier)ModuleManager.getModule((Class)ChatModifier.class);
        if (!chatModifier.isEnabled() || !(boolean)chatModifier.clearBkg.getValue()) {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }
}
