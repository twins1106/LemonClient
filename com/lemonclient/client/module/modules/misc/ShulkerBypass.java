//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.client.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "Peek", category = Category.Misc)
public class ShulkerBypass extends Module
{
    BooleanSetting shulker;
    IntegerSetting cmddelay;
    BooleanSetting map;
    BooleanSetting book;
    public static boolean shulkers;
    public static boolean maps;
    public static boolean books;
    public static int delay;
    
    public ShulkerBypass() {
        this.shulker = this.registerBoolean("Shulker", true);
        this.cmddelay = this.registerInteger("Shulker Delay", 0, 0, 20);
        this.map = this.registerBoolean("Map", true);
        this.book = this.registerBoolean("Book", true);
    }
    
    public void onEnable() {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        MessageBus.sendClientDeleteMessage("[ShulkerBypass] To use this throw a shulker on the ground", "Peek", 3);
    }
    
    public void onUpdate() {
        ShulkerBypass.delay = (int)this.cmddelay.getValue();
        ShulkerBypass.shulkers = (boolean)this.shulker.getValue();
        ShulkerBypass.maps = (boolean)this.map.getValue();
        ShulkerBypass.books = (boolean)this.book.getValue();
    }
}
