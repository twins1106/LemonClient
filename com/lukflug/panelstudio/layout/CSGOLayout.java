//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import java.awt.*;
import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.container.*;
import java.util.stream.*;
import java.util.function.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.setting.*;

public class CSGOLayout implements ILayout, IScrollSize
{
    protected ILabeled label;
    protected Point position;
    protected int width;
    protected Supplier<Animation> animation;
    protected String enabledButton;
    protected boolean horizontal;
    protected boolean moduleColumn;
    protected int weight;
    protected ChildUtil.ChildMode colorType;
    protected ChildUtil util;
    
    public CSGOLayout(final ILabeled label, final Point position, final int width, final int popupWidth, final Supplier<Animation> animation, final String enabledButton, final boolean horizontal, final boolean moduleColumn, final int weight, final ChildUtil.ChildMode colorType, final PopupTuple popupType) {
        this.label = label;
        this.position = position;
        this.width = width;
        this.animation = animation;
        this.enabledButton = enabledButton;
        this.horizontal = horizontal;
        this.moduleColumn = moduleColumn;
        this.weight = weight;
        this.colorType = colorType;
        this.util = new ChildUtil(popupWidth, (Supplier)animation, popupType);
    }
    
    @Override
    public void populateGUI(final IComponentAdder gui, final IComponentGenerator components, final IClient client, final ITheme theme) {
        final Button<Void> title = new Button<Void>(this.label, () -> null, theme.getButtonRenderer(Void.class, 0, 0, true));
        final HorizontalContainer window = new HorizontalContainer(this.label, theme.getContainerRenderer(0, this.horizontal ? 1 : 0, true));
        if (this.horizontal) {
            final VerticalContainer container = new VerticalContainer(this.label, theme.getContainerRenderer(0, 0, false));
            final IEnumSetting catSelect = this.addContainer(this.label, client.getCategories().map(cat -> cat), (com.lukflug.panelstudio.container.IContainer<IComponent>)container, new ThemeTuple(theme, 0, 1), true, button -> button, () -> true);
            container.addComponent((IComponent)window);
            gui.addComponent(title, (IComponent)container, new ThemeTuple(theme, 0, 0), this.position, this.width, this.animation);
        }
        else {
            final IEnumSetting catSelect = this.addContainer(this.label, client.getCategories().map(cat -> cat), (com.lukflug.panelstudio.container.IContainer<IHorizontalComponent>)window, new ThemeTuple(theme, 0, 1), false, button -> this.wrapColumn(button, new ThemeTuple(theme, 0, 1), 1), () -> true);
            gui.addComponent(title, (IComponent)window, new ThemeTuple(theme, 0, 0), this.position, this.width, this.animation);
        }
        final HorizontalContainer window2;
        final Object o;
        IEnumSetting modSelect;
        VerticalContainer container2;
        final HorizontalContainer horizontalContainer;
        final Object o2;
        final Object o3;
        VerticalContainer categoryContent;
        int graphicalLevel;
        FocusableComponent moduleTitle;
        VerticalContainer moduleContainer;
        final VerticalContainer verticalContainer;
        client.getCategories().forEach(category -> {
            if (this.moduleColumn) {
                modSelect = this.addContainer(category, category.getModules().map(mod -> mod), (com.lukflug.panelstudio.container.IContainer<IHorizontalComponent>)window2, new ThemeTuple(theme, 1, 1), false, button -> this.wrapColumn(button, new ThemeTuple(theme, 0, 1), 1), () -> ((IEnumSetting)o).getValueName() == category.getDisplayName());
                category.getModules().forEach(module -> {
                    container2 = new VerticalContainer((ILabeled)module, theme.getContainerRenderer(1, 1, false));
                    horizontalContainer.addComponent((IComponent)this.wrapColumn((IComponent)container2, new ThemeTuple(theme, 1, 1), this.weight), () -> ((IEnumSetting)o2).getValueName() == category.getDisplayName() && ((IEnumSetting)o3).getValueName() == module.getDisplayName());
                    if (module.isEnabled() != null) {
                        container2.addComponent(components.getComponent(new IBooleanSetting() {
                            final /* synthetic */ IModule val$module;
                            
                            public String getDisplayName() {
                                return CSGOLayout.this.enabledButton;
                            }
                            
                            public void toggle() {
                                this.val$module.isEnabled().toggle();
                            }
                            
                            public boolean isOn() {
                                return this.val$module.isEnabled().isOn();
                            }
                        }, this.animation, gui, new ThemeTuple(theme, 1, 2), 2, false));
                    }
                    module.getSettings().forEach(setting -> this.addSettingsComponent(setting, container2, gui, components, new ThemeTuple(theme, 2, 2)));
                });
            }
            else {
                categoryContent = new VerticalContainer((ILabeled)category, theme.getContainerRenderer(0, 1, false));
                window2.addComponent((IComponent)this.wrapColumn((IComponent)categoryContent, new ThemeTuple(theme, 0, 1), 1), () -> ((IEnumSetting)o).getValueName() == category.getDisplayName());
                category.getModules().forEach(module -> {
                    graphicalLevel = 1;
                    if (module.isEnabled() == null) {
                        moduleTitle = new Button<Object>(module, () -> null, (IButtonRenderer<Object>)theme.getButtonRenderer(Void.class, 1, 1, true));
                    }
                    else {
                        moduleTitle = new ToggleButton(module, module.isEnabled(), theme.getButtonRenderer(Boolean.class, 1, 1, true));
                    }
                    moduleContainer = new VerticalContainer((ILabeled)module, theme.getContainerRenderer(1, graphicalLevel, false));
                    if (module.isEnabled() == null) {
                        this.util.addContainer((ILabeled)module, (IComponent)moduleTitle, (IComponent)moduleContainer, () -> null, (Class)Void.class, verticalContainer, gui, new ThemeTuple(theme, 1, graphicalLevel), ChildUtil.ChildMode.DOWN);
                    }
                    else {
                        this.util.addContainer((ILabeled)module, (IComponent)moduleTitle, (IComponent)moduleContainer, () -> module.isEnabled(), (Class)IBoolean.class, verticalContainer, gui, new ThemeTuple(theme, 1, graphicalLevel), ChildUtil.ChildMode.DOWN);
                    }
                    module.getSettings().forEach(setting -> this.addSettingsComponent(setting, moduleContainer, gui, components, new ThemeTuple(theme, 2, graphicalLevel + 1)));
                });
            }
        });
    }
    
    protected <T> void addSettingsComponent(final ISetting<T> setting, final VerticalContainer container, final IComponentAdder gui, final IComponentGenerator components, final ThemeTuple theme) {
        final int colorLevel = (this.colorType == ChildUtil.ChildMode.DOWN) ? theme.graphicalLevel : 0;
        final boolean isContainer = setting.getSubSettings() != null;
        final IComponent component = components.getComponent(setting, this.animation, gui, theme, colorLevel, isContainer);
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
    
    protected <T extends IComponent> IEnumSetting addContainer(final ILabeled label, final Stream<ILabeled> labels, final IContainer<T> window, final ThemeTuple theme, final boolean horizontal, final Function<RadioButton, T> container, final IBoolean visible) {
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
        final RadioButton button = new RadioButton(setting, theme.getRadioRenderer(true), (Animation)this.animation.get(), horizontal) {
            @Override
            protected boolean isUpKey(final int key) {
                if (this.horizontal) {
                    return CSGOLayout.this.isLeftKey(key);
                }
                return CSGOLayout.this.isUpKey(key);
            }
            
            @Override
            protected boolean isDownKey(final int key) {
                if (this.horizontal) {
                    return CSGOLayout.this.isRightKey(key);
                }
                return CSGOLayout.this.isDownKey(key);
            }
        };
        window.addComponent((IComponent)container.apply(button), visible);
        return setting;
    }
    
    protected HorizontalComponent<ScrollBarComponent<Void, IComponent>> wrapColumn(final IComponent button, final ThemeTuple theme, final int weight) {
        return (HorizontalComponent<ScrollBarComponent<Void, IComponent>>)new HorizontalComponent((IComponent)new ScrollBarComponent<Void, IComponent>(button, theme.getScrollBarRenderer(Void.class), theme.getEmptySpaceRenderer(Void.class, false), theme.getEmptySpaceRenderer(Void.class, true)) {
            public int getScrollHeight(final Context context, final int componentHeight) {
                return CSGOLayout.this.getScrollHeight(context, componentHeight);
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
