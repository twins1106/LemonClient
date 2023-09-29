//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.receive;

import club.minnced.discord.webhook.send.*;
import java.time.*;
import java.util.*;
import org.jetbrains.annotations.*;
import org.json.*;

public class ReadonlyEmbed extends WebhookEmbed
{
    private final EmbedProvider provider;
    private final EmbedImage thumbnail;
    private final EmbedImage image;
    private final EmbedVideo video;
    
    public ReadonlyEmbed(@Nullable final OffsetDateTime timestamp, @Nullable final Integer color, @Nullable final String description, @Nullable final EmbedImage thumbnail, @Nullable final EmbedImage image, @Nullable final EmbedFooter footer, @Nullable final EmbedTitle title, @Nullable final EmbedAuthor author, @NotNull final List<EmbedField> fields, @Nullable final EmbedProvider provider, @Nullable final EmbedVideo video) {
        super(timestamp, color, description, (thumbnail == null) ? null : thumbnail.getUrl(), (image == null) ? null : image.getUrl(), footer, title, author, fields);
        this.thumbnail = thumbnail;
        this.image = image;
        this.provider = provider;
        this.video = video;
    }
    
    @Nullable
    public EmbedProvider getProvider() {
        return this.provider;
    }
    
    @Nullable
    public EmbedImage getThumbnail() {
        return this.thumbnail;
    }
    
    @Nullable
    public EmbedImage getImage() {
        return this.image;
    }
    
    @Nullable
    public EmbedVideo getVideo() {
        return this.video;
    }
    
    @NotNull
    @Override
    public WebhookEmbed reduced() {
        return new WebhookEmbed(this.getTimestamp(), this.getColor(), this.getDescription(), (this.thumbnail == null) ? null : this.thumbnail.getUrl(), (this.image == null) ? null : this.image.getUrl(), this.getFooter(), this.getTitle(), this.getAuthor(), this.getFields());
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    
    @Override
    public String toJSONString() {
        final JSONObject base = new JSONObject(super.toJSONString());
        base.put("provider", (Object)this.provider).put("thumbnail", (Object)this.thumbnail).put("video", (Object)this.video).put("image", (Object)this.image);
        if (this.getTitle() != null) {
            base.put("title", (Object)this.getTitle().getText());
            base.put("url", (Object)this.getTitle().getUrl());
        }
        return base.toString();
    }
    
    public static class EmbedProvider implements JSONString
    {
        private final String name;
        private final String url;
        
        public EmbedProvider(@NotNull final String name, @NotNull final String url) {
            this.name = name;
            this.url = url;
        }
        
        @NotNull
        public String getName() {
            return this.name;
        }
        
        @NotNull
        public String getUrl() {
            return this.url;
        }
        
        @Override
        public String toString() {
            return this.toJSONString();
        }
        
        public String toJSONString() {
            return new JSONObject((Object)this).toString();
        }
    }
    
    public static class EmbedVideo implements JSONString
    {
        private final String url;
        private final int width;
        private final int height;
        
        public EmbedVideo(@NotNull final String url, final int width, final int height) {
            this.url = url;
            this.width = width;
            this.height = height;
        }
        
        @NotNull
        public String getUrl() {
            return this.url;
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        @Override
        public String toString() {
            return this.toJSONString();
        }
        
        public String toJSONString() {
            return new JSONObject((Object)this).toString();
        }
    }
    
    public static class EmbedImage implements JSONString
    {
        private final String url;
        private final String proxyUrl;
        private final int width;
        private final int height;
        
        public EmbedImage(@NotNull final String url, @NotNull final String proxyUrl, final int width, final int height) {
            this.url = url;
            this.proxyUrl = proxyUrl;
            this.width = width;
            this.height = height;
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
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        @Override
        public String toString() {
            return this.toJSONString();
        }
        
        public String toJSONString() {
            return new JSONObject((Object)this).toString();
        }
    }
}
