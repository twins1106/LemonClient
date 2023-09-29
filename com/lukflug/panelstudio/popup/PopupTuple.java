//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.popup;

import com.lukflug.panelstudio.component.*;

public class PopupTuple
{
    public final IPopupPositioner popupPos;
    public final boolean dynamicPopup;
    public final IScrollSize popupSize;
    
    public PopupTuple(final IPopupPositioner popupPos, final boolean dynamicPopup, final IScrollSize popupSize) {
        this.popupPos = popupPos;
        this.dynamicPopup = dynamicPopup;
        this.popupSize = popupSize;
    }
}
