//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

public class EncodeException extends Exception
{
    private final Object object;
    private static final long serialVersionUID = 6L;
    
    public EncodeException(final Object object, final String message) {
        super(message);
        this.object = object;
    }
    
    public EncodeException(final Object object, final String message, final Throwable cause) {
        super(message, cause);
        this.object = object;
    }
    
    public Object getObject() {
        return this.object;
    }
}
