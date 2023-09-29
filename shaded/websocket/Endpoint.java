//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

public abstract class Endpoint
{
    public abstract void onOpen(final Session p0, final EndpointConfig p1);
    
    public void onClose(final Session session, final CloseReason closeReason) {
    }
    
    public void onError(final Session session, final Throwable thr) {
    }
}
