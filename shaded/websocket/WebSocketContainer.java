//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.net.*;
import java.io.*;
import java.util.*;

public interface WebSocketContainer
{
    long getDefaultAsyncSendTimeout();
    
    void setAsyncSendTimeout(final long p0);
    
    Session connectToServer(final Object p0, final URI p1) throws DeploymentException, IOException;
    
    Session connectToServer(final Class<?> p0, final URI p1) throws DeploymentException, IOException;
    
    Session connectToServer(final Endpoint p0, final ClientEndpointConfig p1, final URI p2) throws DeploymentException, IOException;
    
    Session connectToServer(final Class<? extends Endpoint> p0, final ClientEndpointConfig p1, final URI p2) throws DeploymentException, IOException;
    
    long getDefaultMaxSessionIdleTimeout();
    
    void setDefaultMaxSessionIdleTimeout(final long p0);
    
    int getDefaultMaxBinaryMessageBufferSize();
    
    void setDefaultMaxBinaryMessageBufferSize(final int p0);
    
    int getDefaultMaxTextMessageBufferSize();
    
    void setDefaultMaxTextMessageBufferSize(final int p0);
    
    Set<Extension> getInstalledExtensions();
}
