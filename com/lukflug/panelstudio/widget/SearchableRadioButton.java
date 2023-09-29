//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.container.*;
import java.util.concurrent.atomic.*;
import com.lukflug.panelstudio.component.*;
import java.util.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;

public abstract class SearchableRadioButton extends VerticalContainer
{
    protected boolean transferFocus;
    
    public SearchableRadioButton(final IEnumSetting setting, final ThemeTuple theme, final boolean container, final ITextFieldKeys keys) {
        super((ILabeled)setting, (IContainerRenderer)new IContainerRenderer() {});
        this.transferFocus = false;
        final AtomicReference<String> searchTerm = new AtomicReference<String>("");
        final TextField textField = new TextField(new IStringSetting() {
            public String getDisplayName() {
                return setting.getDisplayName();
            }
            
            public String getValue() {
                return searchTerm.get();
            }
            
            public void setValue(final String string) {
                searchTerm.set(string);
            }
        }, keys, 0, new SimpleToggleable(false), theme.getTextRenderer(true, container)) {
            @Override
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                if (this.hasFocus(context)) {
                    SearchableRadioButton.this.transferFocus = true;
                }
            }
            
            @Override
            public boolean allowCharacter(final char character) {
                return SearchableRadioButton.this.allowCharacter(character);
            }
        };
        this.addComponent((IComponent)textField);
        final RadioButton content = new RadioButton(new IEnumSetting() {
            ILabeled[] values = Arrays.stream(setting.getAllowedValues()).map(value -> new Labeled(value.getDisplayName(), value.getDescription(), () -> value.isVisible().isOn() && value.getDisplayName().toUpperCase().contains(searchTerm.get().toUpperCase()))).toArray(ILabeled[]::new);
            
            public String getDisplayName() {
                return setting.getDisplayName();
            }
            
            public String getDescription() {
                return setting.getDescription();
            }
            
            public IBoolean isVisible() {
                return setting.isVisible();
            }
            
            public void increment() {
                setting.increment();
            }
            
            public void decrement() {
                setting.decrement();
            }
            
            public String getValueName() {
                return setting.getValueName();
            }
            
            public void setValueIndex(final int index) {
                setting.setValueIndex(index);
            }
            
            public ILabeled[] getAllowedValues() {
                return this.values;
            }
        }, theme.getRadioRenderer(container), this.getAnimation(), false) {
            protected boolean isUpKey(final int key) {
                return SearchableRadioButton.this.isUpKey(key);
            }
            
            protected boolean isDownKey(final int key) {
                return SearchableRadioButton.this.isDownKey(key);
            }
        };
        this.addComponent((IComponent)content);
    }
    
    protected abstract Animation getAnimation();
    
    public abstract boolean allowCharacter(final char p0);
    
    protected abstract boolean isUpKey(final int p0);
    
    protected abstract boolean isDownKey(final int p0);
}
