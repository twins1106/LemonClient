//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import java.awt.*;
import com.lemonclient.api.util.render.*;
import java.util.*;

@Module.Declaration(name = "MobOwner", category = Category.Render)
public class MobOwner extends Module
{
    public void onUpdate() {
        for (final Entity e : MobOwner.mc.world.loadedEntityList) {
            if (e instanceof IEntityOwnable) {
                if (!(e instanceof AbstractHorse)) {
                    try {
                        RenderUtil.drawNametag(e, new String[] { Objects.requireNonNull(((IEntityOwnable)e).getOwner()).getName() + "" }, new GSColor(Color.WHITE), 0);
                    }
                    catch (NullPointerException ex) {}
                }
                else {
                    final String string = "Name: " + e.getCustomNameTag() + ", Owner: " + Objects.requireNonNull(((IEntityOwnable)e).getOwner()).getName() + ", Speed: " + ((AbstractHorse)e).getAIMoveSpeed();
                    RenderUtil.drawNametag(e, new String[] { string }, new GSColor(Color.WHITE), 1);
                }
            }
        }
    }
}
