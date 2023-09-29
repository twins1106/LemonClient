//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.setting.values;

import com.lemonclient.api.setting.*;
import com.lemonclient.client.module.*;

public class StringSetting extends Setting<String>
{
    private String text;
    
    public StringSetting(final String name, final Module parent, final String text) {
        super((Object)text, name, parent);
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
}
