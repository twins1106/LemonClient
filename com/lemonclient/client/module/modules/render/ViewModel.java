//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.render;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.event.events.*;
import me.zero.alpine.listener.*;
import net.minecraftforge.client.event.*;
import java.util.*;
import java.util.function.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

@Module.Declaration(name = "ViewModel", category = Category.Render)
public class ViewModel extends Module
{
    ModeSetting type;
    public BooleanSetting cancelEating;
    DoubleSetting xLeft;
    DoubleSetting yLeft;
    DoubleSetting zLeft;
    DoubleSetting xRight;
    DoubleSetting yRight;
    DoubleSetting zRight;
    DoubleSetting fov;
    @EventHandler
    private final Listener<TransformSideFirstPersonEvent> eventListener;
    @EventHandler
    private final Listener<EntityViewRenderEvent.FOVModifier> fovModifierListener;
    
    public ViewModel() {
        this.type = this.registerMode("Type", (List)Arrays.asList("Value", "FOV", "Both"), "Value");
        this.cancelEating = this.registerBoolean("No Eat", false);
        this.xLeft = this.registerDouble("Left X", 0.0, -2.0, 2.0);
        this.yLeft = this.registerDouble("Left Y", 0.2, -2.0, 2.0);
        this.zLeft = this.registerDouble("Left Z", -1.2, -2.0, 2.0);
        this.xRight = this.registerDouble("Right X", 0.0, -2.0, 2.0);
        this.yRight = this.registerDouble("Right Y", 0.2, -2.0, 2.0);
        this.zRight = this.registerDouble("Right Z", -1.2, -2.0, 2.0);
        this.fov = this.registerDouble("Item FOV", 130.0, 70.0, 200.0);
        this.eventListener = (Listener<TransformSideFirstPersonEvent>)new Listener(event -> {
            if (((String)this.type.getValue()).equalsIgnoreCase("Value") || ((String)this.type.getValue()).equalsIgnoreCase("Both")) {
                if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
                    GlStateManager.translate((double)this.xRight.getValue(), (double)this.yRight.getValue(), (double)this.zRight.getValue());
                }
                else if (event.getEnumHandSide() == EnumHandSide.LEFT) {
                    GlStateManager.translate((double)this.xLeft.getValue(), (double)this.yLeft.getValue(), (double)this.zLeft.getValue());
                }
            }
        }, new Predicate[0]);
        this.fovModifierListener = (Listener<EntityViewRenderEvent.FOVModifier>)new Listener(event -> {
            if (((String)this.type.getValue()).equalsIgnoreCase("FOV") || ((String)this.type.getValue()).equalsIgnoreCase("Both")) {
                event.setFOV(((Double)this.fov.getValue()).floatValue());
            }
        }, new Predicate[0]);
    }
}
