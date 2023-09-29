//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.item.*;

@Mixin({ ItemTool.class })
public interface IItemTool
{
    @Accessor("attackDamage")
    float getAttackDamage();
    
    @Accessor("toolMaterial")
    Item.ToolMaterial getToolMaterial();
}
