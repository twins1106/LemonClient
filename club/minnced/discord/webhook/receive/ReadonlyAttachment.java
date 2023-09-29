//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.receive;

import org.jetbrains.annotations.*;
import org.json.*;

public class ReadonlyAttachment implements JSONString
{
    private final String url;
    private final String proxyUrl;
    private final String fileName;
    private final int width;
    private final int height;
    private final int size;
    private final long id;
    
    public ReadonlyAttachment(@NotNull final String url, @NotNull final String proxyUrl, @NotNull final String fileName, final int width, final int height, final int size, final long id) {
        this.url = url;
        this.proxyUrl = proxyUrl;
        this.fileName = fileName;
        this.width = width;
        this.height = height;
        this.size = size;
        this.id = id;
    }
    
    @NotNull
    public String getUrl() {
        return this.url;
    }
    
    @JSONPropertyName("proxy_url")
    @NotNull
    public String getProxyUrl() {
        return this.proxyUrl;
    }
    
    @JSONPropertyName("filename")
    @NotNull
    public String getFileName() {
        return this.fileName;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public long getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    
    public String toJSONString() {
        return new JSONObject((Object)this).toString();
    }
}
