//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import net.minecraft.entity.*;
import com.lemonclient.client.module.modules.combat.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.api.util.player.social.*;
import java.util.*;
import net.minecraft.util.math.*;

@Module.Declaration(name = "BurrowESP", category = Category.Render)
public class BurrowESP extends Module
{
    BooleanSetting self;
    ColorSetting selfcolor;
    BooleanSetting friend;
    ColorSetting color2;
    BooleanSetting enemy;
    ColorSetting color;
    IntegerSetting ufoAlpha;
    IntegerSetting Alpha;
    
    public BurrowESP() {
        this.self = this.registerBoolean("Self", true);
        this.selfcolor = this.registerColor("Self Color", new GSColor(0, 255, 0, 50));
        this.friend = this.registerBoolean("Friend", true);
        this.color2 = this.registerColor("Friend Color", new GSColor(0, 255, 0, 50));
        this.enemy = this.registerBoolean("Enemy", true);
        this.color = this.registerColor("Color", new GSColor(120, 0, 0));
        this.ufoAlpha = this.registerInteger("Alpha", 120, 0, 255);
        this.Alpha = this.registerInteger("Outline Alpha", 255, 0, 255);
    }
    
    public void onWorldRender(final RenderEvent event) {
        for (final Entity entity : BurrowESP.mc.world.playerEntities) {
            final BlockPos pos = AntiBurrow.getEntityPos(entity);
            if (BlockUtil.getBlock(pos) != Blocks.AIR) {
                final String name = entity.getName();
                if (entity == BurrowESP.mc.player) {
                    if (!(boolean)this.self.getValue()) {
                        continue;
                    }
                    RenderUtil.drawBox(pos, 1.0, new GSColor(this.selfcolor.getValue(), (int)this.ufoAlpha.getValue()), 63);
                    RenderUtil.drawBoundingBox(pos, 1.0, 1.0f, new GSColor(this.selfcolor.getValue(), (int)this.Alpha.getValue()));
                }
                else if (SocialManager.isFriend(name)) {
                    if (!(boolean)this.friend.getValue()) {
                        continue;
                    }
                    RenderUtil.drawBox(pos, 1.0, new GSColor(this.color2.getValue(), (int)this.ufoAlpha.getValue()), 63);
                    RenderUtil.drawBoundingBox(pos, 1.0, 1.0f, new GSColor(this.color2.getValue(), (int)this.Alpha.getValue()));
                }
                else {
                    if (!(boolean)this.enemy.getValue()) {
                        continue;
                    }
                    RenderUtil.drawBox(pos, 1.0, new GSColor(this.color.getValue(), (int)this.ufoAlpha.getValue()), 63);
                    RenderUtil.drawBoundingBox(pos, 1.0, 1.0f, new GSColor(this.color.getValue(), (int)this.Alpha.getValue()));
                }
            }
        }
    }
}
