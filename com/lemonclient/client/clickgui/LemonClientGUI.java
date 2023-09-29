//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.client.clickgui;

import com.lukflug.panelstudio.mc12.*;
import com.lukflug.panelstudio.hud.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.api.util.font.*;
import com.lemonclient.api.util.render.*;
import net.minecraft.util.text.*;
import java.util.stream.*;
import org.lwjgl.input.*;
import com.lemonclient.api.setting.*;
import com.lemonclient.client.module.*;
import com.lukflug.panelstudio.base.*;
import com.lukflug.panelstudio.component.*;
import java.util.function.*;
import com.lukflug.panelstudio.widget.*;
import com.lukflug.panelstudio.theme.*;
import java.awt.*;
import com.lukflug.panelstudio.popup.*;
import java.util.*;
import com.lukflug.panelstudio.layout.*;
import com.lukflug.panelstudio.setting.*;
import com.lemonclient.api.setting.values.*;
import net.minecraft.item.*;
import com.lemonclient.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.inventory.*;
import com.lukflug.panelstudio.container.*;

public class LemonClientGUI extends MinecraftHUDGUI
{
    public static final int WIDTH = 100;
    public static final int HEIGHT = 12;
    public static final int FONT_HEIGHT = 9;
    public static final int DISTANCE = 10;
    public static final int HUD_BORDER = 2;
    public static IClient client;
    public static GUIInterface guiInterface;
    public static HUDGUI gui;
    private final ITheme theme;
    private final ITheme gameSenseTheme;
    private final ITheme clearTheme;
    
    public LemonClientGUI() {
        final ClickGuiModule clickGuiModule = ModuleManager.getModule(ClickGuiModule.class);
        final ColorMain colorMain = ModuleManager.getModule(ColorMain.class);
        LemonClientGUI.guiInterface = new GUIInterface(true) {
            @Override
            public void drawString(final Point pos, final int height, final String s, final Color c) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)pos.x, (float)pos.y, 0.0f);
                final double scale = height / (double)(FontUtil.getFontHeight((boolean)colorMain.customFont.getValue()) + (((boolean)colorMain.customFont.getValue()) ? 1 : 0));
                this.end(false);
                FontUtil.drawStringWithShadow((boolean)colorMain.customFont.getValue(), s, 0, 0, new GSColor(c));
                this.begin(false);
                GlStateManager.scale(scale, scale, 1.0);
                GlStateManager.popMatrix();
            }
            
            @Override
            public int getFontWidth(final int height, final String s) {
                final double scale = height / (double)(FontUtil.getFontHeight((boolean)colorMain.customFont.getValue()) + (((boolean)colorMain.customFont.getValue()) ? 1 : 0));
                return (int)Math.round(FontUtil.getStringWidth((boolean)colorMain.customFont.getValue(), s) * scale);
            }
            
            public double getScreenWidth() {
                return super.getScreenWidth();
            }
            
            public double getScreenHeight() {
                return super.getScreenHeight();
            }
            
            public String getResourcePrefix() {
                return "lemonclient:gui/";
            }
        };
        final ClickGuiModule clickGuiModule2;
        final Supplier<Boolean> themePredicate = () -> ((String)clickGuiModule2.theme.getValue()).equals("2.0") || ((String)clickGuiModule2.theme.getValue()).equals("2.1.2");
        this.gameSenseTheme = new GameSenseTheme(new GSColorScheme("lemonclient", () -> !themePredicate.get()), 9, 3, 5, ": " + TextFormatting.GRAY);
        this.clearTheme = new ClearTheme(new GSColorScheme("clear", themePredicate), () -> ((String)clickGuiModule.theme.getValue()).equals("2.1.2"), 9, 3, 1, ": " + TextFormatting.GRAY);
        this.theme = (() -> {
            if (themePredicate.get()) {
                return this.clearTheme;
            }
            else {
                return this.gameSenseTheme;
            }
        });
        LemonClientGUI.client = (() -> Arrays.stream(Category.values()).sorted(Comparator.comparing((Function<? super Category, ? extends Comparable>)Enum::toString)).map(category -> new ICategory() {
            final /* synthetic */ Category val$category;
            
            @Override
            public String getDisplayName() {
                return this.val$category.toString();
            }
            
            @Override
            public Stream<IModule> getModules() {
                return ModuleManager.getModulesInCategory(this.val$category).stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)Module::getName)).map(module -> new IModule() {
                    final /* synthetic */ Module val$module;
                    
                    @Override
                    public String getDisplayName() {
                        return this.val$module.getName();
                    }
                    
                    @Override
                    public IToggleable isEnabled() {
                        return new IToggleable() {
                            @Override
                            public boolean isOn() {
                                return IModule.this.val$module.isEnabled();
                            }
                            
                            @Override
                            public void toggle() {
                                IModule.this.val$module.toggle();
                            }
                        };
                    }
                    
                    @Override
                    public Stream<ISetting<?>> getSettings() {
                        final Stream temp = SettingsManager.getSettingsForModule(this.val$module).stream().map(setting -> LemonClientGUI.this.createSetting((Setting<?>)setting));
                        return Stream.concat((Stream<? extends ISetting<?>>)temp, Stream.concat((Stream<? extends ISetting<?>>)Stream.of(new IBooleanSetting() {
                            @Override
                            public String getDisplayName() {
                                return "Toggle Msgs";
                            }
                            
                            @Override
                            public void toggle() {
                                IModule.this.val$module.setToggleMsg(!IModule.this.val$module.isToggleMsg());
                            }
                            
                            @Override
                            public boolean isOn() {
                                return IModule.this.val$module.isToggleMsg();
                            }
                        }), (Stream<? extends ISetting<?>>)Stream.of((T)new IKeybindSetting() {
                            @Override
                            public String getDisplayName() {
                                return "Keybind";
                            }
                            
                            @Override
                            public int getKey() {
                                return IModule.this.val$module.getBind();
                            }
                            
                            @Override
                            public void setKey(final int key) {
                                IModule.this.val$module.setBind(key);
                            }
                            
                            @Override
                            public String getKeyName() {
                                return Keyboard.getKeyName(IModule.this.val$module.getBind());
                            }
                        })));
                    }
                });
            }
        }));
        final IToggleable guiToggle = new SimpleToggleable(false);
        final IToggleable hudToggle = new SimpleToggleable(false) {
            @Override
            public boolean isOn() {
                if (guiToggle.isOn() && super.isOn()) {
                    return (boolean)clickGuiModule.showHUD.getValue();
                }
                return super.isOn();
            }
        };
        LemonClientGUI.gui = new HUDGUI(LemonClientGUI.guiInterface, this.theme.getDescriptionRenderer(), new MousePositioner(new Point(10, 10)), guiToggle, hudToggle);
        final BiFunction<Context, Integer, Integer> scrollHeight = (BiFunction<Context, Integer, Integer>)((context, componentHeight) -> {
            if (((String)clickGuiModule.scrolling.getValue()).equals("Screen")) {
                return componentHeight;
            }
            else {
                return Integer.valueOf(Math.min(componentHeight, Math.max(48, this.height - context.getPos().y - 12)));
            }
        });
        final Supplier<Animation> animation = (Supplier<Animation>)(() -> new SettingsAnimation(() -> (Integer)clickGuiModule.animationSpeed.getValue(), () -> LemonClientGUI.guiInterface.getTime()));
        final PopupTuple popupType = new PopupTuple(new PanelPositioner(new Point(0, 0)), false, new IScrollSize() {
            @Override
            public int getScrollHeight(final Context context, final int componentHeight) {
                return scrollHeight.apply(context, componentHeight);
            }
        });
        for (final Module module : ModuleManager.getModules()) {
            if (module instanceof HUDModule) {
                ((HUDModule)module).populate(this.theme);
                LemonClientGUI.gui.addHUDComponent(((HUDModule)module).getComponent(), new IToggleable() {
                    @Override
                    public boolean isOn() {
                        return module.isEnabled();
                    }
                    
                    @Override
                    public void toggle() {
                        module.toggle();
                    }
                }, animation.get(), this.theme, 2);
            }
        }
        final IComponentAdder classicPanelAdder = new PanelAdder(new IContainer<IFixedComponent>() {
            @Override
            public boolean addComponent(final IFixedComponent component) {
                return LemonClientGUI.gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {
                    @Override
                    public void handleScroll(final Context context, final int diff) {
                        super.handleScroll(context, diff);
                        if (((String)clickGuiModule.scrolling.getValue()).equals("Screen")) {
                            final Point p = this.getPosition(LemonClientGUI.guiInterface);
                            p.translate(0, -diff);
                            this.setPosition(LemonClientGUI.guiInterface, p);
                        }
                    }
                    
                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                });
            }
            
            @Override
            public boolean addComponent(final IFixedComponent component, final IBoolean visible) {
                return LemonClientGUI.gui.addComponent(new IFixedComponentProxy<IFixedComponent>() {
                    @Override
                    public void handleScroll(final Context context, final int diff) {
                        super.handleScroll(context, diff);
                        if (((String)clickGuiModule.scrolling.getValue()).equals("Screen")) {
                            final Point p = this.getPosition(LemonClientGUI.guiInterface);
                            p.translate(0, -diff);
                            this.setPosition(LemonClientGUI.guiInterface, p);
                        }
                    }
                    
                    @Override
                    public IFixedComponent getComponent() {
                        return component;
                    }
                }, visible);
            }
            
            @Override
            public boolean removeComponent(final IFixedComponent component) {
                return LemonClientGUI.gui.removeComponent(component);
            }
        }, false, () -> !(boolean)clickGuiModule.csgoLayout.getValue(), title -> title) {
            @Override
            protected IScrollSize getScrollSize(final IResizable size) {
                return new IScrollSize() {
                    @Override
                    public int getScrollHeight(final Context context, final int componentHeight) {
                        return scrollHeight.apply(context, componentHeight);
                    }
                };
            }
        };
        final IComponentGenerator generator = new ComponentGenerator(scancode -> scancode == 211, character -> character >= 32, new TextFieldKeys()) {
            @Override
            public IComponent getColorComponent(final IColorSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
                return new ColorPickerComponent(setting, theme);
            }
            
            @Override
            public IComponent getStringComponent(final IStringSetting setting, final Supplier<Animation> animation, final IComponentAdder adder, final ThemeTuple theme, final int colorLevel, final boolean isContainer) {
                return new TextField(setting, this.keys, 0, new SimpleToggleable(false), theme.getTextRenderer(false, isContainer)) {
                    @Override
                    public boolean allowCharacter(final char character) {
                        return ComponentGenerator.this.charFilter.test(character) && character != '\u007f';
                    }
                };
            }
        };
        final ILayout classicPanelLayout = new PanelLayout(100, new Point(10, 10), 55, 22, animation, level -> ChildUtil.ChildMode.DOWN, level -> ChildUtil.ChildMode.DOWN, popupType);
        classicPanelLayout.populateGUI(classicPanelAdder, generator, LemonClientGUI.client, this.theme);
        final Rectangle rectangle;
        final PopupTuple colorPopup = new PopupTuple(new CenteredPositioner(() -> {
            new Rectangle(new Point(0, 0), LemonClientGUI.guiInterface.getWindowSize());
            return rectangle;
        }), true, new IScrollSize() {});
        final IComponentAdder horizontalCSGOAdder = new PanelAdder((IContainer<? super IFixedComponent>)LemonClientGUI.gui, true, () -> (boolean)clickGuiModule.csgoLayout.getValue(), title -> title);
        final ILayout horizontalCSGOLayout = new CSGOLayout(new Labeled("lemonclient", null, () -> true), new Point(100, 100), 480, 100, animation, "Enabled", true, true, 2, ChildUtil.ChildMode.DOWN, colorPopup) {
            @Override
            public int getScrollHeight(final Context context, final int componentHeight) {
                return 320;
            }
            
            @Override
            protected boolean isUpKey(final int key) {
                return key == 200;
            }
            
            @Override
            protected boolean isDownKey(final int key) {
                return key == 208;
            }
            
            @Override
            protected boolean isLeftKey(final int key) {
                return key == 203;
            }
            
            @Override
            protected boolean isRightKey(final int key) {
                return key == 205;
            }
        };
        horizontalCSGOLayout.populateGUI(horizontalCSGOAdder, generator, LemonClientGUI.client, this.theme);
    }
    
    @Override
    protected HUDGUI getGUI() {
        return LemonClientGUI.gui;
    }
    
    private ISetting<?> createSetting(final Setting<?> setting) {
        if (setting instanceof BooleanSetting) {
            return new IBooleanSetting() {
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }
                
                @Override
                public IBoolean isVisible() {
                    return () -> setting.isVisible();
                }
                
                @Override
                public void toggle() {
                    ((BooleanSetting)setting).setValue((Object)!(boolean)((BooleanSetting)setting).getValue());
                }
                
                @Override
                public boolean isOn() {
                    return (boolean)((BooleanSetting)setting).getValue();
                }
                
                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    if (setting.getSubSettings().count() == 0L) {
                        return null;
                    }
                    return setting.getSubSettings().map(subSetting -> LemonClientGUI.this.createSetting((Setting<?>)subSetting));
                }
            };
        }
        if (setting instanceof IntegerSetting) {
            return new INumberSetting() {
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }
                
                @Override
                public IBoolean isVisible() {
                    return () -> setting.isVisible();
                }
                
                @Override
                public double getNumber() {
                    return (int)((IntegerSetting)setting).getValue();
                }
                
                @Override
                public void setNumber(final double value) {
                    ((IntegerSetting)setting).setValue((Object)(int)Math.round(value));
                }
                
                @Override
                public double getMaximumValue() {
                    return ((IntegerSetting)setting).getMax();
                }
                
                @Override
                public double getMinimumValue() {
                    return ((IntegerSetting)setting).getMin();
                }
                
                @Override
                public int getPrecision() {
                    return 0;
                }
                
                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    if (setting.getSubSettings().count() == 0L) {
                        return null;
                    }
                    return setting.getSubSettings().map(subSetting -> LemonClientGUI.this.createSetting((Setting<?>)subSetting));
                }
            };
        }
        if (setting instanceof DoubleSetting) {
            return new INumberSetting() {
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }
                
                @Override
                public IBoolean isVisible() {
                    return () -> setting.isVisible();
                }
                
                @Override
                public double getNumber() {
                    return (double)((DoubleSetting)setting).getValue();
                }
                
                @Override
                public void setNumber(final double value) {
                    ((DoubleSetting)setting).setValue((Object)value);
                }
                
                @Override
                public double getMaximumValue() {
                    return ((DoubleSetting)setting).getMax();
                }
                
                @Override
                public double getMinimumValue() {
                    return ((DoubleSetting)setting).getMin();
                }
                
                @Override
                public int getPrecision() {
                    return 2;
                }
                
                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    if (setting.getSubSettings().count() == 0L) {
                        return null;
                    }
                    return setting.getSubSettings().map(subSetting -> LemonClientGUI.this.createSetting((Setting<?>)subSetting));
                }
            };
        }
        if (setting instanceof ModeSetting) {
            return new IEnumSetting() {
                private final ILabeled[] states = (ILabeled[])((ModeSetting)setting).getModes().stream().map(mode -> new Labeled(mode, null, () -> 1)).toArray(ILabeled[]::new);
                
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }
                
                @Override
                public IBoolean isVisible() {
                    return () -> setting.isVisible();
                }
                
                @Override
                public void increment() {
                    ((ModeSetting)setting).increment();
                }
                
                @Override
                public void decrement() {
                    ((ModeSetting)setting).decrement();
                }
                
                @Override
                public String getValueName() {
                    return (String)((ModeSetting)setting).getValue();
                }
                
                @Override
                public int getValueIndex() {
                    return ((ModeSetting)setting).getModes().indexOf(this.getValueName());
                }
                
                @Override
                public void setValueIndex(final int index) {
                    ((ModeSetting)setting).setValue(((ModeSetting)setting).getModes().get(index));
                }
                
                @Override
                public ILabeled[] getAllowedValues() {
                    return this.states;
                }
                
                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    if (setting.getSubSettings().count() == 0L) {
                        return null;
                    }
                    return setting.getSubSettings().map(subSetting -> LemonClientGUI.this.createSetting((Setting<?>)subSetting));
                }
            };
        }
        if (setting instanceof ColorSetting) {
            return new IColorSetting() {
                @Override
                public String getDisplayName() {
                    return TextFormatting.BOLD + setting.getName();
                }
                
                @Override
                public IBoolean isVisible() {
                    return () -> setting.isVisible();
                }
                
                @Override
                public Color getValue() {
                    return (Color)((ColorSetting)setting).getValue();
                }
                
                @Override
                public void setValue(final Color value) {
                    ((ColorSetting)setting).setValue(new GSColor(value));
                }
                
                @Override
                public Color getColor() {
                    return (Color)((ColorSetting)setting).getColor();
                }
                
                @Override
                public boolean getRainbow() {
                    return ((ColorSetting)setting).getRainbow();
                }
                
                @Override
                public void setRainbow(final boolean rainbow) {
                    ((ColorSetting)setting).setRainbow(rainbow);
                }
                
                @Override
                public boolean hasAlpha() {
                    return ((ColorSetting)setting).alphaEnabled();
                }
                
                @Override
                public boolean allowsRainbow() {
                    return ((ColorSetting)setting).rainbowEnabled();
                }
                
                @Override
                public boolean hasHSBModel() {
                    return ((String)ModuleManager.getModule(ColorMain.class).colorModel.getValue()).equalsIgnoreCase("HSB");
                }
                
                @Override
                public Stream<ISetting<?>> getSubSettings() {
                    final Stream<ISetting<?>> temp = setting.getSubSettings().map(subSetting -> LemonClientGUI.this.createSetting((Setting<?>)subSetting));
                    return Stream.concat((Stream<? extends ISetting<?>>)temp, (Stream<? extends ISetting<?>>)Stream.of((T)new IBooleanSetting() {
                        @Override
                        public String getDisplayName() {
                            return "Sync Color";
                        }
                        
                        @Override
                        public IBoolean isVisible() {
                            return () -> setting != ModuleManager.getModule(ColorMain.class).enabledColor;
                        }
                        
                        @Override
                        public void toggle() {
                            ((ColorSetting)setting).setValue(ModuleManager.getModule(ColorMain.class).enabledColor.getColor());
                            ((ColorSetting)setting).setRainbow(ModuleManager.getModule(ColorMain.class).enabledColor.getRainbow());
                        }
                        
                        @Override
                        public boolean isOn() {
                            return ModuleManager.getModule(ColorMain.class).enabledColor.getColor().equals((Object)((ColorSetting)setting).getColor());
                        }
                    }));
                }
            };
        }
        if (setting instanceof StringSetting) {
            return new IStringSetting() {
                @Override
                public String getValue() {
                    return ((StringSetting)setting).getText();
                }
                
                @Override
                public void setValue(final String string) {
                    ((StringSetting)setting).setText(string);
                }
                
                @Override
                public String getDisplayName() {
                    return setting.getName();
                }
            };
        }
        return new ISetting<Void>() {
            @Override
            public String getDisplayName() {
                return setting.getName();
            }
            
            @Override
            public IBoolean isVisible() {
                return () -> setting.isVisible();
            }
            
            @Override
            public Void getSettingState() {
                return null;
            }
            
            @Override
            public Class<Void> getSettingClass() {
                return Void.class;
            }
            
            @Override
            public Stream<ISetting<?>> getSubSettings() {
                if (setting.getSubSettings().count() == 0L) {
                    return null;
                }
                return setting.getSubSettings().map(subSetting -> LemonClientGUI.this.createSetting((Setting<?>)subSetting));
            }
        };
    }
    
    public static void renderItem(final ItemStack item, final Point pos) {
        LemonClient.INSTANCE.gameSenseGUI.getInterface().end(false);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GL11.glPushAttrib(524288);
        GL11.glDisable(3089);
        GlStateManager.clear(256);
        GL11.glPopAttrib();
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getRenderItem().zLevel = -150.0f;
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, pos.x, pos.y);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, item, pos.x, pos.y);
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        LemonClient.INSTANCE.gameSenseGUI.getInterface().begin(false);
    }
    
    public static void renderItemTest(final ItemStack item, final Point pos) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GL11.glPushAttrib(524288);
        GL11.glDisable(3089);
        GlStateManager.clear(256);
        GL11.glPopAttrib();
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        Minecraft.getMinecraft().getRenderItem().zLevel = -150.0f;
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, pos.x, pos.y);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, item, pos.x, pos.y);
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
    }
    
    public static void renderEntity(final EntityLivingBase entity, final Point pos, final int scale) {
        LemonClient.INSTANCE.gameSenseGUI.getInterface().end(false);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GL11.glPushAttrib(524288);
        GL11.glDisable(3089);
        GlStateManager.clear(256);
        GL11.glPopAttrib();
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiInventory.drawEntityOnScreen(pos.x, pos.y, scale, 28.0f, 60.0f, entity);
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        LemonClient.INSTANCE.gameSenseGUI.getInterface().begin(false);
    }
    
    @Override
    protected GUIInterface getInterface() {
        return LemonClientGUI.guiInterface;
    }
    
    @Override
    protected int getScrollSpeed() {
        return (int)ModuleManager.getModule(ClickGuiModule.class).scrollSpeed.getValue();
    }
    
    private final class GSColorScheme implements IColorScheme
    {
        private final String configName;
        private final Supplier<Boolean> isVisible;
        
        public GSColorScheme(final String configName, final Supplier<Boolean> isVisible) {
            this.configName = configName;
            this.isVisible = isVisible;
        }
        
        @Override
        public void createSetting(final ITheme theme, final String name, final String description, final boolean hasAlpha, final boolean allowsRainbow, final Color color, final boolean rainbow) {
            final ClickGuiModule clickGuiModule = ModuleManager.getModule(ClickGuiModule.class);
            clickGuiModule.theme.addSubSetting((Setting)new ColorSetting(name, this.configName + "_" + name.replace(" ", ""), (Module)clickGuiModule, (Supplier)this.isVisible, rainbow, allowsRainbow, hasAlpha, new GSColor(color)));
        }
        
        @Override
        public Color getColor(final String name) {
            return (Color)ModuleManager.getModule(ClickGuiModule.class).theme.getSubSettings().filter(setting -> setting.getConfigName().equals(this.configName + "_" + name.replace(" ", ""))).findFirst().orElse(null).getValue();
        }
    }
}
