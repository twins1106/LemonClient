//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.nio.*;

public class DecodeException extends Exception
{
    private final ByteBuffer bb;
    private final String encodedString;
    private static final long serialVersionUID = 6L;
    
    public DecodeException(final ByteBuffer bb, final String message, final Throwable cause) {
        super(message, cause);
        this.encodedString = null;
        this.bb = bb;
    }
    
    public DecodeException(final String encodedString, final String message, final Throwable cause) {
        super(message, cause);
        this.encodedString = encodedString;
        this.bb = null;
    }
    
    public DecodeException(final ByteBuffer bb, final String message) {
        super(message);
        this.encodedString = null;
        this.bb = bb;
    }
    
    public DecodeException(final String encodedString, final String message) {
        super(message);
        this.encodedString = encodedString;
        this.bb = null;
    }
    
    public ByteBuffer getBytes() {
        return this.bb;
    }
    
    public String getText() {
        return this.encodedString;
    }
}
