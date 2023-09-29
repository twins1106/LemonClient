//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

public class MultiThreading
{
    private static final AtomicInteger threadCounter;
    private static final ExecutorService SERVICE;
    
    public static void runAsync(final Runnable task) {
        MultiThreading.SERVICE.execute(task);
    }
    
    static {
        threadCounter = new AtomicInteger(0);
        final Thread thread;
        SERVICE = Executors.newCachedThreadPool(task -> {
            new Thread(task, "Lemon Thread " + MultiThreading.threadCounter.getAndIncrement()) {};
            return thread;
        });
    }
}
