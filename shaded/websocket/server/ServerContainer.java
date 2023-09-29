//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket.server;

import shaded.websocket.*;

public interface ServerContainer extends WebSocketContainer
{
    void addEndpoint(final Class<?> p0) throws DeploymentException;
    
    void addEndpoint(final ServerEndpointConfig p0) throws DeploymentException;
}
