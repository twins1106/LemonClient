//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook;

import okhttp3.*;
import club.minnced.discord.webhook.send.*;
import java.util.*;
import java.util.regex.*;
import net.dv8tion.jda.api.entities.*;
import java.util.function.*;
import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.external.*;
import java.util.concurrent.*;

public class WebhookClientBuilder
{
    public static final Pattern WEBHOOK_PATTERN;
    protected final long id;
    protected final String token;
    protected ScheduledExecutorService pool;
    protected OkHttpClient client;
    protected ThreadFactory threadFactory;
    protected AllowedMentions allowedMentions;
    protected boolean isDaemon;
    protected boolean parseMessage;
    
    public WebhookClientBuilder(final long id, @NotNull final String token) {
        this.allowedMentions = AllowedMentions.all();
        this.parseMessage = true;
        Objects.requireNonNull(token, "Token");
        this.id = id;
        this.token = token;
    }
    
    public WebhookClientBuilder(@NotNull final String url) {
        this.allowedMentions = AllowedMentions.all();
        this.parseMessage = true;
        Objects.requireNonNull(url, "Url");
        final Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        this.id = Long.parseUnsignedLong(matcher.group(1));
        this.token = matcher.group(2);
    }
    
    @NotNull
    public static WebhookClientBuilder fromJDA(@NotNull final Webhook webhook) {
        Objects.requireNonNull(webhook, "Webhook");
        return new WebhookClientBuilder(webhook.getIdLong(), Objects.requireNonNull(webhook.getToken(), "Webhook Token"));
    }
    
    @NotNull
    public static WebhookClientBuilder fromD4J(@NotNull final discord4j.core.object.entity.Webhook webhook) {
        Objects.requireNonNull(webhook, "Webhook");
        final String token = webhook.getToken();
        Objects.requireNonNull(token, "Webhook Token");
        if (token.isEmpty()) {
            throw new NullPointerException("Webhook Token");
        }
        return new WebhookClientBuilder(webhook.getId().asLong(), token);
    }
    
    @NotNull
    public static WebhookClientBuilder fromJavacord(@NotNull final org.javacord.api.entity.webhook.Webhook webhook) {
        Objects.requireNonNull(webhook, "Webhook");
        return new WebhookClientBuilder(webhook.getId(), webhook.getToken().orElseThrow(NullPointerException::new));
    }
    
    @NotNull
    public WebhookClientBuilder setExecutorService(@Nullable final ScheduledExecutorService executorService) {
        this.pool = executorService;
        return this;
    }
    
    @NotNull
    public WebhookClientBuilder setHttpClient(@Nullable final OkHttpClient client) {
        this.client = client;
        return this;
    }
    
    @NotNull
    public WebhookClientBuilder setThreadFactory(@Nullable final ThreadFactory factory) {
        this.threadFactory = factory;
        return this;
    }
    
    @NotNull
    public WebhookClientBuilder setAllowedMentions(@Nullable final AllowedMentions mentions) {
        this.allowedMentions = ((mentions == null) ? AllowedMentions.all() : mentions);
        return this;
    }
    
    @NotNull
    public WebhookClientBuilder setDaemon(final boolean isDaemon) {
        this.isDaemon = isDaemon;
        return this;
    }
    
    @NotNull
    public WebhookClientBuilder setWait(final boolean waitForMessage) {
        this.parseMessage = waitForMessage;
        return this;
    }
    
    @NotNull
    public WebhookClient build() {
        final OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
        final ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new WebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
    }
    
    @NotNull
    public JDAWebhookClient buildJDA() {
        final OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
        final ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new JDAWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
    }
    
    @NotNull
    public D4JWebhookClient buildD4J() {
        final OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
        final ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new D4JWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
    }
    
    @NotNull
    public JavacordWebhookClient buildJavacord() {
        final OkHttpClient client = (this.client == null) ? new OkHttpClient() : this.client;
        final ScheduledExecutorService pool = (this.pool != null) ? this.pool : getDefaultPool(this.id, this.threadFactory, this.isDaemon);
        return new JavacordWebhookClient(this.id, this.token, this.parseMessage, client, pool, this.allowedMentions);
    }
    
    protected static ScheduledExecutorService getDefaultPool(final long id, final ThreadFactory factory, final boolean isDaemon) {
        return Executors.newSingleThreadScheduledExecutor((factory == null) ? new DefaultWebhookThreadFactory(id, isDaemon) : factory);
    }
    
    static {
        WEBHOOK_PATTERN = Pattern.compile("(?:https?://)?(?:\\w+\\.)?discord(?:app)?\\.com/api(?:/v\\d+)?/webhooks/(\\d+)/([\\w-]+)(?:/(?:\\w+)?)?");
    }
    
    private static final class DefaultWebhookThreadFactory implements ThreadFactory
    {
        private final long id;
        private final boolean isDaemon;
        
        public DefaultWebhookThreadFactory(final long id, final boolean isDaemon) {
            this.id = id;
            this.isDaemon = isDaemon;
        }
        
        @Override
        public Thread newThread(final Runnable r) {
            final Thread thread = new Thread(r, "Webhook-RateLimit Thread WebhookID: " + this.id);
            thread.setDaemon(this.isDaemon);
            return thread;
        }
    }
}
