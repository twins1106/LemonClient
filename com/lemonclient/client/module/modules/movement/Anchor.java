//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.movement;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.util.math.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import java.util.*;

@Module.Declaration(name = "Anchor", category = Category.Movement, priority = 1000)
public class Anchor extends Module
{
    BooleanSetting guarantee;
    IntegerSetting activateHeight;
    IntegerSetting activationPitch;
    BooleanSetting stopSpeed;
    BooleanSetting fastFall;
    public static boolean active;
    BlockPos playerPos;
    @EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEventListener;
    
    public Anchor() {
        this.guarantee = this.registerBoolean("Guarantee Hole", true);
        this.activateHeight = this.registerInteger("Activate Height", 2, 1, 5);
        this.activationPitch = this.registerInteger("Activation Pitch", 75, 0, 90);
        this.stopSpeed = this.registerBoolean("Stop Speed", true);
        this.fastFall = this.registerBoolean("Fast Fall", false);
        this.playerMoveEventListener = (Listener<PlayerMoveEvent>)new Listener(event -> {
            if (Anchor.mc.player == null || Anchor.mc.world == null) {
                return;
            }
            Anchor.active = false;
            if (Anchor.mc.player.rotationPitch < (int)this.activationPitch.getValue()) {
                return;
            }
            if (Anchor.mc.player.posY < 0.0) {
                return;
            }
            final double blockX = Math.floor(Anchor.mc.player.posX);
            final double blockZ = Math.floor(Anchor.mc.player.posZ);
            final double offsetX = Math.abs(Anchor.mc.player.posX - blockX);
            final double offsetZ = Math.abs(Anchor.mc.player.posZ - blockZ);
            if ((boolean)this.guarantee.getValue() && (offsetX < 0.30000001192092896 || offsetX > 0.699999988079071 || offsetZ < 0.30000001192092896 || offsetZ > 0.699999988079071)) {
                return;
            }
            this.playerPos = new BlockPos(blockX, Anchor.mc.player.posY, blockZ);
            if (Anchor.mc.world.getBlockState(this.playerPos).getBlock() != Blocks.AIR) {
                return;
            }
            BlockPos currentBlock = this.playerPos.down();
            for (int i = 0; i < (int)this.activateHeight.getValue(); ++i) {
                currentBlock = currentBlock.down();
                if (Anchor.mc.world.getBlockState(currentBlock).getBlock() != Blocks.AIR) {
                    final HashMap<HoleUtil.BlockOffset, HoleUtil.BlockSafety> sides = (HashMap<HoleUtil.BlockOffset, HoleUtil.BlockSafety>)HoleUtil.getUnsafeSides(currentBlock.up());
                    sides.entrySet().removeIf(entry -> entry.getValue() == HoleUtil.BlockSafety.RESISTANT);
                    if (sides.size() == 0) {
                        if (this.stopSpeed.getValue()) {
                            Anchor.active = true;
                        }
                        Anchor.mc.player.motionX = 0.0;
                        Anchor.mc.player.motionZ = 0.0;
                        if (this.fastFall.getValue()) {
                            Anchor.mc.player.motionY = -10.0;
                        }
                        event.cancel();
                    }
                }
            }
        }, new Predicate[0]);
    }
    
    protected void onDisable() {
        Anchor.active = false;
    }
    
    static {
        Anchor.active = false;
    }
}
