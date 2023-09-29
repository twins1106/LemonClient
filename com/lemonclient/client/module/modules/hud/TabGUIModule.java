//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lukflug.panelstudio.theme.*;
import java.awt.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.setting.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.clickgui.*;
import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.tabgui.*;
import com.lukflug.panelstudio.base.*;

@Module.Declaration(name = "TabGUI", category = Category.HUD)
@HUDModule.Declaration(posX = 10, posZ = 10)
public class TabGUIModule extends HUDModule
{
    private ITabGUITheme theme;
    
    public TabGUIModule() {
        this.theme = new StandardTheme(new IColorScheme() {
            @Override
            public void createSetting(final ITheme theme, final String name, final String description, final boolean hasAlpha, final boolean allowsRainbow, final Color color, final boolean rainbow) {
                final ColorSetting setting = new ColorSetting(name, name.replace(" ", ""), (Module)TabGUIModule.this, () -> true, rainbow, allowsRainbow, hasAlpha, new GSColor(color));
                SettingsManager.addSetting((Setting)setting);
            }
            
            @Override
            public Color getColor(final String name) {
                return (Color)((ColorSetting)SettingsManager.getSettingsForModule((Module)TabGUIModule.this).stream().filter(setting -> setting.getName() == name).findFirst().orElse(null)).getValue();
            }
        }, 75, 9, 2, 5);
    }
    
    public void populate(final ITheme theme) {
        final ClickGuiModule clickGuiModule = (ClickGuiModule)ModuleManager.getModule((Class)ClickGuiModule.class);
        final TabGUI tabgui = new TabGUI(() -> "TabGUI", LemonClientGUI.client, this.theme, (IContainer<? super FixedComponent<Tab>>)new IContainer<IFixedComponent>() {
            @Override
            public boolean addComponent(final IFixedComponent component) {
                return LemonClientGUI.gui.addHUDComponent(component, () -> true);
            }
            
            @Override
            public boolean addComponent(final IFixedComponent component, final IBoolean visible) {
                return LemonClientGUI.gui.addHUDComponent(component, visible);
            }
            
            @Override
            public boolean removeComponent(final IFixedComponent component) {
                return LemonClientGUI.gui.removeComponent(component);
            }
        }, () -> new SettingsAnimation(() -> (Integer)clickGuiModule.animationSpeed.getValue(), () -> LemonClientGUI.guiInterface.getTime()), key -> key == 200, key -> key == 208, key -> key == 28 || key == 205, key -> key == 203, this.position, this.getName());
        this.component = tabgui.getWrappedComponent();
    }
}
