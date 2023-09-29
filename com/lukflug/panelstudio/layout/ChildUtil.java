//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import java.util.function.*;
import com.lukflug.panelstudio.container.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.component.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.popup.*;

public class ChildUtil
{
    protected int width;
    protected Supplier<Animation> animation;
    protected PopupTuple popupType;
    
    public ChildUtil(final int width, final Supplier<Animation> animation, final PopupTuple popupType) {
        this.width = width;
        this.animation = animation;
        this.popupType = popupType;
    }
    
    protected <T> void addContainer(final ILabeled label, final IComponent title, final IComponent container, final Supplier<T> state, final Class<T> stateClass, final VerticalContainer parent, final IComponentAdder gui, final ThemeTuple theme, final ChildMode mode) {
        final boolean drawTitle = mode == ChildMode.DRAG_POPUP;
        switch (mode) {
            case DOWN: {
                parent.addComponent((IComponent)new ClosableComponent(title, container, (Supplier<U>)state, new AnimatedToggleable((IToggleable)new SimpleToggleable(false), (Animation)this.animation.get()), (IPanelRenderer<U>)theme.getPanelRenderer(stateClass), false));
                break;
            }
            case POPUP:
            case DRAG_POPUP: {
                final IToggleable toggle = (IToggleable)new SimpleToggleable(false);
                final Button<T> button = new Button<T>(new Labeled(label.getDisplayName(), label.getDescription(), () -> drawTitle && label.isVisible().isOn()), state, theme.getButtonRenderer(stateClass, true));
                IFixedComponent popup;
                if (this.popupType.dynamicPopup) {
                    popup = (IFixedComponent)ClosableComponent.createDynamicPopup(button, container, state, this.animation.get(), new RendererTuple<T>(stateClass, theme), this.popupType.popupSize, toggle, this.width);
                }
                else {
                    popup = (IFixedComponent)ClosableComponent.createStaticPopup(button, container, state, this.animation.get(), new RendererTuple<T>(stateClass, theme), this.popupType.popupSize, toggle, () -> this.width, false, "", false);
                }
                parent.addComponent((IComponent)new ComponentProxy<IComponent>(title) {
                    public void handleButton(final Context context, final int button) {
                        super.handleButton(context, button);
                        if (button == 1 && context.isClicked(button)) {
                            context.getPopupDisplayer().displayPopup((IPopup)popup, context.getRect(), toggle, ChildUtil.this.popupType.popupPos);
                            context.releaseFocus();
                        }
                    }
                });
                gui.addPopup(popup);
                break;
            }
        }
    }
    
    public enum ChildMode
    {
        DOWN, 
        POPUP, 
        DRAG_POPUP;
    }
}
