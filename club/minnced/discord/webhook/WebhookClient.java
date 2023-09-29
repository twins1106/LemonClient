//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook;

import java.util.regex.*;
import javax.annotation.*;
import club.minnced.discord.webhook.send.*;
import java.util.*;
import club.minnced.discord.webhook.exception.*;
import java.io.*;
import okhttp3.*;
import org.jetbrains.annotations.*;
import java.util.concurrent.*;
import club.minnced.discord.webhook.receive.*;
import org.json.*;
import org.slf4j.*;

public class WebhookClient implements AutoCloseable
{
    public static final String WEBHOOK_URL = "https://discord.com/api/v8/webhooks/%s/%s";
    public static final String USER_AGENT = "Webhook(https://github.com/MinnDevelopment/discord-webhooks, 0.5.6)";
    private static final Logger LOG;
    protected final String url;
    protected final long id;
    protected final OkHttpClient client;
    protected final ScheduledExecutorService pool;
    protected final Bucket bucket;
    protected final BlockingQueue<Request> queue;
    protected final boolean parseMessage;
    protected final AllowedMentions allowedMentions;
    protected long defaultTimeout;
    protected volatile boolean isQueued;
    protected boolean isShutdown;
    
    protected WebhookClient(final long id, final String token, final boolean parseMessage, final OkHttpClient client, final ScheduledExecutorService pool, final AllowedMentions mentions) {
        this.client = client;
        this.id = id;
        this.parseMessage = parseMessage;
        this.url = String.format("https://discord.com/api/v8/webhooks/%s/%s", Long.toUnsignedString(id), token);
        this.pool = pool;
        this.bucket = new Bucket();
        this.queue = new LinkedBlockingQueue<Request>();
        this.allowedMentions = mentions;
        this.isQueued = false;
    }
    
    public static WebhookClient withId(final long id, @NotNull final String token) {
        Objects.requireNonNull(token, "Token");
        final ScheduledExecutorService pool = WebhookClientBuilder.getDefaultPool(id, null, false);
        return new WebhookClient(id, token, true, new OkHttpClient(), pool, AllowedMentions.all());
    }
    
    public static WebhookClient withUrl(@NotNull final String url) {
        Objects.requireNonNull(url, "URL");
        final Matcher matcher = WebhookClientBuilder.WEBHOOK_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse webhook URL");
        }
        return withId(Long.parseUnsignedLong(matcher.group(1)), matcher.group(2));
    }
    
    public long getId() {
        return this.id;
    }
    
    @NotNull
    public String getUrl() {
        return this.url;
    }
    
    public boolean isWait() {
        return this.parseMessage;
    }
    
    public boolean isShutdown() {
        return this.isShutdown;
    }
    
    @NotNull
    public WebhookClient setTimeout(@Nonnegative final long millis) {
        if (millis < 0L) {
            throw new IllegalArgumentException("Cannot set a negative timeout");
        }
        this.defaultTimeout = millis;
        return this;
    }
    
    public long getTimeout() {
        return this.defaultTimeout;
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final WebhookMessage message) {
        Objects.requireNonNull(message, "WebhookMessage");
        return this.execute(message.getBody());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final File file) {
        Objects.requireNonNull(file, "File");
        return this.send(file, file.getName());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final File file, @NotNull final String fileName) {
        return this.send(new WebhookMessageBuilder().setAllowedMentions(this.allowedMentions).addFile(fileName, file).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final byte[] data, @NotNull final String fileName) {
        return this.send(new WebhookMessageBuilder().setAllowedMentions(this.allowedMentions).addFile(fileName, data).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final InputStream data, @NotNull final String fileName) {
        return this.send(new WebhookMessageBuilder().setAllowedMentions(this.allowedMentions).addFile(fileName, data).build());
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final WebhookEmbed first, @NotNull final WebhookEmbed... embeds) {
        return this.send(WebhookMessage.embeds(first, embeds));
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull final Collection<WebhookEmbed> embeds) {
        return this.send(WebhookMessage.embeds((Collection)embeds));
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> send(@NotNull String content) {
        Objects.requireNonNull(content, "Content");
        content = content.trim();
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Cannot send an empty message");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters");
        }
        return this.execute(newBody(this.newJson().put("content", (Object)content).toString()));
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final WebhookMessage message) {
        Objects.requireNonNull(message, "WebhookMessage");
        return this.execute(message.getBody(), Long.toUnsignedString(messageId), RequestType.EDIT);
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final WebhookEmbed first, @NotNull final WebhookEmbed... embeds) {
        return this.edit(messageId, WebhookMessage.embeds(first, embeds));
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull final Collection<WebhookEmbed> embeds) {
        return this.edit(messageId, WebhookMessage.embeds((Collection)embeds));
    }
    
    @NotNull
    public CompletableFuture<ReadonlyMessage> edit(final long messageId, @NotNull String content) {
        Objects.requireNonNull(content, "Content");
        content = content.trim();
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Cannot send an empty message");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters");
        }
        return this.edit(messageId, new WebhookMessageBuilder().setContent(content).build());
    }
    
    @NotNull
    public CompletableFuture<Void> delete(final long messageId) {
        return this.execute(null, Long.toUnsignedString(messageId), RequestType.DELETE).thenApply(v -> null);
    }
    
    private JSONObject newJson() {
        final JSONObject json = new JSONObject();
        json.put("allowed_mentions", (Object)this.allowedMentions);
        return json;
    }
    
    @Override
    public void close() {
        this.isShutdown = true;
        if (this.queue.isEmpty()) {
            this.pool.shutdown();
        }
    }
    
    protected void checkShutdown() {
        if (this.isShutdown) {
            throw new RejectedExecutionException("Cannot send to closed client!");
        }
    }
    
    @NotNull
    protected static RequestBody newBody(final String object) {
        return RequestBody.create(IOUtil.JSON, object);
    }
    
    @NotNull
    protected CompletableFuture<ReadonlyMessage> execute(final RequestBody body, @Nullable final String messageId, @NotNull final RequestType type) {
        this.checkShutdown();
        String endpoint = this.url;
        if (type != RequestType.SEND) {
            endpoint = endpoint + "/messages/" + messageId;
        }
        if (this.parseMessage) {
            endpoint += "?wait=true";
        }
        return this.queueRequest(endpoint, type.method, body);
    }
    
    @NotNull
    protected CompletableFuture<ReadonlyMessage> execute(final RequestBody body) {
        return this.execute(body, null, RequestType.SEND);
    }
    
    @NotNull
    protected static HttpException failure(final Response response) throws IOException {
        final InputStream stream = IOUtil.getBody(response);
        final String responseBody = (stream == null) ? "" : new String(IOUtil.readAllBytes(stream));
        return new HttpException(response.code(), responseBody, response.headers());
    }
    
    @NotNull
    protected CompletableFuture<ReadonlyMessage> queueRequest(final String url, final String method, final RequestBody body) {
        final boolean wasQueued = this.isQueued;
        this.isQueued = true;
        final CompletableFuture<ReadonlyMessage> callback = new CompletableFuture<ReadonlyMessage>();
        final Request req = new Request(callback, body, method, url);
        if (this.defaultTimeout > 0L) {
            req.deadline = System.currentTimeMillis() + this.defaultTimeout;
        }
        this.enqueuePair(req);
        if (!wasQueued) {
            this.backoffQueue();
        }
        return callback;
    }
    
    @NotNull
    protected okhttp3.Request newRequest(final Request request) {
        return new okhttp3.Request.Builder().url(request.url).method(request.method, request.body).header("accept-encoding", "gzip").header("user-agent", "Webhook(https://github.com/MinnDevelopment/discord-webhooks, 0.5.6)").build();
    }
    
    protected void backoffQueue() {
        final long delay = this.bucket.retryAfter();
        if (delay > 0L) {
            WebhookClient.LOG.debug("Backing off queue for {}", (Object)delay);
        }
        this.pool.schedule(this::drainQueue, delay, TimeUnit.MILLISECONDS);
    }
    
    protected synchronized void drainQueue() {
        boolean graceful = true;
        while (!this.queue.isEmpty()) {
            final Request pair = this.queue.peek();
            graceful = this.executePair(pair);
            if (!graceful) {
                break;
            }
        }
        this.isQueued = !graceful;
        if (this.isShutdown && graceful) {
            this.pool.shutdown();
        }
    }
    
    private boolean enqueuePair(@Async.Schedule final Request pair) {
        return this.queue.add(pair);
    }
    
    private boolean executePair(@Async.Execute final Request req) {
        if (req.future.isDone()) {
            this.queue.poll();
            return true;
        }
        if (req.deadline > 0L && req.deadline < System.currentTimeMillis()) {
            req.future.completeExceptionally(new TimeoutException());
            this.queue.poll();
            return true;
        }
        final okhttp3.Request request = this.newRequest(req);
        try (final Response response = this.client.newCall(request).execute()) {
            this.bucket.update(response);
            if (response.code() == 429) {
                this.backoffQueue();
                return false;
            }
            if (!response.isSuccessful()) {
                final HttpException exception = failure(response);
                WebhookClient.LOG.error("Sending a webhook message failed with non-OK http response", (Throwable)exception);
                this.queue.poll().future.completeExceptionally((Throwable)exception);
                return true;
            }
            ReadonlyMessage message = null;
            if (this.parseMessage && !"DELETE".equals(req.method)) {
                final InputStream body = IOUtil.getBody(response);
                final JSONObject json = IOUtil.toJSON(body);
                message = EntityFactory.makeMessage(json);
            }
            this.queue.poll().future.complete(message);
            if (this.bucket.isRateLimit()) {
                this.backoffQueue();
                return false;
            }
        }
        catch (JSONException | IOException ex2) {
            final Exception ex;
            final Exception e = ex;
            WebhookClient.LOG.error("There was some error while sending a webhook message", (Throwable)e);
            this.queue.poll().future.completeExceptionally(e);
        }
        return true;
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)WebhookClient.class);
    }
    
    enum RequestType
    {
        SEND("POST"), 
        EDIT("PATCH"), 
        DELETE("DELETE");
        
        private final String method;
        
        private RequestType(final String method) {
            this.method = method;
        }
        
        public String getMethod() {
            return this.method;
        }
    }
    
    protected static final class Bucket
    {
        public static final int RATE_LIMIT_CODE = 429;
        public long resetTime;
        public int remainingUses;
        public int limit;
        
        protected Bucket() {
            this.limit = Integer.MAX_VALUE;
        }
        
        public synchronized boolean isRateLimit() {
            if (this.retryAfter() <= 0L) {
                this.remainingUses = this.limit;
            }
            return this.remainingUses <= 0;
        }
        
        public synchronized long retryAfter() {
            return this.resetTime - System.currentTimeMillis();
        }
        
        private synchronized void handleRatelimit(final Response response, final long current) throws IOException {
            final String retryAfter = response.header("Retry-After");
            long delay;
            if (retryAfter == null) {
                final InputStream stream = IOUtil.getBody(response);
                final JSONObject body = IOUtil.toJSON(stream);
                delay = (long)Math.ceil(body.getDouble("retry_after")) * 1000L;
            }
            else {
                delay = Long.parseLong(retryAfter) * 1000L;
            }
            WebhookClient.LOG.error("Encountered 429, retrying after {}", (Object)delay);
            this.resetTime = current + delay;
        }
        
        private synchronized void update0(final Response response) throws IOException {
            final long current = System.currentTimeMillis();
            final boolean is429 = response.code() == 429;
            if (is429) {
                this.handleRatelimit(response, current);
            }
            else if (!response.isSuccessful()) {
                WebhookClient.LOG.debug("Failed to update buckets due to unsuccessful response with code: {} and body: \n{}", (Object)response.code(), (Object)new IOUtil.Lazy(() -> new String(IOUtil.readAllBytes(IOUtil.getBody(response)))));
                return;
            }
            this.remainingUses = Integer.parseInt(response.header("X-RateLimit-Remaining"));
            this.limit = Integer.parseInt(response.header("X-RateLimit-Limit"));
            if (!is429) {
                final long reset = (long)Math.ceil(Double.parseDouble(response.header("X-RateLimit-Reset-After")));
                final long delay = reset * 1000L;
                this.resetTime = current + delay;
            }
        }
        
        public void update(final Response response) {
            try {
                this.update0(response);
            }
            catch (Exception ex) {
                WebhookClient.LOG.error("Could not read http response", (Throwable)ex);
            }
        }
    }
    
    private static final class Request
    {
        private final CompletableFuture<ReadonlyMessage> future;
        private final RequestBody body;
        private final String method;
        private final String url;
        private long deadline;
        
        public Request(final CompletableFuture<ReadonlyMessage> future, final RequestBody body, final String method, final String url) {
            this.future = future;
            this.body = body;
            this.method = method;
            this.url = url;
        }
    }
}
