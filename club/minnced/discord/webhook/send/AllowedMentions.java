//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.send;

import org.jetbrains.annotations.*;
import java.util.*;
import org.json.*;

public class AllowedMentions implements JSONString
{
    private boolean parseRoles;
    private boolean parseUsers;
    private boolean parseEveryone;
    private final Set<String> users;
    private final Set<String> roles;
    
    public AllowedMentions() {
        this.users = new HashSet<String>();
        this.roles = new HashSet<String>();
    }
    
    public static AllowedMentions all() {
        return new AllowedMentions().withParseEveryone(true).withParseRoles(true).withParseUsers(true);
    }
    
    public static AllowedMentions none() {
        return new AllowedMentions().withParseEveryone(false).withParseRoles(false).withParseUsers(false);
    }
    
    @NotNull
    public AllowedMentions withUsers(@NotNull final String... userId) {
        Collections.addAll(this.users, userId);
        this.parseUsers = false;
        return this;
    }
    
    @NotNull
    public AllowedMentions withRoles(@NotNull final String... roleId) {
        Collections.addAll(this.roles, roleId);
        this.parseRoles = false;
        return this;
    }
    
    @NotNull
    public AllowedMentions withUsers(@NotNull final Collection<String> userId) {
        this.users.addAll(userId);
        this.parseUsers = false;
        return this;
    }
    
    @NotNull
    public AllowedMentions withRoles(@NotNull final Collection<String> roleId) {
        this.roles.addAll(roleId);
        this.parseRoles = false;
        return this;
    }
    
    @NotNull
    public AllowedMentions withParseEveryone(final boolean allowEveryoneMention) {
        this.parseEveryone = allowEveryoneMention;
        return this;
    }
    
    @NotNull
    public AllowedMentions withParseUsers(final boolean allowParseUsers) {
        this.parseUsers = allowParseUsers;
        if (this.parseUsers) {
            this.users.clear();
        }
        return this;
    }
    
    @NotNull
    public AllowedMentions withParseRoles(final boolean allowParseRoles) {
        this.parseRoles = allowParseRoles;
        if (this.parseRoles) {
            this.roles.clear();
        }
        return this;
    }
    
    public String toJSONString() {
        final JSONObject json = new JSONObject();
        json.put("parse", (Object)new JSONArray());
        if (!this.users.isEmpty()) {
            json.put("users", (Collection)this.users);
        }
        else if (this.parseUsers) {
            json.accumulate("parse", (Object)"users");
        }
        if (!this.roles.isEmpty()) {
            json.put("roles", (Collection)this.roles);
        }
        else if (this.parseRoles) {
            json.accumulate("parse", (Object)"roles");
        }
        if (this.parseEveryone) {
            json.accumulate("parse", (Object)"everyone");
        }
        return json.toString();
    }
}
