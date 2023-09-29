//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import java.awt.*;
import java.util.*;
import com.lukflug.panelstudio.popup.*;
import java.util.stream.*;
import com.lukflug.panelstudio.container.*;
import java.util.function.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;

public class SearchableLayout implements ILayout, IScrollSize
{
    protected ILabeled titleLabel;
    protected ILabeled searchLabel;
    protected Point position;
    protected int width;
    protected Supplier<Animation> animation;
    protected String enabledButton;
    protected int weight;
    protected ChildUtil.ChildMode colorType;
    protected ChildUtil util;
    protected Comparator<IModule> comparator;
    protected IntPredicate charFilter;
    protected ITextFieldKeys keys;
    
    public SearchableLayout(final ILabeled titleLabel, final ILabeled searchLabel, final Point position, final int width, final int popupWidth, final Supplier<Animation> animation, final String enabledButton, final int weight, final ChildUtil.ChildMode colorType, final PopupTuple popupType, final Comparator<IModule> comparator, final IntPredicate charFilter, final ITextFieldKeys keys) {
        this.titleLabel = titleLabel;
        this.searchLabel = searchLabel;
        this.position = position;
        this.width = width;
        this.animation = animation;
        this.enabledButton = enabledButton;
        this.weight = weight;
        this.colorType = colorType;
        this.comparator = comparator;
        this.charFilter = charFilter;
        this.keys = keys;
        this.util = new ChildUtil(popupWidth, (Supplier)animation, popupType);
    }
    
    public void populateGUI(final IComponentAdder gui, final IComponentGenerator components, final IClient client, final ITheme theme) {
        final Button<Void> title = new Button<Void>(this.titleLabel, () -> null, theme.getButtonRenderer(Void.class, 0, 0, true));
        final HorizontalContainer window = new HorizontalContainer(this.titleLabel, theme.getContainerRenderer(0, 0, true));
        final Supplier<Stream<IModule>> modules = () -> client.getCategories().flatMap(cat -> cat.getModules()).sorted(this.comparator);
        final IEnumSetting modSelect = this.addContainer(this.searchLabel, modules.get().map(mod -> mod), (com.lukflug.panelstudio.container.IContainer<IHorizontalComponent>)window, new ThemeTuple(theme, 0, 1), false, button -> this.wrapColumn(button, new ThemeTuple(theme, 0, 1), 1), () -> true);
        gui.addComponent((IComponent)title, (IComponent)window, new ThemeTuple(theme, 0, 0), this.position, this.width, (Supplier)this.animation);
        final VerticalContainer container;
        final HorizontalContainer horizontalContainer;
        final Object o;
        modules.get().forEach(module -> {
            container = new VerticalContainer((ILabeled)module, theme.getContainerRenderer(1, 1, false));
            horizontalContainer.addComponent((IComponent)this.wrapColumn((IComponent)container, new ThemeTuple(theme, 1, 1), this.weight), () -> ((IEnumSetting)o).getValueName() == module.getDisplayName());
            if (module.isEnabled() != null) {
                container.addComponent(components.getComponent((ISetting)new IBooleanSetting() {
                    final /* synthetic */ IModule val$module;
                    
                    public String getDisplayName() {
                        return SearchableLayout.this.enabledButton;
                    }
                    
                    public void toggle() {
                        this.val$module.isEnabled().toggle();
                    }
                    
                    public boolean isOn() {
                        return this.val$module.isEnabled().isOn();
                    }
                }, (Supplier)this.animation, gui, new ThemeTuple(theme, 1, 2), 2, false));
            }
            module.getSettings().forEach(setting -> this.addSettingsComponent(setting, container, gui, components, new ThemeTuple(theme, 2, 2)));
        });
    }
    
    protected <T> void addSettingsComponent(final ISetting<T> setting, final VerticalContainer container, final IComponentAdder gui, final IComponentGenerator components, final ThemeTuple theme) {
        final int colorLevel = (this.colorType == ChildUtil.ChildMode.DOWN) ? theme.graphicalLevel : 0;
        final boolean isContainer = setting.getSubSettings() != null;
        final IComponent component = components.getComponent((ISetting)setting, (Supplier)this.animation, gui, theme, colorLevel, isContainer);
        if (component instanceof VerticalContainer) {
            final VerticalContainer colorContainer = (VerticalContainer)component;
            final Button<T> button = new Button<T>(setting, () -> setting.getSettingState(), theme.getButtonRenderer(setting.getSettingClass(), this.colorType == ChildUtil.ChildMode.DOWN));
            this.util.addContainer((ILabeled)setting, (IComponent)button, (IComponent)colorContainer, () -> setting.getSettingState(), (Class)setting.getSettingClass(), container, gui, new ThemeTuple(theme.theme, theme.logicalLevel, colorLevel), this.colorType);
            if (setting.getSubSettings() != null) {
                setting.getSubSettings().forEach(subSetting -> this.addSettingsComponent(subSetting, colorContainer, gui, components, new ThemeTuple(theme.theme, theme.logicalLevel + 1, colorLevel + 1)));
            }
        }
        else if (setting.getSubSettings() != null) {
            final VerticalContainer settingContainer = new VerticalContainer((ILabeled)setting, theme.getContainerRenderer(false));
            this.util.addContainer((ILabeled)setting, component, (IComponent)settingContainer, () -> setting.getSettingState(), (Class)setting.getSettingClass(), container, gui, theme, ChildUtil.ChildMode.DOWN);
            setting.getSubSettings().forEach(subSetting -> this.addSettingsComponent(subSetting, settingContainer, gui, components, new ThemeTuple(theme, 1, 1)));
        }
        else {
            container.addComponent(component);
        }
    }
    
    protected <T extends IComponent> IEnumSetting addContainer(final ILabeled label, final Stream<ILabeled> labels, final IContainer<T> window, final ThemeTuple theme, final boolean horizontal, final Function<SearchableRadioButton, T> container, final IBoolean visible) {
        final IEnumSetting setting = new IEnumSetting() {
            private int state = 0;
            private ILabeled[] array = labels.toArray(ILabeled[]::new);
            
            @Override
            public String getDisplayName() {
                return label.getDisplayName();
            }
            
            @Override
            public String getDescription() {
                return label.getDescription();
            }
            
            @Override
            public IBoolean isVisible() {
                return label.isVisible();
            }
            
            @Override
            public void increment() {
                this.state = (this.state + 1) % this.array.length;
            }
            
            @Override
            public void decrement() {
                --this.state;
                if (this.state < 0) {
                    this.state = this.array.length - 1;
                }
            }
            
            @Override
            public String getValueName() {
                return this.array[this.state].getDisplayName();
            }
            
            @Override
            public void setValueIndex(final int index) {
                this.state = index;
            }
            
            @Override
            public int getValueIndex() {
                return this.state;
            }
            
            @Override
            public ILabeled[] getAllowedValues() {
                return this.array;
            }
        };
        final SearchableRadioButton button = new SearchableRadioButton(setting, theme, true, this.keys) {
            @Override
            protected Animation getAnimation() {
                return SearchableLayout.this.animation.get();
            }
            
            @Override
            public boolean allowCharacter(final char character) {
                return SearchableLayout.this.charFilter.test(character);
            }
            
            @Override
            protected boolean isUpKey(final int key) {
                if (horizontal) {
                    return SearchableLayout.this.isLeftKey(key);
                }
                return SearchableLayout.this.isUpKey(key);
            }
            
            @Override
            protected boolean isDownKey(final int key) {
                if (horizontal) {
                    return SearchableLayout.this.isRightKey(key);
                }
                return SearchableLayout.this.isDownKey(key);
            }
        };
        window.addComponent((IComponent)container.apply(button), visible);
        return setting;
    }
    
    protected HorizontalComponent<ScrollBarComponent<Void, IComponent>> wrapColumn(final IComponent button, final ThemeTuple theme, final int weight) {
        return (HorizontalComponent<ScrollBarComponent<Void, IComponent>>)new HorizontalComponent((IComponent)new ScrollBarComponent<Void, IComponent>(button, theme.getScrollBarRenderer(Void.class), theme.getEmptySpaceRenderer(Void.class, false), theme.getEmptySpaceRenderer(Void.class, true)) {
            public int getScrollHeight(final Context context, final int componentHeight) {
                return SearchableLayout.this.getScrollHeight(context, componentHeight);
            }
            
            @Override
            protected Void getState() {
                return null;
            }
        }, 0, weight);
    }
    
    protected boolean isUpKey(final int key) {
        return false;
    }
    
    protected boolean isDownKey(final int key) {
        return false;
    }
    
    protected boolean isLeftKey(final int key) {
        return false;
    }
    
    protected boolean isRightKey(final int key) {
        return false;
    }
}
