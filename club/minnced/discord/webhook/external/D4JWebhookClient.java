//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.external;

import okhttp3.*;
import discord4j.core.object.entity.*;
import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.*;
import java.util.function.*;
import discord4j.core.spec.*;
import reactor.core.publisher.*;
import club.minnced.discord.webhook.receive.*;
import club.minnced.discord.webhook.send.*;
import javax.annotation.*;
import java.util.concurrent.*;

public class D4JWebhookClient extends WebhookClient
{
    public D4JWebhookClient(final long id, final String token, final boolean parseMessage, final OkHttpClient client, final ScheduledExecutorService pool, final AllowedMentions mentions) {
        super(id, token, parseMessage, client, pool, mentions);
    }
    
    @NotNull
    public static WebhookClient from(@NotNull final Webhook webhook) {
        return WebhookClientBuilder.fromD4J(webhook).build();
    }
    
    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> send(@NotNull final Consumer<? super MessageCreateSpec> callback) {
        final WebhookMessage message = WebhookMessageBuilder.fromD4J(callback).build();
        return (Mono<ReadonlyMessage>)Mono.fromFuture(() -> this.send(message));
    }
    
    @CheckReturnValue
    @NotNull
    public Mono<ReadonlyMessage> edit(final long messageId, @NotNull final Consumer<? super MessageCreateSpec> callback) {
        final WebhookMessage message = WebhookMessageBuilder.fromD4J(callback).build();
        return (Mono<ReadonlyMessage>)Mono.fromFuture(() -> this.edit(messageId, message));
    }
}
