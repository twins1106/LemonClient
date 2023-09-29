//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import java.util.function.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.theme.*;

public class ComponentGenerator implements IComponentGenerator
{
    protected final IntPredicate keybindKey;
    protected final IntPredicate charFilter;
    protected final ITextFieldKeys keys;
    
    public ComponentGenerator(final IntPredicate keybindKey, final IntPredicate charFilter, final ITextFieldKeys keys) {
        this.keybindKey = keybindKey;
        this.charFilter = charFilter;
        this.keys = keys;
    }
    
    @Override
    public IComponent getKeybindComponent(final IKeybindSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new KeybindComponent(setting, theme.getKeybindRenderer(isContainer)) {
            public int transformKey(final int scancode) {
                return ComponentGenerator.this.keybindKey.test(scancode) ? 0 : scancode;
            }
        };
    }
    
    @Override
    public IComponent getStringComponent(final IStringSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new TextField(setting, this.keys, 0, new SimpleToggleable(false), theme.getTextRenderer(false, isContainer)) {
            @Override
            public boolean allowCharacter(final char character) {
                return ComponentGenerator.this.charFilter.test(character);
            }
        };
    }
}
