//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.external;

import okhttp3.*;
import org.javacord.api.entity.webhook.*;
import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.*;
import org.javacord.api.entity.message.*;
import java.util.concurrent.*;
import club.minnced.discord.webhook.receive.*;
import org.javacord.api.entity.message.embed.*;
import club.minnced.discord.webhook.send.*;

public class JavacordWebhookClient extends WebhookClient
{
    public JavacordWebhookClient(final long id, final String token, final boolean parseMessage, final OkHttpClient client, final ScheduledExecutorService pool, final AllowedMentions mentions) {
        super(id, token, parseMessage, client, pool, mentions);
    }
    
    @NotNull
    public static WebhookClient from(@NotNull final Webhook webhook) {
        return WebhookClientBuilder.fromJavacord(webhook).build();
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final Message message) {
        return this.send(WebhookMessageBuilder.fromJavacord(message).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final Embed embed) {
        return this.send(WebhookEmbedBuilder.fromJavacord(embed).build(), new WebhookEmbed[0]);
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final Message message) {
        return this.edit(messageId, WebhookMessageBuilder.fromJavacord(message).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final Embed embed) {
        return this.edit(messageId, WebhookEmbedBuilder.fromJavacord(embed).build(), new WebhookEmbed[0]);
    }
}
