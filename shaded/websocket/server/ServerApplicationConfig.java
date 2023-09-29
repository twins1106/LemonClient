//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket.server;

import java.util.*;
import shaded.websocket.*;

public interface ServerApplicationConfig
{
    Set<ServerEndpointConfig> getEndpointConfigs(final Set<Class<? extends Endpoint>> p0);
    
    Set<Class<?>> getAnnotatedEndpointClasses(final Set<Class<?>> p0);
}
