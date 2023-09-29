//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;

public class KeybindComponent extends FocusableComponent
{
    protected IKeybindSetting keybind;
    protected IButtonRenderer<String> renderer;
    
    public KeybindComponent(final IKeybindSetting keybind, final IButtonRenderer<String> renderer) {
        super((ILabeled)keybind);
        this.keybind = keybind;
        this.renderer = renderer;
    }
    
    public void render(final Context context) {
        super.render(context);
        this.renderer.renderButton(context, this.getTitle(), this.hasFocus(context), (Object)this.keybind.getKeyName());
    }
    
    public void handleKey(final Context context, final int scancode) {
        super.handleKey(context, scancode);
        if (this.hasFocus(context)) {
            this.keybind.setKey(this.transformKey(scancode));
            this.releaseFocus();
        }
    }
    
    public void exit() {
        super.exit();
        this.releaseFocus();
    }
    
    protected int getHeight() {
        return this.renderer.getDefaultHeight();
    }
    
    protected int transformKey(final int scancode) {
        return scancode;
    }
}
