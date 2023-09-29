//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.render.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.client.*;
import java.awt.*;

@Module.Declaration(name = "Welcomer", category = Category.HUD)
@HUDModule.Declaration(posX = 450, posZ = 0)
public class Welcomer extends HUDModule
{
    ColorSetting color;
    
    public Welcomer() {
        this.color = this.registerColor("Color", new GSColor(255, 0, 0, 255));
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), new WelcomerList(), 9, 1);
    }
    
    private class WelcomerList implements HUDList
    {
        @Override
        public int getSize() {
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            return "Hello " + Welcomer.mc.player.getName() + " :^)";
        }
        
        @Override
        public Color getItemColor(final int index) {
            return (Color)Welcomer.this.color.getValue();
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
