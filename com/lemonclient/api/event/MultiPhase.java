//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.event;

public interface MultiPhase<T extends LemonClientEvent>
{
    Phase getPhase();
    
    T nextPhase();
}
