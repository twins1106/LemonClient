//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.client.*;
import java.awt.*;

@Module.Declaration(name = "Frames", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 0)
public class Frames extends HUDModule
{
    int frames;
    private final FrameList list;
    
    public Frames() {
        this.list = new FrameList();
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), this.list, 9, 1);
    }
    
    private class FrameList implements HUDList
    {
        @Override
        public int getSize() {
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            try {
                return "FPS " + Minecraft.getDebugFPS();
            }
            catch (Exception e) {
                return "FPS 0";
            }
        }
        
        @Override
        public Color getItemColor(final int index) {
            return Color.WHITE;
        }
        
        @Override
        public boolean sortUp() {
            return true;
        }
        
        @Override
        public boolean sortRight() {
            return false;
        }
    }
}
