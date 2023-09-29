//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.render.*;
import com.lukflug.panelstudio.theme.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import com.lukflug.panelstudio.hud.*;
import com.lukflug.panelstudio.setting.*;
import java.awt.*;
import com.lemonclient.client.clickgui.*;
import net.minecraft.entity.*;
import net.minecraft.util.text.*;
import com.lemonclient.client.manager.managers.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import com.lukflug.panelstudio.base.*;

@Module.Declaration(name = "TargetHUD", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 70)
public class TargetHUD extends HUDModule
{
    IntegerSetting range;
    ColorSetting outline;
    ColorSetting background;
    private static EntityPlayer targetPlayer;
    
    public TargetHUD() {
        this.range = this.registerInteger("Range", 100, 10, 260);
        this.outline = this.registerColor("Outline", new GSColor(255, 0, 0, 255));
        this.background = this.registerColor("Background", new GSColor(0, 0, 0, 255));
    }
    
    public void populate(final ITheme theme) {
        this.component = new TargetHUDComponent(theme);
    }
    
    private static Color getNameColor(final String playerName) {
        if (SocialManager.isFriend(playerName)) {
            return (Color)new GSColor(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getFriendGSColor(), 255);
        }
        if (SocialManager.isEnemy(playerName)) {
            return (Color)new GSColor(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnemyGSColor(), 255);
        }
        return (Color)new GSColor(255, 255, 255, 255);
    }
    
    private static GSColor getHealthColor(int health) {
        if (health > 36) {
            health = 36;
        }
        if (health < 0) {
            health = 0;
        }
        final int red = (int)(255.0 - health * 7.0833);
        final int green = 255 - red;
        return new GSColor(red, green, 0, 255);
    }
    
    private static boolean isValidEntity(final EntityPlayer e) {
        return e instanceof EntityPlayer && e.getName().length() != 0 && !EntityUtil.basicChecksEntity(e) && e != TargetHUD.mc.player;
    }
    
    private static float getPing(final EntityPlayer player) {
        float ping = 0.0f;
        try {
            ping = EntityUtil.clamp((float)Objects.requireNonNull(TargetHUD.mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1.0f, 300.0f);
        }
        catch (NullPointerException ex) {}
        return ping;
    }
    
    public static boolean isRenderingEntity(final EntityPlayer entityPlayer) {
        return TargetHUD.targetPlayer == entityPlayer;
    }
    
    private class TargetHUDComponent extends HUDComponent
    {
        public TargetHUDComponent(final ITheme theme) {
            super(new Labeled(TargetHUD.this.getName(), null, () -> true), TargetHUD.this.position, TargetHUD.this.getName());
        }
        
        @Override
        public void render(final Context context) {
            super.render(context);
            if (TargetHUD.mc.world != null && TargetHUD.mc.player.ticksExisted >= 10) {
                final EntityPlayer entityPlayer = (EntityPlayer)TargetHUD.mc.world.playerEntities.stream().filter(x$0 -> isValidEntity(x$0)).map(entity -> entity).min(Comparator.comparing(c -> TargetHUD.mc.player.getDistance(c))).orElse(null);
                if (entityPlayer != null && entityPlayer.getDistance((Entity)TargetHUD.mc.player) <= (int)TargetHUD.this.range.getValue()) {
                    final Color bgcolor = (Color)new GSColor(TargetHUD.this.background.getValue(), 100);
                    context.getInterface().fillRect(context.getRect(), bgcolor, bgcolor, bgcolor, bgcolor);
                    final Color color = (Color)TargetHUD.this.outline.getValue();
                    context.getInterface().fillRect(new Rectangle(context.getPos(), new Dimension(context.getSize().width, 1)), color, color, color, color);
                    context.getInterface().fillRect(new Rectangle(context.getPos(), new Dimension(1, context.getSize().height)), color, color, color, color);
                    context.getInterface().fillRect(new Rectangle(new Point(context.getPos().x + context.getSize().width - 1, context.getPos().y), new Dimension(1, context.getSize().height)), color, color, color, color);
                    context.getInterface().fillRect(new Rectangle(new Point(context.getPos().x, context.getPos().y + context.getSize().height - 1), new Dimension(context.getSize().width, 1)), color, color, color, color);
                    TargetHUD.targetPlayer = entityPlayer;
                    LemonClientGUI.renderEntity((EntityLivingBase)entityPlayer, new Point(context.getPos().x + 35, context.getPos().y + 87 - (entityPlayer.isSneaking() ? 10 : 0)), 43);
                    TargetHUD.targetPlayer = null;
                    final String playerName = entityPlayer.getName();
                    final Color nameColor = getNameColor(playerName);
                    context.getInterface().drawString(new Point(context.getPos().x + 71, context.getPos().y + 8), 9, TextFormatting.BOLD + playerName, nameColor);
                    final int playerHealth = (int)(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount());
                    final Color healthColor = (Color)getHealthColor(playerHealth);
                    context.getInterface().drawString(new Point(context.getPos().x + 71, context.getPos().y + 20), 9, TextFormatting.WHITE + "Health: " + TextFormatting.RESET + playerHealth, healthColor);
                    context.getInterface().drawString(new Point(context.getPos().x + 71, context.getPos().y + 30), 9, "Distance: " + (int)entityPlayer.getDistance((Entity)TargetHUD.mc.player), new Color(255, 255, 255));
                    context.getInterface().drawString(new Point(context.getPos().x + 71, context.getPos().y + 40), 9, "Pop: " + TotemPopManager.INSTANCE.getPlayerPopCount(entityPlayer.getName()), new Color(255, 255, 255));
                    String info;
                    if (entityPlayer.inventory.armorItemInSlot(2).getItem().equals(Items.ELYTRA)) {
                        info = TextFormatting.LIGHT_PURPLE + "Wasp";
                    }
                    else if (entityPlayer.inventory.armorItemInSlot(2).getItem().equals(Items.DIAMOND_CHESTPLATE)) {
                        info = TextFormatting.RED + "Threat";
                    }
                    else if (entityPlayer.inventory.armorItemInSlot(3).getItem().equals(Items.AIR)) {
                        info = TextFormatting.GREEN + "NewFag";
                    }
                    else {
                        info = TextFormatting.WHITE + "None";
                    }
                    context.getInterface().drawString(new Point(context.getPos().x + 71, context.getPos().y + 50), 9, info + TextFormatting.WHITE + " | " + getPing(entityPlayer) + " ms", new Color(255, 255, 255));
                    String status = null;
                    Color statusColor = null;
                    for (final PotionEffect effect : entityPlayer.getActivePotionEffects()) {
                        if (effect.getPotion() == MobEffects.WEAKNESS) {
                            status = "Weakness!";
                            statusColor = new Color(135, 0, 25);
                        }
                        else if (effect.getPotion() == MobEffects.INVISIBILITY) {
                            status = "Invisible!";
                            statusColor = new Color(90, 90, 90);
                        }
                        else {
                            if (effect.getPotion() != MobEffects.STRENGTH) {
                                continue;
                            }
                            status = "Strength!";
                            statusColor = new Color(185, 65, 185);
                        }
                    }
                    if (status != null) {
                        context.getInterface().drawString(new Point(context.getPos().x + 71, context.getPos().y + 61), 9, TextFormatting.WHITE + "Status: " + TextFormatting.RESET + status, statusColor);
                    }
                    int xPos = context.getPos().x + 150;
                    for (final ItemStack itemStack : entityPlayer.getArmorInventoryList()) {
                        xPos -= 20;
                        LemonClientGUI.renderItem(itemStack, new Point(xPos, context.getPos().y + 73));
                    }
                }
            }
        }
        
        @Override
        public Dimension getSize(final IInterface inter) {
            return new Dimension(162, 94);
        }
    }
}
