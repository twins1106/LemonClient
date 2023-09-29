//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.send;

import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.receive.*;
import java.util.function.*;
import java.util.*;
import org.json.*;
import okhttp3.*;
import club.minnced.discord.webhook.*;
import java.io.*;

public class WebhookMessage
{
    public static final int MAX_FILES = 10;
    public static final int MAX_EMBEDS = 10;
    protected final String username;
    protected final String avatarUrl;
    protected final String content;
    protected final List<WebhookEmbed> embeds;
    protected final boolean isTTS;
    protected final MessageAttachment[] attachments;
    protected final AllowedMentions allowedMentions;
    
    protected WebhookMessage(final String username, final String avatarUrl, final String content, final List<WebhookEmbed> embeds, final boolean isTTS, final MessageAttachment[] files, final AllowedMentions allowedMentions) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.embeds = embeds;
        this.isTTS = isTTS;
        this.attachments = files;
        this.allowedMentions = allowedMentions;
    }
    
    @Nullable
    public String getUsername() {
        return this.username;
    }
    
    @Nullable
    public String getAvatarUrl() {
        return this.avatarUrl;
    }
    
    @Nullable
    public String getContent() {
        return this.content;
    }
    
    @NotNull
    public List<WebhookEmbed> getEmbeds() {
        return (this.embeds == null) ? Collections.emptyList() : this.embeds;
    }
    
    @Nullable
    public MessageAttachment[] getAttachments() {
        return this.attachments;
    }
    
    public boolean isTTS() {
        return this.isTTS;
    }
    
    @NotNull
    public static WebhookMessage from(@NotNull final ReadonlyMessage message) {
        Objects.requireNonNull(message, "Message");
        final WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setAvatarUrl(message.getAuthor().getAvatarId());
        builder.setUsername(message.getAuthor().getName());
        builder.setContent(message.getContent());
        builder.setTTS(message.isTTS());
        builder.addEmbeds(message.getEmbeds());
        return builder.build();
    }
    
    @NotNull
    public static WebhookMessage embeds(@NotNull final WebhookEmbed first, @NotNull final WebhookEmbed... embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (embeds.length >= 10) {
            throw new IllegalArgumentException("Cannot add more than 10 embeds to a message");
        }
        for (final WebhookEmbed e : embeds) {
            Objects.requireNonNull(e);
        }
        final List<WebhookEmbed> list = new ArrayList<WebhookEmbed>(1 + embeds.length);
        list.add(first);
        Collections.addAll(list, embeds);
        return new WebhookMessage(null, null, null, list, false, null, AllowedMentions.all());
    }
    
    @NotNull
    public static WebhookMessage embeds(@NotNull final Collection<WebhookEmbed> embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (embeds.size() > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 embeds to a message");
        }
        if (embeds.isEmpty()) {
            throw new IllegalArgumentException("Cannot build an empty message");
        }
        embeds.forEach(Objects::requireNonNull);
        return new WebhookMessage(null, null, null, new ArrayList<WebhookEmbed>(embeds), false, null, AllowedMentions.all());
    }
    
    @NotNull
    public static WebhookMessage files(@NotNull final Map<String, ?> attachments) {
        Objects.requireNonNull(attachments, "Attachments");
        final int fileAmount = attachments.size();
        if (fileAmount == 0) {
            throw new IllegalArgumentException("Cannot build an empty message");
        }
        if (fileAmount > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 files to a message");
        }
        final Set<? extends Map.Entry<String, ?>> entries = attachments.entrySet();
        final MessageAttachment[] files = new MessageAttachment[fileAmount];
        int i = 0;
        for (final Map.Entry<String, ?> attachment : entries) {
            final String name = attachment.getKey();
            Objects.requireNonNull(name, "Name");
            final Object data = attachment.getValue();
            files[i++] = convertAttachment(name, data);
        }
        return new WebhookMessage(null, null, null, null, false, files, AllowedMentions.all());
    }
    
    @NotNull
    public static WebhookMessage files(@NotNull final String name1, @NotNull final Object data1, @NotNull final Object... attachments) {
        Objects.requireNonNull(name1, "Name");
        Objects.requireNonNull(data1, "Data");
        Objects.requireNonNull(attachments, "Attachments");
        if (attachments.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide even number of varargs arguments");
        }
        final int fileAmount = 1 + attachments.length / 2;
        if (fileAmount > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 files to a message");
        }
        final MessageAttachment[] files = new MessageAttachment[fileAmount];
        files[0] = convertAttachment(name1, data1);
        int i = 0;
        int j = 1;
        while (i < attachments.length) {
            final Object name2 = attachments[i];
            final Object data2 = attachments[i + 1];
            if (!(name2 instanceof String)) {
                throw new IllegalArgumentException("Provided arguments must be pairs for (String, Data). Expected String and found " + ((name2 == null) ? null : name2.getClass().getName()));
            }
            files[j] = convertAttachment((String)name2, data2);
            ++j;
            i += 2;
        }
        return new WebhookMessage(null, null, null, null, false, files, AllowedMentions.all());
    }
    
    public boolean isFile() {
        return this.attachments != null;
    }
    
    @NotNull
    public RequestBody getBody() {
        final JSONObject payload = new JSONObject();
        payload.put("content", (Object)this.content);
        if (this.embeds != null && !this.embeds.isEmpty()) {
            final JSONArray array = new JSONArray();
            for (final WebhookEmbed embed : this.embeds) {
                array.put((Object)embed.reduced());
            }
            payload.put("embeds", (Object)array);
        }
        else {
            payload.put("embeds", (Object)new JSONArray());
        }
        if (this.avatarUrl != null) {
            payload.put("avatar_url", (Object)this.avatarUrl);
        }
        if (this.username != null) {
            payload.put("username", (Object)this.username);
        }
        payload.put("tts", this.isTTS);
        payload.put("allowed_mentions", (Object)this.allowedMentions);
        final String json = payload.toString();
        if (this.isFile()) {
            final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < this.attachments.length; ++i) {
                final MessageAttachment attachment = this.attachments[i];
                if (attachment == null) {
                    break;
                }
                builder.addFormDataPart("file" + i, attachment.getName(), (RequestBody)new IOUtil.OctetBody(attachment.getData()));
            }
            return (RequestBody)builder.addFormDataPart("payload_json", json).build();
        }
        return RequestBody.create(IOUtil.JSON, json);
    }
    
    @NotNull
    private static MessageAttachment convertAttachment(@NotNull final String name, @NotNull final Object data) {
        Objects.requireNonNull(name, "Name");
        Objects.requireNonNull(data, "Data");
        try {
            MessageAttachment a;
            if (data instanceof File) {
                a = new MessageAttachment(name, (File)data);
            }
            else if (data instanceof InputStream) {
                a = new MessageAttachment(name, (InputStream)data);
            }
            else {
                if (!(data instanceof byte[])) {
                    throw new IllegalArgumentException("Provided arguments must be pairs for (String, Data). Unexpected data type " + data.getClass().getName());
                }
                a = new MessageAttachment(name, (byte[])data);
            }
            return a;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
