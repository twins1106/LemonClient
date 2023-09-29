//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import java.util.function.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.theme.*;

public interface IComponentGenerator
{
    default IComponent getComponent(final ISetting<?> setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        if (setting instanceof IBooleanSetting) {
            return this.getBooleanComponent((IBooleanSetting)setting, animation, adder, theme, colorLevel, isContainer);
        }
        if (setting instanceof INumberSetting) {
            return this.getNumberComponent((INumberSetting)setting, animation, adder, theme, colorLevel, isContainer);
        }
        if (setting instanceof IEnumSetting) {
            return this.getEnumComponent((IEnumSetting)setting, animation, adder, theme, colorLevel, isContainer);
        }
        if (setting instanceof IColorSetting) {
            return this.getColorComponent((IColorSetting)setting, animation, adder, theme, colorLevel, isContainer);
        }
        if (setting instanceof IKeybindSetting) {
            return this.getKeybindComponent((IKeybindSetting)setting, animation, adder, theme, colorLevel, isContainer);
        }
        if (setting instanceof IStringSetting) {
            return this.getStringComponent((IStringSetting)setting, animation, adder, theme, colorLevel, isContainer);
        }
        return (IComponent)new Button(setting, () -> null, (IButtonRenderer<Object>)theme.getButtonRenderer(Void.class, isContainer));
    }
    
    default IComponent getBooleanComponent(final IBooleanSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new ToggleButton(setting, theme.getButtonRenderer(Boolean.class, isContainer));
    }
    
    default IComponent getNumberComponent(final INumberSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new NumberSlider(setting, theme.getSliderRenderer(isContainer));
    }
    
    default IComponent getEnumComponent(final IEnumSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new CycleButton(setting, theme.getButtonRenderer(String.class, isContainer));
    }
    
    default IComponent getColorComponent(final IColorSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new ColorSliderComponent(setting, new ThemeTuple(theme.theme, theme.logicalLevel, colorLevel));
    }
    
    default IComponent getKeybindComponent(final IKeybindSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new KeybindComponent(setting, theme.getKeybindRenderer(isContainer));
    }
    
    default IComponent getStringComponent(final IStringSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
        return (IComponent)new TextField(setting, new ITextFieldKeys() {
            @Override
            public boolean isBackspaceKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isDeleteKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isInsertKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isLeftKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isRightKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isHomeKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isEndKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isCopyKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isPasteKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isCutKey(final int scancode) {
                return false;
            }
            
            @Override
            public boolean isAllKey(final int scancode) {
                return false;
            }
        }, 0, new SimpleToggleable(false), theme.getTextRenderer(false, isContainer)) {
            @Override
            public boolean allowCharacter(final char character) {
                return false;
            }
        };
    }
}
