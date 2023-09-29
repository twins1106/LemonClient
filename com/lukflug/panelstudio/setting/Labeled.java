//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.*;

public class Labeled implements ILabeled
{
    protected String title;
    protected String description;
    protected IBoolean visible;
    
    public Labeled(final String title, final String description, final IBoolean visible) {
        this.title = title;
        this.description = description;
        this.visible = visible;
    }
    
    public String getDisplayName() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public IBoolean isVisible() {
        return this.visible;
    }
}
