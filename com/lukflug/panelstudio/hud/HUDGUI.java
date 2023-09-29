//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.hud;

import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.theme.*;

public class HUDGUI extends GUI
{
    protected IToggleable guiVisibility;
    protected IToggleable hudVisibility;
    
    public HUDGUI(final IInterface inter, final IDescriptionRenderer descriptionRenderer, final IPopupPositioner descriptionPosition, final IToggleable guiVisibility, final IToggleable hudVisibility) {
        super(inter, descriptionRenderer, descriptionPosition);
        this.guiVisibility = guiVisibility;
        this.hudVisibility = hudVisibility;
    }
    
    public boolean addComponent(final IFixedComponent component) {
        return this.container.addComponent((IComponent)component, (IBoolean)this.guiVisibility);
    }
    
    public boolean addComponent(final IFixedComponent component, final IBoolean visible) {
        return this.container.addComponent((IComponent)component, () -> this.guiVisibility.isOn() && visible.isOn());
    }
    
    public boolean addHUDComponent(final IFixedComponent component, final IBoolean visible) {
        return this.container.addComponent((IComponent)component, visible);
    }
    
    public boolean addHUDComponent(final IFixedComponent component, final IToggleable state, final Animation animation, final ITheme theme, final int border) {
        return this.container.addComponent((IComponent)new HUDPanel(component, state, animation, theme, (IBoolean)this.hudVisibility, border), () -> true);
    }
    
    public IToggleable getGUIVisibility() {
        return this.guiVisibility;
    }
    
    public IToggleable getHUDVisibility() {
        return this.hudVisibility;
    }
}
