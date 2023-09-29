//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.dev;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;

@Module.Declaration(name = "ArmorWarning", category = Category.Dev)
public class ArmorMessage extends Module
{
    IntegerSetting armorThreshhold;
    String message;
    boolean render;
    int delay;
    
    public ArmorMessage() {
        this.armorThreshhold = this.registerInteger("Armor%", 20, 1, 100);
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public void onRender() {
    }
    
    public void onUpdate() {
        if (ArmorMessage.mc.world == null || ArmorMessage.mc.player == null || ArmorMessage.mc.player.isDead) {
            return;
        }
        for (final ItemStack stack : ArmorMessage.mc.player.inventory.armorInventory) {
            if (stack == ItemStack.EMPTY) {
                continue;
            }
            final int percent = getRoundedDamage(stack);
            if (percent > (int)this.armorThreshhold.getValue()) {
                continue;
            }
            this.message = "your armor has low dura!";
            this.render = true;
            this.delay = 0;
        }
        if (this.render) {
            MessageBus.sendClientDeleteMessage(this.message, "ArmorWarning", 5);
        }
        this.render = false;
    }
}
