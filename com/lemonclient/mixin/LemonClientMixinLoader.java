//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.mixin;

import net.minecraftforge.fml.relauncher.*;
import com.lemonclient.client.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import java.util.*;

@IFMLLoadingPlugin.Name("Lemon Client")
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class LemonClientMixinLoader implements IFMLLoadingPlugin
{
    public LemonClientMixinLoader() {
        LemonClient.LOGGER.info("Mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.lemonclient.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        LemonClient.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        final boolean isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
