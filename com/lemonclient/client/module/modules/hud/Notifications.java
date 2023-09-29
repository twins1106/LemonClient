//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.api.setting.values.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.util.text.*;
import java.util.*;
import java.awt.*;
import com.lemonclient.client.module.*;

@Module.Declaration(name = "Notifications", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 50)
public class Notifications extends HUDModule
{
    public BooleanSetting sortUp;
    public BooleanSetting sortRight;
    public BooleanSetting disableChat;
    private static final NotificationsList list;
    private static int waitCounter;
    
    public Notifications() {
        this.sortUp = this.registerBoolean("Sort Up", false);
        this.sortRight = this.registerBoolean("Sort Right", false);
        this.disableChat = this.registerBoolean("No Chat Msg", true);
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), Notifications.list, 9, 1);
    }
    
    public void onUpdate() {
        if (Notifications.waitCounter < 500) {
            ++Notifications.waitCounter;
            return;
        }
        Notifications.waitCounter = 0;
        if (Notifications.list.list.size() > 0) {
            Notifications.list.list.remove(0);
        }
    }
    
    public void addMessage(final TextComponentString m) {
        if (Notifications.list.list.size() < 3) {
            Notifications.list.list.remove(m);
            Notifications.list.list.add(m);
        }
        else {
            Notifications.list.list.remove(0);
            Notifications.list.list.remove(m);
            Notifications.list.list.add(m);
        }
    }
    
    static {
        list = new NotificationsList();
    }
    
    private static class NotificationsList implements HUDList
    {
        public List<TextComponentString> list;
        
        private NotificationsList() {
            this.list = new ArrayList<TextComponentString>();
        }
        
        @Override
        public int getSize() {
            return this.list.size();
        }
        
        @Override
        public String getItem(final int index) {
            return this.list.get(index).getText();
        }
        
        @Override
        public Color getItemColor(final int index) {
            return new Color(255, 255, 255);
        }
        
        @Override
        public boolean sortUp() {
            return (boolean)((Notifications)ModuleManager.getModule((Class)Notifications.class)).sortUp.getValue();
        }
        
        @Override
        public boolean sortRight() {
            return (boolean)((Notifications)ModuleManager.getModule((Class)Notifications.class)).sortRight.getValue();
        }
    }
}
