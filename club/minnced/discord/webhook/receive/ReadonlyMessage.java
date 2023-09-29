//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.receive;

import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.send.*;
import org.json.*;
import java.util.*;

public class ReadonlyMessage implements JSONString
{
    private final long id;
    private final long channelId;
    private final boolean mentionsEveryone;
    private final boolean tts;
    private final ReadonlyUser author;
    private final String content;
    private final List<ReadonlyEmbed> embeds;
    private final List<ReadonlyAttachment> attachments;
    private final List<ReadonlyUser> mentionedUsers;
    private final List<Long> mentionedRoles;
    
    public ReadonlyMessage(final long id, final long channelId, final boolean mentionsEveryone, final boolean tts, @NotNull final ReadonlyUser author, @NotNull final String content, @NotNull final List<ReadonlyEmbed> embeds, @NotNull final List<ReadonlyAttachment> attachments, @NotNull final List<ReadonlyUser> mentionedUsers, @NotNull final List<Long> mentionedRoles) {
        this.id = id;
        this.channelId = channelId;
        this.mentionsEveryone = mentionsEveryone;
        this.tts = tts;
        this.author = author;
        this.content = content;
        this.embeds = embeds;
        this.attachments = attachments;
        this.mentionedUsers = mentionedUsers;
        this.mentionedRoles = mentionedRoles;
    }
    
    public long getId() {
        return this.id;
    }
    
    public long getChannelId() {
        return this.channelId;
    }
    
    public boolean isMentionsEveryone() {
        return this.mentionsEveryone;
    }
    
    public boolean isTTS() {
        return this.tts;
    }
    
    @NotNull
    public ReadonlyUser getAuthor() {
        return this.author;
    }
    
    @NotNull
    public String getContent() {
        return this.content;
    }
    
    @NotNull
    public List<ReadonlyEmbed> getEmbeds() {
        return this.embeds;
    }
    
    @NotNull
    public List<ReadonlyAttachment> getAttachments() {
        return this.attachments;
    }
    
    @NotNull
    public List<ReadonlyUser> getMentionedUsers() {
        return this.mentionedUsers;
    }
    
    @NotNull
    public List<Long> getMentionedRoles() {
        return this.mentionedRoles;
    }
    
    @NotNull
    public WebhookMessage toWebhookMessage() {
        return WebhookMessage.from(this);
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    
    public String toJSONString() {
        final JSONObject json = new JSONObject();
        json.put("content", (Object)this.content).put("embeds", (Collection)this.embeds).put("mentions", (Collection)this.mentionedUsers).put("mention_roles", (Collection)this.mentionedRoles).put("attachments", (Collection)this.attachments).put("author", (Object)this.author).put("tts", this.tts).put("id", (Object)Long.toUnsignedString(this.id)).put("channel_id", (Object)Long.toUnsignedString(this.channelId)).put("mention_everyone", this.mentionsEveryone);
        return json.toString();
    }
}
