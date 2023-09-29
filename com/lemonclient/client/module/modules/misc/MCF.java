//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.misc;

import net.minecraftforge.fml.common.gameevent.*;
import me.zero.alpine.listener.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import com.lemonclient.api.util.player.social.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.misc.*;

@Module.Declaration(name = "MCF", category = Category.Misc)
public class MCF extends Module
{
    @EventHandler
    private final Listener<InputEvent.MouseInputEvent> listener;
    
    public MCF() {
        this.listener = (Listener<InputEvent.MouseInputEvent>)new Listener(event -> {
            if (MCF.mc.objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && MCF.mc.objectMouseOver.entityHit instanceof EntityPlayer && Mouse.isButtonDown(2)) {
                if (SocialManager.isFriend(MCF.mc.objectMouseOver.entityHit.getName())) {
                    SocialManager.delFriend(MCF.mc.objectMouseOver.entityHit.getName());
                    MessageBus.sendClientPrefixMessage(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getDisabledColor() + "Removed " + MCF.mc.objectMouseOver.entityHit.getName() + " from friends list");
                }
                else {
                    SocialManager.addFriend(MCF.mc.objectMouseOver.entityHit.getName());
                    MessageBus.sendClientPrefixMessage(((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).getEnabledColor() + "Added " + MCF.mc.objectMouseOver.entityHit.getName() + " to friends list");
                }
            }
        }, new Predicate[0]);
    }
}
