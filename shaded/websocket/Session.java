//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;

public interface Session extends Closeable
{
    WebSocketContainer getContainer();
    
    void addMessageHandler(final MessageHandler p0) throws IllegalStateException;
    
     <T> void addMessageHandler(final Class<T> p0, final MessageHandler.Whole<T> p1);
    
     <T> void addMessageHandler(final Class<T> p0, final MessageHandler.Partial<T> p1);
    
    Set<MessageHandler> getMessageHandlers();
    
    void removeMessageHandler(final MessageHandler p0);
    
    String getProtocolVersion();
    
    String getNegotiatedSubprotocol();
    
    List<Extension> getNegotiatedExtensions();
    
    boolean isSecure();
    
    boolean isOpen();
    
    long getMaxIdleTimeout();
    
    void setMaxIdleTimeout(final long p0);
    
    void setMaxBinaryMessageBufferSize(final int p0);
    
    int getMaxBinaryMessageBufferSize();
    
    void setMaxTextMessageBufferSize(final int p0);
    
    int getMaxTextMessageBufferSize();
    
    RemoteEndpoint.Async getAsyncRemote();
    
    RemoteEndpoint.Basic getBasicRemote();
    
    String getId();
    
    void close() throws IOException;
    
    void close(final CloseReason p0) throws IOException;
    
    URI getRequestURI();
    
    Map<String, List<String>> getRequestParameterMap();
    
    String getQueryString();
    
    Map<String, String> getPathParameters();
    
    Map<String, Object> getUserProperties();
    
    Principal getUserPrincipal();
    
    Set<Session> getOpenSessions();
}
