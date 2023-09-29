//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.api.setting.values.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.client.*;
import java.util.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import net.minecraft.util.text.*;
import java.awt.*;

@Module.Declaration(name = "TextRadar", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 50)
public class TextRadar extends HUDModule
{
    ModeSetting display;
    BooleanSetting sortUp;
    BooleanSetting sortRight;
    IntegerSetting range;
    private final PlayerList list;
    
    public TextRadar() {
        this.display = this.registerMode("Display", (List)Arrays.asList("All", "Friend", "Enemy"), "All");
        this.sortUp = this.registerBoolean("Sort Up", false);
        this.sortRight = this.registerBoolean("Sort Right", false);
        this.range = this.registerInteger("Range", 100, 1, 260);
        this.list = new PlayerList();
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), this.list, 9, 1);
    }
    
    public void onRender() {
        this.list.players.clear();
        TextRadar.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityPlayer).filter(e -> e != TextRadar.mc.player).forEach(e -> {
            if (TextRadar.mc.player.getDistance((Entity)e) <= (int)this.range.getValue()) {
                if (!((String)this.display.getValue()).equalsIgnoreCase("Friend") || SocialManager.isFriend(((Entity)e).getName())) {
                    if (!((String)this.display.getValue()).equalsIgnoreCase("Enemy") || SocialManager.isEnemy(((Entity)e).getName())) {
                        this.list.players.add(e);
                    }
                }
            }
        });
    }
    
    private class PlayerList implements HUDList
    {
        public List<EntityPlayer> players;
        
        private PlayerList() {
            this.players = new ArrayList<EntityPlayer>();
        }
        
        @Override
        public int getSize() {
            return this.players.size();
        }
        
        @Override
        public String getItem(final int index) {
            final EntityPlayer e = this.players.get(index);
            TextFormatting friendcolor;
            if (SocialManager.isFriend(e.getName())) {
                friendcolor = ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getFriendColor();
            }
            else if (SocialManager.isEnemy(e.getName())) {
                friendcolor = ((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnemyColor();
            }
            else {
                friendcolor = TextFormatting.GRAY;
            }
            final float health = e.getHealth() + e.getAbsorptionAmount();
            TextFormatting healthcolor;
            if (health <= 5.0f) {
                healthcolor = TextFormatting.RED;
            }
            else if (health > 5.0f && health < 15.0f) {
                healthcolor = TextFormatting.YELLOW;
            }
            else {
                healthcolor = TextFormatting.GREEN;
            }
            final float distance = TextRadar.mc.player.getDistance((Entity)e);
            TextFormatting distancecolor;
            if (distance < 20.0f) {
                distancecolor = TextFormatting.RED;
            }
            else if (distance >= 20.0f && distance < 50.0f) {
                distancecolor = TextFormatting.YELLOW;
            }
            else {
                distancecolor = TextFormatting.GREEN;
            }
            return TextFormatting.GRAY + "[" + healthcolor + (int)health + TextFormatting.GRAY + "] " + friendcolor + e.getName() + TextFormatting.GRAY + " [" + distancecolor + (int)distance + TextFormatting.GRAY + "]";
        }
        
        @Override
        public Color getItemColor(final int index) {
            return new Color(255, 255, 255);
        }
        
        @Override
        public boolean sortUp() {
            return (boolean)TextRadar.this.sortUp.getValue();
        }
        
        @Override
        public boolean sortRight() {
            return (boolean)TextRadar.this.sortRight.getValue();
        }
    }
}
