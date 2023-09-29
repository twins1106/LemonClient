//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.util.*;

public interface HandshakeResponse
{
    public static final String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";
    
    Map<String, List<String>> getHeaders();
}
