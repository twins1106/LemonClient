//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package me.zero.alpine.bus.type;

import me.zero.alpine.bus.*;

public interface AttachableEventBus extends EventBus
{
    void attach(final EventBus p0);
    
    void detach(final EventBus p0);
}
