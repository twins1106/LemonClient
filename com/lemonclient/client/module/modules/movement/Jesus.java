//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import me.zero.alpine.listener.*;
import com.lemonclient.api.event.events.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

@Module.Declaration(name = "Jesus", category = Category.Movement)
public class Jesus extends Module
{
    @EventHandler
    private final Listener<BoundingBoxEvent> boundingBoxEventListener;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    
    public Jesus() {
        this.boundingBoxEventListener = (Listener<BoundingBoxEvent>)new Listener(event -> {
            if ((event.getBlock().equals(Blocks.WATER) || event.getBlock().equals(Blocks.LAVA)) && !Jesus.mc.gameSettings.keyBindSneak.isKeyDown()) {
                event.setbb(Block.FULL_BLOCK_AABB);
            }
        }, new Predicate[0]);
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if ((Jesus.mc.world.getBlockState(new BlockPos(Jesus.mc.player.getPositionVector())).getBlock().equals(Blocks.WATER) || Jesus.mc.world.getBlockState(new BlockPos(Jesus.mc.player.getPositionVector())).getBlock().equals(Blocks.LAVA)) && !Jesus.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Jesus.mc.player.motionY = 0.1;
            }
        }, new Predicate[0]);
    }
}
