//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

@Module.Declaration(name = "AutoDrop", category = Category.Combat)
public class AutoDrop extends Module
{
    IntegerSetting delay;
    ModeSetting mode;
    private final Timing timer;
    
    public AutoDrop() {
        this.delay = this.registerInteger("Drop Delay", 10, 0, 20);
        this.mode = this.registerMode("Sharpness", (List)Arrays.asList("Sharp5", "Sharp32k", "Both"), "Both");
        this.timer = new Timing();
    }
    
    public void onUpdate() {
        final String s = (String)this.mode.getValue();
        switch (s) {
            case "Sharp32k": {
                if (this.isSuperWeapon(AutoDrop.mc.player.getHeldItemMainhand()) && this.timer.passedDs((double)(int)this.delay.getValue())) {
                    final boolean holding32k = false;
                    final EntityItem entityItem = AutoDrop.mc.player.dropItem(!holding32k);
                    this.timer.reset();
                    break;
                }
            }
            case "Both": {
                if (this.checkSword(AutoDrop.mc.player.getHeldItemMainhand()) && this.timer.passedDs((double)(int)this.delay.getValue())) {
                    final boolean holding = false;
                    AutoDrop.mc.player.dropItem(!holding);
                }
            }
            case "Sharp5": {
                if (this.checkSharpness5(AutoDrop.mc.player.getHeldItemMainhand()) && this.timer.passedDs((double)(int)this.delay.getValue())) {
                    final boolean holding2 = false;
                    AutoDrop.mc.player.dropItem(!holding2);
                    break;
                }
                break;
            }
        }
    }
    
    private boolean checkSword(final ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        }
        if (stack.getEnchantmentTagList().getTagType() == 0) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.getTagCompound().getTag("ench");
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl > 4) {
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
    
    private boolean checkSharpness5(final ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        }
        if (stack.getEnchantmentTagList().getTagType() == 0) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)stack.getTagCompound().getTag("ench");
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl == 5) {
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
}
