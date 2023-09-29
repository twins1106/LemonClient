//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.send;

import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.*;
import java.io.*;

public class MessageAttachment
{
    private final String name;
    private final byte[] data;
    
    MessageAttachment(@NotNull final String name, @NotNull final byte[] data) {
        this.name = name;
        this.data = data;
    }
    
    MessageAttachment(@NotNull final String name, @NotNull final InputStream stream) throws IOException {
        this.name = name;
        try (final InputStream data = stream) {
            this.data = IOUtil.readAllBytes(data);
        }
    }
    
    MessageAttachment(@NotNull final String name, @NotNull final File file) throws IOException {
        this(name, new FileInputStream(file));
    }
    
    @NotNull
    public String getName() {
        return this.name;
    }
    
    @NotNull
    public byte[] getData() {
        return this.data;
    }
}
