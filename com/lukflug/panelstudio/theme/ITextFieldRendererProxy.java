//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.base.*;
import java.awt.*;

@FunctionalInterface
public interface ITextFieldRendererProxy extends ITextFieldRenderer
{
    default int renderTextField(final Context context, final String title, final boolean focus, final String content, final int position, final int select, final int boxPosition, final boolean insertMode) {
        return this.getRenderer().renderTextField(context, title, focus, content, position, select, boxPosition, insertMode);
    }
    
    default int getDefaultHeight() {
        return this.getRenderer().getDefaultHeight();
    }
    
    default Rectangle getTextArea(final Context context, final String title) {
        return this.getRenderer().getTextArea(context, title);
    }
    
    default int transformToCharPos(final Context context, final String title, final String content, final int boxPosition) {
        return this.getRenderer().transformToCharPos(context, title, content, boxPosition);
    }
    
    ITextFieldRenderer getRenderer();
}
