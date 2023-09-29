//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket.server;

import java.util.*;
import java.security.*;
import java.net.*;

public interface HandshakeRequest
{
    public static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
    public static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
    public static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
    public static final String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";
    
    Map<String, List<String>> getHeaders();
    
    Principal getUserPrincipal();
    
    URI getRequestURI();
    
    boolean isUserInRole(final String p0);
    
    Object getHttpSession();
    
    Map<String, List<String>> getParameterMap();
    
    String getQueryString();
}
