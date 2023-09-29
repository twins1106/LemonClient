//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.render.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.setting.*;
import com.lukflug.panelstudio.hud.*;
import com.lemonclient.client.module.*;
import com.lemonclient.client.*;
import com.lemonclient.client.clickgui.*;
import com.mojang.realmsclient.gui.*;
import java.util.*;
import java.awt.*;

@Module.Declaration(name = "ArrayList", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 200)
public class ArrayListModule extends HUDModule
{
    BooleanSetting sortUp;
    BooleanSetting sortRight;
    ColorSetting color;
    private final ModuleList list;
    
    public ArrayListModule() {
        this.sortUp = this.registerBoolean("Sort Up", true);
        this.sortRight = this.registerBoolean("Sort Right", false);
        this.color = this.registerColor("Color", new GSColor(255, 0, 0, 255));
        this.list = new ModuleList();
    }
    
    public void populate(final ITheme theme) {
        this.component = new ListComponent(new Labeled(this.getName(), null, () -> true), this.position, this.getName(), this.list, 9, 1);
    }
    
    public void onRender() {
        this.list.activeModules.clear();
        for (final Module module2 : ModuleManager.getModules()) {
            if (module2.isEnabled() && module2.isDrawn()) {
                this.list.activeModules.add(module2);
            }
        }
        final LemonClientGUI gameSenseGUI;
        this.list.activeModules.sort(Comparator.comparing(module -> {
            gameSenseGUI = LemonClient.INSTANCE.gameSenseGUI;
            return Integer.valueOf(-LemonClientGUI.guiInterface.getFontWidth(9, module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo()));
        }));
    }
    
    private class ModuleList implements HUDList
    {
        public List<Module> activeModules;
        
        private ModuleList() {
            this.activeModules = new ArrayList<Module>();
        }
        
        @Override
        public int getSize() {
            return this.activeModules.size();
        }
        
        @Override
        public String getItem(final int index) {
            final Module module = this.activeModules.get(index);
            return module.getHudInfo().equals("") ? module.getName() : (module.getName() + ChatFormatting.GRAY + " " + module.getHudInfo());
        }
        
        @Override
        public Color getItemColor(final int index) {
            final GSColor c = ArrayListModule.this.color.getValue();
            return Color.getHSBColor(c.getHue() + (ArrayListModule.this.color.getRainbow() ? (0.02f * index) : 0.0f), c.getSaturation(), c.getBrightness());
        }
        
        @Override
        public boolean sortUp() {
            return (boolean)ArrayListModule.this.sortUp.getValue();
        }
        
        @Override
        public boolean sortRight() {
            return (boolean)ArrayListModule.this.sortRight.getValue();
        }
    }
}
