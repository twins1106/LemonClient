//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\¹ÙÅÁ È­¸é\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.hud;

import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import com.lemonclient.api.util.render.*;
import com.lemonclient.client.module.modules.gui.*;
import com.lemonclient.client.module.*;
import com.lemonclient.api.util.font.*;
import java.util.*;

@Module.Declaration(name = "ArmorHUD", category = Category.HUD)
public class ArmorHUD extends Module
{
    public void onRender() {
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        final ScaledResolution resolution = new ScaledResolution(ArmorHUD.mc);
        final int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        final int y = resolution.getScaledHeight() - 55 - (ArmorHUD.mc.player.isInWater() ? 10 : 0);
        for (final ItemStack is : ArmorHUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            ArmorHUD.mc.getRenderItem().zLevel = 200.0f;
            ArmorHUD.mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y);
            ArmorHUD.mc.getRenderItem().renderItemOverlayIntoGUI(ArmorHUD.mc.fontRenderer, is, x, y, "");
            ArmorHUD.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            ArmorHUD.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - ArmorHUD.mc.fontRenderer.getStringWidth(s)), (float)(y + 9), new GSColor(255, 255, 255).getRGB());
            float green = (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage();
            float red = 1.0f - green;
            int dmg = 100 - (int)(red * 100.0f);
            if (green > 1.0f) {
                green = 1.0f;
            }
            else if (green < 0.0f) {
                green = 0.0f;
            }
            if (red > 1.0f) {
                red = 1.0f;
            }
            if (dmg < 0) {
                dmg = 0;
            }
            FontUtil.drawStringWithShadow((boolean)((ColorMain)ModuleManager.getModule((Class)ColorMain.class)).customFont.getValue(), dmg + "", x + 8 - ArmorHUD.mc.fontRenderer.getStringWidth(dmg + "") / 2, y - 11, new GSColor((int)(red * 255.0f), (int)(green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
}
