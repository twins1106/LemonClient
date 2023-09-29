//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.nio.*;
import java.io.*;

public interface Encoder
{
    void init(final EndpointConfig p0);
    
    void destroy();
    
    public interface BinaryStream<T> extends Encoder
    {
        void encode(final T p0, final OutputStream p1) throws EncodeException, IOException;
    }
    
    public interface Binary<T> extends Encoder
    {
        ByteBuffer encode(final T p0) throws EncodeException;
    }
    
    public interface TextStream<T> extends Encoder
    {
        void encode(final T p0, final Writer p1) throws EncodeException, IOException;
    }
    
    public interface Text<T> extends Encoder
    {
        String encode(final T p0) throws EncodeException;
    }
}
