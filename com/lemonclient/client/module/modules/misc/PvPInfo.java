//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.manager.managers.*;
import java.util.stream.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.api.util.misc.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

@Module.Declaration(name = "PvPInfo", category = Category.Misc)
public class PvPInfo extends Module
{
    BooleanSetting visualRange;
    BooleanSetting coords;
    BooleanSetting pearlAlert;
    BooleanSetting strengthDetect;
    BooleanSetting weaknessDetect;
    BooleanSetting popCounter;
    BooleanSetting friend;
    BooleanSetting sharp32;
    ModeSetting type;
    ModeSetting type1;
    ModeSetting type2;
    ModeSetting type3;
    ModeSetting type4;
    ModeSetting type5;
    ModeSetting self;
    ModeSetting chatColor;
    ModeSetting nameColor;
    ModeSetting friColor;
    ModeSetting numberColor;
    List<Entity> knownPlayers;
    List<Entity> antiPearlList;
    List<Entity> players;
    List<Entity> pearls;
    private final Set<EntityPlayer> strengthPlayers;
    private final Set<EntityPlayer> weaknessPlayers;
    private final Set<EntityPlayer> sword;
    
    public PvPInfo() {
        this.visualRange = this.registerBoolean("Visual Range", false);
        this.coords = this.registerBoolean("Coords", true);
        this.pearlAlert = this.registerBoolean("Pearl Alert", false);
        this.strengthDetect = this.registerBoolean("Strength Detect", false);
        this.weaknessDetect = this.registerBoolean("Weakness Detect", false);
        this.popCounter = this.registerBoolean("Pop Counter", false);
        this.friend = this.registerBoolean("My Friend", false);
        this.sharp32 = this.registerBoolean("sharp32", true);
        this.type = this.registerMode("Visual Type", (List)Arrays.asList("Friend", "Enemy", "All"), "All");
        this.type1 = this.registerMode("Pearl Type", (List)Arrays.asList("Friend", "Enemy", "All"), "All");
        this.type2 = this.registerMode("Strength Type", (List)Arrays.asList("Friend", "Enemy", "All"), "All");
        this.type3 = this.registerMode("Weakness Type", (List)Arrays.asList("Friend", "Enemy", "All"), "All");
        this.type4 = this.registerMode("Pop Type", (List)Arrays.asList("Friend", "Enemy", "All"), "All");
        this.type5 = this.registerMode("32k Type", (List)Arrays.asList("Friend", "Enemy", "All"), "All");
        this.self = this.registerMode("Self", (List)Arrays.asList("I", "Name", "Disable"), "Name");
        this.chatColor = this.registerMode("Color", ColorUtil.colors, "Light Purple");
        this.nameColor = this.registerMode("Name Color", ColorUtil.colors, "Light Purple");
        this.friColor = this.registerMode("Friend Color", ColorUtil.colors, "Light Purple");
        this.numberColor = this.registerMode("Number Color", ColorUtil.colors, "Light Purple");
        this.knownPlayers = new ArrayList<Entity>();
        this.antiPearlList = new ArrayList<Entity>();
        this.strengthPlayers = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
        this.weaknessPlayers = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
        this.sword = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
    }
    
    public void onUpdate() {
        if (PvPInfo.mc.player == null || PvPInfo.mc.world == null) {
            return;
        }
        TotemPopManager.INSTANCE.sendMsgs = (boolean)this.popCounter.getValue();
        if (this.popCounter.getValue()) {
            TotemPopManager.INSTANCE.chatFormatting = ColorUtil.textToChatFormatting(this.chatColor);
            TotemPopManager.INSTANCE.nameFormatting = ColorUtil.textToChatFormatting(this.nameColor);
            TotemPopManager.INSTANCE.friFormatting = ColorUtil.textToChatFormatting(this.friColor);
            TotemPopManager.INSTANCE.numberFormatting = ColorUtil.textToChatFormatting(this.numberColor);
            TotemPopManager.INSTANCE.friend = (boolean)this.friend.getValue();
            TotemPopManager.INSTANCE.self = (String)this.self.getValue();
            TotemPopManager.INSTANCE.type4 = (String)this.type4.getValue();
        }
        if (this.visualRange.getValue()) {
            this.players = (List<Entity>)PvPInfo.mc.world.playerEntities.stream().filter(entity -> !entity.getName().equals(PvPInfo.mc.player.getName())).collect(Collectors.toList());
            try {
                for (final Entity e2 : this.players) {
                    if (e2.getName().equalsIgnoreCase("fakeplayer")) {
                        continue;
                    }
                    if (this.knownPlayers.contains(e2)) {
                        continue;
                    }
                    this.knownPlayers.add(e2);
                    final String xyz = this.coords.getValue() ? (" at x:" + (int)e2.posX + " y:" + (int)e2.posY + " z:" + (int)e2.posZ) : "";
                    final String name = e2.getName();
                    if (name.equals("") || name.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name) && !((String)this.type.getValue()).equals("Enemy")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.chatColor) + "Found (" + ColorUtil.textToChatFormatting(this.friColor) + name + ColorUtil.textToChatFormatting(this.chatColor) + ")" + xyz, "VisualRange" + name, 2000);
                    }
                    if (SocialManager.isFriend(name) || ((String)this.type.getValue()).equals("Friend")) {
                        continue;
                    }
                    MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.chatColor) + "Found (" + ColorUtil.textToChatFormatting(this.nameColor) + name + ColorUtil.textToChatFormatting(this.chatColor) + ")" + xyz, "VisualRange" + name, 2000);
                }
            }
            catch (Exception ex) {}
            try {
                for (final Entity e2 : this.knownPlayers) {
                    if (e2.getName().equalsIgnoreCase("fakeplayer")) {
                        continue;
                    }
                    if (this.players.contains(e2)) {
                        continue;
                    }
                    this.knownPlayers.remove(e2);
                    final String xyz = this.coords.getValue() ? (" at x:" + (int)e2.posX + " y:" + (int)e2.posY + " z:" + (int)e2.posZ) : "";
                    final String name = e2.getName();
                    if (name.equals("") || name.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name) && !((String)this.type.getValue()).equals("Enemy")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.chatColor) + "Gone (" + ColorUtil.textToChatFormatting(this.friColor) + name + ColorUtil.textToChatFormatting(this.chatColor) + ")" + xyz, "VisualRange" + name, 2000);
                    }
                    if (SocialManager.isFriend(name) || ((String)this.type.getValue()).equals("Friend")) {
                        continue;
                    }
                    MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.chatColor) + "Gone (" + ColorUtil.textToChatFormatting(this.nameColor) + name + ColorUtil.textToChatFormatting(this.chatColor) + ")" + xyz, "VisualRange" + name, 2000);
                }
            }
            catch (Exception ex2) {}
        }
        if (this.pearlAlert.getValue()) {
            this.pearls = (List<Entity>)PvPInfo.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderPearl).collect(Collectors.toList());
            try {
                for (final Entity e2 : this.pearls) {
                    if (e2 instanceof EntityEnderPearl) {
                        if (e2.getEntityWorld().getClosestPlayerToEntity(e2, 3.0).getName().equalsIgnoreCase("fakeplayer")) {
                            continue;
                        }
                        if (this.antiPearlList.contains(e2)) {
                            continue;
                        }
                        this.antiPearlList.add(e2);
                        String faceing = e2.getHorizontalFacing().toString();
                        if (faceing.equals("west")) {
                            faceing = "east";
                        }
                        else if (faceing.equals("east")) {
                            faceing = "west";
                        }
                        if (PvPInfo.mc.player.getName().equals(e2.getEntityWorld().getClosestPlayerToEntity(e2, 3.0).getName()) && ((String)this.self.getValue()).equals("Disable")) {
                            return;
                        }
                        final String name = (e2.getEntityWorld().getClosestPlayerToEntity(e2, 3.0).getName().equals(PvPInfo.mc.player.getName()) && ((String)this.self.getValue()).equals("I")) ? "I" : e2.getEntityWorld().getClosestPlayerToEntity(e2, 3.0).getName();
                        if (name.equals("") || name.equals(" ")) {
                            return;
                        }
                        if (SocialManager.isFriend(name) && !((String)this.type1.getValue()).equals("Enemy")) {
                            MessageBus.sendClientPrefixMessage(ColorUtil.textToChatFormatting(this.friColor) + name + ColorUtil.textToChatFormatting(this.chatColor) + " has just thrown a pearl! (" + faceing + ")");
                        }
                        if (SocialManager.isFriend(name) || ((String)this.type1.getValue()).equals("Friend")) {
                            continue;
                        }
                        MessageBus.sendClientPrefixMessage(ColorUtil.textToChatFormatting(this.nameColor) + name + ColorUtil.textToChatFormatting(this.chatColor) + " has just thrown a pearl! (" + faceing + ")");
                    }
                }
            }
            catch (Exception ex3) {}
        }
        if (this.strengthDetect.getValue()) {
            for (final EntityPlayer player : PvPInfo.mc.world.playerEntities) {
                if (player.getName().equalsIgnoreCase("fakeplayer")) {
                    continue;
                }
                if (player.isPotionActive(MobEffects.STRENGTH) && !this.strengthPlayers.contains(player)) {
                    if (PvPInfo.mc.player.getName().equals(player.getName()) && ((String)this.self.getValue()).equals("Disable")) {
                        return;
                    }
                    final String name2 = (player.getName().equals(PvPInfo.mc.player.getName()) && ((String)this.self.getValue()).equals("I")) ? "I" : player.getName();
                    if (name2.equals("") || name2.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name2) && !((String)this.type2.getValue()).equals("Enemy")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.friColor) + name2 + ColorUtil.textToChatFormatting(this.chatColor) + " has drank strength", "Strength" + name2, 2000);
                    }
                    if (!SocialManager.isFriend(name2) && !((String)this.type2.getValue()).equals("Friend")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + ChatFormatting.RED + " has drank strength", "Strength" + name2, 2000);
                    }
                    this.strengthPlayers.add(player);
                }
                if (!this.strengthPlayers.contains(player)) {
                    continue;
                }
                if (player.isPotionActive(MobEffects.STRENGTH)) {
                    continue;
                }
                if (PvPInfo.mc.player.getName().equals(player.getName()) && ((String)this.self.getValue()).equals("Disable")) {
                    return;
                }
                final String name2 = (player.getName().equals(PvPInfo.mc.player.getName()) && ((String)this.self.getValue()).equals("I")) ? "I" : player.getName();
                if (name2.equals("") || name2.equals(" ")) {
                    return;
                }
                if (SocialManager.isFriend(name2) && !((String)this.type2.getValue()).equals("Enemy")) {
                    MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.friColor) + name2 + ColorUtil.textToChatFormatting(this.chatColor) + " no longer has strength", "Strength" + name2, 2000);
                }
                if (!SocialManager.isFriend(name2) && !((String)this.type2.getValue()).equals("Friend")) {
                    MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + ChatFormatting.GREEN + " no longer has strength", "Strength" + name2, 2000);
                }
                this.strengthPlayers.remove(player);
            }
        }
        if (this.weaknessDetect.getValue()) {
            for (final EntityPlayer player : PvPInfo.mc.world.playerEntities) {
                if (player.getName().equalsIgnoreCase("fakeplayer")) {
                    continue;
                }
                if (player.isPotionActive(MobEffects.WEAKNESS) && !this.weaknessPlayers.contains(player)) {
                    if (PvPInfo.mc.player.getName().equals(player.getName()) && ((String)this.self.getValue()).equals("Disable")) {
                        return;
                    }
                    final String name2 = (player.getName().equals(PvPInfo.mc.player.getName()) && ((String)this.self.getValue()).equals("I")) ? "I" : player.getName();
                    if (name2.equals("") || name2.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name2) && !((String)this.type3.getValue()).equals("Enemy")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.friColor) + name2 + ColorUtil.textToChatFormatting(this.chatColor) + " has drank weekness", "Weakness" + name2, 2000);
                    }
                    if (!SocialManager.isFriend(name2) && !((String)this.type3.getValue()).equals("Friend")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + ChatFormatting.GREEN + " has drank weekness", "Weakness" + name2, 2000);
                    }
                    this.weaknessPlayers.add(player);
                }
                if (!this.weaknessPlayers.contains(player)) {
                    continue;
                }
                if (player.isPotionActive(MobEffects.WEAKNESS)) {
                    continue;
                }
                if (PvPInfo.mc.player.getName().equals(player.getName()) && ((String)this.self.getValue()).equals("Disable")) {
                    return;
                }
                final String name2 = (player.getName().equals(PvPInfo.mc.player.getName()) && ((String)this.self.getValue()).equals("I")) ? "I" : player.getName();
                if (name2.equals("") || name2.equals(" ")) {
                    return;
                }
                if (SocialManager.isFriend(name2) && !((String)this.type3.getValue()).equals("Enemy")) {
                    MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.friColor) + name2 + ColorUtil.textToChatFormatting(this.chatColor) + " no longer has weekness", "Weakness" + name2, 2000);
                }
                if (!SocialManager.isFriend(name2) && !((String)this.type3.getValue()).equals("Friend")) {
                    MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + ChatFormatting.RED + " no longer has weekness", "Weakness" + name2, 2000);
                }
                this.weaknessPlayers.remove(player);
            }
        }
        if (this.sharp32.getValue()) {
            for (final EntityPlayer player : PvPInfo.mc.world.playerEntities) {
                if (!player.getName().equalsIgnoreCase("fakeplayer")) {
                    if (player.getName().equals(PvPInfo.mc.player.getName())) {
                        continue;
                    }
                    if (this.is32k(player.itemStackMainHand) && !this.sword.contains(player)) {
                        final String name2 = player.getName();
                        if (name2.equals("") || name2.equals(" ")) {
                            return;
                        }
                        if (SocialManager.isFriend(name2) && !((String)this.type5.getValue()).equals("Enemy")) {
                            MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + " is " + ColorUtil.textToChatFormatting(this.chatColor) + "holding a 32k", "32k" + name2, 2000);
                        }
                        if (!SocialManager.isFriend(name2) && !((String)this.type5.getValue()).equals("Friend")) {
                            MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + " is " + ChatFormatting.RED + "holding" + ColorUtil.textToChatFormatting(this.chatColor) + " a 32k", "32k" + name2, 2000);
                        }
                        this.sword.add(player);
                    }
                    if (!this.sword.contains(player)) {
                        continue;
                    }
                    if (this.is32k(player.itemStackMainHand)) {
                        continue;
                    }
                    final String name2 = player.getName();
                    if (name2.equals("") || name2.equals(" ")) {
                        return;
                    }
                    if (SocialManager.isFriend(name2) && !((String)this.type5.getValue()).equals("Enemy")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.friColor) + name2 + " is " + ColorUtil.textToChatFormatting(this.chatColor) + "no longer holding a 32k", "32k" + name2, 2000);
                    }
                    if (!SocialManager.isFriend(name2) && !((String)this.type5.getValue()).equals("Friend")) {
                        MessageBus.sendClientDeleteMessage(ColorUtil.textToChatFormatting(this.nameColor) + name2 + " is " + ChatFormatting.GREEN + "no longer holding" + ColorUtil.textToChatFormatting(this.chatColor) + " a 32k", "32k" + name2, 2000);
                    }
                    this.sword.remove(player);
                }
            }
        }
    }
    
    private boolean is32k(final ItemStack stack) {
        if (stack.getItem() instanceof ItemSword) {
            final NBTTagList enchants = stack.getEnchantmentTagList();
            for (int i = 0; i < enchants.tagCount(); ++i) {
                if (enchants.getCompoundTagAt(i).getShort("lvl") >= 1000) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void onDisable() {
        this.knownPlayers.clear();
        TotemPopManager.INSTANCE.sendMsgs = false;
    }
}
