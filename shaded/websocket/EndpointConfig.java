//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.util.*;

public interface EndpointConfig
{
    List<Class<? extends Encoder>> getEncoders();
    
    List<Class<? extends Decoder>> getDecoders();
    
    Map<String, Object> getUserProperties();
}
