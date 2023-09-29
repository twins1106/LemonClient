//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package club.minnced.discord.webhook.send;

import java.time.temporal.*;
import java.time.*;
import org.jetbrains.annotations.*;
import java.util.*;
import net.dv8tion.jda.api.entities.*;
import java.net.*;
import java.awt.*;
import java.util.function.*;
import discord4j.core.spec.*;
import discord4j.discordjson.possible.*;
import discord4j.discordjson.json.*;
import org.javacord.api.entity.message.embed.*;

public class WebhookEmbedBuilder
{
    private final List<WebhookEmbed.EmbedField> fields;
    private OffsetDateTime timestamp;
    private Integer color;
    private String description;
    private String thumbnailUrl;
    private String imageUrl;
    private WebhookEmbed.EmbedFooter footer;
    private WebhookEmbed.EmbedTitle title;
    private WebhookEmbed.EmbedAuthor author;
    
    public WebhookEmbedBuilder() {
        this.fields = new ArrayList<WebhookEmbed.EmbedField>(10);
    }
    
    public WebhookEmbedBuilder(@Nullable final WebhookEmbed embed) {
        this();
        if (embed != null) {
            this.timestamp = embed.getTimestamp();
            this.color = embed.getColor();
            this.description = embed.getDescription();
            this.thumbnailUrl = embed.getThumbnailUrl();
            this.imageUrl = embed.getImageUrl();
            this.footer = embed.getFooter();
            this.title = embed.getTitle();
            this.author = embed.getAuthor();
            this.fields.addAll(embed.getFields());
        }
    }
    
    public void reset() {
        this.fields.clear();
        this.timestamp = null;
        this.color = null;
        this.description = null;
        this.thumbnailUrl = null;
        this.imageUrl = null;
        this.footer = null;
        this.title = null;
        this.author = null;
    }
    
    @NotNull
    public WebhookEmbedBuilder setTimestamp(@Nullable final TemporalAccessor timestamp) {
        if (timestamp instanceof Instant) {
            this.timestamp = OffsetDateTime.ofInstant((Instant)timestamp, ZoneId.of("UTC"));
        }
        else {
            this.timestamp = ((timestamp == null) ? null : OffsetDateTime.from(timestamp));
        }
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setColor(@Nullable final Integer color) {
        this.color = color;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setDescription(@Nullable final String description) {
        this.description = description;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setThumbnailUrl(@Nullable final String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setImageUrl(@Nullable final String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setFooter(@Nullable final WebhookEmbed.EmbedFooter footer) {
        this.footer = footer;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setTitle(@Nullable final WebhookEmbed.EmbedTitle title) {
        this.title = title;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder setAuthor(@Nullable final WebhookEmbed.EmbedAuthor author) {
        this.author = author;
        return this;
    }
    
    @NotNull
    public WebhookEmbedBuilder addField(@NotNull final WebhookEmbed.EmbedField field) {
        if (this.fields.size() == 25) {
            throw new IllegalStateException("Cannot add more than 25 fields");
        }
        this.fields.add(Objects.requireNonNull(field));
        return this;
    }
    
    public boolean isEmpty() {
        return this.isEmpty(this.description) && this.isEmpty(this.imageUrl) && this.isEmpty(this.thumbnailUrl) && this.isFieldsEmpty() && this.isAuthorEmpty() && this.isTitleEmpty() && this.isFooterEmpty() && this.timestamp == null;
    }
    
    private boolean isEmpty(final String s) {
        return s == null || s.trim().isEmpty();
    }
    
    private boolean isTitleEmpty() {
        return this.title == null || this.isEmpty(this.title.getText());
    }
    
    private boolean isFooterEmpty() {
        return this.footer == null || this.isEmpty(this.footer.getText());
    }
    
    private boolean isAuthorEmpty() {
        return this.author == null || this.isEmpty(this.author.getName());
    }
    
    private boolean isFieldsEmpty() {
        return this.fields.isEmpty() || this.fields.stream().allMatch(f -> this.isEmpty(f.getName()) && this.isEmpty(f.getValue()));
    }
    
    @NotNull
    public WebhookEmbed build() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Cannot build an empty embed");
        }
        return new WebhookEmbed(this.timestamp, this.color, this.description, this.thumbnailUrl, this.imageUrl, this.footer, this.title, this.author, (List)new ArrayList(this.fields));
    }
    
    @NotNull
    public static WebhookEmbedBuilder fromJDA(@NotNull final MessageEmbed embed) {
        final WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
        final String url = embed.getUrl();
        final String title = embed.getTitle();
        final String description = embed.getDescription();
        final MessageEmbed.Thumbnail thumbnail = embed.getThumbnail();
        final MessageEmbed.AuthorInfo author = embed.getAuthor();
        final MessageEmbed.Footer footer = embed.getFooter();
        final MessageEmbed.ImageInfo image = embed.getImage();
        final List<MessageEmbed.Field> fields = (List<MessageEmbed.Field>)embed.getFields();
        final int color = embed.getColorRaw();
        final OffsetDateTime timestamp = embed.getTimestamp();
        if (title != null) {
            builder.setTitle(new WebhookEmbed.EmbedTitle(title, url));
        }
        if (description != null) {
            builder.setDescription(description);
        }
        if (thumbnail != null) {
            builder.setThumbnailUrl(thumbnail.getUrl());
        }
        if (author != null) {
            builder.setAuthor(new WebhookEmbed.EmbedAuthor(author.getName(), author.getIconUrl(), author.getUrl()));
        }
        if (footer != null) {
            builder.setFooter(new WebhookEmbed.EmbedFooter(footer.getText(), footer.getIconUrl()));
        }
        if (image != null) {
            builder.setImageUrl(image.getUrl());
        }
        if (!fields.isEmpty()) {
            fields.forEach(field -> builder.addField(new WebhookEmbed.EmbedField(field.isInline(), field.getName(), field.getValue())));
        }
        if (color != 536870911) {
            builder.setColor(color);
        }
        if (timestamp != null) {
            builder.setTimestamp(timestamp);
        }
        return builder;
    }
    
    @NotNull
    public static WebhookEmbedBuilder fromJavacord(@NotNull final Embed embed) {
        final WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
        embed.getTitle().ifPresent(title -> builder.setTitle(new WebhookEmbed.EmbedTitle(title, (String)embed.getUrl().map(URL::toString).orElse(null))));
        embed.getDescription().ifPresent(builder::setDescription);
        embed.getTimestamp().ifPresent(builder::setTimestamp);
        embed.getColor().map(Color::getRGB).ifPresent(builder::setColor);
        embed.getFooter().map(footer -> new WebhookEmbed.EmbedFooter((String)footer.getText().orElseThrow(NullPointerException::new), (String)footer.getIconUrl().map(URL::toString).orElse(null))).ifPresent(builder::setFooter);
        embed.getImage().map(EmbedImage::getUrl).map(URL::toString).ifPresent(builder::setImageUrl);
        embed.getThumbnail().map(EmbedThumbnail::getUrl).map(URL::toString).ifPresent(builder::setThumbnailUrl);
        embed.getFields().stream().map(field -> new WebhookEmbed.EmbedField(field.isInline(), field.getName(), field.getValue())).forEach(builder::addField);
        return builder;
    }
    
    @NotNull
    public static WebhookEmbedBuilder fromD4J(@NotNull final Consumer<? super EmbedCreateSpec> callback) {
        final EmbedCreateSpec spec = new EmbedCreateSpec();
        callback.accept(spec);
        final EmbedData data = spec.asRequest();
        return fromD4J(data);
    }
    
    @NotNull
    public static WebhookEmbedBuilder fromD4J(@NotNull final EmbedData data) {
        final WebhookEmbedBuilder builder = new WebhookEmbedBuilder();
        final Possible<String> title = (Possible<String>)data.title();
        final Possible<String> description = (Possible<String>)data.description();
        final Possible<String> url = (Possible<String>)data.url();
        final Possible<String> timestamp = (Possible<String>)data.timestamp();
        final Possible<Integer> color = (Possible<Integer>)data.color();
        final Possible<EmbedFooterData> footer = (Possible<EmbedFooterData>)data.footer();
        final Possible<EmbedImageData> image = (Possible<EmbedImageData>)data.image();
        final Possible<EmbedThumbnailData> thumbnail = (Possible<EmbedThumbnailData>)data.thumbnail();
        final Possible<EmbedAuthorData> author = (Possible<EmbedAuthorData>)data.author();
        final Possible<List<EmbedFieldData>> fields = (Possible<List<EmbedFieldData>>)data.fields();
        if (!title.isAbsent()) {
            builder.setTitle(new WebhookEmbed.EmbedTitle((String)title.get(), (String)url.toOptional().orElse(null)));
        }
        if (!description.isAbsent()) {
            builder.setDescription((String)description.get());
        }
        if (!timestamp.isAbsent()) {
            builder.setTimestamp(OffsetDateTime.parse((CharSequence)timestamp.get()));
        }
        if (!color.isAbsent()) {
            builder.setColor((Integer)color.get());
        }
        if (!footer.isAbsent()) {
            builder.setFooter(new WebhookEmbed.EmbedFooter(((EmbedFooterData)footer.get()).text(), (String)((EmbedFooterData)footer.get()).iconUrl().toOptional().orElse(null)));
        }
        if (!image.isAbsent()) {
            builder.setImageUrl((String)((EmbedImageData)image.get()).url().get());
        }
        if (!thumbnail.isAbsent()) {
            builder.setThumbnailUrl((String)((EmbedThumbnailData)thumbnail.get()).url().get());
        }
        if (!author.isAbsent()) {
            final EmbedAuthorData authorData = (EmbedAuthorData)author.get();
            builder.setAuthor(new WebhookEmbed.EmbedAuthor((String)authorData.name().get(), (String)authorData.iconUrl().toOptional().orElse(null), (String)authorData.url().toOptional().orElse(null)));
        }
        if (!fields.isAbsent()) {
            ((List)fields.get()).stream().map(field -> new WebhookEmbed.EmbedField((boolean)field.inline().toOptional().orElse(false), field.name(), field.value())).forEach(builder::addField);
        }
        return builder;
    }
}
