//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

public final class SendResult
{
    private final Throwable exception;
    private final boolean isOK;
    
    public SendResult(final Throwable exception) {
        this.exception = exception;
        this.isOK = false;
    }
    
    public SendResult() {
        this.exception = null;
        this.isOK = true;
    }
    
    public Throwable getException() {
        return this.exception;
    }
    
    public boolean isOK() {
        return this.isOK;
    }
}
