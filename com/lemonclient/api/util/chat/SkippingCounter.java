//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.chat;

import java.util.concurrent.atomic.*;
import java.util.function.*;

public class SkippingCounter
{
    private final AtomicInteger counter;
    private final Predicate<Integer> skip;
    private final int initial;
    
    public SkippingCounter(final int initial, final Predicate<Integer> skip) {
        this.counter = new AtomicInteger(initial);
        this.initial = initial;
        this.skip = skip;
    }
    
    public int get() {
        return this.counter.get();
    }
    
    public int next() {
        int result;
        do {
            result = this.counter.incrementAndGet();
        } while (!this.skip.test(result));
        return result;
    }
    
    public void reset() {
        this.counter.set(this.initial);
    }
}
