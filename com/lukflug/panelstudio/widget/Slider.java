//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.widget;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.base.*;
import java.awt.*;

public abstract class Slider extends FocusableComponent
{
    protected boolean attached;
    protected ISliderRenderer renderer;
    
    public Slider(final ILabeled label, final ISliderRenderer renderer) {
        super(label);
        this.attached = false;
        this.renderer = renderer;
    }
    
    public void render(final Context context) {
        super.render(context);
        if (this.attached) {
            final Rectangle rect = this.renderer.getSlideArea(context, this.getTitle(), this.getDisplayState());
            double value = (context.getInterface().getMouse().x - rect.x) / (double)(rect.width - 1);
            if (value < 0.0) {
                value = 0.0;
            }
            else if (value > 1.0) {
                value = 1.0;
            }
            this.setValue(value);
        }
        if (!context.getInterface().getButton(0)) {
            this.attached = false;
        }
        this.renderer.renderSlider(context, this.getTitle(), this.getDisplayState(), this.hasFocus(context), this.getValue());
    }
    
    public void handleButton(final Context context, final int button) {
        super.handleButton(context, button);
        if (button == 0 && context.isClicked(button) && this.renderer.getSlideArea(context, this.getTitle(), this.getDisplayState()).contains(context.getInterface().getMouse())) {
            this.attached = true;
        }
    }
    
    public void exit() {
        super.exit();
        this.attached = false;
    }
    
    protected int getHeight() {
        return this.renderer.getDefaultHeight();
    }
    
    protected abstract double getValue();
    
    protected abstract void setValue(final double p0);
    
    protected abstract String getDisplayState();
}
