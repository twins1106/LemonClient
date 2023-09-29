//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;

@Module.Declaration(name = "32kAura", category = Category.Combat)
public class kAura extends Module
{
    private int hasWaited;
    ModeSetting time;
    ModeSetting mode;
    IntegerSetting range;
    BooleanSetting only32k;
    BooleanSetting xin;
    BooleanSetting packet;
    BooleanSetting swing;
    BooleanSetting autoswitch;
    BooleanSetting packetswitch;
    BooleanSetting playersOnly;
    IntegerSetting hit;
    
    public kAura() {
        this.time = this.registerMode("Time Mode", (List)Arrays.asList("Tick", "onUpdate", "Both", "Fast"), "Tick");
        this.mode = this.registerMode("Mode", (List)Arrays.asList("CPS", "CPT"), "CPS");
        this.range = this.registerInteger("Range", 6, 0, 20);
        this.only32k = this.registerBoolean("32k Only", true);
        this.xin = this.registerBoolean("Xin", true);
        this.packet = this.registerBoolean("Packet Attack", true);
        this.swing = this.registerBoolean("Swing", true);
        this.autoswitch = this.registerBoolean("Auto Switch", true);
        this.packetswitch = this.registerBoolean("Packet Switch", true);
        this.playersOnly = this.registerBoolean("Players only", true);
        this.hit = this.registerInteger("Hit", 20, 0, Integer.MAX_VALUE);
    }
    
    public void onUpdate() {
        if (((String)this.time.getValue()).equals("onUpdate") || ((String)this.time.getValue()).equals("Both")) {
            this.attack();
        }
    }
    
    public void onTick() {
        if (((String)this.time.getValue()).equals("Tick") || ((String)this.time.getValue()).equals("Both")) {
            this.attack();
        }
    }
    
    public void fast() {
        if (((String)this.time.getValue()).equals("Fast")) {
            this.attack();
        }
    }
    
    private void attack() {
        if (!(boolean)this.xin.getValue()) {
            int reqDelay;
            if (((String)this.mode.getValue()).equals("CPS")) {
                reqDelay = (int)Math.round(20.0 / (int)this.hit.getValue());
            }
            else {
                reqDelay = (int)Math.round(1.0 / (int)this.hit.getValue());
            }
            if (this.hasWaited < reqDelay) {
                ++this.hasWaited;
                return;
            }
        }
        this.hasWaited = 0;
        for (final Entity entity : kAura.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity == kAura.mc.player) {
                    continue;
                }
                if (kAura.mc.player.getDistance(entity) > (int)this.range.getValue() || ((EntityLivingBase)entity).getHealth() <= 0.0f) {
                    continue;
                }
                if (!(entity instanceof EntityPlayer) && (boolean)this.playersOnly.getValue()) {
                    continue;
                }
                if (this.autoswitch.getValue()) {
                    final int oldSlot = kAura.mc.player.inventory.currentItem;
                    this.equipBestWeapon();
                    if (!this.isSuperWeapon(kAura.mc.player.getHeldItemMainhand()) && (boolean)this.only32k.getValue()) {
                        this.switchtoslot(oldSlot);
                    }
                }
                if ((boolean)this.only32k.getValue() && !this.isSuperWeapon(kAura.mc.player.getHeldItemMainhand())) {
                    continue;
                }
                if (SocialManager.isFriend(entity.getName())) {
                    continue;
                }
                boolean attack = true;
                if ((boolean)this.xin.getValue() && kAura.mc.player.getCooledAttackStrength(0.0f) < 1.0f) {
                    attack = false;
                }
                if (!attack) {
                    continue;
                }
                if (this.packet.getValue()) {
                    kAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                }
                else {
                    kAura.mc.playerController.attackEntity((EntityPlayer)kAura.mc.player, entity);
                }
                if (!(boolean)this.swing.getValue()) {
                    continue;
                }
                kAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    
    private boolean isSuperWeapon(final ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.getTagCompound() == null) {
            return false;
        }
        if (item.getEnchantmentTagList().getTagType() == 0) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)item.getTagCompound().getTag("ench");
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl >= 16) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    private void switchtoslot(final int slot) {
        if (this.packetswitch.getValue()) {
            kAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            kAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            kAura.mc.player.inventory.currentItem = slot;
        }
    }
    
    private void equipBestWeapon() {
        int bestSlot = -1;
        double maxDamage = 0.0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = kAura.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty) {
                if (stack.getItem() instanceof ItemTool) {
                    final double damage = ((ItemTool)stack.getItem()).attackDamage + EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
                    if (damage > maxDamage) {
                        maxDamage = damage;
                        bestSlot = i;
                    }
                }
                else if (stack.getItem() instanceof ItemSword) {
                    final double damage = ((ItemSword)stack.getItem()).getAttackDamage() + EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
                    if (damage > maxDamage) {
                        maxDamage = damage;
                        bestSlot = i;
                    }
                }
            }
        }
        if (bestSlot != -1) {
            this.switchtoslot(bestSlot);
        }
    }
}
