//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

@Module.Declaration(name = "KillEffect", category = Category.Misc)
public class KillEffect extends Module
{
    BooleanSetting thunder;
    IntegerSetting numbersThunder;
    BooleanSetting sound;
    IntegerSetting numberSound;
    ArrayList<EntityPlayer> playersDead;
    
    public KillEffect() {
        this.thunder = this.registerBoolean("Thunder", true);
        this.numbersThunder = this.registerInteger("Number Thunder", 1, 1, 10);
        this.sound = this.registerBoolean("Sound", true);
        this.numberSound = this.registerInteger("Number Sound", 1, 1, 10);
        this.playersDead = new ArrayList<EntityPlayer>();
    }
    
    protected void onEnable() {
        this.playersDead.clear();
    }
    
    public void onUpdate() {
        if (KillEffect.mc.world == null) {
            this.playersDead.clear();
            return;
        }
        int i;
        int j;
        KillEffect.mc.world.playerEntities.forEach(entity -> {
            if (this.playersDead.contains(entity)) {
                if (entity.getHealth() > 0.0f) {
                    this.playersDead.remove(entity);
                }
            }
            else if (entity.getHealth() == 0.0f) {
                if (this.thunder.getValue()) {
                    for (i = 0; i < (int)this.numbersThunder.getValue(); ++i) {
                        KillEffect.mc.world.spawnEntity((Entity)new EntityLightningBolt((World)KillEffect.mc.world, entity.posX, entity.posY, entity.posZ, true));
                    }
                }
                if (this.sound.getValue()) {
                    for (j = 0; j < (int)this.numberSound.getValue(); ++j) {
                        KillEffect.mc.player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.5f, 1.0f);
                    }
                }
                this.playersDead.add(entity);
            }
        });
    }
}
