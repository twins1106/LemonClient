//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.world.*;

public class Wrapper
{
    public static EntityPlayerSP getPlayer() {
        final EntityPlayerSP player = Minecraft.getMinecraft().player;
        return player;
    }
    
    public static Minecraft getMinecraft() {
        final Minecraft minecraft = Minecraft.getMinecraft();
        return minecraft;
    }
    
    public static World getWorld() {
        final World world = (World)Minecraft.getMinecraft().world;
        return world;
    }
}
