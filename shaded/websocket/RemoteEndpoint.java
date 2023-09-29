//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.nio.*;
import java.io.*;
import java.util.concurrent.*;

public interface RemoteEndpoint
{
    void setBatchingAllowed(final boolean p0) throws IOException;
    
    boolean getBatchingAllowed();
    
    void flushBatch() throws IOException;
    
    void sendPing(final ByteBuffer p0) throws IOException, IllegalArgumentException;
    
    void sendPong(final ByteBuffer p0) throws IOException, IllegalArgumentException;
    
    public interface Basic extends RemoteEndpoint
    {
        void sendText(final String p0) throws IOException;
        
        void sendBinary(final ByteBuffer p0) throws IOException;
        
        void sendText(final String p0, final boolean p1) throws IOException;
        
        void sendBinary(final ByteBuffer p0, final boolean p1) throws IOException;
        
        OutputStream getSendStream() throws IOException;
        
        Writer getSendWriter() throws IOException;
        
        void sendObject(final Object p0) throws IOException, EncodeException;
    }
    
    public interface Async extends RemoteEndpoint
    {
        long getSendTimeout();
        
        void setSendTimeout(final long p0);
        
        void sendText(final String p0, final SendHandler p1);
        
        Future<Void> sendText(final String p0);
        
        Future<Void> sendBinary(final ByteBuffer p0);
        
        void sendBinary(final ByteBuffer p0, final SendHandler p1);
        
        Future<Void> sendObject(final Object p0);
        
        void sendObject(final Object p0, final SendHandler p1);
    }
}
