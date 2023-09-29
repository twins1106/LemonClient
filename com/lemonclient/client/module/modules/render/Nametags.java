//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.util.text.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.font.*;
import net.minecraft.client.renderer.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;

@Module.Declaration(name = "Nametags", category = Category.Render)
public class Nametags extends Module
{
    IntegerSetting range;
    BooleanSetting renderSelf;
    BooleanSetting showDurability;
    BooleanSetting showItems;
    BooleanSetting showEnchantName;
    BooleanSetting showItemName;
    BooleanSetting showGameMode;
    BooleanSetting showHealth;
    BooleanSetting showPing;
    BooleanSetting showTotem;
    BooleanSetting showEntityID;
    ModeSetting levelColor;
    public BooleanSetting customColor;
    public ColorSetting borderColor;
    
    public Nametags() {
        this.range = this.registerInteger("Range", 100, 10, 260);
        this.renderSelf = this.registerBoolean("Render Self", false);
        this.showDurability = this.registerBoolean("Durability", true);
        this.showItems = this.registerBoolean("Items", true);
        this.showEnchantName = this.registerBoolean("Enchants", true);
        this.showItemName = this.registerBoolean("Item Name", false);
        this.showGameMode = this.registerBoolean("Gamemode", false);
        this.showHealth = this.registerBoolean("Health", true);
        this.showPing = this.registerBoolean("Ping", false);
        this.showTotem = this.registerBoolean("Totem Pops", true);
        this.showEntityID = this.registerBoolean("Entity Id", false);
        this.levelColor = this.registerMode("Level Color", ColorUtil.colors, "Green");
        this.customColor = this.registerBoolean("Custom Color", true);
        this.borderColor = this.registerColor("Border Color", new GSColor(255, 0, 0, 255));
    }
    
    public void onWorldRender(final RenderEvent event) {
        if (Nametags.mc.player == null || Nametags.mc.world == null) {
            return;
        }
        final Vec3d vec3d;
        Nametags.mc.world.playerEntities.stream().filter(this::shouldRender).forEach(entityPlayer -> {
            vec3d = this.findEntityVec3d(entityPlayer);
            this.renderNameTags(entityPlayer, vec3d.x, vec3d.y, vec3d.z);
        });
    }
    
    private boolean shouldRender(final EntityPlayer entityPlayer) {
        return (entityPlayer != Nametags.mc.player || (boolean)this.renderSelf.getValue()) && entityPlayer.getName().length() != 0 && !entityPlayer.isDead && entityPlayer.getHealth() > 0.0f && entityPlayer.getDistance((Entity)Nametags.mc.player) <= (int)this.range.getValue();
    }
    
    private Vec3d findEntityVec3d(final EntityPlayer entityPlayer) {
        final double posX = this.balancePosition(entityPlayer.posX, entityPlayer.lastTickPosX);
        final double posY = this.balancePosition(entityPlayer.posY, entityPlayer.lastTickPosY);
        final double posZ = this.balancePosition(entityPlayer.posZ, entityPlayer.lastTickPosZ);
        return new Vec3d(posX, posY, posZ);
    }
    
    private double balancePosition(final double newPosition, final double oldPosition) {
        return oldPosition + (newPosition - oldPosition) * Nametags.mc.timer.renderPartialTicks;
    }
    
    private void renderNameTags(final EntityPlayer entityPlayer, final double posX, final double posY, final double posZ) {
        final double adjustedY = posY + (entityPlayer.isSneaking() ? 1.9 : 2.1);
        final String[] name = { this.buildEntityNameString(entityPlayer) };
        RenderUtil.drawNametag(posX, adjustedY, posZ, name, this.findTextColor(entityPlayer), 2);
        this.renderItemsAndArmor(entityPlayer, 0, 0);
        GlStateManager.popMatrix();
    }
    
    private String buildEntityNameString(final EntityPlayer entityPlayer) {
        String name = entityPlayer.getName();
        if (this.showEntityID.getValue()) {
            name = name + " ID: " + entityPlayer.getEntityId();
        }
        if (this.showGameMode.getValue()) {
            if (entityPlayer.isCreative()) {
                name += " [C]";
            }
            else if (entityPlayer.isSpectator()) {
                name += " [I]";
            }
            else {
                name += " [S]";
            }
        }
        if (this.showTotem.getValue()) {
            name = name + " [" + TotemPopManager.INSTANCE.getPlayerPopCount(entityPlayer.getName()) + "]";
        }
        if (this.showPing.getValue()) {
            int value = 0;
            if (Nametags.mc.getConnection() != null && Nametags.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) != null) {
                value = Nametags.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()).getResponseTime();
            }
            name = name + " " + value + "ms";
        }
        if (this.showHealth.getValue()) {
            final int health = (int)(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount());
            final TextFormatting textFormatting = this.findHealthColor(health);
            name = name + " " + textFormatting + health;
        }
        return name;
    }
    
    private TextFormatting findHealthColor(final int health) {
        if (health <= 0) {
            return TextFormatting.DARK_RED;
        }
        if (health <= 5) {
            return TextFormatting.RED;
        }
        if (health <= 10) {
            return TextFormatting.GOLD;
        }
        if (health <= 15) {
            return TextFormatting.YELLOW;
        }
        if (health <= 20) {
            return TextFormatting.DARK_GREEN;
        }
        return TextFormatting.GREEN;
    }
    
    private GSColor findTextColor(final EntityPlayer entityPlayer) {
        final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
        if (SocialManager.isFriend(entityPlayer.getName())) {
            return colorMain.getFriendGSColor();
        }
        if (SocialManager.isEnemy(entityPlayer.getName())) {
            return colorMain.getEnemyGSColor();
        }
        if (entityPlayer.isInvisible()) {
            return new GSColor(128, 128, 128);
        }
        if (Nametags.mc.getConnection() != null && Nametags.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) == null) {
            return new GSColor(239, 1, 71);
        }
        if (entityPlayer.isSneaking()) {
            return new GSColor(255, 153, 0);
        }
        return new GSColor(255, 255, 255);
    }
    
    private void renderItemsAndArmor(final EntityPlayer entityPlayer, int posX, int posY) {
        final ItemStack mainHandItem = entityPlayer.getHeldItemMainhand();
        final ItemStack offHandItem = entityPlayer.getHeldItemOffhand();
        int armorCount = 3;
        for (int i = 0; i <= 3; ++i) {
            final ItemStack itemStack = (ItemStack)entityPlayer.inventory.armorInventory.get(armorCount);
            if (!itemStack.isEmpty()) {
                posX -= 8;
                final int size = EnchantmentHelper.getEnchantments(itemStack).size();
                if ((boolean)this.showItems.getValue() && size > posY) {
                    posY = size;
                }
            }
            --armorCount;
        }
        if (!mainHandItem.isEmpty() && ((boolean)this.showItems.getValue() || ((boolean)this.showDurability.getValue() && offHandItem.isItemStackDamageable()))) {
            posX -= 8;
            final int enchantSize = EnchantmentHelper.getEnchantments(offHandItem).size();
            if ((boolean)this.showItems.getValue() && enchantSize > posY) {
                posY = enchantSize;
            }
        }
        if (!mainHandItem.isEmpty()) {
            final int enchantSize = EnchantmentHelper.getEnchantments(mainHandItem).size();
            if ((boolean)this.showItems.getValue() && enchantSize > posY) {
                posY = enchantSize;
            }
            int armorY = this.findArmorY(posY);
            if ((boolean)this.showItems.getValue() || ((boolean)this.showDurability.getValue() && mainHandItem.isItemStackDamageable())) {
                posX -= 8;
            }
            if (this.showItems.getValue()) {
                this.renderItem(mainHandItem, posX, armorY, posY);
                armorY -= 32;
            }
            if ((boolean)this.showDurability.getValue() && mainHandItem.isItemStackDamageable()) {
                this.renderItemDurability(mainHandItem, posX, armorY);
            }
            final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
            armorY -= (colorMain.customFont.getValue() ? FontUtil.getFontHeight((boolean)colorMain.customFont.getValue()) : Nametags.mc.fontRenderer.FONT_HEIGHT);
            if (this.showItemName.getValue()) {
                this.renderItemName(mainHandItem, armorY);
            }
            if ((boolean)this.showItems.getValue() || ((boolean)this.showDurability.getValue() && mainHandItem.isItemStackDamageable())) {
                posX += 16;
            }
        }
        int armorCount2 = 3;
        for (int j = 0; j <= 3; ++j) {
            final ItemStack itemStack2 = (ItemStack)entityPlayer.inventory.armorInventory.get(armorCount2);
            if (!itemStack2.isEmpty()) {
                int armorY2 = this.findArmorY(posY);
                if (this.showItems.getValue()) {
                    this.renderItem(itemStack2, posX, armorY2, posY);
                    armorY2 -= 32;
                }
                if ((boolean)this.showDurability.getValue() && itemStack2.isItemStackDamageable()) {
                    this.renderItemDurability(itemStack2, posX, armorY2);
                }
                posX += 16;
            }
            --armorCount2;
        }
        if (!offHandItem.isEmpty()) {
            int armorY = this.findArmorY(posY);
            if (this.showItems.getValue()) {
                this.renderItem(offHandItem, posX, armorY, posY);
                armorY -= 32;
            }
            if ((boolean)this.showDurability.getValue() && offHandItem.isItemStackDamageable()) {
                this.renderItemDurability(offHandItem, posX, armorY);
            }
        }
    }
    
    private int findArmorY(final int posY) {
        int posY2 = this.showItems.getValue() ? -26 : -27;
        if (posY > 4) {
            posY2 -= (posY - 4) * 8;
        }
        return posY2;
    }
    
    private void renderItemName(final ItemStack itemStack, final int posY) {
        GlStateManager.enableTexture2D();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
        FontUtil.drawStringWithShadow((boolean)colorMain.customFont.getValue(), itemStack.getDisplayName(), -FontUtil.getStringWidth((boolean)colorMain.customFont.getValue(), itemStack.getDisplayName()) / 2, posY, new GSColor(255, 255, 255));
        GlStateManager.popMatrix();
        GlStateManager.disableTexture2D();
    }
    
    private void renderItemDurability(final ItemStack itemStack, final int posX, final int posY) {
        float green;
        final float damagePercent = green = (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (float)itemStack.getMaxDamage();
        if (green > 1.0f) {
            green = 1.0f;
        }
        else if (green < 0.0f) {
            green = 0.0f;
        }
        final float red = 1.0f - green;
        GlStateManager.enableTexture2D();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
        FontUtil.drawStringWithShadow((boolean)colorMain.customFont.getValue(), (int)(damagePercent * 100.0f) + "%", posX * 2, posY, new GSColor((int)(red * 255.0f), (int)(green * 255.0f), 0));
        GlStateManager.popMatrix();
        GlStateManager.disableTexture2D();
    }
    
    private void renderItem(final ItemStack itemStack, final int posX, final int posY, final int posY2) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        final int posY3 = (posY2 > 4) ? ((posY2 - 4) * 8 / 2) : 0;
        Nametags.mc.getRenderItem().zLevel = -150.0f;
        RenderHelper.enableStandardItemLighting();
        Nametags.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, posX, posY + posY3);
        Nametags.mc.getRenderItem().renderItemOverlays(Nametags.mc.fontRenderer, itemStack, posX, posY + posY3);
        RenderHelper.disableStandardItemLighting();
        Nametags.mc.getRenderItem().zLevel = 0.0f;
        RenderUtil.prepare();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5);
        this.renderEnchants(itemStack, posX, posY - 24);
        GlStateManager.popMatrix();
    }
    
    private void renderEnchants(final ItemStack itemStack, final int posX, int posY) {
        GlStateManager.enableTexture2D();
        for (final Enchantment enchantment : EnchantmentHelper.getEnchantments(itemStack).keySet()) {
            if (enchantment == null) {
                continue;
            }
            if (this.showEnchantName.getValue()) {
                final int level = EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack);
                final ColorMain colorMain = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
                FontUtil.drawStringWithShadow((boolean)colorMain.customFont.getValue(), this.findStringForEnchants(enchantment, level), posX * 2, posY, new GSColor(255, 255, 255));
            }
            posY += 8;
        }
        if (itemStack.getItem().equals(Items.GOLDEN_APPLE) && itemStack.hasEffect()) {
            final ColorMain colorMain2 = (ColorMain)ModuleManager.getModule((Class)ColorMain.class);
            FontUtil.drawStringWithShadow((boolean)colorMain2.customFont.getValue(), "God", posX * 2, posY, new GSColor(195, 77, 65));
        }
        GlStateManager.disableTexture2D();
    }
    
    private String findStringForEnchants(final Enchantment enchantment, final int level) {
        final ResourceLocation resourceLocation = (ResourceLocation)Enchantment.REGISTRY.getNameForObject((Object)enchantment);
        String string = (resourceLocation == null) ? enchantment.getName() : resourceLocation.toString();
        final int charCount = (level > 1) ? 12 : 13;
        if (string.length() > charCount) {
            string = string.substring(10, charCount);
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1) + ColorUtil.settingToTextFormatting(this.levelColor) + ((level > 1) ? Integer.valueOf(level) : "");
    }
}
