//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import com.lemonclient.api.util.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import java.util.*;

@Module.Declaration(name = "SwordSwitch", category = Category.Dev)
public class SwordSwitch extends Module
{
    BooleanSetting disable;
    
    public SwordSwitch() {
        this.disable = this.registerBoolean("Disable", true);
    }
    
    public void onUpdate() {
        Pair<Float, Integer> newSlot = (Pair<Float, Integer>)new Pair((Object)0.0f, (Object)(-1));
        newSlot = this.findSwordSlot();
        if ((int)newSlot.getValue() != -1) {
            SwordSwitch.mc.player.inventory.currentItem = (int)newSlot.getValue();
            if (this.disable.getValue()) {
                this.disable();
            }
            return;
        }
        MessageBus.sendClientPrefixMessage("Cant find sword");
        this.disable();
    }
    
    private Pair<Float, Integer> findSwordSlot() {
        final List<Integer> items = (List<Integer>)InventoryUtil.findAllItemSlots((Class)ItemSword.class);
        final List<ItemStack> inventory = (List<ItemStack>)SwordSwitch.mc.player.inventory.mainInventory;
        float bestModifier = 0.0f;
        int correspondingSlot = -1;
        for (final Integer integer : items) {
            if (integer > 8) {
                continue;
            }
            final ItemStack stack = inventory.get(integer);
            final float modifier = (EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED) + 1.0f) * ((ItemSword)stack.getItem()).getAttackDamage();
            if (modifier <= bestModifier) {
                continue;
            }
            bestModifier = modifier;
            correspondingSlot = integer;
        }
        return (Pair<Float, Integer>)new Pair((Object)bestModifier, (Object)correspondingSlot);
    }
}
