//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package shaded.websocket;

import java.util.*;

public interface Extension
{
    String getName();
    
    List<Parameter> getParameters();
    
    public interface Parameter
    {
        String getName();
        
        String getValue();
    }
}
