//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.setting;

import com.lukflug.panelstudio.base.*;
import java.util.stream.*;

public interface IModule extends ILabeled
{
    IToggleable isEnabled();
    
    Stream<ISetting<?>> getSettings();
}
