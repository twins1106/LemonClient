//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.potion.*;

@Module.Declaration(name = "PotionEffects", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 300)
public class PotionEffects extends HUDModule
{
    BooleanSetting sortUp;
    BooleanSetting sortRight;
    private final PotionList list;
    
    public PotionEffects() {
        this.sortUp = this.registerBoolean("Sort Up", false);
        this.sortRight = this.registerBoolean("Sort Right", false);
        this.list = new PotionList();
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), this.list, 9, 1);
    }
    
    Color getColour(final PotionEffect potion) {
        final int colour = potion.getPotion().getLiquidColor();
        final float r = (colour >> 16 & 0xFF) / 255.0f;
        final float g = (colour >> 8 & 0xFF) / 255.0f;
        final float b = (colour & 0xFF) / 255.0f;
        return new Color(r, g, b);
    }
    
    private class PotionList implements HUDList
    {
        @Override
        public int getSize() {
            return PotionEffects.mc.player.getActivePotionEffects().size();
        }
        
        @Override
        public String getItem(final int index) {
            final PotionEffect effect = (PotionEffect)PotionEffects.mc.player.getActivePotionEffects().toArray()[index];
            final String name = I18n.format(effect.getPotion().getName(), new Object[0]);
            final int amplifier = effect.getAmplifier() + 1;
            return name + " " + amplifier + ChatFormatting.GRAY + " " + Potion.getPotionDurationString(effect, 1.0f);
        }
        
        @Override
        public Color getItemColor(final int i) {
            if (PotionEffects.mc.player.getActivePotionEffects().toArray().length != 0) {
                return PotionEffects.this.getColour((PotionEffect)PotionEffects.mc.player.getActivePotionEffects().toArray()[i]);
            }
            return null;
        }
        
        @Override
        public boolean sortUp() {
            return (boolean)PotionEffects.this.sortUp.getValue();
        }
        
        @Override
        public boolean sortRight() {
            return (boolean)PotionEffects.this.sortRight.getValue();
        }
    }
}
