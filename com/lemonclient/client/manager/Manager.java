//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.manager;

import me.zero.alpine.listener.*;
import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.profiler.*;

public interface Manager extends Listenable
{
    default Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    default EntityPlayerSP getPlayer() {
        return this.getMinecraft().player;
    }
    
    default WorldClient getWorld() {
        return this.getMinecraft().world;
    }
    
    default Profiler getProfiler() {
        return this.getMinecraft().profiler;
    }
}
