//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.render.*;
import com.lukflug.panelstudio.theme.*;
import com.lukflug.panelstudio.hud.*;
import com.lukflug.panelstudio.setting.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import com.lemonclient.client.clickgui.*;
import java.awt.*;
import net.minecraft.util.*;
import com.lukflug.panelstudio.base.*;

@Module.Declaration(name = "InventoryViewer", category = Category.HUD)
@HUDModule.Declaration(posX = 0, posZ = 10)
public class InventoryViewer extends HUDModule
{
    ColorSetting fillColor;
    IntegerSetting fill;
    ColorSetting outlineColor;
    IntegerSetting outline;
    
    public InventoryViewer() {
        this.fillColor = this.registerColor("Fill", new GSColor(0, 0, 0, 100));
        this.fill = this.registerInteger("Fill Alpha", 100, 0, 255);
        this.outlineColor = this.registerColor("Outline", new GSColor(255, 0, 0, 255));
        this.outline = this.registerInteger("Outline Alpha", 255, 0, 255);
    }
    
    public void populate(final ITheme theme) {
        this.component = new InventoryViewerComponent(theme);
    }
    
    private class InventoryViewerComponent extends HUDComponent
    {
        public InventoryViewerComponent(final ITheme theme) {
            super(new Labeled(InventoryViewer.this.getName(), null, () -> true), InventoryViewer.this.position, InventoryViewer.this.getName());
        }
        
        @Override
        public void render(final Context context) {
            super.render(context);
            final Color bgcolor = (Color)new GSColor(InventoryViewer.this.fillColor.getValue(), (int)InventoryViewer.this.fill.getValue());
            context.getInterface().fillRect(context.getRect(), bgcolor, bgcolor, bgcolor, bgcolor);
            final Color color = (Color)new GSColor(InventoryViewer.this.outlineColor.getValue(), (int)InventoryViewer.this.outline.getValue());
            context.getInterface().fillRect(new Rectangle(context.getPos(), new Dimension(context.getSize().width, 1)), color, color, color, color);
            context.getInterface().fillRect(new Rectangle(context.getPos(), new Dimension(1, context.getSize().height)), color, color, color, color);
            context.getInterface().fillRect(new Rectangle(new Point(context.getPos().x + context.getSize().width - 1, context.getPos().y), new Dimension(1, context.getSize().height)), color, color, color, color);
            context.getInterface().fillRect(new Rectangle(new Point(context.getPos().x, context.getPos().y + context.getSize().height - 1), new Dimension(context.getSize().width, 1)), color, color, color, color);
            final NonNullList<ItemStack> items = (NonNullList<ItemStack>)Minecraft.getMinecraft().player.inventory.mainInventory;
            for (int size = items.size(), item = 9; item < size; ++item) {
                final int slotX = context.getPos().x + item % 9 * 18;
                final int slotY = context.getPos().y + 2 + (item / 9 - 1) * 18;
                LemonClientGUI.renderItem((ItemStack)items.get(item), new Point(slotX, slotY));
            }
        }
        
        @Override
        public Dimension getSize(final IInterface inter) {
            return new Dimension(162, 56);
        }
    }
}
