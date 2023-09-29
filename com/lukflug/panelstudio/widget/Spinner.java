//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.setting.*;
import java.util.function.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;

public class Spinner extends HorizontalContainer
{
    public Spinner(final INumberSetting setting, final ThemeTuple theme, final boolean container, final boolean allowInput, final ITextFieldKeys keys) {
        super((ILabeled)setting, (IContainerRenderer)new IContainerRenderer() {});
        final TextField textField = new TextField(new IStringSetting() {
            private String value = null;
            private long lastTime;
            
            public String getDisplayName() {
                return setting.getDisplayName();
            }
            
            public String getValue() {
                if (this.value != null && System.currentTimeMillis() - this.lastTime > 500L) {
                    if (this.value.isEmpty()) {
                        this.value = "0";
                    }
                    if (this.value.endsWith(".")) {
                        this.value += '0';
                    }
                    double number = Double.parseDouble(this.value);
                    if (number > setting.getMaximumValue()) {
                        number = setting.getMaximumValue();
                    }
                    else if (number < setting.getMinimumValue()) {
                        number = setting.getMinimumValue();
                    }
                    setting.setNumber(number);
                    this.value = null;
                }
                if (this.value == null) {
                    return setting.getSettingState();
                }
                return this.value;
            }
            
            public void setValue(final String string) {
                if (this.value == null) {
                    this.lastTime = System.currentTimeMillis();
                }
                this.value = new String(string);
            }
        }, keys, 0, new SimpleToggleable(false), theme.getTextRenderer(true, container)) {
            @Override
            public boolean allowCharacter(final char character) {
                return allowInput && ((character >= '0' && character <= '9') || (character == '.' && !this.setting.getSettingState().contains(".")));
            }
        };
        this.addComponent((IComponent)new HorizontalComponent((IComponent)textField, 0, 1));
        final VerticalContainer buttons = new VerticalContainer((ILabeled)setting, (IContainerRenderer)new IContainerRenderer() {});
        buttons.addComponent((IComponent)new Button<Void>(new Labeled((String)null, (String)null, () -> true), () -> null, theme.getSmallButtonRenderer(6, container)) {
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                if (button == 0 && context.isClicked(button)) {
                    double number = setting.getNumber();
                    number += Math.pow(10.0, -setting.getPrecision());
                    if (number <= setting.getMaximumValue()) {
                        setting.setNumber(number);
                    }
                }
            }
            
            public int getHeight() {
                return textField.getHeight() / 2;
            }
        });
        buttons.addComponent((IComponent)new Button<Void>(new Labeled((String)null, (String)null, () -> true), () -> null, theme.getSmallButtonRenderer(7, container)) {
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                if (button == 0 && context.isClicked(button)) {
                    double number = setting.getNumber();
                    number -= Math.pow(10.0, -setting.getPrecision());
                    if (number >= setting.getMinimumValue()) {
                        setting.setNumber(number);
                    }
                }
            }
            
            public int getHeight() {
                return textField.getHeight() / 2;
            }
        });
        this.addComponent((IComponent)new HorizontalComponent((IComponent)buttons, textField.getHeight(), 0));
    }
}
