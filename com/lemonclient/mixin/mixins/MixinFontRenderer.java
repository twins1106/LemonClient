//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.api.util.font.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ FontRenderer.class })
public class MixinFontRenderer
{
    @Redirect(method = { "drawStringWithShadow" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I"))
    public int drawCustomFontStringWithShadow(final FontRenderer fontRenderer, final String text, final float x, final float y, final int color, final boolean dropShadow) {
        final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
        return colorMain.textFont.getValue() ? ((int)FontUtil.drawStringWithShadow(true, text, (int)x, (int)y, new GSColor(color))) : fontRenderer.drawString(text, x, y, color, true);
    }
}
