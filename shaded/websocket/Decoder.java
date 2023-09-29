//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.io.*;
import java.nio.*;

public interface Decoder
{
    void init(final EndpointConfig p0);
    
    void destroy();
    
    public interface TextStream<T> extends Decoder
    {
        T decode(final Reader p0) throws DecodeException, IOException;
    }
    
    public interface Text<T> extends Decoder
    {
        T decode(final String p0) throws DecodeException;
        
        boolean willDecode(final String p0);
    }
    
    public interface BinaryStream<T> extends Decoder
    {
        T decode(final InputStream p0) throws DecodeException, IOException;
    }
    
    public interface Binary<T> extends Decoder
    {
        T decode(final ByteBuffer p0) throws DecodeException;
        
        boolean willDecode(final ByteBuffer p0);
    }
}
