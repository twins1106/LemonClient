//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.manager;

import com.lemonclient.client.manager.managers.*;
import com.lemonclient.client.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.common.*;
import java.util.*;

public class ManagerLoader
{
    private static final List<Manager> managers;
    
    public static void init() {
        register((Manager)ClientEventManager.INSTANCE);
        register((Manager)PlayerPacketManager.INSTANCE);
        register((Manager)TotemPopManager.INSTANCE);
    }
    
    private static void register(final Manager manager) {
        ManagerLoader.managers.add(manager);
        LemonClient.EVENT_BUS.subscribe((Listenable)manager);
        MinecraftForge.EVENT_BUS.register((Object)manager);
    }
    
    static {
        managers = new ArrayList<Manager>();
    }
}
