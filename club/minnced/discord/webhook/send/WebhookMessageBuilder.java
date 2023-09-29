//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.send;

import org.jetbrains.annotations.*;
import java.io.*;
import net.dv8tion.jda.internal.entities.*;
import java.util.stream.*;
import java.util.*;
import org.javacord.api.entity.*;
import java.util.function.*;
import discord4j.core.spec.*;
import discord4j.rest.util.*;
import discord4j.discordjson.json.*;
import discord4j.discordjson.possible.*;
import reactor.util.function.*;
import org.javacord.api.entity.message.embed.*;
import net.dv8tion.jda.api.entities.*;

public class WebhookMessageBuilder
{
    protected final StringBuilder content;
    protected final List<WebhookEmbed> embeds;
    protected final MessageAttachment[] files;
    protected AllowedMentions allowedMentions;
    protected String username;
    protected String avatarUrl;
    protected boolean isTTS;
    private int fileIndex;
    
    public WebhookMessageBuilder() {
        this.content = new StringBuilder();
        this.embeds = new LinkedList<WebhookEmbed>();
        this.files = new MessageAttachment[10];
        this.allowedMentions = AllowedMentions.all();
        this.fileIndex = 0;
    }
    
    public boolean isEmpty() {
        return this.content.length() == 0 && this.embeds.isEmpty() && this.getFileAmount() == 0;
    }
    
    public int getFileAmount() {
        return this.fileIndex;
    }
    
    @NotNull
    public WebhookMessageBuilder reset() {
        this.content.setLength(0);
        this.resetEmbeds();
        this.resetFiles();
        this.username = null;
        this.avatarUrl = null;
        this.isTTS = false;
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder resetFiles() {
        for (int i = 0; i < 10; ++i) {
            this.files[i] = null;
        }
        this.fileIndex = 0;
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder resetEmbeds() {
        this.embeds.clear();
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder setAllowedMentions(@NotNull final AllowedMentions mentions) {
        this.allowedMentions = Objects.requireNonNull(mentions);
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder addEmbeds(@NotNull final WebhookEmbed... embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (this.embeds.size() + embeds.length > 10) {
            throw new IllegalStateException("Cannot add more than 10 embeds to a message");
        }
        for (final WebhookEmbed embed : embeds) {
            Objects.requireNonNull(embed, "Embed");
            this.embeds.add(embed);
        }
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder addEmbeds(@NotNull final Collection<? extends WebhookEmbed> embeds) {
        Objects.requireNonNull(embeds, "Embeds");
        if (this.embeds.size() + embeds.size() > 10) {
            throw new IllegalStateException("Cannot add more than 10 embeds to a message");
        }
        for (final WebhookEmbed embed : embeds) {
            Objects.requireNonNull(embed, "Embed");
            this.embeds.add(embed);
        }
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder setContent(@Nullable final String content) {
        if (content != null && content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters!");
        }
        this.content.setLength(0);
        if (content != null && !content.isEmpty()) {
            this.content.append(content);
        }
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder append(@NotNull final String content) {
        Objects.requireNonNull(content, "Content");
        if (this.content.length() + content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters!");
        }
        this.content.append(content);
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder setUsername(@Nullable final String username) {
        this.username = ((username == null || username.trim().isEmpty()) ? null : username.trim());
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder setAvatarUrl(@Nullable final String avatarUrl) {
        this.avatarUrl = ((avatarUrl == null || avatarUrl.trim().isEmpty()) ? null : avatarUrl.trim());
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder setTTS(final boolean tts) {
        this.isTTS = tts;
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder addFile(@NotNull final File file) {
        Objects.requireNonNull(file, "File");
        return this.addFile(file.getName(), file);
    }
    
    @NotNull
    public WebhookMessageBuilder addFile(@NotNull final String name, @NotNull final File file) {
        Objects.requireNonNull(file, "File");
        Objects.requireNonNull(name, "Name");
        if (!file.exists() || !file.canRead()) {
            throw new IllegalArgumentException("File must exist and be readable");
        }
        if (this.fileIndex >= 10) {
            throw new IllegalStateException("Cannot add more than 10 attachments to a message");
        }
        try {
            final MessageAttachment attachment = new MessageAttachment(name, file);
            this.files[this.fileIndex++] = attachment;
            return this;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    @NotNull
    public WebhookMessageBuilder addFile(@NotNull final String name, @NotNull final byte[] data) {
        Objects.requireNonNull(data, "Data");
        Objects.requireNonNull(name, "Name");
        if (this.fileIndex >= 10) {
            throw new IllegalStateException("Cannot add more than 10 attachments to a message");
        }
        final MessageAttachment attachment = new MessageAttachment(name, data);
        this.files[this.fileIndex++] = attachment;
        return this;
    }
    
    @NotNull
    public WebhookMessageBuilder addFile(@NotNull final String name, @NotNull final InputStream data) {
        Objects.requireNonNull(data, "InputStream");
        Objects.requireNonNull(name, "Name");
        if (this.fileIndex >= 10) {
            throw new IllegalStateException("Cannot add more than 10 attachments to a message");
        }
        try {
            final MessageAttachment attachment = new MessageAttachment(name, data);
            this.files[this.fileIndex++] = attachment;
            return this;
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
    
    @NotNull
    public WebhookMessage build() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Cannot build an empty message!");
        }
        return new WebhookMessage(this.username, this.avatarUrl, this.content.toString(), (List)this.embeds, this.isTTS, (MessageAttachment[])((this.fileIndex == 0) ? null : ((MessageAttachment[])Arrays.copyOf(this.files, this.fileIndex))), this.allowedMentions);
    }
    
    @NotNull
    public static WebhookMessageBuilder fromJDA(@NotNull final Message message) {
        final WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setTTS(message.isTTS());
        builder.setContent(message.getContentRaw());
        message.getEmbeds().forEach(embed -> builder.addEmbeds(WebhookEmbedBuilder.fromJDA(embed).build()));
        if (message instanceof DataMessage) {
            final DataMessage data = (DataMessage)message;
            final AllowedMentions allowedMentions = AllowedMentions.none();
            final EnumSet<Message.MentionType> parse = (EnumSet<Message.MentionType>)data.getAllowedMentions();
            allowedMentions.withUsers(data.getMentionedUsersWhitelist());
            allowedMentions.withRoles(data.getMentionedRolesWhitelist());
            if (parse != null) {
                allowedMentions.withParseUsers(parse.contains(Message.MentionType.USER));
                allowedMentions.withParseRoles(parse.contains(Message.MentionType.ROLE));
                allowedMentions.withParseEveryone(parse.contains(Message.MentionType.EVERYONE) || parse.contains(Message.MentionType.HERE));
            }
            builder.setAllowedMentions(allowedMentions);
        }
        else if (message instanceof ReceivedMessage) {
            final AllowedMentions allowedMentions2 = AllowedMentions.none();
            allowedMentions2.withRoles((Collection)message.getMentionedRoles().stream().map(ISnowflake::getId).collect(Collectors.toList()));
            allowedMentions2.withUsers((Collection)message.getMentionedUsers().stream().map(ISnowflake::getId).collect(Collectors.toList()));
            allowedMentions2.withParseEveryone(message.mentionsEveryone());
            builder.setAllowedMentions(allowedMentions2);
        }
        return builder;
    }
    
    @NotNull
    public static WebhookMessageBuilder fromJavacord(@NotNull final org.javacord.api.entity.message.Message message) {
        final WebhookMessageBuilder builder = new WebhookMessageBuilder();
        builder.setTTS(message.isTts());
        builder.setContent(message.getContent());
        message.getEmbeds().forEach(embed -> builder.addEmbeds(WebhookEmbedBuilder.fromJavacord(embed).build()));
        final AllowedMentions allowedMentions = AllowedMentions.none();
        allowedMentions.withUsers((Collection)message.getMentionedUsers().stream().map(DiscordEntity::getIdAsString).collect(Collectors.toList()));
        allowedMentions.withRoles((Collection)message.getMentionedRoles().stream().map(DiscordEntity::getIdAsString).collect(Collectors.toList()));
        allowedMentions.withParseEveryone(message.mentionsEveryone());
        builder.setAllowedMentions(allowedMentions);
        return builder;
    }
    
    @NotNull
    public static WebhookMessageBuilder fromD4J(@NotNull final Consumer<? super MessageCreateSpec> callback) {
        final WebhookMessageBuilder builder = new WebhookMessageBuilder();
        final MessageCreateSpec spec = new MessageCreateSpec();
        callback.accept(spec);
        final MultipartRequest data = spec.asRequest();
        data.getFiles().forEach(tuple -> builder.addFile((String)tuple.getT1(), (InputStream)tuple.getT2()));
        final MessageCreateRequest parts = data.getCreateRequest();
        if (parts == null) {
            return builder;
        }
        final Possible<String> content = (Possible<String>)parts.content();
        final Possible<EmbedData> embed = (Possible<EmbedData>)parts.embed();
        final Possible<Boolean> tts = (Possible<Boolean>)parts.tts();
        final Possible<AllowedMentionsData> allowedMentions = (Possible<AllowedMentionsData>)parts.allowedMentions();
        if (!content.isAbsent()) {
            builder.setContent((String)content.get());
        }
        if (!embed.isAbsent()) {
            builder.addEmbeds(WebhookEmbedBuilder.fromD4J((EmbedData)embed.get()).build());
        }
        if (!tts.isAbsent()) {
            builder.setTTS((boolean)tts.get());
        }
        if (!allowedMentions.isAbsent()) {
            final AllowedMentionsData mentions = (AllowedMentionsData)allowedMentions.get();
            final AllowedMentions whitelist = AllowedMentions.none();
            if (!mentions.users().isAbsent()) {
                whitelist.withUsers((Collection)mentions.users().get());
            }
            if (!mentions.roles().isAbsent()) {
                whitelist.withRoles((Collection)mentions.roles().get());
            }
            if (!mentions.parse().isAbsent()) {
                final List<String> parse = (List<String>)mentions.parse().get();
                whitelist.withParseRoles(parse.contains("roles"));
                whitelist.withParseEveryone(parse.contains("everyone"));
                whitelist.withParseUsers(parse.contains("users"));
            }
            builder.setAllowedMentions(whitelist);
        }
        return builder;
    }
}
