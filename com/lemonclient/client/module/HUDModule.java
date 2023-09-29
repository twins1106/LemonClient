//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module;

import com.lukflug.panelstudio.component.*;
import java.awt.*;
import com.lukflug.panelstudio.theme.*;
import com.lemonclient.client.*;
import com.lemonclient.client.clickgui.*;
import com.lukflug.panelstudio.base.*;
import java.lang.annotation.*;

public abstract class HUDModule extends Module
{
    public static final int LIST_BORDER = 1;
    protected IFixedComponent component;
    protected Point position;
    
    public HUDModule() {
        this.position = new Point(this.getDeclaration().posX(), this.getDeclaration().posZ());
    }
    
    private Declaration getDeclaration() {
        return this.getClass().getAnnotation(Declaration.class);
    }
    
    public abstract void populate(final ITheme p0);
    
    public IFixedComponent getComponent() {
        return this.component;
    }
    
    public void resetPosition() {
        final IFixedComponent component = this.component;
        final LemonClientGUI gameSenseGUI = LemonClient.INSTANCE.gameSenseGUI;
        component.setPosition(LemonClientGUI.guiInterface, this.position);
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface Declaration {
        int posX();
        
        int posZ();
    }
}
