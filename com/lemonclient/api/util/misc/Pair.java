//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

public class Pair<T, S>
{
    T key;
    S value;
    
    public Pair(final T key, final S value) {
        this.key = key;
        this.value = value;
    }
    
    public T getKey() {
        return this.key;
    }
    
    public S getValue() {
        return this.value;
    }
    
    public void setKey(final T key) {
        this.key = key;
    }
    
    public void setValue(final S value) {
        this.value = value;
    }
}
