//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

@Module.Declaration(name = "Avoid", category = Category.Movement)
public class Avoid extends Module
{
    public static Avoid INSTANCE;
    public BooleanSetting unloaded;
    public BooleanSetting cactus;
    public BooleanSetting fire;
    public BooleanSetting bigFire;
    @EventHandler
    private final Listener<BoundingBoxEvent> playerMoveEventListener;
    
    public Avoid() {
        this.unloaded = this.registerBoolean("Unloaded", false);
        this.cactus = this.registerBoolean("Cactus", false);
        this.fire = this.registerBoolean("Fire", false);
        this.bigFire = this.registerBoolean("Extend Fire", false, () -> (Boolean)this.fire.getValue());
        this.playerMoveEventListener = (Listener<BoundingBoxEvent>)new Listener(event -> {
            if ((event.getBlock().equals(Blocks.STRUCTURE_VOID) && (boolean)this.unloaded.getValue()) || (event.getBlock().equals(Blocks.CACTUS) && (boolean)this.cactus.getValue()) || (event.getBlock().equals(Blocks.FIRE) && (boolean)this.fire.getValue())) {
                if ((boolean)this.bigFire.getValue() && event.getBlock() == Blocks.FIRE) {
                    event.setbb(Block.FULL_BLOCK_AABB.expand(0.1, 0.1, 0.1));
                }
                else {
                    event.setbb(Block.FULL_BLOCK_AABB);
                }
            }
        }, new Predicate[0]);
        Avoid.INSTANCE = this;
    }
}
