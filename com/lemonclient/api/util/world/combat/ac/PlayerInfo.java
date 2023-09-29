//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.world.combat.ac;

import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import java.util.*;

public class PlayerInfo
{
    private static final Potion RESISTANCE;
    private static final DamageSource EXPLOSION_SOURCE;
    public final EntityPlayer entity;
    public final float totalArmourValue;
    public final float armourToughness;
    public final float health;
    public final int enchantModifier;
    public final boolean hasResistance;
    public final boolean lowArmour;
    
    public PlayerInfo(final EntityPlayer entity, final float armorPercent) {
        this.entity = entity;
        this.totalArmourValue = (float)entity.getTotalArmorValue();
        this.armourToughness = (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
        this.health = entity.getHealth() + entity.getAbsorptionAmount();
        int enchantModifier1;
        try {
            enchantModifier1 = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), PlayerInfo.EXPLOSION_SOURCE);
        }
        catch (NullPointerException e) {
            enchantModifier1 = 0;
        }
        this.enchantModifier = enchantModifier1;
        this.hasResistance = entity.isPotionActive(PlayerInfo.RESISTANCE);
        boolean i = false;
        for (final ItemStack stack : entity.getArmorInventoryList()) {
            if (1.0f - stack.getItemDamage() / (float)stack.getMaxDamage() < armorPercent) {
                i = true;
                break;
            }
        }
        this.lowArmour = i;
    }
    
    public PlayerInfo(final EntityPlayer entity, final float armorPercent, final float totalArmourValue, final float armourToughness) {
        this.entity = entity;
        this.totalArmourValue = (float)entity.getTotalArmorValue();
        this.armourToughness = (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
        this.health = entity.getHealth() + entity.getAbsorptionAmount();
        this.enchantModifier = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), PlayerInfo.EXPLOSION_SOURCE);
        this.hasResistance = entity.isPotionActive(PlayerInfo.RESISTANCE);
        boolean i = false;
        for (final ItemStack stack : entity.getArmorInventoryList()) {
            if (1.0f - stack.getItemDamage() / (float)stack.getMaxDamage() < armorPercent) {
                i = true;
                break;
            }
        }
        this.lowArmour = i;
    }
    
    public PlayerInfo(final EntityPlayer entity, final boolean lowArmour, final float totalArmourValue, final float armourToughness) {
        this.entity = entity;
        this.totalArmourValue = totalArmourValue;
        this.armourToughness = armourToughness;
        this.health = entity.getHealth() + entity.getAbsorptionAmount();
        int enchantModifier1;
        try {
            enchantModifier1 = EnchantmentHelper.getEnchantmentModifierDamage(entity.getArmorInventoryList(), PlayerInfo.EXPLOSION_SOURCE);
        }
        catch (NullPointerException e) {
            enchantModifier1 = 0;
        }
        this.enchantModifier = enchantModifier1;
        this.hasResistance = entity.isPotionActive(PlayerInfo.RESISTANCE);
        this.lowArmour = lowArmour;
    }
    
    static {
        RESISTANCE = Potion.getPotionById(11);
        EXPLOSION_SOURCE = new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }
}
