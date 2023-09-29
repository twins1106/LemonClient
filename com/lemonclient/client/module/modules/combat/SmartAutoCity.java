//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.combat;

import com.lemonclient.api.setting.values.*;
import net.minecraft.entity.player.*;
import com.lemonclient.client.module.modules.exploits.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.module.modules.qwq.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.lemonclient.api.util.world.*;
import net.minecraft.entity.*;
import com.lemonclient.client.*;
import java.util.*;
import net.minecraft.block.state.*;

@Module.Declaration(name = "SmartAutoCity", category = Category.Combat)
public class SmartAutoCity extends Module
{
    BooleanSetting doubleMine;
    DoubleSetting range;
    public static EntityPlayer target;
    boolean doubleBreak;
    
    public SmartAutoCity() {
        this.doubleMine = this.registerBoolean("Double Mine", true);
        this.range = this.registerDouble("Range", 5.5, 0.0, 10.0);
    }
    
    public void onUpdate() {
        if (SmartAutoCity.mc.world == null || SmartAutoCity.mc.player == null || SmartAutoCity.mc.player.isDead) {
            return;
        }
        if (ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            this.doubleBreak = (InstantMine.INSTANCE.doubleMine == null);
        }
        else if (ModuleManager.isModuleEnabled((Class)Mine.class)) {
            this.doubleBreak = (Mine.INSTANCE.doubleMine == null);
        }
        SmartAutoCity.target = this.getTarget((double)this.range.getValue());
        if (SmartAutoCity.target == null) {
            return;
        }
        final BlockPos feet = new BlockPos(SmartAutoCity.target.posX, SmartAutoCity.target.posY + 0.55, SmartAutoCity.target.posZ);
        if (!this.detection(SmartAutoCity.target)) {
            if (this.doubleMine.getValue()) {
                if (this.getBlock(feet.add(0, 1, 2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, 1));
                }
                else if (this.getBlock(feet.add(0, 1, -2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, -1));
                }
                else if (this.getBlock(feet.add(2, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(1, 0, 0));
                }
                else if (this.getBlock(feet.add(-2, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-1, 0, 0));
                }
                else if (this.getBlock(feet.add(2, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(2, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(2, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(2, 0, 0));
                }
                else if (this.getBlock(feet.add(-2, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-2, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-2, 0, 0));
                }
                else if (this.getBlock(feet.add(0, 1, -2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -2)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, -2)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, -2));
                }
                else if (this.getBlock(feet.add(0, 1, 2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 2)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, 2)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, 2));
                }
                else if (this.getBlock(feet.add(2, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(2, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(2, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(2, 0, 0));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(1, 0, 0));
                    }
                }
                else if (this.getBlock(feet.add(-2, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-2, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-2, 0, 0));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(-1, 0, 0));
                    }
                }
                else if (this.getBlock(feet.add(0, 1, -2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -2)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, -2)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, -2));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(0, 0, -1));
                    }
                }
                else if (this.getBlock(feet.add(0, 1, 2)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 2)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, 2)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, 2));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(0, 0, 1));
                    }
                }
                else if (this.getBlock(feet.add(0, 2, 1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 1, 1));
                }
                else if (this.getBlock(feet.add(0, 2, 1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, 1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, 1));
                }
                else if (this.getBlock(feet.add(0, 2, -1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, -1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 0, -1));
                }
                else if (this.getBlock(feet.add(1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(1, 0, 0));
                }
                else if (this.getBlock(feet.add(-1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-1, 0, 0));
                }
                else if (this.getBlock(feet.add(1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(1, 1, 0));
                }
                else if (this.getBlock(feet.add(-1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-1, 1, 0));
                }
                else if (this.getBlock(feet.add(0, 2, -1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 1, -1));
                }
                else if (this.getBlock(feet.add(1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(1, 1, 0));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(1, 0, 0));
                    }
                }
                else if (this.getBlock(feet.add(-1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-1, 1, 0));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(-1, 0, 0));
                    }
                }
                else if (this.getBlock(feet.add(0, 2, -1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 1, -1));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(0, 0, -1));
                    }
                }
                else if (this.getBlock(feet.add(0, 2, 1)).getBlock() == Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 1, 1));
                    if (this.doubleBreak) {
                        this.surroundMine(feet.add(0, 0, 1));
                    }
                }
                else if (this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-2, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-2, 1, 0));
                }
                else if (this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(2, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(2, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(2, 1, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(2, 1, 0));
                }
                else if (this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, 2)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, 2)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, 2)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 1, 2));
                }
                else if (this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, -2)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, -2)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, -2)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 1, -2));
                }
                else if (this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 2, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 2, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(-1, 2, 0));
                }
                else if (this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 2, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 2, 0)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(1, 2, 0));
                }
                else if (this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 2, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 2, 1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 2, 1));
                }
                else if (this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 2, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 2, -1)).getBlock() != Blocks.BEDROCK) {
                    this.surroundMine(feet.add(0, 2, -1));
                }
            }
            else if (this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(1, 1, 0));
            }
            else if (this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(1, 0, 0)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(1, 0, 0));
            }
            else if (this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(-1, 1, 0));
            }
            else if (this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(feet.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(-1, 0, 0));
            }
            else if (this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(0, 1, -1));
            }
            else if (this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, -1)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(0, 0, -1));
            }
            else if (this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(0, 1, 1));
            }
            else if (this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.AIR && this.getBlock(feet.add(0, 1, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(feet.add(0, 0, 1)).getBlock() != Blocks.BEDROCK) {
                this.surroundMine(feet.add(0, 0, 1));
            }
        }
    }
    
    private void surroundMine(final BlockPos position) {
        if (position == null) {
            return;
        }
        BlockPos instantPos;
        BlockPos doublePos = instantPos = null;
        if (ModuleManager.isModuleEnabled((Class)InstantMine.class)) {
            instantPos = InstantMine.INSTANCE.lastBlock;
            doublePos = InstantMine.INSTANCE.doubleMine;
        }
        else if (ModuleManager.isModuleEnabled((Class)Mine.class)) {
            instantPos = Mine.INSTANCE.lastBlock;
            doublePos = Mine.INSTANCE.doubleMine;
        }
        if (doublePos != null && doublePos.equals((Object)position)) {
            return;
        }
        if (instantPos != null) {
            if (instantPos.equals((Object)position)) {
                return;
            }
            if (instantPos.equals((Object)new BlockPos(SmartAutoCity.target.posX, SmartAutoCity.target.posY, SmartAutoCity.target.posZ)) && SmartAutoCity.mc.world.getBlockState(new BlockPos(SmartAutoCity.target.posX, SmartAutoCity.target.posY, SmartAutoCity.target.posZ)).getBlock() != Blocks.AIR) {
                return;
            }
            if (instantPos.equals((Object)new BlockPos(SmartAutoCity.mc.player.posX, SmartAutoCity.mc.player.posY + 2.0, SmartAutoCity.mc.player.posZ))) {
                return;
            }
            if (instantPos.equals((Object)new BlockPos(SmartAutoCity.mc.player.posX, SmartAutoCity.mc.player.posY - 1.0, SmartAutoCity.mc.player.posZ))) {
                return;
            }
            if (SmartAutoCity.mc.player.rotationPitch <= 90.0f && SmartAutoCity.mc.player.rotationPitch >= 80.0f) {
                return;
            }
            if (SmartAutoCity.mc.world.getBlockState(instantPos).getBlock() == Blocks.WEB) {
                return;
            }
        }
        SmartAutoCity.mc.playerController.onPlayerDamageBlock(position, BlockUtil.getRayTraceFacing(position));
    }
    
    private boolean detection(final EntityPlayer player) {
        return (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX + 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX + 1.2, player.posY + 1.0, player.posZ)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX - 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX - 1.2, player.posY + 1.0, player.posZ)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 1.2)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY + 1.0, player.posZ + 1.2)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 1.2)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY + 1.0, player.posZ - 1.2)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX + 2.2, player.posY, player.posZ)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX + 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX - 2.2, player.posY, player.posZ)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX - 1.2, player.posY, player.posZ)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 2.2)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 1.2)).getBlock() == Blocks.AIR) || (SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 2.2)).getBlock() == Blocks.AIR & SmartAutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 1.2)).getBlock() == Blocks.AIR);
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : SmartAutoCity.mc.world.playerEntities) {
            if (!EntityUtil.basicChecksEntity(player) && SmartAutoCity.mc.player.getDistance((Entity)player) <= range) {
                if (LemonClient.speedUtil.getPlayerSpeed(player) > 10.0) {
                    continue;
                }
                if (target != null && SmartAutoCity.mc.player.getDistance((Entity)player) >= distance) {
                    continue;
                }
                target = player;
                distance = SmartAutoCity.mc.player.getDistanceSq((Entity)player);
            }
        }
        return target;
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return SmartAutoCity.mc.world.getBlockState(block);
    }
}
