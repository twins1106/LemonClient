//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.receive;

import org.jetbrains.annotations.*;
import club.minnced.discord.webhook.send.*;
import java.time.*;
import org.json.*;
import java.util.function.*;
import java.util.*;

public class EntityFactory
{
    @NotNull
    public static ReadonlyUser makeUser(@NotNull final JSONObject json) {
        final long id = Long.parseUnsignedLong(json.getString("id"));
        final String name = json.getString("username");
        final String avatar = json.optString("avatar", (String)null);
        final short discriminator = Short.parseShort(json.getString("discriminator"));
        final boolean bot = !json.isNull("bot") && json.getBoolean("bot");
        return new ReadonlyUser(id, discriminator, bot, name, avatar);
    }
    
    @NotNull
    public static ReadonlyAttachment makeAttachment(@NotNull final JSONObject json) {
        final String url = json.getString("url");
        final String proxy = json.getString("proxy_url");
        final String name = json.getString("filename");
        final int size = json.getInt("size");
        final int width = json.optInt("width", -1);
        final int height = json.optInt("height", -1);
        final long id = Long.parseUnsignedLong(json.getString("id"));
        return new ReadonlyAttachment(url, proxy, name, width, height, size, id);
    }
    
    @Nullable
    public static WebhookEmbed.EmbedField makeEmbedField(@Nullable final JSONObject json) {
        if (json == null) {
            return null;
        }
        final String name = json.getString("name");
        final String value = json.getString("value");
        final boolean inline = !json.isNull("inline") && json.getBoolean("inline");
        return new WebhookEmbed.EmbedField(inline, name, value);
    }
    
    @Nullable
    public static WebhookEmbed.EmbedAuthor makeEmbedAuthor(@Nullable final JSONObject json) {
        if (json == null) {
            return null;
        }
        final String name = json.getString("name");
        final String url = json.optString("url", (String)null);
        final String icon = json.optString("icon_url", (String)null);
        return new WebhookEmbed.EmbedAuthor(name, icon, url);
    }
    
    @Nullable
    public static WebhookEmbed.EmbedFooter makeEmbedFooter(@Nullable final JSONObject json) {
        if (json == null) {
            return null;
        }
        final String text = json.getString("text");
        final String icon = json.optString("icon_url", (String)null);
        return new WebhookEmbed.EmbedFooter(text, icon);
    }
    
    @Nullable
    public static WebhookEmbed.EmbedTitle makeEmbedTitle(@NotNull final JSONObject json) {
        final String text = json.optString("title", (String)null);
        if (text == null) {
            return null;
        }
        final String url = json.optString("url", (String)null);
        return new WebhookEmbed.EmbedTitle(text, url);
    }
    
    @Nullable
    public static ReadonlyEmbed.EmbedImage makeEmbedImage(@Nullable final JSONObject json) {
        if (json == null) {
            return null;
        }
        final String url = json.getString("url");
        final String proxyUrl = json.getString("proxy_url");
        final int width = json.getInt("width");
        final int height = json.getInt("height");
        return new ReadonlyEmbed.EmbedImage(url, proxyUrl, width, height);
    }
    
    @Nullable
    public static ReadonlyEmbed.EmbedProvider makeEmbedProvider(@Nullable final JSONObject json) {
        if (json == null) {
            return null;
        }
        final String url = json.getString("url");
        final String name = json.getString("name");
        return new ReadonlyEmbed.EmbedProvider(name, url);
    }
    
    @Nullable
    public static ReadonlyEmbed.EmbedVideo makeEmbedVideo(@Nullable final JSONObject json) {
        if (json == null) {
            return null;
        }
        final String url = json.getString("url");
        final int height = json.getInt("height");
        final int width = json.getInt("width");
        return new ReadonlyEmbed.EmbedVideo(url, width, height);
    }
    
    @NotNull
    public static ReadonlyEmbed makeEmbed(@NotNull final JSONObject json) {
        final String description = json.optString("description", (String)null);
        final Integer color = json.isNull("color") ? null : Integer.valueOf(json.getInt("color"));
        final ReadonlyEmbed.EmbedImage image = makeEmbedImage(json.optJSONObject("image"));
        final ReadonlyEmbed.EmbedImage thumbnail = makeEmbedImage(json.optJSONObject("thumbnail"));
        final ReadonlyEmbed.EmbedProvider provider = makeEmbedProvider(json.optJSONObject("provider"));
        final ReadonlyEmbed.EmbedVideo video = makeEmbedVideo(json.optJSONObject("video"));
        final WebhookEmbed.EmbedFooter footer = makeEmbedFooter(json.optJSONObject("footer"));
        final WebhookEmbed.EmbedAuthor author = makeEmbedAuthor(json.optJSONObject("author"));
        final WebhookEmbed.EmbedTitle title = makeEmbedTitle(json);
        OffsetDateTime timestamp;
        if (json.isNull("timestamp")) {
            timestamp = null;
        }
        else {
            timestamp = OffsetDateTime.parse(json.getString("timestamp"));
        }
        final JSONArray fieldArray = json.optJSONArray("fields");
        final List<WebhookEmbed.EmbedField> fields = new ArrayList<WebhookEmbed.EmbedField>();
        if (fieldArray != null) {
            for (int i = 0; i < fieldArray.length(); ++i) {
                final JSONObject obj = fieldArray.getJSONObject(i);
                final WebhookEmbed.EmbedField field = makeEmbedField(obj);
                if (field != null) {
                    fields.add(field);
                }
            }
        }
        return new ReadonlyEmbed(timestamp, color, description, thumbnail, image, footer, title, author, fields, provider, video);
    }
    
    @NotNull
    public static ReadonlyMessage makeMessage(@NotNull final JSONObject json) {
        final long id = Long.parseUnsignedLong(json.getString("id"));
        final long channelId = Long.parseUnsignedLong(json.getString("channel_id"));
        final ReadonlyUser author = makeUser(json.getJSONObject("author"));
        final String content = json.getString("content");
        final boolean tts = json.getBoolean("tts");
        final boolean mentionEveryone = json.getBoolean("mention_everyone");
        final JSONArray usersArray = json.getJSONArray("mentions");
        final JSONArray rolesArray = json.getJSONArray("mention_roles");
        final JSONArray embedArray = json.getJSONArray("embeds");
        final JSONArray attachmentArray = json.getJSONArray("attachments");
        final List<ReadonlyUser> mentionedUsers = convertToList(usersArray, EntityFactory::makeUser);
        final List<ReadonlyEmbed> embeds = convertToList(embedArray, EntityFactory::makeEmbed);
        final List<ReadonlyAttachment> attachments = convertToList(attachmentArray, EntityFactory::makeAttachment);
        final List<Long> mentionedRoles = new ArrayList<Long>();
        for (int i = 0; i < rolesArray.length(); ++i) {
            mentionedRoles.add(Long.parseUnsignedLong(rolesArray.getString(i)));
        }
        return new ReadonlyMessage(id, channelId, mentionEveryone, tts, author, content, embeds, attachments, mentionedUsers, mentionedRoles);
    }
    
    private static <T> List<T> convertToList(final JSONArray arr, final Function<JSONObject, T> converter) {
        if (arr == null) {
            return Collections.emptyList();
        }
        final List<T> list = new ArrayList<T>();
        for (int i = 0; i < arr.length(); ++i) {
            final JSONObject json = arr.getJSONObject(i);
            final T out = converter.apply(json);
            if (out != null) {
                list.add(out);
            }
        }
        return Collections.unmodifiableList((List<? extends T>)list);
    }
}
