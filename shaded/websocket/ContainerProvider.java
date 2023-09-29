//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.util.*;

public abstract class ContainerProvider
{
    public static WebSocketContainer getWebSocketContainer() {
        WebSocketContainer wsc = null;
        for (final ContainerProvider impl : ServiceLoader.load(ContainerProvider.class)) {
            wsc = impl.getContainer();
            if (wsc != null) {
                return wsc;
            }
        }
        if (wsc == null) {
            throw new RuntimeException("Could not find an implementation class.");
        }
        throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
    }
    
    protected abstract WebSocketContainer getContainer();
}
