//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.render.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import java.awt.*;

@Module.Declaration(name = "Watermark", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 0)
public class Watermark extends HUDModule
{
    ColorSetting color;
    
    public Watermark() {
        this.color = this.registerColor("Color", new GSColor(255, 0, 0, 255));
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), new WatermarkList(), 9, 1);
    }
    
    private class WatermarkList implements HUDList
    {
        @Override
        public int getSize() {
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            return "LemonClient v0.0.6";
        }
        
        @Override
        public Color getItemColor(final int index) {
            return (Color)Watermark.this.color.getValue();
        }
        
        @Override
        public boolean sortUp() {
            return false;
        }
        
        @Override
        public boolean sortRight() {
            return false;
        }
    }
}
