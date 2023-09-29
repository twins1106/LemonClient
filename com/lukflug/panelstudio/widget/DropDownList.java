//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.container.*;
import java.util.concurrent.atomic.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;
import java.util.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;
import java.util.function.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.popup.*;

public abstract class DropDownList extends HorizontalContainer
{
    private Rectangle rect;
    private boolean transferFocus;
    protected IToggleable toggle;
    
    public DropDownList(final IEnumSetting setting, final ThemeTuple theme, final boolean container, final boolean allowSearch, final ITextFieldKeys keys, final IScrollSize popupSize, final Consumer<IFixedComponent> popupAdder) {
        super((ILabeled)setting, (IContainerRenderer)new IContainerRenderer() {});
        this.rect = new Rectangle();
        this.transferFocus = false;
        this.toggle = (IToggleable)new SimpleToggleable(false);
        final AtomicReference<String> searchTerm = new AtomicReference<String>(null);
        final TextField textField = new TextField(new IStringSetting() {
            public String getDisplayName() {
                return setting.getDisplayName();
            }
            
            public String getValue() {
                final String returnValue = (allowSearch && DropDownList.this.toggle.isOn()) ? searchTerm.get() : setting.getValueName();
                searchTerm.set(returnValue);
                return returnValue;
            }
            
            public void setValue(final String string) {
                searchTerm.set(string);
            }
        }, keys, 0, new SimpleToggleable(false), theme.getTextRenderer(true, container)) {
            @Override
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                DropDownList.this.rect = this.renderer.getTextArea(context, this.getTitle());
                if (button == 0 && context.isClicked(button)) {
                    DropDownList.this.transferFocus = true;
                }
            }
            
            public boolean hasFocus(final Context context) {
                return super.hasFocus(context) || DropDownList.this.toggle.isOn();
            }
            
            @Override
            public boolean allowCharacter(final char character) {
                return DropDownList.this.allowCharacter(character);
            }
        };
        this.addComponent((IComponent)new HorizontalComponent((IComponent)textField, 0, 1));
        final ThemeTuple popupTheme = new ThemeTuple(theme.theme, theme.logicalLevel, 0);
        final Button<Void> title = (Button<Void>)new Button((ILabeled)new Labeled("", (String)null, () -> false), () -> null, popupTheme.getButtonRenderer((Class)Void.class, false));
        final RadioButton content = new RadioButton(new IEnumSetting() {
            ILabeled[] values = Arrays.stream(setting.getAllowedValues()).map(value -> new Labeled(value.getDisplayName(), value.getDescription(), () -> value.isVisible().isOn() && (!allowSearch || value.getDisplayName().toUpperCase().contains(searchTerm.get().toUpperCase())))).toArray(ILabeled[]::new);
            
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
        }, popupTheme.getRadioRenderer(false), this.getAnimation(), false) {
            @Override
            protected boolean isUpKey(final int key) {
                return DropDownList.this.isUpKey(key);
            }
            
            @Override
            protected boolean isDownKey(final int key) {
                return DropDownList.this.isDownKey(key);
            }
        };
        final IFixedComponent popup = (IFixedComponent)ClosableComponent.createStaticPopup((IComponent)title, (IComponent)content, () -> null, this.getAnimation(), new RendererTuple((Class)Void.class, popupTheme), popupSize, this.toggle, () -> this.rect.width, false, "", true);
        popupAdder.accept(popup);
        final IPopupPositioner positioner = (IPopupPositioner)new IPopupPositioner() {
            public Point getPosition(final IInterface inter, final Dimension popup, final Rectangle component, final Rectangle panel) {
                return new Point(component.x, component.y + component.height);
            }
        };
        final Button<Void> button = new Button<Void>(new Labeled((String)null, (String)null, () -> true), () -> null, theme.getSmallButtonRenderer(7, container)) {
            public void handleButton(final Context context, final int button) {
                super.handleButton(context, button);
                DropDownList.this.rect = new Rectangle(DropDownList.this.rect.x, context.getPos().y, context.getPos().x + context.getSize().width - DropDownList.this.rect.x, context.getSize().height);
                if ((button == 0 && context.isClicked(button)) || DropDownList.this.transferFocus) {
                    context.getPopupDisplayer().displayPopup((IPopup)popup, DropDownList.this.rect, DropDownList.this.toggle, positioner);
                    DropDownList.this.transferFocus = false;
                }
            }
            
            public int getHeight() {
                return textField.getHeight();
            }
        };
        this.addComponent((IComponent)new HorizontalComponent((IComponent)button, textField.getHeight(), 0));
    }
    
    public void handleKey(final Context context, final int scancode) {
        super.handleKey(context, scancode);
        if (this.toggle.isOn() && this.isEnterKey(scancode)) {
            this.toggle.toggle();
        }
    }
    
    protected abstract Animation getAnimation();
    
    public abstract boolean allowCharacter(final char p0);
    
    protected abstract boolean isUpKey(final int p0);
    
    protected abstract boolean isDownKey(final int p0);
    
    protected abstract boolean isEnterKey(final int p0);
}
