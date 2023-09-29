//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

@FunctionalInterface
public interface ISwitchRendererProxy<T> extends ISwitchRenderer<T>, IButtonRendererProxy<T>
{
    default Rectangle getOnField(final Context context) {
        return this.getRenderer().getOnField(context);
    }
    
    default Rectangle getOffField(final Context context) {
        return this.getRenderer().getOffField(context);
    }
    
    ISwitchRenderer<T> getRenderer();
}
