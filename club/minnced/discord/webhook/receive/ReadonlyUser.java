//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.receive;

import org.jetbrains.annotations.*;
import org.json.*;

public class ReadonlyUser implements JSONString
{
    private final long id;
    private final short discriminator;
    private final boolean bot;
    private final String name;
    private final String avatar;
    
    public ReadonlyUser(final long id, final short discriminator, final boolean bot, @NotNull final String name, @Nullable final String avatar) {
        this.id = id;
        this.discriminator = discriminator;
        this.bot = bot;
        this.name = name;
        this.avatar = avatar;
    }
    
    public long getId() {
        return this.id;
    }
    
    public String getDiscriminator() {
        return String.format("%04d", this.discriminator);
    }
    
    public boolean isBot() {
        return this.bot;
    }
    
    @JSONPropertyName("username")
    @NotNull
    public String getName() {
        return this.name;
    }
    
    @JSONPropertyName("avatar_id")
    @Nullable
    public String getAvatarId() {
        return this.avatar;
    }
    
    @Override
    public String toString() {
        return this.toJSONString();
    }
    
    public String toJSONString() {
        return new JSONObject((Object)this).toString();
    }
}
