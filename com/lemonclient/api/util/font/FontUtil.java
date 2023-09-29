//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.font;

import net.minecraft.client.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.client.*;

public class FontUtil
{
    private static final Minecraft mc;
    
    public static float drawStringWithShadow(final boolean customFont, final String text, final int x, final int y, final GSColor color) {
        if (customFont) {
            return LemonClient.INSTANCE.cFontRenderer.drawStringWithShadow(text, (double)x, (double)y, color);
        }
        return (float)FontUtil.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color.getRGB());
    }
    
    public static int getStringWidth(final boolean customFont, final String string) {
        if (customFont) {
            return LemonClient.INSTANCE.cFontRenderer.getStringWidth(string);
        }
        return FontUtil.mc.fontRenderer.getStringWidth(string);
    }
    
    public static int getFontHeight(final boolean customFont) {
        if (customFont) {
            return LemonClient.INSTANCE.cFontRenderer.getHeight();
        }
        return FontUtil.mc.fontRenderer.FONT_HEIGHT;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
