//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.component;

import com.lukflug.panelstudio.base.*;
import java.util.function.*;

@FunctionalInterface
public interface IComponentProxy<T extends IComponent> extends IComponent
{
    default String getTitle() {
        return this.getComponent().getTitle();
    }
    
    default void render(final Context context) {
        this.doOperation(context, this.getComponent()::render);
    }
    
    default void handleButton(final Context context, final int button) {
        this.doOperation(context, subContext -> this.getComponent().handleButton(subContext, button));
    }
    
    default void handleKey(final Context context, final int scancode) {
        this.doOperation(context, subContext -> this.getComponent().handleKey(subContext, scancode));
    }
    
    default void handleChar(final Context context, final char character) {
        this.doOperation(context, subContext -> this.getComponent().handleChar(subContext, character));
    }
    
    default void handleScroll(final Context context, final int diff) {
        this.doOperation(context, subContext -> this.getComponent().handleScroll(subContext, diff));
    }
    
    default void getHeight(final Context context) {
        this.doOperation(context, this.getComponent()::getHeight);
    }
    
    default void enter() {
        this.getComponent().enter();
    }
    
    default void exit() {
        this.getComponent().exit();
    }
    
    default void releaseFocus() {
        this.getComponent().releaseFocus();
    }
    
    default boolean isVisible() {
        return this.getComponent().isVisible();
    }
    
    T getComponent();
    
    default Context doOperation(final Context context, final Consumer<Context> operation) {
        final Context subContext = this.getContext(context);
        operation.accept(subContext);
        if (subContext != context) {
            if (subContext.focusReleased()) {
                context.releaseFocus();
            }
            else if (subContext.foucsRequested()) {
                context.requestFocus();
            }
            context.setHeight(this.getHeight(subContext.getSize().height));
            if (subContext.getDescription() != null) {
                context.setDescription(subContext.getDescription());
            }
        }
        return subContext;
    }
    
    default int getHeight(final int height) {
        return height;
    }
    
    default Context getContext(final Context context) {
        return context;
    }
}
