//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.chat;

import me.zero.alpine.listener.*;
import java.util.*;

public class SubscriberImpl implements Subscriber
{
    protected final List<Listener<?>> listeners;
    
    public SubscriberImpl() {
        this.listeners = new ArrayList<Listener<?>>();
    }
    
    public Collection<Listener<?>> getListeners() {
        return this.listeners;
    }
}
