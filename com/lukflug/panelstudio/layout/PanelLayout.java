//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lukflug.panelstudio.layout;

import java.awt.*;
import java.util.function.*;
import com.lukflug.panelstudio.popup.*;
import com.lukflug.panelstudio.container.*;
import java.util.concurrent.atomic.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.component.*;

public class PanelLayout implements ILayout
{
    protected int width;
    protected Point start;
    protected int skipX;
    protected int skipY;
    protected Supplier<Animation> animation;
    protected IntFunction<ChildUtil.ChildMode> layoutType;
    protected IntFunction<ChildUtil.ChildMode> colorType;
    protected ChildUtil util;
    
    public PanelLayout(final int width, final Point start, final int skipX, final int skipY, final Supplier<Animation> animation, final IntFunction<ChildUtil.ChildMode> layoutType, final IntFunction<ChildUtil.ChildMode> colorType, final PopupTuple popupType) {
        this.width = width;
        this.start = start;
        this.skipX = skipX;
        this.skipY = skipY;
        this.animation = animation;
        this.layoutType = layoutType;
        this.colorType = colorType;
        this.util = new ChildUtil(width, (Supplier)animation, popupType);
    }
    
    public void populateGUI(final IComponentAdder gui, final IComponentGenerator components, final IClient client, final ITheme theme) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/lukflug/panelstudio/layout/PanelLayout.start:Ljava/awt/Point;
        //     4: astore          pos
        //     6: new             Ljava/util/concurrent/atomic/AtomicInteger;
        //     9: dup            
        //    10: aload_0         /* this */
        //    11: getfield        com/lukflug/panelstudio/layout/PanelLayout.skipY:I
        //    14: invokespecial   java/util/concurrent/atomic/AtomicInteger.<init>:(I)V
        //    17: astore          skipY
        //    19: aload_3         /* client */
        //    20: invokeinterface com/lukflug/panelstudio/setting/IClient.getCategories:()Ljava/util/stream/Stream;
        //    25: aload_0         /* this */
        //    26: aload           theme
        //    28: aload_1         /* gui */
        //    29: aload           pos
        //    31: aload           skipY
        //    33: aload_2         /* components */
        //    34: invokedynamic   BootstrapMethod #0, accept:(Lcom/lukflug/panelstudio/layout/PanelLayout;Lcom/lukflug/panelstudio/theme/ITheme;Lcom/lukflug/panelstudio/layout/IComponentAdder;Ljava/awt/Point;Ljava/util/concurrent/atomic/AtomicInteger;Lcom/lukflug/panelstudio/layout/IComponentGenerator;)Ljava/util/function/Consumer;
        //    39: invokeinterface java/util/stream/Stream.forEach:(Ljava/util/function/Consumer;)V
        //    44: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected <T> void addSettingsComponent(final ISetting<T> setting, final VerticalContainer container, final IComponentAdder gui, final IComponentGenerator components, final ThemeTuple theme) {
        final int nextLevel = (this.layoutType.apply(theme.logicalLevel - 1) == ChildUtil.ChildMode.DOWN) ? theme.graphicalLevel : 0;
        final int colorLevel = (this.colorType.apply(theme.logicalLevel - 1) == ChildUtil.ChildMode.DOWN) ? theme.graphicalLevel : 0;
        final boolean isContainer = setting.getSubSettings() != null && this.layoutType.apply(theme.logicalLevel - 1) == ChildUtil.ChildMode.DOWN;
        final IComponent component = components.getComponent((ISetting)setting, (Supplier)this.animation, gui, theme, colorLevel, isContainer);
        if (component instanceof VerticalContainer) {
            final VerticalContainer colorContainer = (VerticalContainer)component;
            final Button<T> button = new Button<T>(setting, () -> setting.getSettingState(), theme.getButtonRenderer(setting.getSettingClass(), this.colorType.apply(theme.logicalLevel - 1) == ChildUtil.ChildMode.DOWN));
            this.util.addContainer((ILabeled)setting, (IComponent)button, (IComponent)colorContainer, () -> setting.getSettingState(), (Class)setting.getSettingClass(), container, gui, new ThemeTuple(theme.theme, theme.logicalLevel, colorLevel), (ChildUtil.ChildMode)this.colorType.apply(theme.logicalLevel - 1));
            if (setting.getSubSettings() != null) {
                setting.getSubSettings().forEach(subSetting -> this.addSettingsComponent(subSetting, colorContainer, gui, components, new ThemeTuple(theme.theme, theme.logicalLevel + 1, colorLevel + 1)));
            }
        }
        else if (setting.getSubSettings() != null) {
            final VerticalContainer settingContainer = new VerticalContainer((ILabeled)setting, theme.theme.getContainerRenderer(theme.logicalLevel, nextLevel, false));
            this.util.addContainer((ILabeled)setting, component, (IComponent)settingContainer, () -> setting.getSettingState(), (Class)setting.getSettingClass(), container, gui, new ThemeTuple(theme.theme, theme.logicalLevel, nextLevel), (ChildUtil.ChildMode)this.layoutType.apply(theme.logicalLevel - 1));
            setting.getSubSettings().forEach(subSetting -> this.addSettingsComponent(subSetting, settingContainer, gui, components, new ThemeTuple(theme.theme, theme.logicalLevel + 1, nextLevel + 1)));
        }
        else {
            container.addComponent(component);
        }
    }
}
