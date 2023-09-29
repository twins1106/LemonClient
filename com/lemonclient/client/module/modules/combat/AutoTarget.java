//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.client.module.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.lemonclient.api.util.player.social.*;
import net.minecraft.util.*;
import java.util.*;

@Module.Declaration(name = "AutoTarget", category = Category.Combat)
public class AutoTarget extends Module
{
    IntegerSetting delay;
    IntegerSetting range;
    BooleanSetting swing;
    BooleanSetting pause;
    int hasWaited;
    
    public AutoTarget() {
        this.delay = this.registerInteger("Delay", 20, 20, 100);
        this.range = this.registerInteger("Range", 20, 0, 40);
        this.swing = this.registerBoolean("Swing", true);
        this.pause = this.registerBoolean("PauseWhenKA", true);
    }
    
    public void onUpdate() {
        if ((boolean)this.pause.getValue() && ModuleManager.isModuleEnabled((Class)KillAura.class) && KillAura.SA) {
            return;
        }
        if (this.hasWaited < (int)this.delay.getValue()) {
            ++this.hasWaited;
            return;
        }
        this.hasWaited = 0;
        for (final Entity entity : AutoTarget.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity == AutoTarget.mc.player) {
                    continue;
                }
                if (AutoTarget.mc.player.getDistance(entity) > (int)this.range.getValue() || ((EntityLivingBase)entity).getHealth() <= 0.0f) {
                    continue;
                }
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                if (SocialManager.isFriend(entity.getName())) {
                    continue;
                }
                AutoTarget.mc.playerController.attackEntity((EntityPlayer)AutoTarget.mc.player, entity);
                if (!(boolean)this.swing.getValue()) {
                    continue;
                }
                AutoTarget.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
