//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook;

import java.util.concurrent.*;
import java.io.*;
import java.util.zip.*;
import org.jetbrains.annotations.*;
import org.json.*;
import java.util.*;
import java.util.function.*;
import okhttp3.*;
import okio.*;

public class IOUtil
{
    public static final MediaType JSON;
    public static final MediaType OCTET;
    public static final byte[] EMPTY_BYTES;
    private static final CompletableFuture[] EMPTY_FUTURES;
    
    @NotNull
    public static byte[] readAllBytes(@NotNull final InputStream stream) throws IOException {
        int count = 0;
        int pos = 0;
        byte[] output = IOUtil.EMPTY_BYTES;
        final byte[] buf = new byte[1024];
        while ((count = stream.read(buf)) > 0) {
            if (pos + count >= output.length) {
                final byte[] tmp = output;
                output = new byte[pos + count];
                System.arraycopy(tmp, 0, output, 0, tmp.length);
            }
            for (int i = 0; i < count; ++i) {
                output[pos++] = buf[i];
            }
        }
        return output;
    }
    
    @Nullable
    public static InputStream getBody(@NotNull final Response req) throws IOException {
        final List<String> encoding = (List<String>)req.headers("content-encoding");
        final ResponseBody body = req.body();
        if (!encoding.isEmpty() && body != null) {
            return new GZIPInputStream(body.byteStream());
        }
        return (body != null) ? body.byteStream() : null;
    }
    
    @NotNull
    public static JSONObject toJSON(@NotNull final InputStream input) {
        return new JSONObject(new JSONTokener(input));
    }
    
    @NotNull
    public static <T> CompletableFuture<List<T>> flipFuture(@NotNull final List<CompletableFuture<T>> list) {
        final List<T> result = new ArrayList<T>(list.size());
        final List<CompletableFuture<Void>> updatedStages = new ArrayList<CompletableFuture<Void>>(list.size());
        list.stream().map(it -> it.thenAccept((Consumer)result::add)).forEach(updatedStages::add);
        final CompletableFuture<Void> tracker = CompletableFuture.allOf((CompletableFuture<?>[])updatedStages.toArray(IOUtil.EMPTY_FUTURES));
        final CompletableFuture<List<T>> future = new CompletableFuture<List<T>>();
        tracker.thenRun(() -> future.complete(result)).exceptionally(e -> {
            future.completeExceptionally(e);
            return null;
        });
        return future;
    }
    
    static {
        JSON = MediaType.parse("application/json; charset=utf-8");
        OCTET = MediaType.parse("application/octet-stream; charset=utf-8");
        EMPTY_BYTES = new byte[0];
        EMPTY_FUTURES = new CompletableFuture[0];
    }
    
    public static class Lazy
    {
        private final SilentSupplier<?> supply;
        
        public Lazy(final SilentSupplier<?> supply) {
            this.supply = supply;
        }
        
        @NotNull
        @Override
        public String toString() {
            try {
                return String.valueOf(this.supply.get());
            }
            catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
    
    public static class OctetBody extends RequestBody
    {
        private final byte[] data;
        
        public OctetBody(@NotNull final byte[] data) {
            this.data = data;
        }
        
        public MediaType contentType() {
            return IOUtil.OCTET;
        }
        
        public void writeTo(final BufferedSink sink) throws IOException {
            sink.write(this.data);
        }
    }
    
    public interface SilentSupplier<T>
    {
        @Nullable
        T get() throws Exception;
    }
}
