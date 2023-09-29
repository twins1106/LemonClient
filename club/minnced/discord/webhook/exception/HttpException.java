//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.exception;

import okhttp3.*;
import org.jetbrains.annotations.*;

public class HttpException extends RuntimeException
{
    private final int code;
    private final String body;
    private final Headers headers;
    
    public HttpException(final int code, @NotNull final String body, @NotNull final Headers headers) {
        super("Request returned failure " + code + ": " + body);
        this.body = body;
        this.code = code;
        this.headers = headers;
    }
    
    public int getCode() {
        return this.code;
    }
    
    @NotNull
    public String getBody() {
        return this.body;
    }
    
    @NotNull
    public Headers getHeaders() {
        return this.headers;
    }
}
