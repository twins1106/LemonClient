//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

public interface MessageHandler
{
    public interface Partial<T> extends MessageHandler
    {
        void onMessage(final T p0, final boolean p1);
    }
    
    public interface Whole<T> extends MessageHandler
    {
        void onMessage(final T p0);
    }
}
