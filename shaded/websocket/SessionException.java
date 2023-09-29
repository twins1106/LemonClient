//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

public class SessionException extends Exception
{
    private final Session session;
    private static final long serialVersionUID = 12L;
    
    public SessionException(final String message, final Throwable cause, final Session session) {
        super(message, cause);
        this.session = session;
    }
    
    public Session getSession() {
        return this.session;
    }
}
