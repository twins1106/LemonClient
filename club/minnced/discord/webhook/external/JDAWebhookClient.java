//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.external;

import okhttp3.*;
import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.*;
import java.util.concurrent.*;
import club.minnced.discord.webhook.receive.*;
import net.dv8tion.jda.api.entities.*;
import club.minnced.discord.webhook.send.*;

public class JDAWebhookClient extends WebhookClient
{
    public JDAWebhookClient(final long id, final String token, final boolean parseMessage, final OkHttpClient client, final ScheduledExecutorService pool, final AllowedMentions mentions) {
        super(id, token, parseMessage, client, pool, mentions);
    }
    
    @NotNull
    public static WebhookClient fromJDA(@NotNull final Webhook webhook) {
        return WebhookClientBuilder.fromJDA(webhook).build();
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final Message message) {
        return this.send(WebhookMessageBuilder.fromJDA(message).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final MessageEmbed embed) {
        return this.send(WebhookEmbedBuilder.fromJDA(embed).build(), new WebhookEmbed[0]);
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final Message message) {
        return this.edit(messageId, WebhookMessageBuilder.fromJDA(message).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final MessageEmbed embed) {
        return this.edit(messageId, WebhookEmbedBuilder.fromJDA(embed).build(), new WebhookEmbed[0]);
    }
}
