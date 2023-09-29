//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import java.util.concurrent.atomic.*;
import java.awt.*;

public class VerticalContainer extends Container<IComponent>
{
    public VerticalContainer(final ILabeled label, final IContainerRenderer renderer) {
        super(label, renderer);
    }
    
    protected void doContextSensitiveLoop(final Context context, final Container.ContextSensitiveConsumer<IComponent> function) {
        final AtomicInteger posy = new AtomicInteger(this.renderer.getTop());
        final AtomicInteger atomicInteger;
        final Context subContext;
        this.doContextlessLoop(component -> {
            subContext = this.getSubContext(context, atomicInteger.get());
            function.accept(subContext, component);
            if (subContext.focusReleased()) {
                context.releaseFocus();
            }
            else if (subContext.foucsRequested()) {
                context.requestFocus();
            }
            atomicInteger.addAndGet(subContext.getSize().height + this.renderer.getBorder());
            return;
        });
        context.setHeight(posy.get() - this.renderer.getBorder() + this.renderer.getBottom());
    }
    
    protected Context getSubContext(final Context context, final int posy) {
        return new Context(context, context.getSize().width - this.renderer.getLeft() - this.renderer.getRight(), new Point(this.renderer.getLeft(), posy), this.hasFocus(context), true);
    }
    
    protected boolean hasFocus(final Context context) {
        return context.hasFocus();
    }
}
