//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.client.*;
import java.awt.*;

@Module.Declaration(name = "ServerInfo", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 0)
public class ServerInfo extends HUDModule
{
    private final IPList list;
    
    public ServerInfo() {
        this.list = new IPList();
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), this.list, 9, 1);
    }
    
    private static class IPList implements HUDList
    {
        @Override
        public int getSize() {
            return 1;
        }
        
        @Override
        public String getItem(final int index) {
            try {
                return "IP: " + ServerInfo.mc.serverName;
            }
            catch (Exception e) {
                return "IP: null";
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
            return true;
        }
    }
}
