//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.container;

import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.base.*;
import java.util.concurrent.atomic.*;
import com.lukflug.panelstudio.component.*;
import java.awt.*;

public class HorizontalContainer extends Container<IHorizontalComponent>
{
    public HorizontalContainer(final ILabeled label, final IContainerRenderer renderer) {
        super(label, renderer);
    }
    
    protected void doContextSensitiveLoop(final Context context, final Container.ContextSensitiveConsumer<IHorizontalComponent> function) {
        final AtomicInteger availableWidth = new AtomicInteger(context.getSize().width - this.renderer.getLeft() - this.renderer.getRight() + this.renderer.getBorder());
        final AtomicInteger totalWeight = new AtomicInteger(0);
        final AtomicInteger atomicInteger;
        final AtomicInteger atomicInteger2;
        this.doContextlessLoop(component -> {
            atomicInteger.addAndGet(-component.getWidth(context.getInterface()) - this.renderer.getBorder());
            atomicInteger2.addAndGet(component.getWeight());
            return;
        });
        final double weightFactor = availableWidth.get() / (double)totalWeight.get();
        final AtomicInteger x = new AtomicInteger(this.renderer.getLeft());
        final AtomicInteger spentWeight = new AtomicInteger(0);
        final AtomicInteger height = new AtomicInteger(0);
        final AtomicInteger atomicInteger3;
        final double n;
        final int start;
        final int end;
        final int componentWidth;
        final AtomicInteger atomicInteger4;
        final int componentPosition;
        final Context subContext;
        final AtomicInteger atomicInteger5;
        this.doContextlessLoop(component -> {
            start = (int)Math.round(atomicInteger3.get() * n);
            end = (int)Math.round((atomicInteger3.get() + component.getWeight()) * n);
            componentWidth = component.getWidth(context.getInterface()) + end - start;
            componentPosition = atomicInteger4.get() + start;
            subContext = this.getSubContext(context, componentPosition, componentWidth);
            function.accept(subContext, (IComponent)component);
            if (subContext.focusReleased()) {
                context.releaseFocus();
            }
            else if (subContext.foucsRequested()) {
                context.requestFocus();
            }
            atomicInteger4.addAndGet(component.getWidth(context.getInterface()) + this.renderer.getBorder());
            atomicInteger3.addAndGet(component.getWeight());
            if (subContext.getSize().height > atomicInteger5.get()) {
                atomicInteger5.set(subContext.getSize().height);
            }
            return;
        });
        context.setHeight(height.get());
    }
    
    protected Context getSubContext(final Context context, final int posx, final int width) {
        return new Context(context, width, new Point(posx, this.renderer.getTop()), context.hasFocus(), true);
    }
}
