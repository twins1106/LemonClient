//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook;

import org.jetbrains.annotations.*;
import java.util.function.*;
import java.util.*;
import java.util.concurrent.*;
import club.minnced.discord.webhook.receive.*;
import okhttp3.*;
import club.minnced.discord.webhook.send.*;
import java.util.stream.*;
import java.io.*;

public class WebhookCluster implements AutoCloseable
{
    protected final List<WebhookClient> webhooks;
    protected OkHttpClient defaultHttpClient;
    protected ScheduledExecutorService defaultPool;
    protected ThreadFactory threadFactory;
    protected AllowedMentions allowedMentions;
    protected boolean isDaemon;
    
    public WebhookCluster(@NotNull final Collection<? extends WebhookClient> initialClients) {
        this.allowedMentions = AllowedMentions.all();
        Objects.requireNonNull(initialClients, "List");
        this.webhooks = new ArrayList<WebhookClient>(initialClients.size());
        for (final WebhookClient client : initialClients) {
            this.addWebhooks(client);
        }
    }
    
    public WebhookCluster(final int initialCapacity) {
        this.allowedMentions = AllowedMentions.all();
        this.webhooks = new ArrayList<WebhookClient>(initialCapacity);
    }
    
    public WebhookCluster() {
        this.allowedMentions = AllowedMentions.all();
        this.webhooks = new ArrayList<WebhookClient>();
    }
    
    @NotNull
    public WebhookCluster setDefaultHttpClient(@Nullable final OkHttpClient defaultHttpClient) {
        this.defaultHttpClient = defaultHttpClient;
        return this;
    }
    
    @NotNull
    public WebhookCluster setDefaultExecutorService(@Nullable final ScheduledExecutorService executorService) {
        this.defaultPool = executorService;
        return this;
    }
    
    @NotNull
    public WebhookCluster setDefaultThreadFactory(@Nullable final ThreadFactory factory) {
        this.threadFactory = factory;
        return this;
    }
    
    @NotNull
    public WebhookCluster setAllowedMentions(@Nullable final AllowedMentions allowedMentions) {
        this.allowedMentions = ((allowedMentions == null) ? AllowedMentions.all() : allowedMentions);
        return this;
    }
    
    @NotNull
    public WebhookCluster setDefaultDaemon(final boolean isDaemon) {
        this.isDaemon = isDaemon;
        return this;
    }
    
    @NotNull
    public WebhookCluster buildWebhook(final long id, @NotNull final String token) {
        this.webhooks.add(this.newBuilder(id, token).build());
        return this;
    }
    
    @NotNull
    public WebhookClientBuilder newBuilder(final long id, @NotNull final String token) {
        final WebhookClientBuilder builder = new WebhookClientBuilder(id, token);
        builder.setExecutorService(this.defaultPool).setHttpClient(this.defaultHttpClient).setThreadFactory(this.threadFactory).setAllowedMentions(this.allowedMentions).setDaemon(this.isDaemon);
        return builder;
    }
    
    @NotNull
    public WebhookCluster addWebhooks(@NotNull final WebhookClient... clients) {
        Objects.requireNonNull(clients, "Clients");
        for (final WebhookClient client : clients) {
            Objects.requireNonNull(client, "Client");
            if (client.isShutdown) {
                throw new IllegalArgumentException("One of the provided WebhookClients has been closed already!");
            }
            this.webhooks.add(client);
        }
        return this;
    }
    
    @NotNull
    public WebhookCluster addWebhooks(@NotNull final Collection<WebhookClient> clients) {
        Objects.requireNonNull(clients, "Clients");
        for (final WebhookClient client : clients) {
            Objects.requireNonNull(client, "Client");
            if (client.isShutdown) {
                throw new IllegalArgumentException("One of the provided WebhookClients has been closed already!");
            }
            this.webhooks.add(client);
        }
        return this;
    }
    
    @NotNull
    public WebhookCluster removeWebhooks(@NotNull final WebhookClient... clients) {
        Objects.requireNonNull(clients, "Clients");
        this.webhooks.removeAll(Arrays.asList(clients));
        return this;
    }
    
    @NotNull
    public WebhookCluster removeWebhooks(@NotNull final Collection<WebhookClient> clients) {
        Objects.requireNonNull(clients, "Clients");
        this.webhooks.removeAll(clients);
        return this;
    }
    
    @NotNull
    public List<WebhookClient> removeIf(@NotNull final Predicate<WebhookClient> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        final List<WebhookClient> clients = new ArrayList<WebhookClient>();
        for (final WebhookClient client : this.webhooks) {
            if (predicate.test(client)) {
                clients.add(client);
            }
        }
        this.removeWebhooks(clients);
        return clients;
    }
    
    @NotNull
    public List<WebhookClient> closeIf(@NotNull final Predicate<WebhookClient> predicate) {
        Objects.requireNonNull(predicate, "Filter");
        final List<WebhookClient> clients = new ArrayList<WebhookClient>();
        for (final WebhookClient client : this.webhooks) {
            if (predicate.test(client)) {
                clients.add(client);
            }
        }
        this.removeWebhooks(clients);
        clients.forEach(WebhookClient::close);
        return clients;
    }
    
    @NotNull
    public List<WebhookClient> getWebhooks() {
        return Collections.unmodifiableList((List<? extends WebhookClient>)new ArrayList<WebhookClient>(this.webhooks));
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> multicast(@NotNull final Predicate<WebhookClient> filter, @NotNull final WebhookMessage message) {
        Objects.requireNonNull(filter, "Filter");
        Objects.requireNonNull(message, "Message");
        final RequestBody body = message.getBody();
        final List<CompletableFuture<ReadonlyMessage>> callbacks = new ArrayList<CompletableFuture<ReadonlyMessage>>();
        for (final WebhookClient client : this.webhooks) {
            if (filter.test(client)) {
                callbacks.add(client.execute(body));
            }
        }
        return callbacks;
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final WebhookMessage message) {
        Objects.requireNonNull(message, "Message");
        RequestBody body = message.getBody();
        final List<CompletableFuture<ReadonlyMessage>> callbacks = new ArrayList<CompletableFuture<ReadonlyMessage>>(this.webhooks.size());
        for (final WebhookClient webhook : this.webhooks) {
            callbacks.add(webhook.execute(body));
            if (message.isFile()) {
                body = message.getBody();
            }
        }
        return callbacks;
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final WebhookEmbed first, @NotNull final WebhookEmbed... embeds) {
        final List<WebhookEmbed> list = new ArrayList<WebhookEmbed>(embeds.length + 2);
        list.add(first);
        Collections.addAll(list, embeds);
        return this.broadcast(list);
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final Collection<WebhookEmbed> embeds) {
        return this.webhooks.stream().map(w -> w.send((Collection)embeds)).collect((Collector<? super Object, ?, List<CompletableFuture<ReadonlyMessage>>>)Collectors.toList());
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final String content) {
        Objects.requireNonNull(content, "Content");
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content may not exceed 2000 characters!");
        }
        return this.webhooks.stream().map(w -> w.send(content)).collect((Collector<? super Object, ?, List<CompletableFuture<ReadonlyMessage>>>)Collectors.toList());
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final File file) {
        Objects.requireNonNull(file, "File");
        return this.broadcast(file.getName(), file);
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final String fileName, @NotNull final File file) {
        Objects.requireNonNull(file, "File");
        if (file.length() > 10L) {
            throw new IllegalArgumentException("Provided File exceeds the maximum size of 8MB!");
        }
        try {
            return this.broadcast(fileName, new FileInputStream(file));
        }
        catch (FileNotFoundException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final String fileName, @NotNull final InputStream data) {
        try {
            return this.broadcast(fileName, IOUtil.readAllBytes(data));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    @NotNull
    public List<CompletableFuture<ReadonlyMessage>> broadcast(@NotNull final String fileName, @NotNull final byte[] data) {
        Objects.requireNonNull(data, "Data");
        if (data.length > 10) {
            throw new IllegalArgumentException("Provided data exceeds the maximum size of 8MB!");
        }
        return this.webhooks.stream().map(w -> w.send(data, fileName)).collect((Collector<? super Object, ?, List<CompletableFuture<ReadonlyMessage>>>)Collectors.toList());
    }
    
    @Override
    public void close() {
        this.webhooks.forEach(WebhookClient::close);
        this.webhooks.clear();
    }
}
